package edu.brown.cs032.miweinst.maps.maps;

import java.io.IOException;

import edu.brown.cs032.miweinst.maps.util.BoundingBox;
import edu.brown.cs032.miweinst.maps.util.LatLng;

public class GUIInfo {
	
/*	private static BoundingBox _boundingBox;
	public static void setBoundingBox(BoundingBox box) {
		_boundingBox = box;
	}*/

	public static MapNode[] nodesForGUI(FileProcessor fp, BoundingBox box) {
		try {
			LatLng center = box.getCenter();
			double diagonal = box.getDiagonalLength();
			MapNode[] nodes_arr = fp.getNodesForGUI(center, diagonal);
			return nodes_arr;
		} catch (IOException e) {
			System.out.println("ERROR: " + "fp.getNodesForGUI throws IOException (GUIInfo.nodesForGUI)");
			e.printStackTrace();
			return null;
		}
	}

/////
	public static void waysForGUI() {
	}
}
