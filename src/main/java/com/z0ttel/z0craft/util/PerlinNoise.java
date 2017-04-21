package com.z0ttel.z0craft.util;

import java.util.Random;

import net.minecraft.util.math.ChunkPos;

import com.z0ttel.z0craft.Z0Craft;

public class PerlinNoise {
	public static double min, max;
	public final long seed;
	// Size of chunks to generate one point in, a rough measure for feature size.
	public final int size;
	public final ChunkPos pos;
	public static Gradient gradients[];
	{
		gradients = new Gradient[]{
			new Gradient(1,1,0),
			new Gradient(-1,1,0),
			new Gradient(1,-1,0),
			new Gradient(-1,-1,0),
			
			new Gradient(1,0,1),
			new Gradient(-1,0,1),
			new Gradient(0,1,1),
			new Gradient(0,-1,1),
			
			new Gradient(1,0,-1),
			new Gradient(-1,0,-1),
			new Gradient(0,1,-1),
			new Gradient(0,-1,-1),
		};
	}
	
	public PerlinNoise(long seedIn, int sizeIn, ChunkPos posIn) {
		seed = seedIn;
		size = sizeIn;
		pos = posIn;
		
		// TODO: instantiate surrounding gradients?
		
	}
	
	protected Gradient gradientFor(int x, int z) {
		Random rand = new Z0Random(seed, new ChunkPos(x, z));
		//return gradients[rand.nextInt(gradients.length)];
		return new Gradient(rand.nextDouble() * 2.0 - 1.0,
		                    rand.nextDouble() * 2.0 - 1.0);
	}
	
	protected double dotGridGradient(int ix, int iz, double x, double z) {
		Gradient grad = gradientFor(ix, iz);
		// distance vector
		double dx = x - (double)ix;
		double dz = z - (double)iz;
		return ((dx * grad.x) + (dz * grad.z));
	}
	
	public double valueAt(double x, double z) {
		x /= size; z /= size;
		// Grid coords.
		int x0 = (x > 0.0 ? (int)x : (int)x - 1);
		int x1 = x0 + 1;
		int z0 = (z > 0.0 ? (int)z : (int)z - 1);
		int z1 = z0 + 1;
		
		
		// Interpolation weights.
		double sx = x - (double) x0;
		double sz = z - (double) z0;
		
		double n0, n1, ix0, ix1;
		
		n0 = dotGridGradient(x0, z0, x, z);
		n1 = dotGridGradient(x1, z0, x, z);
		ix0 = Interpolation.smootherstep(n0, n1, sx);
		
		n0 = dotGridGradient(x0, z1, x, z);
		n1 = dotGridGradient(x1, z1, x, z);
		ix1 = Interpolation.smootherstep(n0, n1, sx);
		
		
		double result = Interpolation.smootherstep(ix0, ix1, sz);
		min = Math.min(min, result);
		max = Math.max(max, result);
		Z0Craft.logger.info("perlin(x={},z={}) = {} (min={}, max={})", x, z, result, min, max);
		//return Math.pow(result, 2) * Math.signum(result);
		return result;
	}
	
	protected final class Gradient {
		public final double x;
		public final double y;
		public final double z;
		
		public Gradient(double xIn, double yIn, double zIn) {
			x = xIn; y = yIn; z = zIn;
		}
		public Gradient(double xIn, double zIn) {
			x = xIn; y = 0; z = zIn;
		}
	}
}
