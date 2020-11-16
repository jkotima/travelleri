package travelleri.domain;

/**
 * Etsii lyhyimmän polun syötetystä verkosta niin, että polku kulkee kaikkien
 * verkon solmujen kautta palaten takaisin lähtösolmuun. Algoritmin dynaamisen
 * ohjelmoinnin toteutus.
 */
public class DynamicTSP implements TSP {
    private double[][] graph; // verkko
    private int nodesCount; // solmujen lukumäärä
    private double shortestPathLength; // laskettu lyhyimmän polun pituus
    private int[] shortestPath; // laskettu verkon lyhyin polku
    private boolean ran; // onko algoritmi suoritettu

    /**
     * Konstruktori.
     *
     * @param graph välimatkoista koostuva n*n matriisi, graph[x][y] on x:n ja y:n
     *              välinen etäisyys liukulukuna
     */
    public DynamicTSP(double[][] graph) {
        this.graph = graph;
        this.nodesCount = graph[0].length;

        // tarkastetaan, onko graph n*n
        for (double[] row : graph) {
            if (row.length != this.nodesCount) {
                throw new IllegalArgumentException("Invalid graph");
            }
        }

        this.shortestPathLength = Double.MAX_VALUE;
        this.shortestPath = new int[this.nodesCount + 1];
        this.ran = false;
    }

    /**
     * Lisää arvon listan loppuun. Apumetodi g() -metodille.
     * 
     * @param array lista, johon arvo lisätään
     * @param value arvo, joka lisätään listaan
     * @return uusi lista, johon arvo on lisätty
     */
    private int[] arrayAppend(int[] array, int value) {
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[newArray.length - 1] = value;

        return newArray;
    }

    /**
     * Toteuttaa rekursiivisen yhtälön:
     * 
     * dtsp(start, {remaining}) = min(graph[start][k] + dtsp(k, {remaining}-{k})),
     * jossa k € {remaining}.
     * 
     * currentPath ja currentPathLength ovat "lisänä" lyhimmän polun solmujen
     * selvitystä varten. Tallentaa lopputuloksen oliomuuttujiin shortestPath ja
     * shortestPathLength.
     * 
     * @param start             aloitussolmu
     * @param remaining         jäljellä olevat solmut
     * @param currentPath       tämänhetkinen polku
     * @param currentPathLength tämänhetkinen polun pituus
     */
    public double dtsp(int start, int[] remaining, int[] currentPath, double currentPathLength) {
        if (remaining.length == 0) {
            if (currentPathLength + graph[start][0] < shortestPathLength) {
                shortestPathLength = currentPathLength + graph[start][0];
                int[] newCurrentPath = arrayAppend(currentPath, start);
                shortestPath = arrayAppend(newCurrentPath, 0);
            }
            return graph[start][0];
        }

        double min = Double.MAX_VALUE;
        for (int k : remaining) {

            // filter k out from remaining
            int[] nextRemaining = new int[remaining.length - 1];
            int i = 0;
            for (int r : remaining) {
                if (r == k) {
                    continue;
                }
                nextRemaining[i] = r;
                i++;
            }

            double x = graph[start][k]
                    + dtsp(k, nextRemaining, arrayAppend(currentPath, start), 
                            currentPathLength + graph[start][k]);

            if (x < min) {
                min = x;
            }
        }

        return min;
    }

    /**
     * Suorittaa algoritmin.
     */
    @Override
    public void run() {
        int[] remaining = new int[nodesCount - 1];
        for (int i = 1; i < nodesCount; i++) {
            remaining[i - 1] = i;
        }

        dtsp(0, remaining, new int[0], 0);

        ran = true;
    }

    /**
     * Palauttaa lyhimmän reitin solmujen listana. Suorittaa algoritmin, jos sitä ei
     * ole vielä suoritettu.
     * 
     * @return lyhyin polku listana
     */
    @Override
    public int[] getShortestPath() {
        if (!ran) {
            run();
        }
        return shortestPath;
    }

    /**
     * Palauttaa lyhimmän reitin kokonaispituuden liukulukuna. Suorittaa algoritmin,
     * jos sitä ei ole vielä suoritettu.
     * 
     * @return lyhyimmän polun pituus
     */
    @Override
    public double getShortestPathLength() {
        if (!ran) {
            run();
        }
        return shortestPathLength;
    }
}
