package junit;

import junit.framework.TestCase;
import pass.Shift;

public class ShiftTest extends TestCase {
    private Shift shift;

    protected void setUp() throws Exception {
        super.setUp();
        shift = new Shift();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSignedShiftLeft() {
        this.assertEquals("a", this.shift.signedShiftLeft(4,1));
    }

    public void testSignedShiftRight() {
        this.assertEquals("b", this.shift.signedShiftRight(-3,1));
    }

    public void testUnsignedShiftRight() {
        this.assertEquals("c", this.shift.unsignedShiftRight(5, 2));
    }
}
