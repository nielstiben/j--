// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

/**
 * The AST node for a elvis (?) expression.
 */

class JElvisExpression extends JExpression {

    private JExpression test;
    private JExpression consequent;
    private JExpression alternate;

    public JElvisExpression(int line, JExpression test, JExpression consequent, JExpression alternate) {
        super(line);
        this.test = test;
        this.consequent = consequent;
        this.alternate = alternate;
    }


    public JExpression analyze(Context context) {
        test = test.analyze(context);
        consequent = consequent.analyze(context);
        alternate = alternate.analyze(context);
        test.type().mustMatchExpected(line(), Type.BOOLEAN);
        if (!consequent.type().equals(alternate.type())) {
            JAST.compilationUnit.reportSemanticError(line(), "consequent and alternate must be of same type.");
        }
        type = consequent.type();
        return this;
    }


    public void codegen(CLEmitter output) {
        test.codegen(output);
    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<TernaryOP line=\"%d\" type=\"%s\">\n", line(),
                ((type == null) ? "" : type.toString()));
        p.indentRight();
        p.printf("<Consequent>\n");
        p.indentRight();
        consequent.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Consequent>\n");
        p.printf("<Alternate>\n");
        p.indentRight();
        alternate.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Alternate>\n");
        p.indentLeft();
        p.printf("</TernaryOP>\n");
    }

}

