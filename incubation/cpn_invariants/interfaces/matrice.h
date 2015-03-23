#ifndef _matrice_
#define	_matrice_

#include "CListes.h"
typedef CListe     Mat_Type;

#include	"vecteur.h"	/* Vect_Type */
#include	"domaine.h"	/* Dom_Liste */

#ifdef __cplusplus
extern "C" {
#endif
extern  bool				MatTestZero(Mat_Type M);
extern  void					MatDetr(Mat_Type M);
extern  void					MatZero(Mat_Type M);
extern  void					MatDupl(Mat_Type M1,Mat_Type M2);
extern  void					MatAdd(Mat_Type M1,Mat_Type M2);
extern  int						MatOrdre(Mat_Type M1,Mat_Type M2);
extern  Mat_Type			MatCreer(void);
extern	void					MatOppose(Mat_Type M);
/* Fonction particuliere */
extern	bool MatPlace(Mat_Type M,int p,Vect_Type *V);
extern	void					MatIdentite(Mat_Type M,int NbPLace,Dom_Liste Domaine,Classe_Liste SpecifClasse);
extern	bool				MatTestPlace(Mat_Type M,int p);
extern	bool				MatTestUneEntreePlace(Mat_Type M,int p,int *t,Fonct_Type *F);
extern	bool				MatTestUneEntreeTransition(Mat_Type M,int *p,int t,Fonct_Type *F);
extern	bool				MatTestCoef(Mat_Type M,int p,int t,Fonct_Type *F);
extern	bool				MatTestUnitaire(Mat_Type M,int p);
extern	void					MatDomaineTransition(Mat_Type M1,Entier_Liste Domaine,int t);
extern	void					MatSupportTransition(Mat_Type M,Entier_Liste SupportTransition,int t);
extern	void					MatSupportPlace(Mat_Type M,Entier_Liste SupportPlace,int p);
extern	bool				MatRechercheIdentite(Mat_Type M,Entier_Liste entree,Entier_Liste sortie,Entier_Liste Domaine,
													int t,int *p,Fonct_Type FI,int NbVariable);
/* Operations sur la matrice */
extern	void					MatMultPlace(Mat_Type M,int p,Fonct_Type F);
extern	void					MatAddPlace(Mat_Type M,int p,int q,Fonct_Type F);
extern	void					MatSupprimePlace(Mat_Type M,int p);
extern	void					MatSupprimeTransition(Mat_Type M,int p);
extern	void					MatSupprimeCoef(Mat_Type M,int p,int t);
extern 	void					MatSimpl2Mat(Mat_Type M1,Mat_Type M2,Entier_Liste ExistePlace,Entier_Liste ExisteTransition,int NbPlace,int NbTransition);
extern 	void					MatCopiePlace(Mat_Type M1,int p,Mat_Type M2,int q);
extern	void					MatDeplacePlace(Mat_Type M1,int p,Mat_Type M2,int q);
extern	void					MatNice(Mat_Type M1,Mat_Type M2);
extern 	void					MatSwitchPlace(Mat_Type M1,Mat_Type M2,int p);
#ifdef __cplusplus
}
#endif
#endif
