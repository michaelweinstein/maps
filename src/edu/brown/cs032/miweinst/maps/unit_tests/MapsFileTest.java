package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.maps.MapsFile;

public class MapsFileTest {

	@Test
	public void testHeadings() {
		boolean pass = false;
		try {
			MapsFile ways = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/ways.tsv");
			if (ways.getFieldIndex("id") == 0 &&
					ways.getFieldIndex("name") == 1 &&
					ways.getFieldIndex("version") == 2 &&
					ways.getFieldIndex("changeset") == 3 &&
					ways.getFieldIndex("uid") == 4 &&
					ways.getFieldIndex("user") == 5 &&
					ways.getFieldIndex("timestamp") == 6 &&
					ways.getFieldIndex("start") == 7 &&
					ways.getFieldIndex("end") == 8)
			{
				pass = true;
			}
			ways.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException");
			System.exit(0);
		}
		assertTrue(pass);
	}
	
	@Test
	public void testReadFirstLine() {
		boolean pass = false;
		try {
			MapsFile index = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv");
			if (index.readFirstLine().compareTo("name	nodes") == 0)
			{
				pass = true;
			}
			index.close();
		} catch (IOException e) {
			System.out.println("IOException");
			System.exit(0);
		}
		assertTrue(pass);
	}
	
	@Test
	public void testReadLastLine() {
		boolean pass = false;
		try {
			MapsFile index = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv");
			String lastLine = "3rd Street	/n/4136.7162.201410042,/n/4136.7162.201410046," +
							  "/n/4136.7162.201410050,/n/4136.7162.201410065,/n/4136.7162.201410067," +
							  "/n/4136.7162.201410070,/n/4136.7162.201410074,/n/4136.7162.201410080,/n/4136.7162.201410082";
			if (index.readLastLine().compareTo(lastLine) == 0)
			{
				pass = true;
			}
			index.close();
		} catch (IOException e) {
			System.out.println("IOException");
			System.exit(0);
		}
		assertTrue(pass);
	}
	

}
