package pass;

public class ReservedWords {
    public void testBreak() {
        for (int i = 0; i < 5; i++) {
            break;
        }
    }

    public void testByte() {
        byte a = 0;
    }

    public void testCaseWord() {
        int a = 1;
        switch (a) {
            case 0:
                int b = 0;
            case 1:
                int c = 0;
        }
    }

    public void testCatch() {
        try {
            int a = 0;
        } catch (Exception e) {
            int b = 0;
        }
    }

    public void testChar() {
        char a = 'a';
    }

    public void testClass() {
        class A {
            int a = 0;
        }
    }

    public void testConst() {
        final int a = 0;
    }

    public void testContinue() {
        for (int i = 0; i < 5; i++) {
            continue;
        }
    }

    public void testDo() {
        do {
            int a = 0;
        } while (true);
    }

    public void testDouble() {
        double a = 0.0;
    }

    public void testElse() {
        if (true) {
            int a = 0;
        } else {
            int b = 0;
        }
    }

    public void testExtends() {
        class A extends B {
            int a = 0;
        }
    }

    public void testFalse() {
        boolean a = false;
    }

    public void testFinal() {
        final int a = 0;
    }

    public void testFinally() {
        try {
            int a = 0;
        } catch (Exception e) {
            int b = 0;
        } finally {
            int c = 0;
        }
    }

    public void testFloat() {
        float a = 0.0f;
    }

    public void testFor() {
        for (int i = 0; i < 5; i++) {
            int a = 0;
        }
    }

    public void testGoto() {
        goto a;
        int a = 0;
        a:
        int b = 0;
    }

    public void testIf() {
        if (true) {
            int a = 0;
        }
    }

//    public void testImplements() {
//        interface B {
//        }
//        class A extends B {
//            int a = 0;
//        }
//    }

//    public void testImport() {
//        import java.util.List;
//    }

    public void testInstanceof() {
        Object a = new Object();
        if (a instanceof A) {
            int b = 0;
        }
    }

    public void testInt() {
        int a = 0;
    }

//    public void testInterface() {
//        interface A {
//            int a = 0;
//        }
//    }

    public void testLong() {
        long a = 0L;
    }

//    public void testNative() {
//        native int a();
//    }

    public void testNew() {
        new Object();
    }

    public void testNull() {
        Object a = null;
    }

//    public void testPackage() {
//        package a;
//    }

    public void testPrivate() {
        class A {
            private int a = 0;
        }
    }

    public void testProtected() {
        class A {
            protected int a = 0;
        }
    }

    public void testPublic() {
        class A {
            public int a = 0;
        }
    }

    public void testReturn() {
        return;
    }

    public void testShort() {
        short a = 0;
    }

//    public void testStatic() {
//        static int a = 0;
//    }

//    public void testStrictfp() {
//        strictfp int a = 0;
//    }

    public void testSuper() {
        class A {
            int a = 0;
        }
        class B extends A {
            int b = 0;
        }
        B b = new B();
        b.a = 0;
        b.b = 0;
    }

    public void testSwitch() {
        int a = 0;
        switch (a) {
            case 0:
                int b = 0;
            case 1:
                int c = 0;
        }
    }

    public void testSynchronized() {
        synchronized (this) {
            int a = 0;
        }
    }
    public void testThis() {
        class A {
            int a = 0;
        }
        A a = new A();
        a.a = 0;
    }

//    public void testThrow() {
//        throw new Exception();
//    }

    public void testThrows() {
        class A {
            int a = 0;
        }
        class B extends A {
            int b = 0;
        }
        B b = new B();
        b.a = 0;
        b.b = 0;
    }

    public void testTransient() {
        class A {
            int a = 0;
        }
        class B extends A {
            int b = 0;
        }
        B b = new B();
        b.a = 0;
        b.b = 0;
    }

    public void testTrue() {
        boolean a = true;
    }

    public void testTry() {
        try {
            int a = 0;
        } catch (Exception e) {
            int b = 0;
        }
    }

//    public void testVoid() {
//        void a() {
//        }
//    }

    public void testWhile() {
        while (true) {
            int a = 0;
        }
    }
}
