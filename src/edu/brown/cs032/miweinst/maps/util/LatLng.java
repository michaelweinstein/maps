package edu.brown.cs032.miweinst.maps.util;


public final class LatLng {
	
	public final double lat;
	public final double lng;
	
	public LatLng(double lat, double lng) {
		this.lat = checkLatitude(lat);
		this.lng = checkLongitude(lng);
	}
	
	/* PUBLIC METHODS */

	/**
	 * Finds distance between to LatLng objects.  
	 * Just uses euclidean distance, because we 
	 * are dealing with distances on small areas
	 * of the Earth, so not calculating actual 
	 * spheroidal distance between LatLngs.
	 */
	public final double dist(LatLng other) {
		return Math.sqrt(dist2(other));
	}
	/**
	 * Finds distance squared. Use for comparing distances because
	 * it is faster without the sqrt operation. 
	 */
	public double dist2(LatLng other) {
		if (equals(other)) 
			return 0.0;
		return Math.pow(other.lat - this.lat, 2) + Math.pow(other.lng - this.lng, 2);
	}	

	/*
	 * determines if the input lat is
	 * within diff degrees of ll.lat
	 */
	public boolean isWithinRadius(LatLng other, double radius) {
		return (this.dist(other) <= radius);
	}
	
	/* PRIVATE METHODS */
	private static double checkLatitude(double lat) {
		if (lat < -90.0 || lat > 90.0) 
			throw new IllegalArgumentException(lat + "_latitude_is_out_of_range");
		return lat;
	}
	private static double checkLongitude(double lng) {
		if (lng < -90.0 || lng > 90.0)
			throw new IllegalArgumentException(lng + "_longitude_is_out_of_range");
		return lng;
	} 
	
	/* HOLY TRINITY */
	@Override
	public String toString() {
		return String.format("lat: %1.3f,  lng: %1.3f", lat, lng);
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
