package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class For extends Statement {
  public VariableDeclaration vd;  
  public Exp e;
  public Statement s;

  public For(VariableDeclaration avd ,Exp ae, Statement as) {
    vd = avd;  e=ae; s=as; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
