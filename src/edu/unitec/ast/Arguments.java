package edu.unitec.ast;

import java.util.Vector;

public class Arguments {
   private Vector list;

   public Arguments() {
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