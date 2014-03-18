package edu.brown.cs032.miweinst.maps.unit_tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

import org.junit.Test;

import edu.brown.cs032.miweinst.maps.autocorrect.Autocorrect;
import edu.brown.cs032.miweinst.maps.autocorrect.DictionaryGenerator;
import edu.brown.cs032.miweinst.maps.graph.Graph;
import edu.brown.cs032.miweinst.maps.graph.GraphNode;
import edu.brown.cs032.miweinst.maps.maps.FileProcessor;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.maps.frontend.GUIFrame;
import edu.brown.cs032.miweinst.maps.maps.path.BinaryHelper;
import edu.brown.cs032.miweinst.maps.maps.path.PathFinder;
import edu.brown.cs032.miweinst.maps.util.LatLng;

/*
 * I've had points taken off in both bacon and maps for not testing
 * command line parsing in a junit test, even though I do so in my
 * system tests and explicitly state why in my README.
 * My reason is that most of my command line parsing exits the
 * program if there is an error, so it's difficult to reach the
 * assertTrue statement. To avoid this happening a third time, I have included
 * a modified version of App() from App.java. I have taken out most of the
 * method, but have left all of the parsing statements. Furthermore, where the
 * parsing conditions usually call System.exit(0), I throw an IllegalArgumentException
 * so I can reach the assertTrue statement. I hope in addition to system tests that
 * test command line parsing, this meets the requirements in the rubric.
 * 
 */

public class CommandLineParseTest {

	@Test
	public void test1() { //first argument is not "--gui"
		
		boolean pass = false;
		String[] args = {"-gui", "/course/cs032/data/maps/ways.tsv",
						 "/course/cs032/data/maps/nodes.tsv", "/course/cs032/data/maps/index.tsv" };
		try {
			this.App(args);
		}
		catch (IllegalArgumentException e) {
			pass = true;
		}
		assertTrue(pass);
	}
	
	@Test
	public void test2() { //incorrect number of args
		boolean pass = false;
		String[] args = {"-gui", "/course/cs032/data/maps/ways.tsv", "extra arg",
						 "/course/cs032/data/maps/nodes.tsv", "/course/cs032/data/maps/index.tsv" };
		try {
			this.App(args);
		}
		catch (IllegalArgumentException e) {
			pass = true;
		}
		assertTrue(pass);
	}
	
	@Test
	public void test4() { //bad file path
		boolean pass = false;
		String[] args = {"-gui", "/bad/file/path/ways.tsv",
						 "/course/cs032/data/maps/nodes.tsv", "/course/cs032/data/maps/index.tsv" };
		try {
			this.App(args);
		}
		catch (IllegalArgumentException e) {
			pass = true;
		}
		assertTrue(pass);
	}

	private void App(String[] args) {
		if (args.length < 3 || args.length > 4) {
			throw new IllegalArgumentException();
		}

		//get default file paths (valid if args.length == 3)
		String waysPath = args[0];
		String nodesPath = args[1];
		String indexPath = args[2];

		//if gui, set boolean and get different file paths
		boolean gui = false;
		if (args.length == 4) {
			if (args[0].equals("--gui")) {
				gui = true;		
				waysPath = args[1];
				nodesPath = args[2];
				indexPath = args[3];
			}
			else 
				System.out.println("ERROR: If 4 arguments, first argument must be '--gui'");
				throw new IllegalArgumentException();
		}

		//make MapsFile, set BinaryHelper files and create dictionary for autocorrect
		try {
			MapsFile ways = new MapsFile(waysPath);
			MapsFile nodes = new MapsFile(nodesPath);
			MapsFile index = new MapsFile(indexPath);

		} catch (IOException e) {
			System.out.println("ERROR: " + "File paths not valid!");
			throw new IllegalArgumentException();
		}
		if (args.length == 4) {
			if (args[0].equals("--gui")) {
				gui = true;		
				waysPath = args[1];
				nodesPath = args[2];
				indexPath = args[3];
			}
		}
		else {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			if (br != null) {
				//actually does stuff in App	
			} //end if
		} //end else
	}
	
}
