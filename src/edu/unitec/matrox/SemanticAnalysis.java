
package edu.unitec.matrox;

import java.util.Hashtable;
import java.util.Objects;

/**
 *
 * @author Edilson Gonzalez
 */
public class SemanticAnalysis {
    
    private Hashtable<String, SemanticTableNode> semanticTable;

    public SemanticAnalysis() {
        this.semanticTable = new Hashtable<String, SemanticTableNode>();
    }
    
    public SemanticTableNode findID(String id) {
        return this.semanticTable.get(id);
    }
    
    public boolean addID(String id, SemanticTableNode value) {
        if (findID(id) != null)
            return false;
        this.semanticTable.put(id, value);
        return true;
    }
    
    public Hashtable<String, SemanticTableNode> getSemanticTable() {
        return semanticTable;
    }

}
