package travelleri.domain;

import java.util.Arrays;

public class ApproxTSP implements TSP {
    private int nodesCount;
    private double[][] graph;
    private boolean[] included;
    private double shortestPathLength; // laskettu lyhyimmän polun pituus
    private int[] shortestPath; // laskettu verkon lyhyin polku
    private boolean ran; // onko algoritmi suoritettu

    public ApproxTSP(double[][] graph) {
        this.graph = graph;
        this.nodesCount = graph.length;
        this.included = new boolean[graph.length];
    }

    private int[] arrayAppend(int[] array, int value) {
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[newArray.length - 1] = value;

        return newArray;
    }

    private void primMST(int currentNode, int[] currentPath, double currentPathLength) {
        //System.out.println(currentNode);
        included[currentNode] = true;

        double minWeight = Double.MAX_VALUE;
        int closestNeighbor = -1;

        for (int node = 0; node < nodesCount; node++) {
            if (included[node]) {
                continue;
            }

            double weight = graph[currentNode][node];
            if (weight < minWeight) {
                minWeight = weight;
                closestNeighbor = node;
            }
        }

        if (closestNeighbor == -1) {
            //System.out.println(Arrays.toString(arrayAppend(arrayAppend(currentPath, currentNode),0)));
            //System.out.println(currentPathLength+graph[currentNode][0]);

            shortestPath = arrayAppend(arrayAppend(currentPath, currentNode),0);
            shortestPathLength = currentPathLength+graph[currentNode][0];

            return;
        }
        primMST(closestNeighbor, arrayAppend(currentPath, currentNode), currentPathLength+minWeight);
    }


    @Override
    public void run() {
        primMST(0, new int[0], 0);

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
