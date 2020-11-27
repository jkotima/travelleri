// kesken, tätä tulee käyttämään BranchTSP

package travelleri.domain;

import java.util.Arrays;

public class DtspMemo {
    private class DtspResult {
        int start;
        int[] remaining;
        double result;

        int next;

        DtspResult(int start, int[] remaining, double result) {
            this.start = start;
            this.remaining = remaining;
            this.result = result;
            this.next = -1;
        }
    }

    private DtspResult[] results;
    private int resultsIndex;
    private int[] HashToResultsIndex;

    private int collisions;

    public DtspMemo() {
        this.results = new DtspResult[100000000];
        this.resultsIndex = 0;
        this.HashToResultsIndex = new int[100000000];
        this.collisions = 0;
    }

    // TODO: parempi hashfunktio
    private static int hashFor(int start, int[] remaining) {
        int hash = 0;
        for (int r : remaining) {
            hash += r;
        }

        return hash+start;
    }

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

    public void add(int start, int[] remaining, double result) {
        resultsIndex++;
        results[resultsIndex] = new DtspResult(start, remaining, result);

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

    public double get(int start, int[] remaining) {
        DtspResult dr = results[ HashToResultsIndex[ hashFor(start, remaining) ] ];

        while(true) {
            if (start == dr.start && arrayEquals(remaining, dr.remaining)) {
                return dr.result;
            }

            if (dr.next == -1) {
                break;
            }

            dr = results[dr.next];
        }
        
        return dr.result;
    }

    public int getCollisions() {
        return collisions;
    }
    /*
    public void printResults() {
        results[0] = new DtspResult(0, new int[0], new Dtsp(0, new int[0]));
        int i = 0;
        for (DtspResult c : results) {
            if (c == null) break;
            if (c.result.getMin() < 123123123 && arrayEquals(c.result.getPath(), new int[] {0, 6, 7, 8, 4, 1, 2, 5, 3, 0} )) {
                System.out.println(i+". "+c.start + "," + Arrays.toString(c.remaining) + "=" + c.result.getMin() + Arrays.toString(c.result.getPath()) +  "   next:" + c.next);

            }
            i++;
        }
    }
    */
}
