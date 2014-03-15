package edu.brown.cs032.miweinst.maps.util;

public final class LatLng {
	
	public final double lat;
	public final double lng;
	
	public LatLng(double lat, double lng) {
		this.lat = checkLatitude(lat);
		this.lng = checkLongitude(lng);
	}

	/* PRIVATE METHODS */
	private static double checkLatitude(double lat) {
		if (lat < -90.0 || lat > 90.0) 
			throw new IllegalArgumentException(lat + "_is_out_of_range");
		return lat;
	}
	private static double checkLongitude(double lng) {
		if (lng < -90.0 || lng > 90.0)
			throw new IllegalArgumentException(lng + "_is_out_of_range");
		return lng;
	} 
	
	/* HOLY TRINITY */
	@Override
	public String toString() {
		return String.format("(%1.3f,  %1.3f)", lat, lng);
	}
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof LatLng)) return false;
		LatLng x = (LatLng) o;
		return (x.lat == lat && x.lng == lng);
	}
	@Override
	public int hashCode() {
		long x = Double.doubleToLongBits(lat);
		long y = Double.doubleToLongBits(lng);
		return (int) (x^(x>>32)^y^(x>>32));
	}
}
