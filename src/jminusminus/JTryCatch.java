// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

import java.util.ArrayList;

/**
 * The AST node for a for-statement.
 */

class JTryCatch extends JStatement {

    /** The body. */
    private JStatement tryBody;
    private ArrayList<JStatement> catchBodies;
    private ArrayList <JFormalParameter> cParameters;
    private JStatement finalBody;
    


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

    public JTryCatch(int line, JStatement tryBody, ArrayList<JStatement> catchBodies, ArrayList<JFormalParameter> cParameters, JStatement finalBody) {
        super(line);
        this.tryBody = tryBody;
        this.catchBodies = catchBodies;
        this.cParameters = cParameters;
        this.finalBody = finalBody;
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
        //tryBody = (JStatement) tryBody.analyze(context);
       // catchBody = (JStatement) catchBody.analyze(context);
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
            
        for (JFormalParameter cParameter : cParameters) {
            cParameter.writeToStdOut(p);    
        }  
        p.print("</CatchParameter>");
        p.println();
        p.printf("<CatchBody>\n");
        for (JStatement catchBody : catchBodies) {
            catchBody.writeToStdOut(p);    
        }
        p.printf("</Catch>\n");
        
        if (finalBody != null){
            p.printf("<Finally>");
            p.println();
            finalBody.writeToStdOut(p);
            p.printf("</Finally>");
            p.println();
        }
        
        p.printf("</TryCatch>\n");
    }
}
