package edu.unitec.ast;

import edu.unitec.intermediatelanguage.IntermediateForm;
import edu.unitec.visitor.IntermediateVisitor;
import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class SwitchCaseStatement {

    public SwitchCaseExpList scel;
    public Statements s;

    public SwitchCaseStatement(SwitchCaseExpList ascel, Statements as) {
        scel = ascel;
        s = as;
    }

    public SwitchCaseStatement(Statements as) {
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
