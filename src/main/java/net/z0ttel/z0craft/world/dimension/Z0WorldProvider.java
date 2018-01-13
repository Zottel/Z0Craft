package net.z0ttel.z0craft.world.dimension;

import net.minecraft.world.WorldProvider;

import net.minecraft.init.Biomes;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.ChunkProviderServer;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import net.z0ttel.z0craft.Z0Craft;

public class Z0WorldProvider extends WorldProvider{
	
	public void init()
	{
			this.biomeProvider = new BiomeProviderSingle(Biomes.SKY);
			
			this.hasSkyLight = false;
	}
	
	public IChunkGenerator createChunkGenerator()
	{
		return new Z0ChunkProvider(this.world, this.world.getSeed());
	}
	
	public DimensionType getDimensionType()
	{
		// TODO: hope this gets called only after registration >.<
		return DimensionType.getById(Z0Craft.config.dimensionID);
	}
	
	
	public boolean canRespawnHere()
	{
		return false;
	}
	
	public boolean isSurfaceWorld()
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isSkyColored()
	{
		return false;
	}
	
	
}
