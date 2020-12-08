package travelleri.io;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Scanner;


public class OsrmFetch {
    private class OsrmData {
        private double[][] distances;
    }

    public static double[][] fetchFromOsmr(String[][] coordinates) throws IOException {
        String allCoords = "";
        boolean first = true;
        for (String[] c : coordinates) {
            if (first) {
                first = false;
                allCoords += c[1] + "," + c[0];
            } else {
                allCoords += ";" + c[1]+"," + c[0];
            }
        }

        String url = "http://router.project-osrm.org/table/v1/driving/"
                    + allCoords
                    + "?annotations=distance";

        OsrmData osmrdata;

        try (InputStream is = new URL(url).openStream();
             Reader reader = new InputStreamReader(is)) {

            Gson gson = new Gson();
            osmrdata = gson.fromJson(reader, OsrmData.class);
        }

        return osmrdata.distances;
    }
}