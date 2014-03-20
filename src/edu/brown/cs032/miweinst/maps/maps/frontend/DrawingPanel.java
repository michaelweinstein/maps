package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.brown.cs032.miweinst.maps.maps.GUIInfo;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.util.BoundingBox;
import edu.brown.cs032.miweinst.maps.util.LatLng;
import edu.brown.cs032.miweinst.maps.util.Vec2d;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
	private GUIInfo _guiInfo;

////
//	private MapNode[] _nodes;
	private Map<String, MapNode> _nodes;
	private Way[] _ways;
	
	public DrawingPanel(GUIInfo info, MainPanel mp) {		
		//Sets size, background color and border of DrawingPanel
		Dimension size = mp.getSize();
		int w = (int) (size.getWidth()*4/5);
		int h = (int) (size.getHeight()-15);
		this.setPreferredSize(new Dimension(w, h));
		this.setSize(w, h);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));		

		//receive info from back end about nodes and ways to paint
		_guiInfo = info;
		_nodes = info.nodesForGUI();
		_ways = info.waysForGUI(_nodes);
		
////////	
		System.out.println("nodes.length: " + _nodes.size());
		System.out.println("ways.length: " + _ways.length);
		
		this.addMouseListener(new MapMouseListener());
		this.addMouseMotionListener(new MapMotionListener());	
		this.repaint();
	}
	
	/* UI Manipulation Methods */

	/**
	 * Front-end panning, called throughout mouseDragged
	 */
	public void pan(Vec2d dxy) {
//THREAD 1: front-end 
		BoundingBox oldBox = _guiInfo.getBoundingBox();
		Vec2d scalexy = _guiInfo.getScale();
		//set _guiInfo BoundingBox
		LatLng oldCenter = oldBox.getCenter();
		//convert translation to LatLng units
		double dx = dxy.x / scalexy.x;
		double dy = dxy.y / scalexy.y;
		//create new box and translate to new center (based on dx and dy)
		BoundingBox newBox = new BoundingBox(oldBox.getNorthwest(), oldBox.getSoutheast());
		newBox.setCenter(new LatLng(oldCenter.lat + dy, oldCenter.lng + dx));
		//updates _translate var, convertToScreen method
		_guiInfo.updateBounds(_guiInfo.getFileProcessor(), newBox);
		//convertToScreen is called in paintComponent, so automatically updates
		this.repaint();
	}
	/**
	 * Call to back-end after panning ends, called in mouseReleased.
	 * Updates _nodes and _ways array
	 */
	public void endPan(Vec2d loc) {
//THREAD 2: back-end
		//update _nodes and _ways
		_nodes = _guiInfo.nodesForGUI();
		_ways = _guiInfo.waysForGUI(_nodes);
////
		center = _guiInfo.getBoundingBox().getCenter();
		this.repaint();
	}
	
//////
	//Marks center of BoundingBox at all times
	private LatLng center;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		
/////	MARK CENTER FOR TESTING
		BoundingBox testBox = _guiInfo.getBoundingBox();
		if (center == null)
			center = testBox.getCenter();
		Vec2d testScreenC = _guiInfo.convertToScreen(center);
//		Vec2d testScreenC = _guiInfo.convertToScreen(new LatLng(center.lat+.05, center.lng+.5));
		brush.setColor(Color.RED);
		brush.fill(new Ellipse2D.Double(testScreenC.x, testScreenC.y, 15, 15));
//////^^^^^		
		
///DRAW NODES
//		for (int i=0; i<_nodes.size(); i++) {
/*		for (MapNode node: _nodes.values()) {
//			Vec2d screenLoc = _guiInfo.convertToScreen(_nodes[i].loc);
			Vec2d screenLoc = _guiInfo.convertToScreen(node.loc);
			System.out.println("screenLoc: " + screenLoc.toString());
			brush.setColor(Color.BLACK);
			brush.fill(new Ellipse2D.Double(screenLoc.x, screenLoc.y, 3, 3));
		}*/
///DRAW WAYS
		//draw Ways between _nodes as Line2D
		for (int i=0; i<_ways.length; i++) {
			MapNode startNode = _nodes.get(_ways[i].start);
			Vec2d screenLocStart = _guiInfo.convertToScreen(startNode.loc);
			MapNode endNode = _nodes.get(_ways[i].end);
			if (endNode != null) {
				Vec2d screenLocEnd = _guiInfo.convertToScreen(endNode.loc);
				brush.setColor(Color.BLACK);
				brush.setStroke(new BasicStroke(1));
				brush.draw(new Line2D.Double(screenLocStart.x, screenLocStart.y, screenLocEnd.x, screenLocEnd.y));
			}
		}
	}
	
	/*INNER CLASSES*/
	
	private Vec2d prev;
	private class MapMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) { }
		@Override
		public void mouseEntered(MouseEvent arg0) { }
		@Override
		public void mouseExited(MouseEvent arg0) { }
		@Override
		public void mousePressed(MouseEvent arg0) {		
			prev = new Vec2d(arg0.getX(), arg0.getY());
		}
		@Override
		public void mouseReleased(MouseEvent arg0) { 
			endPan(new Vec2d(arg0.getX(), arg0.getY()));
		}
	}
	
	private class MapMotionListener implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent arg0) {
			//prev will never be null because it is set on mousePressed, ie before mouseDragged
			double dx = prev.x - arg0.getX();
			double dy = prev.y - arg0.getY();
			//pan DrawingPanel 
			pan(new Vec2d(dx, dy));
			//update prev pointer
			prev = new Vec2d(arg0.getX(), arg0.getY());
		}
		@Override
		public void mouseMoved(MouseEvent arg0) {
		}
	}
}
