package com.gmail.xfrednet.worldgentest.math;

public class Vec2 {
	
	public int X;
	public int Y;
	
	public Vec2() {
		this.X = 0;
		this.Y = 0;
	}
	public Vec2(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public double distance(Vec2 other) {
		return Math.sqrt(distanceSqr(other));
	}
	
	public int distanceSqr(Vec2 other) {
		int w = this.X - other.X;
		int h = this.Y - other.Y;
		
		return w * w + h * h;
	}
	
	
}
