package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.util.LatLng;

public class UtilTest {
	
	@Test
	public void latLngEqualsTest()
	{
		/**
		 * Tests the overloaded equals method
		 * in LatLng. 
		 */
		LatLng l1 = new LatLng(1, 1);
		LatLng l2 = new LatLng(1, 1);
		LatLng l3 = new LatLng(1, 0);
		assertTrue(l1.equals(l2));
		assertTrue(!l2.equals(l3));
	}
	
	@Test
	public void latLngToString() 
	{
	}
	
	@Test
	public void latLngHash() 
	{
		
	}
}
