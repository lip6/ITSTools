grammar JSONOrderParser;

options {
  language = Java;
}


@lexer::header {

package main.antlr3.fr.lip6.move.coloane.projects.its.order.parser;

}

@parser::header {

package main.antlr3.fr.lip6.move.coloane.projects.its.order.parser;

import fr.lip6.move.coloane.projects.its.order.*;
import fr.lip6.move.coloane.projects.its.antlrutil.*;
}

@members {
    private IErrorReporter errorReporter = null;
    public void setErrorReporter(IErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }
    public void emitErrorMessage(String msg) {
        errorReporter.reportError(msg);
    }
}

prog returns [Ordering expr] : exp=ordering EOF { expr = exp;};

ordering returns [Ordering res] :
  ( '[' 
  { Group g = new Group("g"); }
  elt1=ordering 
  { g.addChild(elt1); }
  (',' eltn=ordering
  {
    g.addChild(eltn);
  }
  )* ']' 
  {
    res = g;
  }
  ) | 
  var=ident
  {
    res = new Variable(var);
  } 
  ;


ident returns [String name] : txt=VARIABLE 
{
      String n = txt.getText();
      name = n.replace("\"", "");
};


fragment LETTER : 'a'..'z' | 'A'..'Z' | '_'
  ;
fragment DIGIT  : '0'..'9'
  ;
fragment STRING : '"'.*'"'
  ;

// ignore whitespace
WS  : ( ' ' | '\t' | '\n' | '\r') { $channel = HIDDEN; }
  ;
NUMBER : DIGIT (DIGIT)*
  ;
  
INFINITY : 'inf'
  ;

VARIABLE  : ( STRING | (LETTER (LETTER | DIGIT)*) )
  ;
  
// LABEL : ' ' ( options{greedy=false;}: .* ) ' ';

// LETTER (LETTER | DIGIT | ' ' | '-')* );
  
