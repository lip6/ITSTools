#ifndef _fonction_
#define	_fonction_

#include	"domaine.h"	/* Entier_Liste */
#include	"classe.h"	/* Classe_Liste */
#include "CListes.h"
typedef CListe     Fonct_Type;

#ifdef __cplusplus
extern "C" {
#endif
extern  bool				FonctTestZero(Fonct_Type F);
extern  void					FonctDetr(Fonct_Type F);
extern  void					FonctZero(Fonct_Type F);
extern  void					FonctDupl(Fonct_Type F1,Fonct_Type F2);
extern  void					FonctAdd(Fonct_Type F1,Fonct_Type F2);
extern  int						FonctOrdre(Fonct_Type F1,Fonct_Type F2);
extern  Fonct_Type		FonctCreer(void);
extern	void					FonctOppose(Fonct_Type F);
extern 	void					FonctMult(Fonct_Type F1,Fonct_Type F2);
extern	int						FonctSigne(Fonct_Type F);
/* Construction de fonction particuliere */
extern	void					FonctIdentite(Fonct_Type F,Entier_Liste DomainePlace,Classe_Liste SpecifClasse);
extern	void					FonctConstante(Fonct_Type F,int NbVariable);
/* Procedure de test */
extern	bool				FonctTestIdentite(Fonct_Type F,Fonct_Type FI, int	NbVariable);
extern	bool				FonctTestUnitaire(Fonct_Type F);
extern	void					FonctDomaine(Fonct_Type F,Entier_Liste Domaine);
extern	void					FonctDomainePlus(Fonct_Type F,Entier_Liste Domaine);
extern	bool				FonctTestQuasi(Fonct_Type F);
/* Simplification de domaine */
extern	bool				FonctTestSimplificationDomaine(Fonct_Type F,Fonct_Type G,int i,
        										Entier_Liste DomainePlace,Classe_Liste SpecifClasse);
extern	void    			FonctSupprimeComp(Fonct_Type F,int i);        
/* Comparaison de 2 fonctions */
extern	bool				FonctTestDomaine(Fonct_Type F,Entier_Liste Domaine);
extern	bool 			FonctSuperieur(Fonct_Type F,Fonct_Type G,int NbVariable);
#ifdef __cplusplus
}
#endif
#endif
