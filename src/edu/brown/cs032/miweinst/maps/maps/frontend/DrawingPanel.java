package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.util.LatLng;
import edu.brown.cs032.miweinst.maps.util.Vec2f;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
	
	private MapNode[] _nodes;

	public DrawingPanel(MapNode[] nodes, MainPanel mp) {		
		//Sets size, background color and border of DrawingPanel
		Dimension size = mp.getSize();
		int w = (int) (size.getWidth()*4/5);
		int h = (int) (size.getHeight()-15);
		this.setPreferredSize(new Dimension(w, h));
		this.setSize(w, h);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));		
		
		_nodes = nodes;
//		System.out.println("nodes.length: " + nodes.length);
		
		this.repaint();
	}
	
/*	private Vec2f worldToScreen(LatLng ll) {
		return null;
	}*/
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
///
		for (int i=0; i<_nodes.length; i++) {
//			System.out.println(_nodes[i].loc.toString());
			if (i == 0) {
				// ~ lowest lat, highest lng
			} 
			else if (i == _nodes.length-1) {
				// ~ highest lat, lowest lng
			}
		}
	}
}
