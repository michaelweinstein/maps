package edu.brown.cs032.miweinst.maps.KDTree;

import java.util.Comparator;

/*
 * compares two comparables in the comparator's
 * instance's dimension. If the first comparable's coordinate
 * is greater than or equal to that of the second, compare()
 * returns positive 1. Otherwise negative. If it is instantiated 
 * with a dimension irrelevant to our KDTree, compare() returns 0.
 */

public class MyComparator implements Comparator<KDComparable> {

		private int _dimension;
	
		public MyComparator(int dim) {
			_dimension = dim;
		}

		@Override
		public int compare(KDComparable c1, KDComparable c2) {
			KDPoint p1 = c1.getPoint();
			KDPoint p2 = c2.getPoint();

			if (_dimension == 1) {
				if (p1.getX() > p2.getX()) {
					return 1;
				}
				else if (p1.getZ() < p2.getZ()) {
					return -1;
				}
			}
			if (_dimension == 2) {
				if (p1.getY() > p2.getY()) {
					return 1;
				}
				else if (p1.getY() < p2.getY()) {
					return -1;
				}
			}
			if (_dimension == 3) {
				if (p1.getZ() > p2.getZ()) {
					return 1;
				}
				else if (p1.getZ() < p2.getZ()) {
					return -1;
				}
			}
			return 0;
		}
		
}
