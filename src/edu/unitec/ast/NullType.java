package edu.unitec.ast;

import edu.unitec.intermediatelanguage.IntermediateForm;
import edu.unitec.visitor.IntermediateVisitor;
import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class NullType extends Type {

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public IntermediateForm accept(IntermediateVisitor v) {
        return v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NullType) {
            return true;
        } else {
            return false;
        }
    }

}
