package junit;

import junit.framework.TestCase;

public class TryCatchTest extends TestCase {

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
        }
        finally {
            a = a + 1;
            assertEquals(a, 2);
        }

    }

}
