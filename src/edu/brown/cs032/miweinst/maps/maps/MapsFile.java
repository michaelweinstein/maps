package edu.brown.cs032.miweinst.maps.maps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
/*
 *  This class adds some maps-specific functionality to 
 *  java's RAF, like a map from the headings to their indices,
 *  and a custom readLine() (it's called readNextLine() in here)
 */
public class MapsFile extends RandomAccessFile {

	private HashMap<String,Integer> _headings;
	private long _firstLineBreak, _length;
	private String _filePath;
	
	
	public MapsFile(String name, String mode) throws FileNotFoundException, IOException {
		super(name, mode);
		_firstLineBreak = 0;
		_filePath = name;
		this.setUpHeadingsMap(new HashMap<String,Integer>());
	}
	
	public void setUpHeadingsMap(HashMap<String,Integer> map) throws IOException {
		_headings = map;
		String[] first_line = this.readFirstLine().split("\t");
		for (int i = 0; i < first_line.length; i++) {
			_headings.put(first_line[i], i);
		}
		
	}
	
	public int getFieldIndex(String field) { 
		return _headings.get(field);
	}
	
	public String readNextLine() throws IOException {
	//int 10 indicates a break line
	String returnStr = null;

	if (this.getFilePointer() <= _firstLineBreak || this.getFilePointer() >= _length) {
		return null; //should throw IOException
	}
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
		
	byte[] b = new byte[num_bytes];
	this.read(b); //read the line and then create a string out of its bytes
	returnStr = new String(b,"UTF-8");

	return returnStr;
}
	
	private String readFirstLine() throws IOException {
		
		String returnStr = null;

		_length = this.length();
		this.seek(0); //make sure we are at start of file and read the first line
		int curr_byte = this.read();
		int num_bytes = 0;
		
		while (curr_byte != 10) {	 //find number of bytes in first line
			curr_byte = this.read();
			num_bytes++;
		}
		_firstLineBreak = num_bytes; //to be used in readNextLine() call from binarySearch
		this.seek(0);
			
		byte[] b = new byte[num_bytes];
		this.read(b);
		returnStr = new String(b,"UTF-8");

		return returnStr;
	}
	
	public String getFilePath() { return _filePath; }
	
}
