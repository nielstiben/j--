package jminusminus;

import java.util.ArrayList;

class JSwitchBlock extends JStatement {

    private final ArrayList<JSwitchBlockStatement> blockStatements;

    public JSwitchBlock(int line, ArrayList<JSwitchBlockStatement> blockStatements) {
        super(line);
        this.blockStatements = blockStatements;
    }

    @Override
    public JAST analyze(Context context) {
        return null;
    }

    @Override
    public void codegen(CLEmitter output) {

    }

    @Override
    public void writeToStdOut(PrettyPrinter p) {

    }
}
