package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class VariableDeclaration {
  public Type t;
  public Identifier i;
  
  public VariableDeclaration(Type at, Identifier ai) {
    t=at; i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}