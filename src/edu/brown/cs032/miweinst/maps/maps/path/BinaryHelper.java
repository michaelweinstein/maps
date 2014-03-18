package edu.brown.cs032.miweinst.maps.maps.path;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.brown.cs032.miweinst.maps.binarySearch.BinarySearch;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.maps.Way;

public class BinaryHelper {
	private static BinarySearch ways;
	private static BinarySearch nodes;
	private static BinarySearch index;
	
	public static void setFiles(MapsFile waysFile, MapsFile nodesFile, MapsFile indexFile) 
			throws IOException, FileNotFoundException {
		ways = new BinarySearch(waysFile);
		nodes = new BinarySearch(nodesFile);
		index = new BinarySearch(indexFile);		
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
	 */
	public static Way[] nodeToWayArr(MapNode node) {
		String[] cols = {"ways"};
		String[] waysCSV = nodes.search(node.id, "id", cols);
		String[] waysIdArr = waysCSV[0].split(",");
		Way[] waysArr = new Way[waysIdArr.length];
		for (int i=0; i<waysIdArr.length; i++) {
			String id = waysIdArr[i];
			String start = node.id;
			String[] newCols = {"end"};
			String[] end = ways.search(id, "id", newCols);
			waysArr[i] = new Way(id, start, end[0]);
		}
		return waysArr;
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
