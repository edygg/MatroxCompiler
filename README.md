![Logo](http://res.cloudinary.com/dodpsiyv0/image/upload/c_scale,w_250/v1424639316/matrox-hd_tnwkun.png)

# Documentación y tutoriales
Puede encontrar documentación y pequeños tutoriales del lenguaje en nuestro [wiki en GitHub](https://github.com/efgm1024/MatroxCompiler/wiki)

# Acerca de JFlex y CUP
Matrox es desarrollado en Java con la ayuda de [JFlex](http://jflex.de/), generador de analizadores léxicos y [Cup](http://www2.cs.tum.edu/projects/cup/), generador de analizadores sintácticos. Ambas librerias se incluyen en la carpeta `libs/`.

Los archivos `MatroxLexer.flex` y `MatroxParser.cup` ubicados en la carpeta `src/edu/unitec/matrox/` son los archivos fuente para JFlex y Cup.

## Compilar el archivo MatroxLexer.flex

Para compilar el archivo MatroxLexer.flex realice los siguientes pasos:
  1. Vaya a la carpeta `libs/`
  2. Abra el archivo `jflex-1.6.0.jar`
  3. Click en el botón `Browse` en `Lexical specification`
  4. Ubique el archivo `src/edu/unitec/matrox/MatroxLexer.flex`
  5. Click en el botón `Generate`

Verá la creación de la clase `Lexer.java` en el directorio.

## Compilar el archivo MatroxParser.cup

Para compilar el archivo MatroxParser.cup realice los siguientes pasos:
  1. Abra una terminal
  2. Muevase hasta la carpeta `src/edu/unitec/matrox/`
  3. Escriba el comando `java -jar ../../../../libs/java-cup-11b.jar -parser Parser MatroxParser.cup`
  4. Se generan dos clases `Parser.java` y `sym.java`, donde `Parser.java` tiene toda la lógica del analizador sintáctico y `sym.java` contiene los terminales dentro de la gramática definida en el archivo `MatroxParser.cup`.

# Acerca de Matrox
Proyecto de la clase de Compiladores en UNITEC Tegucigalpa, desarrollado por [Guillermo R. Mazzoni](https://github.com/Mazzonig) y [Edilson F. Gonzalez](https://github.com/efgm1024)
