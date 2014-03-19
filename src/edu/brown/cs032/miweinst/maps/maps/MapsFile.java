package edu.brown.cs032.miweinst.maps.maps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
/*
 *  This class adds some maps-specific functionality to 
 *  java's RAF, like a map from the headings to their indices,
 *  and a custom readLine() (it's called readNextLine() in here)
 */
public class MapsFile extends RandomAccessFile {

	private HashMap<String,Integer> _headings;
	private long _length;
	private String _filePath, _lastLine;
	
	
	public MapsFile(String name) throws FileNotFoundException, IOException {
		super(name, "r");
		_filePath = name;
		_length = this.length();
		this.setUpHeadingsMap(new HashMap<String,Integer>());
		_lastLine = this.readLastLine();
	}
	
	/*
	 * creates map of headings to their indices
	 */
	public void setUpHeadingsMap(HashMap<String,Integer> map) throws IOException {
		_headings = map;
		String[] first_line = this.readFirstLine().split("\t");
		for (int i = 0; i < first_line.length; i++) {
			_headings.put(first_line[i], i);
		}		
	}
	/*
	 * public access to index of heading 
	 */
	public int getFieldIndex(String field) { 
		if (!_headings.containsKey(field)) {
			System.out.println("ERROR: " + _filePath + " does not contain header '" + field + "'");
			System.exit(0);
		}
		return _headings.get(field);
	}
	
	/*
	 * based on the current file pointer, we
	 * return the next line. If we point to the
	 * middle of a line, we read the line after 
	 */	
	public String readNextLine() throws IOException {

	  String returnStr = null;
	  try {
		  
		this.findNextBreakLine();
		
		boolean line_end = false;
		
		long start_line = this.getFilePointer(); 
		
		//read the file with byte arrays until we find the next break line
		ArrayList<byte[]> byte_arrays = new ArrayList<byte[]>();
		int index = 0;
		int default_size = 300;
		while (!line_end) {					
			byte[] b = new byte[default_size];
			if (this.read(b) == -1) { //encountered EOF, read last line
				return readLastLine();
			}
			byte_arrays.add(b); //add the array to our list of arrays
			for (int i = 0; i < b.length; i++) { //increment index until breakline
				if (b[i] == 10) {
					line_end = true;
					break;
				}
				index++;
			}
		}
		
		//add the contents of our arrays into one larger array (up until breakline)
		byte[] b = new byte[index];
		int count = 0;
		outer: for (byte[] arr: byte_arrays) {
			for (int i=0; i < arr.length; i++) {
				if (count >= index) {
					break outer;
				}
				b[count] = arr[i];
				count++;
			}
		}
		//set file pointer to start of line and create a string out of the following bytes
		this.seek(start_line);
		this.read(b);
		returnStr = new String(b,"UTF-8");
	  }
	  catch(OutOfMemoryError e) {
		  System.out.println("EOFException");
	  }
		return returnStr;
	}
	
	/*
	 * based on the current file pointer location,
	 * we find the nearest breakline approaching
	 * the end of the file 
	 */
	private void findNextBreakLine() throws IOException {

		boolean new_line = false;
		while (!new_line) {
			byte[] curr_bytes = new byte[10];
			
			this.read(curr_bytes);

			for (int i = 0; i < curr_bytes.length; i++) {
								
				if (curr_bytes[i] == 10) {
					new_line = true;
					this.seek(this.getFilePointer() - 10 + i + 1);
					break;
				} //end if
//////	MICHAEL ADDED THIS TO FIX BUG WHEN READING LAST LINE
		/* But I think this causes the last line to be read twice...*/
				else if (curr_bytes[i] == 0) {
					new_line = true;
				}
			} //end for			
		}
	}

	/*
	 * this reads byte-by-byte from disk, but since we only
	 * run it once (and cache result), it will not 
	 * noticeably affect our runtime
	 */
	public String readLastLine() throws IOException {
		
		String returnStr = null;
	
		this.seek(this.length() - 2);
		
		int curr_byte = this.read();
		
		while (curr_byte != 10) {	//set file pointer to previous break line
			this.seek(this.getFilePointer() - 2);
			curr_byte = this.read();
		}
		while (curr_byte == 10) { //find next byte that isn't a break line (this should almost always just run once)
			curr_byte = this.read();
		}
	
		int num_bytes = 0;
		//find number of bytes in line by looking until next break line or end of file
		while (curr_byte != 10 && this.getFilePointer() < _length) {
			curr_byte = this.read();
			num_bytes++;
		}
		
		this.seek(this.getFilePointer() - num_bytes - 1); //set the file pointer to start of line
		byte[] b = new byte[num_bytes + 1];
		this.read(b); //read the line and then create a string out of its bytes
		returnStr = new String(b,"UTF-8");
		//System.out.println("LAST LINE: " + returnStr);
		return returnStr;
	}
	
	/*
	 * this reads byte-by-byte from disk, but since we only
	 * run it once, it will not noticeably affect our runtime
	 */
	public String readFirstLine() throws IOException {

		String returnStr = null;

		_length = this.length();
		this.seek(0); //make sure we are at start of file and read the first line
		int curr_byte = this.read();
		int num_bytes = 0;
		
		while (curr_byte != 10) {	 //find number of bytes in first line
			curr_byte = this.read();
			num_bytes++;
		}

		this.seek(0);
			
		byte[] b = new byte[num_bytes];
		this.read(b);
		returnStr = new String(b,"UTF-8");

		return returnStr;
	}


	public String getFilePath() { return _filePath; }
	
	public String getLastLine() { return _lastLine; }
	
}