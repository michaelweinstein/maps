package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.maps.path.BinaryHelper;
import edu.brown.cs032.miweinst.maps.util.LatLng;

public class BinaryHelperTest {
	
	@Test
	public void makeMapNode()
	{
		/**
		 * Tests BinaryHelper.makeMapNode, which is also
		 * used as a dependency in other BinaryHelper methods.
		 */
		setBinaryHelperFiles();	
		String id = "/n/4017.7374.527767851";
		MapNode node = BinaryHelper.makeMapNode(id);
		// Test id storage 
		assertTrue(node.id.equals(id));
		// Test LatLng location 
		assertTrue(node.loc.equals(new LatLng(40.1732129, -73.7434298)));
		// Test array of Ways 
		String wayList = "/w/4017.7374.42295268.5.2,/w/4017.7374.42295268.6.1";
		String[] wayArr = wayList.split(",");		
		for (int i=0; i<wayArr.length; i++) {
			assertTrue(wayArr[i].equals(node.ways[i]));
		}
	}
	
	@Test
	public void nodeToWayArr()
	{		
		/**
		 * Tests BinaryHelper.nodeToWayArr
		 */
		setBinaryHelperFiles();
		String id = "/n/4017.7374.527767851";
		MapNode node = BinaryHelper.makeMapNode(id);
		Way[] arr = BinaryHelper.nodeToWayArr(node);		
		assertTrue(arr.length == 2);
		assertTrue(arr[0].id.equals("/w/4017.7374.42295268.5.2"));
		assertTrue(arr[1].id.equals("/w/4017.7374.42295268.6.1"));
	}

	@Test
	public void wayToEndNodes()
	{		
		/**
		 * Tests BinaryHelper.wayToEndNodes
		 */
		setBinaryHelperFiles();
		String id = "/w/4016.7374.42295268.4.1";
		MapNode[] endnodes = BinaryHelper.wayToEndNodes(id);
		// /n/4016.7374.527767850
		assertTrue(endnodes[0].id.equals("/n/4016.7374.527767850"));
		// /n/4016.7374.527767852
		assertTrue(endnodes[1].id.equals("/n/4016.7374.527767852"));
	}
	
	//Helper method
	private void setBinaryHelperFiles() {
		try {
			MapsFile ways = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/ways.tsv");
			MapsFile nodes = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/nodes.tsv");
			MapsFile index = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv");
			BinaryHelper.setFiles(ways, nodes, index);
		} catch (FileNotFoundException e) {
			System.out.println("BinaryHelperTest.setBinaryHelperFiles FileNotFoundException");
		} catch (IOException e) {
			System.out.println("BinaryHelperTest.setBinaryHelperFiles IOException");
		}
	}
}
