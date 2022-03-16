// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

class JInterfaceDeclaration extends JAST implements JTypeDecl {
    /**
     * 
     * Alot of works need to be done here since this has only been an attemp to get
     * the interface to parse correctly
     * - this class is a modified copy of the JClassDeclaration.java
     * 
     */
    /** Interface modifiers. */
    private ArrayList<String> mods;

    /** Interface name. */
    private String name;

    /** Interface block. */
    private ArrayList<JMember> interfaceBlock;

    /** Super interface type. */
    private Type superType;

    /** This interface type. */
    private Type thisType;

    /** Context for this interface. */
    private ClassContext context;

    /** Whether this interface has an explicit constructor. */
    private boolean hasExplicitConstructor;

    /**
     * Constructs an AST node for a interface declaration given the line number,
     * list
     * of interface modifiers, name of the interface, its super interface type, and
     * the
     * interface block.
     * 
     * @param line
     *                       line in which the interface declaration occurs in the
     *                       source file.
     * @param mods
     *                       interface modifiers.
     * @param name
     *                       interface name.
     * @param superType
     *                       interface interface type.
     * @param interfaceBlock
     *                       interface block.
     */

    public JInterfaceDeclaration(int line, ArrayList<String> mods, String name,
            Type superType, ArrayList<JMember> interfaceBlock) {
        super(line);
        this.mods = mods;
        this.name = name;
        this.superType = superType;
        this.interfaceBlock = interfaceBlock;
        hasExplicitConstructor = false;
    }

    /**
     * Returns the interface name.
     * 
     * @return the interface name.
     */

    public String name() {
        return name;

    }

    /**
     * Returns the interface super interface type.
     * 
     * @return the super interface type.
     */

    public Type superType() {
        return superType;
    }

    /**
     * Returns the type that this interface declaration defines.
     * 
     * @return the defined type.
     */

    public Type thisType() {
        return thisType;
    }

    public void declareThisType(Context context) {
        // TODO: Needs to be implementet
    }

    /**
     * Pre-analyzes the members of this declaration in the parent context.
     * Pre-analysis extends to the member headers (including method headers) but
     * not into the bodies.
     * 
     * @param context
     *                the parent (compilation unit) context.
     */

    public void preAnalyze(Context context) {
        // TODO: yet to be implementet
    }

    /**
     * Performs semantic analysis on the interface and all of its members within the
     * given context. Analysis includes field initializations and the method
     * bodies.
     * 
     * @param context
     *                the parent (compilation unit) context. Ignored here.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JAST analyze(Context context) {
        // Analyze all members
        // TODO: Still needs some work.
        for (JMember member : interfaceBlock) {
            ((JAST) member).analyze(this.context);
        }

        return this;
    }

    /**
     * Generates code for the interface declaration.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .interface file).
     */

    public void codegen(CLEmitter output) {
        // The class header
        String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
                : JAST.compilationUnit.packageName() + "/" + name;
        output.addClass(mods, qualifiedName, superType.jvmName(), null, false);

        // The implicit empty constructor?
        if (!hasExplicitConstructor) {
            codegenImplicitConstructor(output);
        }

        // The membersmustBe(IDENTIFIER);
        for (JMember member : interfaceBlock) {
            ((JAST) member).codegen(output);
        }

    }

    

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        // TODO: Needsto be implementet
    }

    /**
     * Generates code for an implicit empty constructor. (Necessary only if there
     * is not already an explicit one.)
     * 
     * @param partial
     *                the code emitter (basically an abstraction for producing a
     *                Java interface).
     */

    private void codegenPartialImplicitConstructor(CLEmitter partial) {
       // Invoke super constructor
       ArrayList<String> mods = new ArrayList<String>();
       mods.add("public");
       partial.addMethod(mods, "<init>", "()V", null, false);
       partial.addNoArgInstruction(ALOAD_0);
       partial.addMemberAccessInstruction(INVOKESPECIAL, superType.jvmName(),
               "<init>", "()V");

       // Return
       partial.addNoArgInstruction(RETURN);
    }

    /**
     * Generates code for an implicit empty constructor. (Necessary only if there
     * is not already an explicit one.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .interface file).
     */

    private void codegenImplicitConstructor(CLEmitter output) {
        // TODO: Needs to be implementet
    }
}
