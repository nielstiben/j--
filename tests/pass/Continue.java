package pass;

public class Continue {
    public void test() {
        int i = 0;
        while (i < 10) {
            i++;
            if (i == 5) {
                continue;
            }
        }
    }
}
