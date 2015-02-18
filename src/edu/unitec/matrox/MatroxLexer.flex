package edu.unitec.matrox;

import java_cup.runtime.*;

%%

%unicode
%class Lexer
%cup
%line
%column

%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

%eofval{ 
    return symbol(sym.EOF);
%eofval}


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
    "number"                { return symbol(sym.DOUBLE);    }
    "string"                { return symbol(sym.STRING);    }

    "if" 	            { return symbol(sym.IF);        }
    "else"                  { return symbol(sym.ELSE);      }
    "while"                 { return symbol(sym.WHILE);     }
    "for"                   { return symbol(sym.FOR);       }
    "switch"                { return symbol(sym.SWITCH);    }
    "option"                { return symbol(sym.CASE);      }
    "stop"                  { return symbol(sym.BREAK);     }
    "end"                   { return symbol(sym.END);       }

    "function"              { return symbol(sym.FUNCTION);  }
    "giveback"              { return symbol(sym.RETURN);    }
    
    "print"                 { return symbol(sym.WRITE);     }
    "getvalue"              { return symbol(sym.READ);      }
    
    ","                     { return symbol(sym.COMMA);     }

    "+"                     { return symbol(sym.ADD);       }
    "-"                     { return symbol(sym.MIN);       }
    "*"                     { return symbol(sym.MUL);       }
    "/"                     { return symbol(sym.DIV);       }
    
    "("                     { return symbol(sym.LPAR);      }
    ")"                     { return symbol(sym.RPAR);      }
    "["                     { return symbol(sym.LBRACK);    }
    "]"                     { return symbol(sym.RBRACK);     }

    ">="                    { return symbol(sym.GREATEREQ); }
    "<="                    { return symbol(sym.LESSEQ);    }
    ">"                     { return symbol(sym.GREATER);   }
    "<"                     { return symbol(sym.LESS);      }
    "<>"                    { return symbol(sym.NEQ);       }
    "="                     { return symbol(sym.EQU);       }

    "not"                   { return symbol(sym.NOT);       }
    "or"                    { return symbol(sym.OR);        } 
    "and"                   { return symbol(sym.AND);       }

    "->"                    { return symbol(sym.ASSIGN);    }
   
 
    {Variable}              { return symbol(sym.IDENTIFIER, yytext());                                   }
    {Integer}               { return symbol(sym.INTNUMBER, new Integer(Integer.parseInt(yytext())));     }
    {Decimal}               { return symbol(sym.DOUBLENUMBER, new Double(Double.parseDouble(yytext()))); }
    {Comment}               {  /* ignore */                                                              } 
    {WhiteSpace}            {  /* ignore */                                                              }

}

