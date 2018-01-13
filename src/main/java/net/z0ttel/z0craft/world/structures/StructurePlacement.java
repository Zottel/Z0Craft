package net.z0ttel.z0craft.world.structures;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;

import net.z0ttel.z0craft.math.Z0Random;
import net.z0ttel.z0craft.math.Z0Random.WorldChunk;


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
		ChunkPos superChunk = new ChunkPos(chunk.x / inX, chunk.z / inZ);
		
		Z0Random rand = new Z0Random(world, superChunk);
		rand.feed(seed);
		
		int num = lowerLimit + rand.nextInt(upperLimit - lowerLimit);
		
		LinkedList<StructureInstance> structs = new LinkedList<StructureInstance>();
		
		for(int i = 0; i < num; i++) {
			int xChunk = rand.nextInt(inX), zChunk = rand.nextInt(inZ);
			int xOff = rand.nextInt(16), zOff = rand.nextInt(16);
			long sSeed = rand.nextLong();
			if(superChunk.x + xChunk == chunk.x &&
			   superChunk.z + zChunk == chunk.z) {
				structs.add(structure.place(sSeed, chunk.x * 16 + xOff,
				                                   chunk.z * 16 + zOff));
			}
		}
		
		return structs;
	}
}
