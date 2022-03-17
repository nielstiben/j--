package pass;

public class CompoundAssign {
    public int incrementAssign(int toAssign, int multiplyBy) {
        toAssign += multiplyBy;
        return toAssign;
    }

    public int decrementAssign(int toAssign, int multiplyBy) {
        toAssign -= multiplyBy;
        return toAssign;
    }

    public int multiplyAssign(int toAssign, int multiplyBy) {
        toAssign *= multiplyBy;
        return toAssign;
    }

    public int divideAssign(int toAssign, int multiplyBy) {
        toAssign /= multiplyBy;
        return toAssign;
    }

    public int remainderAssign(int toAssign, int divideBy) {
        toAssign %= divideBy;
        return toAssign;
    }

    public int signedBitShiftLeftAssign(int toAssign, int shiftBy) {
        toAssign <<= shiftBy;
        return toAssign;
    }

    public int signedBitShiftRightAssign(int toAssign, int shiftBy) {
        toAssign >>= shiftBy;
        return toAssign;
    }

    public int unsignedBitShiftRightAssign(int toAssign, int shiftBy) {
        toAssign >>>= shiftBy;
        return toAssign;
    }

    public int exOrEqualsAssign(int toAssign, int shiftBy) {
        toAssign ^= shiftBy;
        return toAssign;
    }

    public int AndEqualsAssign(int toAssign, int equals) {
        toAssign &= equals;
        return toAssign;
    }

    public int OrEqualsAssign(int toAssign, int equals) {
        toAssign |= equals;
        return toAssign;
    }
}
