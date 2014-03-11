package edu.brown.cs032.miweinst.maps.graph;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public abstract class Path<N, E> {
	
	private Graph<N, E> _graph;
	private int _size;
	
	public Path(Graph<N, E> graph) {
		_graph = graph;
		_size = _graph.size();
	}

	/**
	 * This method calculates the heuristic for the specified node to
	 * the destination node to be used in the A* algorithm.
	 * 
	 * This method is to be defined in subclasses because the heuristic
	 * should be specific to whatever game is implementing this pathfinding.
	 * 
	 * @param node
	 * @param dest
	 * @return
	 */
	public abstract float heuristic(GraphNode<N> node, GraphNode<N> dest);
	
	/**
	 * This is a pathfinding method that implements the A* algorithm.
	 * 
	 * It uses a Priority Queue to set each node's prev pointer to the
	 * neighbor node that took the least cost, and then creates the path
	 * by starting at the dest and retracing each prev pointer until the
	 * source is reached, adding each node to an ArrayDeque (used solely as
	 * a non-synchronized version of a Stack), which is then returned.
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public ArrayDeque<GraphNode<N>> findPath(GraphNode<N> src, GraphNode<N> dest) {			
		//Non-synchronized implementation of LIFO stack operations 
		ArrayDeque<GraphNode<N>> path = new ArrayDeque<GraphNode<N>>();		
		//Custom comparator class compares the distances of two GraphNodes
		Comparator<GraphNode<N>> comparator = new DistanceComparator<N>();
		//PQ keeps order of nodes based on their getDist attributes
		PriorityQueue<GraphNode<N>> pq = new PriorityQueue<GraphNode<N>>(_size, comparator); 				
		if (_size > 1) {
			Iterator<GraphNode<N>> nodes = _graph.nodes();
			
			/*Initialization; Set distances to INFINITY (unless src node); previous to NULL*/
			while (nodes.hasNext()) {
				GraphNode<N> n = nodes.next();
				if (n == src) {
					n.setDist(0f);									
				}
				else {
					n.setDist(Float.POSITIVE_INFINITY);
				}
				n.setPrev(null);
				pq.add(n);
			}			
			
			/*Run A* Algorithm*/
			while (pq.peek() != dest) {
				GraphNode<N> curr = pq.remove();	//Get the min elt from PQ
				//For each neighbor of the current node
				Iterator<GraphNode<N>> neighbors = _graph.neighbors(curr);
				while (neighbors.hasNext()) {	
					//For each neighbor of current node
					GraphNode<N> n = neighbors.next();	
					//Only check neighbors visible to traversal
					if (n.isVisible()) {
						//Get cost of edge between nodes; sum of edge weight, metric distance heuristic
						float cost = _graph.getEdge(curr, n).getWeight() + this.heuristic(n, dest);							
						//If the new dist of neighbor is less than old dist
						if (n.getDist() > curr.getDist() + cost) {													
							//Change dist and order in pq
							//Remove node
							pq.remove(n);
							//Update it
							n.setDist(curr.getDist()+cost);
							n.setPrev(curr);
							//And re-insert to PQ
							pq.add(n);
						}
					}
				}
			}
			
			/*Compile Path; retrace path back from dst to src using prev pointers*/
			GraphNode<N> n = dest;
			//Until the src node is reached
			while (n.getPrev() != null) {
				path.push(n);	
				n = n.getPrev();
			}		
		}	
		return path;
	}
}
