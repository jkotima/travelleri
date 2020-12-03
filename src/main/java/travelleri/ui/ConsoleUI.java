package travelleri.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import travelleri.domain.ApproxTSP;
import travelleri.domain.DynamicTSP;
import travelleri.domain.NaiveTSP;
import travelleri.domain.TSP;
import travelleri.domain.BranchTSP;

import travelleri.io.FileIO;


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
            System.out.println("3. aja suorituskykytesti kaikille algoritmeille");


            while (!scan.hasNextInt()) {
                scan.next();
            }

            int selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
                case 1:
                    createGraph();
                    break;
                case 2:
                    openGraph();
                    break;
                case 3:
                    runPerformanceTest("naive", 8);
                    runPerformanceTest("branch", 12);
                    runPerformanceTest("approx", 18);
                    runPerformanceTest("dynamic", 18);

                    break;
                default:
                    return;
            }

            return;
        }

        if (args[0].equals("create")) {
            createGraph();
        }

        if (args[0].equals("open")) {
            openGraph();
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
            if (args[1].equals("all")) {
                runPerformanceTest("naive", 8);
                runPerformanceTest("branch", 12);
                runPerformanceTest("approx", 18);
                runPerformanceTest("dynamic", 18);
                return;
            }

            runPerformanceTest(args[1], Integer.parseInt(args[2]));
        }
    }

    private void runApprox(double[][] graph) {
        ApproxTSP approx = new ApproxTSP(graph);

        long t = System.nanoTime();
        approx.run();
        t = System.nanoTime() - t;

        System.out.println("Suoritusaika: " + t + " ns");
        System.out.println("Lyhin polku:");
        System.out.println(Arrays.toString(approx.getShortestPath()));
        System.out.println("Lyhimmän polun pituus:");
        System.out.println(approx.getShortestPathLength());
    }

    private void runNaive(double[][] graph) {
        NaiveTSP naive = new NaiveTSP(graph);

        long t = System.nanoTime();
        naive.run();
        t = System.nanoTime() - t;

        System.out.println("Suoritusaika: " + t + " ns");
        System.out.println("Lyhin polku:");
        System.out.println(Arrays.toString(naive.getShortestPath()));
        System.out.println("Lyhimmän polun pituus:");
        System.out.println(naive.getShortestPathLength());
    }

    private void runDynamic(double[][] graph) {
        DynamicTSP dynamic = new DynamicTSP(graph);

        long t = System.nanoTime();
        dynamic.run();
        t = System.nanoTime() - t;

        System.out.println("Suoritusaika: " + t + " ns");
        System.out.println("Lyhin polku:");
        System.out.println(Arrays.toString(dynamic.getShortestPath()));
        System.out.println("Lyhimmän polun pituus:");
        System.out.println(dynamic.getShortestPathLength());
    }

    private void runBranch(double[][] graph) {
        BranchTSP branch = new BranchTSP(graph);

        long t = System.nanoTime();
        branch.run();
        t = System.nanoTime() - t;

        System.out.println("Suoritusaika: " + t + " ns");
        System.out.println("Lyhin polku:");
        System.out.println(Arrays.toString(branch.getShortestPath()));
        System.out.println("Lyhimmän polun pituus:");
        System.out.println(branch.getShortestPathLength());
    }

    private double[][] generateRandomGraph(int nodesCount) {
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

    private void runPerformanceTest(String algorithm, int maxNodesCount) {
        System.out.println("**Testataan " + algorithm + " kymmenellä 2-" 
                            + maxNodesCount +" solmuisella satunnaisverkolla**");

        for (int nodesCount = 2; nodesCount <= maxNodesCount; nodesCount++) {
            double[][] graph = generateRandomGraph(nodesCount);
  
            long t = System.nanoTime();
            long tAcc = 0;
            TSP tsp;

            for (int i = 0; i < 10; i++) {
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

                tAcc += t;
            }
            System.out.println("Suoritusaika "+ nodesCount + " solmuisilla verkoilla keskimäärin: " 
                                    + tAcc / 10 + " ns ("+ (double) (tAcc / 10)/1000000000 + "s)");
        }
    }
    

    public void openGraph() throws FileNotFoundException {
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

        switch (selection) {
            case 1:
                runNaive(graph);
                break;
            case 2:
                runDynamic(graph);
                break;
            case 3:
                runApprox(graph);
                break;
            case 4:
                runBranch(graph);
                break;
            default:
                return;

        }
    }

    private void createGraph() throws IOException {
        System.out.print("Solmujen lukumäärä? ");
        while (!scan.hasNextInt()) {
            scan.next();    
        } 
        int nodesCount = scan.nextInt();

        System.out.print("1. Syötä painot käsin 2. Generoi satunnaiset painot 3. Generoi säännöllinen ruudukko");
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
    
}
