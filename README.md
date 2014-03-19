maps
====

QUESTION
What do we do with Ways whose source node is getting rendered but its destination node is outside the BoundingBox? Should we add the destination node, or just not draw it?

ARGS
--gui /gpfs/main/home/miweinst/course/cs032/maps/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/ways.tsv /gpfs/main/home/miweinst/course/cs032/maps/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/nodes.tsv /gpfs/main/home/miweinst/course/cs032/maps/src/edu/brown/cs032/miweinst/maps/unit_tests/test_data_files/index.tsv

TODO
Direction of Ways for PathFinding??
Way.hashCode()
App REPL for Street names
	BinaryHelper.findIntersection
Still need to switch from Dijkstra's to A* in PathFinder! 
Street and Cross-Street input in REPL
	BinaryHelper.findIntersection
Create repOK() in BoundingBox to make sure corners not impossible


WRITE TESTS
BoundingBox.contains()
BoundingBox.repOK()
BinaryHelper.findIntersection
App.nearestNeighbor
REPL for lat/lon and street/cross-street (System Tests)


CLEAN UP
Factor out code in App()
Fix KDTree so we don't need to cast and use instanceof
