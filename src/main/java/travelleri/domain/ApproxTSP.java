package travelleri.domain;

/**
 * Pyrkii etsimään lyhyen polun syötetystä verkosta niin, että polku kulkee
 * kaikkien verkon solmujen kautta palaten takaisin lähtösolmuun. Algoritmin
 * approksimoiva toteutus (ei palauta optimaalista tulosta, mutta on
 * suorituskykyinen).
 */
public class ApproxTSP implements TSP {
    private double[][] graph; // solmujen määrä
    private int nodesCount; // verkko
    private boolean[] visited; // primMST apumuuttuja (onko solmussa käyty)
    private double shortestPathLength; // laskettu lyhyimmän polun pituus
    private int[] shortestPath; // laskettu verkon lyhyin polku
    private boolean ran; // onko algoritmi suoritettu

    /**
     * Konstruktori.
     *
     * @param graph välimatkoista koostuva n*n matriisi, graph[x][y] on x:n ja y:n
     *              välinen etäisyys liukulukuna
     */
    public ApproxTSP(double[][] graph) {
        this.graph = graph;
        this.nodesCount = graph.length;
        this.visited = new boolean[graph.length];

        // tarkastetaan, onko graph n*n
        for (double[] row : graph) {
            if (row.length != this.nodesCount) {
                throw new IllegalArgumentException("Invalid graph");
            }
        }
    }

    /**
     * Lisää arvon listan loppuun. Apumetodi primMST() -metodille.
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
     * Toteuttaa Primin algoritmin mukaisen verkon läpikäynnin, jossa valitaan aina
     * solmun lähimpänä oleva naapuri ja edetään sinne
     * 
     * currentPath ja currentPathLength ovat "lisänä" ns. lyhimmän polun solmujen
     * kirjanpitoa varten. Tallentaa lopputuloksen oliomuuttujiin shortestPath ja
     * shortestPathLength, kun kaikki verkon solmut on käyty läpi.
     * 
     * @param currentNode       aloitussolmu
     * @param currentPath       tämänhetkinen polku
     * @param currentPathLength tämänhetkinen polun pituus
     */
    private void primMST(int currentNode, int[] currentPath, double currentPathLength) {
        visited[currentNode] = true;

        double minWeight = Double.MAX_VALUE;
        int closestNeighbor = -1;

        for (int node = 0; node < nodesCount; node++) {
            if (visited[node]) {
                continue;
            }

            double weight = graph[currentNode][node];
            if (weight < minWeight) {
                minWeight = weight;
                closestNeighbor = node;
            }
        }

        if (closestNeighbor == -1) {
            shortestPath = arrayAppend(arrayAppend(currentPath, currentNode), 0);
            shortestPathLength = currentPathLength + graph[currentNode][0];

            return;
        }
        primMST(closestNeighbor, arrayAppend(currentPath, currentNode),
                currentPathLength + minWeight);
    }

    /**
     * Suorittaa algoritmin.
     */
    @Override
    public void run() {
        primMST(0, new int[0], 0);
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
