
package edu.unitec.matrox;

import java.util.ArrayList;
import java.util.List;
import edu.unitec.ast.*;

/**
 *
 * @author Edilson Gonzalez
 */
public class SemanticAnalysis {
    public static int SIZE_OF_INTEGER = 4; //BYTES
    public static int SIZE_OF_NUMBER = 8; //BYTES
    public static int SIZE_OF_BOOLEAN = 1; //BYTES
    public static int SIZE_OF_CHAR = 1; //BYTES
    public static int SIZE_OF_STRING = 4; //BYTES
    
    public static int sizeOf(Type type) {
        if (type instanceof IntegerType) {
            return SIZE_OF_INTEGER;
        } else if (type instanceof DoubleType) {
            return SIZE_OF_NUMBER;
        } else if (type instanceof BooleanType) {
            return SIZE_OF_BOOLEAN;
        } else if (type instanceof CharType) {
            return SIZE_OF_CHAR;
        } else if (type instanceof StringType) {
            return SIZE_OF_STRING;
        }else {
            return -1;
        }
    }
    
    private List<SemanticTableNode> symbols;

    public SemanticAnalysis() {
        this.symbols = new ArrayList();
    }
    
    public SemanticTableNode findID(String id, String currentScope) {
        for (int i = 0; i < symbols.size(); i++) {
            SemanticTableNode currentSymbolInfo = symbols.get(i);
            if (currentSymbolInfo.getName().equals(id) && currentScope.startsWith(currentSymbolInfo.getScope())) {
                return currentSymbolInfo;
            }
        }
        
        return null;
    }
    
    public boolean addID(String id, String scope, SemanticTableNode value) {
        if (findID(id, scope) != null)
            return false;
        
        this.symbols.add(value);
        return true;
    }

}
