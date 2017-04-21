package com.z0ttel.z0craft.structures;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;

import com.z0ttel.z0craft.util.Z0Random;
import com.z0ttel.z0craft.util.Z0Random.WorldChunk;


class StructurePlacement {
	public final int upperLimit, lowerLimit;
	protected int inX = 1, inZ = 1;
	protected long seed;
	
	protected Structure structure;
	
	public StructurePlacement(int num) {
		upperLimit = (lowerLimit = num);
	}
	
	public StructurePlacement(int upper, int lower) {
		upperLimit = upper; lowerLimit = lower;
	}
	
	// Number of Chunks in which this will be placed!
	public StructurePlacement in(int x, int z) {
		inX = x; inZ = z;
		return this;
	}
	
	public StructurePlacement seededWith(long seedIn) {
		seed = seedIn;
		return this;
	}
	
	public StructurePlacement of(Structure structureIn) {
		this.structure = structureIn;
		return this;
	}
	
	public List<StructureInstance> structuresForChunk(World world, ChunkPos chunk) {
		ChunkPos superChunk = new ChunkPos(chunk.chunkXPos / inX, chunk.chunkZPos / inZ);
		
		Z0Random rand = new Z0Random(world, superChunk);
		rand.feed(seed);
		
		int num = lowerLimit + rand.nextInt(upperLimit - lowerLimit);
		
		LinkedList<StructureInstance> structs = new LinkedList<StructureInstance>();
		
		for(int i = 0; i < num; i++) {
			int xChunk = rand.nextInt(inX), zChunk = rand.nextInt(inZ);
			int xOff = rand.nextInt(16), zOff = rand.nextInt(16);
			long sSeed = rand.nextLong();
			if(superChunk.chunkXPos + xChunk == chunk.chunkXPos &&
			   superChunk.chunkZPos + zChunk == chunk.chunkZPos) {
				structs.add(structure.place(sSeed, chunk.chunkXPos * 16 + xOff,
				                                   chunk.chunkZPos * 16 + zOff));
			}
		}
		
		return structs;
	}
}
