package edu.brown.cs032.miweinst.maps.graph;

public class GraphNode<N> {
	
	private N _element;
	private GraphNode<N> _prev;
	private float _dist;
	private boolean _isExplored;
	private boolean _isVisible;
		
	public GraphNode(N element) {
		_element = element;
		_dist = Float.POSITIVE_INFINITY;
		_prev = null;
		_isExplored = false;
		_isVisible = true;
	}
	
	//ACCESSORS
	public N getElement() {
		return _element;
	}		
	public GraphNode<N> getPrev(){
		return _prev;
	}	
	public float getDist() {
		return _dist;
	}		
	public boolean isExplored() {
		return _isExplored;
	}
	public boolean isVisible() {
		return _isVisible;
	}
	public boolean hasPrev() {
		return _prev != null;
	}
	
	//MUTATORS
	public void setElement(N elt) {
		_element = elt;
	}
	public void setDist(float dist) {
		_dist = dist;
	}	
	public void setPrev(GraphNode<N> prev){
		_prev = prev;
	}
	public void setExplored(boolean exp) {
		_isExplored = exp;
	}
	public void setVisible(boolean vis) {
		_isVisible = vis;
	}

}
