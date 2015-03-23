#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/* Quelques fichiers utiles */

#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "CListes.h"
/* Specification des listes */
#include "taillechaine.h"
#include "variable.h"
#include "entiervariable.h"
/* Specification des classes*/
#include "classe.h"
/* Specification des polynomes */
#include "poly.h"
/* Specification des fonctions elementaires */
#include "fonctionelementaire.h"
#include	"fonction.h"
/* Preparation final */
#include "compfonction.h"
/* Le final !!!!n */
#include "composition.c"
#include "compositionmulti.c"

/************************************************************************/
/* Construction de fonction particuliere				*/
/************************************************************************/

/* fonction elementaire  -> fonction */

static	void    FonctFonctElement(Fonct_Type F,FonctElement_Type Fe)
{	PCoType	aF;

	FonctZero(F);
	aF=(PCoType)malloc(sizeof(CoType));
	aF->coef=PolyCreer();
	PolyEntier(aF->coef,1);
	aF->elt=Fe;
	AjoutListe(F, aF);
}	
/*
static	void    FonctPolyFonctElement(Fonct_Type F,Poly_Type p,FonctElement_Type Fe)
{	PCoType	aF;

	FonctZero(F);
	aF=(PCoType)malloc(sizeof(CoType));
	aF->coef=p;
	aF->elt=Fe;
	AjoutListe(F, aF);
}	
*/

/* fonction identite */

void 	FonctIdentite(Fonct_Type F,
                      Entier_Liste DomainePlace,
                      Classe_Liste SpecifClasse)
{	FonctElement_Type Fe;

	Fe=FonctElementCreer();
	FonctElementIdentite(Fe,DomainePlace,SpecifClasse);
	FonctFonctElement(F,Fe);
}	

/* fonction constante */

void 	FonctConstante(Fonct_Type F,
                        int NbVariable)
{	FonctElement_Type Fe;

	Fe=FonctElementCreer();
	FonctElementConstante(Fe,NbVariable);
	FonctFonctElement(F,Fe);
}


/************************************************************************/
/* Procedure de test 							*/
/************************************************************************/

/* test fonction identite avec calcul inverse generalise  */

bool FonctTestIdentite(Fonct_Type F,
	                        Fonct_Type FI,
				int NbVariable)
{	PCoType aF;
	FonctElement_Type Fe;                                                

	Fe=FonctElementCreer();
	if ((aF=(PCoType)ListePremier(F))==NULL) return(false);
	if(!UnSeulElement(F)) return(false);
	if (!PolyTestUn(aF->coef)) return(false);
	if (!FonctElementTestIdentite(aF->elt,Fe,NbVariable))
		return(false);
	FonctFonctElement(FI,Fe);	
	return(true);
}	

/* Test fonction unitaire avec calcul inverse generalise  */

static PCoType ProcFonctTestUnitaire(PCoType aF,bool *b)
{	if (!PolyTestNegatif(aF->coef))
	{	if (*b)	return(aF);
		else
		{	if (!PolyTestUn(aF->coef)) return(aF);
			else	*b=true;
		}
	}
	return(NULL);
}
	
bool FonctTestUnitaire(Fonct_Type F)
{
	PCoType aF;
	bool	b;

	b=false;
	if ((aF=(PCoType)ListePremier(F))==NULL) return(false);
	if( ParcoursListe(F,(ParcoursProcPtr)ProcFonctTestUnitaire,&b)
			==NULL)
		return(true);
	else
		return(false);	
}	

/* Calcul du Domaine d'une fonction */

static void * ProcFonctDomaine(PCoType aF,Entier_Liste Domaine)
{
	FonctElementDomaine(aF->elt,Domaine);
	return(NULL);
}	

void	FonctDomaine(Fonct_Type F,Entier_Liste Domaine)
{	

	EntierDetruit(Domaine);

        (void) ParcoursListe(F,(ParcoursProcPtr)ProcFonctDomaine,Domaine);
}

void	FonctDomainePlus(Fonct_Type F,Entier_Liste Domaine)
{	

        (void) ParcoursListe(F,(ParcoursProcPtr)ProcFonctDomaine,Domaine);
}


/* Test Quasi injective par rapport a un domaine*/

	/* Calcul du domaine d'injectivite */

static void * ProcFonctDomaineQuasi(PCoType aF,FonctElement_Type Fe)
{	FonctElementQuasi(aF->elt,Fe);
	return(NULL);
}	

static void FonctDomaineQuasi(Fonct_Type F,Entier_Liste Domaine)
{	PCoType	aF;
	FonctElement_Type Fe=FonctElementCreer();

	EntierDetruit(Domaine);
	if ((aF=(PCoType)ListePremier(F))!=NULL)
        {	FonctElementDupl(aF->elt,Fe);
                (void) ParcoursListe(F,
                  (ParcoursProcPtr)ProcFonctDomaineQuasi,Fe);
		FonctElementDomaine(Fe,Domaine);
	}
	FonctElementDetr(Fe); 
}	

          
bool	FonctTestQuasi(Fonct_Type F)
{	Entier_Liste DomaineF=EntierCreer();
 	Entier_Liste DomaineQ=EntierCreer();
	bool b;
	
	FonctDomaine(F,DomaineF);
	FonctDomaineQuasi(F,DomaineQ);
	
	b=EntierInclus(DomaineF,DomaineQ);
	EntierDetr(DomaineF);
	EntierDetr(DomaineQ); 
	return(b);
}

/*********** Recherche des simplifications possible *******/

bool	FonctTestSimplificationDomaine(Fonct_Type F,Fonct_Type G,int i,
	Entier_Liste DomainePlace,Classe_Liste SpecifClasse)
{	FonctElement_Type Ge=FonctElementCreer();
 	Fonct_Type GI=FonctCreer();
 	Poly_Type p=PolyCreer();
	PCoType aF;
	bool b;

	aF=(PCoType)ListePremier(F);
	if (!FonctElementTestSimplificationDomaine(aF->coef,p,Ge,i,DomainePlace,SpecifClasse))
		b=false;
	else
	{	FonctFonctElement(G,Ge);FonctOppose(G);
		FonctIdentite(GI,DomainePlace,SpecifClasse);
		FonctMultpoly(p,GI);
		FonctAdd(GI,G);
		b=true;
	}
	FonctElementDetr(Ge);
	FonctDetr(GI);
	PolyDetr(p); 
	return(b);
}

/***** Suppression la compossante composante i d'une fonction */
static 	void *	ProcFonctSupprimeComp(PCoType aF,int i)
{	VectSupprimeComp(aF->coef,i);
	return(NULL);
}

void	FonctSupprimeComp(Fonct_Type F,int i)
{
	(void) ParcoursListe(F,
                        (ParcoursProcPtr)ProcFonctSupprimeComp,i);	
	Arrangement(F); /* procedure definie dans compositionmulti.h */
}

/************ Test Domaine(F)<Domaine ************************/ 

bool	FonctTestDomaine(Fonct_Type F,Entier_Liste Domaine)
{	Entier_Liste DomaineF=EntierCreer();
	bool b;

	FonctDomaine(F,DomaineF);

	b=EntierInclus(DomaineF,Domaine);
	EntierDetr(DomaineF);
	return(b);
}

/****************************************************************/
/* Comparaison de deux fonctions a une seule componsante	*/
/****************************************************************/

bool FonctSuperieur(Fonct_Type F,Fonct_Type G,int NbVariable)
{	PCoType aF,aG;

	if(!UnSeulElement(F)) return(false);
	if(!UnSeulElement(G)) return(false);

	aF=(PCoType)ListePremier(F);
	aG=(PCoType)ListePremier(G);

	if (!PolySuperieur(aF->coef,aG->coef)) return(false);
	if (!FonctElementSuperieur(aF->elt,aG->elt,NbVariable)) return(false);
	return(true);
}	
