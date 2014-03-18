package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.util.LatLng;

public class LatLngTest {

	@Test
	public void test() {
		boolean pass = false;
		LatLng ll1 = new LatLng(40.105, -70.105);
		LatLng ll2 = new LatLng(40.101, -70.105);
		if (ll1.equals(ll1) &&
			!ll1.equals(ll2) &&
			ll1.hashCode() != ll2.hashCode() &&
			ll1.isWithinRadius(ll2, .004) &&
			!ll1.isWithinRadius(ll2, .001))
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
