package travelleri.ui;

import travelleri.domain.NaiveTSP;
import java.util.Scanner;

import travelleri.io.FileIO;

import java.util.Arrays;

import java.io.IOException; 
import java.io.FileNotFoundException; 

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

            while (!scan.hasNextInt()) scan.next();
            int selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
                case 1:
                    createGraph();
                case 2:
                    openGraph();
            }

            return;
        }

        if (args[0].equals("create")) {
            createGraph();
        }

        if (args[0].equals("open")) {
            openGraph();
        }
    }

    private void runNaive(double[][] graph) {
        NaiveTSP naive = new NaiveTSP(graph);
        
        // TODO: suoritusajan mittaus
        naive.run();
        
        System.out.println("Lyhin polku:");
        System.out.println(Arrays.toString(naive.getShortestPath()));
        System.out.println("Lyhimmän polun pituus:");
        System.out.println(naive.getShortestPathLength());
    }

    public void openGraph() throws FileNotFoundException {
        System.out.print("Anna avattavan tiedoston nimi: ");
        String filename = scan.nextLine();
        double[][] graph = FileIO.openGraphFromFile(filename);

        System.out.print("1. aja verkolle naivi algoritmi");
        while (!scan.hasNextInt()) scan.next();
        int selection = scan.nextInt();
        scan.nextLine();

        switch (selection) {
            case 1:
                runNaive(graph);
        }
    }
    
    private void createGraph() throws IOException {
        System.out.print("Solmujen lukumäärä? ");
        while (!scan.hasNextInt()) scan.next();
        int nodesCount = scan.nextInt();
        double[][] newGraph = new double[nodesCount][nodesCount];
        for (int x = 0; x < nodesCount; x++) {
            for (int y = 0; y < nodesCount; y++) {
                if (x==y) {
                    newGraph[x][y] = 0;
                    continue;
                }

                if (newGraph[y][x] != 0) {
                    newGraph[x][y] = newGraph[y][x];
                    continue;
                }

                System.out.print("Kaaren "+(x+1)+"-"+(y+1)+" paino? ");
                while (!scan.hasNextDouble()) scan.next();

                newGraph[x][y] = scan.nextDouble();
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
