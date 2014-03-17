package edu.brown.cs032.miweinst.maps.maps.path;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;

import edu.brown.cs032.miweinst.maps.graph.Graph;
import edu.brown.cs032.miweinst.maps.graph.GraphEdge;
import edu.brown.cs032.miweinst.maps.graph.GraphNode;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.util.LatLng;

public class PathFinder {
	
	/**
	 * Takes in a graph that stores GraphNode<MapNode> and GraphEdge<Way>. Takes in two
	 * instances of MapNode, a start node and end node, and calls buildGraph to find
	 * the path between them.
	 */
	public static ArrayDeque<GraphNode<MapNode>> buildGraphFromNames(Graph<MapNode, Way> g, MapNode node1, MapNode node2) {
//		try {		
			//initiate empty ArrayDeque to populate with path if there is one
			ArrayDeque<GraphNode<MapNode>> path = new ArrayDeque<GraphNode<MapNode>>();
			Comparator<GraphNode<MapNode>> comp = new DijkstraComparator();
			PriorityQueue<GraphNode<MapNode>> pq = new PriorityQueue<GraphNode<MapNode>>(40, comp);		
			buildGraph(g, path, pq, node1, node2);
			return path;
//		} catch (IOException e) {
//			return null;
//		}
	}
	
	
	/**
	 * Creates graph with GraphNode storing actor ids (NOT names)
	 * Dynamically keeps track of lowest cost path (using node dist fields
	 * and previous node pointers to track path). Combination of recursively
	 * building a Graph and running Dijkstra's. Should take in an empty Graph g,
	 * which is built and passed into the method recursively. 
	 * Builds until the current actor is equal to the target actor.
	 * If actors cannot be found, returns null.
	 * If no path between actors can be found, returns an ArrayDeque path containing only the srcNode, size == 1
	 */
	private static void buildGraph(Graph<MapNode, Way> g, ArrayDeque<GraphNode<MapNode>> path,
			PriorityQueue<GraphNode<MapNode>> pq, MapNode srcNode, MapNode dstNode) {
//		try {			
			GraphNode<MapNode> currNode;
			//(FIRST CALL ONLY) set first node's distance to 0, add to PQ
			if (g.size() == 0)  {
				//make node for first actor (srcId)
				currNode = g.insertNode(srcNode);					
				currNode.setDist(0);
				pq.add(currNode);
			}			
			//if PQ empty, end algorithm
			if (pq.isEmpty()) {
				//if path is still empty, no path was found
				if (path.isEmpty())
					path.add(g.getNode(srcNode));	
			}
			//still looking for path
			else {		
				//remove lowest-distance node
				currNode = pq.poll();
////				
				System.out.println(currNode.getElement().id + " currNode.isExplored: " + currNode.isExplored());
				
				//get id of curr node
				MapNode currMapNode = currNode.getElement();

				/*Fully explore currNode*/
				//find all ways starting at this node
				Way[] ways = BinaryHelper.nodeToWayArr(currMapNode);				
				//for each film (id) actor1 stars in
				films: 
				for (Way way: ways) {	
					
					//get list of actors in film
					MapNode[] endNodes = BinaryHelper.wayToEndNodes(way.id);	
					
					//for each actor in film, other than actor1
//					for (MapNode id: endNodes) {	
					
					//endNodes[0] == currNode
					//for endnode of each way
					MapNode endMapNode = endNodes[1];
					
					//explore if node is not yet created or unexplored
					boolean explore = false;
					//get GraphNode containing endMapNode, if one exists
					GraphNode<MapNode> node = g.getNode(endMapNode);
					if (node == null) 
						explore = true;
					else if (!node.isExplored()) 
						explore = true;	
					
					//if endMapNode != currMapNode and endMapNode not yet explored
					if (!endMapNode.equals(currMapNode) && explore) {	
						
//						String currName = BinarySearchUtil.idToActor(currMapNode);
//						String name = BinarySearchUtil.idToActor(endMapNode);						
						//if actor is related to actor1 (pass in names, not ids)
//						if (isRelated(currName, name)) {		
						
						/*Get node or create new node */							
						//get or create node with endMapNode
						if (node == null)
							//create new node in graph with connected endMapNode
							node = g.insertNode(endMapNode);

						/*Create new edge if lower weight than any current edge*/ 							
						//insertLowestWeightEdge from actor1 to actor; if Edge already exists, keep edge with lowest weight
						GraphEdge<Way> newEdge = g.insertLowestWeightEdge(currNode, node, way, calculateEdgeWeight(currNode, node));

						/*Dijkstra's: update GraphNode's dist and prev pointers (dist initialized to INFINITY)	*/						
						//new dist option is startNode + edgeweight; compare against curr dist
						float dist = currNode.getDist() + newEdge.getWeight();		
						//if dist has not been set, getDist() == INFINITY (set in GraphNode() constructor)
						if (dist < node.getDist()) {
							//update dist and prev pointers if new dist < old dist
							node.setDist(dist);
							node.setPrev(currNode);
						}
						pq.add(node);
						
						/* If target actor has been reached! */
						if (endMapNode.equals(dstNode)) {		
							//populate path using prev pointers
							GraphNode<MapNode> n = node;
							path.push(n);
							while (n.getPrev() != null) {
								 path.push(n.getPrev());
								 n = n.getPrev();
							}
							//clear PQ to signal end of algorithm
							pq.clear();
							break films;
						}
//						}
					}
//					}
				}					
/////
				System.out.println("SET EXPLORED: " + pq.size());
				
				/* Recursive Step */
				currNode.setExplored(true);
				//recursive step from new actor to same actor2					
				buildGraph(g, path, pq, srcNode, dstNode);
			}
/*		} catch (IOException e) {
			System.out.println("ERROR: IOException in GraphBuilder.buildGraph");
			path = null;
		}*/
	}
	
	/**
	 * Calculates the weight of an edge GraphEdge<Way> by taking in the two connecting nodes. 
	 * The edge weight is the distance between startNode and endNode.
	 * Returns a float because the edge weight of a GraphEdge is stored as float.
	 */
	private static float calculateEdgeWeight(GraphNode<MapNode> startNode, GraphNode<MapNode> endNode) {
		LatLng src = startNode.getElement().loc;
		LatLng dst = endNode.getElement().loc;
		return (float) src.dist(dst);
	}
}
