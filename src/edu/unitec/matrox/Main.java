package edu.unitec.matrox;

import edu.unitec.ast.Program;
import edu.unitec.intermediatelanguage.IntermediateStatement;
import edu.unitec.visitor.IntermediateCodeGenerator;
import edu.unitec.visitor.TypeDepthFirstVisitor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java_cup.runtime.Symbol;

public class Main {

    static public void main(String args[]) {
        /* Errors */
        if (args.length != 1) {
            System.err.println("No input file.");
            System.exit(1);
        }
        if (!args[0].endsWith(".mtx")) {
            System.err.println("Invalid file extension.");
            System.exit(1);
        }
        /* End Errors */
        try {
            File file = new File(args[0]);
            Lexer lexer = new Lexer(new InputStreamReader(new FileInputStream(file)));
            Parser parse = new Parser(lexer);
            parse.parse();
            Program generatedProgram = parse.getGeneratedProgram();
            SemanticAnalysis semanticTable = new SemanticAnalysis();
            TypeDepthFirstVisitor tdfv = new TypeDepthFirstVisitor(semanticTable);
            generatedProgram.accept(tdfv);
            System.out.println(semanticTable);
            if (!tdfv.hasErrors()) {
                //Correr las generaciones de còdigo
                File interOut = new File(file.getAbsolutePath().replace(".mtx", "")  + ".o");
                IntermediateCodeGenerator icg = new IntermediateCodeGenerator(interOut, semanticTable);
                IntermediateStatement interForm = (IntermediateStatement) icg.visit(generatedProgram);
                icg.creatFile(interForm.buildIntermediateCode());
                
                
                File interOut2 = new File(file.getAbsolutePath().replace(".mtx", "")  + ".s");
                FinalCodeBuilder fcb = new FinalCodeBuilder(semanticTable, interOut2 ,interForm, icg.getStringsTable());
                System.out.println(fcb.buildFinalCode());
            }
            
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
