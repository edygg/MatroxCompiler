package edu.unitec.ast;

import java.util.Vector;

public class SwitchCaseExpList {
   private Vector list;

   public SwitchCaseExpList() {
      list = new Vector();
   }

   public void add(Exp n) {
      list.add(0, n);
   }

   public Exp elementAt(int i)  { 
      return (Exp)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}