package edu.unitec.ast;
import visitor.Visitor;
import visitor.TypeVisitor;

public class DoubleLiteral extends Exp {
  public double i;

  public DoubleLiteral(double ai) {
    i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}