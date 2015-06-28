
package edu.unitec.intermediatelanguage;

/**
 *
 * @author Edilson Gonzalez
 */
public class Temp {
    private static int count = 0;
    private String literal;
    
    public Temp() {
        this.literal = "t" + count++;
    }
    
    public Temp(String literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return literal;
    }
    
}
