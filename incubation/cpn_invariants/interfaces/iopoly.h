#ifndef _iopoly_
#define	_iopoly_

#include	"variable.h"

#ifdef __cplusplus
extern "C" {
#endif
extern	bool				PolyEntre(Poly_Type m,char *f,Var_Liste L);
extern	void					PolySort(Poly_Type m,char *f,Var_Liste L);
#ifdef __cplusplus
}
#endif
#endif


