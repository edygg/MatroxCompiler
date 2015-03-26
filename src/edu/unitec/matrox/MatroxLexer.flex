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


Variable = [_$a-zA-Z][a-zA-Z0-9_]*
Integer = [0-9]+
Decimal = [0-9]*[\.][0-9]+

LineTerminator      = \r|\n|\r\n
WhiteSpace          = {LineTerminator} | [\s\t\f]
AllCharset          = [\w\W]*
CommentDelimiter    = #
StringDelimiter     = [\"]
StringContent       = ([^\"\\;] | (\\n) | (\\t) | (\\\\) | (\\r) | (\\\") | (\\;))*
CharDelimiter       = [\\]
CharContent         = ([^\\])|(\\n)|(\\t)|(\\\\)|(\\r)

%state STRINGFOUND
%state CHARFOUND
%state COMMENTFOUND

%%


<YYINITIAL> {
	
    "integer"               { return symbol(sym.INTEGER);   }
    "character"             { return symbol(sym.CHAR);      }
    "boolean"               { return symbol(sym.BOOLEAN);   }
    "number"                { return symbol(sym.DOUBLE);    }
    "string"                { return symbol(sym.STRING);    }

    "true"                  { return symbol(sym.TRUE);      }
    "false"                 { return symbol(sym.FALSE);     }

    "if" 	            { return symbol(sym.IF);        }
    "else"                  { return symbol(sym.ELSE);      }
    "elseif"                { return symbol(sym.ELSEIF);    }
    "while"                 { return symbol(sym.WHILE);     }
    "for"                   { return symbol(sym.FOR);       }
    "switch"                { return symbol(sym.SWITCH);    }
    "option"                { return symbol(sym.CASE);      }
    "by_default"            { return symbol(sym.DEFAULT);   }
    "end"                   { return symbol(sym.END);       }

    "main"                  { return symbol(sym.MAIN);      }
    "function"              { return symbol(sym.FUNCTION);  }
    "give_back"             { return symbol(sym.RETURN);    }
    "void"                  { return symbol(sym.VOID);      }
    
    "print"                 { return symbol(sym.WRITE);     }
    "get_value"             { return symbol(sym.READ);      }
    
    ","                     { return symbol(sym.COMMA);     }
    ";"                     { return symbol(sym.SEMICOLON); }

    "+"                     { return symbol(sym.ADD);       }
    "-"                     { return symbol(sym.MIN);       }
    "*"                     { return symbol(sym.MUL);       }
    "/"                     { return symbol(sym.DIV);       }
    
    "("                     { return symbol(sym.LPAR);      }
    ")"                     { return symbol(sym.RPAR);      }
    "\["                    { return symbol(sym.LBRACK);    }
    "\]"                    { return symbol(sym.RBRACK);     }

    ">="                    { return symbol(sym.GREATEREQ); }
    "<="                    { return symbol(sym.LESSEQ);    }
    ">"                     { return symbol(sym.GREATER);   }
    "<"                     { return symbol(sym.LESS);      }
    "<>"                    { return symbol(sym.NEQ);       }
    "="                     { return symbol(sym.EQU);       }

    "not"                   { return symbol(sym.NOT);       }
    "or"                    { return symbol(sym.OR);        } 
    "and"                   { return symbol(sym.AND);       }

    "<-"                    { return symbol(sym.ASSIGN);    }
   
 
    {Variable}              { return symbol(sym.IDENTIFIER, yytext());                                   }
    {Integer}               { return symbol(sym.INTNUMBER, new Integer(Integer.parseInt(yytext())));     }
    {Decimal}               { return symbol(sym.DOUBLENUMBER, new Double(Double.parseDouble(yytext()))); }
    {StringDelimiter}       { yybegin(STRINGFOUND);                                                      }
    {CharDelimiter}         { yybegin(CHARFOUND);                                                        }
    {CommentDelimiter}      { yybegin(COMMENTFOUND);                                                     }
    {WhiteSpace}            { /* ignore */                                                               }
    .                       { System.err.println("Illegal character <" + yytext() + "> at line: " + (yyline + 1) + " column: " + (yycolumn + 1)); }

}

<STRINGFOUND> {
     ";"                    { yybegin(YYINITIAL);                         }
    {StringDelimiter}       { yybegin(YYINITIAL);                         }
    {StringContent}         { return symbol(sym.STRINGCONTENT, yytext()); }
    .                       { System.err.println("Illegal character <" + yytext() + "> at line: " + (yyline + 1) + " column: " + (yycolumn + 1)); }
}

<CHARFOUND> {
    {CharDelimiter}         { yybegin(YYINITIAL);                         }
    {CharContent}           { return symbol(sym.CHARCONTENT, new Character(yytext().charAt(0))); }
}

<COMMENTFOUND> {
    {CommentDelimiter}      { yybegin(YYINITIAL);                           }             
    {WhiteSpace}            { /* Ignore */                                  }
    .                       { /* Ignore */                                  }
}

