package edu.unitec.ast;

import java.util.Vector;

public class Parameters {
   public Vector list;

   public Parameters() {
      list = new Vector();
   }

   public void add(Parameter n) {
      list.add(0, n);
   }

   public Parameter elementAt(int i)  { 
      return (Parameter)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}