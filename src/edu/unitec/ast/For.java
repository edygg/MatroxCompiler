package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class For extends Statement {
  public VariableDeclaration vd;  
  public Exp e;
  public Statements s;

  //Este hay que hacer el for_init
  //FOR LPAR for_init SEMICOLON exp SEMICOLON exp RPAR statements END
  //FOR LPAR for_init SEMICOLON exp SEMICOLON variable_declarator RPAR statements END
  public For(VariableDeclaration avd ,Exp ae, Statements as) {
    vd = avd;  e=ae; s=as; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
