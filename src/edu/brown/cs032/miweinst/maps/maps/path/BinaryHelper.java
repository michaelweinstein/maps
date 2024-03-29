package edu.brown.cs032.miweinst.maps.maps.path;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

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
		String[] waysIdArr = node.ways;
		Way[] waysArr = new Way[waysIdArr.length];	
		for (int i=0; i<waysIdArr.length; i++) {
			String id = waysIdArr[i];
///////////////
			if (WaysCacheWrapper.containsKey(id)) {
				waysArr[i] = WaysCacheWrapper.get(id);
			}
			else {
				String start = node.id;
				String[] newCols = {"end"};
				//System.out.println(_count++);
				String[] end = ways.search(id, "id", newCols);			
//////////////

				waysArr[i] = new Way(id, start, end[0]);
				WaysCacheWrapper.put(id, waysArr[i]);
				BinaryHelper.addWaysBlock(waysArr[i]);
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
				if (idIndex < curr.length && startIndex < curr.length && endIndex < curr.length) {
					curr_id = curr[idIndex];
					String start = curr[startIndex];
					String end = curr[endIndex];
					if (WaysCacheWrapper.containsKey(curr_id)) break;
					WaysCacheWrapper.put(curr_id, new Way(curr_id, start, end));
				}
			} catch (IOException e) { System.out.println("IOException in addWaysBlock()"); }	
		}
		
	}
	
	/*
	 * returns intersection node given two street names
	 * returns null if such a node DNE 
	 */
	public static MapNode findIntersection(String name1, String name2) {
		String[] cols = { "nodes" };
		String[] way1_nodes = index.search(name1,"name", cols)[0].split(",");
		String[] way2_nodes = index.search(name2,"name", cols)[0].split(",");
		HashSet<String> way1_set = new HashSet<String>();
		for (String id: way1_nodes) {
			way1_set.add(id);
		}
		for (String id: way2_nodes) {
			if (way1_set.contains(id)) return makeMapNode(id);
		}
		return null;
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
	
}
