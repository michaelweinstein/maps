package edu.brown.cs032.miweinst.maps.maps;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs032.miweinst.maps.maps.path.BinaryHelper;
import edu.brown.cs032.miweinst.maps.util.BoundingBox;
import edu.brown.cs032.miweinst.maps.util.LatLng;
import edu.brown.cs032.miweinst.maps.util.Vec2d;

public class GUIInfo {
	
	private FileProcessor _fp;
	private BoundingBox _boundingBox;
	private Vec2d _scale;
	private Vec2d _translate;	
////
	private Vec2d _dpDim;
	
	public GUIInfo(FileProcessor fp, BoundingBox initialBounds) {
		_fp = fp;
		_boundingBox = initialBounds;
		setDimensions();
		updateBounds(fp, initialBounds);
	}
	
	/**
	 * 
	 */
	public void updateBounds(FileProcessor fp, BoundingBox box) {
		//update vars
		_boundingBox = box;
		_fp = fp;
		//translate northwest to origin, store translation
		LatLng nw = _boundingBox.getSoutheast();
		_translate = new Vec2d(-1*nw.lng, -1*nw.lat);
		//stretch southeast to DrawingPanel, store scale
//		LatLng se = _boundingBox.getSoutheast();
				
		double xScale = _dpDim.x / _boundingBox.getWidth();	//panel.width/box.width
		double yScale = _dpDim.y / _boundingBox.getHeight(); //panel.height/box.height
		
//////
/*		System.out.println("width: " + _boundingBox.getWidth());
		System.out.println("height: " + _boundingBox.getHeight());
		System.out.println("translate: " + _translate);
		System.out.println("xScale: " + xScale);
		System.out.println("yScale: " + yScale);*/
		
//		_scale = new Vec2d(xScale, yScale);
/////	ONLY FOR TESTING SCALE, SO NODES SHOW UP WITH TEST TSV FILES
		_scale = new Vec2d(xScale*-1/2, yScale/2);
	}
	
	
	/**
	 * Convert from LatLng coordinates to XY java coordinates, 
	 * where _translate and _scale have been set according to BoundingBox
	 */
	public Vec2d convertToScreen(LatLng ll) {
		// First translate to origin
		double x = ll.lng + _translate.x;
		double y = ll.lat + _translate.y;
		// Then scale to DP
		x *= _scale.x;
		y *= _scale.y;
		return new Vec2d(x, y);
	}

	/**
	 * Return MapNodes to be rendered. Reads from file within bounds
	 */
	public Map<String, MapNode> nodesForGUI() {
		try {
			LatLng center = _boundingBox.getCenter();
			double diagonal = _boundingBox.getDiagonalLength();
			MapNode[] nodes_arr = _fp.getNodesForGUI(center, diagonal);
			Map<String, MapNode> map = new HashMap<String, MapNode>(nodes_arr.length);
			for (MapNode n: nodes_arr) {
				map.put(n.id, n);
			}
			return map;
		} catch (IOException e) {
			System.out.println("ERROR: " + "fp.getNodesForGUI throws IOException (GUIInfo.nodesForGUI)");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Return all Ways connecting to MapNode in nodes,
	 * which should be return value of nodesForGUI().
	 */
	public Way[] waysForGUI(Map<String, MapNode> nodes) {
		List<Way> waysList = new ArrayList<Way>();
		for (MapNode node: nodes.values()) {		
			Way[] arr = BinaryHelper.nodeToWayArr(node);			
			for (Way w: arr) {
				if (!waysList.contains(w)) {
					waysList.add(w);
				}
			}
		}		
		return waysList.toArray(new Way[waysList.size()]);
	}
	
	public BoundingBox getBoundingBox() {
		return _boundingBox;
	}

//////////
	private void setDimensions() {
///////// 	DON'T HARDCODE SIZES; SOMEHOW GET DRAWINGPANEL SIZE 
		int w = 800;
		int h = 600;
		Dimension size = new Dimension(w, h);
		float dpWidth = (float) size.getWidth()*4/5;
		float dpHeight = (float) size.getHeight()-15;
		_dpDim = new Vec2d(dpWidth, dpHeight);
	}
}
