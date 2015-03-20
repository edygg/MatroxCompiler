package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class FunctionCall extends Exp {
  public Identifier i;
  public Arguments as;

  public FunctionCall(Identifier ai, Arguments aas) {
    i=ai; as=aas; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
