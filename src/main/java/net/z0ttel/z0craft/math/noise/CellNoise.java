package net.z0ttel.z0craft.math.noise;

import java.util.Random;

import net.minecraft.util.math.ChunkPos;

import net.z0ttel.z0craft.math.Z0Random;

public class CellNoise implements Noise2D {
	public final long seed;
	// Size of chunks to generate one point in, a rough measure for feature size.
	public final double distance;
	
	public CellNoise(long seedIn, double distanceIn) {
		seed = seedIn;
		distance = distanceIn;
	}
	
	protected Point pointFor(double x, double z) {
		Random rand = new Z0Random(seed, new ChunkPos((int)x, (int)z));
		return new Point((x * distance) + (distance * rand.nextDouble()),
		                 (z * distance) + (distance * rand.nextDouble()));
	}
	
	public double at(double x, double z) {
		double closestDistance = 3 * distance;
		double secondClosestDistance = 3 * distance;
		for(int xIt = -2; xIt <= 2; xIt++) {
			for(int zIt = -2; zIt <= 2; zIt++) {
				Point curr = pointFor((x / distance) + (double)xIt,
				                      (z / distance) + (double)zIt);
				double xDist = Math.abs( x - curr.x);
				double zDist = Math.abs( z - curr.z);
				double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));
				if(dist < closestDistance) {
					secondClosestDistance = closestDistance;
					closestDistance = dist;
				} else if(dist < secondClosestDistance) {
					secondClosestDistance = dist;
				}
			}
		}
		
		// _Should_ return value between 0 and 1
		double distanceSum = closestDistance + secondClosestDistance;
		double largestDistanceSum = Math.sqrt(4.5 * Math.pow(distance, 2));
		return (2 * (distanceSum / largestDistanceSum)) - 1;
	}
	
	protected class Point {
		public final double x;
		public final double z;
		public Point(double xIn, double zIn) {
			x = xIn; z = zIn;
		}
	}
}
