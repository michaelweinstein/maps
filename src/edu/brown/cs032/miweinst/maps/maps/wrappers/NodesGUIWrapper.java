package edu.brown.cs032.miweinst.maps.maps.wrappers;

import java.util.HashMap;
import java.util.Map;

import edu.brown.cs032.miweinst.maps.maps.MapNode;

/**
 * 
 * provides synchronized access to the map of
 * nodes used by drawing panel to display our map
 * 
 * @author abok
 *
 */

public class NodesGUIWrapper {

	private Map<String,MapNode> _nodes;
	
	public NodesGUIWrapper() {
		_nodes = new HashMap<String,MapNode>();
	}
	
	
	public synchronized Map<String,MapNode> get() {
		return _nodes;
	}
	
	public synchronized void set(Map<String,MapNode> nodes) {
		_nodes = nodes;
	}
	
	/*
	 * @return boolean of if
	 * @param String key exists in map
	 */
	public synchronized boolean containsKey(String key) {
		return _nodes.containsKey(key);
	}
	
	/*
	 * @return MapNode value at
	 * @param String key 
	 */
	public synchronized MapNode get(String key) {
		return _nodes.get(key);
	}
	
	/*
	 * maps
	 * @param String key to
	 * @param MapNode value
	 */
	public synchronized void put(String key, MapNode value) {
		_nodes.put(key, value);
	}
	
	
	
}
