package edu.brown.cs032.miweinst.maps.graph;

import java.util.Comparator;

public class DistanceComparator<N> implements Comparator<GraphNode<N>> {

	@Override
	public int compare(GraphNode<N> n1, GraphNode<N> n2) {				
		if (n1.getDist() < n2.getDist()) {
			return -1;
		}
		if (n1.getDist() > n2.getDist()) {
			return 1;
		}
		return 0;
	}

}
