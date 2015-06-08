package edu.unitec.visitor;

import edu.unitec.ast.*;
import edu.unitec.matrox.SemanticAnalysis;
import edu.unitec.matrox.SemanticFunctionTableNode;
import edu.unitec.matrox.SemanticTableNode;
import edu.unitec.matrox.SemanticVariableTableNode;
import java.util.Vector;

public class TypeDepthFirstVisitor implements TypeVisitor {

    private String scope;
    private Type currentFunctionReturnType;
    private SemanticAnalysis semanticTable;

    public TypeDepthFirstVisitor(SemanticAnalysis semanticTable) {
        this.semanticTable = semanticTable;
    }

    public void errorComplain(String message) {
        System.err.println(message);
    }

    public Type visit(Program n) {
        FunctionDeclarations allFunctions = n.fds;

        //Verificando función por función si están correctamente declaradas
        for (int i = 0; i < allFunctions.size(); i++) {
            allFunctions.elementAt(i).accept(this);
        }

        //Continuando con el llamado de instrucción por instrucción
        for (int i = 0; i < allFunctions.size(); i++) {
            FunctionDeclaration currentFunction = allFunctions.elementAt(i);
            currentFunctionReturnType = currentFunction.t;
            Statements currentStatements = currentFunction.ss;
            for (int j = 0; j < currentStatements.size(); j++) {
                currentStatements.elementAt(j).accept(this);
            }
        }

        return new NullType();
    }

    public Type visit(FunctionDeclaration n) {
        //Variables necesarias para la tabla de símbolos
        Type returnType = n.t;
        Identifier id = n.i;
        //Revisando los parámetros
        Vector<Type> paramTypes = new Vector();
        Parameters functionParams = n.ps;
        if (functionParams != null) {
            for (int j = 0; j < functionParams.size(); j++) {
                Parameter currentParam = functionParams.elementAt(j);
                Type paramType = currentParam.accept(this);
                paramTypes.add(paramType);
            }
        }
        //Comprobación de tipos para la función
        SemanticFunctionTableNode neoNode = new SemanticFunctionTableNode(returnType, paramTypes, id.toString(), scope);
        if (!semanticTable.addID(id.toString(), neoNode)) {
            errorComplain(id.toString() + "is already taken.");
        }

        return new NullType();
    }

    public Type visit(Statement n) {
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
            return new ErrorType();
        }
    }

    public Type visit(If n) {
        Type trueExp = n.e.accept(this);
        if (!(trueExp instanceof BooleanType)) {
            //Mala expresión
            return new ErrorType();
        }

        for (int i = 0; i < n.s1.size(); i++) {
            n.s1.elementAt(i).accept(this);
        }
        
        if (n.eis != null) {
            for (int i = 0; i < n.eis.size(); i++) {
                n.eis.elementAt(i).accept(this);
            }
        }
        
        if (n.s2 != null) {
            for (int i = 0; i < n.s2.size(); i++) {
                n.s2.elementAt(i).accept(this);
            }
        }
        
        return new NullType();
    }

    public Type visit(ElseIfStatement n) {
        Type trueExpType = n.e.accept(this);
        
        if (!(trueExpType instanceof BooleanType)) {
            //Mala expresión
            return new ErrorType();
        }
        
        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
        return new NullType();
    }

    public Type visit(While n) {
        Type trueExpType = n.e.accept(this);
        
        if (!(trueExpType instanceof BooleanType)) {
            //No es booleana la exp
            return new ErrorType();
        }

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
        return null;
    }

    public Type visit(For n) {
        n.fi.accept(this);
        
        Type centerCondType = n.e1.accept(this);
        if (!(centerCondType instanceof BooleanType)) {
            //No es una expresión booleana
            return new ErrorType();
        }
           
        if (n.e2 != null)
            n.e2.accept(this);
        
        if (n.vd != null)
            n.vd.accept(this);

        for (int i = 0; i < n.s.size(); i++) {
            n.s.elementAt(i).accept(this);
        }
        return new NullType();
    }

    public Type visit(ForInit n) {
        if (n.vd != null)
            return n.vd.accept(this);
        
        if (n.vdn != null)
            return n.vdn.accept(this);
        
        return new ErrorType();
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
        if (n.i != null) {
            //Agregar el nodo a la tabla de simbolos
            SemanticVariableTableNode neoVar = new SemanticVariableTableNode(n.t, n.i.toString(), scope);
            if (!semanticTable.addID(n.i.toString(), neoVar)) {
                //El identificador ya existe
                return new ErrorType();
            }
            return new NullType();
        }
        
        if (n.vds != null) {
            for (int i = 0; i < n.vds.size(); i++) {
                VariableDeclarator currentVar = n.vds.elementAt(i);
                SemanticVariableTableNode neoVar = new SemanticVariableTableNode(n.t, currentVar.i.toString(), scope);
                if (!semanticTable.addID(currentVar.i.toString(), neoVar)) {
                    //El identificador existe
                    return new ErrorType();
                }
                
                return new NullType();
            }
        }

        return new ErrorType();
    }

    public Type visit(VariableDeclarator n) {
        SemanticVariableTableNode varInfo = null;
        
        if (semanticTable.findID(n.i.toString()) instanceof SemanticVariableTableNode) {
            varInfo = (SemanticVariableTableNode) semanticTable.findID(n.i.toString());
            Type expType = n.e.accept(this);
            if (expType.equals(varInfo.getType())) {
                return new NullType();
            } else {
                //POrque la exp no es asignamble
                return new ErrorType();
            }
        } else {
            //No se encuentra el id
            return new ErrorType();
        }
    }

    public Type visit(Return n) {
        Type expRetType = n.e.accept(this);
        
        if (!(currentFunctionReturnType.equals(expRetType))) {
            //Typo de retorno no es el correcto
            return new ErrorType();
        }
            
        return new NullType();
    }

    public Type visit(Read n) {
        if (!(n.e instanceof Identifier)) {
            //No se está asignando una lectura a un identificador
            return new ErrorType();
        }
        return new NullType();
    }

    public Type visit(Write n) {
        n.e.accept(this);
        return new NullType();
    }

    public Type visit(Exp n) {
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
            return new ErrorType();
        }
    }

    public Type visit(NumericExp n) {
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
            return new ErrorType();
        }
    }

    public Type visit(BooleanExp n) {
        if (n instanceof Greater)
            return ((Greater) n).accept(this);
        else if (n instanceof Less)
            return ((Less) n).accept(this);
        else if (n instanceof GreaterEq)
            return ((GreaterEq) n).accept(this);
        else if (n instanceof LessEq)
            return ((LessEq) n).accept(this);
        else if (n instanceof Equ)
            return ((Equ) n).accept(this);
        else if (n instanceof Neq)
            return ((Neq) n).accept(this);
        else 
            return new ErrorType();
    }

    public Type visit(LogicalExp n) {
        if (n instanceof Not)
            return ((Not) n).accept(this);
        else if (n instanceof Or)
            return ((Not) n).accept(this);
        else if (n instanceof And)
            return ((And) n).accept(this);
        else if (n instanceof True)
            return ((True) n).accept(this);
        else if (n instanceof False)
            return ((False) n).accept(this);
        else 
            return new ErrorType();
    }

    public Type visit(LiteralExp n) {
        if (n instanceof IntegerLiteral)
            return ((IntegerLiteral) n).accept(this);
        else if (n instanceof DoubleLiteral)
            return ((DoubleLiteral) n).accept(this);
        else if (n instanceof StringLiteral)
            return ((StringLiteral) n).accept(this);
        else if (n instanceof CharLiteral)
            return ((CharLiteral) n).accept(this);
        else 
            return new ErrorType();
    }

    public Type visit(FunctionCall n) {
        SemanticFunctionTableNode functionInfo = null;
        if (semanticTable.findID(n.i.toString()) instanceof SemanticFunctionTableNode) {
            functionInfo = (SemanticFunctionTableNode) semanticTable.findID(n.i.toString());
            Vector<Type> paramTypes = functionInfo.getParams();
            Arguments args = n.as;
            //Verificar cuando sea sobrecarga
            if (paramTypes.size() == args.size()) {
                for (int i = 0; i < paramTypes.size(); i++) {
                    Type currentArgType = args.elementAt(i).accept(this);
                    if (!(currentArgType.equals(paramTypes.get(i)))) {
                        //El parámetro encontrado no es del tipo que corresponde en esa posición
                        return new ErrorType();
                    }
                }
            } else {
                //La cantidad de parámetros es diferente
            }
        } else {
            //El identificador no es una función
            return new ErrorType();
        }
        
        if (n.as != null) {
            for (int i = 0; i < n.as.size(); i++) {
                n.as.elementAt(i).accept(this);
            }
        }

        return null;
    }

    public Type visit(LParExpRPar n) {
        return n.e1.accept(this);
    }

    public Type visit(Identifier n) {
        SemanticVariableTableNode varInfo = null;
        if (semanticTable.findID(n.toString()) instanceof SemanticVariableTableNode) {
            varInfo = (SemanticVariableTableNode) semanticTable.findID(n.toString());
            return varInfo.getType();
        } else {
            //No es una variable
            return new ErrorType();
        }
    }

    public Type visit(IntegerLiteral n) {
        return new IntegerType();
    }

    public Type visit(DoubleLiteral n) {
        return new DoubleType();
    }

    public Type visit(StringLiteral n) {
        return new StringType();
    }

    public Type visit(CharLiteral n) {
        return new CharType();
    }

    public Type visit(Umin n) {
        Type expType = n.e1.accept(this);
        if (!(expType instanceof IntegerType || expType instanceof DoubleType)) {
            return new ErrorType();
        } else {
            return expType;
        }
    }

    public Type visit(Uprinc n) {
        return null;
    }

    public Type visit(Uprdec n) {
        return null;
    }

    public Type visit(Upinc n) {
        Type expType = n.e1.accept(this);
        if (!(expType instanceof IntegerType || expType instanceof DoubleType)) {
            return new ErrorType();
        } else {
            return expType;
        }
    }

    public Type visit(Updec n) {
        Type expType = n.e1.accept(this);
        if (!(expType instanceof IntegerType || expType instanceof DoubleType)) {
            return new ErrorType();
        } else {
            return expType;
        }
    }

    public Type visit(Add n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            if (leftExp instanceof DoubleType || leftExp instanceof IntegerType || leftExp instanceof StringType) {
                return leftExp;
            }
        }

        return new ErrorType();
    }

    public Type visit(AddAssign n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            if (leftExp instanceof DoubleType || leftExp instanceof IntegerType || leftExp instanceof StringType) {
                return leftExp;
            }
        }

        return new ErrorType();
    }

    public Type visit(Min n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            if (leftExp instanceof DoubleType || leftExp instanceof IntegerType) {
                return leftExp;
            }
        }

        return new ErrorType();
    }

    public Type visit(MinAssign n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            if (leftExp instanceof DoubleType || leftExp instanceof IntegerType) {
                return leftExp;
            }
        }

        return new ErrorType();
    }

    public Type visit(Mul n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            if (leftExp instanceof DoubleType || leftExp instanceof IntegerType) {
                return leftExp;
            }
        }

        return new ErrorType();
    }

    public Type visit(MulAssign n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            if (leftExp instanceof DoubleType || leftExp instanceof IntegerType) {
                return leftExp;
            }
        }

        return new ErrorType();
    }

    public Type visit(Div n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            if (leftExp instanceof DoubleType || leftExp instanceof IntegerType) {
                return leftExp;
            }
        }

        return new ErrorType();
    }

    public Type visit(DivAssign n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            if (leftExp instanceof DoubleType || leftExp instanceof IntegerType) {
                return leftExp;
            }
        }

        return new ErrorType();
    }

    public Type visit(Greater n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            return new BooleanType();
        }

        return new ErrorType();
    }

    public Type visit(Less n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            return new BooleanType();
        }

        return new ErrorType();
    }

    public Type visit(GreaterEq n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            return new BooleanType();
        }

        return new ErrorType();
    }

    public Type visit(LessEq n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            return new BooleanType();
        }

        return new ErrorType();
    }

    public Type visit(Equ n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            return new BooleanType();
        }

        return new ErrorType();
    }

    public Type visit(Neq n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);

        if (leftExp.equals(rightExp)) {
            return new BooleanType();
        }

        return new ErrorType();
    }

    public Type visit(Not n) {
        Type exp = n.e.accept(this);
        
        if (exp instanceof BooleanType)
            return exp;
        else 
            return new ErrorType();
    }

    public Type visit(Or n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);
        
        if (leftExp instanceof BooleanType && rightExp instanceof BooleanType)
            return leftExp;
        else 
            return new ErrorType();
    }

    public Type visit(And n) {
        Type leftExp = n.e1.accept(this);
        Type rightExp = n.e2.accept(this);
        
        if (leftExp instanceof BooleanType && rightExp instanceof BooleanType)
            return leftExp;
        else 
            return new ErrorType();
    }

    public Type visit(True n) {
        return new BooleanType();
    }

    public Type visit(False n) {
        return new BooleanType();
    }

    public Type visit(Parameter n) {
        return n.t;
    }

    public Type visit(IntegerType n) {
        return n;
    }

    public Type visit(NullType n) {
        return n;
    }

    public Type visit(ErrorType n) {
        return n;
    }

    public Type visit(DoubleType n) {
        return n;
    }

    public Type visit(StringType n) {
        return n;
    }

    public Type visit(CharType n) {
        return n;
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
