package edu.unitec.ast;

import java.util.Vector;

public class Parameters {
   private Vector list;

   public Parameters() {
      list = new Vector();
   }

   public void addElement(Parameter n) {
      list.addElement(n);
   }

   public Parameter elementAt(int i)  { 
      return (Parameter)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}