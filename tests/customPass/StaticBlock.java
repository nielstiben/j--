public class StaticBlock {

    public static int minusminus(int a){
        return a--;
    }

    static {
       int a = 0;
        minusminus(a);
       a++;
    }
}
