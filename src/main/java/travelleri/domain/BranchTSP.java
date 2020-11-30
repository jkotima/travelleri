package travelleri.domain;

import java.util.Arrays;

public class BranchTSP implements TSP {
    private double[][] graph; // verkko
    private int nodesCount; // solmujen määrä
    private double shortestPathLength; // laskettu lyhyimmän polun pituus
    private int[] shortestPath; // laskettu verkon lyhyin polku
    private boolean ran; // onko algoritmi suoritettu
    private boolean[] included;
    private DtspMemo dtspResults;
    private boolean foundPath;
    /**
     * Konstruktori.
     *
     * @param graph välimatkoista koostuva n*n matriisi, graph[x][y] on x:n ja y:n
     *              välinen etäisyys liukulukuna
     */
    public BranchTSP(double[][] graph) {
        this.graph = graph;
        this.nodesCount = graph.length;
        //this.shortestPathLength = 238.63300271532952D;
        this.shortestPathLength = Double.MAX_VALUE;
        this.included = new boolean[nodesCount];
        this.shortestPath = new int[graph.length+1];
        this.dtspResults = new DtspMemo();
        this.foundPath = false;
        // tarkastetaan, onko graph n*n
        for (double[] row : graph) {
            if (row.length != this.nodesCount) {
                throw new IllegalArgumentException("Invalid graph");
            }
        }
    }

    private int[] arrayAppend(int[] array, int value) {
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[newArray.length - 1] = value;

        return newArray;
    }

    public double dtsp(int start, int[] remaining) {
        if (remaining.length == 0) {
            return graph[start][0];
        }
        
        if (dtspResults.exists(start, remaining)) {
            return dtspResults.get(start, remaining);
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

            double x = graph[start][k] + dtsp(k, nextRemaining);

            if (x < min) {
                min = x;
            }
        }

        dtspResults.add(start, remaining, min);
        return min;
    }

    private void backtrack(int node, double pathLength, int[] currentPath) {
        // todo: branch and bound ajamalla taulukuidista dynamicista(myöskin todo) oikean mittainen polku       
        if (pathLength > shortestPathLength ||foundPath) {
            return;
        }
        if (node == nodesCount) {
            double wholeLength = pathLength + graph[ currentPath[currentPath.length-1 ]][0];
            if (wholeLength <= shortestPathLength) {
                if (wholeLength == shortestPathLength) {
                    foundPath = true;
                }
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
        shortestPathLength = dtsp(0, remaining);
        System.out.println("reitin pituus löydetty");
        backtrack(1, 0, new int[1]);
        System.out.println("Collisions: " + dtspResults.getCollisions());
        System.out.println("Total dtsp-results: " + dtspResults.getResultsIndex());

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

