package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		
		_guiInfo = info;
		_nodes = info.nodesForGUI();
		_ways = info.waysForGUI(_nodes);
		
////////	
		System.out.println("nodes.length: " + _nodes.size());
		System.out.println("ways.length: " + _ways.length);
		
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		
///////	 TEST CONVERSION WITH CENTER NODE
		BoundingBox testBox = _guiInfo.getBoundingBox();
		LatLng testBoxC = testBox.getCenter();
		Vec2d testScreenC = _guiInfo.convertToScreen(testBoxC);
		
		System.out.println(testBoxC.toString());
		System.out.println(testScreenC.toString());
		System.out.println(_guiInfo.getDimensions().toString());
		
		brush.setColor(Color.RED);
		brush.fill(new Ellipse2D.Double(testScreenC.x, testScreenC.y, 15, 15));
////^^^^^		
		
///DRAW NODES
//		for (int i=0; i<_nodes.size(); i++) {
/*		for (MapNode node: _nodes.values()) {
//			Vec2d screenLoc = _guiInfo.convertToScreen(_nodes[i].loc);
			Vec2d screenLoc = _guiInfo.convertToScreen(node.loc);
			System.out.println("screenLoc: " + screenLoc.toString());
			brush.setColor(Color.BLACK);
			brush.fill(new Ellipse2D.Double(screenLoc.x, screenLoc.y, 3, 3));
		}*/
		
		//draw Ways between _nodes as Line2D
		for (int i=0; i<_ways.length; i++) {
			MapNode start = _nodes.get(_ways[i].start);
			Vec2d screenLocStart = _guiInfo.convertToScreen(start.loc);
			MapNode end = _nodes.get(_ways[i].end);
			if (end != null) {
				Vec2d screenLocEnd = _guiInfo.convertToScreen(end.loc);
				brush.setColor(Color.BLACK);
				brush.setStroke(new BasicStroke(3));
				brush.draw(new Line2D.Double(screenLocStart.x, screenLocStart.y, screenLocEnd.x, screenLocEnd.y));
			}
		}
	}
}
