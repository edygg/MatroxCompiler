package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class LParExpRPar extends Exp {

    public Exp e1;

    public LParExpRPar(Exp ae1) {
        e1 = ae1;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}

