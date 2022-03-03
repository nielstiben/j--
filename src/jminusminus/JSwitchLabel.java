package jminusminus;

abstract class JSwitchLabel extends JSwitchBlockStatement {
    protected JSwitchLabel(int line, JExpression expr) {
        super(line, expr);
    }
}
