package travelleri.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class BranchTSPTest {
    @Test
    public void knownGraphTest() {
        double[][] graph = { { 0, 10, 15, 20 }, 
                             { 10, 0, 35, 25 }, 
                             { 15, 35, 0, 30 }, 
                             { 20, 25, 30, 0 } };

        BranchTSP tsp = new BranchTSP(graph);
        assertEquals("Shortest path length is 80.0", 80, tsp.getShortestPathLength(), 1);
        assertArrayEquals("The shortest path is correct", new int[] { 0, 2, 3, 1, 0 }, 
                tsp.getShortestPath());
    }

    @Test
    public void gettersTest() {
        double[][] graph = { { 0, 10 }, { 10, 0 } };
        BranchTSP tsp1 = new BranchTSP(graph);
        tsp1.run();
        assertEquals("Returns right path length after calling run() first", 20, 
                tsp1.getShortestPathLength(), 1);

        BranchTSP tsp2 = new BranchTSP(graph);
        assertEquals("Returns right path length without calling run() first", 20, 
                tsp2.getShortestPathLength(), 1);

        BranchTSP tsp3 = new BranchTSP(graph);
        tsp3.run();
        assertArrayEquals("Returns right path after calling run() first", new int[] { 0, 1, 0 },
                tsp3.getShortestPath());

        BranchTSP tsp4 = new BranchTSP(graph);
        assertArrayEquals("Returns right path without calling run() first", new int[] { 0, 1, 0 },
                tsp4.getShortestPath());
    }
    

    @Test(expected = IllegalArgumentException.class)
    public void invalidGraphTest() {
        double[][] graph = { { 10, 0 }, 
                             { 10, 0, 35, 25 }, 
                             {}, 
                             { 20, 25, 30} };
        
        BranchTSP tsp = new BranchTSP(graph);
        tsp.run();
    }
}