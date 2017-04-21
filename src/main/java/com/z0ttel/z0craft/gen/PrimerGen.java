package com.z0ttel.z0craft.gen;

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
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.Rotation;

import com.z0ttel.z0craft.Z0Craft;
import com.z0ttel.z0craft.structures.Z0Structures;
import com.z0ttel.z0craft.blocks.Z0Blocks;
import com.z0ttel.z0craft.util.PerlinNoise;

public class PrimerGen {
	final public World world;
	
	final public long seed;
	final public int haloBlocks;
	final public int haloChunks;
	
	private PrimerGenPart[] parts;
	
	public PrimerGen(World worldIn, long seedIn, PrimerGenPart... partsIn) {
		world = worldIn;
		seed = seedIn;
		parts = partsIn;
		
		int haloBlocks = 0;
		for(PrimerGenPart part: parts) {
			haloBlocks += part.halo;
		}
		
		this.haloBlocks = haloBlocks;
		this.haloChunks = (int) Math.ceil((double)haloBlocks / 16);
	}
	
	public ChunkPrimer provideChunk(int x, int z) {
		ChunkPrimer[] primerField = new ChunkPrimer[(2 * haloChunks + 1) * (2 * haloChunks + 1)];
		for(int i = 0; i < primerField.length; i++) {
			primerField[i] = new ChunkPrimer();
		}
		
		// Fill with content.
		for(PrimerGenPart genPart: parts) {
			genPart.apply(x, z, haloChunks, primerField);
		}
		
		return primerField[primerField.length / 2];
	}
	
}
