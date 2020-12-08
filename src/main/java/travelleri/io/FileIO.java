package travelleri.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter; 
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class FileIO {
    public static double[][] openGraphFromFile(String filename) throws FileNotFoundException {
        File f = new File(filename);
        Scanner sc = new Scanner(f);           

        String firstLine = sc.nextLine();
        int n = firstLine.split(",").length;

        double[][] graph = new double[n][n];

        for (int i = 0; i < n; i++) {
            graph[0][i] = Double.parseDouble(firstLine.split(",")[i]);
        }
        int i = 1;
        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();

            for (int j = 0; j < n; j++) {
                graph[i][j] = Double.parseDouble(nextLine.split(",")[j]);
            }
            i++;
        }
        sc.close();

        return graph;
    }

    public static void saveGraphToFile(String filename, double[][] graph) throws IOException {
        new File(filename);
        FileWriter fw = new FileWriter(filename);
        for (double[] row : graph) {
            for (int i = 0; i < row.length; i++) {
                fw.write(Double.toString(row[i]));
                if (i < row.length - 1) {
                    fw.write(",");
                }
            }
            fw.write("\n");
        }
        fw.close();

    }

    public static String[][] openCoordinatesFromFile(String filename) throws FileNotFoundException {
        File f = new File(filename);
        Scanner sc = new Scanner(f);           

        String[][] coords = new String[100][2];
        int i = 0;
        while (sc.hasNextLine()) {
            String[] splittedNextLine = sc.nextLine().split(", ");

            coords[i][0] = splittedNextLine[0];
            coords[i][1] = splittedNextLine[1];

            i++;
        }
        sc.close();

        return Arrays.copyOf(coords, i);
    }
}
