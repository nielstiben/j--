package pass;

public class Continue {
    public void test() {
        int i = 10;
        while (i > 0){
            i--;
            if (i == 5) {
                continue;
            }
        }
    }
}
