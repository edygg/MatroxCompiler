package edu.unitec.ast;

import edu.unitec.intermediatelanguage.IntermediateForm;
import edu.unitec.visitor.IntermediateVisitor;
import edu.unitec.visitor.TypeVisitor;
import edu.unitec.visitor.Visitor;

public class Argument {

    public Exp e;

    public Argument(Exp e) {
        this.e = e;
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
