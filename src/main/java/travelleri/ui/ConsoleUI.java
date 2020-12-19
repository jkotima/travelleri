package travelleri.ui;

import java.util.Scanner;
import travelleri.domain.ApproxTSP;
import travelleri.domain.BranchTSP;
import travelleri.domain.DynamicTSP;
import travelleri.domain.NaiveTSP;
import travelleri.domain.TSP;
import travelleri.io.FileIO;
import travelleri.io.OsrmFetch;

/**
 * Komentorivipohjainen käyttöliittymä
 */
public class ConsoleUI {
    private String[] args;
    private Scanner scan;

    public ConsoleUI(String[] args, Scanner scan) {
        this.args = args;
        this.scan = scan;
    }

    public void run() throws Exception {
        // menu
        if (args.length == 0) {

            System.out.println("1. luo uusi verkko ja tallenna se tiedostoon");
            System.out.println("2. avaa verkko tiedostosta ja aja algoritmi");
            System.out.println("3. pasteta koordinaatteja GoogleMapsista" 
                                + " ja aja dynaaminen algoritmi");
            System.out.println("4. aja default suorituskykytesti satunnaisverkoille");
            System.out.println("5. aja testi ApproxTSP:n polun pituudelle suhteessa optimipolkuun");

            while (!scan.hasNextInt()) {
                scan.next();
            }

            int selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
                case 1:
                    GraphTool.creater(scan);
                    break;
                case 2:
                    GraphTool.opener(scan);
                    break;
                case 3:
                    GmapsTool.clipboardReader();
                    break;
                case 4:
                    TSPTests.runPerformanceTest("naive", 2, 7, 10);
                    TSPTests.runPerformanceTest("branch", 2, 10, 10);
                    TSPTests.runPerformanceTest("dynamic", 2, 15, 10);
                    TSPTests.runPerformanceTest("approx", 2, 15, 10);
                    break;
                case 5:
                    TSPTests.runApproxPathTest(2, 22, 10);
                    break;
                default:
                    return;
            }

            return;
        }

        // command line args
        switch (args[0]) {
            case "runPerformanceTest":
                TSPTests.runPerformanceTest(args[1], Integer.parseInt(args[2]), 
                                Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                break;
            case "runApproxPathTest":
                TSPTests.runApproxPathTest(Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                        Integer.parseInt(args[3]));
                break;
            case "runFromCoordinates":
                String[][] coords = FileIO.openCoordinatesFromFile(args[1]);
                TSP tsp;

                switch (args[2]) {
                    case "naive":
                        tsp = new NaiveTSP(OsrmFetch.getGraph(coords));
                        break;
                    case "dynamic":
                        tsp = new DynamicTSP(OsrmFetch.getGraph(coords));
                        break;
                    case "approx":
                        tsp = new ApproxTSP(OsrmFetch.getGraph(coords));
                        break;
                    case "branch":
                        tsp = new BranchTSP(OsrmFetch.getGraph(coords));
                        break;
                    default:
                        tsp = new DynamicTSP(OsrmFetch.getGraph(coords));
                        break;
                }

                GmapsTool.runToGmapsLink(coords, tsp);
                break;
            default:
                double[][] graph = FileIO.openGraphFromFile(args[1]);
                switch (args[0]) {
                    case "runNaive":
                        GraphTool.runTSP(new NaiveTSP(graph));
                        break;
                    case "runDynamic":
                        GraphTool.runTSP(new DynamicTSP(graph));
                        break;
                    case "runApprox":
                        GraphTool.runTSP(new ApproxTSP(graph));
                        break;
                    case "runBranch":
                        GraphTool.runTSP(new BranchTSP(graph));
                        break;
                    default:
                        break;
                }
                break;
        }
    }
}
