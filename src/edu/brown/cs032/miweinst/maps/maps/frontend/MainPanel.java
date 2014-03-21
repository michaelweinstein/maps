package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import edu.brown.cs032.miweinst.maps.maps.GUIInfo;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	
	private Dimension _size;
	private InputPanel _inputPanel;
	private DrawingPanel _dp;
	
	public MainPanel(GUIInfo gui, Dimension defaultSize, AutocorrectConnector acc) {
		_size = defaultSize;
		
		_dp = new DrawingPanel(gui, this);
		_inputPanel = new InputPanel(this, acc);
		
		//add panels to border layout
		this.setLayout(new BorderLayout());
		this.add(_dp, BorderLayout.EAST);
		this.add(_inputPanel, BorderLayout.WEST);
		
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
	
	public InputPanel getInputPanel() {
		return _inputPanel;
	}
	
}
