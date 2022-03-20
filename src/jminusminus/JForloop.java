// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

import java.time.chrono.JapaneseChronology;

/**
 * The AST node for a for-statement.
 */

class JForloop extends JStatement {

    /** Test expression. */
    private JExpression condition;

    /** The body. */
    private JStatement body;

    private JAssignment assignement;

    private JExpression expression;

    private JUnaryExpression unaryExpression;

    /**
     * Constructs an AST node for a for-statement given its line number, the
     * test expression, and the body.
     * 
     * @param line
     *            line in which the for-statement occurs in the source file.
     * @param condition
     * 
     * @param assignment
     * 
     * @param comparison
     *            test expression.
     * @param body
     *            
     */

    public JForloop(int line, JAssignment assignment, JExpression expression, JUnaryExpression unaryExpression, JStatement body) {
        super(line);
        this.assignement = assignment;
        this.expression = expression;
        this.unaryExpression = unaryExpression;
        this.body = body;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JForloop analyze(Context context) {
        //TODO : Needs to be implementet- this has been taken from whileloops
        body = (JStatement) body.analyze(context);
        return this;
    }

    /**
     * Generates code for the for loop.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        //TODO : Needs to be implementet- this has been taken from whileloops
        // Need two labels
        String test = output.createLabel();
        String out = output.createLabel();

        // Branch out of the loop on the test condition
        // being false
        output.addLabel(test);
        condition.codegen(output, out, false);

        // Codegen body
        body.codegen(output);

        // Unconditional jump back up to test
        output.addBranchInstruction(GOTO, test);

        // The label below and outside the loop
        output.addLabel(out);
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JWhileStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<TestExpression>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TestExpression>\n");
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.indentLeft();
        p.printf("</JWhileStatement>\n");
    }

}
