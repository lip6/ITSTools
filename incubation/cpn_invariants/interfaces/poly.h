#ifndef _poly_
#define	_poly_

#include "CListes.h"
typedef CListe     Poly_Type;

#ifdef __cplusplus
extern "C" {
#endif
extern  bool				PolyTestZero(Poly_Type m);
extern  void					PolyDetr(Poly_Type m);
extern  void					PolyZero(Poly_Type m);
extern  void					PolyDupl(Poly_Type m1,Poly_Type m2);
extern  void					PolyAdd(Poly_Type m1,Poly_Type m2);
extern  int						PolyOrdre(Poly_Type m1,Poly_Type m2);
extern  Poly_Type			PolyCreer(void);
extern 	void					PolyMult(Poly_Type m1,Poly_Type m2);
extern	void					PolyOppose(Poly_Type m);
extern	int						PolySigne(Poly_Type m);
/* le plus  */
extern	void					PolyEntier(Poly_Type p,int i);
extern	void					PolyMonome(Poly_Type p,int i);
extern	bool				PolyTestUn(Poly_Type p);
extern	bool				PolyTestNegatif(Poly_Type p);
extern	bool PolySuperieur(Poly_Type p1,Poly_Type p2);
#ifdef __cplusplus
}
#endif
#endif
