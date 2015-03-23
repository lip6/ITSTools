	/******* Le type liste de classe *********/
#ifndef _classe_
#define	_classe_

#include	"variable.h"	/* Var_Liste */

#include "CListes.h"
typedef	CListe Classe_Liste;

#ifdef __cplusplus
extern "C" {
#endif
extern	bool 			ClasseListeEntre(Classe_Liste L,char *f,Var_Liste NomParametre);
extern	void					ClasseListeSort(Classe_Liste L,char *f,Var_Liste NomParametre);
extern	Classe_Liste	ClasseCreer(void);
extern	void					ClasseInit(Classe_Liste L);
extern 	int						ClasseTaille(Classe_Liste L,int i);
extern 	int						ClasseConstanteEntre(Classe_Liste L,char *f,int IndClasse);
extern 	void					ClasseConstanteSort(Classe_Liste L,char *f,int IndClasse,int k);
#ifdef __cplusplus
}
#endif
#endif
