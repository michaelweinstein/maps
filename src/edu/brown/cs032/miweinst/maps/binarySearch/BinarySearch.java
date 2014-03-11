package edu.brown.cs032.miweinst.maps.binarySearch;

import java.io.IOException;

import edu.brown.cs032.miweinst.maps.maps.MapsFile;

/*
 * this class performs binary search on a tsv file 
 * it assumes that the files are formatted like bacon files
 * the search method requires the string to find, the string's
 * index in its line and the target string's index. These elements
 * can be found by asking the BaconFile
 */
public class BinarySearch {

	private MapsFile _file;
	private long _length;
	private int _inlinePosition, _inlineTargetPosition;
	private String _toFind, _target;
	
	public BinarySearch(MapsFile file) {
		_file = file;
		_target = null;
	}
	
	public String search(String toFind, String field, String targetField) {
		/*
		 *  determines what we are looking for and makes call to recursive
		 *  searchHelper() to find what we are looking for
		 */
		_toFind = toFind;
		_inlinePosition = _file.getFieldIndex(field);
		_inlineTargetPosition = _file.getFieldIndex(targetField);
		
		try {
			
			_length = _file.length();
			this.searchHelper(0,_length);
			
			if (_target != null) { //reset target for next binSearch and return
				String buffer = _target;
				_target = null;
				return buffer;
			}
			
			else {
				System.out.println("ERROR: " + _toFind + " not found in " + _file.getFilePath());
				System.exit(0);
			}
		}
		catch (IOException e) {
			System.out.println("ERROR: I/O error in BinarySearch search().");
			System.exit(0);
		}
		return null;
	} //end search()

	public void searchHelper(long start, long end) {
		/*
		 *  binary search on BaconFile's bytes. Looks for _toFind
		 *  and then returns the string stored in the inlineTargetPosition
		 */
		long mid = (start + end)/2;
		try {
			if (start == end - 2) return; //reached end of file, _target will not be set

			_file.seek(mid);
			
			String line = _file.readNextLine();
			
			if (line == null) return;

			//System.out.println(line);
			String[] curr_line = line.split("\t");
			System.out.println("-------------");
			for (int i = 0; i < curr_line.length; i++) {
				System.out.println("elt at [" + i + "]: " + curr_line[i]);
			}
			System.out.println("-------------");
			if (curr_line[_inlinePosition].compareTo(_toFind) > 0) {
				searchHelper(start, mid); //look higher
			}
			else if (curr_line[_inlinePosition].compareTo(_toFind) < 0) {
				searchHelper(mid, end); //look lower
			}
			else {
				_target = curr_line[_inlineTargetPosition]; //target found
			}
		}
		catch (IOException e) {
			System.out.println("ERROR: I/O error in BinarySearch searchHelper().");
			System.exit(0);
		}
		catch (StackOverflowError e) { //indicates that we have not found _toFind
			System.out.println("ERROR: " + _toFind + " not found in " + _file.getFilePath());
			System.exit(0);
		}
	} //end searchHelper

	
}
