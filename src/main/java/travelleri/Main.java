package travelleri;

import java.util.Arrays;
import travelleri.domain.NaiveTSP;

public class Main {

    public static void main(String[] args) {
        // int[][] graph = new int[3][3];
        double[][] graph = { { 0, 10, 15, 20 }, 
                             { 10, 0, 35, 25 }, 
                             { 15, 35, 0, 30 }, 
                             { 20, 25, 30, 0 } };

        NaiveTSP tsp = new NaiveTSP(graph);

        // tsp.run();
        System.out.println(tsp.getShortestPathLength());
        System.out.println(Arrays.toString(tsp.getShortestPath()));
    }
}
