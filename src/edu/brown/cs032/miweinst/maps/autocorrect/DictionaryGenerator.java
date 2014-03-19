package edu.brown.cs032.miweinst.maps.autocorrect;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import edu.brown.cs032.miweinst.maps.maps.MapsFile;

public class DictionaryGenerator {

	private static MapsFile _waysFile;
	
	public DictionaryGenerator(MapsFile f) {
		_waysFile = f;
		generateDictionary();
	}

	private static void generateDictionary() {
		/*
		 * read each line (intended for use on index.tsv) and
		 * print elements in name column into a text file in autocorrect 
		 * directory. Text file will be used to populate our Trie, which 
		 * we will generate suggestions from. It will be overwritten every
		 * time we run this method.
		 */
		try {
			String dir = System.getProperty("user.dir") + 
					"/src/edu/brown/cs032/miweinst/maps/autocorrect/";
			PrintWriter writer = new PrintWriter(dir + "autocorrect_dictionary.txt", "UTF-8");
			
			int idIndex = _waysFile.getFieldIndex("id");
			int nameIndex = _waysFile.getFieldIndex("name");
			String[] last_line = _waysFile.readLastLine().split("\t");
			String last_id = last_line[idIndex];
			_waysFile.readFirstLine(); //don't add headings to dictionary
			String[] curr = _waysFile.readNextLine().split("\t");
			String id = curr[idIndex];
			while (id.compareTo(last_id) != 0) {
				String name = curr[nameIndex];
				if (!name.isEmpty()) writer.println(name);
				curr = _waysFile.readNextLine().split("\t");
				id = curr[idIndex];
			}
			if (!last_line[nameIndex].isEmpty()) writer.println(last_line[nameIndex]);
			
			
			
			
			/*
			String line = _file.readLine();
			while (line != null) {
				String[] line_array = line.split("\t");
				String s = line_array[_file.getFieldIndex("name")];
				line = _file.readLine(); //read the next line after to avoid null pointer
				writer.println(s);
			}
			*/
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
