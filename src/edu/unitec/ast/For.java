package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public class For extends Statement {
  public ForInit fi;
  public Exp e1;
  public Exp e2;
  public VariableDeclarator vd;
  public Statements s;

  public For(ForInit afi ,Exp ae1, Exp ae2, Statements as) {
    fi = afi; e1=ae1; e2=ae2; s=as; 
  }
  
  public For(ForInit afi, Exp ae1, VariableDeclarator avd, Statements as) {
    fi = afi; e1=ae1; vd=avd; s=as; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
