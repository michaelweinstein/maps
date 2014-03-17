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
	
	public LatLng getNorthwest() {
		return _northwest;
	}
	public LatLng getSoutheast() {
		return _southeast;
	}
}
