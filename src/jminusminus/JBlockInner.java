package jminusminus;

import java.util.ArrayList;

public class JBlockInner extends JBlock implements JMember {

    private JBlock body;
    private LocalContext context;
    private ArrayList<String> mods;
    private ArrayList<JFieldDeclaration> staticFieldInitializations;
    protected boolean isStatic;
    
    public JBlockInner(int line, JBlock body, ArrayList<String> mods) {
        super(line, body.statements());
        this.body = body;
        this.mods = mods;
        this.isStatic = mods != null && mods.contains("static");
        this.staticFieldInitializations = new ArrayList<JFieldDeclaration>();;
    }

    public JBlock getBody() {
        return body;
    }

    public LocalContext getContext() {
        return context;
    }

    public ArrayList<String> getMods() {
        return mods;
    }

    public ArrayList<JFieldDeclaration> getStaticFieldInitializations() {
        return staticFieldInitializations;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public JBlockInner analyze(Context context) {
        body.analyze(context);
        return this;
    }

    public void codegen(CLEmitter output) {
    }

    @Override
    public void preAnalyze(Context context, CLEmitter partial) {

    }


public void writeToStdOut(PrettyPrinter p) {
    p.printf("<JInnerBlock> line=" + "\""+line+"\"");
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
    p.println("</JInnerBlock>");
}
}

