package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class StringLiteral extends Exp {
  public String i;

  public StringLiteral(String ai) {
    i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}