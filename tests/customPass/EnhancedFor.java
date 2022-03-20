import java.util.ArrayList;

public class EnhancedFor    {
    public static void main(String[] args) {

        ArrayList a = new ArrayList<Integer>();
        a.add(1);
        a.add(2);
        a.add(3);

        for (Object object : a) {
            System.out.println(a);
        }
    }
}
