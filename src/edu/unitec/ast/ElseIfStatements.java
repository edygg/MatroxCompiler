package edu.unitec.ast;

import java.util.Vector;

public class ElseIfStatements {
   private Vector list;

   public ElseIfStatements() {
      list = new Vector();
   }

   public void addElement(ElseIfStatement n) {
      list.addElement(n);
   }

   public ElseIfStatement elementAt(int i)  { 
      return (ElseIfStatement)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}