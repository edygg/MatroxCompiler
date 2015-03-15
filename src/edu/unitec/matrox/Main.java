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
         // Parser parse = new Parser(new InputStreamReader(new FileInputStream(args[0])));
         // parse.parse();
          Lexer lexer = new Lexer(new InputStreamReader(new FileInputStream(args[0])));
          Symbol token = lexer.next_token();
          while (token.sym != sym.EOF) {
              System.out.printf("Token Name: %-20s\tToken value: %-30s\n", sym.terminalNames[token.sym], token.value);
              token = lexer.next_token();
          }
      } catch (FileNotFoundException ex) {
          System.err.println("File not found.");
      } catch (Exception ex) {
          System.err.println(ex.getMessage());
      }
  }
}

