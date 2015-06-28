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
import edu.unitec.matrox.SemanticAnalysis;
import edu.unitec.matrox.SemanticFunctionTableNode;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.Vector;

/**
 *
 * @author Edilson Gonzalez
 */
public class IntermediateCodeGenerator implements IntermediateVisitor {

    private BufferedWriter out;
    private StringBuilder file;
    private IntermediateStatement program;
    private SemanticAnalysis semanticTable;
    private static final String GLOBAL_SCOPE = "s0";

    public IntermediateCodeGenerator(File outputFile, SemanticAnalysis semanticTable) throws IOException {
        this.out = new BufferedWriter(new FileWriter(outputFile));
        this.semanticTable = semanticTable;
    }

    public void complete(Vector<Label> list, Label label) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setName(label.toString());
        }
    }

    public Vector<Label> merge(Vector<Label> list1, Vector<Label> list2) {
        Vector<Label> neoList = new Vector();
        neoList.addAll(list1);
        neoList.addAll(list2);
        return neoList;
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
        IntermediateStatement ret = new IntermediateStatement();
        ret.operations.add(new Operation(new Label(n.i.toString())));

        IntermediateStatement allStatements = (IntermediateStatement) this.visit(n.ss);

        ret.operations = ret.operations.merge(allStatements.operations);
        
        SemanticFunctionTableNode functionInfo = (SemanticFunctionTableNode) semanticTable.findID(n.i.toString(), GLOBAL_SCOPE);
        
        if (functionInfo.getReturnType() == null) {
            ret.operations.add(new Operation("", "RET", "", Operation.Operations.VOID_RET));
        }
        return ret;
    }

    public IntermediateForm visit(Statement n) {
        if (n instanceof Exp) {
            return ((Exp) n).accept(this);
        } else if (n instanceof For) {
            return ((For) n).accept(this);
        } else if (n instanceof If) {
            return ((If) n).accept(this);
        } else if (n instanceof Read) {
            return ((Read) n).accept(this);
        } else if (n instanceof Return) {
            return ((Return) n).accept(this);
        } else if (n instanceof SwitchStatement) {
            return ((SwitchStatement) n).accept(this);
        } else if (n instanceof VariableDeclaration) {
            return ((VariableDeclaration) n).accept(this);
        } else if (n instanceof VariableDeclarator) {
            return ((VariableDeclarator) n).accept(this);
        } else if (n instanceof Write) {
            return ((Write) n).accept(this);
        } else if (n instanceof While) {
            return ((While) n).accept(this);
        } else {
            return new IntermediateStatement();
        }
    }

    public IntermediateForm visit(If n) {
        if (n.s1 != null && n.s2 == null && n.eis == null) { // Solamente if
            IntermediateStatement ret = new IntermediateStatement();
            
            OperationList tmp = new OperationList();
            IntermediateExpression exp = (IntermediateExpression) n.e.accept(this);
            Label trueLabel = new Label();
            complete(exp.getTrue(), trueLabel);
            tmp = tmp.merge(exp.operations);
            tmp.add(new Operation(trueLabel));
            
            IntermediateStatement trueStatements = (IntermediateStatement) this.visit(n.s1);
            tmp = tmp.merge(trueStatements.operations);
            ret.operations = tmp;
            ret.setNext(merge(exp.getFalse(), trueStatements.getNext()));

            return ret;
        }

        return null;
    }

    public IntermediateForm visit(ElseIfStatement n) {
        return null;
    }

    public IntermediateForm visit(While n) {
        return null;
    }

    public IntermediateForm visit(For n) {
        return null;
    }

    public IntermediateForm visit(ForInit n) {
        return null;
    }

    public IntermediateForm visit(SwitchStatement n) {
        return null;
    }

    public IntermediateForm visit(SwitchCaseStatement n) {
        return null;
    }

    public IntermediateForm visit(VariableDeclaration n) {
        return null;
    }

    public IntermediateForm visit(VariableDeclarator n) {
        return null;
    }

    public IntermediateForm visit(Return n) {
        IntermediateStatement ret = new IntermediateStatement();
        
        IntermediateExpression exp = (IntermediateExpression) n.e.accept(this);
        ret.operations.add(new Operation("RET", exp.getPlace().toString(), "", Operation.Operations.ASSIGN));
        
        return ret;
    }

    public IntermediateForm visit(Read n) {
        IntermediateStatement ret = new IntermediateStatement();
        
        IntermediateExpression exp = (IntermediateExpression) n.e.accept(this);
        ret.operations = ret.operations.merge(exp.operations);
        ret.operations.add(new Operation("", exp.getPlace().toString(), "", Operation.Operations.READ));
        
        return ret;
    }

    public IntermediateForm visit(Write n) {
        IntermediateStatement ret = new IntermediateStatement();
        
        IntermediateExpression exp = (IntermediateExpression) n.e.accept(this);
        ret.operations = ret.operations.merge(exp.operations);
        ret.operations.add(new Operation("", exp.getPlace().toString(), "", Operation.Operations.PRINT));
        
        return ret;
    }

    public IntermediateForm visit(Exp n) {
        if (n instanceof NumericExp) {
            return ((NumericExp) n).accept(this);
        } else if (n instanceof BooleanExp) {
            return ((BooleanExp) n).accept(this);
        } else if (n instanceof LogicalExp) {
            return ((LogicalExp) n).accept(this);
        } else if (n instanceof LiteralExp) {
            return ((LiteralExp) n).accept(this);
        } else if (n instanceof FunctionCall) {
            return ((FunctionCall) n).accept(this);
        } else if (n instanceof Identifier) {
            return ((Identifier) n).accept(this);
        } else if (n instanceof LParExpRPar) {
            return ((LParExpRPar) n).accept(this);
        } else {
            return new IntermediateExpression();
        }
    }

    public IntermediateForm visit(NumericExp n) {
        if (n instanceof Umin) {
            return ((Umin) n).accept(this);
        } else if (n instanceof Upinc) {
            return ((Upinc) n).accept(this);
        } else if (n instanceof Updec) {
            return ((Updec) n).accept(this);
        } else if (n instanceof Add) {
            return ((Add) n).accept(this);
        } else if (n instanceof AddAssign) {
            return ((AddAssign) n).accept(this);
        } else if (n instanceof Min) {
            return ((Min) n).accept(this);
        } else if (n instanceof MinAssign) {
            return ((MinAssign) n).accept(this);
        } else if (n instanceof Mul) {
            return ((Mul) n).accept(this);
        } else if (n instanceof MulAssign) {
            return ((MulAssign) n).accept(this);
        } else if (n instanceof Div) {
            return ((Div) n).accept(this);
        } else if (n instanceof DivAssign) {
            return ((DivAssign) n).accept(this);
        } else {
            return new IntermediateExpression();
        }
    }

    public IntermediateForm visit(BooleanExp n) {
        if (n instanceof Greater) {
            return ((Greater) n).accept(this);
        } else if (n instanceof Less) {
            return ((Less) n).accept(this);
        } else if (n instanceof GreaterEq) {
            return ((GreaterEq) n).accept(this);
        } else if (n instanceof LessEq) {
            return ((LessEq) n).accept(this);
        } else if (n instanceof Equ) {
            return ((Equ) n).accept(this);
        } else if (n instanceof Neq) {
            return ((Neq) n).accept(this);
        } else {
            return new IntermediateExpression();
        }
    }

    public IntermediateForm visit(LogicalExp n) {
        if (n instanceof Not) {
            return ((Not) n).accept(this);
        } else if (n instanceof Or) {
            return ((Not) n).accept(this);
        } else if (n instanceof And) {
            return ((And) n).accept(this);
        } else if (n instanceof True) {
            return ((True) n).accept(this);
        } else if (n instanceof False) {
            return ((False) n).accept(this);
        } else {
            return new IntermediateExpression();
        }
    }

    public IntermediateForm visit(LiteralExp n) {
        if (n instanceof IntegerLiteral) {
            return ((IntegerLiteral) n).accept(this);
        } else if (n instanceof DoubleLiteral) {
            return ((DoubleLiteral) n).accept(this);
        } else if (n instanceof StringLiteral) {
            return ((StringLiteral) n).accept(this);
        } else if (n instanceof CharLiteral) {
            return ((CharLiteral) n).accept(this);
        } else {
            return new IntermediateExpression();
        }
    }

    public IntermediateForm visit(FunctionCall n) {
        IntermediateExpression ret = new IntermediateExpression();
        
        IntermediateExpression args = (IntermediateExpression) this.visit(n.as);
        OperationList tmp = new OperationList();
        tmp = tmp.merge(args.operations);
        tmp.add(new Operation("", n.i.toString(), Integer.toString(n.as.size()), Operation.Operations.CALL));
        SemanticFunctionTableNode functionInfo = (SemanticFunctionTableNode) semanticTable.findID(n.i.toString(), GLOBAL_SCOPE);
        
        if (functionInfo.getReturnType() != null) {
            ret.setPlace(new Temp("RET"));
        }
        
        return ret;
    }

    public IntermediateForm visit(LParExpRPar n) {
        return n.e1.accept(this);
    }

    public IntermediateForm visit(Identifier n) {
        IntermediateExpression ret = new IntermediateExpression();
        ret.setPlace(new Temp(n.toString()));
        return ret;
    }

    public IntermediateForm visit(IntegerLiteral n) {
        IntermediateExpression ret = new IntermediateExpression();
        
        ret.setPlace(new Temp(Integer.toString(n.i)));
        
        return ret;
    }

    public IntermediateForm visit(DoubleLiteral n) {
        IntermediateExpression ret = new IntermediateExpression();
        
        ret.setPlace(new Temp(Double.toString(n.i)));
        
        return ret;
    }

    public IntermediateForm visit(StringLiteral n) {
        IntermediateExpression ret = new IntermediateExpression();
        
        ret.setPlace(new Temp("\"" + n.i + "\""));
        
        //Guardar en una tabla de Strings
        return ret;
    }

    public IntermediateForm visit(CharLiteral n) {
        IntermediateExpression ret = new IntermediateExpression();
        
        ret.setPlace(new Temp("'" + Character.toString(n.i) + "'"));
        
        return ret;
    }

    public IntermediateForm visit(Umin n) {
        IntermediateExpression ret = new IntermediateExpression();
        Temp tmp = new Temp();
        IntermediateExpression exp = (IntermediateExpression) n.e1.accept(this);
        
        ret.operations = ret.operations.merge(exp.operations);
        
        Operation operation = new Operation(tmp.toString(), exp.getPlace().toString(), "", Operation.Operations.UMIN);
        ret.setPlace(tmp);
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(Uprinc n) {
        /* Nothing to do */
        return null;
    }

    public IntermediateForm visit(Uprdec n) {
        /* Nothing to do */
        return null;
    }

    public IntermediateForm visit(Upinc n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp = (IntermediateExpression) n.e1.accept(this);
        
        ret.operations = ret.operations.merge(exp.operations);
        
        Operation operation = new Operation(exp.getPlace().toString(), exp.getPlace().toString(), "1", Operation.Operations.ADD);
        ret.setPlace(exp.getPlace());
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(Updec n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp = (IntermediateExpression) n.e1.accept(this);
        
        ret.operations = ret.operations.merge(exp.operations);
        
        Operation operation = new Operation(exp.getPlace().toString(), exp.getPlace().toString(), "1", Operation.Operations.MIN);
        ret.setPlace(exp.getPlace());
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(Add n) {
        IntermediateExpression ret = new IntermediateExpression();
        Temp tmp = new Temp();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);
        
        Operation operation = new Operation(tmp.toString(), exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.ADD);
        ret.setPlace(tmp);
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(AddAssign n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);
        
        Operation operation = new Operation(exp1.getPlace().toString(), exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.ADD);
        ret.setPlace(exp1.getPlace());
        
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(Min n) {
        IntermediateExpression ret = new IntermediateExpression();
        Temp tmp = new Temp();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);

        Operation operation = new Operation(tmp.toString(), exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.MIN);
        ret.setPlace(tmp);
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(MinAssign n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);
        
        Operation operation = new Operation(exp1.getPlace().toString(), exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.MIN);
        ret.setPlace(exp1.getPlace());
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(Mul n) {
        IntermediateExpression ret = new IntermediateExpression();
        Temp tmp = new Temp();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);

        Operation operation = new Operation(tmp.toString(), exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.MUL);
        ret.setPlace(tmp);
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(MulAssign n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);

        Operation operation = new Operation(exp1.getPlace().toString(), exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.MUL);
        ret.setPlace(exp1.getPlace());
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(Div n) {
        IntermediateExpression ret = new IntermediateExpression();
        Temp tmp = new Temp();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);
        
        Operation operation = new Operation(tmp.toString(), exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.DIV);
        ret.setPlace(tmp);
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(DivAssign n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);

        Operation operation = new Operation(exp1.getPlace().toString(), exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.ADD);
        ret.setPlace(exp1.getPlace());
        ret.operations.add(operation);

        return ret;
    }

    public IntermediateForm visit(Greater n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);

        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);
        
        Label trueLabel = new Label("");
        Operation operation = new Operation("", exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.IF_GT, trueLabel);
        ret.getTrue().add(trueLabel);
        ret.operations.add(operation);

        Label falseLabel = new Label("");
        ret.operations.add(new Operation("", "", "", Operation.Operations.GOTO, falseLabel));
        ret.getFalse().add(falseLabel);

        return ret;
    }

    public IntermediateForm visit(Less n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);
      
        Label trueLabel = new Label("");
        Operation operation = new Operation("", exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.IF_LT, trueLabel);
        ret.getTrue().add(trueLabel);
        ret.operations.add(operation);

        Label falseLabel = new Label("");
        ret.operations.add(new Operation("", "", "", Operation.Operations.GOTO, falseLabel));
        ret.getFalse().add(falseLabel);

        return ret;
    }

    public IntermediateForm visit(GreaterEq n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);
        
        Label trueLabel = new Label("");
        Operation operation = new Operation("", exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.IF_GEQ, trueLabel);
        ret.getTrue().add(trueLabel);
        ret.operations.add(operation);

        Label falseLabel = new Label("");
        ret.operations.add(new Operation("", "", "", Operation.Operations.GOTO, falseLabel));
        ret.getFalse().add(falseLabel);

        return ret;
    }

    public IntermediateForm visit(LessEq n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);

        Label trueLabel = new Label("");
        Operation operation = new Operation("", exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.IF_LEQ, trueLabel);
        ret.getTrue().add(trueLabel);
        ret.operations.add(operation);

        Label falseLabel = new Label("");
        ret.operations.add(new Operation("", "", "", Operation.Operations.GOTO, falseLabel));
        ret.getFalse().add(falseLabel);

        return ret;
    }

    public IntermediateForm visit(Equ n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);

        Label trueLabel = new Label("");
        Operation operation = new Operation("", exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.IF_EQ, trueLabel);
        ret.getTrue().add(trueLabel);
        ret.operations.add(operation);

        Label falseLabel = new Label("");
        ret.operations.add(new Operation("", "", "", Operation.Operations.GOTO, falseLabel));
        ret.getFalse().add(falseLabel);

        return ret;
    }

    public IntermediateForm visit(Neq n) {
        IntermediateExpression ret = new IntermediateExpression();
        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        ret.operations = ret.operations.merge(exp1.operations);
        ret.operations = ret.operations.merge(exp2.operations);

        Label trueLabel = new Label("");
        Operation operation = new Operation("", exp1.getPlace().toString(), exp2.getPlace().toString(), Operation.Operations.IF_NEQ, trueLabel);
        ret.getTrue().add(trueLabel);
        ret.operations.add(operation);

        Label falseLabel = new Label("");
        ret.operations.add(new Operation("", "", "", Operation.Operations.GOTO, falseLabel));
        ret.getFalse().add(falseLabel);

        return ret;
    }

    public IntermediateForm visit(Not n) {
        IntermediateExpression ret = new IntermediateExpression();

        IntermediateExpression exp = (IntermediateExpression) n.e.accept(this);
        
        ret.setTrue(exp.getFalse());
        ret.setFalse(exp.getTrue());

        ret.operations = exp.operations;

        return ret;
    }

    public IntermediateForm visit(Or n) {
        IntermediateExpression ret = new IntermediateExpression();

        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);
        
        Label falseLabel = new Label();
        OperationList tmpOp = new OperationList();
        tmpOp = tmpOp.merge(exp1.operations);
        tmpOp.add(new Operation(falseLabel));
        tmpOp = tmpOp.merge(exp2.operations);

        ret.operations = tmpOp;
        complete(exp1.getFalse(), falseLabel);
        ret.setTrue(merge(exp1.getTrue(), exp2.getTrue()));
        ret.setFalse(exp2.getFalse());

        return ret;
    }

    public IntermediateForm visit(And n) {
        IntermediateExpression ret = new IntermediateExpression();

        IntermediateExpression exp1 = (IntermediateExpression) n.e1.accept(this);
        IntermediateExpression exp2 = (IntermediateExpression) n.e2.accept(this);

        Label trueLabel = new Label();
        OperationList tmpOp = new OperationList();
        tmpOp = tmpOp.merge(exp1.operations);
        tmpOp.add(new Operation(trueLabel));
        tmpOp = tmpOp.merge(exp2.operations);

        ret.operations = tmpOp;
        complete(exp1.getTrue(), trueLabel);
        ret.setFalse(merge(exp1.getFalse(), exp2.getFalse()));
        ret.setTrue(exp2.getTrue());

        return ret;
    }

    public IntermediateForm visit(True n) {
        IntermediateExpression ret = new IntermediateExpression();

        Label nextLabel = new Label("");
        ret.operations.add(new Operation("", "", "", Operation.Operations.GOTO, nextLabel));
        ret.getTrue().add(nextLabel);

        return ret;
    }

    public IntermediateForm visit(False n) {
        IntermediateExpression ret = new IntermediateExpression();

        Label nextLabel = new Label("");
        ret.operations.add(new Operation("", "", "", Operation.Operations.GOTO, nextLabel));
        ret.getFalse().add(nextLabel);

        return ret;
    }

    public IntermediateForm visit(Parameter n) {
        return null;
    }

    public IntermediateForm visit(IntegerType n) {
        return null;
    }

    public IntermediateForm visit(NullType n) {
        return null;
    }

    public IntermediateForm visit(ErrorType n) {
        return null;
    }

    public IntermediateForm visit(DoubleType n) {
        return null;
    }

    public IntermediateForm visit(StringType n) {
        return null;
    }

    public IntermediateForm visit(CharType n) {
        return null;
    }

    public IntermediateForm visit(Assign n) {
        return null;
    }

    public IntermediateForm visit(BooleanLiteral n) {
        return null;
    }

    public IntermediateForm visit(BooleanType n) {
        return null;
    }

    public IntermediateForm visit(IdentifierExp n) {
        return null;
    }

    public IntermediateForm visit(IdentifierType n) {
        return null;
    }

    public IntermediateForm visit(Type n) {
        return null;
    }

    public IntermediateForm visit(Arguments n) {
        return null;
    }

    public IntermediateForm visit(Argument n) {
        return null;
    }

    public IntermediateForm visit(ElseIfStatements n) {
        return null;
    }

    public IntermediateForm visit(SwitchCaseStatements n) {
        return null;
    }

    public IntermediateForm visit(SwitchCaseExpList n) {
        return null;
    }

    public IntermediateForm visit(VariableDeclarators n) {
        return null;
    }

    public IntermediateForm visit(Parameters n) {
        return null;
    }

    public IntermediateForm visit(VariableDeclarations n) {
        return null;
    }

    public IntermediateForm visit(Statements n) {
        IntermediateStatement ret = new IntermediateStatement();

        for (int i = 0; i < n.size(); i++) {
            
            IntermediateStatement currentStatement = (IntermediateStatement) n.elementAt(i).accept(this);
            if (currentStatement != null) {
                Label nextLabel = new Label();
                ret.operations = ret.operations.merge(currentStatement.operations);
                ret.operations.add(new Operation(nextLabel));
                complete(currentStatement.getNext(), nextLabel);
            }
            
        }

        return ret;
    }

    public IntermediateForm visit(MainClass n) {
        return null;
    }

    public IntermediateForm visit(FunctionDeclarations n) {
        return null;
    }
}
