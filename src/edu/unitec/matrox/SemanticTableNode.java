
package edu.unitec.matrox;

import java.util.Objects;

/**
 *
 * @author Edilson Gonzalez
 */
public abstract class SemanticTableNode {
    protected String name;
    protected String scope;

    public SemanticTableNode(String name, String scope) {
        this.name = name;
        this.scope = scope;
    }
    
    public String getScope() {
        return scope;
    }
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.scope);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SemanticTableNode other = (SemanticTableNode) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.scope, other.scope)) {
            return false;
        }
        return true;
    }
    
}
