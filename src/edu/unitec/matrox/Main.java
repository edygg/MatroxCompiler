package edu.unitec.matrox;

import edu.unitec.ast.Program;
import edu.unitec.visitor.TypeDepthFirstVisitor;
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
            Lexer lexer = new Lexer(new InputStreamReader(new FileInputStream(args[0])));
            Parser parse = new Parser(lexer);
            parse.parse();
            Program generatedProgram = parse.getGeneratedProgram();
            SemanticAnalysis semanticTable = new SemanticAnalysis();
            TypeDepthFirstVisitor tdfv = new TypeDepthFirstVisitor(semanticTable);
            generatedProgram.accept(tdfv);
            System.out.println(semanticTable);
            
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
