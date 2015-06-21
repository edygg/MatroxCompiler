package edu.unitec.ast;

import edu.unitec.intermediatelanguage.IntermediateForm;
import edu.unitec.visitor.IntermediateVisitor;
import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class SwitchStatement extends Statement {

    public Exp e;
    public SwitchCaseStatements scs;

    public SwitchStatement(Exp ae, SwitchCaseStatements as) {
        e = ae;
        scs = as;
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
