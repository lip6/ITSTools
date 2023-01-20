grammar Selt;

/** Formula parsing */

ctl : op=('[]'|'<>') pred=boolPred
	;

boolPred : 
	op='-' left=boolPred | 
	left=boolPred op='/\\' right=boolPred  |
	left=boolPred op='\\/' right=boolPred  |	
	sub=comparison |
	tt=('T'|'F') |
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
COMMENT : ':'+'\n'  -> skip
  ; 

fragment DIGIT : '0'..'9'
  ;

Int: ('0'..'9')+;

//Ignore white spaces
WS  : (' ' | '\t' | '\n' | '\r' ) -> skip
  ;

//LoLA identifiers forbidden characters 
//EXCLUDE : ('(' | ')' | '{' | '}' | ',' | ';' | ':' | ' ' | '\t' | '\n' | '\r' );  
//OPERATOR : ('<->' | '<>' | '->' | '=' | '[]' | '.' | '+' | '-' | '*' | '/' | '|' | '>' | '<' | '#' | '>=' | '<=' ) -> skip;
//KEYWORD : ('RECORD' | 'END' | 'SORT' | 'FUNCTION' | 'DO' | 'ARRAY' | 'ENUMERATE' | 'CONSTANT' | 'BOOLEAN' | 'OF' | 'BEGIN' | 'WHILE' | 'IF' | 'THEN' | 'ELSE' 
//         | 'SWITCH' | 'CASE' | 'NEXTSTEP' | 'REPEAT' | 'FOR' | 'TO' | 'ALL' | 'EXIT' | 'EXISTS' | 'RETURN' | 'TRUE' | 'FALSE' | 'MOD' | 'VAR' | 'GUARD' | 'STATE' 
//         | 'PATH' | 'GENERATOR' | 'ANALYSE' | 'PLACE' | 'TRANSITION' | 'MARKING' | 'CONSUME' | 'PRODUCE' | 'FORMULA' | 'EXPATH' | 'ALLPATH' | 'ALWAYS' | 'UNTIL' 
//         | 'EVENTUALLY' | 'AND' | 'OR' | 'NOT') -> skip;

//Identifier representation  
Name  : (LETTER | DIGIT | '_' | '-' | '\'' | '#' | '.' )+;

