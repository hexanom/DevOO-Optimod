package fr.insalyon.optimod;

import junit.framework.TestCase;

public class ApplicationTest extends TestCase {
    public void test_getInstance() throws Exception {
        assertTrue(Application.getInstance() != null);
        assertEquals(Application.getInstance(), Application.getInstance());
    }
}
