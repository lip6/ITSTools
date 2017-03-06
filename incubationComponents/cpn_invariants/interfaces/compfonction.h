#ifndef _compfonction_
#define	_compfonction_

/* Declaration des operations du TYPE1 = poly */

#define TYPE1           Poly_Type
#define T1CREER		PolyCreer	
#define T1NULL		PolyTestZero
#define T1DETR		PolyDetr
#define T1DUPL          PolyDupl
#define T1ADD      	PolyAdd
#define T1ORDRE    	PolyOrdre
#define T1MULT		PolyMult
#define	T1OPPOSE	PolyOppose
#define	T1SIGNE		PolySigne

/* Declaration des operations du TYPE2 = fonction elementaire */

#define TYPE2           FonctElement_Type
#define T2CREER		FonctElementCreer
#define T2DETR		FonctElementDetr
#define T2DUPL		FonctElementDupl 	
#define T2ORDRE		FonctElementOrdre 
#define T2MULTBIS	FonctElementMult 
#define T2NULL(a)	false

/*  Declaration des operations sur les fonctions */

#define TYPE3           Fonct_Type
#define T3CREER         FonctCreer
#define T3NULL          FonctTestZero
#define T3DETR          FonctDetr
#define T3ZERO          FonctZero
#define T3DUPL          FonctDupl
#define T3ADD           FonctAdd
#define T3ORDRE		FonctOrdre
#define T3MULT1Gauche	FonctMultpoly
#define T3MULT2Gauche	FonctMultFonctElement
#define T3MULT		FonctMult
#define T3OPPOSE	FonctOppose
#define	T3SIGNE		FonctSigne
#endif
