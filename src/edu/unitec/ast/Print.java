package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class Print extends Statement {
  public Exp e;

  public Print(Exp ae) {
    e=ae; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}