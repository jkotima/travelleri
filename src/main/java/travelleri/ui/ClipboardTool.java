package travelleri.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Arrays;

public class ClipboardTool {
    private static String readClipboard() throws Exception {
        return (String) Toolkit
                            .getDefaultToolkit()
                            .getSystemClipboard()
                            .getData(DataFlavor.stringFlavor);
    }

    public static void run() throws Exception {
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
        }

        ConsoleUI.runFromCoordinates(Arrays.copyOf(coords, i), "dynamic");
    }
}