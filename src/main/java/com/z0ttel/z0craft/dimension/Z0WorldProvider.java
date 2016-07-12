package com.z0ttel.z0craft.dimension;

import net.minecraft.world.WorldProvider;

import net.minecraft.init.Biomes;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.ChunkProviderEnd;

import com.z0ttel.z0craft.Z0Craft;

public class Z0WorldProvider extends WorldProvider{
	
	public Z0WorldProvider() {
		this.hasNoSky = true;
	}
	
	public void createBiomeProvider()
	{
		this.biomeProvider = new BiomeProviderSingle(Biomes.SKY);
	}
	
	public IChunkGenerator createChunkGenerator()
	{
		return new Z0ChunkProvider(this.worldObj, this.worldObj.getSeed());
	}
	
	public boolean canRespawnHere()
	{
		return false;
	}
	
	public boolean isSurfaceWorld()
	{
		return false;
	}
	
	public DimensionType getDimensionType()
	{
		// TODO: hope this gets called only after registration >.<
		return DimensionType.getById(Z0Craft.config.dimensionID);
	}
	
	
	
}
