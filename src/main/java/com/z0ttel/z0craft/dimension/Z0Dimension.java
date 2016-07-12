package com.z0ttel.z0craft.dimension;

import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.DimensionType;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import net.minecraftforge.common.DimensionManager;

import com.z0ttel.z0craft.dimension.Z0WorldProvider;
import com.z0ttel.z0craft.Z0Craft;

public class Z0Dimension {
	
	public void preInit(FMLPreInitializationEvent event) {
	}
	
	public void init(FMLInitializationEvent event) {
		
		DimensionType dimT = DimensionType.register("Z0Dimension", "_z0dim", Z0Craft.config.dimensionID, Z0WorldProvider.class, false);
		DimensionManager.registerDimension(Z0Craft.config.dimensionID, dimT);
		
		//DimensionManager.registerProviderType(Z0Craft.config.dimensionID, Z0WorldProvider.class, false);
		//DimensionManager.registerDimension(Z0Craft.config.dimensionID, Z0Craft.config.dimensionID);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}
}

