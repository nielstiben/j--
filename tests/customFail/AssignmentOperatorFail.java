class AssignmentOperatorFail {
public static void main(String[]args){
        int    intA = 19;
        double doubleB = 19.0;
        String stringC = "lol";

        String e;

        intA = "string";
        doubleB = "string";
        intA = doubleB;

        e = stringC -= intA;
        e = stringC -= doubleB;
        e = stringC -= stringC;

        e = stringC /= intA;
        e = stringC /= doubleB;
        e = stringC /= stringC;

        e = stringC *= intA;
        e = stringC *= doubleB;
        e = stringC *= stringC;

       // e = stringC %= intA;
        e = stringC %= doubleB;
        //e = stringC %= stringC;

        }
}