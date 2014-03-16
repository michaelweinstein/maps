package edu.brown.cs032.miweinst.maps;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.brown.cs032.miweinst.maps.autocorrect.DictionaryGenerator;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.maps.frontend.GUIFrame;

public class App {
	
	public App(String[] args) {
		//EDGE CASE: wrong number of args; exits
		if (args.length != 3) {
			System.out.println("ERROR: Expecting 3 args -- ways nodes index");
			System.exit(0);
		}
		
		//
//		BinaryHelper.setFiles(args[0], args[1], args[2]);
		
		
		//if (args.contains("--gui")) {
		new GUIFrame();
//		}
		
/// I THINK IT'S FINE, WE JUST NEED TO PASS IN args[2] INSTEAD OF HARDCODING
		/*
		 * The following generates a dictionary to process for the Trie.
		 * It's a hack and should probably be changed in the future.
		 */
		MapsFile index = null;
		try {
			String curr_directory = System.getProperty("user.dir") + 
									"/src/edu/brown/cs032/miweinst/maps";
			index = new MapsFile(curr_directory + "/test/test_data_files/index.tsv");
			new DictionaryGenerator(index);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + "index file path not valid (FileNotFoundException)");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR: " + "index file path not valid (IOException)");
			e.printStackTrace();
		}
	}

}
