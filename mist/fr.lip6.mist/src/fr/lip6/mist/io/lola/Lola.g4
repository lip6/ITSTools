grammar Lola;

//Net description
net : placeDecl markDecl transDecl* ;

//Places declaration
placeDecl : 'PLACE' place (',' place )* ';' ;

place : name=Name ;

//Markings declaration
markDecl : 'MARKING' mark (',' mark)*  ';';

mark : pref=Name (':' val=Int)?;

//Transitions declaration
transDecl : 'TRANSITION'  transition 
  'CONSUME' (prevalue (','prevalue)*)? ';'
  'PRODUCE' (postvalue (','postvalue)*)? ';'
  ;

transition : name=Name ;

//Place value
prevalue : pref=Name (':' val=Int)? ;

postvalue : pref=Name (':' val=Int)? ;


/** Task parsing */

ctl : 'EF' pred=boolPred;

boolPred : 
	sub=comparison |
	'(' nested=boolPred ')' |
	first=boolPred op='AND' children+=boolPred ('AND' children+=boolPred)* |
	first=boolPred op='OR' children+=boolPred ('OR' children+=boolPred)* |
	op='NOT' children+=boolPred 
	;
	

comparison : lhs=expr op=('<'|'<='|'='|'!='|'>='|'>') rhs=expr;

expr :
	constant | 
	placeref |	
	'(' nested2=expr  ')' |
	l=expr op='+' r=expr |
	l=expr op='-' r=expr 
	;
	
placeref : name=Name;
 
constant : val=Int;

/****** Basics ********/
//Letters representation
fragment LETTER : 'a'..'z' | 'A'..'Z' | '_' | '\''
  ;
//Ignore comments
COMMENT : '{'.*?'}'  -> skip
  ; 

fragment DIGIT : '0'..'9'
  ;

Int: ('0'..'9')+;

//Ignore white spaces
WS  : (' ' | '\t' | '\n' | '\r'| ',' ) -> skip
  ;

//LoLA identifiers forbidden characters 
//EXCLUDE : ('(' | ')' | '{' | '}' | ',' | ';' | ':' | ' ' | '\t' | '\n' | '\r' );  
//OPERATOR : ('<->' | '<>' | '->' | '=' | '[]' | '.' | '+' | '-' | '*' | '/' | '|' | '>' | '<' | '#' | '>=' | '<=' ) -> skip;
//KEYWORD : ('RECORD' | 'END' | 'SORT' | 'FUNCTION' | 'DO' | 'ARRAY' | 'ENUMERATE' | 'CONSTANT' | 'BOOLEAN' | 'OF' | 'BEGIN' | 'WHILE' | 'IF' | 'THEN' | 'ELSE' 
//         | 'SWITCH' | 'CASE' | 'NEXTSTEP' | 'REPEAT' | 'FOR' | 'TO' | 'ALL' | 'EXIT' | 'EXISTS' | 'RETURN' | 'TRUE' | 'FALSE' | 'MOD' | 'VAR' | 'GUARD' | 'STATE' 
//         | 'PATH' | 'GENERATOR' | 'ANALYSE' | 'PLACE' | 'TRANSITION' | 'MARKING' | 'CONSUME' | 'PRODUCE' | 'FORMULA' | 'EXPATH' | 'ALLPATH' | 'ALWAYS' | 'UNTIL' 
//         | 'EVENTUALLY' | 'AND' | 'OR' | 'NOT') -> skip;

//Identifier representation  
Name  : (LETTER | DIGIT | '_' | '-' | '\'' )+;

