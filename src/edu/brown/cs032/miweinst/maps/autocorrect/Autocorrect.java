package edu.brown.cs032.miweinst.maps.autocorrect;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

public class Autocorrect {
	private Trie _trie;
	private Scanner _scanner;
	private Suggestions _suggestion;
	private double _numWords;
	private int _led;
	private boolean _prefix, _whitespace;
	private Vector<String> _files;

	public Autocorrect(String[] argv) {
		
		_trie = new Trie();
		_suggestion = new Suggestions(_trie);
		_numWords = 0.0;

		try {
		 this.mainLineParser(argv);
		 if (_files.size() == 0) {
		    System.out.println("ERROR:");
		    System.exit(0);
		 }
		 for (String fileName: _files) {
		  _scanner = new Scanner(new FileReader(fileName));
		  String prev = null;
		  while (_scanner.hasNext()) {
		    _numWords = _numWords + 1.0;
		    String word = _scanner.next();
		    word = _trie.pleaseMakeThisKosher(word);
		    if (word.trim().length() > 0) {
		      _trie.addInput(word,prev);
		      prev = word;
		    } //end if
		  } //end while

		 }
		}
		catch (FileNotFoundException e) { 
		  System.out.println("ERROR: FileNotFoundException"); 
		  System.exit(0);
		}
		catch (NoSuchElementException e) { 
		  System.out.println("ERROR: NoSuchElementException"); 
		  System.exit(0);
		}
		catch (IllegalStateException e) { 
		  System.out.println("ERROR: IllegalStateException"); 
		  System.exit(0);
		}
		catch (IllegalArgumentException e) { 
		  System.out.println("ERROR: IllegalArgumentException"); 
		  System.exit(0);
		}
	}

	/*
	 * constructor with defaulted parameters (except for filepath)
	 */
	public static Autocorrect makeAutocorrect(String filepath) {
		String[] argv = { "--led", "3", "--prefix", "--whitespace", filepath };
		return new Autocorrect(argv);
	}
	
	public String[] generateSuggestions(String input) {
	    if (!input.trim().isEmpty()) {	
	    	return _suggestion.generateSuggestions(input, _numWords, _led, _prefix, _whitespace);
	    }
	    return null;
	}

	//string parser that handles arugments, throws IllegalArgumentException 
	private void mainLineParser(String[] arguments) {

	  _led = 0;
	  _prefix = false;
	  _whitespace = false;
	  _files = new Vector<String>();


	  int i = 0;
	  while (i < arguments.length) {
	    boolean kosher_args = false;
	    String arg = arguments[i].trim();
	    if (arg.compareTo("--led") == 0 && Integer.parseInt(arguments[i+1]) >= 0) {
	      _led = Integer.parseInt(arguments[i+1]);
	      i = i + 2;
	      kosher_args = true;
	    }
	    else if (arg.compareTo("--prefix") == 0) {
	      _prefix = true;
	      i++;
	      kosher_args = true;
	    }
	    else if (arg.compareTo("--whitespace") == 0) {
	      _whitespace = true;
	      i++;
	      kosher_args = true;
	    }
	    else { 
	      char[] chars = arg.toCharArray();
	      if (chars[0] == '/' && chars[chars.length - 1] == 't') {
	    	  _files.add(arg);
	    	  kosher_args = true;
	      }//end if
	      i++;
	    }//end else
	    if (!kosher_args) {
	      throw new IllegalArgumentException();
	    }
	  }//end while
	}//end mainLineParser()

}
