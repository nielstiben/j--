package pass;//package pass;

public class BitwiseOperators {

    public int unaryComplement(int x) {
        return ~x;
    }

    public int signedShiftLeft(int x, int y) {
        return x << y;
    }

    public int signedShiftRight(int x, int y) {
        return x >> y;
    }

    public int unsignedShiftRight(int x, int y) {
        return x >>> y;
    }

    public int bitwiseAnd(int x, int y) {
        return x & y;
    }

    public int bitwiseOr(int x, int y) {
        return x | y;
    }

    public int bitwiseXor(int x, int y) {
        return x ^ y;
    }
}