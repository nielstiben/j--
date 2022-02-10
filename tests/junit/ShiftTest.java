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
        this.assertEquals(8, this.shift.signedShiftLeft(4,1));
        this.assertEquals(16, this.shift.signedShiftLeft(4,2));
        this.assertEquals(12, this.shift.signedShiftLeft(3,2));
    }

    public void testSignedShiftRight() {
        this.assertEquals(-2, this.shift.signedShiftRight(-3,1));
        this.assertEquals(1, this.shift.signedShiftRight(4,2));
        this.assertEquals(4, this.shift.signedShiftRight(17,2));
    }

    public void testUnsignedShiftRight() {
        this.assertEquals(1, this.shift.unsignedShiftRight(5, 2));
        this.assertEquals(7, this.shift.unsignedShiftRight(15, 1));
    }
}
