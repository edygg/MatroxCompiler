package edu.unitec.ast;

import java.util.Vector;

public class VariableDeclarators {
   private Vector list;

   public VariableDeclarators() {
      list = new Vector();
   }

   public void add(VariableDeclarator n) {
      list.add(0, n);
   }

   public VariableDeclarator elementAt(int i)  { 
      return (VariableDeclarator)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}