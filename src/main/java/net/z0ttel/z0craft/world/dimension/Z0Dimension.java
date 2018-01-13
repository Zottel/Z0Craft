package net.z0ttel.z0craft.world.dimension;

import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.util.math.ChunkPos;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.DimensionManager;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import net.z0ttel.z0craft.world.dimension.Z0WorldProvider;
import net.z0ttel.z0craft.world.dimension.Z0Teleporter;
import net.z0ttel.z0craft.Z0Craft;

public class Z0Dimension {
	public DimensionType dimType;
	public Z0DimensionTransfer transfer = new Z0DimensionTransfer();
	
	public void preInit(FMLPreInitializationEvent event) {
		Z0Craft.logger.info("Z0Dimension/preInit called");
	}
	
	public void init(FMLInitializationEvent event) {
		Z0Craft.logger.info("Z0Dimension/init called");
		
		dimType = DimensionType.register("Z0Dimension", "_z0dim", Z0Craft.config.dimensionID, Z0WorldProvider.class, false);
		DimensionManager.registerDimension(Z0Craft.config.dimensionID, dimType);
		
		// Required for PopulateChunkEvent
		// (which I use for spawning structures in the overworld)
		MinecraftForge.EVENT_BUS.register(this);
		
		// Required for tick-phased teleport.
		MinecraftForge.EVENT_BUS.register(transfer);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		Z0Craft.logger.info("Z0Dimension/postInit called");
	}
	
	// The overworld will be populated alongside other stuffs.
	@SubscribeEvent(priority = EventPriority.LOW)
	public void OnChunkPopulated(PopulateChunkEvent.Post event) {
		ChunkPos chunk = new ChunkPos(event.getChunkX(), event.getChunkZ());
		//Z0Craft.logger.info("Z0Dimension/OnChunkPopulated called for " + chunk);
		
		if(event.getWorld().provider.getDimensionType() == DimensionType.OVERWORLD) {
			Z0Craft.logger.info("Z0Dimension/OnChunkPopulated called for Overworld " + chunk);
			long seed = event.getWorld().getSeed();
			//ChunkPos chunk = new ChunkPos(event.getChunkX(), event.getChunkZ());
			
			// Have structures module fill the chunk.
			Z0Craft.structures.fillChunk(event.getWorld(), seed, chunk);
		}
	}
}

