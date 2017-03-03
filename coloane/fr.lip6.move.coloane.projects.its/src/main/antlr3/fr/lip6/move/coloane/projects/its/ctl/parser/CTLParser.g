grammar CTLParser;

options {
  language = Java;
}



@lexer::header {

package main.antlr3.fr.lip6.move.coloane.projects.its.ctl.parser;

}

@parser::header {

package main.antlr3.fr.lip6.move.coloane.projects.its.ctl.parser;

import java.util.Collections;

import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.checks.*;
import fr.lip6.move.coloane.projects.its.ctl.*;
import fr.lip6.move.coloane.projects.its.antlrutil.*;
import fr.lip6.move.coloane.projects.its.checks.ui.controls.CTLText;
import fr.lip6.move.coloane.projects.its.variables.*;


}

@members {
    private ParsedCTLFormula errorReporter = null;
    public void setErrorReporter(ParsedCTLFormula errorReporter) {
        this.errorReporter = errorReporter;
    }
    public void emitErrorMessage(String msg, int charAt, int len, List<String> suggs) {
            errorReporter.reportError(msg, charAt, len, suggs);
    }
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
          super.displayRecognitionError(tokenNames, e);
          int len = 1;
          if (e.token != null && e.token.getText() != null) {
            len = e.token.getText().length();
          }
          List<String> suggs = new ArrayList<String>();
          if (e instanceof MismatchedTokenException) {
      			MismatchedTokenException e2 = (MismatchedTokenException) e;
      			// protect against NPE
      			if (e2.expecting >= 0 && e2.expecting < tokenNames.length ) {
	      			suggs.add(tokenNames [e2.expecting]);
	      		}
      }
          errorReporter.reportError(getErrorMessage(e, tokenNames),e.charPositionInLine, len, suggs);
        };
            private CheckList cl; 
    public void setCheckList (CheckList cl) {
      this.cl = cl;
    }
    
}

ctlformula returns [CTLFormula form] : (f=formula {form = f;} ';')* EOF ;

formula returns [CTLFormula form] : 
  f=uformula
  {
  form = f;
  }
  | 'DEADLOCK'
  {
  form = CTLFormula.DEAD;
  }
;

uformula returns [CTLFormula form] :
  ( 'A' '(' f=formula 'U' g=formula  ')' 
  {
  form = new AU (f,g);
  }  
  )
  |
  ( 'E' '(' f=formula 'U' g=formula  ')' 
  {
  form = new EU (f,g);
  }
  ) 
  |
  f=implyformula
  {
  form =f;
  }   
; 

implyformula returns [CTLFormula form] : 
  f=equivformula
  { form = f ;}
  ('->' g=equivformula 
    { form = new CTLImply (form,g); }
  )? 
;

equivformula returns [CTLFormula form] :
  f=xorformula 
  { form = f ;}
  ('<->' g=xorformula 
    { form = new CTLEquiv (form,g); }
  )?
;

xorformula returns [CTLFormula form] :
  f=orformula 
  { form = f ;}
  ('^' g=orformula 
    { form = new CTLXor (form,g); }
  )?
;

orformula returns [CTLFormula form] :
 f=andformula 
 { form = f ;}
 (('+'|'||') g=andformula 
     { form = new CTLOr (form,g); }
 )?
;

andformula returns [CTLFormula form] :
 f=timeformula 
 { form = f ;}
 ( ('*'|'&&') g=timeformula 
 { form = new CTLAnd (form,g); }
 )?
;

timeformula returns [CTLFormula form] :
  f=negformula
   { form = f ;}
  |
  ( 'AG' f=negformula  
   { form = new AG(f) ;}
  )
  |
  ( 'AF' f=negformula  
   { form = new AF(f) ;}  
  )
  |
  ( 'AX' f=negformula  
   { form = new AX(f) ;}  
  )
  |
  ( 'EG' f=negformula  
   { form = new EG(f) ;}  
  )
  |
  ( 'EF' f=negformula  
   { form = new EF(f) ;}  
  )
  |
  ( 'EX' f=negformula  
   { form = new EX(f) ;}  
  )
;

negformula returns [CTLFormula form] :
  f=atomicformula { form = f; }
  |
  ( '!' f=atomicformula  { form = new CTLNot(f); } )
;

atomicformula returns [CTLFormula form] :
  f=predicate { form =f ; }
  |
  ( '(' f=formula  {form=f;} ')' )
  ;
  
predicate returns [CTLFormula form] :
  'TRUE'
  { form = CTLFormula.TRUE; }
  |
  'FALSE'
  { form = CTLFormula.FALSE; }
  |
  var=VARIABLE comp=comparator val=NUMBER
  { 
                                           String name = var.getText();
                                          ITypeDeclaration type = cl.getType();
                                            if (type==null) {
                                                emitErrorMessage("Token " + name + " does not seem to designate a variable of your model.", var.getCharPositionInLine(), var.getText().length(), Collections.EMPTY_LIST);
                    						} else {                                           	
                    							IModelVariable  v = type.findQualifiedVariable(name);
                    							if (v == null) {
                    								emitErrorMessage("Token " + name + " does not seem to designate a variable of your model.", var.getCharPositionInLine(), var.getText().length(), Collections.EMPTY_LIST);
                    							} else if (! (v instanceof LeafModelVariable)) {
                    								emitErrorMessage("Token " + name + " does not designate an integer variable of your model. Qualify with \".\"", var.getCharPositionInLine(), var.getText().length(), Collections.EMPTY_LIST);                         
                    							} else {
                    								name = v.getId();
                    							}
                    						}
 				                       form = new CTLVariablePredicate(name, comp, val.getText() );   }
  |
  '@' var=VARIABLE
  { 
                      String formName = var.getText();
                    form = new CTLFormulaReference(formName); 

                    CTLFormulaDescription cfd = cl.findCtlFormula (formName);
                    if (cfd == null) {
                      emitErrorMessage("Token @" + formName + " does not designate an existing CTL formula.", var.getCharPositionInLine(), var.getText().length(), cl.getCtlFomulaNames());                                               
                    } else { 
                      ((CTLFormulaReference) form).setFormulaDescription (cfd.getCtlFormula().toString());
                    }
  
   }   
  ;


comparator returns [String val] : tok=('=' | '>=' | '<=' | '<' | '>')  {
	val = tok.getText();
};


fragment LETTER : 'a'..'z' | 'A'..'Z' | '_' | '.'
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

VARIABLE  : ( STRING | ( (LETTER | DIGIT)+ ) ) ( '[' DIGIT+ ']' )? 
  ;
  
  
// LABEL : ' ' ( options{greedy=false;}: .* ) ' ';

// LETTER (LETTER | DIGIT | ' ' | '-')* );
  
