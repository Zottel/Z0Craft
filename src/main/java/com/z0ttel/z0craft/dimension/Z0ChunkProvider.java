package com.z0ttel.z0craft.dimension;

import java.util.List;
import java.util.ArrayList;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;

import com.z0ttel.z0craft.Z0Craft;
import com.z0ttel.z0craft.CommonProxy;


public class Z0ChunkProvider implements IChunkGenerator {
	private World worldIn;
	private long seed;
	
	public Z0ChunkProvider(World worldIn, long seed) {
		this.worldIn = worldIn;
		this.seed = seed;
	}
	
	
	public Chunk provideChunk(int x, int z) {
		ChunkPrimer chunkprimer = new ChunkPrimer();
		
		Block block = Z0Craft.proxy.blocks[0];
		
		for (int j = 0; j < 16; ++j)
		{
			for (int k = 0; k < 16; ++k)
			{
				chunkprimer.setBlockState(j, 0, k, block.getDefaultState());
			}
		}
		
		Chunk chunk = new Chunk(this.worldIn, chunkprimer, x, z);
		Biome[] abiome = this.worldIn.getBiomeProvider().getBiomes((Biome[])null, x * 16, z * 16, 16, 16);
		byte[] abyte = chunk.getBiomeArray();
		
		for (int l = 0; l < abyte.length; ++l)
		{
			abyte[l] = (byte)Biome.getIdForBiome(abiome[l]);
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	public void populate(int x, int z) {
		// TODO: add structures here?
	}
	
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}
	
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return new ArrayList<Biome.SpawnListEntry>(0);
	}
	
	@Nullable
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
		return null;
	}
	
	public void recreateStructures(Chunk chunkIn, int x, int z){}
}
