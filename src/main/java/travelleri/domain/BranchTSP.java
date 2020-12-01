package travelleri.domain;

/**
 * Etsii lyhyimmän polun syötetystä verkosta niin, että polku kulkee kaikkien
 * verkon solmujen kautta palaten takaisin lähtösolmuun. Algoritmin peruuttavan haun
 * branch and bound -toteutus.
 */
public class BranchTSP implements TSP {
    private double[][] graph; // verkko
    private int nodesCount; // solmujen määrä
    private double shortestPathLength; // laskettu lyhyimmän polun pituus
    private int[] shortestPath; // laskettu verkon lyhyin polku
    private boolean ran; // onko algoritmi suoritettu
    private boolean[] included; // backtrack-metodin apumuuttuja, onko solmu mukana

    /**
     * Konstruktori.
     *
     * @param graph välimatkoista koostuva n*n matriisi, graph[x][y] on x:n ja y:n
     *              välinen etäisyys liukulukuna
     */
    public BranchTSP(double[][] graph) {
        this.graph = graph;
        this.nodesCount = graph.length;
        this.shortestPathLength = Double.MAX_VALUE;
        this.included = new boolean[nodesCount];
        this.shortestPath = new int[graph.length+1];

        // tarkastetaan, onko graph n*n
        for (double[] row : graph) {
            if (row.length != this.nodesCount) {
                throw new IllegalArgumentException("Invalid graph");
            }
        }
    }

    /**
     * Lisää arvon listan loppuun.
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
     * Lisää arvon listan loppuun. Apumetodi primMST() -metodille.
     * 
     * @param node lista, johon arvo lisätään
     * @param pathLength arvo, joka lisätään listaan
	 * @param currentPath tämänhetkinen polku
     */
    private void backtrack(int node, double pathLength, int[] currentPath) {
        if (pathLength > shortestPathLength) {
            return;
        }
        if (node == nodesCount) {
            double wholeLength = pathLength + graph[ currentPath[currentPath.length-1 ]][0];
            if (wholeLength <= shortestPathLength) {
                shortestPathLength = wholeLength;
                shortestPath = arrayAppend(currentPath, 0);
            }
        } else {
            for (int i = 1; i < nodesCount; i++) {
                if (!included[i]) {
                    included[i] = true;
                    double distanceToNext = graph[currentPath[currentPath.length-1]][i];
                    backtrack(node + 1, pathLength + distanceToNext, arrayAppend(currentPath, i));
                    included[i] = false;
                }
            }
        }
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
        backtrack(1, 0, new int[1]);
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

