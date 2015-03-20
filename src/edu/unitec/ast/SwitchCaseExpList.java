package edu.unitec.ast;

import java.util.Vector;

public class SwitchCaseExpList {
   private Vector list;

   public SwitchCaseExpList() {
      list = new Vector();
   }

   public void addElement(Exp n) {
      list.addElement(n);
   }

   public Exp elementAt(int i)  { 
      return (Exp)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}