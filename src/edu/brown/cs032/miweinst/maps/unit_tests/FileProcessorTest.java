package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.maps.FileProcessor;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;

public class FileProcessorTest {

	@Test
	public void test() {
		boolean pass = false;
		try {
			MapsFile nodes = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/nodes.tsv");
			MapsFile index = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv");
			MapsFile ways = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/ways.tsv");
			FileProcessor fp = new FileProcessor(nodes,index,ways);
			MapNode[] nodes_array = fp.nodesArrayForKDTree();
			if (nodes_array[0].toString().compareTo("MapNode. id: /n/4015.7374.527767659, lat: 40.158176, long: -73.748566") == 0 &&
				nodes_array[2].toString().compareTo("MapNode. id: /n/4016.7374.527767845, lat: 40.161514, long: -73.74798")	== 0 &&
				nodes_array[nodes_array.length - 1].toString().compareTo("MapNode. id: /n/4037.7360.527767961, lat: 40.37469, long: -73.60741") == 0)
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
