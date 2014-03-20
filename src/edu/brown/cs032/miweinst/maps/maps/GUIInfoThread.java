package edu.brown.cs032.miweinst.maps.maps;

import edu.brown.cs032.miweinst.maps.maps.wrappers.NodesGUIWrapper;
import edu.brown.cs032.miweinst.maps.maps.wrappers.WaysGUIWrapper;

public class GUIInfoThread extends Thread {

	private GUIInfo _guiInfo;
	
	public GUIInfoThread(GUIInfo g) {
		_guiInfo = g;
	}
	
	@Override
	public void run() {
		NodesGUIWrapper.set(_guiInfo.nodesForGUI());
		WaysGUIWrapper.set(_guiInfo.waysForGUI(NodesGUIWrapper.get()));
	}
}
