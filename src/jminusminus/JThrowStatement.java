// Copyright 2011 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a throw-statement.
 */

class JThrowStatement extends JStatement {

    /**
     * The throw expression.
     */
    private JExpression expr;

    /**
     * Constructs an AST node for a throw-statement given its
     * line number, and the expression that is returned.
     *
     * @param line line in which the throw-statement appears
     *             in the source file.
     * @param expr the thrown expression.
     */

    public JThrowStatement(int line, JExpression expr) {
        super(line);
        this.expr = expr;
    }

    /**
     * Analysis distinguishes between being in a constructor
     * or in a regular method in checking return types. In the
     * case of a throw expression, analyze it and check types.
     *
     * @param context context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JStatement analyze(Context context) {
        if (expr != null) {
            expr = expr.analyze(context);
            if(!Throwable.class.isAssignableFrom(expr.type.classRep())) {
                JAST.compilationUnit.reportSemanticError(line(),
                        "Throw expression must be of type Throwable");
            }
            expr.type().mustMatchExpected(line(), Type.EXCEPTION);
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Throw statement must have an expression");
        }
        return this;
    }

    /**
     * Generates code for the throw statement.
     *
     * @param output the code emitter (basically an abstraction
     *               for producing the .class file).
     */

    public void codegen(CLEmitter output) {

        expr.codegen(output);
        output.addNoArgInstruction(DUP);
        output.addNoArgInstruction(ATHROW);

    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {

        p.printf("<JThrowStatement line=\"%d\">\n", line());
        p.indentRight();
        expr.writeToStdOut(p);
        p.indentLeft();
        p.printf("</JThrowStatement>\n");
    }
}
