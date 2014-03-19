package edu.brown.cs032.miweinst.maps.maps.frontend.mapgui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

@SuppressWarnings("serial")
public class Node extends Ellipse2D.Double {

	public Node() {
		// TODO Auto-generated constructor stub
	}

	public Node(double x, double y, double w, double h) {
		super(x, y, w, h);
		
//		this.setFrame(x, y, w, h);
	}
}
