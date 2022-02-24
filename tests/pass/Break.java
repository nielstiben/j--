package pass;

public class Break {
    public void test() {
        int i = 0;
        while (true) {
            if (i == 10) {
                break;
            }
            i++;
        }
    }
}
