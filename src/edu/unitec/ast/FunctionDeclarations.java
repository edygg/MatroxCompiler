package edu.unitec.ast;

import java.util.Vector;

public class FunctionDeclarations {
   private Vector list;

   public FunctionDeclarations() {
      list = new Vector();
   }

   public void add(FunctionDeclaration n) {
      list.add(0, n);
   }

   public FunctionDeclaration elementAt(int i)  { 
      return (FunctionDeclaration)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}