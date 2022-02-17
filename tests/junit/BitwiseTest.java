package junit;

import junit.framework.TestCase;
import pass.Bitwise;

public class BitwiseTest extends TestCase {
    private Bitwise bitwise;

    protected void setUp() throws Exception {
        super.setUp();
        bitwise = new Bitwise();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

//    public void testUnaryComplement() {
//        this.assertEquals(-5, bitwise.unaryComplement(4));
//        this.assertEquals(-9, bitwise.unaryComplement(8));
//        this.assertEquals(7, bitwise.unaryComplement(-8));
//    }

    public void testOr() {
        this.assertEquals(true, bitwise.or(true, true));
        this.assertEquals(true, bitwise.or(true, false));
        this.assertEquals(true, bitwise.or(false, true));
        this.assertEquals(false, bitwise.or(false, false));
    }

//    public void testExclusiveOr() {
//        this.assertEquals(false, bitwise.exclusiveOr(true, true));
//        this.assertEquals(true, bitwise.exclusiveOr(true, false));
//        this.assertEquals(true, bitwise.exclusiveOr(false, true));
//        this.assertEquals(false, bitwise.exclusiveOr(false, false));
//    }
//
//    public void testAnd() {
//        this.assertEquals(true, bitwise.and(true, true));
//        this.assertEquals(false, bitwise.and(true, false));
//        this.assertEquals(false, bitwise.and(false, true));
//        this.assertEquals(false, bitwise.and(false, false));
//    }
}
