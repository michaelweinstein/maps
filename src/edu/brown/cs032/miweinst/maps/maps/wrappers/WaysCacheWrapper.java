package edu.brown.cs032.miweinst.maps.maps.wrappers;

import java.util.HashMap;

import edu.brown.cs032.miweinst.maps.maps.Way;

/**
 *
 * Class to wrap our ways cache for threading use
 * Provides synchronized access to a map from
 * way ids to ways
 * 
 * @author abok
 *
 */

public class WaysCacheWrapper {

	private static HashMap<String, Way> _waysCache;
	
	public WaysCacheWrapper() {
		_waysCache = new HashMap<String,Way>();
	}
	
	/*
	 * @return boolean of if
	 * @param String key exists in map
	 */
	public synchronized static boolean containsKey(String key) {
		return _waysCache.containsKey(key);
	}
	
	/*
	 * @return Way value at
	 * @param String key 
	 */
	public synchronized static Way get(String key) {
		return _waysCache.get(key);
	}
	
	/*
	 * maps
	 * @param String key to
	 * @param Way value
	 */
	public synchronized static void put(String key, Way value) {
		_waysCache.put(key, value);
	}
	
	
}
