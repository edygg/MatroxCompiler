package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class ElseIfStatement {
  public Exp e;
  public Statements s;

  public ElseIfStatement(Exp ae, Statements as) {
    e=ae; s=as;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
