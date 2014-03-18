package edu.brown.cs032.miweinst.maps.util;

public class BoundingBox {
	
	private LatLng _northwest;
	private LatLng _southeast;
	
	public BoundingBox(LatLng nw, LatLng se) {
		this._northwest = nw;
		this._southeast = se;
	}
	
	public boolean contains(LatLng pt) {
		if (pt.lat > _northwest.lat && pt.lat < _southeast.lat) 
			if (pt.lng < _northwest.lng && pt.lng > _southeast.lng) 
				return true;
		return false;
	}
	
	/*
	 * returns the center point of bounding box as a LatLng 
	 */
	public LatLng getCenter() {
		double lat = (_northwest.lat + _southeast.lat)/2;
		double lng = (_northwest.lng + _southeast.lng)/2;
		return new LatLng(lat,lng);
	}
	/*
	 * returns length of diagonal in degerees
	 */
	public double getDiagonalLength() {
		return _northwest.dist2(_southeast);
	}
	
	public LatLng getNorthwest() {
		return _northwest;
	}
	public LatLng getSoutheast() {
		return _southeast;
	}
}
