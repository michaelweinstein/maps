package edu.brown.cs032.miweinst.maps.maps.path;

import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.util.LatLng;

public class MapsPath {

	/**
	 * Returns heuristic, as in distance between two nodes. Used for A*
	 * @param node
	 * @param dest
	 * @return
	 */
	public static float heuristic(MapNode node, MapNode dest) {
		
		//heuristic is euclidean distance between current node and destination
		LatLng curr = node.loc;
		LatLng dst = dest.loc;
		double heuristic = curr.dist2(dst);
		
////
		//PROBABLY NOT NECESSARY
		/* This breaks ties when multiple shortest paths are possible.
		 * Sets h(n)(heuristic) > g(n)(weight cost) by a small increment.
		 * Creates very slight bias towards tiles closer to
		 * the end node (h(n)), only having an effect when the cost
		 * f(n) = g(n) + h(n) is unchanging and therefore path is
		 * arbitrary between many shortest options. */	
//		heuristic *= (1+(1/1000));  
		
		// h(n) -- Heuristic value based on metric dist
		return (float) heuristic;	
	}

}
