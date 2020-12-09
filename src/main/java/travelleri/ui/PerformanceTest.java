package travelleri.ui;

import java.util.Random;
import travelleri.domain.ApproxTSP;
import travelleri.domain.BranchTSP;
import travelleri.domain.DynamicTSP;
import travelleri.domain.NaiveTSP;
import travelleri.domain.TSP;

public class PerformanceTest {
    private static double[][] generateRandomGraph(int nodesCount) {
        Random r = new Random();

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
                break;
            }
        }

        return randomGraph;
    }

    public static void run(String algorithm, int startNodesCount,
                                    int maxNodesCount, int repeats) {

        System.out.println("**Testataan " + algorithm + " " + startNodesCount + "..."
                            + maxNodesCount + " solmuisella satunnaisverkoilla ("
                            + repeats + " testiä/verkkokoko)**");

        for (int nodesCount = startNodesCount; nodesCount <= maxNodesCount; 
                                nodesCount++) {
                                    
            TSP tsp;
            long t = System.nanoTime();
            long tAcc = 0;

            for (int i = 0; i < repeats; i++) {
                
                double[][] graph = generateRandomGraph(nodesCount);

                switch (algorithm) {
                    case "naive":
                        tsp = new NaiveTSP(graph);
                        break;
                    case "branch":
                        tsp = new BranchTSP(graph);
                        break;
                    case "dynamic":
                        tsp = new DynamicTSP(graph);
                        break;
                    case "approx":
                        tsp = new ApproxTSP(graph);
                        break;
                    default:
                        return;
                }

                t = System.nanoTime();
                tsp.run();
                t = System.nanoTime() - t;

                //System.out.println((i + 1) + ". tulos: " + t);
                tAcc += t;
            }
            
            System.out.println("Suoritusaika " + nodesCount 
                                + " solmuisilla verkoilla keskimäärin: " 
                                + tAcc / repeats + " ns (" 
                                + (double) (tAcc / repeats) / 1000000000 + "s)");
        }
    }
}