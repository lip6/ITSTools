#ifndef _domaine_
#define	_domaine_

#include	"entiervariable.h"	/* Entier_Liste */

#include "CListes.h"
typedef CListe     Dom_Liste;

typedef	Entier_Liste *PListeEntier;

#ifdef __cplusplus
extern "C" {
#endif
extern	Dom_Liste 		DomCreer(void);
extern 	void    			DomNom(Dom_Liste L,int i,Entier_Liste *v);
extern 	void    			DomAjoutFin(Dom_Liste L,Entier_Liste v);
#ifdef __cplusplus
}
#endif
#endif
