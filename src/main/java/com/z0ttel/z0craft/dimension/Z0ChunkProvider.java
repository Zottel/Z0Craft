package com.z0ttel.z0craft.dimension;

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
import net.minecraft.world.chunk.IChunkGenerator;

import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.PlacementSettings;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;

import com.z0ttel.z0craft.Z0Craft;
import com.z0ttel.z0craft.blocks.Z0Blocks;


public class Z0ChunkProvider implements IChunkGenerator {
	private World world;
	private long seed;
	
	public Z0ChunkProvider(World worldIn, long seed) {
		Z0Craft.logger.info("Z0ChunkProvider/Z0ChunkProvider called");
		this.world = worldIn;
		this.seed = seed;
	}
	
	private void primeLayer(ChunkPrimer primer, int layer, Block block) {
		
		for (int j = 0; j < 16; ++j)
		{
			for (int k = 0; k < 16; ++k)
			{
				primer.setBlockState(j, layer, k, block.getDefaultState());
			}
		}
	}
	
	public Chunk provideChunk(int x, int z) {
		Z0Craft.logger.info("Z0ChunkProvider/provideChunk called for " + x + "/" + z);
		ChunkPrimer chunkprimer = new ChunkPrimer();
		
		Block block = Z0Craft.blocks.LIGHT;
		
		this.primeLayer(chunkprimer, 0, Blocks.BEDROCK);
		this.primeLayer(chunkprimer, 1, block);
		this.primeLayer(chunkprimer, 255, Blocks.BEDROCK);
		
		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
		Biome[] abiome = this.world.getBiomeProvider().getBiomes((Biome[])null, x * 16, z * 16, 16, 16);
		byte[] abyte = chunk.getBiomeArray();
		
		for (int l = 0; l < abyte.length; ++l)
		{
			abyte[l] = (byte)Biome.getIdForBiome(abiome[l]);
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	public void populate(int x, int z) {
		Z0Craft.logger.info("Z0ChunkProvider/populate called for " + x + "/" + z);
		// TODO: add structures here?
		
		PlacementSettings pls = new PlacementSettings();
		pls.setIgnoreEntities(false);
		switch(Math.abs(x) % 4) {
			case 0:
				pls.setRotation(Rotation.NONE);
				break;
			case 1:
				pls.setRotation(Rotation.CLOCKWISE_90);
				break;
			case 2:
				pls.setRotation(Rotation.CLOCKWISE_180);
				break;
			case 3:
				pls.setRotation(Rotation.COUNTERCLOCKWISE_90);
				break;
		}
		
		Z0Craft.logger.info("Z0ChunkProvider/placing stuhl with rotation " + pls.getRotation());
		
		Z0Craft.stuhl.addBlocksToWorld(this.world, new BlockPos(x * 16 + 8, 2, z * 16 + 8), pls);
	}
	
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		Z0Craft.logger.info("Z0ChunkProvider/generateStructures called for " + x + "/" + z);
		return false;
	}
	
	private static List<Biome.SpawnListEntry> NOCREATURES = new ArrayList<Biome.SpawnListEntry>(0);
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		//Z0Craft.logger.info("Z0ChunkProvider/getPossibleCreatures called for " + creatureType + " at " + pos);
		return NOCREATURES;
	}
	
	@Nullable
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
		return null;
	}
	
	public void recreateStructures(Chunk chunkIn, int x, int z){}
}
