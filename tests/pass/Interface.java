package pass;
import java.lang.System;

interface BananaCaller {
    void callBanana();
    abstract void eatBanana();
}

public class Interface implements BananaCaller {
    public void callBanana() {
        System.out.println("Hello, banana is speaking!");
    }

    public void eatBanana() {
        System.out.println("Help! Don't eat the banana!");
    }
}
