package edu.brown.cs032.miweinst.maps.maps;

import java.util.List;

import edu.brown.cs032.miweinst.maps.util.Vec2f;

public class MapNode {
	
	public final String id;
	public final Vec2f loc;
	//list of Ways this node is source of
	public final List<Way> ways;
	
	public MapNode(String id, float lat, float lon, List<Way> ways) {
		this.id = id;
		this.loc = new Vec2f(lat, lon);
		this.ways = ways;
	}
}
