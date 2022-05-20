grammar Tpn;


@header {
package fr.lip6.mist.io.tpn;
}


//Net description
spec : place* transition*;

//Places declaration
placeDecl :  place ( place )* ;

place : 'place' name=Name ('init' val=Int)? ';' ;

//Transitions declaration
transition: 'trans' name=TName 
	('in' prePlace*)?
	('out' postPlace*)? ';';

prePlace : pref=Name ;
postPlace : pref=Name ;

/****** Basics ********/

fragment DIGIT : '0'..'9'
  ;

Int: ('0'..'9')+;

//Ignore white spaces
WS  : (' ' | '\t' | '\r'| '\n' ) -> skip
  ;

//Identifier representation
// code for double quoted string from SO : https://stackoverflow.com/questions/49150088/how-to-write-antlr4-rule-for-string   
Name  :  '"' ( ~[\\"\n\r] | '\\' [\\"] )* '"'  
  ;

TName  :  Name ('~' Name)  
  ;
