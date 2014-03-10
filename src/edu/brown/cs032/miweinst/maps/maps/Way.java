package edu.brown.cs032.miweinst.maps.maps;

import edu.brown.cs032.miweinst.maps.util.Vec2f;

public class Way {
	
	public final String id;	
	//Vec2f latitude, longitude (in ID)
	public final Vec2f loc;
	//start node
	public final MapNode start;
	//end node
	public final MapNode end;
	//direction (in ID)
	public final float dir;
	//segment of street (in ID)
	public final int seg;
	
	public Way(String id, float lat, float lon, MapNode start, MapNode end, int seg, float dir) {
		this.id = id;
		this.loc = new Vec2f(lat, lon);
		this.start = start;
		this.end = end;
		this.seg = seg;
		this.dir = dir;
	}

}
