package edu.unitec.matrox;

/**
 *
 * @author Guillermo
 */

import java.util.*;
import java.io.*;

public class GenerateLexer {

    public static void main(String[] args) {
        Flex();     
    }

    public static void Flex() {
        String filePath = "./src/edu/unitec/matrox/MatroxLexer.flex";
        generate(filePath);
    }

    public static void generate(String path) {
        File f = new File(path);
        jflex.Main.generate(f);
    }
}
