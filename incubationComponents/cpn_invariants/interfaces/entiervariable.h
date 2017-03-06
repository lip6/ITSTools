#ifndef _entiervariable_
#define	_entiervariable_

#include "CListes.h"
typedef CListe Entier_Liste;
typedef int	ObjetEntier_Liste;

#ifdef __cplusplus
extern "C" {
#endif
extern 	Entier_Liste  EntierCreer(void);
extern 	void					EntierDetruit(Entier_Liste L);
extern 	void					EntierDetr(Entier_Liste L);
extern 	int						EntierTaille(Entier_Liste L);
extern 	int						EntierIndice(Entier_Liste L,int v); 
extern 	int						EntierAjout(Entier_Liste L,int v);
extern 	void					EntierSupprime(Entier_Liste L,int i);
extern 	void 					EntierNom(Entier_Liste L,int i,int *v);
extern	int						EntierAjoutFin(Entier_Liste L,int v);
extern	bool				EntierInclus(Entier_Liste L1,Entier_Liste L2);
extern	bool				EntierEgal(Entier_Liste L1,Entier_Liste L2);
extern	bool				EntierDisjoint(Entier_Liste L1,Entier_Liste L2);
extern	void					EntierInit(Entier_Liste L,int n);
#ifdef __cplusplus
}
#endif

#endif
