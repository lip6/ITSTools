/* Declaration des operations du TYPE1 = entier */

#define TYPE1           int
#define T1CREER()	0	
#define T1NULL(a)       ((a==0) ? true : false)
#define T1DETR(a)
#define T1DUPL(a,b)     b=a
#define T1ADD(a,b)      b=a+b
#define T1ORDRE(a,b)    ((a<b) ? 1 : ((a>b) ? -1 : 0))
#define T1MULT(a,b)	b=a*b
#define T1OPPOSE(a)	a=-a
#define	T1SIGNE(b)	 ((0<b) ? 1 : ((0>b) ? -1 : 0))

/* Declaration des operations du TYPE2 = monome */

#define TYPE2           Mo_Type
#define T2CREER		MoCreer
#define T2DETR		MoDetr
#define T2DUPL		MoDupl
#define T2ORDRE		MoOrdre
#define T2MULT		MoMult
#define T2NULL(a)	false

/*  Declaration des operations sur les polynomes */

#define TYPE3           Poly_Type
#define T3CREER         PolyCreer
#define T3NULL          PolyTestZero
#define T3DETR          PolyDetr
#define T3ZERO          PolyZero
#define T3DUPL          PolyDupl
#define T3ADD           PolyAdd
#define T3ORDRE		PolyOrdre
#define T3MULT1Gauche	PolyMultEntier
#define T3MULT2Gauche	PolyMultMonome
#define T3MULT		PolyMult
#define	T3OPPOSE	PolyOppose
#define	T3SIGNE		PolySigne



