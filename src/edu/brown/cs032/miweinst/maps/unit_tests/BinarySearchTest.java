package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.binarySearch.BinarySearch;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;

public class BinarySearchTest {

	@Test
	public void test() {
		boolean pass = false;
		try {
			MapsFile index = new MapsFile(System.getProperty("user.dir") + "/src/edu/brown/cs032/miweinst/maps/test/test_data_files/index.tsv");
			BinarySearch bs = new BinarySearch(index);
			
			String nodes1 = "/n/4182.7140.1554255283,/n/4182.7140.1554255287," +
						   "/n/4182.7140.1554255263,/n/4182.7140.1554255250," + 
						   "/n/4182.7140.1554255283";
			
			String nodes2 = "/n/4166.7126.201011755,/n/4167.7126.201049984,/n/4167.7126.201049987";
			
			String lastLine = "/n/4132.7181.201400491,/n/4132.7181.201410028,/n/4132.7181.201410033";
			
			String firstEntry = "/n/4188.7152.1554862524,/n/4188.7152.1554862519," +
					  			"/n/4188.7152.1554862515,/n/4188.7152.1554862499," +
					  			"/n/4188.7152.1554862489,/n/4188.7152.1554862501," +
					  			"/n/4188.7152.1554862508,/n/4188.7152.1554862520," +
					  			"/n/4188.7152.1554862524";
			
			if (bs.search("111 Brown Street","name","nodes").compareTo(nodes1) == 0 &&
				bs.search("1st School Street","name","nodes").compareTo(nodes2) == 0 &&
				bs.search("3rd Street","name","nodes").compareTo(lastLine) == 0 &&
				bs.search("#19","name","nodes").compareTo(firstEntry) == 0)
			{
				pass = true;
			}
		} catch (IOException e) {
			System.out.println("IOException");
			System.exit(0);
		}
		assertTrue(pass);
	}

}
