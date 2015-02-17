package edu.unitec.matrox;

import java_cup.runtime.*;

%%

%unicode
%class Lexer
%cup
%line
%column

%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}


Variable = [_$A-Za-z][_a-zA-z0-9]*
Integer = [0-9]+
Decimal = [0-9]*[\.][0-9]+

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [\s\t\f] 

Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment   = "/#" [^#] ~"#/" | "/#" "#"+ "/"
EndOfLineComment     = "//" {InputCharacter}# {LineTerminator}?
DocumentationComment = "/##" {CommentContent} "#"+ "/"
CommentContent       = ( [^#] | \#+ [^/#] )#

%%


<YYINITIAL> {
	
    "integer"               { return symbol(sym.INTEGER);   }
    "character"             { return symbol(sym.CHAR);      }
    "boolean"               { return symbol(sym.BOOLEAN);   }
    "giveback"              { return symbol(sym.RETURN);    }
    "if" 	            { return symbol(sym.IF);        }
    "else"                  { return symbol(sym.ELSE);      }
    "while"                 { return symbol(sym.WHILE);     }
    "do"                    { return symbol(sym.DO);        }
    "print"                 { return symbol(sym.WRITE);     }
    "getvalue"              { return symbol(sym.READ);      }
    "stop"                  { return symbol(sym.BREAK);     }
    "switch"                { return symbol(sym.SWITCH);    }
    "option"                { return symbol(sym.CASE);      }
    "end"                   { return symbol(sym.END);       }

    ","                     { return symbol(sym.COMMA);     }

    "+"                     { return symbol(sym.ADD);       }
    "-"                     { return symbol(sym.MIN);       }
    "*"                     { return symbol(sym.MUL);       }
    "/"                     { return symbol(sym.DIV);       }
    "("                     { return symbol(sym.LPAR);      }
    ")"                     { return symbol(sym.RPAR);      }
    "["                     { return symbol(sym.LRBACK);    }
    "]"                     { return symbol(sym.RBACK);     }
    "{"                     { return symbol(sym.LBRACE);    }
    "}"                     { return symbol(sym.RBRACE);    }


    ">"                     { return symbol(sym.GREATER);   }
    "<"                     { return symbol(sym.LESS);      }
    "<>"                    { return symbol(sym.NEQ);       }
    "=="                    { return symbol(sym.EQU);       }

    "!"                     { return symbol(sym.NOT);       }
    "="                     { return symbol(sym.ASSIGN);    }
    "or"                    { return symbol(sym.OR);        } 
    "and"                   { return symbol(sym.AND);       }
   
 
   {Variable}               { return symbol(sym.IDENTIFIER, yytext());                              }
   {Integer}                { return symbol(sym.NUMBER, new Integer(Integer.parseInt(yytext())));   }
   {Comment}                { yyline += countLines(yytext());                                           } 
   {WhiteSpace}             {  /* ignore */                                                             }

}

