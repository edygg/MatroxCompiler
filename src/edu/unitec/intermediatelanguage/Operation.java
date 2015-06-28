
package edu.unitec.intermediatelanguage;

/**
 *
 * @author Edilson Gonzalez
 */
public class Operation {
    public enum Operations { ADD, MIN, UMIN, MUL, DIV, IF_GEQ, IF_LEQ, IF_GT, IF_LT, IF_NEQ, IF_EQ, ASSIGN, PARAM, CALL, GOTO, PRINT, READ, LABEL, EXIT, VOID_RET }
    private String store;
    private String op1;
    private String op2;
    private Operations type;
    private Label l;

    public Operation(String store, String op1, String op2, Operations type) {
        this.store = store;
        this.op1 = op1;
        this.op2 = op2;
        this.type = type;
    }
    
    public Operation(String store, String op1, String op2, Operations type, Label l) {
        this.store = store;
        this.op1 = op1;
        this.op2 = op2;
        this.type = type;
        this.l = l;
    }

    public Operation(Label l) {
        this.l = l;
        this.type = Operations.LABEL;
    }
    
    public String getStore() {
        return store;
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public Operations getType() {
        return type;
    }
    
    public Label getLabel() {
        return l;
    }
    
}
