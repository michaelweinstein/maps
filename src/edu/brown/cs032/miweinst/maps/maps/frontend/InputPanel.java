package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InputPanel extends JPanel {
	
	private JComboBox _toField, _fromField;
	private JComboBox _focusedJCombo;
	
	public InputPanel(MainPanel mp) {
		Dimension size = mp.getSize();
		int w = (int) (size.getWidth()*1/5);
		int h = (int) (size.getHeight()-15);
		this.setPreferredSize(new Dimension(w, h));
		this.setSize(w, h);
		this.setBackground(Color.GRAY);
		this.addToField();
		this.addFromField();
		
		this.addJComboListeners();
		
		_focusedJCombo = null;
	}

	private void addToField() {
		_toField = new JComboBox();
		_toField.addItem("Starting location");
		_toField.setEditable(true);
		this.add(_toField, BorderLayout.CENTER);
		//basically sets the JComboBox to have a fixed width
		_toField.setPrototypeDisplayValue("---------------");
	}
	
	private void addFromField() {
		_fromField = new JComboBox();
		_fromField.addItem("Destination");
		_fromField.setEditable(true);
		this.add(_fromField, BorderLayout.SOUTH);
		//basically sets the JComboBox to have a fixed width
		_fromField.setPrototypeDisplayValue("---------------");
	}
	
	public String getToText() { return (String) _toField.getEditor().getItem(); }
	public String getFromText() { return (String) _fromField.getEditor().getItem(); }
	
	public void setSuggestionList(String[] list) {
		if (_focusedJCombo != null) {
			
			String buffer = (String)_focusedJCombo.getEditor().getItem();
			_focusedJCombo.removeAllItems();
			
			if (buffer.compareTo("Starting location") != 0 && buffer.compareTo("Destination") != 0) {
				_focusedJCombo.addItem(buffer);
			}
			for (int i = 0; i < list.length; i++) {
				if (i > 5) break;
				_focusedJCombo.addItem(list[i]);
			}
			if (list.length > 0) _focusedJCombo.showPopup();
			else _focusedJCombo.hidePopup();
		}
	} //end setSuggestionList()
	
	public JComboBox getToField() { return _toField; }

	public void addJComboListeners() {
		JComboBox[] boxes = { _toField, _fromField };
		for (final JComboBox box: boxes) {
			box.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent arg0) {}
				@Override
				public void keyReleased(KeyEvent arg0) {}
				@Override
				public void keyTyped(KeyEvent e) {
					_focusedJCombo = box;
				}
			});
		}
	}
	
} //end class
