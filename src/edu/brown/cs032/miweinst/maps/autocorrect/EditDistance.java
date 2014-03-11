package edu.brown.cs032.miweinst.maps.autocorrect;

public class EditDistance {
	
	/** Returns levenshtein edit distance between two strings, 
	 * where each operation has a cost of 1. 
	 * Using dynamic programming method, creating matrix of edit
	 * costs between two words and finding least cost path. */
	public static int findEditDistance(String str1, String str2) {
		str1 = str1.toLowerCase();
		str2 = str2.toLowerCase();	
		//create matrix and find 
		int[][] costs = new int[str1.length()+1][str2.length()+1];		
		//base case: populate first row/col
		for (int i=0; i<str1.length()+1; i++) 
			costs[i][0] = i;		
		for (int j=1; j<str2.length()+1; j++) 
			costs[0][j] = j;		
		//populate distance/cost matrix
		for (int i=1; i<str1.length()+1; i++) 
			for (int j=1; j<str2.length()+1; j++) 			
				if (str1.charAt(i-1) == str2.charAt(j-1)) 
					costs[i][j] = costs[i-1][j-1];
				else 
					costs[i][j] = 1 + minimum(costs[i][j-1], costs[i-1][j], costs[i-1][j-1]);						
//		printMatrix(costs);			
		//edit distance is last unit in matrix
		return costs[str1.length()][str2.length()];
	}
	
	//HELPER: just extends Math's min to find minimum of three ints
	public static int minimum(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}
}
