package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.App;
import edu.brown.cs032.miweinst.maps.graph.Graph;
import edu.brown.cs032.miweinst.maps.graph.GraphNode;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.maps.path.BinaryHelper;
import edu.brown.cs032.miweinst.maps.maps.path.PathFinder;

public class PathFinderTest {
	
	@Test
	public void pathFinderTest() 
	{
		/**
		 * Makes sure PathFinder runs successfully on the large tsv files.
		 * Chooses two nodes relatively close together so that I can 
		 * check path.size by hand to compare against.
		 */
		setFullBinaryHelperFiles();	
		String startId = "/n/4017.7374.527767851";
		String endId = "/n/4018.7373.527767859";
		Graph<MapNode, Way> g = new Graph<MapNode, Way>();
		MapNode startNode = BinaryHelper.makeMapNode(startId);
		MapNode endNode = BinaryHelper.makeMapNode(endId);	
		System.out.println(startNode.loc.toString());
		System.out.println(endNode.loc.toString());
		ArrayDeque<GraphNode<MapNode>> path = PathFinder.buildGraphFromNames(g, startNode, endNode);

		assertTrue(path.size() == 6);
		assertTrue(g.size() == 9);
		
////	Tests output specification in console
		App.printPath(g, path, startId, endId);
	}
	
	
	//Helper method
	/*Sets BinaryHelper files to large tsv files (NOT test files)*/
	private void setFullBinaryHelperFiles() {
		try {
			MapsFile ways = new MapsFile("/course/cs032/data/maps/ways.tsv");
			MapsFile nodes = new MapsFile("/course/cs032/data/maps/nodes.tsv");
			MapsFile index = new MapsFile("/course/cs032/data/maps/nodes.tsv");
			BinaryHelper.setFiles(ways, nodes, index);
		} catch (FileNotFoundException e) {
			System.out.println("PathFinderTest.setFullBinaryHelperFiles FileNotFoundException");
		} catch (IOException e) {
			System.out.println("PathFinderTest.setFullBinaryHelperFiles IOException");
		}
	}
}
