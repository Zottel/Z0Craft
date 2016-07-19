package com.z0ttel.z0craft.dimension;

import java.lang.reflect.Method;

import java.util.List;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ICrashReportDetail;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.entity.Entity;

import net.minecraft.server.MinecraftServer;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

import net.minecraftforge.fml.common.gameevent.TickEvent;


import com.z0ttel.z0craft.Z0Craft;
import com.z0ttel.z0craft.util.EntityPos;

public class Z0Teleporter extends Teleporter {
	public static enum Direction {
		UP, DOWN
	};
	
	private final WorldServer worldServer;
	private Direction direction;
	private EntityPos ePos;
	
	public Z0Teleporter(WorldServer worldIn, Direction directionIn, EntityPos ePosIn) {
		super(worldIn);
		//Z0Craft.logger.info("Z0Teleporter/Z0Teleporter(worldIn) called");
		
		worldServer = worldIn;
		direction = directionIn;
		ePos = ePosIn;
	}
	
	private void placeAtHeight(Entity entityIn, double height) {
		placeAt(entityIn, ePos.x, height, ePos.z);
	}
	
	private void placeAt(Entity entityIn, double x, double y, double z) {
		//double x = (double)blockpos.getX() + 0.5D;
		//double z = (double)blockpos.getZ() + 0.5D;
		//entityIn.posY
		//x = ((long) x) - 1.5; // TODO: Fix this fix into understanding.
		//z = ((long) z) - 0.5;
		
		if (entityIn instanceof EntityPlayerMP)
		{
			((EntityPlayerMP)entityIn).connection.setPlayerLocation(x, y, z, entityIn.rotationYaw, entityIn.rotationPitch);
		}
		else
		{
			entityIn.setLocationAndAngles(x, y, z, entityIn.rotationYaw, entityIn.rotationPitch);
		}
	}
	
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		//Z0Craft.logger.info("Z0Teleporter/placeInPortal called for entity: " + entityIn);
		
		// Do at least call the methods we don't need...
		if(!this.placeInExistingPortal(entityIn, rotationYaw))
		{
			if(this.makePortal(entityIn)) {
				this.placeInExistingPortal(entityIn, rotationYaw);
			} else {
				placeAtHeight(entityIn, 256);
			}
		}
	}
	
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		BlockPos.MutableBlockPos iterator =
			new BlockPos.MutableBlockPos(ePos.block());
		//Z0Craft.logger.info("Z0Teleporter/placeInExistingPortal called at " + ePos);
		
		ChunkProviderServer providerServer = worldServer.getChunkProvider();
		IChunkGenerator generator = providerServer.chunkGenerator;
		
		// Make sure the blocks surrounding the current one are generated
		// and loaded (it won't be populated otherwise)
		ChunkPos chunkPos = new ChunkPos(iterator);
		for (int x = chunkPos.chunkXPos - 1; x <= chunkPos.chunkXPos + 1; x++)
			for (int z = chunkPos.chunkZPos - 1; z <= chunkPos.chunkZPos + 1; z++)
				providerServer.provideChunk(x, z);
		
		// make sure the portal is spawned in:
		Chunk chunk = worldServer.getChunkFromBlockCoords(iterator);
		chunk.populateChunk(providerServer, generator);
		
		if (!chunk.isTerrainPopulated()) {
			Z0Craft.logger.info("Z0Teleporter: EVERYTHING IS ASPLODE! " + chunk);
			Z0Craft.logger.info("" + providerServer + ", " + generator);
			
			if (!chunk.isTerrainPopulated()) {
				Z0Craft.logger.info("Z0Teleporter: EVERYTHING IS EVEN MORE ASPLODE!");
			}
		}
		
		// Derp correction:
		//iterator.setPos(iterator.getX() - 1, iterator.getY(), iterator.getZ() - 1);
		
		// Count blocks of air for player to be placed in.
		// Stop when first opening of 2 or 3 blocks of air is found.
		int freeBlocks = 0;
		int step = direction == Direction.UP ? 1 : -1;
		int start = direction == Direction.UP ? 0 : 255;
		int stop = direction == Direction.UP ? 256 : -1;
		
		for(int i = start; i != stop; i += step) {
			iterator.setY(i);
			
			// Maybe try Block.isAir too?
			// (may help with mod air)
			boolean isAir = worldServer.getBlockState(iterator).getBlock() == Z0Craft.blocks.PORTAL;
			isAir = isAir || worldServer.isAirBlock(iterator);
			if(isAir) {
				freeBlocks++;
				if(freeBlocks == 3) {
					placeAtHeight(entityIn,
					              Math.min(i, i - (2 * step)));
					return true;
				}
			} else {
				if(freeBlocks == 2) {
					placeAtHeight(entityIn,
					              Math.min(i - step, i - (2 * step)));
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean makePortal(Entity entityIn) {
		//Z0Craft.logger.info("Z0Teleporter/makePortal called");
		return false;
	}
	
	public void removeStalePortalLocations(long worldTime) {
		//Z0Craft.logger.info("Z0Teleporter/removeStalePortalLocations called");
	}
	/*public class PortalPosition extends BlockPos {
		public PortalPosition() {
			Z0Craft.logger.info("Z0Teleporter/PortalPosition/PortalPosition called");
		}
	}*/
	
}
