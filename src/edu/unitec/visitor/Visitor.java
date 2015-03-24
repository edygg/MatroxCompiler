package edu.unitec.visitor;

import edu.unitec.ast.*;

public interface Visitor {
  //public void visit(Program n);
    
  public void visit(Add n);
  public void visit(And n);
  public void visit(Assign n);
  public void visit(BooleanLiteral n);
  public void visit(BooleanType n);
  public void visit(CharLiteral n);
  public void visit(CharType n);
  public void visit(Div n);
  public void visit(DoubleLiteral n);
  public void visit(DoubleType n);
  public void visit(Equ n);
  public void visit(Exp n);
  public void visit(False n);
  public void visit(For n);
  public void visit(FunctionDeclaration n);
  public void visit(FunctionDeclarations n);
  public void visit(Greater n);
  public void visit(GreaterEq n);
  public void visit(Identifier n);
  public void visit(IdentifierExp n);
  public void visit(IdentifierType n);
  public void visit(If n);
  public void visit(IntegerLiteral n);
  public void visit(IntegerType n);
  public void visit(Less n);
  public void visit(LessEq n);
  public void visit(MainClass n);
  public void visit(Min n);
  public void visit(Mul n);
  public void visit(Neq n);
  public void visit(Not n);
  public void visit(Or n);
  public void visit(Parameter n);
  public void visit(Parameters n);
  public void visit(Print n);
  public void visit(Statement n);
  public void visit(Statements n);
  public void visit(StringLiteral n);
  public void visit(StringType n);
  public void visit(True n);
  public void visit(Type n);
  public void visit(VariableDeclaration n);
  public void visit(VariableDeclarations n);
  public void visit(While n); 
  public void visit(NumericExp n);
  public void visit(Umin n);
  public void visit(Uprinc n);
  public void visit(Uprdec n);
  public void visit(Upinc n);
  public void visit(Updec n);
  public void visit(AddAssign n);
  public void visit(MinAssign n);
  public void visit(MulAssign n);
  public void visit(DivAssign n);
  public void visit(BooleanExp n);
  public void visit(LogicalExp n);
  public void visit(LiteralExp n);
  public void visit(LParExpRPar n);
  public void visit(VariableDeclarator n);
  public void visit(VariableDeclarators n);
  public void visit(ForInit n);
  public void visit(SwitchCaseExpList n);
  public void visit(SwitchCaseStatement n);
  public void visit(SwitchCaseStatements n);
  public void visit(SwitchStatement n);
  public void visit(Return n);
  public void visit(Read n);
  public void visit(Write n);
  public void visit(Arguments n);
  public void visit(Program n);
  public void visit(FunctionCall n);
  public void visit(ElseIfStatement n);
  public void visit(ElseIfStatements n);
}