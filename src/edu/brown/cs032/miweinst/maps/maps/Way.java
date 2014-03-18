package edu.brown.cs032.miweinst.maps.maps;

import edu.brown.cs032.miweinst.maps.util.LatLng;


public class Way {
	
	public final String id;	
	//Vec2f latitude, longitude (in ID)
//	public final LatLng loc;
	//start node
	public final String start;
	//end node
	public final String end;
	//direction (in ID)
	public final int dir;
	//segment of street (in ID)
	public final int seg;
	
	public Way(String id, String start, String end) {
		this.id = id;
//		this.loc = new LatLng(lat, lon);
		this.start = start;
		this.end = end;
		this.seg = getSegFromId(id);
		this.dir = getDirFromId(id);
	}
	
	/*
	 * given a valid id of a way, determines if the way is
	 * within diff degrees of ll
	 */
	public static boolean isWithinLat(String identifier, LatLng ll, double diff) {
		String[] id_array = identifier.split("\\.");
		String lat_str = id_array[0].substring(3,5) + "." + id_array[0].substring(5,7);
		double lat_dbl = Double.parseDouble(lat_str);
		return (Math.abs(lat_dbl - ll.lat) <= diff);
	}
	
	public static boolean isWithinLng(String identifier, LatLng ll, double diff) {
		String[] id_array = identifier.split("\\.");
		String lng_str = id_array[1].substring(3,5) + "." + id_array[0].substring(5,7);
		double lng_dbl = Double.parseDouble(lng_str);
		return (Math.abs(lng_dbl - ll.lng) <= diff);
	}
	
	private static int getSegFromId(String id) {
		String[] id_array = id.split("\\.");
		return Integer.parseInt(id_array[3]);
	}
	private static int getDirFromId(String id) {
		String[] id_array = id.split("\\.");
		return Integer.parseInt(id_array[4]);
	}

}
