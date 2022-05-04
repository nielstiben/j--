public class SimpleTryCatchFinally {
    public static void main(String[] args) {
        int a = 0;
        try {
             a = 44;
        } catch (Exception e) {
             a = 55;
        } finally {
             a = a + 2;
        }
    }
}
