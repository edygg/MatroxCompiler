package edu.unitec.ast;

import edu.unitec.visitor.Visitor;
import edu.unitec.visitor.TypeVisitor;

public abstract class Type {
  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);
}