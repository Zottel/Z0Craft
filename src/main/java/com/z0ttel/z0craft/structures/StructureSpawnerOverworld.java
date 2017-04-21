package com.z0ttel.z0craft.structures;

import java.util.List;
import java.util.Map;

import net.minecraft.util.math.ChunkPos;

import net.minecraft.world.World;

import com.z0ttel.z0craft.util.Z0Random.WorldChunk;


public class StructureSpawnerOverworld extends StructureSpawner {
	public static Structure wolleteil = new Structure("z0craft:wolleteil");
	
	
	
	public StructureSpawnerOverworld(World worldIn, Map<WorldChunk, List<StructureInstance>> cache) {
		super(worldIn, cache);
	}
	
	public List<StructureInstance> structuresForChunk(World world, ChunkPos chunk) {
		return wolleteil.forChunk(world, chunk);
	}
}
