package travelleri.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.util.Arrays;
import travelleri.domain.DynamicTSP;
import travelleri.domain.TSP;
import travelleri.io.OsrmFetch;

/**
* Google Maps -koordinaattien lukemiseen leikepöydältä ja algoritmin ajamiseksi Google Maps-linkiksi.
*/
public class GmapsTool {
    private static String readClipboard() throws Exception {
        return (String) Toolkit
                            .getDefaultToolkit()
                            .getSystemClipboard()
                            .getData(DataFlavor.stringFlavor);
    }

    public static void clipboardReader() throws Exception {
        System.out.println("Kopioi koordinaatteja leikepöydälle. " 
                            + "Kun olet valmis, paina mitä tahansa näppäintä");
        String clipboardtxt = readClipboard();
        int i = 0;
        String[][] coords = new String[100][2];
        while (System.in.available() == 0) {
            if (!readClipboard().equals(clipboardtxt)) {
                System.out.println(readClipboard());
                coords[i][0] = readClipboard().split(", ")[0];
                coords[i][1] = readClipboard().split(", ")[1];
                clipboardtxt = readClipboard();
                i++;
            }
            Thread.sleep(100);
        }
        coords = Arrays.copyOf(coords, i);
        runToGmapsLink(coords, new DynamicTSP(OsrmFetch.getGraph(coords)));
    }

    private static String googleMapsLink(int[] path, String[][] coordinates) {
        String link = "https://www.google.com/maps/dir/";
        for (int p : path) {
            link += coordinates[p][0] + "," + coordinates[p][1] + "/";
        }
        return link;
    }

    public static void runToGmapsLink(String[][] coords, TSP tsp) throws IOException {
        tsp.run();

        System.out.println("Lyhyin polku: " + Arrays.toString(tsp.getShortestPath()));
        System.out.println("Kokonaismatka: " + tsp.getShortestPathLength());
        System.out.println(googleMapsLink(tsp.getShortestPath(), coords));
    }
}