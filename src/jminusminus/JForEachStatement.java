// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a for-statement.
 */

class JForEachStatement extends JStatement {

    /** The body. */
    private JStatement body;
    private JVariableDeclaration forInit;
    private JExpression expres;


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

    public JForEachStatement(int line, JStatement body, JVariableDeclaration forInit, JExpression expres) {
        super(line);
        this.body = body;
        this.forInit = forInit;
        this.expres = expres;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JForEachStatement analyze(Context context) {
        forInit.analyze(context);
        expres.analyze(context);
       if (!expres.type().isArray()){
        JAST.compilationUnit.reportSemanticError(line,
         "Expression must be of type []");
       }
       
        body = (JStatement) body.analyze(context);
        return this;
    }

    /**
     * Generates code for the for loo<JWildExpressionp.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        boolean isLoopDone = false;
        String test = output.createLabel();
        String out = output.createLabel();

        // Branch out of the loop on the test condition
        // being false
        forInit.codegen(output);
        output.addLabel(test);
        

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



/*
    private JStatement body;
    private JVariableDeclaration forInit;
    private JStatement forUpdate;
    private JExpression expres;
*/

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<ForEachStatement line=\"%d\">\n", line());
        p.printf("<forInit>\n");
        forInit.writeToStdOut(p);
        p.printf("</forInit>\n");
        p.print("<expres>");
        p.println();
        expres.writeToStdOut(p);
        p.print("</expres>");
        p.println();
        p.printf("<Body>\n");
        body.writeToStdOut(p);
        p.printf("</Body>\n");
        p.printf("</ForEachStatement>\n");
    }
    }
    