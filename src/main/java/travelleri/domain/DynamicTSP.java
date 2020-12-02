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
    private DtspMemo dtspResults; // dstp-funktion tulokset

    /**
     * Konstruktori.
     *
     * @param graph välimatkoista koostuva n*n matriisi, graph[x][y] on x:n ja y:n
     *              välinen etäisyys liukulukuna
     */
    public DynamicTSP(double[][] graph) {
        this.graph = graph;
        this.nodesCount = graph.length;

        // tarkastetaan, onko graph n*n
        for (double[] row : graph) {
            if (row.length != this.nodesCount) {
                throw new IllegalArgumentException("Invalid graph");
            }
        }

        this.shortestPathLength = Double.MAX_VALUE;
        this.shortestPath = new int[this.nodesCount + 1];
        this.dtspResults = new DtspMemo(graph.length);
        this.ran = false;
    }

    /**
     * Poistaa taulukosta yksittäisen halutun alkion.
     *
     * @param array taulukko
     * @param toBeRemoved poistettava alkio
     * @return taulukko ilman haluttua alkiota
     */
    private int[] removeFromArray(int[] array, int toBeRemoved) {
        int[] newArray = new int[array.length - 1];
        int i = 0;
        for (int a : array) {
            if (a == toBeRemoved) {
                continue;
            }
            newArray[i] = a;
            i++;
        }
        return newArray;
    }

    /**
     * Toteuttaa rekursiivisen yhtälön:
     * 
     * dtsp(start, {remaining}) = min(graph[start][k] + dtsp(k, {remaining}-{k})),
     * jossa k € {remaining}.
     * 
     * Tulokset taulukoidaan ja haetaan dtspResults-oliosta.
     * 
     * @param start             aloitussolmu
     * @param remaining         jäljellä olevat solmut
     * @return                  lyhyin matka
     */
    public double dtsp(int start, int[] remaining) {
        if (remaining.length == 0) {
            return graph[start][0];
        }

        double min = Double.MAX_VALUE;
        int predecessor = -1;
        for (int k : remaining) {
            int[] nextRemaining = removeFromArray(remaining, k);
            double nextDtsp;

            if (dtspResults.exists(k, nextRemaining)) {
                nextDtsp = dtspResults.findResult(k, nextRemaining);
            } else {
                nextDtsp = dtsp(k, nextRemaining);
            }

            double x = graph[start][k] + nextDtsp;

            if (x < min) {
                min = x;
                predecessor = k;
            }
        }

        dtspResults.add(start, remaining, min, predecessor);

        return min;
    }

    /**
     * Käy lyhyimmän polun läpi käyttäen hyväksi laskettujen dtsp-tuloksien
     * edeltävien solmujen tietoja. Tallentaa lyhimmän polun shortestPath -muuttujaan.
     * 
     * @param remaining jäljellä olevat solmut (ajettaessa kaikki verkon solmut paitsi 0)
     */
    private void updateShortestPath(int[] remaining) {
        int predecessor = dtspResults.findPredecessor(0, remaining);
        shortestPath[shortestPath.length - 2] = predecessor;

        for (int i = nodesCount; i > 2; i--) {
            int[] nextRemaining = removeFromArray(remaining, predecessor);
            predecessor = dtspResults.findPredecessor(predecessor, nextRemaining);
            remaining = nextRemaining;
            shortestPath[i - 2] = predecessor;
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

        shortestPathLength = dtsp(0, remaining);
        updateShortestPath(remaining);

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
