package travelleri.domain;

import static org.junit.Assert.*;

import java.util.Random;
import org.junit.Test;


public class TSPTest {
    private static double[][] generateRandomGraph() {
        Random r = new Random();
        int nodesCount = r.nextInt(6 - 2 + 1) + 2;

        double[][] randomGraph = new double[nodesCount][nodesCount];

        for (int x = 0; x < nodesCount; x++) {
            for (int y = 0; y < nodesCount; y++) {
                if (x == y) {
                    randomGraph[x][y] = 0;
                    continue;
                }
                if (randomGraph[y][x] != 0) {
                    randomGraph[x][y] = randomGraph[y][x];
                    continue;
                }

                randomGraph[x][y] = 100 * r.nextDouble();
            }
        }

        return randomGraph;
    }

    @Test
    public void sameLengthsWithRandomGraph() {
        double[][] graph = generateRandomGraph();

        NaiveTSP ntsp = new NaiveTSP(graph);
        BranchTSP btsp = new BranchTSP(graph);
        DynamicTSP dtsp = new DynamicTSP(graph);

        assertEquals("Path lenght is the same with NaiveTSP and BranchTSP", 
                        ntsp.getShortestPathLength(), btsp.getShortestPathLength(), 1);
        assertEquals("Path lenght is the same with NaiveTSP and DynamicTSP", 
                        ntsp.getShortestPathLength(), dtsp.getShortestPathLength(), 1);
    }
}