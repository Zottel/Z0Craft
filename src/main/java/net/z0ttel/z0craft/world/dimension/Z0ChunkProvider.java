package net.z0ttel.z0craft.world.dimension;

import java.util.List;
import java.util.ArrayList;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import net.minecraft.init.Blocks;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.PlacementSettings;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.Rotation;

import net.z0ttel.z0craft.Z0Craft;
import net.z0ttel.z0craft.blocks.Z0Blocks;
import net.z0ttel.z0craft.math.noise.PerlinNoise;
import net.z0ttel.z0craft.world.primer.base.Limits;
import net.z0ttel.z0craft.world.primer.features.Roads;
import net.z0ttel.z0craft.world.primer.PrimerGen;
import net.z0ttel.z0craft.world.structures.Z0Structures;


public class Z0ChunkProvider implements IChunkGenerator {
	private World world;
	private long seed;
	private PrimerGen primerGen;
	
	public Z0ChunkProvider(World worldIn, long seedIn) {
		world = worldIn; seed = seedIn;
		
		this.primerGen = new PrimerGen(worldIn, seedIn,
			new Roads(world, seed),
			new Limits(world, seed));
	}
	
	public Chunk generateChunk(int x, int z) {
		Z0Craft.logger.info("Z0ChunkProvider/provideChunk called for " + x + "/" + z);
		
		// Get (mostly) filled ChunkPrimer from PrimerGen
		ChunkPrimer primer = primerGen.provideChunk(x, z);
		
		// Necessary misc stuff:
		Chunk chunk = new Chunk(this.world, primer, x, z);
		Biome[] abiome = this.world.getBiomeProvider().getBiomes((Biome[])null, x * 16, z * 16, 16, 16);
		byte[] abyte = chunk.getBiomeArray();
		for (int l = 0; l < abyte.length; ++l) {
			abyte[l] = (byte)Biome.getIdForBiome(abiome[l]);
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	public void populate(int x, int z) {
		Z0Craft.logger.info("Z0ChunkProvider/populate called for " + x + "/" + z);
		// Only called when surrounding (4) chunks have been loaded.
		//Z0Craft.structures.fillChunk(world, seed, new ChunkPos(x, z));
	}
	
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		//Z0Craft.logger.info("Z0ChunkProvider/generateStructures called for " + x + "/" + z);
		
		// Return value is used for marking the chunk as modified.
		return false;
	}
	
	private static List<Biome.SpawnListEntry> NOCREATURES = new ArrayList<Biome.SpawnListEntry>(0);
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		//Z0Craft.logger.info("Z0ChunkProvider/getPossibleCreatures called for " + creatureType + " at " + pos);
		return NOCREATURES;
	}
	
	@Nullable
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
		return null;
	}
	
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		Z0Craft.logger.info("Z0ChunkProvider/recreateStructures called for " + x + "/" + z);
	}
	
	@Nullable
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		return null;
	}
	
  public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}
	
}
