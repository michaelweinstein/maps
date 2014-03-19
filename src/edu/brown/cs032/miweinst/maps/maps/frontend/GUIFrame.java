package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

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
		
		_mainPanel = new MainPanel(gui, defaultSize);
		this.add(_mainPanel);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		_acConnector = acc;
		
		this.manageKeyboard();
	}
	
	private void manageKeyboard() {
		KeyboardFocusManager kbfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kbfm.addKeyEventDispatcher(new MyKeyEventDispatcher());
	}
	
	class MyKeyEventDispatcher implements KeyEventDispatcher {
		/*
		 * gets key events before the InputPanel
		 * knows about it. This is somewhat of a hack,
		 * but it is the best way I could figure out
		 * how to use the JComboBox editor
		 */
		public boolean dispatchKeyEvent(KeyEvent e) {
			if(e.getID() == KeyEvent.KEY_TYPED) {
				String s = _mainPanel.getInputPanel().getToText();
				if (!s.isEmpty()) {
					_mainPanel.getInputPanel().setSuggestionList(_acConnector.getSuggestions(s));
				}
			}
			return false;
		}
    }

	public MainPanel getMainPanel() { return _mainPanel; }

	
}
