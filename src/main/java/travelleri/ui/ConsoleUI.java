package travelleri.ui;

// import travelleri.domain.NaiveTSP;
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
            System.out.println("1. uuden verkon luominen");
            System.out.println("2. verkon avaaminen tiedostosta");

            while (!scan.hasNextInt()) scan.next();
            int selection = scan.nextInt();

            if (selection==1) {
                scan.nextLine();
                createGraph();
            } else if (selection==2) {
                scan.nextLine();
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

    public void openGraph() throws FileNotFoundException {
        System.out.print("Anna avattavan tiedoston nimi: ");
        String filename = scan.nextLine();
        double[][] graph = FileIO.openGraphFromFile(filename);
        System.out.println(Arrays.deepToString(graph)); 
    
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
