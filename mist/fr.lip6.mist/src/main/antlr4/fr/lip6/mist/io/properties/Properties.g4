grammar Properties;


/**
property "ReachabilityProperty1" [reach] : EF "P1" + P2 <= 5;
property "InvariantProperty1" [reach] : AG (P1 + P2 > 5);
property "AP1" [atom] : P1 <= P2;
property "RefAtom" [reach] : EF @AP1;
property "CTLProperty1" [ctl] : AG(P1 <= 10 && P2 > 3);
property "CTLProperty2" [ctl] : E (P1 <= 10) U (P2 > 3);
property "LTLProperty1" [ltl] : G(P1 <= 10);
property "LTLProperty2" [ltl] : F(P2 > 3 U P1 <= 10);
property "BoundsProperty1" [bounds] : P1 + P2;
property "KeywordProperty" [ctl] : "A"==0 && AX(P1 <= 10);
property "ComplexProperty" [ltl] : F("A" + B == 0 U C <= "F" + 5);

*/

properties
    : property* EOF
    ;

property
    : 'property' (name=ID | name=STRING) body=logicProp ';'
    ;

logicProp
    : boundsProp
    | boolProp
    ;

boolProp
    : reachableProp
    | ctlProp
    | ltlProp
    | atomicProp
    ;

boundsProp
    : '[' 'bounds' ']' ':' target=boundsPredicate
    ;

reachableProp
    : '[' 'reach' ']' ':' op=('AG'|'EF') predicate=pOr
    ;

atomicProp
    : '[' 'atom' ']' ':' predicate=pOr
    ;

ctlProp
    : '[' 'ctl' ']' ':' predicate=ctlUntil
    ;

ltlProp
    : '[' 'ltl' ']' ':' predicate=ltlImply
    ;

/* =====   Arithmetic expressions ===== */

/* ====== Arithmetic operators ======= */
// by order of increasing precedence
constant
    : INT
    ;

pAddition
    : pPrimary ( '+'  pPrimary )*
    ;

pPrimary
    : reference
    | constant
    ;

pOr
    : pAnd ( '||' pAnd )*
    ;

pAnd
    : pNot ( '&&' pNot )*
    ;

pNot
    : isNeg='!'? pPrimaryBool  ;

pPrimaryBool
    : tTrue
    | fFalse
    | pComparison
    | '(' nested=pOr ')'
    | atomReference
    ;

pComparison
    : left=pAddition operator=comparisonOperators right=pAddition
    ;

comparisonOperators
    : '<='
    | '<'
    | '>='
    | '>'
    | '=='
    | '!='
    ;


ctlUntil
    : q='A' left=ctlImply 'U' right=ctlImply
    | q='E' left=ctlImply 'U' right=ctlImply
    | left=ctlImply
    ;

ctlImply
    : left=ctlOr ('->' right=ctlOr)?
    ;

ctlOr
    : ctlAnd ( '||' ctlAnd )*
    ;

ctlAnd
    : ctlTemporal ( '&&' ctlTemporal )*
    ;

ctlTemporal
    : op=('AG'|'AF'|'AX'|'EG'|'EF'|'EX')? left=ctlNot
    ;

ctlNot
    : isNeg='!'? left=ctlPrimaryBool
    ;

ctlPrimaryBool
    : pPrimaryBool
    | '(' nested=ctlUntil ')'
    ;

ltlImply
    : left=ltlOr ( '->' right=ltlOr )?
    ;

ltlOr
    : ltlAnd ( '||' ltlAnd )*
    ;

ltlAnd
    : ltlUntil ( '&&' ltlUntil )*
    ;

ltlUntil
    : left=ltlFutGen ( 'U' right=ltlFutGen )?
    ;

ltlFutGen
    : (op=('F'|'G') left=ltlFutGen)
    | ltlNext
    ;

ltlNext
    : op='X'? left=ltlNot
    ;

ltlNot
    : isNeg='!'? ltlPrimaryBool    
    ;

ltlPrimaryBool
    : pPrimaryBool
    | '(' nested=ltlImply ')'
    ;

boundsPredicate
    : bpPrimary ( '+' bpPrimary )*
    ;

bpPrimary
    : reference
    | constant
    ;

reference
    : name=(ID | STRING)
    ;

atomReference
    : '@' name=(ID | STRING)
    ;

tTrue
    : 'true'
    ;

fFalse
    : 'false'
    ;

INT
    : [0-9]+
    ;

ID
    : [a-zA-Z_][a-zA-Z0-9_]*
    ;

PARAM
    : '$' ID
    ;

STRING
    : '"' ( ~[\\"\n\r] | '\\' [\\"] )* '"'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;
