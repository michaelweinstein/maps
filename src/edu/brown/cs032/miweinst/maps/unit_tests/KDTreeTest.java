package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.KDTree.KDPoint;
import edu.brown.cs032.miweinst.maps.KDTree.KDTree;
import edu.brown.cs032.miweinst.maps.KDTree.KDTreeNode;
import edu.brown.cs032.miweinst.maps.KDTree.NeighborSearch;
import edu.brown.cs032.miweinst.maps.maps.FileProcessor;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;

public class KDTreeTest {

	@Test
	public void test() {
		//boolean pass = false;
		try {
			MapsFile nodes = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/nodes.tsv");
			MapsFile index = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv");
			MapsFile ways = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/ways.tsv");
			FileProcessor fp = new FileProcessor(nodes,index,ways);
			MapNode[] nodes_array = fp.nodesArrayForKDTree();
			KDTree t = new KDTree(nodes_array);
			KDPoint p = new KDPoint(40.1581762, -73.7485663, 0.0);
			NeighborSearch ns = new NeighborSearch(p, 1, 0, false, false);
			KDTreeNode[] neighbors = ns.nearestNeighbors(t.getRoot());
			MapNode neighbor = (MapNode) neighbors[0].getComparable();
			System.out.println(neighbor.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//assertTrue(pass);
	}

}
