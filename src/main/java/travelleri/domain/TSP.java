package travelleri.domain;

public interface TSP {
    /**
     * Suorittaa algoritmin.
     */
    void run();

    /**
     * Palauttaa lyhimmän reitin solmujen listana. Suorittaa algoritmin, jos sitä ei
     * ole vielä suoritettu.
     * 
     * @return lyhyin polku listana
     */
    int[] getShortestPath();
    
    /**
     * Palauttaa lyhimmän reitin kokonaispituuden liukulukuna. Suorittaa algoritmin,
     * jos sitä ei ole vielä suoritettu.
     * 
     * @return lyhyimmän polun pituus
     */
    double getShortestPathLength();
}
