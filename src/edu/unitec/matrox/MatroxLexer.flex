import java_cup.runtime.*;

%%

%unicode
%class Lexer
%cup
%line
%column
%standalone

%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}


Variable = _[A-Za-z0-9]+ | "$"[A-Za-z0-9]+
Integer = [0-9]+
Decimal = [0-9]+ | ([0-9])+"."([0-9])*

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f] 

Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment   = "/#" [^#] ~"#/" | "/#" "#"+ "/"
EndOfLineComment     = "//" {InputCharacter}# {LineTerminator}?
DocumentationComment = "/##" {CommentContent} "#"+ "/"
CommentContent       = ( [^#] | \#+ [^/#] )#

%%


<YYINITIAL> {
	
    "integer"               { return new symbol(sym.INTEGER);}
    "character"             { return new symbol(sym.CHAR);}
    "boolean"               { return new symbol(sym.BOOLEAN);}
    "giveback"              { return new symbol(sym.RETURN);}
    "if" 	            { return new symbol(sym.IF); }
    "else"                  { return new symbol(sym.ELSE); }
    "while"                 { return new symbol(sym.WHILE); }
    "do"                    { return new symbol(sym.DO); }
    "print"                 { return new symbol(sym.WRITE); }
    "getvalue"              { return new symbol(sym.READ); } // Or Scan
    "stop"                  { return new symbol(sym.BREAK); }
    "switch"                { return new symbol(sym.SWITCH); }
    "option"                { return new symbol(sym.CASE); }
    "end"                   { return new symbol(sym.END); }

    ","                     { return new symbol(sym.COMMA); }

    "+"                     { return new symbol(sym.ADD); }
    "-"                     { return new symbol(sym.MIN); }
    "*"                     { return new symbol(sym.MUL); }
    "/"                     { return new symbol(sym.DIV); }
    "("                     { return new symbol(sym.LPAR); }
    ")"                     { return new symbol(sym.RPAR); }
    "["                     { return new symbol(sym.LRBACK); }
    "]"                     { return new symbol(sym.RBACK); }
    "{"                     { return new symbol(sym.LBRACE); }
    "}"                     { return new symbol(sym.RBRACE); }


    ">"                     { return new symbol(sym.GREATER); }
    "<"                     { return new symbol(sym.LESS); }
    "<>"                    { return new symbol(sym.NEQ); }
    "=="                    { return new symbol(sym.EQU); }

    "!"                     { return new symbol(sym.NOT); }
    "="                     { return new symbol(sym.ASSIGN); }
    "OR"                    { return new symbol(sym.OR); } 
    "AND"                   { return new symbol(sym.AND); }
   
 
   {Variable}               { return new symbol(sym.IDENTIFIER, yytext());    }
   {Integer}                { return new symbol(sym.NUMBER, new Integer(Integer.parseInt(yytext()))); }
   {Comment}               { yyline += countLines(yytext()); } 
   {WhiteSpace} {}

}

