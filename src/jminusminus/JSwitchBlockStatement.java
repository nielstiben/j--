package jminusminus;

import java.util.ArrayList;

class JSwitchBlockStatement extends JSwitchBlock{

    public JSwitchBlockStatement(int line, ArrayList<JSwitchLabel> labels, ArrayList<JStatement> statements) {
        super(line, labels, statements);
    }

    public JSwitchBlockStatement(int line, ArrayList<JSwitchLabel> labels, ArrayList<JStatement> statements, ArrayList<JStatement> defaultStatements)
}