// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package fail;

import java.lang.Integer;
import java.lang.System;

// This program has syntax errors and shouldn't compile.

public class SyntaxErrors {

    public static int factorial(int n) {
        int result = 1;
        for (int i = 1 i <= n i++) {
            result *= i;
        }
        return result;
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        System.out.println(x + "! = " + factorial(x));
    }

    public static void faultyForLoops() {

    }

    public static void faultyForLoops() {
        for (double i = 0.0; i < 10.0; i=i+"2") {
            // String is not a number to increment by
        }

        for (int i = 0; 13; i++) {
            // 13 is an integer, not a boolean
        }
}

}
