package edu.brown.cs032.miweinst.maps.unit_tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;

import org.junit.Test;

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
////
		//SET BINARY HELPER FILES (full-sized files?)
		setFullBinaryHelperFiles();
		
		String startId = "/n/4017.7374.527767851";
		String endId = "/n/4018.7373.527767859";
		
		Graph<MapNode, Way> g = new Graph<MapNode, Way>();
		MapNode startNode = BinaryHelper.makeMapNode(startId);
		MapNode endNode = BinaryHelper.makeMapNode(endId);
		
		ArrayDeque<GraphNode<MapNode>> path = PathFinder.buildGraphFromNames(g, startNode, endNode);
	}
	
	//Helper method
	private void setFullBinaryHelperFiles() {
		try {
			MapsFile ways = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/ways.tsv");
			MapsFile nodes = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/nodes.tsv");
			MapsFile index = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv");
			BinaryHelper.setFiles(ways, nodes, index);
		} catch (FileNotFoundException e) {
			System.out.println("PathFinderTest.setFullBinaryHelperFiles FileNotFoundException");
		} catch (IOException e) {
			System.out.println("PathFinderTest.setFullBinaryHelperFiles IOException");
		}
	}
}
