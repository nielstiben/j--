package pass;

public class Bitwise {
    public int unaryComplement(int x) {
        return ~x;
    }
    public boolean or(boolean x, boolean y){
        return x | y;
    }
    public boolean exclusiveOr(boolean x, boolean y){
        return x ^ y;
    }
    public boolean and(boolean x, boolean y){
        return x & y;
    }
}
