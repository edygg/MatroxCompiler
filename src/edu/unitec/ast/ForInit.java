package edu.unitec.ast;

import edu.unitec.intermediatelanguage.IntermediateForm;
import edu.unitec.visitor.IntermediateVisitor;
import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class ForInit {

    public VariableDeclaration vdn;
    public VariableDeclarator vd;

    public ForInit(VariableDeclaration avdn) {
        vdn = avdn;
    }

    public ForInit(VariableDeclarator avd) {
        vd = avd;
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
