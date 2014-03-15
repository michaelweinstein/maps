package edu.brown.cs032.miweinst.maps.KDTree;

import java.util.Arrays;

public class KDTree {
	
	private KDTreeNode _root;

	
	public KDTree(KDComparable[] array) {
		_root = new KDTreeNode();
		_root.setRoot(true);
		if (array.length > 0) this.buildTree(_root, array, 0, array.length);
		System.out.println("ready");
	}
	/*
	 * the input array is sorted along the first dimension and then recursive
	 * calls are made to sort the the two halves along the second dimension
	 * and then the quarters are sorted along the next dimension. This process
	 * continues until we are sorting one node. In this case, we create a leaf node
	 * that contains the element and then move back up a level of recursion.
	 */
	private void buildTree(KDTreeNode node, KDComparable[] array, int start, int end) {

		int mid = (start + end)/2;
		
		if (start != mid) {
			KDTreeNode rightChild = new KDTreeNode();
			KDTreeNode leftChild = new KDTreeNode();
			
			rightChild.setDepth(node.getDepth() + 1);
			leftChild.setDepth(node.getDepth() + 1);
			
			leftChild.setLeft(true);
			
			leftChild.inheritSplit(node);
			rightChild.inheritSplit(node);
			
			node.setLeftChild(leftChild);
			node.setRightChild(rightChild);
			
			int dim = node.getDimension();
			
			this.split(array,dim,start,end);
			double split = array[mid].getPoint().getCoordinateInDimension(dim);
			
			node.setSplit(split, dim);
			
			this.buildTree(leftChild, array, start, mid);
			this.buildTree(rightChild, array, mid, end);
		}
		else {
			if (!node.isRoot() || array.length <= 1) {
				node.setLeaf(true);
				node.setComparable(array[mid]);
			} //end if
		} //end else
	} //end buildTree()
	
	/*
	 * sorts the portion of the array from
	 * start to end along the given dimension 
	 */
	private void split(KDComparable[] array, int dim, int start, int end) {
		Arrays.sort(array, start, end, new MyComparator(dim));
	}
	
	public KDTreeNode getRoot() {
		return _root;
	}
	
}
