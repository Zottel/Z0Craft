package com.z0ttel.z0craft.util;


class Smoothing {
	private static double clamp(double x, double x0, double x1) {
		if(x > x1)
			return x1;
		else if(x < x0)
			return x0;
		else
			return x;
	}
	
	public static double linear(double x) {
		return x;
	}
	
	// https://en.wikipedia.org/wiki/Smoothstep
	public static double smoothstep(double x) {
		double edge0 = 0, edge1 = 1;
		// Scale, bias and saturate x to 0..1 range
		x = clamp((x - edge0)/(edge1 - edge0), 0.0, 1.0);
		// Evaluate polynomial
		return x*x*(3 - 2*x);
	}
	
	// https://en.wikipedia.org/wiki/Smoothstep
	public static double smootherstep(double x) {
		double edge0 = 0, edge1 = 1;
		// Scale, and clamp x to 0..1 range
		x = clamp((x - edge0)/(edge1 - edge0), 0.0, 1.0);
		// Evaluate polynomial
		return x*x*x*(x*(x*6 - 15) + 10);
	}
	
	public static double logistics(double x) {
		double k = 1;
		double x0 = 0.5;
		return (double)1 / ((double)1 + Math.exp((double)-1 * k * (x - x0)));
	}
}
