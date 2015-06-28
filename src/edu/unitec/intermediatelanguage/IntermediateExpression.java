
package edu.unitec.intermediatelanguage;

import java.util.Vector;

/**
 *
 * @author Edilson Gonzalez
 */
public class IntermediateExpression extends IntermediateStatement {
    private Temp place;
    private Vector<Label> t;
    private Vector<Label> f;
    
    public IntermediateExpression() { 
        this.t = new Vector();
        this.f = new Vector();
    }

    public Temp getPlace() {
        return place;
    }

    public Vector<Label> getTrue() {
        return t;
    }

    public Vector<Label> getFalse() {
        return f;
    }

    public void setPlace(Temp place) {
        this.place = place;
    }

    public void setTrue(Vector<Label> t) {
        this.t = t;
    }

    public void setFalse(Vector<Label> f) {
        this.f = f;
    }
}
