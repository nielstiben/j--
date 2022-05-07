package junit;

import junit.framework.TestCase;

public class StatementsTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testTryCatch() {
        int a = 0;
        try {
            a = a + 1;
            assertEquals(a, 1);
        } catch (Exception e) {
            a = 2;
        } finally {
            a = a + 1;
            assertEquals(a, 2);
        }

    }

    public void testTernary() {
        int a = 0;
        a = a == 0 ? 1 : 2;
        assertEquals(a, 1);
        a = a == 0 ? 1 : 2;
        assertEquals(a, 2);
    }

    public void test_inc_dec() {
        int a = 0;
        a--;
        assertEquals(a, -1);
        a++;
        assertEquals(a, 0);
        --a;
        assertEquals(a, -1);
       ++a;
        assertEquals(a, 0);
    }

    public void or() {
        assertEquals(true || false, true);
        assertEquals(false || true, true);
        assertEquals(false || false, false);
    }
}