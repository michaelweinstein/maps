package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.KDTree.KDComparable;
import edu.brown.cs032.miweinst.maps.KDTree.KDTree;
import edu.brown.cs032.miweinst.maps.maps.FileProcessor;
import edu.brown.cs032.miweinst.maps.maps.GUIInfo;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.maps.path.BinaryHelper;
import edu.brown.cs032.miweinst.maps.util.BoundingBox;
import edu.brown.cs032.miweinst.maps.util.LatLng;
import edu.brown.cs032.miweinst.maps.util.Vec2d;

public class GUITest {
	
	private String waysPath = "/course/cs032/data/maps/ways.tsv";
	private String nodesPath = "/course/cs032/data/maps/nodes.tsv";
	private String indexPath = "/course/cs032/data/maps/index.tsv";
	
	@Test
	public void conversionTest() 
	{
		try {
			//make FileProcessor and set BinaryHelper files
			MapsFile ways = new MapsFile(waysPath);
			MapsFile nodes = new MapsFile(nodesPath);
			MapsFile index = new MapsFile(indexPath);
			BinaryHelper.setFiles(ways, nodes, index);
			FileProcessor fp = new FileProcessor(nodes, index, ways);
			BoundingBox box = new BoundingBox(new LatLng(40.2720844,	-73.7175365), new LatLng(40.1615135, -73.6937661));
			GUIInfo gui = new GUIInfo(fp, box, new KDTree(new KDComparable[0]));
			//test that LatLng remains the same after converting to screen and back
			LatLng ll = new LatLng(45, -45);
			Vec2d vec = gui.convertToScreen(ll);
			LatLng test = gui.convertToLatLng(vec);
			//LatLng before == after
			assertTrue(test.equals(ll));
		} catch (IOException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void centerTest()
	{
		try {
			//make FileProcessor and set BinaryHelper files
			MapsFile ways = new MapsFile(waysPath);
			MapsFile nodes = new MapsFile(nodesPath);
			MapsFile index = new MapsFile(indexPath);
			BinaryHelper.setFiles(ways, nodes, index);
			FileProcessor fp = new FileProcessor(nodes, index, ways);
			//Create BoundingBox and try to convert center LatLng to XY
			BoundingBox box = new BoundingBox(new LatLng(40.2720844,	-73.7175365), new LatLng(40.1615135, -73.6937661));
			LatLng c = box.getCenter();
			GUIInfo gui = new GUIInfo(fp, box, new KDTree(new KDComparable[0]));
			Vec2d screenCenter = gui.convertToScreen(c);
			Vec2d dpDim = gui.getDimensions();
			Vec2d dpCenter = new Vec2d(dpDim.x/2, dpDim.y/2);
///
			System.out.println("dpCenter: " + dpCenter);
			System.out.println("convertToScreen(c): " + screenCenter.toString());
		} catch (IOException e) {
			assertTrue(false);
		}
	}

}
