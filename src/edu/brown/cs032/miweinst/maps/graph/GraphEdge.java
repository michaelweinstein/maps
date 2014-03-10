package edu.brown.cs032.miweinst.maps.graph;

///// GraphEdge generic has no relation to GraphNode generic, which can be anything
@SuppressWarnings({"rawtypes"})

public class GraphEdge<E> {
	
	private E _element;
	private float _weight;	
	private GraphNode _aNode;
	private GraphNode _bNode;
	
	public GraphEdge(E elt, float weight) {		
		_element = elt;
		_weight = weight;		
	}
	
	public GraphNode getANode() {
		return _aNode;
	}
	public GraphNode getBNode() {
		return _bNode;
	}
	public GraphNode[] getEndNodes() {
		GraphNode[] nodes = new GraphNode[2];
		nodes[0] = _aNode;
		nodes[1] = _bNode;
		return nodes;
	}
	public void setEndNodes(GraphNode v, GraphNode u) {
		_aNode = v;
		_bNode = u;
	}
	
	public E getElement() {
		return _element;
	}
	public void setElement(E data) {
		_element = data;
	}
	
	public float getWeight() {
		return _weight;
	}
	public void setWeight(float elt) {
		_weight = elt;
	}
}
