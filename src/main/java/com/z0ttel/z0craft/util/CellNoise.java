package com.z0ttel.z0craft.util;

import java.util.Random;

import net.minecraft.util.math.ChunkPos;

public class CellNoise {
	public final long seed;
	// Size of chunks to generate one point in, a rough measure for feature size.
	public final int distance;
	
	public CellNoise(long seedIn, int distanceIn) {
		seed = seedIn;
		distance = distanceIn;
	}
	
	protected Point pointFor(int x, int z) {
		Random rand = new Z0Random(seed, new ChunkPos(x, z));
		return new Point((x * distance) + rand.nextInt(distance),
		                 (z * distance) + rand.nextInt(distance));
	}
	
	public double valueAt(int x, int z) {
		double closestDistance = 3 * distance;
		double secondClosestDistance = 3 * distance;
		for(int xIt = -2; xIt <= 2; xIt++) {
			for(int zIt = -2; zIt <= 2; zIt++) {
				Point curr = pointFor((x / distance) + xIt,
				                      (z / distance) + zIt);
				double xDist = Math.abs((double) x - curr.x);
				double zDist = Math.abs((double) z - curr.z);
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
		public final int x;
		public final int z;
		public Point(int xIn, int zIn) {
			x = xIn; z = zIn;
		}
	}
}
