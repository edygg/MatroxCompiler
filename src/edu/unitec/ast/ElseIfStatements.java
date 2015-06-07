package edu.unitec.ast;

import java.util.Vector;

public class ElseIfStatements {
   private Vector list;

   public ElseIfStatements() {
      list = new Vector();
   }

   public void add(ElseIfStatement n) {
      list.add(0, n);
   }

   public ElseIfStatement elementAt(int i)  { 
      return (ElseIfStatement)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}