
package edu.unitec.matrox;

import edu.unitec.ast.Type;
import java.util.Vector;

/**
 *
 * @author Edilson Gonzalez
 */
public class SemanticFunctionTableNode extends SemanticTableNode {
    
    private Type returnType;
    private Vector<Type> params;

    public SemanticFunctionTableNode(Type returnType, Vector<Type> params, String name, String scope) {
        super(name, scope);
        this.returnType = returnType;
        this.params = params;
    }
    
    public Vector<Type> getParams() {
        return params;
    }

    public Type getReturnType() {
        return returnType;
    }

}
