
package edu.unitec.matrox;

import edu.unitec.ast.Type;
import java.util.ArrayList;

/**
 *
 * @author Edilson Gonzalez
 */
public class SemanticFunctionTableNode extends SemanticTableNode {
    
    private Type returnType;
    private ArrayList<Type> params;

    public SemanticFunctionTableNode(Type returnType, ArrayList<Type> params, String name, String scope) {
        super(name, scope);
        this.returnType = returnType;
        this.params = params;
    }
    
    public ArrayList<Type> getParams() {
        return params;
    }

    public Type getReturnType() {
        return returnType;
    }

}
