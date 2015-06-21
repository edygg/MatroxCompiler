
package edu.unitec.intermediatelanguage;

import java.util.Vector;

/**
 *
 * @author Edilson Gonzalez
 */
public class OperationList {
    private Vector<Operation> list;
    
    public OperationList() {
        this.list = new Vector();
    }
    
    public void add(Operation n) {
        list.add(n);
    }
    
    public Operation elementAt(int i) {
        return list.get(i);
    }
    
    public int size() {
        return list.size();
    }
    
    public OperationList merge(OperationList other) {
        Vector<Operation> neoList = new Vector();
        neoList.addAll(this.list);
        neoList.addAll(other.list);
        OperationList neo = new OperationList();
        neo.list = neoList;
        return neo;
    }
}
