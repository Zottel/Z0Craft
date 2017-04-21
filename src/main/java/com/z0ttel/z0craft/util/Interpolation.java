package com.z0ttel.z0craft.util;


class Interpolation {
	public static double linear(double v1, double v2, double w) {
		return ((1.0 - w) * v1) + (w * v2);
	}
	
	public static double smoothstep(double v1, double v2, double w) {
		return linear(v1, v2, Smoothing.smoothstep(w));
	}
	
	public static double smootherstep(double v1, double v2, double w) {
		return linear(v1, v2, Smoothing.smootherstep(w));
	}
	
	public static double logistics(double v1, double v2, double w) {
		return linear(v1, v2, Smoothing.logistics(w));
	}
}
