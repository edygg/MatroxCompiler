package edu.unitec.ast;
import visitor.Visitor;
import visitor.TypeVisitor;

public class CharLiteral extends Exp {
  public char i;

  public CharLiteral(char ai) {
    i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}