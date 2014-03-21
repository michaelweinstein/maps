package edu.brown.cs032.miweinst.maps.threading;

import java.util.Map;

import edu.brown.cs032.miweinst.maps.maps.GUIInfo;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.maps.frontend.DrawingPanel;
import edu.brown.cs032.miweinst.maps.maps.wrappers.NodesGUIWrapper;
import edu.brown.cs032.miweinst.maps.maps.wrappers.WaysGUIWrapper;

public class GUIInfoThread extends Thread {

	private static GUIInfo _guiInfo;
	private static GUIInfoThread _instance = null;
	private static DrawingPanel _dp = null;
	public boolean _stop;
	
	public GUIInfoThread() {
		_stop = false;
	}
	
	public static void setGUIInfo(GUIInfo g, DrawingPanel dp) {
		_guiInfo = g;
		if (_dp == null) {
			_dp = dp;
		}
	}
	
	
	public static void newThread() {
		if (_instance != null) {
			//if there is another thread running, stop it
			//it will be stopped in between finding nodes and finding ways
			_instance._stop = true;
		}
		_instance = new GUIInfoThread();
		_instance.start();
	}
	
	@Override
	public void run() {
		//search for nodes and ways to render
		Map<String,MapNode> nodes = _guiInfo.nodesForGUI();
		if (!_stop) { //if we haven't stopped this thread (i.e started a new one):
			Way[] ways = _guiInfo.waysForGUI(nodes);
			//set in wrappers once *both* searches are complete
			NodesGUIWrapper.set(nodes);
			WaysGUIWrapper.set(ways);
			_dp.repaint();
		}
		
	}
}
