package junit;

import junit.framework.TestCase;
import pass.Bitwise;

public class RemainderTest extends TestCase {
    private Bitwise bitwis;

    protected void setUp() throws Exception {
        super.setUp();
        bitwis = new Bitwise();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(bitwis.remain(9, 3), 0);
        this.assertEquals(bitwis.remain(10, 3), 1);
        this.assertEquals(bitwis.remain(25, 10), 5);
    }
}
