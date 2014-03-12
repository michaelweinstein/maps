package edu.brown.cs032.miweinst.maps.KDTree;
/*
 * essentially a three dimensional point. This class
 * would need slight modifications if the number
 * of dimensions k changed
 */
public class KDPoint implements KDComparable {
	
	private double _x, _y, _z;
	
	public KDPoint(double x, double y, double z)  {
		_x = x;
		_y = y;
		_z = z;
	}
	
	public double calculateDistance(KDPoint p) {
		return java.lang.Math.sqrt(java.lang.Math.pow((_x - p.getX()),2.0) 
								 + java.lang.Math.pow((_y - p.getY()),2.0)
								 + java.lang.Math.pow((_z - p.getZ()),2.0));
	}
	
	public double getCoordinateInDimension(int dim) {
		if (dim == 1) return _x;
		if (dim == 2) return _y;
		if (dim == 3) return _z;
		else return 0.0;
	}
	
	public double getX() { return _x; }
	public void setX(double x) { _x = x; }
	
	public double getY() { return _y; }
	public void setY(double y) { _y = y; }
	
	public double getZ() { return _z; }
	public void setZ(double z) { _z = z; }
	
	public KDPoint getPoint() { return this; }
}
