package edu.brown.cs032.miweinst.maps.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Node type, and Edge type
 * @param <N>
 * @param <E>
 */
public class Graph<N, E> {
	//List of all nodes in Graph
	private List<GraphNode<N>> _nodes;
	//List of all edges in Graph
	private List<GraphEdge<E>> _edges;
	//Maps Elt to the GraphNode that holds it
	private HashMap<N, GraphNode<N>> _elementMap;
	//Each node's incident edges
	private HashMap<GraphNode<N>, ArrayList<GraphEdge<E>>> _incidentEdgeMap;
	
	public Graph() {
		_nodes = new ArrayList<GraphNode<N>>();
		_edges = new ArrayList<GraphEdge<E>>();
		_elementMap = new HashMap<N, GraphNode<N>>();
		_incidentEdgeMap = new HashMap<GraphNode<N>, ArrayList<GraphEdge<E>>>();
	}
	
	/**
	 * Inserts a GraphNode into the graph. Doesn't
	 * create new GraphNode like the other insertNode.
	 * Returns GraphNode that was originally passed in. 
	 * Cannot insert duplicate node.
	 */
	public GraphNode<N> insertNode(GraphNode<N> node) {
		if (!_nodes.contains(node)) {
			_elementMap.put(node.getElement(), node);
			_incidentEdgeMap.put(node, new ArrayList<GraphEdge<E>>());
			_nodes.add(node);
			return node;
		}
		return null;
	}
	/**
	 * Decorate objDecorator for new data to new GraphNode itself.
	 * Decorate incEdgeDecorator for any new node with
	 * an empty list of incident GraphEdges.
	 * Returns newly created/added GraphNode.
	 * If node already exists, passes duplicate node
	 * into insertNode, which returns null.
	 */
	public GraphNode<N> insertNode(N data) {
		if (!_elementMap.containsKey(data)) {
			GraphNode<N> node = new GraphNode<N>(data);
			return insertNode(node);
		}
		else 
			return insertNode(_elementMap.get(data));
	}
	
	/**
	 * Make new GraphEdge with specified weight. Add to
	 * incident edge list for both connecting nodes a, b.
	 * Returns newly created/added GraphEdge. If either
	 * GraphNode is not actually contained in graph, 
	 * no edge is added and returns null.
	 */
	public GraphEdge<E> insertEdge(GraphNode<N> a, GraphNode<N> b, E data, float weight) {
		if (a != b) {
			//make sure GraphNodes are actually in graph
			if (_nodes.contains(a) && _nodes.contains(b)) {
				GraphEdge<E> edge = new GraphEdge<E>(data, weight);
				edge.setEndNodes(a, b);
				_incidentEdgeMap.get(a).add(edge);
				_incidentEdgeMap.get(b).add(edge);
				_edges.add(edge);
				return edge;
			}
		}
		return null;
	}
	/**
	 * Inserts an edge between GraphNode that holds specified elements. 
	 * If no GraphNodes exist that hold N a or N b, return null and don't
	 * add any edge to graph. If they exist, call other insertEdge method.
	 */
	public GraphEdge<E> insertEdge(N a, N b, E data, float weight) {
		if (_elementMap.containsKey(a) && _elementMap.containsKey(b)) 
			return insertEdge(_elementMap.get(a), _elementMap.get(b), data, weight);
		return null;
	}
	
	/**
	 * Removes one edge connecting GraphNodes a and b. If no GraphEdge
	 * exists between those GraphNodes, edge will be null and will
	 * return null. Should be only one edge between any two nodes,
	 * probably of lowest weight (insertEdgeKeepLowestWeight), so
	 * this methods should return only edge between a and b. But if
	 * there are multiple edges between a and b, this graph will
	 * only remove one of them. 
	 */
	public GraphEdge<E> removeEdge(GraphNode<N> a, GraphNode<N> b) {
		GraphEdge<E> edge = getEdge(a, b);
		if (edge == null)
			return null;
		//remove edge from List
		_edges.remove(edge);
		//remove edge from _incidentEdgeMap of both GraphNodes a, b
		if (_incidentEdgeMap.get(a).contains(edge)) 
			_incidentEdgeMap.get(a).remove(edge);
		 if (_incidentEdgeMap.get(b).contains(edge))
			_incidentEdgeMap.get(b).remove(edge);
		return edge;
	}
	/**
	 * Calls removeEdge, but takes in two N elements rather than
	 * actual GraphNode. If GraphNode exists for both elements, forwards
	 * the call to other removeEdge. If GraphNode does not exist for either
	 * element, edge is not removed, and returns null.
	 */
	public GraphEdge<E> removeEdge(N a, N b) {
		if (_elementMap.containsKey(a) && _elementMap.containsKey(b)) 
			return removeEdge(_elementMap.get(a), _elementMap.get(b));
		return null;
	}
	
	
	/**
	 * Inserts a GraphEdge between nodes a and b if no GraphEdge
	 * exists there yet. If a GraphEdge already exists, it keeps the Edge
	 * with the lowest weight and returns that edge.
	 */
	public GraphEdge<E> insertLowestWeightEdge(GraphNode<N> a, GraphNode<N> b, E data, float weight) {
		if (a == b)
			return null;
		GraphEdge<E> e = getEdge(a, b);
		//if edge already exists between a and b
		if (e != null) {
			//if old edge weight is lower, return old edge without inserting new one
			if (e.getWeight() < weight) 
				return e;
			//if new edge weight is lower, remove old and add new
			else {
				//remove old edge (higher weight)
				removeEdge(a, b);			
				//insert new edge (lower weight)
				e = insertEdge(a, b, data, weight);
				return e;
			}
		}
		else {
			//if no edge exists yet, just insertEdge (i.e. getEdge(a, b) == null)
			return insertEdge(a, b, data, weight);
		}
	}
	/**
	 * Same as other insertEdgeLowestWeight method, but takes in the elements
	 * held by two GraphNodes instead of the GraphNode objects themselves. If
	 * GraphNodes do not exist that hold BOTH N a and b, returns null.
	 */
	public GraphEdge<E> insertLowestWeightEdge(N a, N b, E data, float weight) {
		if (_elementMap.containsKey(a) && _elementMap.containsKey(b)) 
			return insertLowestWeightEdge(_elementMap.get(a), _elementMap.get(b), data, weight);
		return null;
	}
	
	/**
	 * Returns the node that contains the specified
	 * N element, if one exists. Uses HashMap for 
	 * quick lookup, mapped from element (key) to
	 * the GraphNode object (value).
	 */
	public GraphNode<N> getNode(N elt) {
		if (_elementMap.containsKey(elt))
			return _elementMap.get(elt);
		else return null;
	}
	
	/**
	 * Finds the GraphEdge that connects node a and 
	 * b, if one exists. Else returns null. Searches
	 * through the incidentEdgeMap of one and compares
	 * it against the incidentEdgeMap of the other.
	 * Cannot get edge connecting GraphNode to itself.
	 */
	public GraphEdge<E> getEdge(GraphNode<N> a, GraphNode<N> b) {
		if (a != b) {
			if (_incidentEdgeMap.containsKey(a) && _incidentEdgeMap.containsKey(b)) {
				ArrayList<GraphEdge<E>> aEdges = _incidentEdgeMap.get(a);
				ArrayList<GraphEdge<E>> bEdges = _incidentEdgeMap.get(b);
				for (GraphEdge<E> e: aEdges) 
					if (bEdges.contains(e)) 
						return e;
			}
		}
		//if no edge exists
		return null;
	}
	/**
	 * Finds GraphEdge that connects nodes containing 
	 * data a and b. Arguments are elements, not GraphNodes.
	 * Returns null if Node with data a or b does not exist.
	 */
	public GraphEdge<E> getEdge(N a, N b) {
		if (_elementMap.containsKey(a) && _elementMap.containsKey(b))
			return getEdge(_elementMap.get(a), _elementMap.get(b));
		return null;
	}
		
	/**
	 * Returns iterator over all nodes in graph.
	 */
	public Iterator<GraphNode<N>> nodes() {
		return _nodes.iterator();
	}
	/**
	 * Returns iterator over all edges in graph.
	 */
	public Iterator<GraphEdge<E>> edges() {
		return _edges.iterator();
	}
	
	/**
	 * Returns the GraphNode opposite from specified 
	 * GraphNode n on the specified GraphEdge edge.
	 */
	public GraphNode<N> opposite(GraphNode<N> n, GraphEdge<E> edge) {
		GraphNode<N> opposite = null;
		if (n != null && edge != null) {
			if (edge.getANode() != edge.getBNode()) {
				if (n == edge.getANode()) {
					opposite = edge.getBNode();
				}
				else if (n == edge.getBNode()) {
					opposite = edge.getANode();
				}
			}
		}
		return opposite;
	}
	
	/**
	 * Returns iterator of all neighbors with specified GraphNode,
	 * ie nodes opposite all incident edges of GraphNode n. 
	 * For each incident edge of node n, get the node opposite
	 * that edge using this.opposite(). Iterate through 
	 * incident edges using the _incidentEdgeMap, fast lookup times.
	 */
	public Iterator<GraphNode<N>> neighbors(GraphNode<N> n) {
		ArrayList<GraphNode<N>> neighbors = new ArrayList<GraphNode<N>>();
		if (_incidentEdgeMap.containsKey(n)) {
			//for each incident edge, get opposite GraphNode
			for (GraphEdge<E> edge: _incidentEdgeMap.get(n)) {
				GraphNode<N> neighbor = this.opposite(n, edge);
				neighbors.add(neighbor);
			}
		}
		return neighbors.iterator();
	}
	
	/**
	 * Returns number of nodes in graph.
	 */
	public int size() {
		return _nodes.size();
	}
	/**
	 * Returns number of edges in graph.
	 */
	public int sizeEdges() {
		return _edges.size();
	}
}
