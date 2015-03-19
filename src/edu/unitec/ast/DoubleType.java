package edu.unitec.ast;
import visitor.Visitor;
import visitor.TypeVisitor;

public class DoubleType extends Type {
  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}