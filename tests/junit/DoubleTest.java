package junit;

import junit.framework.TestCase;
import pass.Doubles;

public class DoubleTest extends TestCase {
    public void testDoubles() {
        Doubles d = new Doubles();
        assertEquals(5.0, d.addDouble(3.5, 1.5));
        assertEquals(2.0, d.subDouble(3.5, 1.5));
        assertEquals(4.5, d.mulDouble(3, 1.5));
        assertEquals(6.0, d.divDouble(9, 1.5));
//        assertEquals(4.5, d.doublePlusPlus(3.5));
//        assertEquals(2.5, d.doubleMinusMinus(3.5));
    }
}
