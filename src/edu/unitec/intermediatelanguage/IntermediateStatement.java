
package edu.unitec.intermediatelanguage;

import java.util.Vector;

/**
 *
 * @author Edilson Gonzalez
 */
public class IntermediateStatement extends IntermediateForm {
    private Vector<Label> next;
   
    public IntermediateStatement() { 
        this.next = new Vector();
    }


    public Vector<Label> getNext() {
        return next;
    }

    public void setNext(Vector<Label> next) {
        this.next = next;
    }
    
    public String buildIntermediateCode() {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < operations.size(); i++) {
            Operation currentOp = operations.elementAt(i);
            switch(currentOp.getType()) {
                case ADD: {
                    sb.append(currentOp.getStore() + " := " + currentOp.getOp1() + " + " + currentOp.getOp2());
                    break;
                }
                case MIN: {
                    sb.append(currentOp.getStore() + " := " + currentOp.getOp1() + " - " + currentOp.getOp2());
                    break;
                }
                case UMIN: {
                    sb.append(currentOp.getStore() + " := -" + currentOp.getOp1());
                    break;
                }
                case MUL: {
                    sb.append(currentOp.getStore() + " := " + currentOp.getOp1() + " * " + currentOp.getOp2());
                    break;
                }
                case DIV: {
                    sb.append(currentOp.getStore() + " := " + currentOp.getOp1() + " / " + currentOp.getOp2());
                    break;
                }
                case IF_GEQ: {
                    sb.append("if " + currentOp.getOp1() + " >= " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_LEQ: {
                    sb.append("if " + currentOp.getOp1() + " <= " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_GT: {
                    sb.append("if " + currentOp.getOp1() + " > " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_LT: {
                    sb.append("if " + currentOp.getOp1() + " < " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_NEQ: {
                    sb.append("if " + currentOp.getOp1() + " <> " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_EQ: {
                    sb.append("if " + currentOp.getOp1() + " = " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case ASSIGN: {
                    sb.append(currentOp.getStore() + " := " + currentOp.getOp1());
                    break;
                }
                case PARAM: {
                    sb.append("params " + currentOp.getOp1());
                    break;
                }
                case CALL: {
                    sb.append("call " + currentOp.getOp1() + ", " + currentOp.getOp2());
                    break;
                }
                case GOTO: {
                    sb.append("goto " + currentOp.getLabel());
                    break;
                }
                case PRINT: {
                    sb.append("print " + currentOp.getOp1());
                    break;
                }
                case READ: {
                    sb.append("read " + currentOp.getOp1());
                    break;
                }
                case LABEL: {
                    sb.append(currentOp.getLabel() + ":");
                    break;
                }
                case EXIT: {
                    sb.append(currentOp.getOp1());
                    break;
                }
                case VOID_RET: {
                    sb.append(currentOp.getOp1());
                    break;
                }
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
}
