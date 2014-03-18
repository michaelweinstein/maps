package edu.brown.cs032.miweinst.maps.KDTree;

/*
 * node holds a comparable (e.g. a Star) and has
 * most fields you would expect. The split is a point
 * that helps decide which subtree the comparable goes in
 * getDimension() would mod a different number if the number
 * of dimensions k changed. In retrospect, I should have abstracted
 * by taking the mod of a public final variable (in this case 3)
 */
public class KDTreeNode {
	
	private boolean _isLeft, _isLeaf, _isRoot, _searched;
	private KDTreeNode _right, _left;
	private KDComparable _comparable;
	private int _depth;
	private KDPoint _split;
	
	public KDTreeNode() {
		_right = null;
		_left = null;
		_isLeaf = false;
		_isLeft = false;
		_isRoot = false;
		_searched = false;
		_comparable = null;
		_depth = 0;
		_split = new KDPoint(0.0,0.0,0.0);
	}
	
	public boolean hasLeft() { return (_left != null); }
	public boolean hasRight() { return (_right != null); }
	
	public KDTreeNode getRightChild() { return _right; }
	public KDTreeNode getLeftChild() { return _left; }
	
	public void setRightChild(KDTreeNode n) { _right = n; }
	public void setLeftChild(KDTreeNode n) { _left = n; }
	
	public boolean isLeaf() { return _isLeaf; }
	public void setLeaf(boolean b) { _isLeaf = b; }
	
	public boolean isLeft() { return _isLeft; }
	public void setLeft(boolean b) { _isLeft = b; }
	
	public boolean hasComparable() { return (_comparable != null); }
	
	public KDComparable getComparable() { return _comparable; }
	public void setComparable(KDComparable c) { _comparable = c; }
	
	public int getDepth() { return _depth; }
	public void setDepth(int d) { _depth = d; }
	
	public boolean isRoot() { return _isRoot; }
	public void setRoot(boolean b) { _isRoot = b; }
	
	public boolean hasBeenSearched() { return _searched; }
	public void setSearched(boolean b) { _searched = b; }

	public KDPoint getPoint() { return _split; }
	
	public int getDimension() { return ((_depth % 2) + 1); }
	
	/*
	 * set the split based on another node
	 * intended input node is the instance's parent node 
	 */
	public void inheritSplit(KDTreeNode n) {
		double x = n.getPoint().getX();
		double y = n.getPoint().getY();
		double z = n.getPoint().getZ();
		_split = new KDPoint(x,y,z);
	}
	
	public void setSplit(double d, int dim) {
		if (dim == 1) _split.setX(d);
		if (dim == 2) _split.setY(d);
		if (dim == 3) _split.setZ(d);
	}


	
}
