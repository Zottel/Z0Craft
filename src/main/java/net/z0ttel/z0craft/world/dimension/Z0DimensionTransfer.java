package net.z0ttel.z0craft.world.dimension;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ICrashReportDetail;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraft.launchwrapper.Launch;

import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.entity.Entity;

import net.minecraft.server.MinecraftServer;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.ReportedException;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

import net.minecraftforge.fml.common.gameevent.TickEvent;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;


import net.z0ttel.z0craft.Z0Craft;
import net.z0ttel.z0craft.util.EntityPos;

public class Z0DimensionTransfer {
	// Storage for delayed transfers.
	// Will be done at end of server tick to get rid of heisenbugs.
	private static Map<Entity, Integer> toTransfer = new HashMap<Entity, Integer>();
	private Set<Entity> done = new HashSet<Entity>();
	
	public synchronized void queueToDimension(Entity entityIn, int destinationDim) {
		if(entityIn.world.isRemote) {
			return;
		}
		
		if(!toTransfer.containsKey(entityIn)) {
			toTransfer.put(entityIn, destinationDim);
		}
		
		Z0Craft.logger.info("Queued entity for transport across dimension: " + entityIn);
	}
	
	protected void toDimension(Entity entityIn, int destinationDim) {
		toDimensionEntity(entityIn, destinationDim);
	}
	protected Entity toDimensionEntity(Entity entityIn, int destinationDim) {
		// if riding: recurse to lowest entity in riding chain and return.
		if(entityIn.isRiding()) {
			toDimension(entityIn.getLowestRidingEntity(), destinationDim);
			return null;
		}
		
		Z0Craft.logger.info("Transporting entity across dimension: " + entityIn);
		
		// Add entityIn to entities marked as done this tick
		done.add(entityIn);
		
		if(entityIn.world.isRemote) {
			return null;
		}
		MinecraftServer mcServer = ((WorldServer) entityIn.world).getMinecraftServer();
		
		//entity.dimension
		int sourceDim = entityIn.world.provider.getDimension();
		
		WorldServer sourceWorldServer = mcServer.getWorld(sourceDim);
		WorldServer destinationWorldServer = mcServer.getWorld(destinationDim);
		
		Z0Teleporter.Direction direction =
			destinationDim == 0 ? Z0Teleporter.Direction.UP : Z0Teleporter.Direction.DOWN;
		
		// Our teleporter remembers the exact position before transfer
		EntityPos ePos = new EntityPos(entityIn);
		Teleporter teleporter =
			new Z0Teleporter(destinationWorldServer,
			                 direction,
			                 ePos);
		
		// Save velocity, thanks Vanilla
		double motionX = entityIn.motionX,
		       motionY = entityIn.motionY,
		       motionZ = entityIn.motionZ;
		
		// Dismount riding passengers, but remember them.
		List<Entity> passengers = entityIn.getPassengers();
		// TODO: Remember relative position to make transition less
		//       clunky.
		entityIn.removePassengers();
		
		// Remove from world, this entity will not be seen here again.
		//entityIn.world.removeEntity(entityIn);
		
		if(entityIn instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)entityIn;
			
			mcServer.getPlayerList().transferPlayerToDimension(player, destinationDim, teleporter);
		} else {
			//mcServer.getPlayerList().transferEntityToWorld(Entity entityIn, int lastDimension, WorldServer oldWorldIn, WorldServer toWorldIn, net.minecraft.world.Teleporter teleporter)
			
			//mcServer.getPlayerList().transferEntityToWorld(entityIn, sourceDim, sourceWorldServer, destinationWorldServer, teleporter);
			entityIn = transferEntity(entityIn, sourceWorldServer, destinationWorldServer, teleporter);

			//Z0Craft.logger.info("Transported entity across dimension: " + entityIn);
			
			entityIn.timeUntilPortal = 100; // 2 seconds
		}
		
		// public boolean startRiding(Entity entityIn, boolean force)
		for(Entity passenger: passengers) {
			passenger = toDimensionEntity(passenger, destinationDim);
			//Z0Craft.logger.info("mounting {} on {}", passenger, entityIn);
			
			
			passenger.startRiding(entityIn, true);
			passenger.updateRidden();
		}
		
		// TODO: maybe update only specific entity?
		sourceWorldServer.updateEntities();
		destinationWorldServer.updateEntities();
		
		//destinationWorldServer.updateEntities();
		
		//destinationWorldServer.tickUpdates(true); // true = force?
		
		entityIn.motionX = motionX;
		entityIn.motionY = motionY;
		entityIn.motionZ = motionZ;
		
		return entityIn;
	}
	
	protected Entity transferEntity(Entity entityIn, WorldServer worldServerSrc, WorldServer worldServerDest, Teleporter teleporter) {
		//entityIn.world.removeEntity(entityIn);
		entityIn.isDead = false;
		
		Entity entity = EntityList.newEntity(entityIn.getClass(), worldServerDest);
		
		if (entity != null)
		{
			// entity.copyDataFromOld(this);
			try {
				// Obfuscated env requires other method name
				String mName = ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) ? "copyDataFromOld" : "func_180432_n";
				
				Method copyMethod = Entity.class.getDeclaredMethod(mName, new Class[] {Entity.class});
				copyMethod.setAccessible(true);
				copyMethod.invoke(entity, entityIn);
			} catch(Exception e) {
				CrashReport crashreport1 = CrashReport.makeCrashReport(e, "Unable to copy entity data via private method!");
				worldServerSrc.addWorldInfoToCrashReport(crashreport1);
				throw new ReportedException(crashreport1);
			}
			
			teleporter.placeInPortal(entity, 0.0F);
			
			boolean flag = entity.forceSpawn;
			entity.forceSpawn = true;
			worldServerDest.spawnEntity(entity);
			entity.forceSpawn = flag;
			worldServerDest.updateEntityWithOptionalForce(entity, true);
		}

		entityIn.isDead = true;
		return entity;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public synchronized void onServerTickEvent(TickEvent.ServerTickEvent tickEvent) {
		//this.theProfiler.startSection("pollingChunks");
		if(tickEvent.phase == TickEvent.Phase.END) {
			//Z0Craft.logger.info("dimension transfer tick start");
			for(Entity entity: toTransfer.keySet()) {
				if(!done.contains(entity)) {
					toDimension(entity, toTransfer.get(entity));
				}
			}
			
			if(done.size() > 0) {
				toTransfer.clear();
				done.clear();
			}
			//Z0Craft.logger.info("dimension transfer tick end");
		}
		
		//this.theProfiler.endSection("pollingChunks");
	}
}
