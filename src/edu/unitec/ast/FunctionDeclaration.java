package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class FunctionDeclaration {
  public Type t;
  public Identifier i;
  public Parameters ps;
  public Statements ss;

  public FunctionDeclaration(Type at, Identifier ai, Parameters aps, Statements ass) {
    t=at; i=ai; ps=aps; ss=ass;
  }
 
  public FunctionDeclaration(Identifier ai, Parameters aps, Statements ass) {
    i=ai; ps=aps; ss=ass;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}