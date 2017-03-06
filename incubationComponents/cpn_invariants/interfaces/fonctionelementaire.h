#ifndef _fonctionelementaire_
#define	_fonctionelementaire_
/************************************************************************/
/* Types associes aux fonctions elementaires                            */
/************************************************************************/
#include "CListes.h"
typedef CListe FonctElement_Type;

struct FonctionClasse
{	char	typefonction;
	int	variable;
	int	successeur;
	int	classe;
	int	taille;
};
typedef struct FonctionClasse FonctionClasse;

typedef FonctionClasse *PFonctionClasse;


#ifdef __cplusplus
extern "C" {
#endif
/* Operations */
extern	FonctElement_Type	FonctElementCreer(void);
extern 	void					FonctElementUn(FonctElement_Type F);
extern	void					FonctElementDetr(FonctElement_Type F);
extern 	void					FonctElementDupl(FonctElement_Type F,FonctElement_Type G);
extern 	int						FonctElementOrdre(FonctElement_Type F,FonctElement_Type G);
extern 	void					FonctElementMult(FonctElement_Type F,FonctElement_Type G,Poly_Type P);
extern	int						FonctElementArite(FonctElement_Type F);
/* Construction fonction particuliere */
extern	void					FonctElementIdentite(FonctElement_Type F,
                        Entier_Liste DomainePlace,
                        Classe_Liste SpecifClasse);
extern	void					FonctElementConstante(FonctElement_Type F,int NbVariable);
extern	void					FonctElementDomaine(FonctElement_Type F,Entier_Liste Domaine);
/* Test sur fonctions */
extern	bool 			FonctElementTestIdentite(FonctElement_Type F,FonctElement_Type FI,int NbVariable);
extern	void    			FonctElementQuasi(FonctElement_Type F,FonctElement_Type FC);
/* Pour simplification de domaine */
extern	bool				FonctElementTestSimplificationDomaine(FonctElement_Type F,
								Poly_Type p,
                FonctElement_Type G,int i,
                Entier_Liste DomainePlace,Classe_Liste SpecifClasse);
extern	void   				VectSupprimeComp(FonctElement_Type F,int i);
/* Comparaison de 2 fonctions */
extern	bool				FonctElementSuperieur(FonctElement_Type F,FonctElement_Type G,int NbVariable);
/* Entree/Sortie */
extern	bool FonctElementEntre(FonctElement_Type F,char *f,
								Classe_Liste SpecifClasse,Var_Liste NomVariable,
								Entier_Liste Domaine);
extern 	void FonctElementSort(FonctElement_Type F,char *f,
								Classe_Liste SpecifClasse,Var_Liste NomVariable,
								Var_Liste NomClasse);
#ifdef __cplusplus
}
#endif
#endif

