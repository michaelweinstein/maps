package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.maps.MapNode;

public class MapsNodeTest {

	@Test
	public void test() {
		boolean pass = false;
		MapNode node1 = new MapNode("1",40.115,-73.105,"way1,way2");
		MapNode node2 = new MapNode("2",41.115,-70.205,"way1,way2");
		if (node1.equals(node1) &&
			!node2.equals(node1) &&
			node1.hashCode() != node2.hashCode() &&
			node1.hashCode() == node1.hashCode())
		{
			pass = true;
		}
		assertTrue(pass);
	}
	

}
