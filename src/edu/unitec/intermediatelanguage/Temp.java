
package edu.unitec.intermediatelanguage;

/**
 *
 * @author Edilson Gonzalez
 */
public class Temp {
    private static int count = 0;
    private int num;
    
    public Temp() {
        this.num = count++;
    }

    @Override
    public String toString() {
        return "t" + num;
    }
    
}
