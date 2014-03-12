package edu.brown.cs032.miweinst.maps.autocorrect;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import edu.brown.cs032.miweinst.maps.maps.MapsFile;

public class DictionaryGenerator {

	private static MapsFile _file;
	
	public DictionaryGenerator(MapsFile f) {
		_file = f;
		generateDictionary();
	}

	private static void generateDictionary() {
		/*
		 * read each line (intended for use on index.tsv) and
		 * print elements in name column into a text file in autocorrect 
		 * directory. Text file will be used to populate our Trie, which 
		 * we will generate suggestions from
		 */
		try {
			String dir = System.getProperty("user.dir") + 
					"/maps/src/edu/brown/cs032/miweinst/maps/autocorrect/";
			PrintWriter writer = new PrintWriter(dir + "autocorrect_dictionary.txt", "UTF-8");
			_file.seek(0);
			_file.readLine(); //don't add headings to dictionary
			String line = _file.readLine();
			while (line != null) {
				String[] line_array = line.split("\t");
				String s = line_array[_file.getFieldIndex("name")];
				line = _file.readLine(); //read the next line after to avoid null pointer
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException");
		} catch (IOException e) {
			System.out.println("IOException");
		}
	}
	
	
}
