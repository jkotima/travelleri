package travelleri.ui;

// import travelleri.domain.NaiveTSP;
import java.util.Scanner;
import java.util.Arrays;

import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class ConsoleUI {
    private String[] args;
    private Scanner scan;
    
    public ConsoleUI(String[] args) {
        this.args = args;
        this.scan = new Scanner(System.in);
    }

    public void run() {
        if (args.length == 0) {
            System.out.println("Käyttö (komentoriviargumentit):");
            System.out.println("create - uuden verkon luominen");
            System.out.println("open * - verkon avaaminen tiedostosta *");

            return;
        }

        if (args[0].equals("create")) {
            createGraph();
        }
    }

    private void saveGraphToFile(String filename, double[][] graph) {
        try {
            File f = new File(filename);
            FileWriter fw = new FileWriter(filename);
            for (double[] row : graph) {
                for (int i = 0; i < row.length; i++) {
                    fw.write(Double.toString(row[i]));
                    if (i < row.length-1) {
                        fw.write(",");
                    }
                }
                fw.write("\n");
            }
            fw.close();
            System.out.println("Verkko tallennettu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createGraph() {
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
        String filename = scan.nextLine();
        saveGraphToFile(filename, newGraph);
    }
}
