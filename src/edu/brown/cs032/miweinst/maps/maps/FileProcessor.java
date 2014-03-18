package edu.brown.cs032.miweinst.maps.maps;

import java.io.IOException;
import java.util.ArrayList;

import edu.brown.cs032.miweinst.maps.KDTree.KDTreeNode;
import edu.brown.cs032.miweinst.maps.binarySearch.BinarySearch;
import edu.brown.cs032.miweinst.maps.util.LatLng;

public class FileProcessor {

	private MapsFile _nodesFile, _indexFile, _waysFile;
	
	public FileProcessor(MapsFile n, MapsFile i, MapsFile w) {
		_nodesFile = n;
		_indexFile = i;
		_waysFile = w;
	}
	
	/**
	 * reads through each line of the nodes file and creates
	 * an array of MapNodes holding every node in the array
	 */
	public MapNode[] nodesArrayForKDTree() throws IOException {
		ArrayList<MapNode> nodes = new ArrayList<MapNode>();
		
		//get necessary indices
		int idIndex = _nodesFile.getFieldIndex("id");
		int latIndex = _nodesFile.getFieldIndex("latitude");
		int lngIndex = _nodesFile.getFieldIndex("longitude");
		int waysIndex = _nodesFile.getFieldIndex("ways");
		
		//read the last line first for its id
		//so we know when we've reached it in our while loop
		String[] last_line = _nodesFile.readLastLine().split("\t");
		_nodesFile.readFirstLine();
		String curr[] = _nodesFile.readNextLine().split("\t");
		String id = curr[idIndex];
		//while we haven't reached the last line, read the next
		//line and create a node from its data
		while(id.compareTo(last_line[idIndex]) != 0) { 
			double lat = Double.parseDouble(curr[latIndex]);
			double lng = Double.parseDouble(curr[lngIndex]);
			String ways = null;
			if (curr.length - 1 > waysIndex) ways = curr[waysIndex];
			MapNode newNode = new MapNode(curr[idIndex],lat,lng,ways);
			nodes.add(newNode);
			curr = _nodesFile.readNextLine().split("\t");
			id = curr[idIndex];
		}
		return nodes.toArray(new MapNode[nodes.size()]);
	} 

	/*
	 * looks through ways file and finds all ways that are within
	 * constraint degrees from the input LatLng
	 * input 'll' is intended to be the center of the bounding box
	 * input 'constraint' is intended to be the length of the 
	 * diagonal of the bounding box, in degrees
	 */

	public MapNode[] getNodesForGUI(LatLng ll, double constraint) throws IOException {
		ArrayList<MapNode> nodes = new ArrayList<MapNode>();
		
		int idIndex = _nodesFile.getFieldIndex("id");
		int latIndex = _nodesFile.getFieldIndex("latitude");
		int lngIndex = _nodesFile.getFieldIndex("longitude");
		int waysIndex = _nodesFile.getFieldIndex("ways");
		
		//read the last line first for its id
		//so we know when we've reached it in our while loop
		String[] last_line = _nodesFile.readLastLine().split("\t");
		
		_nodesFile.readFirstLine();
		
		String curr[] = _nodesFile.readNextLine().split("\t");
		String id = curr[idIndex];
		double lat = Double.parseDouble(curr[latIndex]);
		double lng = Double.parseDouble(curr[lngIndex]);
		//while we haven't reached the last line, read next line
		//break when we see a node with latitude within the area we are loading
		while (id.compareTo(last_line[idIndex]) != 0) { 
			if (ll.isWithinRadius(new LatLng(lat,lng),constraint)) { //if latlng is within radius, make node and break
					nodes.add(new MapNode(id, lat, lng,curr[waysIndex]));
					break;
			}
			curr = _nodesFile.readNextLine().split("\t");
			id = curr[idIndex];
			lat = Double.parseDouble(curr[latIndex]);
			lng = Double.parseDouble(curr[lngIndex]);
		}
		//while we are within our bounding lat, iterate through and add
		//if we are also within our bounding lng
		curr = _nodesFile.readNextLine().split("\t");
		id = curr[idIndex];
		lat = Double.parseDouble(curr[latIndex]);
		lng = Double.parseDouble(curr[lngIndex]);
		//while we are within the constraint of the lat and we are not at the last line:
		while (ll.isWithinRadius(new LatLng(lat,lng),constraint) && id.compareTo(last_line[idIndex]) != 0) {
			lat = Double.parseDouble(curr[latIndex]);
			lng = Double.parseDouble(curr[lngIndex]);

			if (ll.isWithinRadius(new LatLng(lat,lng),constraint)) { //if lng is within constraint, create a node and add it
				nodes.add(new MapNode(id,lat,lng,curr[waysIndex]));
			}
			curr = _nodesFile.readNextLine().split("\t");
			id = curr[idIndex];
		}
		//handle last node separately
		lat = Double.parseDouble(last_line[latIndex]);
		lng = Double.parseDouble(last_line[lngIndex]);
		if (ll.isWithinRadius(new LatLng(lat,lng),constraint) && LatLng.isWithinLng(lng,ll,constraint)) {
			System.out.println(new MapNode(last_line[idIndex], lat, lng, last_line[waysIndex]).toString());
			nodes.add(new MapNode(last_line[idIndex], lat, lng, last_line[waysIndex]));
		}
		return nodes.toArray(new MapNode[nodes.size()]);
	}
	
}
