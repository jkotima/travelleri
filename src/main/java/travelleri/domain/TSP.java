package travelleri.domain;

public interface TSP {
	void run();
	int[] getShortestRoute();
	double getShortestRouteLength();
}
