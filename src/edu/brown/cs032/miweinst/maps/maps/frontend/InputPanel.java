package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		this.addToField();
	}

	private void addToField() {
		_toField = new JComboBox();
		_toField.addItem("Starting location");
		_toField.setEditable(true);
		this.addActionListener(_toField);
		this.add(_toField, BorderLayout.CENTER);
	}
	
	public String getToText() { return (String)_toField.getSelectedItem(); }
	
	public void setToList(String[] list) {
		_toField.removeAllItems();
		for (int i = 0; i < list.length; i++) {
			_toField.addItem(list[i]);
		}
	}
	
	public JComboBox getToField() { return _toField; }
	
	private void addActionListener(JComboBox jcb) {
		jcb.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				if (e.getActionCommand().compareTo("comboBoxChanged") == 0) {
					System.out.println("combo changed");
				}
				System.out.println(e.getActionCommand());
            }
		});
	}
	
} //end class
