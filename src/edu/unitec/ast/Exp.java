package edu.unitec.ast;

import edu.unitec.intermediatelanguage.IntermediateForm;
import edu.unitec.visitor.IntermediateVisitor;
import edu.unitec.visitor.TypeVisitor;
import edu.unitec.visitor.Visitor;

public abstract class Exp extends Statement {

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

    public abstract void accept(Visitor v);

    public abstract Type accept(TypeVisitor v);
    
    public abstract IntermediateForm accept(IntermediateVisitor v);
}
