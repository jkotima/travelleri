package travelleri.domain;

public class NaiveTSP implements TSP {
	private double[][] graph;
	private int n;

	private int[][] permutations;
	private boolean[] included;
	private int[] digits;

	private int[] shortestRoute;
	private double shortestRouteLength;

	private boolean ran;
	
	public NaiveTSP(double[][] graph) {
		this.graph = graph;
		this.n = graph[0].length;

		this.permutations = new int[0][0];
		this.included = new boolean[graph[0].length];
		this.digits = new int[graph[0].length];

		this.shortestRoute = new int[graph[0].length + 1];
		this.shortestRouteLength = Double.MAX_VALUE;
		
		this.ran = false;
	}

	private void makePermutations(int k) {
		if (k == n) {
			int[][] newPermutations = new int[permutations.length + 1][n];
			newPermutations[newPermutations.length - 1] = digits.clone();
			System.arraycopy(permutations, 0, newPermutations, 0, permutations.length);
			permutations = newPermutations.clone();
		} else {
			for (int i = 1; i < n; i++) {
				if (!included[i]) {
					included[i] = true;
					digits[k] = i;
					makePermutations(k + 1);
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
			int[] route = new int[n + 1];
			route[0] = 0;
			int[] permutation = permutations[i];
			
			for (int j = 0; j < permutation.length - 1; j++) {
				totalDistance += graph[permutation[j]][permutation[j + 1]];
				route[j + 1] = permutation[j + 1];
			}
			totalDistance += graph[permutation[permutation.length - 1]][0];

			if (totalDistance < shortestRouteLength) {
				shortestRouteLength = totalDistance;
				shortestRoute = route;
			}
		}
		
		ran = true;
	}
	
	@Override
	public int[] getShortestRoute() {
		if (!ran) run();
		return shortestRoute;
	}
	@Override
	public double getShortestRouteLength() {
		if (!ran) run();
		return shortestRouteLength;
	}
}
