
package edu.unitec.intermediatelanguage;

/**
 *
 * @author Edilson Gonzalez
 */
public class Label {
    private static int count = 0;
    
    private String name;

    public Label() {
        this("L" + count++);
    }
    
    public Label(String name) {
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
