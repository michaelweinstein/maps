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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InputPanel extends JPanel {
	
	private JComboBox _toStreet1, _toStreet2, _fromStreet1, _fromStreet2;
	private JComboBox _focusedJCombo;
	private JButton _btn;
	
	public InputPanel(MainPanel mp) {
		Dimension size = mp.getSize();
		int w = (int) (size.getWidth()*1/5);
		int h = (int) (size.getHeight()-15);
		this.setPreferredSize(new Dimension(w, h));
		this.setSize(w, h);
		this.setBackground(Color.GRAY);
		
		this.addJComboFields();
		this.addButton();
		
		this.addListeners();
		
		_focusedJCombo = _fromStreet1;
		
	}

	/*
	 * add JComboBoxes. Basically exists to
	 * factor out code from constructor
	 */
	private void addJComboFields() {
		_fromStreet1 = new JComboBox();
		_fromStreet2 = new JComboBox();
		_toStreet1 = new JComboBox();
		_toStreet2 = new JComboBox();
		JComboBox[] boxes = { _fromStreet1, _fromStreet2, _toStreet1, _toStreet2 };
		for (JComboBox box: boxes) {
			box.addItem("Enter a street");
			box.setEditable(true);
			this.add(box);
			//basically sets the JComboBox to have a fixed width
			box.setPrototypeDisplayValue("---------------");
		}
	}

	private void addButton() {
		_btn = new JButton("Get Directions");
		this.add(_btn);
	}
	
	/*
	 * populates the JCombo box currently in use with
	 * suggestions generated by autocorrect
	 */
	public void setSuggestionList(String[] list) {
		if (_focusedJCombo != null) {
			
			String buffer = (String)_focusedJCombo.getEditor().getItem();
			_focusedJCombo.removeAllItems();
			//if the current text in the editor isn't the placeholder, keep it
			if (buffer.compareTo("Enter a street") != 0) {
				_focusedJCombo.addItem(buffer);
			}
			for (int i = 0; i < list.length; i++) { //show up to six suggestions
				if (i > 5) break;
				_focusedJCombo.addItem(list[i]);
			}
			//if suggestions exist, show them
			if (list.length > 0) _focusedJCombo.showPopup(); 
			else _focusedJCombo.hidePopup();
		}
	} //end setSuggestionList()

	/*
	 * adds a key listener to the editor component
	 * of each of our JComboBoxes
	 */
	public void addListeners() {
		JComboBox[] boxes = { _toStreet1, _toStreet2, _fromStreet1, _fromStreet2 };
		for (final JComboBox box: boxes) {			
			box.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
					_focusedJCombo = box;
				}
				@Override
				public void keyReleased(KeyEvent e) {}
				@Override
				public void keyTyped(KeyEvent e) {}
			});
		}
		for (final JComboBox box: boxes) {			
			box.getEditor().getEditorComponent().addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {	
					_focusedJCombo = box;
				}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}

			});
		}
	}
	
	public String getInputText() { 
		if (_focusedJCombo == null) return null;
		return (String)_focusedJCombo.getSelectedItem(); 
	}
	
	public String[] buttonPress() {
		String[] a = { (String)_fromStreet1.getSelectedItem(), 
					   (String)_fromStreet2.getSelectedItem(),
					   (String)_toStreet1.getSelectedItem(),
					   (String)_toStreet2.getSelectedItem()};
		return a;
	}
	
	//will be used in gui to add action listener
	public JButton getButton() { return _btn; }
	
} //end class
