package com.z0ttel.z0craft.structures;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.AbstractMap.SimpleImmutableEntry;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.DimensionType;

import net.minecraft.util.math.ChunkPos;

import com.z0ttel.z0craft.Z0Craft;

public abstract class StructureSpawner {
	// Assuming structures will spread no more than one chunk in each direction.
	int RADIUS = 1;
	
	int dimensionId;
	long seed;
	Map<Map.Entry<Integer, ChunkPos>, List<StructurePlacement>> structCache;
	
	public StructureSpawner(World worldIn, Map<Map.Entry<Integer, ChunkPos>, List<StructurePlacement>> structCacheIn) {
		dimensionId = worldIn.provider.getDimension();
		seed = worldIn.getSeed();
		structCache = structCacheIn;
	}
	
	public abstract List<StructurePlacement> structuresForChunk(World world, ChunkPos pos);
	
	public List<StructurePlacement> structuresForChunkCached(World worldIn, ChunkPos pos) {
		Map.Entry<Integer, ChunkPos> key = new SimpleImmutableEntry<Integer, ChunkPos>(dimensionId, pos);
		if(!structCache.containsKey(key)) {
			List<StructurePlacement> tmp = structuresForChunk(worldIn, pos);
			structCache.put(key, tmp);
			return tmp;
		} else {
			return structCache.get(key);
		}
	}
	
	public void fillChunk(World worldIn, ChunkPos chunk) {
		LinkedList<StructurePlacement> toDraw = new LinkedList<StructurePlacement>();
		// Aggregate structures from surrounding chunks.
		
		for(int x = chunk.chunkXPos - RADIUS; x <= chunk.chunkXPos + RADIUS; x++) {
			for(int z = chunk.chunkZPos - RADIUS; z <= chunk.chunkZPos + RADIUS; z++) {
				toDraw.addAll(structuresForChunkCached(worldIn, new ChunkPos(x, z)));
				//toDraw.addAll(structuresForChunk(worldIn, new ChunkPos(x, z)));
			}
		}
		
		for(StructurePlacement struct: toDraw) {
			struct.fillChunk(worldIn, chunk);
		}
	}
	
}
