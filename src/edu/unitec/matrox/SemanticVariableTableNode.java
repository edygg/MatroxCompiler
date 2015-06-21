
package edu.unitec.matrox;

import edu.unitec.ast.Type;

/**
 *
 * @author Edilson Gonzalez
 */
public class SemanticVariableTableNode extends SemanticTableNode {
    
    private Type type;
    private boolean parameter;
    private int direction;

    public SemanticVariableTableNode(Type type, String name, String scope, boolean parameter, int direction) {
        super(name, scope);
        this.type = type;
        this.parameter = parameter;
        this.direction = direction;
    }
    
    public boolean isParameter() {
        return parameter;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public int getDirection() {
        return this.direction;
    }
    
    public Type getType() {
        return type;
    }

}
