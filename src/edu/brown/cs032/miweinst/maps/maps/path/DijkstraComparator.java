package edu.brown.cs032.miweinst.maps.maps.path;

import java.util.Comparator;

import edu.brown.cs032.miweinst.maps.graph.GraphNode;
import edu.brown.cs032.miweinst.maps.maps.MapNode;

public class DijkstraComparator implements Comparator<GraphNode<MapNode>> {

	/**
	 * Compares GraphNodes (holding MapNodes for maps) based on distance 
	 * decoration. Used in PriorityQueue for Dijkstra (or A*) 
	 */
	@Override
	public int compare(GraphNode<MapNode> arg0, GraphNode<MapNode> arg1) {
		if (arg0.getDist() > arg1.getDist()) 
			return 1;
		else if (arg0.getDist() < arg1.getDist()) 
			return -1;
		else 
			return 0;
	}
}
