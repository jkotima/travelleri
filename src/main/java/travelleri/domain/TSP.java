package travelleri.domain;

public interface TSP {
    void run();

    int[] getShortestPath();

    double getShortestPathLength();
}
