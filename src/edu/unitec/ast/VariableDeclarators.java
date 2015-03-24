package edu.unitec.ast;

import java.util.Vector;

public class VariableDeclarators {
   private Vector list;

   public VariableDeclarators() {
      list = new Vector();
   }

   public void addElement(VariableDeclarator n) {
      list.addElement(n);
   }

   public VariableDeclarator elementAt(int i)  { 
      return (VariableDeclarator)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}