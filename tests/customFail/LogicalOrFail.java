public class LogicalOrFail {

    public static void main(String[] args) {
       if ("string" || true){}

       if (true || "string"){}

       if (1 || "string"){}

       if (true || 1){}
    }
}