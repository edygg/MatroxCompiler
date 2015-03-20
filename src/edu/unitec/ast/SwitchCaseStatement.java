package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class SwitchCaseStatement {
  public SwitchCaseExpList scel;
  public Statement s;

  public SwitchCaseStatement(SwitchCaseExpList ascel, Statement as){
      scel=ascel; s=as;
  }
  
  public SwitchCaseStatement(Statement as){
      s=as;
  }
  
  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
