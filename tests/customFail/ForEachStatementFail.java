class ForEachStatementFail {
    public static void main(String[] args) {
        int [] ints = new int[10];
        String [] strings = new String[10];

        for (String s, d ,f ,g: strings) {

        }


        for (int nr: ints) {
            -{}-
        }
    }
}