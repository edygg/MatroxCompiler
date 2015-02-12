package edu.unitec.matrox;

import java.io.*;
   
public class Main {
  static public void main(String argv[]) {    
	try {
    		Lexer scanner = new Lexer(new FileReader("./src/SomeFile.txt"));
	    	scanner.yylex();
	} catch (Exception e) {}
        
        
  }
}

