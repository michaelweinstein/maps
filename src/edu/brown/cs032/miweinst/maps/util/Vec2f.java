package edu.brown.cs032.miweinst.maps.util;

public final class Vec2f {
	
	public final float x;
	public final float y;
	
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/** Distance squared. Faster ops,
	 * use this for relative distances. */
	public final float dist2(Vec2f v) {
		return (float) (Math.pow(v.x-this.x, 2) + Math.pow(v.y-this.y, 2));
	}
	/** Distance formula.*/
	public final float dist(Vec2f v) {
		return (float) (Math.sqrt(dist2(v)));
	}
}
