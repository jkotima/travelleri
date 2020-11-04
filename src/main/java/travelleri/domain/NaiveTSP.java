package travelleri.domain;

/**
 * Etsii lyhimmän polun syötetystä verkosta niin, että polku kulkee kaikkien
 * verkon solmujen kautta palaten takaisin lähtösolmuun. Algoritmin naivi
 * (hidas) toteutus.
 */
public class NaiveTSP implements TSP {
    private double[][] graph; // verkko
    private int nodesCount; // solmujen lukumäärä
    private int[][] permutations; // kaikki mahdolliset polut
    private boolean[] included; // makePermutations -metodin apumuuttuja
    private int[] digits; // makePermutations -metodin apumuuttuja
    private int[] shortestRoute; // laskettu verkon lyhyin polku
    private double shortestRouteLength; // laskettu lyhyimmän polun pituus
    private boolean ran; // onko algoritmi suoritettu

    /**
     * Konstruktori.
     *
     * @param graph välimatkoista koostuva n*n matriisi, graph[x][y] on x:n ja y:n
     *              välinen etäisyys liukulukuna
     */
    public NaiveTSP(double[][] graph) {
        this.graph = graph;
        this.nodesCount = graph[0].length;
        this.permutations = new int[0][0];
        this.included = new boolean[graph[0].length];
        this.digits = new int[graph[0].length];
        this.shortestRoute = new int[graph[0].length + 1];
        this.shortestRouteLength = Double.MAX_VALUE;
        this.ran = false;
    }

    /**
     * Rekursiivinen metodi, jolla alustetaan n mittaiset permutaatiot
     * oliomuuttujaan permutations. Apumetodi run() -metodille.
     * 
     * @param k rekursion aloituskohta taulukossa
     */
    private void makePermutations(int k) {
        if (k == nodesCount) {
            int[][] newPermutations = new int[permutations.length + 1][nodesCount];
            newPermutations[newPermutations.length - 1] = digits.clone();
            System.arraycopy(permutations, 0, newPermutations, 0, permutations.length);
            permutations = newPermutations.clone();
        } else {
            for (int i = 1; i < nodesCount; i++) {
                if (!included[i]) {
                    included[i] = true;
                    digits[k] = i;
                    makePermutations(k + 1);
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
        makePermutations(1);

        for (int i = 0; i < permutations.length; i++) {
            int totalDistance = 0;
            int[] route = new int[nodesCount + 1];
            route[0] = 0;
            int[] permutation = permutations[i];

            for (int j = 0; j < permutation.length - 1; j++) {
                totalDistance += graph[permutation[j]][permutation[j + 1]];
                route[j + 1] = permutation[j + 1];
            }
            totalDistance += graph[permutation[permutation.length - 1]][0];

            if (totalDistance < shortestRouteLength) {
                shortestRouteLength = totalDistance;
                shortestRoute = route;
            }
        }

        ran = true;
    }

    /**
     * Palauttaa lyhimmän reitin solmujen listana. Suorittaa algoritmin, jos sitä ei
     * ole vielä suoritettu.
     * 
     * @return lyhyin reitti listana
     **/
    @Override
    public int[] getShortestRoute() {
        if (!ran) {
            run();
        }
        return shortestRoute;
    }

    /**
     * Palauttaa lyhimmän reitin kokonaispituuden liukulukuna. Suorittaa algoritmin,
     * jos sitä ei ole vielä suoritettu.
     * 
     * @return lyhyin reitti listana
     **/
    @Override
    public double getShortestRouteLength() {
        if (!ran) {
            run();
        }
        return shortestRouteLength;
    }
}
