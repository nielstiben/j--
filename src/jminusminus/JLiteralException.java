// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a {@code exception} literal.
 */

class JLiteralException extends JExpression {

    /** String representation of the . */
    private String text;

    /**
     * Constructs an AST node for a {@code exception} literal given its line number 
     * and text representation.
     * 
     * @param line
     *            line in which the literal occurs in the source file.
     * @param text
     *            string representation of the literal.
     */

    public JLiteralException(int line, String text) {
        super(line);
        this.text = text;
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
        p.printf("<JLiteralException line=\"%d\" type=\"%s\" " + "value=\"%s\"/>\n",
                line(), ((type == null) ? "" : type.toString()), Util
                        .escapeSpecialXMLChars(text));
    }

}
