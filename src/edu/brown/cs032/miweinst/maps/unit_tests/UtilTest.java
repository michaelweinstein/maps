package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.util.BoundingBox;
import edu.brown.cs032.miweinst.maps.util.LatLng;

public class UtilTest {
	
	@Test
	public void boundingBox() {
		/**
		 * Tests repOK validity checking in boundingBox constructor.
		 * Tests box with no width.
		 * Tests box where northwest and southeast corners are switched.
		 */
		boolean exception = false;
		try {
			new BoundingBox(new LatLng(0, 0), new LatLng(0, 1));
		} catch (IllegalArgumentException e) {
			exception = true;
		}
		assertTrue(exception);
		
		try {
			new BoundingBox(new LatLng(32, -71), new LatLng(40, -75));
		} catch (IllegalArgumentException e) {
			exception = true;
		}
		assertTrue(exception);
	}
	
	@Test
	public void boundingBoxCenter()
	{
		/**
		 * Tests get and setCenter method in BoundingBox;
		 * should translate both corners so that LatLng center 
		 * is new center of BoundingBox.
		 */
		BoundingBox bb = new BoundingBox(new LatLng(40.0, -75), new LatLng(32.0, -71));
		//getCenter before
		assertTrue(bb.getCenter().equals(new LatLng(36, -73)));
		//setCenter to origin
		bb.setCenter(new LatLng(0, 0));
		//getCenter after should be origin
		LatLng c = bb.getCenter();
		assertTrue(c.equals(new LatLng(0, 0)));
		//corners should have translated correctly
		assertTrue(bb.getNorthwest().equals(new LatLng(4, -2)));
		assertTrue(bb.getSoutheast().equals(new LatLng(-4, 2)));
	}
	
	@Test
	public void boundingBoxIsSquare()
	{
		/**
		 * Tests construction of a square BoundingBox, for
		 * analogous x- and y-scales in gui.
		 */
		BoundingBox bs = new BoundingBox(new LatLng(40.3,	-71.8), new LatLng(40.0, -71.5));
		BoundingBox br = new BoundingBox(new LatLng(40.4, -71.8), new LatLng(40, -71.5));
		assertTrue(bs.isSquare());
		assertTrue(!br.isSquare());
	}
	
	@Test
	public void latLngEqualsTest()
	{
		/**
		 * Tests the overloaded equals method
		 * in LatLng, and hashCode() too.
		 */
		LatLng l1 = new LatLng(1, 1);
		LatLng l2 = new LatLng(1, 1);
		LatLng l3 = new LatLng(1, 0);
		assertTrue(l1.equals(l2));
		assertTrue(l1.hashCode() == l2.hashCode());
		assertTrue(!l2.equals(l3));
	}
}
