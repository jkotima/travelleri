package travelleri.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import travelleri.domain.ApproxTSP;
import travelleri.domain.BranchTSP;
import travelleri.domain.DynamicTSP;
import travelleri.domain.NaiveTSP;
import travelleri.domain.TSP;
import travelleri.io.FileIO;

/**
* Verkkojen luomista, avaamista, ajamista varten
*/
public class GraphTool {
    public static void opener(Scanner scan) throws FileNotFoundException {
        System.out.print("Anna avattavan tiedoston nimi: ");
        String filename = scan.nextLine();
        double[][] graph = FileIO.openGraphFromFile(filename);
        System.out.println("Verkko avattu");
        System.out.println("1. aja verkolle naivi algoritmi");
        System.out.println("2. aja verkolle dynaaminen algoritmi");
        System.out.println("3. aja verkolle aproksoiva algoritmi");
        System.out.println("4. aja verkolle branch-and-bound algoritmi");

        while (!scan.hasNextInt())  {
            scan.next();
        }
        int selection = scan.nextInt();
        scan.nextLine();
        TSP tsp;
        switch (selection) {
            case 1:
                tsp = new NaiveTSP(graph);
                break;
            case 2:
                tsp = new DynamicTSP(graph);
                break;
            case 3:
                tsp = new ApproxTSP(graph);
                break;
            case 4:
                tsp = new BranchTSP(graph);;
                break;
            default:
                return;
        }

        runTSP(tsp);
    }

    public static void creator(Scanner scan) throws IOException {
        System.out.print("Solmujen lukumäärä? ");
        while (!scan.hasNextInt()) {
            scan.next();    
        } 
        int nodesCount = scan.nextInt();

        System.out.print("1. Syötä painot käsin 2. Generoi satunnaiset painot"
                        + " 3. Generoi säännöllinen ruudukko ");
        
        while (!scan.hasNextInt()) {
            scan.next();
        }
        int selection = scan.nextInt();

        double[][] newGraph = new double[nodesCount][nodesCount];

        Random r = new Random();

        for (int x = 0; x < nodesCount; x++) {
            for (int y = 0; y < nodesCount; y++) {
                if (x == y) {
                    newGraph[x][y] = 0;
                    continue;
                }

                if (newGraph[y][x] != 0) {
                    newGraph[x][y] = newGraph[y][x];
                    continue;
                }

                switch (selection) {
                    case 1:
                        System.out.print("Kaaren " + (x) + "-" + (y) + " paino? ");
                        while (!scan.hasNextDouble()) { 
                            scan.next();
                        }
                        newGraph[x][y] = scan.nextDouble();
                        break;
                    case 2:
                        newGraph[x][y] = 100 * r.nextDouble();
                        break;
                    case 3:
                        int leveys = (int) Math.sqrt(nodesCount);
                        int x1 = x / leveys;
                        int y1 = x - (x1 * leveys);
                        int x2 = y / leveys;
                        int y2 = y - (x2 * leveys);
                        
                        newGraph[x][y] = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                        
                        break;
                    default:
                        return;
                }
            }
        }



        System.out.println("Luotu verkko: ");
        for (double[] line : newGraph) {
            System.out.println(Arrays.toString(line));
        }
        System.out.println();
        System.out.print("Anna tiedostolle nimi: ");
        scan.nextLine();
        String filename = "";
        while (filename.equals("")) {
            filename = scan.nextLine();
        }
        
        FileIO.saveGraphToFile(filename, newGraph);
    }

    public static void runTSP(TSP tsp) {
        System.out.println("Ajetaan algoritmi...");
        long t = System.nanoTime();
        tsp.run();
        t = System.nanoTime() - t;

        System.out.println("Suoritusaika: " + t + " ns");
        System.out.println("Lyhin polku:");
        System.out.println(Arrays.toString(tsp.getShortestPath()));
        System.out.println("Lyhimmän polun pituus:");
        System.out.println(tsp.getShortestPathLength());
    }
}