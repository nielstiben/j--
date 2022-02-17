package pass;

public class Assign {
    public int multiplyAssign(int toAssign, int multiplyBy) {
        toAssign *= multiplyBy;
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

    public int exorEqualsAssign(int toAssign, int shiftBy) {
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
