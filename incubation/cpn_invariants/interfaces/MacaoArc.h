#ifndef _MacaoArc_
#define	_MacaoArc_

#include "CListes.h"
typedef CListe Arc_Matrice;

#ifdef __cplusplus
extern "C" {
#endif
/* creation d'une matrice */
extern	Arc_Matrice		ArcCreer(void);
/* lecture d'une matrice de N0 */
extern	void					ArcEntre(Arc_Matrice L,FILE *Donnees,int NbPlace,int NbTransition,int *n);
/* Ajout d'un numero dans la matrice */
extern	int						ArcAjout(Arc_Matrice L,int p,int t,int *n);
/* Recherche d'un No */
extern	int						ArcRecherche(Arc_Matrice L,int p,int q);
/* Suppression d'une ligne */
extern	void					ArcPlaceSupprime(Arc_Matrice L,int p);
/*Suppresssion d'une colonne */
extern	void					ArcTransitionSupprime(Arc_Matrice L,int t);
/* impression matrice */
extern	void					ArcSort(Arc_Matrice M,char *f);
#ifdef __cplusplus
}
#endif
#endif
