package edu.unitec.ast;

import java.util.Vector;

public class SwitchCaseStatements {
   private Vector list;

   public SwitchCaseStatements() {
      list = new Vector();
   }

   public void addElement(SwitchCaseStatement n) {
      list.addElement(n);
   }

   public SwitchCaseStatement elementAt(int i)  { 
      return (SwitchCaseStatement)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}