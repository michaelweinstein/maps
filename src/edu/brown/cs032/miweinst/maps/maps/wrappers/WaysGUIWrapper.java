package edu.brown.cs032.miweinst.maps.maps.wrappers;

import edu.brown.cs032.miweinst.maps.maps.Way;

/**
 * 
 * provides synchronized access to the array of
 * ways used by drawing panel to display our map
 * 
 * @author abok
 *
 */

public class WaysGUIWrapper {

	private static Way[] _ways;
	
	public WaysGUIWrapper() {
		_ways = new Way[0];
	}
	
	/*
	 * @return ways array stored in wrapper
	 */
	public static synchronized Way[] get() {
		return _ways;
	}
	
	/*
	 * @param ways to set the wrapper to
	 */
	public static synchronized void set(Way[] ways) {
		_ways = ways;
	}
	
	
}
