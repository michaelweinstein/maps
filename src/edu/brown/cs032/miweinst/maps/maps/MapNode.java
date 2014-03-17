package edu.brown.cs032.miweinst.maps.maps;

import edu.brown.cs032.miweinst.maps.KDTree.KDComparable;
import edu.brown.cs032.miweinst.maps.KDTree.KDPoint;
import edu.brown.cs032.miweinst.maps.util.LatLng;

public class MapNode implements KDComparable {
	
	public final String id;
	public final LatLng loc;
	//list of Ways this node is source of
	public final String[] ways;
	
	public MapNode(String id, double lat, double lon, String waysList) {
		this.id = id;
		this.loc = new LatLng(lat, lon);
		this.ways = generateWays(waysList);
	}

	private static String[] generateWays(String waysList) {
		if (waysList == null) return new String[0];
		return waysList.split(",");
	}
	
	
	@Override
	public String toString() {
		String s = "MapNode. id: " + this.id + ", lat: " + this.loc.lat + ", long: " + this.loc.lng;
		return s;
	}
	
	/*
	 * this method is overkill since our LatLng is public,
	 * but it allows us to store MapNodes in the KDTree
	 */
	public KDPoint getPoint() {
		return new KDPoint(this.loc.lat,this.loc.lng,0.0);
	}
}
