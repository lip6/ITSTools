#ifndef _variable_
#define	_variable_

#include	"taillechaine.h"	/* VARMAX */
#include "CListes.h"
typedef CListe Var_Liste;

typedef char Var_Type[VARMAX];

#ifdef __cplusplus
extern "C" {
#endif
extern	Var_Liste			VarCreer(void);
extern	void					VarDetruit(Var_Liste L);
extern	int						VarTaille(Var_Liste L);
extern	int						VarIndice(Var_Liste L,Var_Type v); 
extern	int						VarAjout(Var_Liste L,Var_Type v);
extern	void					VarSupprime(Var_Liste L,int i);
extern	void					VarNom(Var_Liste L,int i,Var_Type *v);
extern	int						VarAjoutFin(Var_Liste L,Var_Type v);
extern	void					VarInitxi(Var_Liste L,int n);
#ifdef __cplusplus
}
#endif
#endif
