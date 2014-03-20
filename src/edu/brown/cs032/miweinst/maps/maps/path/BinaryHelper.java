package edu.brown.cs032.miweinst.maps.maps.path;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import edu.brown.cs032.miweinst.maps.binarySearch.BinarySearch;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.maps.wrappers.WaysCacheWrapper;

public class BinaryHelper {
	private static BinarySearch ways, nodes, index;
	private static WaysCacheWrapper _waysCache;
	private static MapsFile _waysFile;
//////
	private static int _count;
	private static String _waysLastLineId;
	
	public static void setFiles(MapsFile waysFile, MapsFile nodesFile, MapsFile indexFile) 
			throws IOException, FileNotFoundException {
		ways = new BinarySearch(waysFile);
		nodes = new BinarySearch(nodesFile);
		index = new BinarySearch(indexFile);	
		_waysCache = new WaysCacheWrapper();
		
		_waysFile = waysFile;
		_waysLastLineId = waysFile.readLastLine().split("/t")[0];
		_count = 0;
	}
	
	/**
	 * Create MapNode from id.
	 */
	public static MapNode makeMapNode(String id) {		
		String[] cols = {"latitude", "longitude", "ways"};
		String[] arr = nodes.search(id, "id", cols);
		double lat = Double.parseDouble(arr[0]);
		double lng = Double.parseDouble(arr[1]);
		return new MapNode(id, lat, lng, arr[2]);
	}
	
	/**
	 * Returns arr of all Ways that the specified
	 * MapNode is the source of. 
	 * Uses nodes.tsv.
	 * @throws IOException 
	 */
	public static Way[] nodeToWayArr(MapNode node) {
		String[] cols = {"ways"};
		String[] waysCSV = node.ways;
		if (waysCSV.length == 0)
			return null;
		String[] waysIdArr = waysCSV[0].split(",");
		Way[] waysArr = new Way[waysIdArr.length];	
		for (int i=0; i<waysIdArr.length; i++) {
			String id = waysIdArr[i];
///////////////
			if (_waysCache.containsKey(id)) {
				waysArr[i] = _waysCache.get(id);
			}
			else {
				String start = node.id;
				String[] newCols = {"end"};
				//System.out.println(_count++);
				String[] end = ways.search(id, "id", newCols);			
//////////////
				
				
				
				waysArr[i] = new Way(id, start, end[0]);
				_waysCache.put(id, waysArr[i]);
				BinaryHelper.addWaysBlock(waysArr[i]);
				
/////////////SPEEDS UP WAYS FOR GUI BUT (MAYBE??) BREAKS DIJKSTRA
				//String id_opposite = id.substring(0, id.length() - 1);
				//if (id.endsWith("1"))
				//	id_opposite += "2";
				//else
				//	id_opposite += "1";
				//_waysCache.put(id_opposite, new Way(id_opposite,end[0], start));
			}
		}
		return waysArr;
	}
/////////THIS WILL BREAK UNLESS *ALL* WAYS HAVE A START AND END (do they??)
	/*
	 * takes advantage of predictive paging. Once we find a way,
	 * cache all ways that are within .01 degrees latitude and are
	 * farther down the page 
	 */
	private static void addWaysBlock(Way way) {
		int idIndex = _waysFile.getFieldIndex("id");
		int startIndex = _waysFile.getFieldIndex("start");
		int endIndex = _waysFile.getFieldIndex("end");
		
		String curr_id = way.id;
			
		while (curr_id.compareTo(_waysLastLineId) != 0 &&
			   curr_id.substring(0,7).compareTo(way.id.substring(0,7)) == 0)
		{
			try {
				String[] curr = _waysFile.readNextLine().split("\t");
				curr_id = curr[idIndex];
				String start = curr[startIndex];
				String end = curr[endIndex];
				if (_waysCache.containsKey(curr_id)) break;
				_waysCache.put(curr_id, new Way(curr_id, start, end));
				
				
			} catch (IOException e) { System.out.println("IOException in addWaysBlock()"); }	
		}
		
	}
	
	/**
	 * Returns the start and end MapNode of the
	 * specified Way, as an array of length 2, where
	 * arr[0] = srcNode and arr[1] = dstNode.
	 * Uses ways.tsv.
	 */
	public static MapNode[] wayToEndNodes(String id) {
		String[] cols = {"start", "end"};
		String[] startend = ways.search(id, "id", cols);
		MapNode[] endnodes = new MapNode[2];
		endnodes[0] = makeMapNode(startend[0]);
		endnodes[1] = makeMapNode(startend[1]);
		return endnodes;
	}
	
/////// 	MICHAEL WILL FILL THIS IN
	/**
	 * Returns MapNode at intersection of two streets, if one exists.
	 * Else returns null.
	 */
	public static MapNode findIntersection(String street1, String street2) {
		return null;
	}
}
