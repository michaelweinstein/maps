package edu.brown.cs032.miweinst.maps.threading;

import java.util.ArrayDeque;

import edu.brown.cs032.miweinst.maps.graph.Graph;
import edu.brown.cs032.miweinst.maps.graph.GraphNode;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.maps.path.PathFinder;

public class PathFindingThread extends Thread {

	private static Graph<MapNode, Way> _graph;
	private static MapNode _start, _end;
	private static ArrayDeque<GraphNode<MapNode>> _deque;
	
	public PathFindingThread() {
		_deque = null;
	}

	public static void setInfo(Graph<MapNode, Way> g, MapNode n1, MapNode n2) {
		_graph = g;
		_start = n1;
		_end = n2;
	}
	
	public static ArrayDeque<GraphNode<MapNode>> getPath() {
		return _deque;
	}
	
	
	@Override
	public void run() {
		_deque = PathFinder.buildGraphFromNames(_graph, _start, _end);
	}
	
	
}
