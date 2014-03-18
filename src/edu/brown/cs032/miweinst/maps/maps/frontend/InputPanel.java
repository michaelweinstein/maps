package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InputPanel extends JPanel {
	
	private  JComboBox _toField;
	
	public InputPanel(MainPanel mp) {
		Dimension size = mp.getSize();
		int w = (int) (size.getWidth()*1/5);
		int h = (int) (size.getHeight()-15);
		this.setPreferredSize(new Dimension(w, h));
		this.setSize(w, h);
		this.setBackground(Color.GRAY);
	}

	private void addToField() {
		_toField = new JComboBox();
		_toField.addItem("Starting location");
		_toField.setEditable(true);
		this.add(_toField, BorderLayout.CENTER);
	}
	
	public String getToText() { return (String)_toField.getSelectedItem(); }
	
	public void setToList(String[] list) {
		_toField.removeAllItems();
		for (int i = 0; i < list.length; i++) {
			_toField.addItem(list[i]);
		}
	}
	
} //end class
