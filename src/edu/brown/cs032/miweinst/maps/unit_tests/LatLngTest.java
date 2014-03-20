package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.maps.FileProcessor;
import edu.brown.cs032.miweinst.maps.maps.GUIInfo;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.util.BoundingBox;
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
	@Test
	public void isWithinTest() throws IOException {
		boolean pass = false;
		MapsFile nodes = new MapsFile("/course/cs032/data/maps/nodes.tsv");
		MapsFile ways = new MapsFile("/course/cs032/data/maps/ways.tsv");
		MapsFile index = new MapsFile("/course/cs032/data/maps/index.tsv");
		FileProcessor fp = new FileProcessor(nodes,index,ways);
		LatLng nw = new LatLng(42.3734759, -73.5618164);
		LatLng se = new LatLng(40.0,-71.4);
		double radius = nw.dist(se);
		double lat = (nw.lat + se.lat)/2;
		double lng = (nw.lng + se.lng)/2;
		LatLng center = new LatLng(lat,lng);
		LatLng llWithin = new LatLng(42.0773374,-71.7867287);
		BoundingBox bb = new BoundingBox(nw,se);
		GUIInfo guiInfo = new GUIInfo(fp,bb);
		Map<String, MapNode> guiNodes = guiInfo.nodesForGUI();
		System.out.println(guiNodes.size());
		if (llWithin.isWithinRadius(center, radius)) {
			pass = true;
		}
		assertTrue(pass);
	}
	
}
