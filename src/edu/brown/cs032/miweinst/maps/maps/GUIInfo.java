package edu.brown.cs032.miweinst.maps.maps;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs032.miweinst.maps.KDTree.KDTree;
import edu.brown.cs032.miweinst.maps.KDTree.KDTreeNode;
import edu.brown.cs032.miweinst.maps.KDTree.NeighborSearch;
import edu.brown.cs032.miweinst.maps.maps.path.BinaryHelper;
import edu.brown.cs032.miweinst.maps.maps.wrappers.NodesGUIWrapper;
import edu.brown.cs032.miweinst.maps.maps.wrappers.WaysGUIWrapper;
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
	private KDTree _KDTree;
	
	public GUIInfo(FileProcessor fp, BoundingBox initialBounds, KDTree t) {
		_fp = fp;
		_boundingBox = initialBounds;
		_KDTree = t;
		setDimensions();
		updateBounds(fp, initialBounds);
	}
	
	/**
	 * Updates all variables necessary for converting from LatLng
	 * to screen coordinates. 
	 */
	public void updateBounds(FileProcessor fp, BoundingBox box) {
		//update vars
		_boundingBox = box;
		_fp = fp;
		//translate corner to origin, store translation
		LatLng se = _boundingBox.getSoutheast();
		_translate = new Vec2d(-1*se.lng, -1*se.lat);
		//scale box to DrawingPanel; divide BoundingBox dimensions into DP dimensions
		double xScale = _dpDim.x / _boundingBox.getWidth();	//panel.width/box.width
		double yScale = _dpDim.y / _boundingBox.getHeight(); //panel.height/box.height
		//flip scales if longitude or latitude is negative 
		int m = 1;
		int n = 1;
		//for maps, longitude is negative, m = -1
		if (se.lng < 0) 
			m = -1;
		if (se.lat < 0) 
			n = -1;
		_scale = new Vec2d(m*xScale, n*yScale);
/////	ONLY FOR TESTING SCALE, CUT IN HALF SO NODES SHOW UP WITH TEST TSV FILES
		//_scale = new Vec2d(xScale*m/2, yScale*n/2);
	}
	
	/**
	 * Changes x and y scale by a constant (not fraction), but doesn't
	 * translate Box at all, which gives the appearance of perspective
	 * and distance from ground. Called in DrawingPanel with CTRL + scroll
	 */
	public void changeScaleByConstant(int dScale) {
		_scale = new Vec2d(_scale.x + dScale, _scale.y + dScale);
	}
//////
	public void changeScaleByRelative(int dScale) {
		double x = _scale.x;
		double y = _scale.y;
		
		double fraction = dScale / _scale.x;
		
		_scale = new Vec2d(x*fraction, y*fraction);
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
		//try {
			LatLng center = _boundingBox.getCenter();
			double diagonal = _boundingBox.getDiagonalLength();
			//MapNode[] nodes_arr = _fp.getNodesForGUI(center, diagonal);
////////////////
			NeighborSearch ns = new NeighborSearch(center, diagonal);
			KDTreeNode[] nodes_arr = ns.nearestNeighbors(_KDTree.getRoot());
			Map<String, MapNode> map = new HashMap<String, MapNode>(nodes_arr.length);
			for (KDTreeNode n: nodes_arr) {
			//for (MapNode n: nodes_arr) {
////////////
				MapNode mn = (MapNode)n.getComparable();
				map.put(mn.id,mn);
				//map.put(n.id, n);
			}
			return map;
		//} catch (IOException e) {
		//	System.out.println("ERROR: " + "fp.getNodesForGUI throws IOException (GUIInfo.nodesForGUI)");
		//	e.printStackTrace();
		//	return null;
		//}
	}
	
	/**
	 * Return all Ways connecting to MapNode in nodes,
	 * which should be return value of nodesForGUI().
	 * If the endNode of a Way is not included in nodes(),
	 * that Way is not included
	 */
	public Way[] waysForGUI(Map<String, MapNode> nodes) {
		List<Way> waysList = new ArrayList<Way>();
		for (MapNode node: nodes.values()) {	

			Way[] arr = BinaryHelper.nodeToWayArr(node);			
			
			if (arr != null) {
				for (Way w: arr) {										
					if (!waysList.contains(w)) {
						waysList.add(w);
					}
				}
			}
		}		
		return waysList.toArray(new Way[waysList.size()]);
	}
	
	/* Exposes data to front-end to communicate with back-end */
	public BoundingBox getBoundingBox() {
		return _boundingBox;
	}
	public Vec2d getScale() {
		return _scale;
	}
	public FileProcessor getFileProcessor() {
		return _fp;
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
	public Vec2d getDimensions() {
		return _dpDim;
	}
	
}
