package edu.brown.cs032.miweinst.maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;

import edu.brown.cs032.miweinst.maps.KDTree.KDComparable;
import edu.brown.cs032.miweinst.maps.KDTree.KDTree;
import edu.brown.cs032.miweinst.maps.KDTree.KDTreeNode;
import edu.brown.cs032.miweinst.maps.KDTree.NeighborSearch;
import edu.brown.cs032.miweinst.maps.autocorrect.Autocorrect;
import edu.brown.cs032.miweinst.maps.autocorrect.DictionaryGenerator;
import edu.brown.cs032.miweinst.maps.graph.Graph;
import edu.brown.cs032.miweinst.maps.graph.GraphEdge;
import edu.brown.cs032.miweinst.maps.graph.GraphNode;
import edu.brown.cs032.miweinst.maps.maps.FileProcessor;
import edu.brown.cs032.miweinst.maps.maps.GUIInfo;
import edu.brown.cs032.miweinst.maps.maps.MapNode;
import edu.brown.cs032.miweinst.maps.maps.MapsFile;
import edu.brown.cs032.miweinst.maps.maps.Way;
import edu.brown.cs032.miweinst.maps.maps.frontend.AutocorrectConnector;
import edu.brown.cs032.miweinst.maps.maps.frontend.GUIFrame;
import edu.brown.cs032.miweinst.maps.maps.path.BinaryHelper;
import edu.brown.cs032.miweinst.maps.maps.path.PathFinder;
import edu.brown.cs032.miweinst.maps.util.BoundingBox;
import edu.brown.cs032.miweinst.maps.util.LatLng;

public class App {

	private static FileProcessor _fp = null;
	private static Autocorrect _autocorrect = null;
	
	private BoundingBox _boundingBox;

	public App(String[] args) {
		//EDGE CASE: wrong number of args; exits
		if (args.length < 3 || args.length > 4) {
			System.out.println("ERROR: Expecting 3 or 4 args -- [--gui] ways nodes index");
			System.exit(0);
		}
		//get default file paths (valid if args.length == 3)
		String waysPath = args[0];
		String nodesPath = args[1];
		String indexPath = args[2];
		

////TESTING boxes
		//create default BoundingBox
		_boundingBox = new BoundingBox(new LatLng(40.2720844,	-73.7175365), new LatLng(40.1615135, -73.6937661));	

//// THROWS ArrayOutOfBoundsException: 1
		//square BoundingBox
//		_boundingBox = new BoundingBox(new LatLng(40.3,	-71.8), new LatLng(40.0, -71.5));
//// THROWS Java OutOfMemoryError: Java heap space
//		_boundingBox = new BoundingBox(new LatLng(42.3734759, -73.5618164), new LatLng(40.0, -71.4));
		
		//if gui, set boolean and get different file paths
		boolean gui = false;
		if (args.length == 4) {
			if (args[0].equals("--gui")) {
				gui = true;		
				waysPath = args[1];
				nodesPath = args[2];
				indexPath = args[3];
///// print lines
				System.out.println(waysPath);
				System.out.println(nodesPath);
				System.out.println(indexPath);
			}
		else 
				System.out.println("ERROR: If 4 arguments, first argument must be '--gui'");
		}

		//make MapsFile, set BinaryHelper files and create dictionary for autocorrect
		try {
			MapsFile ways = new MapsFile(waysPath);
			MapsFile nodes = new MapsFile(nodesPath);
			MapsFile index = new MapsFile(indexPath);
			//set all references to data files
			BinaryHelper.setFiles(ways, nodes, index);
			new DictionaryGenerator(ways);
			_fp = new FileProcessor(nodes, index, ways);
		}
		//EDGE CASE: File paths not valid; exits program
		catch (FileNotFoundException e) {
			System.out.println("ERROR: File paths not valid!");
			System.exit(0);
		}
		//EDGE CASE: IOException when reading files; exits program
		catch (IOException e) {
			System.out.println("ERROR: IOException in App constructor");
			System.exit(0);
		}

		//if args.length == 4 && args[0].equals("--gui")
		if (gui) {
			try {
				this.handleGUI();
			}
			catch (IOException e) {
				System.out.println("ERROR: IO Exception while trying to setup GUI");
			}
		}
		//if args.length == 3 (otherwise it would've been caught at top of constructor)
		else {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			if (br != null) {
				boolean runloop = true;
				while (runloop) {
					try {
						String line = br.readLine();
						//EDGE CASE: empty user input
						if (line.trim().isEmpty()) {
							runloop = false;
							System.out.println("ERROR: " + "Console input empty -- program exiting");
						}
						else {
							String[] input = line.split(" ");
							if (input.length == 4) {
								boolean streetNames = false;
								//try input 'lat1 lon1 lat2 lon2'
								try {
									double lat1 = Double.parseDouble(input[0]);
									double lng1 = Double.parseDouble(input[1]);
									double lat2 = Double.parseDouble(input[2]);
									double lng2 = Double.parseDouble(input[3]);
									//get nearest node to each LatLng
									MapNode srcNode = nearestNeighbor(new LatLng(lat1, lng1));
									MapNode dstNode = nearestNeighbor(new LatLng(lat2, lng2));		
									//find path between the two nodes
									Graph<MapNode, Way> g = new Graph<MapNode, Way>();
									ArrayDeque<GraphNode<MapNode>> path = PathFinder.buildGraphFromNames(g, srcNode, dstNode);
									//prints output
									printPath(g, path, srcNode.id, dstNode.id);
								} catch (NumberFormatException e) {
									streetNames = true;
								}
								//input 'street1 cross-street1 street2 cross-street2'
								if (streetNames) {
									String street1 = input[0];
									String c_street1 = input[1];
									String street2 = input[2];
									String c_street2 = input[3];
///////////////						PROCESS STREET NAMES INPUT
								}
							}
							//EDGE CASE: wrong number of args in user input
							else {
								runloop = false;
								System.out.println("ERROR: " + "Expecting 4 args (lat1 " + 
										"lon1 lat2 lon2 OR 'Street 1' 'C-Street 1' 'Street 2' 'C-Street 2'");
							}
						}
					} catch (IOException e) {
						System.out.println("ERROR: " + "Console input cannot be read");
					}
				}	
			}
		}
	}

	/**
	 * Wrapper method for getting the nearest neighbor to a LatLng. 
	 * Used to find nearest nodes to LatLng inputted by user.
	 */
	public static MapNode nearestNeighbor(LatLng latlng) throws IOException {
		if (_fp != null) {
			MapNode[] kdnodes_arr = _fp.nodesArrayForKDTree();
			KDTree tree = new KDTree(kdnodes_arr);
			NeighborSearch ns = new NeighborSearch(latlng);
			KDTreeNode[] neighbor_arr = ns.nearestNeighbors(tree.getRoot());
			KDComparable mapnode = neighbor_arr[0].getComparable();

/////////	WE SHOULDN'T USE INSTANCEOF; NEED A NEW WAY TO GET MAPNODE
			if (mapnode instanceof MapNode) {
				return (MapNode) mapnode;
			}
			else {
				System.out.println("KDTreeNode.getComparable is NOT a MapNode (App.nearestNeighbor)");
				return null;
			}
		}
		else {
			System.out.println("This shouldn't be happening! (App.nearestNeighbor)");
			return null;
		}
	}

	/**
	 * Console output according to assignment specifications.
	 */
	public static void printPath(Graph<MapNode, Way> g, ArrayDeque<GraphNode<MapNode>> path, String srcId, String dstId) {
		if (path != null) {
			if (path.size() > 1) {
				GraphNode<MapNode> n1 = path.pop();
				GraphNode<MapNode> n2 = path.pop();
				GraphEdge<Way> edge = g.getEdge(n1, n2);
				while (!path.isEmpty()) {				
					//print line
					System.out.println(n1.getElement().id + " -> " + n2.getElement().id + 
							" : " + edge.getElement().id);					
					n1 = n2;
					n2 = path.pop();			
					edge = g.getEdge(n1, n2);
				}
				edge = g.getEdge(n1, n2);
				//print line
				System.out.println(n1.getElement().id + " -> " + n2.getElement().id + 
						" : " + edge.getElement().id);
			}
			//EDGE CASE: no path between nodes was found
			else 
				System.out.println(srcId + " -/- " + dstId);
		}
		//EDGE CASE: node ids could not be found in PathFinder
		else 
			System.out.println("ERROR: " + "One of the node IDs cannot be found.");
	}
	
	private void handleGUI() throws IOException {
		String filepath = System.getProperty("user.dir") + 
				  "/src/edu/brown/cs032/miweinst/maps/autocorrect/autocorrect_dictionary.txt";
		//PUT THE FOLLOWING TWO LINES INTO SEPARATE THREADS
		_autocorrect = Autocorrect.makeAutocorrect(filepath);
		if (_fp != null) {
//////
			GUIInfo gui = new GUIInfo(_fp, _boundingBox);
//			gui.updateBounds(_fp, _boundingBox);
//			MapNode[] nodesForGUI = gui.nodesForGUI(_fp, _boundingBox);
			
			HashMap<String,String> valid_ways = _fp.getWays();				
			GUIFrame guiFrame = new GUIFrame(gui, new AutocorrectConnector(_autocorrect, valid_ways));
		} else 
			System.out.println("ERROR: " + "FileProcessor is null in App (App.handleGUI)");
	}
	
} //end class

