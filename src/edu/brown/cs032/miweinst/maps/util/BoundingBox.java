package edu.brown.cs032.miweinst.maps.util;

public class BoundingBox {
	
	private LatLng _northwest;
	private LatLng _southeast;
	
	public BoundingBox(LatLng nw, LatLng se) {
		this._northwest = nw;
		this._southeast = se;
		if (!repOK()) {
			System.out.println("ERROR: " + "BoundingBox corners not valid (BoundingBox())");
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Verify that BoundingBox is valid.
	 */
	private boolean repOK() {
		if (_southeast.lng > _northwest.lng) {
			if (_southeast.lat < _northwest.lat) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if BoundingBox is square.
	 * Should be square for gui uses because
	 * the x and y -scale will be the same.
	 * Java rounding inconsistencies are
	 * accounted for.
	 */
	public boolean isSquare() {
		return Math.abs(getWidth() - getHeight()) < .0001;
	}

	
	public boolean contains(LatLng pt) {
		if (pt.lat < _northwest.lat && pt.lat > _southeast.lat) 
			if (pt.lng > _northwest.lng && pt.lng < _southeast.lng) 
				return true;
		return false;
	}
	
	/**
	 * Translates entire BoundingBox so that the 
	 * center is at specified LatLng. Maintains
	 * dimensions of box. Finds difference between
	 * old center and new center, and then translates
	 * both corners by that same difference.
	 * Does NOT support crossing 0-->90 quadrant;
	 * will not setCenter if lat or lng goes past 90 or 0.
	 */
	public void setCenter(LatLng center) {
		LatLng oldCenter = getCenter();
		double dlat = center.lat - oldCenter.lat;
		double dlng = center.lng - oldCenter.lng;
		//does NOT support setting Center across quadrants (0 < abs(ll) < 90)
		try {
			LatLng nw = new LatLng(_northwest.lat + dlat, _northwest.lng + dlng);
			LatLng se = new LatLng(_southeast.lat + dlat, _southeast.lng + dlng);
			_northwest = nw;
			_southeast = se;
		} catch (IllegalArgumentException e) {
			System.out.println("Cannot setCenter of BoundingBox because lat/lng not" + 
					"between 0 and 90 after translation (BoundingBox.setCenter)");
		}
	}
	
	/**
	 * returns the center point of bounding box as a LatLng 
	 */
	public LatLng getCenter() {
		double lat = (_northwest.lat + _southeast.lat)/2;
		double lng = (_northwest.lng + _southeast.lng)/2;
		return new LatLng(lat,lng);
	}
	/**
	 * returns length of diagonal in degrees
	 */
	public double getDiagonalLength() {
		return _northwest.dist(_southeast);
//		return _northwest.dist2(_southeast);
	}
	
	/**
	 * Return width of BoundingBox, used for GUI scaling
	 */
	public double getWidth() {
		return _southeast.lng - _northwest.lng;
	}
	/**
	 * Return height of BoundingBox, used for GUI scaling
	 */
	public double getHeight() {
		return _northwest.lat - _southeast.lat;
	}
	
	public LatLng getNorthwest() {
		return _northwest;
	}
	public LatLng getSoutheast() {
		return _southeast;
	}
}
