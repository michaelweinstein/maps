package edu.brown.cs032.miweinst.maps.threading;

import java.io.IOException;
import java.util.HashMap;

import edu.brown.cs032.miweinst.maps.maps.FileProcessor;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.util.LatLng;

public class FileProcessorThread extends Thread {

	private LatLng _latLng;
	private double _constraint;
	private FileProcessor _fp;
	private String _function;
	private MapNode[] _KDNodes, _guiNodes;
	private HashMap<String, String> _ways;
	
	FileProcessorThread(FileProcessor fp, String func) {
		_latLng = null;
		_constraint = 0.0;
		_function = func;
		_fp = fp;
		_KDNodes = null;
		_guiNodes = null;
	}
	
	public void run() {
		try{ 
			if (_function.compareTo("nodesArrayForKDTree") == 0) {
				_KDNodes = _fp.nodesArrayForKDTree();
			}
			else if (_function.compareTo("getNodesForGUI") == 0 && _latLng != null) {
				_guiNodes = _fp.getNodesForGUI(_latLng, _constraint);
			}
			else if (_function.compareTo("getWays") == 0) {
				_ways = _fp.getWays();
			}
		}
		catch (IOException e) {
			System.out.println("IOException in FileProcessorThread.run()");
		}
	}
	
	public void setArgs(LatLng ll, double c) {
		_latLng = ll;
		_constraint = c;
	}
	
	public MapNode[] getGUINodes() { return _guiNodes; }
	public MapNode[] getKDNodes() { return _KDNodes; }
	public HashMap<String,String> getWays() { return _ways; }
	
}
