package edu.brown.cs032.miweinst.maps.KDTree;

import edu.brown.cs032.miweinst.maps.util.LatLng;


/*
 * the readme contains a description of how the neighbor
 * finding algorithm works
 * this class would work for any number of dimensions k
 */

public class NeighborSearch {

	private KDTreeNode[] _neighbors;
	private double _radius;
	private boolean _byRadius, _byName;
	private KDPoint _point;
	
	
	public NeighborSearch(KDPoint p, int num_neighbors, double r, boolean byRadius, boolean byName) {
		_point = p;
		_neighbors = new KDTreeNode[num_neighbors];
		_byRadius = byRadius;
		_byName = byName;
		_radius = r;
		
		if (!_byRadius) _radius = Double.MAX_VALUE;
	}
	
	/*
	 * Wrapper constructor if we want the closest neighbor to the given LatLng
	 */
	public NeighborSearch(LatLng ll) {
		_point = new KDPoint(ll.lat,ll.lng,0.0);
		_neighbors = new KDTreeNode[1];
		_byRadius = false;
		_byName = false;
		_radius = Double.MAX_VALUE;
	}
	
	public KDTreeNode[] nearestNeighbors(KDTreeNode n) {
		
		int dim = n.getDimension();

		if (n.isLeaf() && _point.dist(n.getComparable().getPoint()) < _radius) {
			if (!_byName) {
				this.insertIntoNeighbors(n);
			} 
			else if (_point.dist(n.getComparable().getPoint()) > 0.0) {
				this.insertIntoNeighbors(n);
			}
		} //end if
		else if (!n.isLeaf()) {
			
			MyComparator comparator = new MyComparator(dim);
			int c = comparator.compare(_point, n.getPoint());
			
			if (c < 0) {
				this.nearestNeighbors(n.getLeftChild());
			} 
			else if (c >= 0) {
				this.nearestNeighbors(n.getRightChild());
			} 
			
			if ((_point.getCoordinateInDimension(dim) - n.getPoint().getCoordinateInDimension(dim)) < _radius) {
				if (!n.getRightChild().hasBeenSearched()) {
					this.nearestNeighbors(n.getRightChild());
				} 
				else if (!n.getLeftChild().hasBeenSearched()){
					this.nearestNeighbors(n.getLeftChild());
				}
			}
			n.getLeftChild().setSearched(false);
			n.getRightChild().setSearched(false);
		} //end else if
		n.setSearched(true);
		return _neighbors;
	}
	
	private void insertIntoNeighbors(KDTreeNode neighbor) {
		
		int index = 0;
		boolean breakOut = false;
		
		while (index < _neighbors.length && !breakOut) {
			
			double neighborDistance = _point.dist(neighbor.getComparable().getPoint());
			
			if (_neighbors[index] == null) {
				_neighbors[index] = neighbor;
				breakOut = true;
			}
			else if (neighborDistance < _point.dist(_neighbors[index].getComparable().getPoint())) {
				KDTreeNode buffer = _neighbors[index];
				_neighbors[index] = neighbor;
				neighbor = buffer;
			}
			index++;
		} //end while
		if (_neighbors[_neighbors.length - 1] != null && !_byRadius) { 
			_radius = _point.dist(_neighbors[_neighbors.length - 1].getComparable().getPoint());
		}
		
	} //end insertIntoNeighbors()
	
} //end NeighborSearch class
