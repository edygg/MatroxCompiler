package edu.unitec.matrox;
   
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  static public void main(String args[]) {    
      /* Errors */
      if (args.length != 1) {
          System.err.println("No input file.");
      }
      if (!args[0].endsWith(".mtx")) {
          System.err.println("Invalid file extension.");
      }
      /* End Errors */
      try {
          Parser parse = new Parser(new InputStreamReader(new FileInputStream(args[0])));
      } catch (FileNotFoundException ex) {
          System.err.println("File not found.");
      } catch (IOException ex) {
          System.err.println("Error.");
      }
  }
}

