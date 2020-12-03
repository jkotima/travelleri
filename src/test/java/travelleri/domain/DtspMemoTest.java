package travelleri.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DtspMemoTest {
    DtspMemo dtspmemo;

    @Before
    public void init() {
        dtspmemo = new DtspMemo(3);
        dtspmemo.add(1, new int[] {2,3}, 123, 0);
    }

    @Test
    public void existTest() {
        assertTrue("Added result exists", dtspmemo.exists(1, new int[] {2,3}));
    }

    @Test
    public void addTest() {
        dtspmemo.add(6, new int[] {7,8}, 321, 1);
        assertTrue("Added result exists", dtspmemo.exists(6, new int[] {7,8}));
    }

    @Test
    public void findResultTest() {
        assertEquals("Added result returned correctly", 123, 
                        dtspmemo.findResult(1, new int[] {2,3}), 1);
    }

    @Test
    public void findPredecessorTest() {
        assertEquals("Predecessor returned correctly", 0, 
                        dtspmemo.findPredecessor(1, new int[] {2,3}), 1);
    }

    @Test
    public void resultWithExistingHashCanBeAddedTest() {
        dtspmemo.add(1, new int[] {2,3}, 321, 1);
        assertEquals("Collisions = 1", dtspmemo.getCollisions(), 1);
        assertEquals("Total results = 2", dtspmemo.getResultsIndex(), 2);
    }

}