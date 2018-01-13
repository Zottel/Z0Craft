package net.z0ttel.z0craft.math.noise;

import java.util.Random;

import net.minecraft.util.math.ChunkPos;

import net.z0ttel.z0craft.Z0Craft;
import net.z0ttel.z0craft.math.Z0Random;
import net.z0ttel.z0craft.math.Interpolation;

public class PerlinNoise implements Noise2D {
	public final long seed;
	// Size of chunks to generate one point in, a rough measure for feature scaling.
	public final double scale;
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
	
	public PerlinNoise(long seedIn, double scaleIn) {
		seed = seedIn; scale = scaleIn;
	}
	
	public PerlinNoise() {
		seed = 0; scale = 1;
	}
	
	public PerlinNoise withSeed(long seed) {
		return new PerlinNoise(seed, this.scale);
	}
	public PerlinNoise withScale(double scale) {
		return new PerlinNoise(this.seed, scale);
	}
	
	
	protected Gradient gradientFor(int x, int z) {
		Random rand = new Z0Random(seed, new ChunkPos(x, z));
		return gradients[rand.nextInt(gradients.length)];
		/*
		return new Gradient(rand.nextDouble() * 2.0 - 1.0,
		                    rand.nextDouble() * 2.0 - 1.0);
		*/
	}
	
	protected double dotGridGradient(int ix, int iz, double x, double z) {
		Gradient grad = gradientFor(ix, iz);
		// distance vector
		double dx = x - (double)ix;
		double dz = z - (double)iz;
		return ((dx * grad.x) + (dz * grad.z));
	}
	
	public double at(double x, double z) {
		x /= scale; z /= scale;
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
		
		
		return Interpolation.smootherstep(ix0, ix1, sz);
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
