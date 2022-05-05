// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

/**
 * A interface declaration has a list of modifiers, a name, a super interface and a
 * interface block; it distinguishes between instance fields and static (interface)
 * fields for initialization, and it defines a type. It also introduces its own
 * (interface) context.
 *
 *
 * This class is corruntly only made for parsing - alot needs to be implementet and it is currently just a copy
 *  of the "classDeclaration class" beaware of this when doingfurther work! If
 * @see ClassContext
 */

class JInterfaceDeclaration extends JAST implements JTypeDecl {

    /** interface modifiers. */
    private ArrayList<String> mods;

    /** interface name. */
    private String name;

    /** interface block. */
    private ArrayList<JMember> interfaceBlock;

    /** Super interface type. */
    private Type superType;

    /** This interface type. */
    private Type thisType;

    /** Context for this interface. */
    private ClassContext context;

    /** Whether this interface has an explicit constructor. */
    private boolean hasExplicitConstructor;

    /** Fields **/
    private ArrayList<JFieldDeclaration> fields;

    /**
     * Constructs an AST node for a interface declaration given the line number, list
     * of interface modifiers, name of the interface, its super interface type, and the
     * interface block.
     *
     * @param line
     *            line in which the interface declaration occurs in the source file.
     * @param mods
     *            interface modifiers.
     * @param name
     *            interface name.
     * @param superType
     *            super interface type.
     * @param interfaceBlock
     *            interface block.
     */
    public JInterfaceDeclaration(int line, ArrayList<String> mods, String name,
                                 Type superType, ArrayList<JMember> interfaceBlock) {
        super(line);
        this.mods = mods;
        this.name = name;
        this.superType = superType;
        this.interfaceBlock = interfaceBlock;
        this.hasExplicitConstructor = false;
        this.fields = new ArrayList<JFieldDeclaration>();
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
     * Returns the interface' super interface type.
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

    /**
     * Returns the initializations for instance fields (now expressed as
     * assignment statements).
     *
     * @return the field declarations having initializations.
     */

    /**
     * Declares this interface in the parent (compilation unit) context.
     *
     * @param context
     *            the parent (compilation unit) context.
     */

    public void declareThisType(Context context) {
        String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
                : JAST.compilationUnit.packageName() + "/" + name;
        CLEmitter partial = new CLEmitter(false);
        partial.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), null,
                false); // Object for superClass, just for now
        thisType = Type.typeFor(partial.toClass());
        context.addType(line, thisType);
    }

    /**
     * Pre-analyzes the members of this declaration in the parent context.
     * Pre-analysis extends to the member headers (including method headers) but
     * not into the bodies.
     *
     * @param context
     *            the parent (compilation unit) context.
     */

    public void preAnalyze(Context context) {
        // Construct a interface context
        this.context = new ClassContext(this, context);

        // Resolve superinterface
        if (superType != null) {
            superType = superType.resolve(this.context);
            thisType.checkAccess(line, superType);
            if (superType.isFinal()) {
                JAST.compilationUnit.reportSemanticError(line,
                        "Cannot extend a final type: %s", superType.toString());
            }
        }

        // Creating a partial interface in memory can result in a
        // java.lang.VerifyError if the semantics below are
        // violated, so we can't defer these checks to analyze()


        // Create the (partial) interface
        CLEmitter partial = new CLEmitter(false);

        // Add the interface header to the partial class
        String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
                : JAST.compilationUnit.packageName() + "/" + name;
        if (superType != null) {
            partial.addClass(mods, qualifiedName, superType.jvmName(), null, false);
        } else {
            partial.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), null, false);
        }

        // Pre-analyze the members and add them to the partial
        // interface
        for (JMember member : interfaceBlock) {
            member.preAnalyze(this.context, partial);
            if (member instanceof JConstructorDeclaration
                    && ((JConstructorDeclaration) member).params.size() == 0) {
                hasExplicitConstructor = true;
            }
        }

        // Get the interface rep for the (partial) interface and make it
        // the
        // representation for this type
        Type id = this.context.lookupType(name);
        if (id != null && !JAST.compilationUnit.errorHasOccurred()) {
            id.setClassRep(partial.toClass());
        }

        // Now we call it an interface instead of a class
        if(!mods.contains(TokenKind.INTERFACE.image())){
            mods.add(TokenKind.INTERFACE.image());
        }
    }

    /**
     * Performs semantic analysis on the interface and all of its members within the
     * given context. Analysis includes field initializations and the method
     * bodies.
     *
     * @param context
     *            the parent (compilation unit) context. Ignored here.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JAST analyze(Context context) {
        // Analyze all members
        for (JMember member : interfaceBlock) {
            if (member instanceof JFieldDeclaration) {
                JFieldDeclaration fieldDecl = (JFieldDeclaration) member;
                fields.add(fieldDecl);
            }

        }

        return this;
    }

    /**
     * Generates code for the interface declaration.
     *
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .interface file).
     */

    public void codegen(CLEmitter output) {
        // The interface header
        String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
                : JAST.compilationUnit.packageName() + "/" + name;
        if (superType != null) {
            output.addClass(mods, qualifiedName, superType.jvmName(), null, false);
        } else {
            output.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), null, false);
        }

        // The members
        for (JMember member : interfaceBlock) {
            ((JAST) member).codegen(output);
        }


        // Generate an interface initialization method?
        if (fields.size() > 0) {
            codegenInterfaceFields(output);
        }
    }

    private void codegenInterfaceFields(CLEmitter output) {
        ArrayList<String> mods = new ArrayList<String>();
        mods.add("public");
        mods.add("static");
        output.addMethod(mods, "<clinit>", "()V", null, false);

        // If there are instance initializations, generate code
        // for them
        for (JFieldDeclaration staticField : fields) {
            staticField.codegenInitializations(output);
        }

        // Return
        output.addNoArgInstruction(RETURN);
    }


    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        if (superType != null) {
            p.printf("<JInterfaceDeclaration line=\"%d\" name=\"%s\"" + " super=\"%s\">\n", line(),
                    name,
                    superType.toString());
        } else {
            p.printf("<JInterfaceDeclaration line=\"%d\" name=\"%s\">\n", line(), name);
        }
        p.indentRight();
        if (context != null) {
            context.writeToStdOut(p);
        }
        if (mods != null) {
            p.println("<Modifiers>");
            p.indentRight();
            for (String mod : mods) {
                p.printf("<Modifier name=\"%s\"/>\n", mod);
            }
            p.indentLeft();
            p.println("</Modifiers>");
        }
        p.printf("<SuperInterfaces>");
        p.indentRight();
//        for(String superInterfaceName : abstractNames){
//            p.printf("<Interface name =\"%s\"/>\n",superInterfaceName);
//        }
        p.indentLeft();
        p.println("</SuperInterfaces");
        if (interfaceBlock != null) {
            p.println("<InterfaceBlock>");
            p.indentRight();
            for (JMember member : interfaceBlock) {
                ((JAST) member).writeToStdOut(p);
            }
            p.indentLeft();
            p.println("</InterfaceBlock>");
        }
        p.indentLeft();
        p.println("</JInterfaceDeclaration>");

    }
}