// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

import static jminusminus.CLConstants.GOTO;

/**
 * The AST node for a for-statement.
 */

class JTryCatch extends JStatement {

    /** The body. */
    private JBlock tryBody;
    private ArrayList<JBlock> catchBodies;
    private ArrayList <JFormalParameter> cParameters;
    private JBlock finallyBody;




    public JTryCatch(int line, JBlock tryBody,
                     ArrayList<JBlock> catchBodies,
                     ArrayList<JFormalParameter> cParameters,
                     JBlock finallyBody) {
        super(line);
        this.tryBody = tryBody;
        this.catchBodies = catchBodies;
        this.cParameters = cParameters;
        this.finallyBody = finallyBody;

    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JTryCatch analyze(Context context) {
        tryBody = (JBlock) tryBody.analyze(context);
        if (catchBodies != null) {
            for (int i = 0; i < cParameters.size(); i++) {
                JFormalParameter param = cParameters.get(i);
                cParameters.set(i, (JFormalParameter) cParameters.get(i).analyze(context));
            }
            for (int i = 0; i < catchBodies.size(); i++) {
                catchBodies.set(i, (JBlock) catchBodies.get(i).analyze(context));

            }
        }
        if (finallyBody != null) {
            finallyBody = (JBlock) finallyBody.analyze(context);
        }
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
        //TODO : Needs to be implementet- this has been taken from whileloops
        // Need two labels
        String test = output.createLabel();
        String out = output.createLabel();

        // Branch out of the loop on the test condition
        // being false
        output.addLabel(test);


        // Codegen body
        tryBody.codegen(output);

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
        p.printf("<TryCatch line=\"%d\">\n", line());
        p.printf("<TryBody>\n");
        tryBody.writeToStdOut(p);
        p.printf("</TryBody>\n");
        p.print("<CatchParameter>");
        p.println();
        if (cParameters != null) {
            for (JFormalParameter cParameter : cParameters) {
                cParameter.writeToStdOut(p);
            }
            p.print("</CatchParameter>");
            p.println();
            p.printf("<CatchBody>\n");
            for (JStatement catchBody : catchBodies) {
                catchBody.writeToStdOut(p);
            }
        }
        p.printf("</Catch>\n");
        if (finallyBody != null) {
            p.printf("<FinallyBody>\n");
            finallyBody.writeToStdOut(p);
            p.printf("</FinallyBody>\n");
        }
        p.printf("</TryCatch>\n");
    }
}
