package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.util.BoundingBox;


@SuppressWarnings("serial")
public class GUIFrame extends JFrame {

	private MainPanel _mainPanel;
	private AutocorrectConnector _acConnector;
	
	public GUIFrame(MapNode[] nodes, AutocorrectConnector acc) {
		super("Maps");
		
		int w = 800;
		int h = 600;
		Dimension defaultSize = new Dimension(w, h);
		this.setSize(w, h);
		this.setPreferredSize(defaultSize);
		
		_mainPanel = new MainPanel(nodes, defaultSize);
		this.add(_mainPanel);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		this.addButtonListener();
		
		_acConnector = acc;
		
		this.manageKeyboard();
	}
	
	
	private void addButtonListener() {
		_mainPanel.getInputPanel().getButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] ways = _mainPanel.getInputPanel().buttonPress();
				_acConnector.getDirections(ways);
			}
		});
	}
	
	
	private void manageKeyboard() {
		KeyboardFocusManager kbfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kbfm.addKeyEventDispatcher(new MyKeyEventDispatcher());
	}
	
	class MyKeyEventDispatcher implements KeyEventDispatcher {
		/*
		 * gets the key event right after the input panel
		 * does (the key event in inputpanel is key_pressed)
		 * so this way it can get what the user has just typed
		 * for suggestion generation
		 */
		public boolean dispatchKeyEvent(KeyEvent e) {
			if(e.getID() == KeyEvent.KEY_RELEASED) {
				String s = _mainPanel.getInputPanel().getInputText();
				if (s != null && !s.isEmpty()) {
					_mainPanel.getInputPanel().setSuggestionList(_acConnector.getSuggestions(s));
				}
			}
			return false;
		}
    }

	public MainPanel getMainPanel() { return _mainPanel; }

	
}
