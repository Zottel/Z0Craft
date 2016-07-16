package com.z0ttel.z0craft.structures;

import java.util.List;
import java.util.Map;

import net.minecraft.util.math.ChunkPos;

import net.minecraft.world.World;

public class StructureSpawnerOverworld extends StructureSpawner {
	public static Structure wolleteil = new Structure("z0craft:wolleteil");
	
	public StructureSpawnerOverworld(World worldIn, Map<Map.Entry<Integer, ChunkPos>, List<StructurePlacement>> cache) {
		super(worldIn, cache);
	}
	
	public List<StructurePlacement> structuresForChunk(World world, ChunkPos chunk) {
		return wolleteil.forChunk(world, chunk);
	}
}
