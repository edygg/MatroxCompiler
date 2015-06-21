
package edu.unitec.intermediatelanguage;

/**
 *
 * @author Edilson Gonzalez
 */
public class IntermediateStatement extends IntermediateForm {
    private Label start;
    private Label next;
   
    public IntermediateStatement() { /* Nothing */ }

    public Label getStart() {
        return start;
    }

    public Label getNext() {
        return next;
    }

    public void setStart(Label start) {
        this.start = start;
    }

    public void setNext(Label next) {
        this.next = next;
    }
    
    
}
