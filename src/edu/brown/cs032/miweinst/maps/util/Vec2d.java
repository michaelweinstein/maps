package edu.brown.cs032.miweinst.maps.util;

public final class Vec2d {
	
	public final double x;
	public final double y;
	
	public Vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/** Distance squared. Faster ops,
	 * use this for relative distances. */
	public final double dist2(Vec2d v) {
		return Math.pow(v.x-this.x, 2) + Math.pow(v.y-this.y, 2);
	}
	/** Distance formula.*/
	public final double dist(Vec2d v) {
		return Math.sqrt(dist2(v));
	}

	@Override
	public final String toString() {
		return new String(this.x + ", " + this.y);
	}

}
