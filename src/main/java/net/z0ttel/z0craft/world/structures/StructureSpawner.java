package net.z0ttel.z0craft.world.structures;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.AbstractMap.SimpleImmutableEntry;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.DimensionType;

import net.minecraft.util.math.ChunkPos;

import net.z0ttel.z0craft.Z0Craft;
import net.z0ttel.z0craft.math.Z0Random.WorldChunk;


public abstract class StructureSpawner {
	// Assuming structures will spread no more than one chunk in each direction.
	int RADIUS = 1;
	
	int dimensionId;
	long seed;
	Map<WorldChunk, List<StructureInstance>> structCache;
	
	public StructureSpawner(World worldIn, Map<WorldChunk, List<StructureInstance>> structCacheIn) {
		dimensionId = worldIn.provider.getDimension();
		seed = worldIn.getSeed();
		structCache = structCacheIn;
	}
	
	public abstract List<StructureInstance> structuresForChunk(World world, ChunkPos pos);
	
	public List<StructureInstance> structuresForChunkCached(World worldIn, ChunkPos pos) {
		WorldChunk key = new WorldChunk(worldIn, pos);
		if(!structCache.containsKey(key)) {
			List<StructureInstance> tmp = structuresForChunk(worldIn, pos);
			structCache.put(key, tmp);
			return tmp;
		} else {
			return structCache.get(key);
		}
	}
	
	public void fillChunk(World worldIn, ChunkPos chunk) {
		LinkedList<StructureInstance> toDraw = new LinkedList<StructureInstance>();
		// Aggregate structures from surrounding chunks.
		
		for(int x = chunk.x - RADIUS; x <= chunk.x + RADIUS; x++) {
			for(int z = chunk.z - RADIUS; z <= chunk.z + RADIUS; z++) {
				toDraw.addAll(structuresForChunkCached(worldIn, new ChunkPos(x, z)));
				//toDraw.addAll(structuresForChunk(worldIn, new ChunkPos(x, z)));
			}
		}
		
		for(StructureInstance struct: toDraw) {
			struct.fillChunk(worldIn, chunk);
		}
	}
	
}
