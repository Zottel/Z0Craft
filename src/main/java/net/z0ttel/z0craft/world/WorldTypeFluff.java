package net.z0ttel.z0craft;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

class WorldTypeFluff extends WorldType {
	
	public String setting = "3;1*minecraft:bedrock,1*minecraft:glowstone,3*minecraft:air,59*minecraft:wool:0;2;";
	
	public WorldTypeFluff() {
		super("FLUFF");
		//this.hasSkyLight = true;
	}
	
	
	public net.minecraft.world.biome.BiomeProvider getBiomeProvider(World world) {
		//net.minecraft.world.gen.FlatGeneratorInfo flatgeneratorinfo = net.minecraft.world.gen.FlatGeneratorInfo.createFlatGeneratorFromString(world.getWorldInfo().getGeneratorOptions());
		net.minecraft.world.gen.FlatGeneratorInfo flatgeneratorinfo = net.minecraft.world.gen.FlatGeneratorInfo.createFlatGeneratorFromString(setting);
		return new net.minecraft.world.biome.BiomeProviderSingle(net.minecraft.world.biome.Biome.getBiome(flatgeneratorinfo.getBiome(), net.minecraft.init.Biomes.DEFAULT));
	}
	
	public net.minecraft.world.gen.IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new net.minecraft.world.gen.ChunkGeneratorFlat(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), setting);
	}
	
	public int getMinimumSpawnHeight(World world) {
		return 4;
	}
	
	
	public double getHorizon(World world) {
		return 0.0D;
	}
	
	public double voidFadeMagnitude() {
		return 1.0D;
	}
	
	public boolean handleSlimeSpawnReduction(java.util.Random random, World world) {
		return random.nextInt(4) != 1;
	}
	
	
	
}
