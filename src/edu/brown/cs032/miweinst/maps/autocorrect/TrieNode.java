package edu.brown.cs032.miweinst.maps.autocorrect;

import java.util.HashMap;
/*
*
* This is the Node class for my Trie. It is pretty self-explanatory and essentially
* comprises its private variables and their getters/setters
*
* author: abok
**/

public class TrieNode {
	
	private Character _letter;
	private String _word;
	private int _count;
	private HashMap<Character,TrieNode> _children;
	private HashMap<String,Integer> _bigramMap;
	
	public TrieNode(Character c) {
		_children = new HashMap<Character,TrieNode>();
		_bigramMap = new HashMap<String,Integer>();
		_letter = c;
		_word = null;
		_count = 0;
	}

	public TrieNode addChild(Character c) {
		TrieNode returnNode = new TrieNode(c);
		_children.put(c, returnNode);
		return returnNode;
	}

	public TrieNode child(Character c) {
		return _children.get(c);
	}

	public HashMap<Character,TrieNode> children() {
		return _children;
	}

	public boolean hasChild(Character c) {
		return _children.containsKey(c);
	}

	public void setWord(String s) {
		_word = s;
	}

	public String getWord() {
		return _word;
	}

	public Character getLetter() {
	  return _letter;
	}

	public int getCount() {
	  return _count;
	}

	public void incrementCount() {
	  _count = _count + 1;
	}

	public void putInBigramMap(String s) {
	  if (_bigramMap.containsKey(s)) {
	    _bigramMap.put(s, _bigramMap.get(s) + 1);
	  }
	  else {
	    _bigramMap.put(s, 1);
	  }
	}

	public int getBigramCount(String s) {
	  if (_bigramMap.containsKey(s)) {
	      return _bigramMap.get(s);
	  }
	  return 0;
	}

	
} //end class
