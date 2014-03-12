package edu.brown.cs032.miweinst.maps.maps;

import edu.brown.cs032.miweinst.maps.util.Vec2f;

public class MapNode {
	
	public final String id;
	public final Vec2f loc;
	//list of Ways this node is source of
	public final String[] ways;
	
	public MapNode(String id, float lat, float lon, String waysList) {
		this.id = id;
		this.loc = new Vec2f(lat, lon);
		this.ways = generateWays(waysList);
	}

	private static String[] generateWays(String waysList) {
		return waysList.split(",");
	}
	
	
}
