package travelleri.domain;

public class NaiveTSP implements TSP {
  private int[][] graph;
  private int n;
  
  private int[][] permutations;
  private boolean[] included;
  private int[] digits;
  
  
  public NaiveTSP(int[][] graph) {
    this.graph = graph;
    this.n = graph[0].length;
    
    this.permutations = new int[0][0];
    this.included = new boolean[graph[0].length];
    this.digits = new int[graph[0].length];
  }
 
  private void makePermutations(int k) {
	  if (k == n) {
		  // "append" found permutation to permutations
		  int[][] newPermutations = new int [permutations.length+1][n]; 
		  newPermutations[newPermutations.length-1] = digits.clone();
		  System.arraycopy(permutations, 0, newPermutations, 0, permutations.length);	  	  
		  permutations = newPermutations.clone();

	  } else {
		  for (int i = 1; i < n; i++) {
			  if (!included[i]) {
				  included[i] = true;
				  digits[k] = i;
				  makePermutations(k+1);
				  included[i] = false;
			  }
		  }
	  }
  }
  
  @Override
  public void run() {
	makePermutations(1);
	for (int i = 0; i < permutations.length; i++) {
		int totalDistance = 0;
		int[] permutation = permutations[i];
		for (int j = 0; j < permutation.length-1; j++) {
			totalDistance += graph[permutation[j]][permutation[j+1]];
			System.out.println(graph[permutation[j]][permutation[j+1]]);
		}
		//back to start:
		totalDistance += graph[permutation[permutation.length-1]][0];
		System.out.println(graph[permutation[permutation.length-1]][0]);
		//print total
		System.out.println("total: " + totalDistance);
		System.out.println("----------");
	}
  }
}
