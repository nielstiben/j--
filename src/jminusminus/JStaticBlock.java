// Copyright 2011 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

/**
 * The AST node for a StaticBlock declaration.
 */

class JStaticBlock extends JAST implements JMember {

    /**
     * Method body.
     */
    protected JBlock body;

    /**
     * Built in analyze().
     */
    protected MethodContext context;

    /**
     * Computed by preAnalyze().
     */
    protected String descriptor;


    /**
     * Constructs an AST node for a method declaration given the
     * line number, method name, return type, formal parameters,
     * and the method body.
     *
     * @param line       line in which the method declaration occurs
     *                   in the source file.
     * @param mods       modifiers.
     * @param name       method name.
     * @param returnType return type.
     * @param params     the formal parameters.
     * @param body       method body.
     */

    public JStaticBlock(int line, JBlock body) {
        super(line);
        this.body = body;
       
    }

    /**
     * Declares this method in the parent (class) context.
     *
     * @param context the parent (class) context.
     * @param partial the code emitter (basically an abstraction
     *                for producing the partial class).
     */

    public void preAnalyze(Context context, CLEmitter partial) {
        // Resolve types of the formal parameters
      
        // Compute descriptor
        descriptor = "(";
    
        // Generate the method with an empty body (for now)
        partialCodegen(context, partial);
    }

    /**
     * Analysis for a method declaration involves (1) creating a
     * new method context (that records the return type; this is
     * used in the analysis of the method body), (2) bumping up
     * the offset (for instance methods), (3) declaring the
     * formal parameters in the method context, and (4) analyzing
     * the method's body.
     *
     * @param context context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JAST analyze(Context context) {
        
       

        
        return this;
    }

    /**
     * Adds this method declaration to the partial class.
     *
     * @param context the parent (class) context.
     * @param partial the code emitter (basically an abstraction
     *                for producing the partial class).
     */

    public void partialCodegen(Context context, CLEmitter partial) {
    
    }

    /**
     * Generates code for the method declaration.
     *
     * @param output the code emitter (basically an abstraction
     *               for producing the .class file).
     */

    public void codegen(CLEmitter output) {
        
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JStaticBlock> line=" + "\""+line+"\"");
        p.indentRight();
        if (context != null) {
            context.writeToStdOut(p);
        }
        if (body != null) {
            System.out.println();
            p.println("<Body>");
            p.indentRight();
            body.writeToStdOut(p);
            p.indentLeft();
            p.println("</Body>");
        }
        p.indentLeft();
        p.println("</JStaticBlock>");
    }

}