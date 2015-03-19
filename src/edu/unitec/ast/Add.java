package edu.unitec.ast;

import visitor.Visitor;
import visitor.TypeVisitor;

public class Add extends Exp {
  public Exp e1,e2;
  
  public Add(Exp ae1, Exp ae2) { 
    e1=ae1; e2=ae2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}