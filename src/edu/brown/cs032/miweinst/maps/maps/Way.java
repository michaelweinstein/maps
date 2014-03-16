package edu.brown.cs032.miweinst.maps.maps;


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
	
	
	private static int getSegFromId(String id) {
		String[] id_array = id.split(".");
		return Integer.parseInt(id_array[3]);
	}
	private static int getDirFromId(String id) {
		String[] id_array = id.split(".");
		return Integer.parseInt(id_array[4]);
	}

}
