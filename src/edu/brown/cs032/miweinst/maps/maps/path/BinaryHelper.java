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
		String latString = nodes.search(id, "id", "latitude");
		String lngString = nodes.search(id, "id", "longitude");
		String wayslist = nodes.search(id, "id", "ways");
		double lat = Double.parseDouble(latString);
		double lng = Double.parseDouble(lngString);
		return new MapNode(id, lat, lng, wayslist);
	}
	
	/**
	 * Returns arr of all Ways that the specified
	 * MapNode is the source of. 
	 * Uses nodes.tsv.
	 */
	public static Way[] nodeToWayArr(MapNode node) {
		String waysCSV = nodes.search(node.id, "id", "ways");
		String[] waysIdArr = waysCSV.split(",");
		Way[] waysArr = new Way[waysIdArr.length];
		for (int i=0; i<waysIdArr.length; i++) {
			String id = waysIdArr[i];
			String start = node.id;
			String end = ways.search(id, "id", "end");
			waysArr[i] = new Way(id, start, end);
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
		String start = ways.search(id, "id", "start");
		String end = ways.search(id, "id", "end");
		MapNode[] endnodes = new MapNode[2];
		endnodes[0] = makeMapNode(start);
		endnodes[1] = makeMapNode(end);
		return endnodes;
	}
}
