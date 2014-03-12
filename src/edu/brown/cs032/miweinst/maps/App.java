package edu.brown.cs032.miweinst.maps;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.brown.cs032.miweinst.maps.autocorrect.DictionaryGenerator;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.maps.frontend.GUIFrame;

public class App {
	
	public App(String[] args) {
		
		//if (args.contains("--gui")) {
		new GUIFrame();
//		}
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
