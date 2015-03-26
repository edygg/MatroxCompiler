package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class If extends Statement {
  public Exp e;
  public Statements s1,s2;
  public ElseIfStatements eis;
  
  public If(Exp ae, Statements as1) {
    e=ae; s1=as1;
  }

  public If(Exp ae, Statements as1, Statements as2) {
    e=ae; s1=as1; s2=as2;
  }

  public If(Exp ae, Statements as1, ElseIfStatements aeis) {
    e=ae; s1=as1; eis=aeis;
  }
  
  public If(Exp ae, Statements as1, ElseIfStatements aeis ,Statements as2) {
    e=ae; s1=as1; eis=aeis; s2=as2;
  }
  
  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
