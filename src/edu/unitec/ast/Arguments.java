package edu.unitec.ast;

import java.util.Vector;

public class Arguments {
   private Vector list;

   public Arguments() {
      list = new Vector();
   }

   public void add(Argument n) {
      list.add(0, n);
   }

   public Argument elementAt(int i)  { 
      return (Argument)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}