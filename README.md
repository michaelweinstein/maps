maps
====

ARGS
--gui /gpfs/main/home/miweinst/course/cs032/maps/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/ways.tsv /gpfs/main/home/miweinst/course/cs032/maps/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/nodes.tsv /gpfs/main/home/miweinst/course/cs032/maps/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv

---- TODO ----
GUI - Put bounds on how far you can pan and zoom
Intersection for autocorrect
Fix findIntersection -- need to read backwards too
Fix readLine
Thread pathfinding()

GUIInfo
	- Needs reference to size of DrawingPanel
Holy Trinity: complete in LatLng, Vec2d, Way, MapNode
	- hashCodes
		-- Write Way.hashCode()
		-- Fix LatLng.hashCode()


-- WRITE TESTS --
BoundingBox.contains()
BoundingBox.repOK()
BinaryHelper.findIntersection
App.nearestNeighbor
REPL for lat/lon and street/cross-street (System Tests)
Holy Trinity in LatLng, Vec2d, Way, MapNode


---------AUTOCORRECT----------
Autocorrect gets the user's input, and displays up to 5 *valid* suggestions from the Trie.
A valid suggestion is one that is in the Trie and is also a valid way from the ways file. 

Panning and Zooming back-end have a thread
Pathfinding has a thread
Autocorrect does not need one, it's very responsive/fast


