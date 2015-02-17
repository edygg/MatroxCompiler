package edu.unitec.matrox;
   
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
  }
}

