package travelleri.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NodeListTest {
    NodeList nl;

    @Before
    public void init() {
        nl = new NodeList(5);
        nl.add(1);
        nl.add(2);
        nl.add(3);
        nl.add(4);
        nl.add(5);
    }

    @Test
    public void getPathTest() {
        assertArrayEquals("Returned path is correct", new int[] { 1,2,3,4,5 },
                            nl.getPath());
    }

    @Test
    public void getLastTest() {
        assertEquals("Last node is correct", 5, nl.getLast());
    }

    @Test
    public void copyConstructorTest() {
        NodeList copy = new NodeList(nl);
        assertArrayEquals("Returned path is correct", new int[] { 1,2,3,4,5 },
                        copy.getPath());
        assertEquals("Last node is correct", 5, copy.getLast());
    }
}