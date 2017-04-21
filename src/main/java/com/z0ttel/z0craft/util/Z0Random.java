package com.z0ttel.z0craft.util;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.util.math.ChunkPos;

public class Z0Random extends Random {
	private long seed;
	private ChunkPos chunk;
	
	public Z0Random(World worldIn, ChunkPos chunk) {
		super();
		
		this.seed = worldIn.getSeed();
		this.chunk = chunk;
		
		this.reset();
	}
	
	public Z0Random(long seed, ChunkPos chunk) {
		this.seed = seed;
		this.chunk = chunk;
		
		this.reset();
	}
	
	public void feed(long feed) {
		this.setSeed(this.nextLong()^feed);
	}
	
	public void feed(int feed) {
		this.feed((long) feed);
	}
	
	public void feed(String feed) {
		this.feed(feed.hashCode());
	}
	
	public void feed(ChunkPos feed) {
		this.feed(feed.hashCode());
	}
	
	public void reset() {
		this.setSeed(seed);
		feed(chunk);
	}
	
	public static class WorldChunk {
		public final int dimensionId;
		public final long seed;
		public final ChunkPos chunk;
		public final int hash;
		
		public WorldChunk(World worldIn, ChunkPos chunkIn) {
			this(worldIn.provider.getDimension(), worldIn.getSeed(), chunkIn);
			;
		}
		
		public WorldChunk(int dimensionIn, long seedIn, ChunkPos chunkIn) {
			this.dimensionId = dimensionIn;
			this.seed = seedIn; this.chunk = chunkIn;
			Z0Random rand = new Z0Random(seedIn, chunkIn);
			rand.feed(dimensionId);
			this.hash = rand.nextInt();
		}
		
		@Override
		public int hashCode() {
			return this.hash;
		}
	}
}
