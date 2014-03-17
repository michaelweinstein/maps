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
		boolean pass = false;
		try {
			//MapsFile nodes = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/nodes.tsv");
			MapsFile nodes = new MapsFile("/course/cs032/data/maps/nodes.tsv");
			MapsFile index = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv");
			MapsFile ways = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/ways.tsv");
			
			FileProcessor fp = new FileProcessor(nodes,index,ways);
			
			MapNode[] nodes_array = fp.nodesArrayForKDTree();
			KDTree t = new KDTree(nodes_array);
			
			KDPoint p1 = new KDPoint(40.1581762, -73.7485663, 0.0);
			NeighborSearch ns1 = new NeighborSearch(p1, 5, 0, false, false);
			KDTreeNode[] neighbors1 = ns1.nearestNeighbors(t.getRoot());
			MapNode neighbor1 = (MapNode)neighbors1[0].getComparable();
			
			KDPoint p2 = new KDPoint(40.301302, -73.714131, 0.0);
			NeighborSearch ns2 = new NeighborSearch(p2, 5, 0, false, false);
			KDTreeNode[] neighbors2 = ns2.nearestNeighbors(t.getRoot());
			MapNode neighbor2 = (MapNode)neighbors2[0].getComparable();
			if (neighbor1.id.compareTo("/n/4015.7374.527767659") == 0 &&
				neighbor2.id.compareTo("/n/4030.7371.527767900") == 0)
			{
				pass = true;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(pass);
	}

}
