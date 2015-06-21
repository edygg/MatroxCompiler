
package edu.unitec.visitor;

public class Scope {

    private static int count = 0;
    
    public static String genNewScope() {
        return String.format("s%d", count++);
    }
}
