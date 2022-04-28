// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a {@code exception} literal.
 */

class JLiteralException extends JExpression {

    /** String representation of the . */
    private JExpression expr;

    /**
     * Constructs an AST node for a {@code exception} literal given its line number 
     * and text representation.
     * 
     * @param line
     *            line in which the literal occurs in the source file.
     * @param expr
     *            string representation of the literal.
     */

    public JLiteralException(int line, JExpression expr) {
        super(line);
        this.expr = expr;
    }

    /**
     * Analyzing a exception literal is trivial.
     * 
     * @param context
     *            context in which names are resolved (ignored here).
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        type = Type.EXCEPTION;
        return this;
    }

    /**
     * Generating code for a exception literal means generating code to push it onto
     * the stack.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
       
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        if (expr != null) {
            p.printf("<JReturnStatement line=\"%d\">\n", line());
            p.indentRight();
            expr.writeToStdOut(p);
            p.indentLeft();
            p.printf("</JReturnStatement>\n");
        } else {
            p.printf("<JReturnStatement line=\"%d\"/>\n", line());
        }
    }

}
