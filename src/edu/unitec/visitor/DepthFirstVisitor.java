package edu.unitec.visitor;

import edu.unitec.ast.*;

public class DepthFirstVisitor implements Visitor {

    public void visit(Program n) {
        n.m.accept(this);
        for (int i = 0; i < n.fds.size(); i++) {
            n.fds.elementAt(i).accept(this);
        }
    }

    public void visit(FunctionDeclaration n) {
        n.i.accept(this);
        n.t.accept(this);

        for (int i = 0; i < n.ss.size(); i++) {
            n.ss.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.ps.size(); i++) {
            n.ps.elementAt(i).accept(this);
        }
    }

    public void visit(Statement n) {
        //n.accept(this);
    }

    public void visit(If n) {
        n.e.accept(this);

        for (int i = 0; i < n.s1.size(); i++) {
            n.s1.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.s2.size(); i++) {
            n.s2.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.eis.size(); i++) {
            n.eis.elementAt(i).accept(this);
        }
    }

    public void visit(ElseIfStatement n) {
        n.e.accept(this);

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
    }

    public void visit(While n) {
        n.e.accept(this);

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
    }

    public void visit(For n) {
        n.e1.accept(this);
        n.e2.accept(this);
        n.fi.accept(this);
        n.vd.accept(this);

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
    }

    public void visit(ForInit n) {
        n.vd.accept(this);
        n.vdn.accept(this);
    }

    public void visit(SwitchStatement n) {

        n.e.accept(this);

        for (int i = 0; i < n.scs.size(); i++) {
            n.scs.elementAt(i).accept(this);
        }
    }

    public void visit(SwitchCaseStatement n) {

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.scel.size(); i++) {
            n.scel.elementAt(i).accept(this);
        }
    }

    public void visit(VariableDeclaration n) {

        n.i.accept(this);
        n.t.accept(this);

        for (int i = 0; i < n.vds.size(); i++) {
            n.vds.elementAt(i).accept(this);
        }
    }

    public void visit(VariableDeclarator n) {

        n.e.accept(this);
        n.i.accept(this);
    }

    public void visit(Return n) {
        n.e.accept(this);
    }

    public void visit(Read n) {
        n.e.accept(this);
    }

    public void visit(Write n) {
        n.e.accept(this);
    }

    public void visit(Exp n) {
        // n.accept(this);
    }

    public void visit(NumericExp n) {
        //n.accept(this);
    }

    public void visit(BooleanExp n) {
        //n.accept(this);
    }

    public void visit(LogicalExp n) {
        // n.accept(this);
    }

    public void visit(LiteralExp n) {
        //  n.accept(this);
    }

    public void visit(FunctionCall n) {

        n.i.accept(this);

        for (int i = 0; i < n.as.size(); i++) {
            n.as.elementAt(i).accept(this);
        }
    }

    public void visit(LParExpRPar n) {
        n.e1.accept(this);
    }

    public void visit(Identifier n) {
        //n.accept(this);
    }

    public void visit(IntegerLiteral n) {

    }

    public void visit(DoubleLiteral n) {

    }

    public void visit(StringLiteral n) {

    }

    public void visit(CharLiteral n) {

    }

    public void visit(Umin n) {
        n.e1.accept(this);
    }

    public void visit(Uprinc n) {
        n.e1.accept(this);
    }

    public void visit(Uprdec n) {
        n.e1.accept(this);
    }

    public void visit(Upinc n) {
        n.e1.accept(this);
    }

    public void visit(Updec n) {
        n.e1.accept(this);
    }

    public void visit(Add n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(AddAssign n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(Min n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(MinAssign n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(Mul n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(MulAssign n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(Div n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(DivAssign n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(Greater n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(Less n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(GreaterEq n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(LessEq n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(Equ n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(Neq n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(Not n) {
        n.e.accept(this);
    }

    public void visit(Or n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(And n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    public void visit(True n) {

    }

    public void visit(False n) {

    }

    public void visit(Parameter n) {
        n.i.accept(this);
        n.t.accept(this);

    }

    public void visit(IntegerType n) {

    }

    public void visit(DoubleType n) {

    }

    public void visit(StringType n) {

    }

    public void visit(CharType n) {

    }

    public void visit(NullType n) {
        
    }
    
    public void visit(ErrorType n) {
        
    }
   
    public void visit(Assign n) {
        n.e.accept(this);
        n.i.accept(this);
    }

    public void visit(BooleanLiteral n) {

    }

    public void visit(BooleanType n) {

    }

    public void visit(IdentifierExp n) {

    }

    public void visit(IdentifierType n) {

    }

    public void visit(Type n) {

    }

    public void visit(Arguments n) {

    }

    public void visit(ElseIfStatements n) {

    }

    public void visit(SwitchCaseStatements n) {

    }

    public void visit(SwitchCaseExpList n) {

    }

    public void visit(VariableDeclarators n) {
    }

    public void visit(Parameters n) {

    }

    public void visit(VariableDeclarations n) {

    }

    public void visit(Statements n) {

    }

    public void visit(MainClass n) {

    }

    public void visit(FunctionDeclarations n) {

    }
}
