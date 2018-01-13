package net.z0ttel.z0craft.math.noise;
import java.util.Random;


import net.z0ttel.z0craft.math.Z0Random;

public class NoiseDistribution {
	private static double testScale = 10000;
	
	public static Result test(Noise2D noise, long times) {
		Random rand = new Random();
		double val = noise.at(rand.nextDouble() * testScale, rand.nextDouble() * testScale);
		double max = val;
		double min = val;
		for(long i = 0; i < times; i++) {
			val = noise.at(rand.nextDouble() * testScale, rand.nextDouble() * testScale);
			if(val < min) {
				min = val;
			} else if(val > max) {
				max = val;
			}
		}
		
		return new Result(min, max);
	}
	
	public static Result test(Noise3D noise, long times) {
		return new Result(0, 0);
	}
	
	public static class Result {
		public double min, max;
		public Result(double min, double max) {
			this.min = min; this.max = max;
		}
		public String toString() {
			return "DistributionResult(" + min + " < x < " + max + ")";
		}
	}
}
