package edu.brown.cs032.miweinst.maps.autocorrect;
import java.util.HashMap;
import java.util.List;
/*
*
* this class contains the algorithms to generate our suggestions for autocorrect
* it generates suggestions through prefix matching, whitespace matching and levenshtein edit distance matching
* _completions will contain the final suggestions, which will be sorted before displaying to the user
*
* author: abok
**/
public class Suggestions {

	private Trie _trie;
	private HashMap<String,String> _completions;

	public Suggestions(Trie t) {
	  _trie = t;
	  _completions = new java.util.HashMap<String,String>();
	}

	private void prefixMatch(String s) {
	    if (_trie.exists(s)) {
	      TrieNode lastLetter = _trie.lastNodeInWord(s);
	      _trie.findCompletions(lastLetter, _completions);
	      if (_trie.isWord(s)) {
	    	  _completions.put(s,s); 
	      }
	    }
	}

	//if the word can be broken up into two valid words, then suggest those words
	private void whitespaceMatch(String s) {
	    int s_length = s.length();
	    for (int i = 1; i < s_length; i++) {
	      String s1 = s.substring(0,i);
	      String s2 = s.substring(i, s_length);
	      if (_trie.isWord(s1) && _trie.isWord(s2)) {
	    	  _completions.put(s1 + " " + s2,s1 + " " + s2);
	      }
	    }

	}

	private void getLevenshteinMatch(String s, int led) {
		EditDistanceGenerator edg = new EditDistanceGenerator(_trie, led);
		List<String> suggestions = edg.generateSuggestions(s);
		for (String str: suggestions) {
			_completions.put(str,str);
		}

	}
	
	public String[] generateSuggestions(String s, double total_words, int led, boolean prefix, boolean whitespace) {
		s = _trie.pleaseMakeThisKosher(s);
		String[] input = s.split(" ");
		for (int c = 0; c < input.length; c++) { 
		    input[c] = _trie.pleaseMakeThisKosher(input[c]);
		}

		s = input[input.length - 1];

		if (_trie.isWord(s)) _completions.put(s,s); //look for exact matches
		if (prefix) this.prefixMatch(s);
		if (whitespace) this.whitespaceMatch(s);
		if (led > 0) this.getLevenshteinMatch(s,led);

		SingleSuggestion[] a_return = new SingleSuggestion[_completions.size()];
		int index = 0;

		for (String word: _completions.values()) {

		    String ss_word = word;

		    String[] word_test = word.split(" ");
		    String unigramWord = word;

		    if (word_test.length > 1) {
		      word = word_test[word_test.length - 1];
		      unigramWord = word_test[word_test.length - 2];
		    }

		    boolean exactMatch = false;
		    double bigramCount = 0.0;

		    double unigramCount = _trie.lastNodeInWord(unigramWord).getCount();
		    if (input.length > 1) {
			String prev = input[input.length - 2];
			TrieNode wordNode = _trie.lastNodeInWord(word);

			String preString = "";
			for (int f = 0;f < input.length - 1; f++) {
			  preString = preString + " " + input[f];
			}
			ss_word = preString + " " + ss_word; //string to be suggested

			bigramCount = wordNode.getBigramCount(prev);
		    }
 		    if (word.compareTo(s) == 0) exactMatch = true;

 		    SingleSuggestion ss = new SingleSuggestion(ss_word, s, bigramCount, unigramCount, total_words, exactMatch);
 		    a_return[index] = ss;
 		    index++;

 		}
		
		_completions = new HashMap<String,String>(); //empty completions for next input word

		return this.sortSuggestions(a_return);
	}

	//this method takes in array of SingleSuggestions and sorts them by comparing their ranks
	//it is essentially an implementation of insertion sort
	private String[] sortSuggestions(SingleSuggestion[] ss) {

	    if (ss.length == 0) return (new String[0]);

	    String[] a_return = new String[ss.length]; 
	    a_return[0] = ss[0].getWord();

	    for (int i = 1; i < ss.length; i++) {
    		SingleSuggestion current = ss[i];
    		int j = i - 1;
    		while (j >= 0 && ss[j].getRank() >= current.getRank()) {
    			if (ss[j].getRank() == current.getRank()) {
    				if (this.compareAlphabetically(ss[j],current)) break;
    			}
    			ss[j+1] = ss[j];
    			a_return[j+1] = ss[j].getWord();
    			j = j - 1;
    		}
    		ss[j+1] = current;
    		a_return[j+1] = current.getWord();
	    }
	    a_return[ss.length - 1] = ss[ss.length - 1].getWord();
	    return a_return;
	}

	//in the case of equal rank, this method helps sort them alphabetically and is used in sortSuggestions() above
	private boolean compareAlphabetically(SingleSuggestion ss1, SingleSuggestion ss2) {

	  char[] char_ss1 = ss1.getWord().toCharArray();
	  char[] char_ss2 = ss2.getWord().toCharArray();

	  int length_ss1 = char_ss1.length;
	  int length_ss2 = char_ss2.length;
	  int i = 0;

	  while (i < length_ss1 && i < length_ss2) {
	    if (char_ss1[i] < char_ss2[i]) return false;
	    if (char_ss1[i] > char_ss2[i]) return true; 
	    i++;
	  }
	  return true;
	}
	//this private class houses a word and its rank
	//it assigns the rank by scaling and summing its properties (unigram/bigram probability and whether or not its an exact match)
	private class SingleSuggestion {
	    private String _word;
	    private double _bigramProb;
	    private double _unigram;
	    private double _rank;
	    public SingleSuggestion(String w, String input, double bi, double count, double total_words, boolean exactMatch) {
	    	_word = w;
	    	_bigramProb = bi;
	    	_unigram = count;
	    	_rank = total_words*_bigramProb + 100.0*_unigram;
	    	if (exactMatch) _rank = _rank + Double.POSITIVE_INFINITY; //if exact match, make sure it has highest rank
	    }
	    public String getWord() { return _word; } 
	    public double getRank() { return _rank; }
	} //end private class


} //end Suggestions class