package junit;

import junit.framework.TestCase;
import pass.Remainder;

public class RemainderTest extends TestCase {
    private Remainder remainder;

    protected void setUp() throws Exception {
        super.setUp();
        remainder = new Remainder();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(remainder.remain(9, 3), 0);
        this.assertEquals(remainder.remain(10, 3), 1);
        this.assertEquals(remainder.remain(25, 10), 5);
    }
}
