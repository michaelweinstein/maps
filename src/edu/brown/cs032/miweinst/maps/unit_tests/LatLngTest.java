package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.util.LatLng;

public class LatLngTest {

	@Test
	public void test() {
		boolean pass = false;
		LatLng ll1 = new LatLng(40.105, -70.105);
		LatLng ll2 = new LatLng(41.101, -71.101);
		if (ll1.equals(ll1) &&
			!ll1.equals(ll2) &&
			ll1.hashCode() != ll2.hashCode() &&
			LatLng.isWithinLat(40.106, ll1, .0011) &&
			!LatLng.isWithinLat(40.106, ll2, .1))
		{
			pass = true;
		}
		assertTrue(pass);
	}
	
	@Test
	public void badLatTest() {
		boolean pass = false;
		try {
			LatLng ll = new LatLng(91.1,50.0);
		}
		catch (IllegalArgumentException e) {
			pass = true;
		}
		assertTrue(pass);
	}

	@Test
	public void badLngTest() {
		boolean pass = false;
		try {
			LatLng ll = new LatLng(55.3,-90.1);
		}
		catch (IllegalArgumentException e) {
			pass = true;
		}
		assertTrue(pass);
	}
}
