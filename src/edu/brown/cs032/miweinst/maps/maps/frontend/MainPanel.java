package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	
	private Dimension _size;
	
	public MainPanel(Dimension defaultSize) {
		_size = defaultSize;
		
		DrawingPanel dp = new DrawingPanel(this);
		InputPanel ip = new InputPanel(this);
		
		//add panels to border layout
		this.setLayout(new BorderLayout());
		this.add(dp, BorderLayout.EAST);
		this.add(ip, BorderLayout.WEST);
		
		//sets size and background color of MainPanel
		this.setPreferredSize(defaultSize);
		this.setBackground(new Color(190, 220, 235));	
	}
	
	/**
	 * Accessor for size of MainPanel. 
	 * Size of other panels defined 
	 * relative to this reference, so
	 * size can be dynamically changed.
	 */
	public final Dimension getSize() {
		return _size;
	}
}
