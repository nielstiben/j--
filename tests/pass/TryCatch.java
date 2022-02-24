package pass;

public class TryCatch {
    public void tryCatch(){
        try {
            int divideNull = 2/0;
        } catch (Exception e) {
            int exceptionOcurred = 1;
        }
    }
}
