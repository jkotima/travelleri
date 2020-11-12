package travelleri.domain;
import java.util.Arrays; 

public class DynamicTSP implements TSP {
    private double shortestPathLength; // laskettu lyhyimmän polun pituus
    private int[] shortestPath; // laskettu verkon lyhyin polku
    private int nodesCount; // solmujen lukumäärä
    private boolean ran; // onko algoritmi suoritettu
    private double[][] graph; // verkko

    public DynamicTSP(double[][] graph)  {
        this.graph = graph;
        this.nodesCount = graph[0].length;
        this.shortestPathLength = Double.MAX_VALUE;
        this.shortestPath = new int[this.nodesCount + 1];
        this.ran = false;
    }

    public double g(int start, int[] remaining, int[] currentPath, double currentTotalLength) {
        //System.out.println("g("+start+", "+Arrays.toString(remaining)+")");
        
        // updateCurrentPath
        int[] newCurrentPath = new int[currentPath.length+1];
        System.arraycopy(currentPath, 0, newCurrentPath, 0, currentPath.length);
        newCurrentPath[newCurrentPath.length-1]=start;

        if (remaining.length==0) {
            if (currentTotalLength+graph[start][0] < shortestPathLength) {
                shortestPathLength = currentTotalLength+graph[start][0];
                shortestPath = newCurrentPath.clone();
            }

            //System.out.println(Arrays.toString(newCurrentPath)+"|||"+(currentTotalLength+graph[start][0]));
            return graph[start][0];
        }

        double min = Double.MAX_VALUE;
        for (int k : remaining) {

            // filter nextRemaining from remaining
            int[] nextRemaining = new int[remaining.length-1];
            int i = 0;
            for (int r : remaining) {
                if (r==k){
                    continue;
                }
                nextRemaining[i] = r;
                i++;
            }

            double x = graph[start][k]+g(k, nextRemaining, newCurrentPath, currentTotalLength+graph[start][k]);

            if (x < min) {
                min = x;
            }
        }
        
        return min;
    }

    @Override
    public void run() {
        int[] remaining = new int[nodesCount-1];
        for (int i=1; i<nodesCount; i++) {
            remaining[i-1]=i;
        }

        g(0,remaining,new int[0],0);
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
