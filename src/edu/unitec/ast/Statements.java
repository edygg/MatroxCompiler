package edu.unitec.ast;

import java.util.Vector;

public class Statements {
   private Vector list;

   public Statements() {
      list = new Vector();
   }

   public void addElement(Statement n) {
      list.addElement(n);
   }

   public Statement elementAt(int i)  { 
      return (Statement)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}