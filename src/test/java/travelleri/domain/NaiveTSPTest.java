package travelleri.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class NaiveTSPTest {
	@Test
	public void testWithKnownGraph() {
		double[][] graph = { { 0, 10, 15, 20 }, { 10, 0, 35, 25 }, { 15, 35, 0, 30 }, { 20, 25, 30, 0 } };
		NaiveTSP tsp = new NaiveTSP(graph);
		assertEquals("Shortest route length is 80.0", 80, tsp.getShortestRouteLength(), 1);
		assertArrayEquals("The shortest route is correct", new int[] {0, 1, 3, 2, 0}, tsp.getShortestRoute());
	}
}
