maps
====

ARGS
--gui /gpfs/main/home/miweinst/course/cs032/maps/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/ways.tsv /gpfs/main/home/miweinst/course/cs032/maps/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/nodes.tsv /gpfs/main/home/miweinst/course/cs032/maps/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv

---- TODO ----
GUI
	- Panning
		-- Fix/Finish DrawingPanel.endPan() 
		-- Need to put some bounds on how far you can pan
	- Zooming
	- GUIInfo
		-!- MUST FIX scaling problem with _scale
		-- Needs reference to size of DrawingPanel
	- Clicking on map and finding nearest intersection/node
	- Highlighting relevant street segments
Console.io
	- Street/Cross-Street name inputs in REPL
		-- BinaryHelper.findIntersection
Threading
Still need to switch from Dijkstra's to A* in PathFinder! 	
Holy Trinity: complete in LatLng, Vec2d, Way, MapNode
	- hashCodes
		-- Write Way.hashCode()
		-- Fix LatLng.hashCode()


---- BUGS ----
getNodesForGUI: ArrayOutOfBoundsException
	- Trigger: Pan heavily; drag screen down as far as possible
	- Trigger: One BoundingBox instantiation in App
getNodesForGUI: OutOfMemoryError
	- Trigger: One BoundingBox instantiation in App
QUESTION
	What do we do with Ways whose source node is getting rendered but its destination node is outside the BoundingBox? Should we add the destination node, or just not draw it?


-- WRITE TESTS --
BoundingBox.contains()
BoundingBox.repOK()
BinaryHelper.findIntersection
App.nearestNeighbor
REPL for lat/lon and street/cross-street (System Tests)
Holy Trinity in LatLng, Vec2d, Way, MapNode


---------AUTOCORRECT----------
Autocorrect gets the user's input, and displays up to 5 *valid* suggestions from the Trie.
A valid suggestion is one that is in the Trie and is also a valid way from the ways file. The


