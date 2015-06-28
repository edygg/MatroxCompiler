package edu.unitec.matrox;

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

    private SemanticAnalysis semanticTable;
    private BufferedWriter out;
    private HashMap<String, Boolean> avalibleTemps;
    private HashMap<String, String> finalTemps;
    private IntermediateStatement intermediateForm;
    private List<String> stringsTable;

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
        StringBuilder sbText = new StringBuilder();

        for (int i = 0; i < intermediateForm.operations.size(); i++) {
            Operation currentOp = intermediateForm.operations.elementAt(i);
            switch (currentOp.getType()) {
                case ADD: {
                    String t1 = getAvaliableTemp();
                    String t2 = getAvaliableTemp();
                    String t3 = getAvaliableTemp();

                    sbText.append("lw " + t1 + ", " + currentOp.getOp1() + "\n " + "lw " + t2 + ", " + currentOp.getOp2() + "\n addi " + t3 + ", " + t1 + ", " + t2);

                    setAvaliableTemp(t1);
                    setAvaliableTemp(t2);
                    
                    finalTemps.put(currentOp.getStore(), t3);
                    break;
                }
                case MIN: {
                    String t1 = getAvaliableTemp();
                    String t2 = getAvaliableTemp();
                    String t3 = getAvaliableTemp();

                    sbText.append("lw " + t1 + ", " + currentOp.getOp1() + "\n " + "lw " + t2 + ", " + currentOp.getOp2() + "\n addi " + t3 + ", " + t1 + ", -" + t2);

                    setAvaliableTemp(t1);
                    setAvaliableTemp(t2);
                    
                    finalTemps.put(currentOp.getStore(), t3);
                    break;
                }
                case UMIN: {

                    break;
                }
                case MUL: {
                    String t1 = getAvaliableTemp();
                    String t2 = getAvaliableTemp();
                    String t3 = getAvaliableTemp();

                    sbText.append("lw " + t1 + ", " + currentOp.getOp1() + "\n " + "lw " + t2 + ", " + currentOp.getOp2() + "\n mul " + t3 + ", " + t1 + ", " + t2);

                    setAvaliableTemp(t1);
                    setAvaliableTemp(t2);
                    
                    finalTemps.put(currentOp.getStore(), t3);
                    break;
                }
                case DIV: {
                    String t1 = getAvaliableTemp();
                    String t2 = getAvaliableTemp();
                    String t3 = getAvaliableTemp();

                    sbText.append("lw " + t1 + ", " + currentOp.getOp1() + "\n " + "lw " + t2 + ", " + currentOp.getOp2() + "\n div " + t3 + ", " + t1 + ", " + t2);

                    setAvaliableTemp(t1);
                    setAvaliableTemp(t2);
                    
                    finalTemps.put(currentOp.getStore(), t3);
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
                    sbText.append("sw " + finalTemps.get(currentOp.getStore()) + ", " + currentOp.getStore());
                    //sb.append(currentOp.getStore() + " := " + currentOp.getOp1());
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
                    //sb.append("print " + currentOp.getOp1());
                    break;
                }
                case READ: {
                    //sb.append("read " + currentOp.getOp1());
                    break;
                }
                case LABEL: {
                    //sb.append(currentOp.getLabel() + ":");
                    break;
                }
                case EXIT: {
                    //sb.append(currentOp.getOp1());
                    break;
                }
                case VOID_RET: {
                    //sb.append(currentOp.getOp1());
                    break;
                }
            }
            sbText.append("\n");
        }

        return sbText.toString();
    }

}
