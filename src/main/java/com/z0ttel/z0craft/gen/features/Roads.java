package com.z0ttel.z0craft.gen.features;

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
import com.z0ttel.z0craft.gen.PrimerGenPart;

public class Roads extends PrimerGenPart {
	final public int halo = 2;

	public Roads(World worldIn, long seedIn) {
		super(worldIn, seedIn);
	}
	
	public void apply(int x, int z, int halo, ChunkPrimer[] primerField)
	{
		// * Find Points to connect in surrounding area.
		// * Check if straigt lines intersect the bounding box.
		// * Draw intersecting part of line.
		// * Apply horizontal cellular automata?
		// * Apply vertical cellular automata
	}
	
}
