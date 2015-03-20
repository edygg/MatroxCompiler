package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class FunctionDeclaration {
  public Type t;
  public Identifier i;
  public Parameters fl;
  public Statements sl;
  public Exp e;

  public FunctionDeclaration(Type at, Identifier ai, Parameters afl, Statements asl, Exp ae) {
    t=at; i=ai; fl=afl; sl=asl; e=ae;
  }
 
  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}