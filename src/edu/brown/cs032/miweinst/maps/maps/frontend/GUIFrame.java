package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.Dimension;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GUIFrame extends JFrame {

	private MainPanel _mainPanel;
	
	public GUIFrame() {
		super("Maps");
		
		int w = 800;
		int h = 600;
		Dimension defaultSize = new Dimension(w, h);
		this.setSize(w, h);
		this.setPreferredSize(defaultSize);
		
		_mainPanel = new MainPanel(defaultSize);
		this.add(_mainPanel);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	public MainPanel getMainPanel() { return _mainPanel; }
}
