package edu.brown.cs032.miweinst.maps.autocorrect;

import java.util.HashMap;
public class Trie {
/*
* This is the Trie class comprising nodes that each store a letter and a word if that node and all of its
* parent nodes form a valid word as defined in the dictionary.
*
*
* author: abok
**/
	private TrieNode _root;
	
	public Trie() {
		_root = new TrieNode('\0');
	}
	
	//this method handles multiple word inputs
	public void addInput(String s, String prev) {
	  String[] input = s.split(" ");
	  if (input.length == 1) this.add(s, prev);
	  else {
	    this.add(input[0],null);
	    for (int i = 1; i < input.length; i++) {
	      this.add(input[i],input[i-1]);
	    }
	  }
	}
	// this method actually creates nodes and adds them to the Trie structure. 
	// it handles bigram/unigram frequencies and adding actual words to nodes that meet the criteria
	private void add(String s, String prev) {
	    if (!s.trim().isEmpty() && s.indexOf(' ') == -1) {
		TrieNode node = this.getRoot();
		int i = 0;
		char[] s_array = s.toCharArray();
		  
		while (i < s.length()) { //find the prefix and set word if necessary
			if (node.hasChild(s_array[i])) {
				node = node.child(s_array[i]);
				i++;
				if (i == s.length()) {
					node.setWord(s);
					node.incrementCount();
					if (prev != null) node.putInBigramMap(prev);
				}
			}
			else {
				break;
			}
		}
		while (i < s.length()) { //if prefix is not whole word, keep adding nodes until entire input is consumed
			node = node.addChild(s_array[i]);
			i++;
			if (i == s.length()) {
				node.setWord(s);
				node.incrementCount();
				if (prev != null) node.putInBigramMap(prev);
			}
		}
	    }
	} //end add()
	
	public TrieNode getRoot() {
		return _root;
	}
	//checks to see if a string exists; does not determine if a string forms a valid word
	public boolean exists(String word) { //returns root if DNE
		char[] word_array = word.toCharArray();
		TrieNode node = this.getRoot();
		for (int i = 0; i < word.length(); i++) {
			if (node.hasChild(word_array[i])) {
				node = node.child(word_array[i]);
			} //end if 
			else return false; //if letter DNE, return false
		} //end for
		return true; //if we find all of the letters, return true
	}
	//returns true if string forms a valid word
	public boolean isWord(String s) {
 		char[] s_array = s.toCharArray();
 		TrieNode node = this.getRoot();
 		for (int i = 0; i < s.length(); i++) {
 			if (node.hasChild(s_array[i])) {
 				node = node.child(s_array[i]);
 				if (node.getWord() != null && s.compareTo(node.getWord()) == 0)  {
				  return true;
				}
 			} //end if
 			else { return false; }
 		} //end for
 		return (node.getWord() != null);
	}
	//this method is necessary for extracting a word if we have the corresponding string
	public TrieNode lastNodeInWord(String word) {
		char[] word_array = word.toCharArray();
		TrieNode node = this.getRoot();
		for (int i = 0; i < word.length(); i++) {
			//System.out.println("letter: " + node.getLetter());
			if (node.child(word_array[i]) != null) node = node.child(word_array[i]);
		} //end for
		return node;
	}
	// the meat and potatoes of Suggestions.prefixMatch()
	public HashMap<String,String> findCompletions(TrieNode lastLetter, HashMap<String,String> completions) {
		for (TrieNode letter: lastLetter.children().values()) {
		  if (letter.getWord() != null) {
		    completions.put(letter.getWord(),letter.getWord());
		  }
		  this.findCompletions(letter, completions);
		}
		return completions;
	}
	//makes sure a string is all lowercase alphanum (for before we insert into our Trie)
	public String pleaseMakeThisKosher(String s) {
	    s = s.replace(",","");
	    s = s.replace("'","");
	    s = s.replace("-"," ");
	    s = s.replace(".","");
 	    s = s.toLowerCase().trim();
	    return s.replaceFirst("[^A-Za-z0-9\\s]", " ").replaceAll("[^A-Za-z0-9\\s]", "");
	}















} //end class
