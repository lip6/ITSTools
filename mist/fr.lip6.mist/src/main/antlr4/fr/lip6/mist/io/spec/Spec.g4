grammar Spec;

//Net description
spec : placeDecl 'rules' transDecl* markDecl targetDecl invariantDecl?;

//Places declaration
placeDecl : 'vars' place ( place )* ;

place : name=Name ;

//Markings declaration
markDecl : 'init' (mark|mark2) (',' (mark|mark2))*  ;

mark : pref=Name '=' val=Int;
mark2 : pref=Name '>=' val=Int;

//Transitions declaration
transDecl : 
  guard '->' 
  ( (prevalue|postvalue) (',' (prevalue|postvalue))*)? ';'
  ;

guard :
	precond (',' precond)* ;
	
precond : pref=Name '>=' val=Int ;

//Place value
prevalue : prefl=Name '=' prefr=Name '-' val=Int ;

postvalue : prefl=Name '=' prefr=Name '+' val=Int ;

targetDecl : 'target' (geqVar | eqVar) (',' (geqVar | eqVar))*;

geqVar : pref=Name '>=' val=Int ;

eqVar : pref=Name '=' val=Int ;

/** Task parsing */

invariantDecl : 'invariants' invariant *;

invariant : invVar (',' invVar)* ;
invVar : pref=Name '=' val=Int ;

/****** Basics ********/
//Letters representation
fragment LETTER : 'a'..'z' | 'A'..'Z' | '_' | '\''
  ;
//Ignore comments
COMMENT : '#' .*? '\n'  -> skip
  ; 

fragment DIGIT : '0'..'9'
  ;

Int: ('0'..'9')+;

//Ignore white spaces
WS  : (' ' | '\t' | '\r'| '\n' ) -> skip
  ;

//Identifier representation  
Name  : (LETTER | DIGIT | '_' )+ '\''?;

