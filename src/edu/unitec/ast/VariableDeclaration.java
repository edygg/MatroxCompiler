package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class VariableDeclaration extends Statement{
  public Type t;
  public Identifier i;
  public VariableDeclarators vds;

  
  public VariableDeclaration(Type at, Identifier ai) {
    t=at; i=ai;
  }
  
   public VariableDeclaration(Type at, Identifier ai, VariableDeclarators avds) {
    t=at; i=ai; vds = avds;
  }
  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}