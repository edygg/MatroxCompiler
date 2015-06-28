package edu.unitec.matrox;

import edu.unitec.ast.CharType;
import edu.unitec.ast.DoubleType;
import edu.unitec.ast.IntegerType;
import edu.unitec.ast.StringType;
import edu.unitec.intermediatelanguage.IntermediateStatement;
import edu.unitec.intermediatelanguage.Operation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Edilson Gonzalez
 */
public class FinalCodeBuilder {

    public static final String $v0 = "$v0";
    public static final String $v1 = "$v1";

    public static final String $a0 = "$a0";
    public static final String $a1 = "$a1";
    public static final String $a2 = "$a2";
    public static final String $a3 = "$a3";

    public static final String $t0 = "$t0";
    public static final String $t1 = "$t1";
    public static final String $t2 = "$t2";
    public static final String $t3 = "$t3";
    public static final String $t4 = "$t4";
    public static final String $t5 = "$t5";
    public static final String $t6 = "$t6";
    public static final String $t7 = "$t7";

    public static final String $s0 = "$s0";
    public static final String $s1 = "$s1";
    public static final String $s2 = "$s2";
    public static final String $s3 = "$s3";
    public static final String $s4 = "$s4";
    public static final String $s5 = "$s5";
    public static final String $s6 = "$s6";
    public static final String $s7 = "$s7";

    public static final String $sp = "$sp";
    public static final String $fp = "$fp";
    public static final String $ra = "$ra";

    private enum OperationType { INTEGER_OPERATION, DOUBLE_OPERATION, CHAR_OPERATION }
    private static final String GLOBAL_SCOPE = "s0";
    
    private SemanticAnalysis semanticTable;
    private BufferedWriter out;
    private HashMap<String, Boolean> avalibleTemps;
    private HashMap<String, Info> finalTemps;
    private IntermediateStatement intermediateForm;
    private List<String> stringsTable;
    
    private class Info {
        public String reg;
        public OperationType type;
        
        public Info(String reg, OperationType type) {
            this.reg = reg;
            this.type = type;
        }
    }

    public FinalCodeBuilder(SemanticAnalysis semanticTable, File out, IntermediateStatement intermediateForm, List<String> stringsTable) throws IOException {
        this.semanticTable = semanticTable;
        this.out = new BufferedWriter(new FileWriter(out));
        this.avalibleTemps = new HashMap();
        this.finalTemps = new HashMap();
        this.stringsTable = stringsTable;

        // Filling hashmap
        avalibleTemps.put($v0, true);
        avalibleTemps.put($v1, true);

        avalibleTemps.put($a0, true);
        avalibleTemps.put($a1, true);
        avalibleTemps.put($a2, true);
        avalibleTemps.put($a3, true);

        avalibleTemps.put($t0, true);
        avalibleTemps.put($t1, true);
        avalibleTemps.put($t2, true);
        avalibleTemps.put($t3, true);
        avalibleTemps.put($t4, true);
        avalibleTemps.put($t5, true);
        avalibleTemps.put($t6, true);
        avalibleTemps.put($t7, true);

        avalibleTemps.put($s0, true);
        avalibleTemps.put($s1, true);
        avalibleTemps.put($s2, true);
        avalibleTemps.put($s3, true);
        avalibleTemps.put($s4, true);
        avalibleTemps.put($s5, true);
        avalibleTemps.put($s6, true);
        avalibleTemps.put($s7, true);

        this.intermediateForm = intermediateForm;
    }

    private String getAvaliableTemp() {
        for (int i = 0; i < 8; i++) {
            if (avalibleTemps.get("$t" + i)) {
                avalibleTemps.put("$t" + i, false);
                return "$t" + i;
            }
        }

        return null;
    }

    private void setAvaliableTemp(String reg) {
        avalibleTemps.put(reg, true);
    }

    public String buildFinalCode() {
        StringBuilder sbData = new StringBuilder();
        //sacando main
        SemanticFunctionTableNode mainInfo = (SemanticFunctionTableNode) semanticTable.findID("main");
        List<SemanticVariableTableNode> vars = semanticTable.getAllVariables(mainInfo.getScope());
        
        sbData.append(".data\n");
        
        for (int i = 0; i < vars.size(); i++) {
            SemanticVariableTableNode cur = vars.get(i);
            if (cur.getType().equals(new IntegerType())) {
                sbData.append(cur.getName() + ": \t.word\t0");
            } else if (cur.getType().equals(new StringType())) {
                sbData.append(cur.getName() + ": \t.space\t255");
            } else if (cur.getType().equals(new CharType())) {
                sbData.append(cur.getName() + ": \t.byte\t' '");
            } else if (cur.getType().equals(new DoubleType())) {
                sbData.append(cur.getName() + ": \t.double\t0.0");
            }
            sbData.append("\n");
        }
        
        for (int i = 0; i < stringsTable.size(); i++) {
            sbData.append("message" + i + ": \t.asciiz\t\""+ stringsTable.get(i) + "\"\n");
        }
        
        //variable para imprimir caracteres
        sbData.append("character:\t.asciiz\t\" \"\n");
        sbData.append("integer:\t.word\t0\n");
        sbData.append("double:\t.double\t0.0\n");
        
        sbData.append("\n.text\n.globl main\n\n");
        
        StringBuilder sbText = new StringBuilder();

        for (int i = 0; i < intermediateForm.operations.size(); i++) {
            Operation currentOp = intermediateForm.operations.elementAt(i);
            switch (currentOp.getType()) {
                case ADD: {
                    String t3 = getAvaliableTemp();
                    OperationType type = null;
                    
                    String t1 = null;
                    if (finalTemps.get(currentOp.getOp1()) != null) {
                        t1 = finalTemps.get(currentOp.getOp1()).reg;
                        type = finalTemps.get(currentOp.getOp1()).type;
                    } else {
                        if (currentOp.getOp1().matches("[0-9]+")) { // es integer
                            t1 = getAvaliableTemp();
                            sbText.append("li " + t1 + ", " + currentOp.getOp1() + "\n");
                            type = OperationType.INTEGER_OPERATION;
                        } else if (currentOp.getOp1().matches("[0-9]*[\\.][0-9]+")) { // es double
                            type = OperationType.DOUBLE_OPERATION;
                        } else { // es identificador
                            SemanticVariableTableNode varInfo = (SemanticVariableTableNode) semanticTable.findID(currentOp.getOp1());
                            if (varInfo.getType().equals(new IntegerType())) {
                                t1 = getAvaliableTemp();
                                sbText.append("lw " + t1 + ", " + currentOp.getOp1() + "\n");
                                type = OperationType.INTEGER_OPERATION;
                            } else {
                                type = OperationType.DOUBLE_OPERATION;
                            }
                        }
                    }
              
                    String t2 = null;
                    if (finalTemps.get(currentOp.getOp2()) != null) {
                        t2 = finalTemps.get(currentOp.getOp2()).reg;
                        type = finalTemps.get(currentOp.getOp2()).type;
                    } else {
                        if (currentOp.getOp2().matches("[0-9]+")) { // es integer
                            t2 = getAvaliableTemp();
                            sbText.append("li " + t2 + ", " + currentOp.getOp2() + "\n");
                        } else if (currentOp.getOp1().matches("[0-9]*[\\.][0-9]+")) { // es double

                        } else {
                            SemanticVariableTableNode varInfo = (SemanticVariableTableNode) semanticTable.findID(currentOp.getOp2());
                            if (varInfo.getType().equals(new IntegerType())) {
                                t2 = getAvaliableTemp();
                                sbText.append("lw " + t2 + ", " + currentOp.getOp2() + "\n");
                                type = OperationType.INTEGER_OPERATION;
                            } else {
                                type = OperationType.DOUBLE_OPERATION;
                            }
                        }
                    }
                    
                    if (type == OperationType.INTEGER_OPERATION){
                        sbText.append("add " + t3 + ", " + t1 + ", " + t2);
                    } else {
                    
                    }
                    
                    setAvaliableTemp(t1);
                    setAvaliableTemp(t2);
                    
                    finalTemps.put(currentOp.getStore(), new Info(t3, type));
                    if (finalTemps.get(currentOp.getOp1()) != null) {
                        finalTemps.remove(currentOp.getOp1());
                    }
                    if (finalTemps.get(currentOp.getOp2()) != null) {
                        finalTemps.remove(currentOp.getOp2());
                    }
                    break;
                }
                case MIN: {
                    
                    break;
                }
                case UMIN: {

                    break;
                }
                case MUL: {
                    
                    break;
                }
                case DIV: {
                    
                    break;
                }
                case IF_GEQ: {
                    //sb.append("if " + currentOp.getOp1() + " >= " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_LEQ: {
                    //sb.append("if " + currentOp.getOp1() + " <= " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_GT: {
                    //sb.append("if " + currentOp.getOp1() + " > " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_LT: {
                    //sb.append("if " + currentOp.getOp1() + " < " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_NEQ: {
                    //sb.append("if " + currentOp.getOp1() + " <> " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case IF_EQ: {
                    //sb.append("if " + currentOp.getOp1() + " = " + currentOp.getOp2() + " goto " + currentOp.getLabel());
                    break;
                }
                case ASSIGN: {
                    OperationType type = null;
                    
                    if (currentOp.getOp1().matches("[\\'][\\w\\W][\\']")) { // Es char
                        String t1 = null;
                        
                        if (finalTemps.get(currentOp.getOp1()) != null) {
                            t1 = finalTemps.get(currentOp.getOp1()).reg;
                            type = finalTemps.get(currentOp.getOp1()).type;
                        } else {
                            t1 = getAvaliableTemp();
                            sbText.append("li " + t1 + ", " + currentOp.getOp1() + "\n");
                            type = OperationType.CHAR_OPERATION;
                        }
                        
                        sbText.append("sb " + t1 + ", " + currentOp.getStore());
                        
                        setAvaliableTemp(t1);
                        if (finalTemps.get(currentOp.getOp1()) != null) {
                            finalTemps.remove(currentOp.getOp1());
                        }
                    } else if (currentOp.getOp1().matches("[0-9]+")) { // es integer
                        String t1 = null;

                        if (finalTemps.get(currentOp.getOp1()) != null) {
                            t1 = finalTemps.get(currentOp.getOp1()).reg;
                            type = finalTemps.get(currentOp.getOp1()).type;
                        } else {
                            t1 = getAvaliableTemp();
                            sbText.append("li " + t1 + ", " + currentOp.getOp1() + "\n");
                        }

                        sbText.append("sw " + t1 + ", " + currentOp.getStore());

                        setAvaliableTemp(t1);
                        if (finalTemps.get(currentOp.getOp1()) != null) {
                            finalTemps.remove(currentOp.getOp1());
                        }
                    } else if (currentOp.getOp1().matches("[0-9]*[\\.][0-9]+")) { // es double
                    
                    } else {
                        String t1 = finalTemps.get(currentOp.getOp1()).reg;
                        type = finalTemps.get(currentOp.getOp1()).type;
                        
                        if(type == OperationType.INTEGER_OPERATION) {
                            sbText.append("sw " + t1 + ", " + currentOp.getStore());
                        }
                    }
                    break;
                }
                case PARAM: {
                    //sb.append("params " + currentOp.getOp1());
                    break;
                }
                case CALL: {
                    //sb.append("call " + currentOp.getOp1() + ", " + currentOp.getOp2());
                    break;
                }
                case GOTO: {
                    //sb.append("goto " + currentOp.getLabel());
                    break;
                }
                case PRINT: {
                    if (currentOp.getOp1().matches("[\\\"][\\w\\W]*[\\\"]")) {
                        sbText.append("li " + $v0 + ", 4\n");
                       
                        sbText.append("la " + $a0 + ", message" + Integer.toString(stringsTable.indexOf(currentOp.getOp1().replaceAll("\"", ""))) + "\n");
                        sbText.append("syscall\n");
                    } else if (currentOp.getOp1().matches("[\\'][\\w\\W][\\']")) {
                        String t1 = getAvaliableTemp();
                        
                        sbText.append("li " + $v0 + ", 4\n");
                        sbText.append("li " + t1 + ", " + currentOp.getOp1() + "\n");
                        sbText.append("la " + $a0 + ", character\n");
                        sbText.append("sb " + t1 + ", (" + $a0 + ")\n");
                        sbText.append("syscall\n");
                        setAvaliableTemp(t1);
                    } else if (currentOp.getOp1().matches("[0-9]+")) {
                        String t1 = getAvaliableTemp();
                        
                        sbText.append("li " + $v0 + ", 1\n");
                        sbText.append("li " + t1 + ", " + currentOp.getOp1() + "\n");
                        sbText.append("sw " + t1 + ", integer\n");
                        sbText.append("lw " + $a0 + ", integer\n");
                        sbText.append("syscall\n");
                        setAvaliableTemp(t1);
                    } else if (currentOp.getOp1().matches("[0-9]*[\\.][0-9]+")) {
                    
                    } else {
                        if (semanticTable.findID(currentOp.getOp1()) != null) {
                            SemanticVariableTableNode varInfo = (SemanticVariableTableNode) semanticTable.findID(currentOp.getOp1());
                            if (varInfo.getType().equals(new IntegerType())) {
                                sbText.append("li " + $v0 + ", 1\n");
                                sbText.append("lw " + $a0 + ", " + varInfo.getName() + "\n");
                                sbText.append("syscall\n");
                            } else if (varInfo.getType().equals(new StringType())) {
                                sbText.append("li " + $v0 + ", 4\n");
                                sbText.append("la " + $a0 + ", " + varInfo.getName() + "\n");
                                sbText.append("syscall\n");
                            } else if (varInfo.getType().equals(new DoubleType())) {
                                
                            } else {
                                String t1 = getAvaliableTemp();
                                sbText.append("li " + $v0 + ", 4\n");
                                sbText.append("lb " + t1 + ", " + varInfo.getName() + "\n");
                                sbText.append("la " + $a0 + ", " + "character" + "\n");
                                sbText.append("sb " + t1 + ", (" + $a0 + ")\n");
                                sbText.append("syscall\n");
                                setAvaliableTemp(t1);
                            }
                        } else {
                            String t1 = finalTemps.get(currentOp.getOp1()).reg;
                            OperationType type = finalTemps.get(currentOp.getOp1()).type;
                            
                            if (type == OperationType.INTEGER_OPERATION) {
                                sbText.append("li " + $v0 + ", 1\n");
                                sbText.append("sw " + t1 + ", integer" + "\n");
                                sbText.append("lw " + $a0 + ", " + "integer" + "\n");
                                sbText.append("syscall\n");
                            } else if (type == OperationType.DOUBLE_OPERATION) {
                                
                            } else {
                            
                            }
                            setAvaliableTemp(t1);
                            finalTemps.remove(currentOp.getOp1());
                        }
                    }
                    
                    break;
                }
                case READ: {
                    //sb.append("read " + currentOp.getOp1());
                    break;
                }
                case LABEL: {
                    sbText.append(currentOp.getLabel() + ":");
                    break;
                }
                case EXIT: {
                    sbText.append("li " + $v0 + ", 10\nsyscall\n");
                    break;
                }
                case VOID_RET: {
                    //sb.append(currentOp.getOp1());
                    break;
                }
            }
            sbText.append("\n");
        }

        return sbData.append(sbText.toString()).toString();
    }

}
