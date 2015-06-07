package edu.unitec.ast;

import java.util.Vector;

public class Statements {
   private Vector list;

   public Statements() {
      list = new Vector();
   }

   public void add(Statement n) {
      list.add(0, n);
   }

   public Statement elementAt(int i)  { 
      return (Statement)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}