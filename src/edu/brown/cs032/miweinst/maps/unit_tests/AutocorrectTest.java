package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.autocorrect.Autocorrect;

public class AutocorrectTest {

	@Test
	public void test() {
		boolean pass = false;
		String[] argv = new String[5];
		argv[0] = "--led";
		argv[1] = "3";
		argv[2] = "--prefix";
		argv[3] = "--whitespace";
		argv[4] = "/course/cs032/data/autocorrect/dictionary.txt";
		Autocorrect a = new Autocorrect(argv);
		String[] suggestions = a.generateSuggestions("alex");
		if (suggestions[suggestions.length - 1].compareTo("aahed") == 0 && 
			suggestions[suggestions.length - 2].compareTo("aah") == 0 &&
			suggestions[suggestions.length - 3].compareTo("aal") == 0 &&
			suggestions[suggestions.length - 4].compareTo("aas") == 0 &&
			suggestions[suggestions.length - 5].compareTo("aa") == 0)
		{
			pass = true;
		}
		assertTrue(pass);
	}

}
