package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InputPanel extends JPanel {
	
	public InputPanel(MainPanel mp) {
		Dimension size = mp.getSize();
		int w = (int) (size.getWidth()*1/5);
		int h = (int) (size.getHeight()-15);
		this.setPreferredSize(new Dimension(w, h));
		this.setSize(w, h);
		this.setBackground(Color.GRAY);
	}

}
