package edu.unitec.ast;

import java.util.Vector;

public class VariableDeclarations {
   private Vector list;

   public VariableDeclarations() {
      list = new Vector();
   }

   public void addElement(VariableDeclaration n) {
      list.addElement(n);
   }

   public VariableDeclaration elementAt(int i)  { 
      return (VariableDeclaration)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}