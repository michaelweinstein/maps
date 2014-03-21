package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import edu.brown.cs032.miweinst.maps.maps.GUIInfo;


@SuppressWarnings("serial")
public class GUIFrame extends JFrame {

	private MainPanel _mainPanel;
	private AutocorrectConnector _acConnector;
	
	public GUIFrame(GUIInfo gui, AutocorrectConnector acc) {
		super("Maps");
		
		int w = 800;
		int h = 600;
		Dimension defaultSize = new Dimension(w, h);
		this.setSize(w, h);
		this.setPreferredSize(defaultSize);
		
		_mainPanel = new MainPanel(gui, defaultSize, acc);
		this.add(_mainPanel);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		this.addButtonListener();
		
		_acConnector = acc;
	}
	
	private void addButtonListener() {
		_mainPanel.getInputPanel().getButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] ways = _mainPanel.getInputPanel().buttonPress();
				_acConnector.getDirections(ways);
				_mainPanel.setStreetNames(ways);
			}
		});
	}
	
	public MainPanel getMainPanel() { return _mainPanel; }

	
}
