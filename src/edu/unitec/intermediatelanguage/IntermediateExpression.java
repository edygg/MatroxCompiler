
package edu.unitec.intermediatelanguage;

/**
 *
 * @author Edilson Gonzalez
 */
public class IntermediateExpression extends IntermediateForm {
    private Temp place;
    private Label t;
    private Label f;
    
    public IntermediateExpression() { /* Nothing */ }

    public Temp getPlace() {
        return place;
    }

    public Label getTrue() {
        return t;
    }

    public Label getFalse() {
        return f;
    }

    public void setPlace(Temp place) {
        this.place = place;
    }

    public void setTrue(Label t) {
        this.t = t;
    }

    public void setFalse(Label f) {
        this.f = f;
    }
}
