package edu.unitec.ast;

import java.util.Vector;

public class VariableDeclarations {
   private Vector list;

   public VariableDeclarations() {
      list = new Vector();
   }

   public void add(VariableDeclaration n) {
      list.add(0, n);
   }

   public VariableDeclaration elementAt(int i)  { 
      return (VariableDeclaration)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}