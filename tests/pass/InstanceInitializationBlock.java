package pass;
public class InstanceInitializationBlock {

    public static String bananas = "ananas";
    {   
        int a = 4;
        bananas = "apples";
    }

    static {
        int b = 4;
    }


    public static void main(String[] args) {
        
        
        {

        }


    }
}

public class Test {
    public Test() {
        int a = 4;
        InstanceInitializationBlock.bananas = "potatoes";
    }
}
