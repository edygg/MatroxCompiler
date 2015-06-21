package edu.unitec.visitor;

import edu.unitec.ast.Add;
import edu.unitec.ast.AddAssign;
import edu.unitec.ast.And;
import edu.unitec.ast.Argument;
import edu.unitec.ast.Arguments;
import edu.unitec.ast.Assign;
import edu.unitec.ast.BooleanExp;
import edu.unitec.ast.BooleanLiteral;
import edu.unitec.ast.BooleanType;
import edu.unitec.ast.CharLiteral;
import edu.unitec.ast.CharType;
import edu.unitec.ast.Div;
import edu.unitec.ast.DivAssign;
import edu.unitec.ast.DoubleLiteral;
import edu.unitec.ast.DoubleType;
import edu.unitec.ast.ElseIfStatement;
import edu.unitec.ast.ElseIfStatements;
import edu.unitec.ast.Equ;
import edu.unitec.ast.ErrorType;
import edu.unitec.ast.Exp;
import edu.unitec.ast.False;
import edu.unitec.ast.For;
import edu.unitec.ast.ForInit;
import edu.unitec.ast.FunctionCall;
import edu.unitec.ast.FunctionDeclaration;
import edu.unitec.ast.FunctionDeclarations;
import edu.unitec.ast.Greater;
import edu.unitec.ast.GreaterEq;
import edu.unitec.ast.Identifier;
import edu.unitec.ast.IdentifierExp;
import edu.unitec.ast.IdentifierType;
import edu.unitec.ast.If;
import edu.unitec.ast.IntegerLiteral;
import edu.unitec.ast.IntegerType;
import edu.unitec.ast.LParExpRPar;
import edu.unitec.ast.Less;
import edu.unitec.ast.LessEq;
import edu.unitec.ast.LiteralExp;
import edu.unitec.ast.LogicalExp;
import edu.unitec.ast.MainClass;
import edu.unitec.ast.Min;
import edu.unitec.ast.MinAssign;
import edu.unitec.ast.Mul;
import edu.unitec.ast.MulAssign;
import edu.unitec.ast.Neq;
import edu.unitec.ast.Not;
import edu.unitec.ast.NullType;
import edu.unitec.ast.NumericExp;
import edu.unitec.ast.Or;
import edu.unitec.ast.Parameter;
import edu.unitec.ast.Parameters;
import edu.unitec.ast.Program;
import edu.unitec.ast.Read;
import edu.unitec.ast.Return;
import edu.unitec.ast.Statement;
import edu.unitec.ast.Statements;
import edu.unitec.ast.StringLiteral;
import edu.unitec.ast.StringType;
import edu.unitec.ast.SwitchCaseExpList;
import edu.unitec.ast.SwitchCaseStatement;
import edu.unitec.ast.SwitchCaseStatements;
import edu.unitec.ast.SwitchStatement;
import edu.unitec.ast.True;
import edu.unitec.ast.Type;
import edu.unitec.ast.Umin;
import edu.unitec.ast.Updec;
import edu.unitec.ast.Upinc;
import edu.unitec.ast.Uprdec;
import edu.unitec.ast.Uprinc;
import edu.unitec.ast.VariableDeclaration;
import edu.unitec.ast.VariableDeclarations;
import edu.unitec.ast.VariableDeclarator;
import edu.unitec.ast.VariableDeclarators;
import edu.unitec.ast.While;
import edu.unitec.ast.Write;
import java.io.BufferedWriter;
import edu.unitec.intermediatelanguage.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author Edilson Gonzalez
 */
public class IntermediateCodeGenerator implements IntermediateVisitor {

    private BufferedWriter out;
    private StringBuilder file;
    private IntermediateStatement program;

    public IntermediateCodeGenerator(File outputFile) throws IOException {
        out = new BufferedWriter(new FileWriter(outputFile));
    }

    public IntermediateForm visit(Program n) {
        IntermediateStatement allCode = new IntermediateStatement();
        allCode.operations = new OperationList();

        for (int i = 0; i < n.fds.size(); i++) {
            IntermediateForm current = n.fds.elementAt(i).accept(this);
            allCode.operations = allCode.operations.merge(current.operations);
        }

        return allCode;
    }

    public IntermediateForm visit(FunctionDeclaration n) {
        IntermediateStatement ss = new IntermediateStatement();
        ss.operations = new OperationList();
        ss.operations.add(new Operation(new Label(n.i.toString())));
        for (int i = 0; i < n.ss.size(); i++) {
            IntermediateForm current = n.ss.elementAt(i).accept(this);
            ss.operations = ss.operations.merge(current.operations);
        }
        return ss;
    }

    public IntermediateForm visit(Statement n) {

    }

    public IntermediateForm visit(If n) {

    }

    public IntermediateForm visit(ElseIfStatement n) {

    }

    public IntermediateForm visit(While n) {

    }

    public IntermediateForm visit(For n) {

    }

    public IntermediateForm visit(ForInit n) {

    }

    public IntermediateForm visit(SwitchStatement n) {

    }

    public IntermediateForm visit(SwitchCaseStatement n) {

    }

    public IntermediateForm visit(VariableDeclaration n) {

    }

    public IntermediateForm visit(VariableDeclarator n) {

    }

    public IntermediateForm visit(Return n) {

    }

    public IntermediateForm visit(Read n) {

    }

    public IntermediateForm visit(Write n) {

    }

    public IntermediateForm visit(Exp n) {

    }

    public IntermediateForm visit(NumericExp n) {

    }

    public IntermediateForm visit(BooleanExp n) {

    }

    public IntermediateForm visit(LogicalExp n) {

    }

    public IntermediateForm visit(LiteralExp n) {

    }

    public IntermediateForm visit(FunctionCall n) {

    }

    public IntermediateForm visit(LParExpRPar n) {

    }

    public IntermediateForm visit(Identifier n) {

    }

    public IntermediateForm visit(IntegerLiteral n) {

    }

    public IntermediateForm visit(DoubleLiteral n) {

    }

    public IntermediateForm visit(StringLiteral n) {

    }

    public IntermediateForm visit(CharLiteral n) {

    }

    public IntermediateForm visit(Umin n) {

    }

    public IntermediateForm visit(Uprinc n) {

    }

    public IntermediateForm visit(Uprdec n) {

    }

    public IntermediateForm visit(Upinc n) {

    }

    public IntermediateForm visit(Updec n) {

    }

    public IntermediateForm visit(Add n) {

    }

    public IntermediateForm visit(AddAssign n) {

    }

    public IntermediateForm visit(Min n) {

    }

    public IntermediateForm visit(MinAssign n) {

    }

    public IntermediateForm visit(Mul n) {

    }

    public IntermediateForm visit(MulAssign n) {

    }

    public IntermediateForm visit(Div n) {

    }

    public IntermediateForm visit(DivAssign n) {

    }

    public IntermediateForm visit(Greater n) {

    }

    public IntermediateForm visit(Less n) {

    }

    public IntermediateForm visit(GreaterEq n) {

    }

    public IntermediateForm visit(LessEq n) {

    }

    public IntermediateForm visit(Equ n) {

    }

    public IntermediateForm visit(Neq n) {

    }

    public IntermediateForm visit(Not n) {

    }

    public IntermediateForm visit(Or n) {

    }

    public IntermediateForm visit(And n) {

    }

    public IntermediateForm visit(True n) {

    }

    public IntermediateForm visit(False n) {

    }

    public IntermediateForm visit(Parameter n) {

    }

    public IntermediateForm visit(IntegerType n) {
        return n;
    }

    public IntermediateForm visit(NullType n) {

    }

    public IntermediateForm visit(ErrorType n) {

    }

    public IntermediateForm visit(DoubleType n) {

    }

    public IntermediateForm visit(StringType n) {

    }

    public IntermediateForm visit(CharType n) {

    }

    public IntermediateForm visit(Assign n) {

    }

    public IntermediateForm visit(BooleanLiteral n) {

    }

    public IntermediateForm visit(BooleanType n) {

    }

    public IntermediateForm visit(IdentifierExp n) {

    }

    public IntermediateForm visit(IdentifierType n) {

    }

    public IntermediateForm visit(Type n) {

    }

    public IntermediateForm visit(Arguments n) {

    }

    public IntermediateForm visit(Argument n) {

    }

    public IntermediateForm visit(ElseIfStatements n) {

    }

    public IntermediateForm visit(SwitchCaseStatements n) {

    }

    public IntermediateForm visit(SwitchCaseExpList n) {

    }

    public IntermediateForm visit(VariableDeclarators n) {

    }

    public IntermediateForm visit(Parameters n) {

    }

    public IntermediateForm visit(VariableDeclarations n) {

    }

    public IntermediateForm visit(Statements n) {

    }

    public IntermediateForm visit(MainClass n) {

    }

    public IntermediateForm visit(FunctionDeclarations n) {

    }
}
