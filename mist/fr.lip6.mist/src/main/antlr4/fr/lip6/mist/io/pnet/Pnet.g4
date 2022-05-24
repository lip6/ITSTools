grammar Pnet;

//Net description
net : 
	'petri' 'net' name=STRING '{'
	placeDecl 
	transDecl 
	arcsDecl 
	initDecl 
	'}'
	property*
;

//Places declaration
placeDecl : 'places' decl=placeSet ;

placeSet : '{' places+=place* '}' ;

place : name=Name ;

//Transitions declaration
transDecl : 'transitions' '{' transitions+=transition* '}' 
  ;

transition : name=Name ;

// arcs
arcsDecl : 
	'arcs' '{' 
	arc|singleArc*
	'}'
;

arc: 
	(pre=placeSet '->')? tref=Name ('->' post=placeSet)? 
;

singleArc:
	pre=Name '->' tref=Name '->' post=Name
;

//Markings declaration
initDecl : 'initial' init=placeSet ;

property : type=('liveness'|'safety') 'property' name=STRING? '{' pred=boolPred '}' ;

/** Task parsing */

boolPred : 
	op='!' left=boolPred | 
	left=boolPred op='&&' right=boolPred  |
	left=boolPred op='||' right=boolPred  |	
	sub=comparison |
	tt=('true'|'false') |
	'(' nested=boolPred ')' 	
	;
	

comparison : lhs=expr op=('<'|'<='|'='|'!='|'>='|'>') rhs=expr;

expr :
	l=expr op='+' r=expr |
	l=expr op='-' r=expr | 
	constant | 
	placeref |	
	'(' nested2=expr  ')' |
	;
	
placeref : name=Name;
 
constant : val=Int;

/****** Basics ********/
//Letters representation
fragment LETTER : 'a'..'z' | 'A'..'Z' | '_' | '\''
  ;
//Ignore comments
COMMENT : '/*'.*?'*/'  -> skip
  ; 

SL_COMMENT : '//'.*?'\n'  -> skip
  ; 


fragment DIGIT : '0'..'9'
  ;

Int: ('0'..'9')+;

//Ignore white spaces
WS  : (' ' | '\t' | '\n' | '\r' ) -> skip
  ;


//Identifier representation  
Name  : (LETTER | DIGIT | '_' )+;

//Identifier representation
// code for double quoted string from SO : https://stackoverflow.com/questions/49150088/how-to-write-antlr4-rule-for-string   
STRING  :  '"' ( ~[\\"\n\r] | '\\' [\\"] )* '"'  
  ;
