package travelleri.domain;

/**
 * Luokka tallentamaan DynamicTSP:n dtsp-metodin tuloksia.
 */
public class DtspMemo {
    /**  Luokka dtsp-tulokselle */
    private class DtspResult {
        int start;
        int[] remaining;
        double result;
        int predecessor;
        int next; // törmäystilanteessa, seuraavan dtsp-tuloksen indeksi

        DtspResult(int start, int[] remaining, double result, int predecessor) {
            this.start = start;
            this.remaining = remaining;
            this.result = result;
            this.predecessor = predecessor;
            this.next = -1;
        }
    }

    private DtspResult[] results;       // tulokset
    private int resultsIndex;           // tulosten indeksi, kasvaa, kun tulos lisätään
    private int[] HashToResultsIndex;   // hash->results -taulukko

    private int collisions;             // törmäysten määrä (säätämiseen)
    private int resultsSize;            // koko results-taulukolle

    public DtspMemo(int nodesCount) {
        this.resultsSize = (int) (Math.pow(2, nodesCount) * Math.sqrt(nodesCount) * 1.2);
        this.results = new DtspResult[resultsSize];
        this.resultsIndex = 0;
        this.HashToResultsIndex = new int[resultsSize*5];
        this.collisions = 0;
    }
    
    /**
     * Laskee hash-arvon start/remaining -parille.
     *
     * @param start dtsp:n start
     * @param remaining dtsp:n remaining
     * @return hash-arvo
     */
    private int hashFor(int start, int[] remaining) {
        int h = 1;
        h = (h*7+start)%(resultsSize*5);
        for (int r : remaining) {
            h = (h*7+r)%(resultsSize*5);
        }
        
        return h;
    }

    /**
     * Vertaa kahden taulukon samuutta.
     *
     * @param array1 ensimmäinen taulukko
     * @param array2 toinen taulukko
     * @return onko taulukot samat
     */
    private static boolean arrayEquals(int[] array1, int[] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tarkastaa, onko tiettyä start/remaining -parin tulosta taulukoituna.
     *
     * @param start dtsp:n start
     * @param remaining dtsp:n remaining
     * @return onko tulos taulukoituna
     */
    public boolean exists(int start, int[] remaining) {
        if (HashToResultsIndex[hashFor(start, remaining)] == 0) {
            return false;
        }

        DtspResult dr = results[ HashToResultsIndex[hashFor(start, remaining)] ];

        while(true) {
            if (start == dr.start && arrayEquals(remaining, dr.remaining)) {
                return true;
            }
            if (dr.next == -1) {
                break;
            }
            dr = results[dr.next];
        }
        return false;        
    }

    /**
     * Taulukoi dtsp-tuloksen.
     *
     * @param start dtsp:n start
     * @param remaining dtsp:n remaining
     * @param result dtsp:n tulos
     * @param predecessor dtsp:n edeltäjäsolmu
     */
    public void add(int start, int[] remaining, double result, int predecessor) {
        resultsIndex++;
        results[resultsIndex] = new DtspResult(start, remaining, result, predecessor);

        if (HashToResultsIndex[ hashFor(start, remaining) ] == 0) {
            HashToResultsIndex[ hashFor(start, remaining) ] = resultsIndex;
        } else {
            collisions++;

            DtspResult dr = results[ HashToResultsIndex[ hashFor(start, remaining) ] ];
            while (true) {
                if (dr.next == -1) {
                    dr.next = resultsIndex;
                    break;
                }
                dr = results[dr.next];
            }
        }
    }

    /**
     * Etsii taulukosta dtsp:n start/remaining -paria vastaavan tulos-olion.
     *
     * @param start dtsp:n start
     * @param remaining dtsp:n remaining
     * @return dtsp-tulosolio
     */
    private DtspResult find(int start, int[] remaining) {
        DtspResult dr = results[ HashToResultsIndex[ hashFor(start, remaining) ] ];

        while(true) {
            if (start == dr.start && arrayEquals(remaining, dr.remaining)) {
                return dr;
            }

            if (dr.next == -1) {
                break;
            }

            dr = results[dr.next];
        }
        
        return dr;
    }

    /**
     * Etsii taulukosta dtsp:n start/remaining -paria vastaavan tuloksen.
     *
     * @param start dtsp:n start
     * @param remaining dtsp:n remaining
     * @return dtsp-tulos
     */
    public double findResult(int start, int[] remaining) {
        return this.find(start, remaining).result;
    }

    /**
     * Etsii taulukosta dtsp:n start/remaining -paria vastaavan edeltäjäsolmun.
     *
     * @param start dtsp:n start
     * @param remaining dtsp:n remaining
     * @return dtsp-edeltäjäsolmu
     */
    public int findPredecessor(int start, int[] remaining) {
        return this.find(start, remaining).predecessor;
    }

    public int getCollisions() {
        return collisions;
    }

    public int getResultsIndex() {
        return resultsIndex;
    }
    public int getResultsSize() {
        return resultsSize;
    }
}
