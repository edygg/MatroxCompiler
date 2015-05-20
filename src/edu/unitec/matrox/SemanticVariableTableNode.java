
package edu.unitec.matrox;

import edu.unitec.ast.Type;

/**
 *
 * @author Edilson Gonzalez
 */
public class SemanticVariableTableNode extends SemanticTableNode {
    
    private Type type;

    public SemanticVariableTableNode(Type type, String name, String scope) {
        super(name, scope);
        this.type = type;
    }
    
    public Type getType() {
        return type;
    }

}
