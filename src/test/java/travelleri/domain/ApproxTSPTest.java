package travelleri.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class ApproxTSPTest {
    @Test
    public void knownGraphTest() {
        double[][] graph = { { 0, 10, 15, 20 }, { 10, 0, 35, 25 }, { 15, 35, 0, 30 }, { 20, 25, 30, 0 } };

        ApproxTSP tsp = new ApproxTSP(graph);
        assertEquals("Shortest path length is 80.0", 80, tsp.getShortestPathLength(), 1);
        assertArrayEquals("The shortest path is correct", new int[] { 0, 1, 3, 2, 0 }, tsp.getShortestPath());
    }

    @Test
    public void knownBigGraphApproxTest() {
        double[][] graph = { { 0.0, 8.0, 66.0, 68.0, 84.0, 24.0, 24.0, 28.0, 36.0, 10.0 },
                { 8.0, 0.0, 34.0, 86.0, 91.0, 7.0, 16.0, 76.0, 54.0, 77.0 },
                { 66.0, 34.0, 0.0, 79.0, 14.0, 19.0, 30.0, 92.0, 96.0, 80.0 },
                { 68.0, 86.0, 79.0, 0.0, 95.0, 67.0, 54.0, 64.0, 81.0, 46.0 },
                { 84.0, 91.0, 14.0, 95.0, 0.0, 83.0, 6.0, 87.0, 19.0, 56.0 },
                { 24.0, 7.0, 19.0, 67.0, 83.0, 0.0, 96.0, 6.0, 1.0, 23.0 },
                { 24.0, 16.0, 30.0, 54.0, 6.0, 96.0, 0.0, 7.0, 37.0, 40.0 },
                { 28.0, 76.0, 92.0, 64.0, 87.0, 6.0, 7.0, 0.0, 80.0, 27.0 },
                { 36.0, 54.0, 96.0, 81.0, 19.0, 1.0, 37.0, 80.0, 0.0, 12.0 },
                { 10.0, 77.0, 80.0, 46.0, 56.0, 23.0, 40.0, 27.0, 12.0, 0.0 } };
        ApproxTSP tsp = new ApproxTSP(graph);

        assertEquals("Shortest path length is around 199 with +-20% error", 199, tsp.getShortestPathLength(), 40);
    }

    @Test
    public void gettersTest() {
        double[][] graph = { { 0, 10 }, { 10, 0 } };
        ApproxTSP tsp1 = new ApproxTSP(graph);
        tsp1.run();
        assertEquals("Returns right path length after calling run() first", 20, tsp1.getShortestPathLength(), 1);

        ApproxTSP tsp2 = new ApproxTSP(graph);
        assertEquals("Returns right path length without calling run() first", 20, tsp2.getShortestPathLength(), 1);

        ApproxTSP tsp3 = new ApproxTSP(graph);
        tsp3.run();
        assertArrayEquals("Returns right path after calling run() first", new int[] { 0, 1, 0 },
                tsp3.getShortestPath());

        ApproxTSP tsp4 = new ApproxTSP(graph);
        assertArrayEquals("Returns right path without calling run() first", new int[] { 0, 1, 0 },
                tsp4.getShortestPath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidGraphTest() {
        double[][] graph = { { 10, 0 }, { 10, 0, 35, 25 }, {}, { 20, 25, 30 } };

        ApproxTSP tsp = new ApproxTSP(graph);
        tsp.run();
    }
}
