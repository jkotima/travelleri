package travelleri.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import travelleri.domain.ApproxTSP;
import travelleri.domain.DynamicTSP;
import travelleri.domain.NaiveTSP;
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
            runNaive(graph);
        }

        if (args[0].equals("runDynamic")) {
            if (args.length == 1) {
                throw new FileNotFoundException();
            }
            double[][] graph = FileIO.openGraphFromFile(args[1]);
            runDynamic(graph);
        }

        if (args[0].equals("runApprox")) {
            if (args.length == 1) {
                throw new FileNotFoundException();
            }
            double[][] graph = FileIO.openGraphFromFile(args[1]);
            runApprox(graph);
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

    public void openGraph() throws FileNotFoundException {
        System.out.print("Anna avattavan tiedoston nimi: ");
        String filename = scan.nextLine();
        double[][] graph = FileIO.openGraphFromFile(filename);

        System.out.println("1. aja verkolle naivi algoritmi");
        System.out.println("2. aja verkolle dynaaminen algoritmi");
        System.out.println("3. aja verkolle aproksoiva algoritmi");

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

        System.out.print("1. Syötä painot käsin 2. Generoi satunnaiset painot ");
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
