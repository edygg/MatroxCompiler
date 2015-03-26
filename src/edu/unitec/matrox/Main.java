package edu.unitec.matrox;

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
            Lexer lexer2 = new Lexer(new InputStreamReader(new FileInputStream(args[0])));
            Symbol token = lexer2.next_token();
            while (token.sym != sym.EOF) {
                System.out.printf("Token Name: %-20s\tToken value: %-30s\n", sym.terminalNames[token.sym], token.value);
                token = lexer2.next_token();
            }
            lexer2.yyclose();
            System.out.println("\n\n");
            
            Lexer lexer = new Lexer(new InputStreamReader(new FileInputStream(args[0])));
            Parser parse = new Parser(lexer);
            parse.parse();
            
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
        } catch (Exception ex) {
            //System.err.println(ex.getMessage());
        }
    }
}
