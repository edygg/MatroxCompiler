package edu.unitec.ast;

import edu.unitec.intermediatelanguage.IntermediateForm;
import edu.unitec.visitor.IntermediateVisitor;
import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class ElseIfStatement {

    public Exp e;
    public Statements s;

    private int column;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    private int line;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public ElseIfStatement(Exp ae, Statements as) {
        e = ae;
        s = as;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public IntermediateForm accept(IntermediateVisitor v) {
        return v.visit(this);
    }
}
