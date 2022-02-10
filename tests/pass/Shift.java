package pass;

public class Shift {
    public int signedShiftLeft(int x, int y) {
        return x << y;
    }
    public int signedShiftRight(int x, int y) {
        return x >> y;
    }
    public int unsignedShiftRight(int x, int y) {
        return x >>> y;
    }
}
