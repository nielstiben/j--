package pass;

interface TestInterface {
    public int f(int x);
}

class TestClass implements TestInterface {
    public int f(int x) {
        return x * x;
    }
}

