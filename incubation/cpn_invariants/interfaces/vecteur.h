#ifndef _vecteur_
#define	_vecteur_

#include "CListes.h"
typedef CListe     Vect_Type;

#ifdef __cplusplus
extern "C" {
#endif
extern  bool				VectTestZero(Vect_Type V);
extern  void					VectDetr(Vect_Type V);
extern  void					VectZero(Vect_Type V);
extern  void					VectDupl(Vect_Type V1,Vect_Type V2);
extern  void					VectAdd(Vect_Type V1,Vect_Type V2);
extern  int						VectOrdre(Vect_Type V1,Vect_Type V2);
extern  Vect_Type			VectCreer(void);
extern 	void					VectMultGaucheFonct(Fonct_Type F,Vect_Type V2);
extern	void					VectOppose(Vect_Type V);
extern	int						VectSigne(Vect_Type V);
/* Vecteur particulier */
extern	bool				VectTestSupport(Vect_Type V,int t,Fonct_Type *F);
extern 	void					VectSupport(Vect_Type V,Entier_Liste Support);
extern	bool				VectTestUneEntree(Vect_Type V,int *t,Fonct_Type *F);
extern	void					VectFonct(Vect_Type V,Fonct_Type F,int t);
extern	void					VectIdentite(Vect_Type V,int p,Entier_Liste DomainePlace,Classe_Liste SpecifClasse);
extern	bool				VectTestUnitaire(Fonct_Type V);
/* Operations elementaires sur les vecteurs */
extern	void					VectSupprimeTransition(Vect_Type M,int t);
/* Fonction de comparaison */
extern	bool				VectSuperieur(Vect_Type V,Fonct_Type G,int t,int *tm,int NbVariable);
extern	bool				VectInferieur(Vect_Type V,Fonct_Type G,int NbVariable);
#ifdef __cplusplus
}
#endif
#endif
