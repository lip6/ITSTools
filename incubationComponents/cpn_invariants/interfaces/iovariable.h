#ifndef _iovariable_
#define	_iovariable_

#include	"variable.h"

#ifdef __cplusplus
extern "C" {
#endif
extern	bool				VarEntre(Var_Liste L,char *f);
extern  void					VarSort(Var_Liste L,char *f);
extern	bool				VarChiffreEntre(Var_Liste L,char *f);
#ifdef __cplusplus
}
#endif
#endif
