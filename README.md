maps
====

RUNNING
Run our program from the top directory. There is a bash script file in bin. Run:
./bin/maps <args>

To run our system tests:
ant system_test_miweinst
-- BUG: Every time we try to run our unit tests, we get a "Crashed on start up" response from ant system_test_miweinst script! We tested all our system tests manually through the console, but this scrpit was not working.

To run our unit tests:
ant unit_test

CONTROLS
Ctrl+click: set start or end of path
Drag: pan
Scroll: zoom
Ctrl+Scroll: isomorphic zoom

KNOWN BUGS
- Our findIntersection method may not read in all the nodes of a given street if there are rows with the same name. It uses binary search, and only reads in the rows after the one it first finds. We did not have time to code readLineBefore
- Slow at getting all the nodes within a large bounding box

FRONT END AND BACK END
The two objects which communicate between front end and back end are AutocorrectConnector and GUIInfo. GuiInfo is passed from App down to DrawingPanel, and can forward methods to the back end. The front end (panning, zooming, drawing a path) is all taken care of in DrawingPanel, but when it needs information, it makes a call using _guiInfo to the backend on a separate thread.

OPTIMIZATION
We cached any Way that we have already read in GUIInfo so we would have to binary search less
We run panning, zooming and pathfinding in separate threads from the GUI


AUTOCORRECT
Autocorrect gets the user's input, and displays up to 5 *valid* suggestions from the Trie. A valid suggestion is one that is in the Trie and is also a valid way from the ways file. This has its own generic package, and then specific implementation within the maps package. 

KDTREE
Written generically in its own package, we use the KDTree to find nearestNeighbor when the user clicks or enters LatLng. Also have find nearest neighbors within radius, in combination with predictive paging, to find the nodes/ways within the BoundingBox to send to the front end and render.

PATHFINDING
Builds the graph and runs A* in the same algorithm. Uses a heuristic that measures the euclidean distance from a node to the destination node of the graph.

THREADING
Panning and Zooming back-end have a thread
Pathfinding has a thread
Autocorrect does not need one, it's very responsive/fast


