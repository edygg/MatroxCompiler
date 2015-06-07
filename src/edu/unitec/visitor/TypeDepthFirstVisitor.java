package edu.unitec.visitor;

import edu.unitec.ast.*;
import edu.unitec.matrox.SemanticAnalysis;
import edu.unitec.matrox.SemanticFunctionTableNode;
import edu.unitec.matrox.SemanticTableNode;
import java.util.Vector;

public class TypeDepthFirstVisitor implements TypeVisitor {
    
    private String scope;
    private SemanticTableNode currentType;
    private SemanticAnalysis semanticTable;

    public TypeDepthFirstVisitor(SemanticAnalysis semanticTable) {
        this.semanticTable = semanticTable;
    }
    
    public void errorComplain(String message) {
        System.err.println(message);
    }
    
    public Type visit(Program n) {
        //n.m.accept(this);
        for (int i = 0; i < n.fds.size(); i++) {
            FunctionDeclaration current = n.fds.elementAt(i);
            scope = current.i.toString();
            if (current.ps != null) {
                Vector<Type> onlyParamTypes = new Vector();
                Parameters ps = current.ps;
                for (int j = 0; j < ps.size(); j++) {
                    onlyParamTypes.add(ps.elementAt(j).t);
                }
                currentType = new SemanticFunctionTableNode(current.t, onlyParamTypes, current.i.toString(), scope);
            } else {
                currentType = new SemanticFunctionTableNode(current.t, new Vector(), current.i.toString(), scope);
            }
                
            if (!semanticTable.addID(scope, currentType)) {
                errorComplain(current.i.toString() + " is already defined.");
            }
            current.accept(this);
            //return current.t; // Retornar todo el tipo de una función
        }
        return null;
    }

    public Type visit(FunctionDeclaration n) {
        n.i.accept(this);
        n.t.accept(this);

        for (int i = 0; i < n.ss.size(); i++) {
            n.ss.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.ps.size(); i++) {
            n.ps.elementAt(i).accept(this);
        }
        return null;
    }

    public Type visit(Statement n) {
        //n.accept(this);
        return null;
    }

    public Type visit(If n) {
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
        return null;
    }

    public Type visit(ElseIfStatement n) {
        n.e.accept(this);

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
        return null;
    }

    public Type visit(While n) {
        n.e.accept(this);

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
        return null;
    }

    public Type visit(For n) {
        n.e1.accept(this);
        n.e2.accept(this);
        n.fi.accept(this);
        n.vd.accept(this);

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
        return null;
    }

    public Type visit(ForInit n) {
        n.vd.accept(this);
        n.vdn.accept(this);

        return null;
    }

    public Type visit(SwitchStatement n) {

        n.e.accept(this);

        for (int i = 0; i < n.scs.size(); i++) {
            n.scs.elementAt(i).accept(this);
        }

        return null;
    }

    public Type visit(SwitchCaseStatement n) {

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
       
        for (int i = 0; i < n.scel.size(); i++) {
            n.scel.elementAt(i).accept(this);
        }

        return null;
    }

    public Type visit(VariableDeclaration n) {
        if (n.i != null)    
            n.i.accept(this);
        n.t.accept(this);
        
        if (n.vds != null) {
            for (int i = 0; i < n.vds.size(); i++) {
                n.vds.elementAt(i).accept(this);
            }
        }
        
        return null;
    }

    public Type visit(VariableDeclarator n) {

        n.e.accept(this);
        n.i.accept(this);

        return null;
    }

    public Type visit(Return n) {
        n.e.accept(this);
        return null;
    }

    public Type visit(Read n) {
        n.e.accept(this);
        return null;
    }

    public Type visit(Write n) {
        n.e.accept(this);
        return null;
    }

    public Type visit(Exp n) {
        // n.accept(this);
        return null;
    }

    public Type visit(NumericExp n) {
        //n.accept(this);
        return null;
    }

    public Type visit(BooleanExp n) {
        //n.accept(this);
        return null;
    }

    public Type visit(LogicalExp n) {
        // n.accept(this);
        return null;
    }

    public Type visit(LiteralExp n) {
        //  n.accept(this);
        return null;
    }

    public Type visit(FunctionCall n) {

        n.i.accept(this);
        
        if (n.as != null) {
            for (int i = 0; i < n.as.size(); i++) {
                n.as.elementAt(i).accept(this);
            }
        }

        return null;
    }

    public Type visit(LParExpRPar n) {
        n.e1.accept(this);
        return null;
    }

    public Type visit(Identifier n) {
        //n.accept(this);
        return null;
    }

    public Type visit(IntegerLiteral n) {
        return null;
    }

    public Type visit(DoubleLiteral n) {
        return null;
    }

    public Type visit(StringLiteral n) {
        return null;
    }

    public Type visit(CharLiteral n) {
        return null;
    }

    public Type visit(Umin n) {
        n.e1.accept(this);
        return null;
    }

    public Type visit(Uprinc n) {
        n.e1.accept(this);
        return null;
    }

    public Type visit(Uprdec n) {
        n.e1.accept(this);
        return null;
    }

    public Type visit(Upinc n) {
        n.e1.accept(this);
        return null;
    }

    public Type visit(Updec n) {
        n.e1.accept(this);
        return null;
    }

    public Type visit(Add n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(AddAssign n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(Min n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(MinAssign n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(Mul n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(MulAssign n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(Div n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(DivAssign n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(Greater n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(Less n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(GreaterEq n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(LessEq n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(Equ n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(Neq n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(Not n) {
        n.e.accept(this);
        return null;
    }

    public Type visit(Or n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(And n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    public Type visit(True n) {
        return null;
    }

    public Type visit(False n) {
        return null;
    }

    public Type visit(Parameter n) {
        n.i.accept(this);
        n.t.accept(this);
        return null;
    }

    public Type visit(IntegerType n) {
        return null;
    }

    public Type visit(DoubleType n) {
        return null;
    }

    public Type visit(StringType n) {
        return null;
    }

    public Type visit(CharType n) {
        return null;
    }

    public Type visit(Assign n) {
        n.e.accept(this);
        n.i.accept(this);
        return null;
    }

    public Type visit(BooleanLiteral n) {
        return null;
    }

    public Type visit(BooleanType n) {
        return null;
    }

    public Type visit(IdentifierExp n) {
        return null;
    }

    public Type visit(IdentifierType n) {
        return null;
    }

    public Type visit(Print n) {
        n.e.accept(this);
        return null;
    }

    public Type visit(Type n) {
        return null;
    }

    public Type visit(Arguments n) {
        return null;
    }

    public Type visit(ElseIfStatements n) {
        return null;
    }

    public Type visit(SwitchCaseStatements n) {
        return null;
    }

    public Type visit(SwitchCaseExpList n) {
        return null;
    }

    public Type visit(VariableDeclarators n) {
        return null;
    }

    public Type visit(Parameters n) {
        return null;
    }

    public Type visit(VariableDeclarations n) {
        return null;
    }

    public Type visit(Statements n) {
        return null;
    }

    public Type visit(MainClass n) {
        return null;
    }

    public Type visit(FunctionDeclarations n) {
        return null;
    }
}
