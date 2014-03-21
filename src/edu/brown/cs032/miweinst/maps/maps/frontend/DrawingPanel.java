package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.brown.cs032.miweinst.maps.App;
import edu.brown.cs032.miweinst.maps.graph.Graph;
import edu.brown.cs032.miweinst.maps.graph.GraphEdge;
import edu.brown.cs032.miweinst.maps.graph.GraphNode;
import edu.brown.cs032.miweinst.maps.maps.GUIInfo;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.maps.path.PathFinder;
import edu.brown.cs032.miweinst.maps.maps.wrappers.NodesGUIWrapper;
import edu.brown.cs032.miweinst.maps.maps.wrappers.WaysGUIWrapper;
import edu.brown.cs032.miweinst.maps.threading.GUIInfoThread;
import edu.brown.cs032.miweinst.maps.util.BoundingBox;
import edu.brown.cs032.miweinst.maps.util.LatLng;
import edu.brown.cs032.miweinst.maps.util.Vec2d;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
	
	private GUIInfo _guiInfo;
////
	//PathFinding vars
	private MapNode _startNode = null;
	private MapNode _endNode = null;
	private ArrayDeque<GraphNode<MapNode>> _path = null;
	private Graph<MapNode, Way> _graph = new Graph<MapNode, Way>();
//	private List<Way> _waysInPath = new ArrayList<Way>();
	
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
		//_nodes = info.nodesForGUI();
		NodesGUIWrapper.set(info.nodesForGUI());
////		
		System.out.println("START WAYS SEARCH");
		//_ways = info.waysForGUI(_nodes);
		WaysGUIWrapper.set( info.waysForGUI(NodesGUIWrapper.get()));
////////	
		System.out.println("FINISHED WAYS SEARCH");
		System.out.println("nodes.length: " + NodesGUIWrapper.get().size());
		System.out.println("ways.length: " + WaysGUIWrapper.get().length);
		
		this.addMouseListener(new MapMouseListener());
		this.addMouseMotionListener(new MapMotionListener());	
		this.addMouseWheelListener(new MapWheelListener());
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
	public void callBackEnd() {
//THREAD 2: back-end
		//update _nodes and _ways
//		_nodes = _guiInfo.nodesForGUI();
//		_ways = _guiInfo.waysForGUI(_nodes);
/////
		GUIInfoThread.setGUIInfo(_guiInfo, this);
		GUIInfoThread.newThread();

		//NodesGUIWrapper.set(_guiInfo.nodesForGUI());
		//System.out.println("GET NODES FOR GUI FINISHED");
		//_ways = _guiInfo.waysForGUI(_nodes);
		//WaysGUIWrapper.set(_guiInfo.waysForGUI(NodesGUIWrapper.get()));
////
		System.out.println("nodes.length: " + NodesGUIWrapper.get().size());
		System.out.println("ways.length: " + WaysGUIWrapper.get().length);
		this.repaint();
	}
	
	/** Zooms front-end */
	public void zoom(int dRot) {
		BoundingBox oldBox = _guiInfo.getBoundingBox();
		LatLng oldCenter = oldBox.getCenter();
		Vec2d scale = _guiInfo.getScale();
		LatLng oldNw = oldBox.getNorthwest();
		LatLng oldSw = oldBox.getSoutheast();
		double rot = dRot/scale.x*10;	
		LatLng nw = new LatLng(oldNw.lat + rot, oldNw.lng - rot);
		LatLng se = new LatLng(oldSw.lat - rot, oldSw.lng + rot);		
		//new box, but center remains the same
		BoundingBox newBox = new BoundingBox(nw, se);
		newBox.setCenter(oldCenter);	
		_guiInfo.updateBounds(_guiInfo.getFileProcessor(), newBox);	
		this.repaint();		

		//Opens thread
		callBackEnd();
	}
	
	/** Affects angle of bird's eye view of landscape! 
	 * Ctrl + scroll 
	 */
	public void isomorphicZoom(int dRot) {
		_guiInfo.changeScaleByConstant(dRot*200);
		this.repaint();
	}
	
	/**
	 * Called when a _startNode and _endNode have been set by the user.
	 * Creates new Graph object for each pathfinding, and graph is used
	 * by draw method to get edges between nodes. After finding the Path,
	 * this method removes the Way from each GraphEdge in the path and
	 * stores it in _waysInPath, to be rendered in paintComponent.
	 */
	public void pathfinding() {
		if (_startNode!= null && _endNode != null) {
			_graph = new Graph<MapNode, Way>();
			try {
				_path = PathFinder.buildGraphFromNames(_graph, _startNode, _endNode);
				//no path was found, just print and get rid of start/end pointers
				if (_path.size() <= 1) {
					System.out.println(_startNode.id + " -/- " + _endNode.id);
					_startNode = null;
					_endNode = null;
					_path = null;
				}
			} catch (StackOverflowError err) {
				System.out.println("StackOverflowError when trying to find paths " + 
						"in DrawingPanel.pathfinding()");
				_path = null;
			}
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;	
/////DRAW PATH
		if (_path != null) {	
			//store all popped elts to add back to path, for continued drawing
			ArrayDeque<GraphNode<MapNode>> tempStorage = new ArrayDeque<GraphNode<MapNode>>();
			//path should be yellow
			brush.setColor(new Color(237, 255, 41));
			brush.setStroke(new BasicStroke(10f));
			//store all Ways in path
			GraphNode<MapNode> n1 = _path.pop();
			tempStorage.add(n1);
			GraphNode<MapNode> n2 = _path.pop();
			tempStorage.add(n2);
			while (!_path.isEmpty()) {
				Vec2d startLoc = _guiInfo.convertToScreen(n1.getElement().loc);
				Vec2d endLoc = _guiInfo.convertToScreen(n2.getElement().loc);				
				brush.draw(new Line2D.Double(startLoc.x, startLoc.y, endLoc.x, endLoc.y));
				//move to next edge/way
				n1 = n2;
				n2 = _path.pop();
				//save popped nodes
				tempStorage.add(n1);
				tempStorage.add(n2);
			}
			//draw last way
			Vec2d startLoc = _guiInfo.convertToScreen(n1.getElement().loc);
			Vec2d endLoc = _guiInfo.convertToScreen(n2.getElement().loc);
			brush.draw(new Line2D.Double(startLoc.x, startLoc.y, endLoc.x, endLoc.y));
			//add all nodes back to path, so it can be drawn on next repaint
			_path = tempStorage;
		}
		
/////DRAW WAYS
		//draw Ways between _nodes as Line2D
		Way[] ways = WaysGUIWrapper.get();
		for (int i=0; i<ways.length; i++) {
			MapNode startNode = NodesGUIWrapper.get(ways[i].start);
			if (startNode != null) {
				Vec2d screenLocStart = _guiInfo.convertToScreen(startNode.loc);
				MapNode endNode = NodesGUIWrapper.get(ways[i].end);
				if (endNode != null) {
					Vec2d screenLocEnd = _guiInfo.convertToScreen(endNode.loc);
					brush.setColor(Color.BLACK);
					brush.setStroke(new BasicStroke(1));
					brush.draw(new Line2D.Double(screenLocStart.x, screenLocStart.y, screenLocEnd.x, screenLocEnd.y));
				}
			}
		}//end for
		
/////DRAW START, END NODE MARKERS
	//draw starting and ending nodes
	if (_startNode != null) {
		Color startColor = new Color(47, 222, 52);
		Color endColor = new Color(207, 33, 33);
		double radius = 10;
		//startNode should be green			
		Vec2d startLoc = _guiInfo.convertToScreen(_startNode.loc);
		brush.setColor(startColor);	//green		
		brush.fill(new Ellipse2D.Double(startLoc.x-radius/2, startLoc.y-radius/2, radius, radius));
		if (_endNode != null) {
			//endNode should be red
			Vec2d endLoc = _guiInfo.convertToScreen(_endNode.loc);
			brush.setColor(endColor); //red
			brush.fill(new Ellipse2D.Double(endLoc.x-radius/2, endLoc.y-radius/2, radius, radius));
		}
	}		
	}
	
	/*INNER CLASSES*/

	private Vec2d prev;
	//whether adding start node or end node
	private boolean isStart = true;
	private class MapMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) { }
		@Override
		public void mouseEntered(MouseEvent arg0) { }
		@Override
		public void mouseExited(MouseEvent arg0) { }
		@Override
		public void mousePressed(MouseEvent arg0) {		
			//CNTRL + click sets 
			if (arg0.isControlDown()) {
				//get nearest MapNode to where the user clicked
				LatLng ll = _guiInfo.convertToLatLng(new Vec2d(arg0.getX(), arg0.getY()));
				MapNode node = App.nearestNeighbor(ll);
				//alternate between setting start and end node
				if (isStart) {
					_startNode = node;
					_endNode =  null;
					isStart = false;
					repaint();
				}
				else {
					_endNode = node;
					isStart = true;
					repaint();	//call before pathfinding(), better responsiveness
					pathfinding();
				}
			}
			//stores prev pointer; for finding dx, dy when panning
			prev = new Vec2d(arg0.getX(), arg0.getY());
		}
		@Override
		public void mouseReleased(MouseEvent arg0) { 
			callBackEnd();
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
		public void mouseMoved(MouseEvent arg0) { }
	}
	
	private class MapWheelListener implements MouseWheelListener {
		@Override
		public void mouseWheelMoved(MouseWheelEvent arg0) {
			//CTRL + scroll -- isomorphic zoom
			if (arg0.isControlDown()) 
				isomorphicZoom(arg0.getWheelRotation());
			//scroll -- regular zoom
			else 
				zoom(arg0.getWheelRotation());
		}
	}
}
