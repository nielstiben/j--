// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

import java.util.ArrayList;

/**
 * This abstract base class is the AST node for a binary expression.
 * A binary expression has an operator and two operands: a lhs and a rhs.
 */

abstract class JBinaryExpression extends JExpression {

    /** The binary operator. */
    protected String operator;

    /** The lhs operand. */
    protected JExpression lhs;

    /** The rhs operand. */
    protected JExpression rhs;

    /**
     * Constructs an AST node for a binary expression given its line number, the
     * binary operator, and lhs and rhs operands.
     *
     * @param line
     *            line in which the binary expression occurs in the source file.
     * @param operator
     *            the binary operator.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    protected JBinaryExpression(int line, String operator, JExpression lhs,
            JExpression rhs) {
        super(line);
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JBinaryExpression line=\"%d\" type=\"%s\" "
                + "operator=\"%s\">\n", line(), ((type == null) ? "" : type
                .toString()), Util.escapeSpecialXMLChars(operator));
        p.indentRight();
        p.printf("<Lhs>\n");
        p.indentRight();
        lhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Lhs>\n");
        p.printf("<Rhs>\n");
        p.indentRight();
        rhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Rhs>\n");
        p.indentLeft();
        p.printf("</JBinaryExpression>\n");
    }

}

/**
 * The AST node for a plus (+) expression. In j--, as in Java, + is overloaded
 * to denote addition for numbers and concatenation for Strings.
 */

class JPlusOp extends JBinaryExpression {

    /**
     * Constructs an AST node for an addition expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line
     *            line in which the addition expression occurs in the source
     *            file.
     * @param lhsJ
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JPlusOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "+", lhs, rhs);
    }

    /**
     * Analysis involves first analyzing the operands. If this is a string
     * concatenation, we rewrite the subtree to make that explicit (and analyze
     * that). Otherwise we check the types of the addition operands and compute
     * the result type.
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.DOUBLE, Type.STRING);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.DOUBLE, Type.STRING);

        if (lhs.type() == Type.STRING || rhs.type() == Type.STRING) {
            // string concat
            return (new JStringConcatenationOp(line, lhs, rhs))
                    .analyze(context);
        } else if (lhs.type() == Type.DOUBLE || rhs.type() == Type.DOUBLE) {
            // one of them is double, so out type is double
            type = Type.DOUBLE;
        } else if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            // both are int, return type is int
            type = Type.INT;
        } else {
            // typechecking fails
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for +");
        }
        return this;
    }

    /**
     * Any string concatenation has been rewritten as a
     * {@link JStringConcatenationOp} (in {@code analyze}), so code generation
     * here involves simply generating code for loading the operands onto the
     * stack and then generating the appropriate add instruction.
     *
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IADD);
        } else if (type == Type.DOUBLE) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DADD);
        }
    }

}

/**
 * The AST node for a subtraction (-) expression.
 */

class JSubtractOp extends JBinaryExpression {

    /**
     * Constructs an AST node for a subtraction expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line
     *            line in which the subtraction expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JSubtractOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "-", lhs, rhs);
    }

    /**
     * Analyzing the - operation involves analyzing its operands, checking
     * types, and determining the result type.
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.DOUBLE);
        rhs.type().mustMatchOneOf(line(), Type.INT, Type.DOUBLE);
        if ((lhs.type() == Type.DOUBLE) || (rhs.type() == Type.DOUBLE)) {
          type = Type.DOUBLE;
        } else {
          type = Type.INT;
        }
        return this;
    }

    /**
     * Generating code for the - operation involves generating code for the two
     * operands, and then the subtraction instruction.
     *
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        if (type == Type.INT) {
            output.addNoArgInstruction(ISUB);
        } else if (type == Type.DOUBLE) {
            output.addNoArgInstruction(DSUB);
        }
    }

}

/**
 * The AST node for a multiplication (*) expression.
 */

class JMultiplyOp extends JBinaryExpression {

    /**
     * Constructs an AST for a multiplication expression given its line number,
     * and the lhs and rhs operands.
     *
     * @param line
     *            line in which the multiplication expression occurs in the
     *            source file.
     * @param lhsif (!lhs.type().mustMatchExpected(line(), Type.BOOLEAN)){
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
            "Invalid operand types for %");
        }
     * @param rhs
     *            the rhs operand.
     */

    public JMultiplyOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "*", lhs, rhs);
    }

    /**
     * Analyzing the * operation involves analyzing its operands, checking
     * types, and determining the result type.
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);

        if (lhs.type() == Type.DOUBLE || rhs.type() == Type.DOUBLE) {
            // one of them is double, so out type is double
            type = Type.DOUBLE;
        } else if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            // both are int, return type is int
            type = Type.INT;
        } else {
            // shrug we dunno.
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for *");
        }
        return this;
    }
    

    /**
     * Generating code for the * operation involves generating code for the two
     * operands, and then the multiplication instruction.
     *
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        if (type == Type.INT) {
            output.addNoArgInstruction(IMUL);
        } else if (type == Type.DOUBLE) {
            output.addNoArgInstruction(DMUL);
        }
    }

}

class JDivideOp extends JBinaryExpression {
    public JDivideOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "/", lhs, rhs);
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);

        if (lhs.type() == Type.DOUBLE || rhs.type() == Type.DOUBLE) {
            // one of them is double, so out type is double
            type = Type.DOUBLE;
        } else if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            // both are int, return type is int
            type = Type.INT;
        } else {
            // shrug we dunno.
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for /");
        }
        return this;
    }

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        if (type == Type.INT) {
        output.addNoArgInstruction(IDIV);
        }
        else if (type == Type.DOUBLE) {
        output.addNoArgInstruction(DDIV);
        }
    }
}





class JTernaryOp extends JBinaryExpression {
    private JExpression rhs2;
    public JTernaryOp(int line, JExpression lhs, JExpression rhs, JExpression rh2) {
        super(line, "?", lhs, rhs);
        this.rhs2 = rh2;
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        rhs2 = (JExpression) rhs2.analyze(context);
        lhs.type().mustMatchExpected(line(), Type.BOOLEAN);
        
        if (rhs.type == rhs2.type){
            type = rhs.type;
        } else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for ?");
        }

        return this;
    }

    public void codegen(CLEmitter output) {
        String alternate = output.createLabel();
        String end = output.createLabel();
        lhs.codegen(output, alternate, false);
        rhs.codegen(output);
        output.addBranchInstruction(GOTO, end);
        output.addLabel(alternate);
        rhs2.codegen(output);
        output.addLabel(end);
    }


    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JBinaryExpression line=\"%d\" type=\"%s\" "
                + "operator=\"%s\">\n", line(), ((type == null) ? "" : type
                .toString()), Util.escapeSpecialXMLChars(operator));
        p.indentRight();
        p.printf("<Lhs>\n");
        p.indentRight();
        lhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Lhs>\n");
        p.printf("<Rhs>\n");
        p.indentRight();
        rhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Rhs>\n");
        p.indentLeft();
        p.printf("<Rhs2>\n");
        p.indentRight();
        rhs2.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Rhs2>\n");
        p.indentLeft();
        p.printf("</JBinaryExpression>\n");
    }

}


class JRemainderOp extends JBinaryExpression {
    public JRemainderOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "%", lhs, rhs);
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);

        if (lhs.type() == Type.DOUBLE || rhs.type() == Type.DOUBLE) {
            // one of them is double, so out type is double
            type = Type.DOUBLE;
        } else if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            // both are int, return type is int
            type = Type.INT;
        } else {
            // shrug we dunno.
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for %");
        }
        return this;
    }

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(IREM);
    }
}

class JOrOp extends JBooleanBinaryExpression {
    public JOrOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "|", lhs, rhs );
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchExpected(line(), Type.BOOLEAN);
        rhs.type().mustMatchExpected(line(), Type.BOOLEAN);
        type = Type.BOOLEAN;
        return this;
    }

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        if (onTrue)  {
            lhs.codegen(output, targetLabel, true);
            rhs.codegen(output, targetLabel, true);
        } else {
            String falseLabel = output.createLabel();
            lhs.codegen(output, falseLabel, true);
            rhs.codegen(output, targetLabel, false);
            output.addLabel(falseLabel);
        }
    }
}

class JXorOp extends JBooleanBinaryExpression {
    public JXorOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "^", lhs, rhs );
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchExpected(line(), Type.BOOLEAN);
        rhs.type().mustMatchExpected(line(), Type.BOOLEAN);
        type = Type.BOOLEAN;
        return this;
    }

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        if (onTrue)  {
            lhs.codegen(output, targetLabel, true);
            rhs.codegen(output, targetLabel, true);
        } else {
            String falseLabel = output.createLabel();
            lhs.codegen(output, falseLabel, true);
            rhs.codegen(output, targetLabel, false);
            output.addLabel(falseLabel);
        }
    }
}

class JAndOp extends JBooleanBinaryExpression {
    public JAndOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "&", lhs, rhs );
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchExpected(line(), Type.BOOLEAN);
        rhs.type().mustMatchExpected(line(), Type.BOOLEAN);
        type = Type.BOOLEAN;
        return this;
    }

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        if (onTrue)  {
            lhs.codegen(output, targetLabel, false);
            rhs.codegen(output, targetLabel, true);
        } else {
            String falseLabel = output.createLabel();
            lhs.codegen(output, falseLabel, false);
            rhs.codegen(output, targetLabel, false);
            output.addLabel(falseLabel);
        }
    }
}

class JShiftLeftOp extends JBinaryExpression {
    public JShiftLeftOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "<<", lhs, rhs);
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchExpected(line(), Type.INT);
        rhs.type().mustMatchExpected(line(), Type.INT);
        type = Type.INT;

        return this;
    }

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(ISHL);
    }
}

class JShiftRightOp extends JBinaryExpression {
    public JShiftRightOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>", lhs, rhs);
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchExpected(line(), Type.INT);
        rhs.type().mustMatchExpected(line(), Type.INT);
        type = Type.INT;

        return this;
    }

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(ISHR);
    }
}

class JUShiftRightOp extends JBinaryExpression {
    public JUShiftRightOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>>", lhs, rhs);
    }

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchExpected(line(), Type.INT);
        rhs.type().mustMatchExpected(line(), Type.INT);
        type = Type.INT;

        return this;
    }

    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addNoArgInstruction(IUSHR);
    }
}
