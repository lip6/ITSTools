#ifndef _compositionmulti_
#define	_compositionmulti_
	
#ifdef __cplusplus
extern "C" {
#endif
#ifdef T3MULT1Gauche
/****************************************************************/
/*	T3MULT1Gauche(a,F)	F=a.F				*/
/****************************************************************/
extern	void					T3MULT1Gauche(TYPE1 a,TYPE3 F);
#endif
/****************************************************************/
/*	T3MULT1Droite(F,a)	F=F.a				*/
/****************************************************************/
#ifdef T3MULT1Droite
extern	void					T3MULT1Droite(TYPE3 F,TYPE1 a);
#endif
/****************************************************************/
/*	T3MULT2Gauche(f,F)	F=f.F				*/
/****************************************************************/
#ifdef T3MULT2Gauche
extern	void					T3MULT2Gauche(TYPE2 f,TYPE3 F);
#endif
/****************************************************************/
/*	T3MULT1Droite(a,F)	F=F.f				*/
/****************************************************************/
#ifdef T3MULT2Droite
extern	void					T3MULT2Droite(TYPE3 F,TYPE2 f);
#endif
/****************************************************************/
/*	T3MULT(f,g)		G:=F*G				*/
/****************************************************************/
#ifdef T3MULT
extern	void					T3MULT(TYPE3 F,TYPE3 G);
#endif
#ifdef __cplusplus
}
#endif
#endif
