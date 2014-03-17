package edu.brown.cs032.miweinst.maps.maps;

import java.io.IOException;
import java.util.ArrayList;

import edu.brown.cs032.miweinst.maps.KDTree.KDTreeNode;

public class FileProcessor {

	private MapsFile _nodesFile, _indexFile, _waysFile;
	
	public FileProcessor(MapsFile n, MapsFile i, MapsFile w) {
		_nodesFile = n;
		_indexFile = i;
		_waysFile = w;
	}
	
	public MapNode[] nodesArrayForKDTree() throws IOException {
		ArrayList<MapNode> nodes = new ArrayList<MapNode>();
		
		//get necessary indices
		int idIndex = _nodesFile.getFieldIndex("id");
		int latIndex = _nodesFile.getFieldIndex("latitude");
		int longIndex = _nodesFile.getFieldIndex("longitude");
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
			float lat = Float.parseFloat(curr[latIndex]);
			float lon = Float.parseFloat(curr[longIndex]);
			String ways = null;
			if (curr.length - 1 > waysIndex) ways = curr[waysIndex];
			MapNode newNode = new MapNode(curr[idIndex],lat,lon,ways);
			nodes.add(newNode);
			curr = _nodesFile.readNextLine().split("\t");
			id = curr[idIndex];
		}
		return nodes.toArray(new MapNode[nodes.size()]);
	} 
	
	
}
