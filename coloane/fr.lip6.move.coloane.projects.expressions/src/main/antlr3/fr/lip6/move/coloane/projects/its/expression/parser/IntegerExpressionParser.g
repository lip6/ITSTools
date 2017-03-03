grammar IntegerExpressionParser;

options {
  language = Java;
}

tokens {
  OPEN_PAREN = '(';
  CLOSE_PAREN = ')';
  PLUS = '+';
  MINUS = '-';
  MULT = '*';
  DIV = '/';
  SEMICOL = ';';
}

@lexer::header {

package fr.lip6.move.coloane.projects.its.expression.parser;
import fr.lip6.move.coloane.projects.its.expression.*;
import fr.lip6.move.coloane.projects.its.antlrutil.*;
}

@parser::header {

package fr.lip6.move.coloane.projects.its.expression.parser;

import fr.lip6.move.coloane.projects.its.expression.*;
import fr.lip6.move.coloane.projects.its.antlrutil.*;
}

@lexer::members {
    private IErrorReporter errorReporter = null;
    public void setErrorReporter(IErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }
    public void emitErrorMessage(String msg) {
        errorReporter.reportError(msg);
    }
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

prog returns [IntegerExpression expr] : exp=expression EOF { expr = exp;};

expression returns [IntegerExpression expr] : 
  exp1=mult_exp 
    { expr = exp1; }
  ( sign=('+' | '-') exp2=mult_exp 
  { 
    if ($sign.getText().equals("+")) {
      if (expr instanceof Add) {
        expr.getChildren().add(exp2);
      } else {
        IntegerExpression tmp = new Add();
        tmp.getChildren().add(expr);
        tmp.getChildren().add(exp2);
        expr = tmp;
      }
    } else {
      if (expr instanceof Minus) {
        expr.getChildren().add(exp2);
      } else {
        IntegerExpression tmp = new Minus();
        tmp.getChildren().add(expr);
        tmp.getChildren().add(exp2);
        expr = tmp;
      }    
    }
  })* ;

mult_exp returns [IntegerExpression expr] : 
  exp1=unary_exp 
  {
    expr = exp1;
  }
  ( sign=('*' | '/') exp2=unary_exp 
  {
    if ($sign.getText().equals("*")) {
      if (expr instanceof Mult) {
        expr.getChildren().add(exp2);
      } else {
        IntegerExpression tmp = new Mult();
        tmp.getChildren().add(expr);
        tmp.getChildren().add(exp2);
        expr = tmp;
      }
    } else {
      if (expr instanceof Div) {
        expr.getChildren().add(exp2);
      } else {
        IntegerExpression tmp = new Div();
        tmp.getChildren().add(expr);
        tmp.getChildren().add(exp2);
        expr = tmp;
      }    
    }    
  }
  )* ;

// power_exp : unary_exp ( ('**' | '//') power_exp )? ;

unary_exp returns [IntegerExpression expr]
  : '-' exp=unary_exp 
  {
    expr = new UnaryMinus(exp);
  }
  | exp2=factor_exp
  {
    expr = exp2;
  } 
  ;

factor_exp returns [IntegerExpression expr] : 
  num=NUMBER 
  {
    expr = new Constant(Integer.parseInt($num.getText()));
  }
  | 
  var=VARIABLE 
  {
    expr = new Variable($var.getText());
  }
  |
  inf=INFINITY
  {
    expr = new Infinity();
  }  
  | 
  exp=par_exp 
  {
    expr = exp;
  };

par_exp  returns [IntegerExpression expr] : '(' exp=expression ')' { expr = exp; } ;

/*
expression returns [IntegerExpression expr]: 
  (
  (term ( (PLUS|MINUS) term )*)
  |
  OPEN_PAREN term CLOSE_PAREN
  ) SEMICOL;
  
term : 
      (
      factor ( (MULT|DIV) factor )* 
      )
      | 
      (
        OPEN_PAREN factor CLOSE_PAREN
      )
      ;

factor :  constant
  | VARIABLE 
  |  (OPEN_PAREN factor CLOSE_PAREN)
  ;

constant returns [IntegerExpression expr]: val=INTEGER { expr = new Constant(Integer.parseInt($val.getText()); } ;
*/


fragment LETTER : 'a'..'z' | 'A'..'Z' | '_'
  ;
fragment DIGIT  : '0'..'9'
  ;
fragment STRING : '"'.*'"'
  ;
fragment DOLLAR : '$';
// ignore whitespace
WS  : ( ' ' | '\t' | '\n' | '\r') { $channel = HIDDEN; }
  ;
NUMBER : DIGIT (DIGIT)*
  ;
  
INFINITY : 'inf'
  ;

VARIABLE  : DOLLAR ( STRING | (LETTER (LETTER | DIGIT)*) )
  ;
  
// LABEL : ' ' ( options{greedy=false;}: .* ) ' ';

// LETTER (LETTER | DIGIT | ' ' | '-')* );
  
