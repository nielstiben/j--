// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a for-statement.
 */

class JForEachStatement extends JStatement {

    /** The body. */
    private JStatement body;
    private Type type;
    private JExpression expres;
    private int arrayOffset;
    private int localOffset;

    private JVariableDeclaration varDecs;
    private JVariableDeclarator varDec;


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

    public JForEachStatement(int line, JStatement body, Type type, JExpression expres) {
        super(line);
        this.body = body;
        this.type = type;
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
        localOffset = ((LocalContext) context).nextOffset();
        LocalVariableDefn offset = new LocalVariableDefn(Type.INT, localOffset);
        context.addEntry(line, "offsetDefn", offset);

        arrayOffset = ((LocalContext) context).nextOffset();
        LocalVariableDefn arrayOffsetDefn = new LocalVariableDefn(Type.INT, arrayOffset);
        context.addEntry(line, "arrayOffsetDefn", arrayOffsetDefn);

        JExpression init = (JExpression )new JLiteralInt(line, "0");

        type = type.resolve(context);
        varDec = new JVariableDeclarator(line,name,  type, init);
        ArrayList<JVariableDeclarator> decs = new ArrayList<JVariableDeclarator>();
        decs.add(varDec);
        ArrayList<String> mods = new ArrayList<String>();

        varDecs = new JVariableDeclaration(line, mods, decs);

        varDecs = (JVariableDeclaration) varDecs.analyze(context);
        iterator = new JVariable(line, name);
        iterator = (JVariable) iterator.analyze(context);



        JExpression expression = expres.analyze(context);
        if (!expression.type().isArray()) {
            JAST.compilationUnit.reportSemanticError(line, "Array type expected");
        }
        body.analyze(context);



    }

    /**
     * Generates code for the for loo<JWildExpressionp.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        String loopLabel = output.createLabel();
        String exitLabel = output.createLabel();
        expres.codegen(output);
        output.addNoArgInstruction(ARRAYLENGTH);
        output.addOneArgInstruction(ISTORE, arrayOffset);

        output.addNoArgInstruction(ICONST_0);
        output.addOneArgInstruction(ISTORE, localOffset);

        output.addLabel(loopLabel);
        output.addBranchInstruction(IF_ICMPGT, exitLabel);

        expres.codegen(output);
        output.addOneArgInstruction(ILOAD, localOffset);
        output.addNoArgInstruction(IALOAD);

        forInit.codegenStore()


        body.codegen(output);

        output.addOneArgInstruction(IINC, localOffset);
        output.addBranchInstruction(GOTO, loopLabel);

        output.addLabel(exitLabel);

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
    