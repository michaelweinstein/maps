package edu.brown.cs032.miweinst.maps.maps.frontend;

import java.util.ArrayList;
import java.util.HashMap;

import edu.brown.cs032.miweinst.maps.autocorrect.Autocorrect;

public class AutocorrectConnector {

	private Autocorrect _autocorrect;
	private HashMap<String,String> _validWays;
	
	public AutocorrectConnector(Autocorrect a, HashMap<String,String> ways) {
		_autocorrect = a;
		_validWays = ways;
	}
	
	/*
	 * returns all generated suggestions that
	 * form a full way name
	 */
	public String[] getSuggestions(String s) {
		ArrayList<String> list = new ArrayList<String>();
		for (String suggestion: _autocorrect.generateSuggestions(s)) {
			suggestion = suggestion.toLowerCase().trim();
			if (_validWays.containsKey(suggestion)) list.add(_validWays.get(suggestion));
		}
		return list.toArray(new String[list.size()]);
	}
	
	public void getDirections(String[] ways) {
		for (String s: ways) {
			System.out.println(s);
		}
	}

	
}
