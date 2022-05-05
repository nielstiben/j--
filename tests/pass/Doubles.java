
package pass;
import java.lang.System;
public class Doubles {
    public void init() {
        double d = 1.1;
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(6.5+3.5);
    }
    public double addDouble(double d, double i) {
        return d + i;
    }

    public int addInt(int d, int i) {
        return d + i;
    }

    public double subDouble(double d, double i) {
        return d - i;
    }

    public double mulDouble(double d, double i) {
        return d * i;
    }

    public double divDouble(double d, double i) {
        return d / i;
    }

//    public double doublePlusPlus(double d) {
//        d++;
//        return d;
//    }
//
//    public double doubleMinusMinus(double d) {
//        d--;
//        return d;
//    }
}