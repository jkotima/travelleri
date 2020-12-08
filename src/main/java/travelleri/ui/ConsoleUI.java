package travelleri.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import travelleri.domain.ApproxTSP;
import travelleri.domain.BranchTSP;
import travelleri.domain.DynamicTSP;
import travelleri.domain.NaiveTSP;
import travelleri.domain.TSP;
import travelleri.io.FileIO;
import travelleri.io.OsrmFetch;

public class ConsoleUI {
    private String[] args;
    private Scanner scan;

    public ConsoleUI(String[] args) {
        this.args = args;
        this.scan = new Scanner(System.in);
    }

    public void run() throws Exception {
        if (args.length == 0) {

            System.out.println("1. luo uusi verkko ja tallenna se tiedostoon");
            System.out.println("2. avaa verkko tiedostosta");
            System.out.println("3. pasteta koordinaatteja GoogleMapsista"
                                 + " ja aja niille dynaaminen algoritmi");
            System.out.println("4. aja suorituskykytesti satunnaisverkoille (default)");

            while (!scan.hasNextInt()) {
                scan.next();
            }

            int selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
                case 1:
                    GraphTool.create(scan);
                    break;
                case 2:
                    GraphTool.open(scan);
                    break;
                case 3:
                    ClipboardTool.run();
                    break;
                case 4:
                    PerformanceTest.run("naive", 2, 7, 10);
                    PerformanceTest.run("branch", 2, 10, 10);
                    PerformanceTest.run("dynamic", 2, 15, 10);
                    PerformanceTest.run("approx", 2, 15, 10);
                    break;

                default:
                    return;
            }

            return;
        }

        if (args[0].equals("create")) {
            GraphTool.create(scan);
        }

        if (args[0].equals("open")) {
            GraphTool.open(scan);
        }

        if (args[0].equals("runNaive")) {
            if (args.length == 1) {
                throw new FileNotFoundException();
            }
            double[][] graph = FileIO.openGraphFromFile(args[1]);
            System.out.println("Verkko avattu");

            runNaive(graph);
        }

        if (args[0].equals("runDynamic")) {
            if (args.length == 1) {
                throw new FileNotFoundException();
            }
            double[][] graph = FileIO.openGraphFromFile(args[1]);
            System.out.println("Verkko avattu");

            runDynamic(graph);
        }

        if (args[0].equals("runApprox")) {
            if (args.length == 1) {
                throw new FileNotFoundException();
            }
            double[][] graph = FileIO.openGraphFromFile(args[1]);
            System.out.println("Verkko avattu");

            runApprox(graph);
        }

        if (args[0].equals("runBranch")) {
            if (args.length == 1) {
                throw new FileNotFoundException();
            }
            double[][] graph = FileIO.openGraphFromFile(args[1]);
            System.out.println("Verkko avattu");

            runBranch(graph);
        }
        if (args[0].equals("runPerformanceTest")) {
            PerformanceTest.run(args[1], Integer.parseInt(args[2]), 
                        Integer.parseInt(args[3]),Integer.parseInt(args[4]));
        }

        if (args[0].equals("runFromCoordinates")) {
            String[][] coords = FileIO.openCoordinatesFromFile(args[1]);
            
            if (args.length == 2) {
                runFromCoordinates(coords, "dynamic");
            } else {
                runFromCoordinates(coords, args[2]);
            }
        }
    }

    private static String googleMapsLink(int[] path, String[][] coordinates) {
        String link = "https://www.google.com/maps/dir/";
        for (int p : path) {
            link += coordinates[p][0] + "," + coordinates[p][1] + "/";
        }
        return link;
    }

    public static void runFromCoordinates(String[][] coords, String algorithm) throws Exception {
        double[][] graph = OsrmFetch.fetchFromOsmr(coords);
        TSP tsp;

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
        tsp.run();

        System.out.println("Lyhyin polku: " + Arrays.toString(tsp.getShortestPath()));
        System.out.println("Kokonaismatka: " + tsp.getShortestPathLength());
        System.out.println(googleMapsLink(tsp.getShortestPath(), coords));
    }

    private static void runTSP(TSP tsp) {
        long t = System.nanoTime();
        tsp.run();
        t = System.nanoTime() - t;

        System.out.println("Suoritusaika: " + t + " ns");
        System.out.println("Lyhin polku:");
        System.out.println(Arrays.toString(tsp.getShortestPath()));
        System.out.println("Lyhimmän polun pituus:");
        System.out.println(tsp.getShortestPathLength());
    }

    public static void runApprox(double[][] graph) {
        ApproxTSP approx = new ApproxTSP(graph);
        runTSP(approx);
    }

    public static void runNaive(double[][] graph) {
        NaiveTSP naive = new NaiveTSP(graph);
        runTSP(naive);
    }

    public static void runDynamic(double[][] graph) {
        DynamicTSP dynamic = new DynamicTSP(graph);
        runTSP(dynamic);
    }

    public static void runBranch(double[][] graph) {
        BranchTSP branch = new BranchTSP(graph);
        runTSP(branch);
    }  
}
