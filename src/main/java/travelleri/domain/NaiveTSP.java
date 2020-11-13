package travelleri.domain;

/**
 * Etsii lyhyimmän polun syötetystä verkosta niin, että polku kulkee kaikkien
 * verkon solmujen kautta palaten takaisin lähtösolmuun. Algoritmin naivi
 * (hidas) toteutus, jossa käydään kaikki reittivaihtoehdot (permutaatiot) läpi
 * ja valitaan näistä lyhyin polku.
 */
public class NaiveTSP implements TSP {
    private double[][] graph; // verkko
    private int nodesCount; // solmujen lukumäärä
    private int[][] permutations; // kaikki mahdolliset polut
    private boolean[] included; // makePermutations -metodin apumuuttuja
    private int[] digits; // makePermutations -metodin apumuuttuja
    private int[] shortestPath; // laskettu verkon lyhyin polku
    private double shortestPathLength; // laskettu lyhyimmän polun pituus
    private boolean ran; // onko algoritmi suoritettu

    /**
     * Konstruktori.
     *
     * @param graph välimatkoista koostuva n*n matriisi, graph[x][y] on x:n ja y:n
     *              välinen etäisyys liukulukuna
     */
    public NaiveTSP(double[][] graph) {
        this.nodesCount = graph[0].length;

        // tarkastetaan, onko graph  n*n
        for (double[] row : graph) {
            if (row.length != this.nodesCount) {
                throw new IllegalArgumentException("Invalid graph"); 
            }
        }

        this.graph = graph;
        this.permutations = new int[0][0];
        this.included = new boolean[this.nodesCount];
        this.digits = new int[this.nodesCount];
        this.shortestPath = new int[this.nodesCount + 1];
        this.shortestPathLength = Double.MAX_VALUE;
        this.ran = false;
    }

    /**
     * Rekursiivinen metodi, jolla alustetaan nodesCount mittaiset permutaatiot
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
            double totalDistance = 0;
            int[] path = new int[nodesCount + 1];
            path[0] = 0;
            int[] permutation = permutations[i];

            for (int j = 0; j < permutation.length - 1; j++) {
                totalDistance += graph[permutation[j]][permutation[j + 1]];
                path[j + 1] = permutation[j + 1];
            }
            totalDistance += graph[permutation[permutation.length - 1]][0];

            if (totalDistance < shortestPathLength) {
                shortestPathLength = totalDistance;
                shortestPath = path;
            }
        }

        ran = true;
    }

    /**
     * Palauttaa lyhimmän reitin solmujen listana. Suorittaa algoritmin, jos sitä ei
     * ole vielä suoritettu.
     * 
     * @return lyhyin polku listana
     **/
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
     * @return lyhyin polku listana
     **/
    @Override
    public double getShortestPathLength() {
        if (!ran) {
            run();
        }
        return shortestPathLength;
    }
}
