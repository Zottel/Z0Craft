package net.z0ttel.z0craft.world.primer;

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
import net.z0ttel.z0craft.world.structures.Z0Structures;

public abstract class PrimerGenPart {
	final protected World world;
	final protected long seed;
	
	final public int halo = 2;
	
	public PrimerGenPart(World worldIn, long seedIn) {
		world = worldIn;
		seed = seedIn;
	}
	
	public void primeLayer(ChunkPrimer primer, int layer, Block block) {
		primeLayer(primer, layer, block.getDefaultState());
	}
	
	public void primeLayer(ChunkPrimer primer, int layer, IBlockState blockstate) {
		for (int j = 0; j < 16; ++j) {
			for (int k = 0; k < 16; ++k) {
				primer.setBlockState(j, layer, k, blockstate);
			}
		}
	}
	
	public abstract void apply(int x, int z, int halo, ChunkPrimer[] primerField);
	
}
