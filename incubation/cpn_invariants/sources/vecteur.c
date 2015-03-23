#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/* Quelques fichiers utiles */

#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "CListes.h"

/* Specification des listes de variables */
#include "taillechaine.h"
#include "variable.h"
/* Specification des listes d'entiers */
#include "entiervariable.h"
/* Specification des classes */
#include "classe.h"
/* Specification des fonctions */
#include "fonction.h"
/* Preparation final */
#include "compvecteur.h"
/* Le final !!!!n */
#include "composition.c"
#include "compositionmulti.c"

#include	"vecteur.h"
/************************************************************************/
/* Construction de fonction particuliere				*/
/************************************************************************/

/* fonction  -> vecteur */

void    VectFonct(Vect_Type V,Fonct_Type F,int t)
{	PCoType	aV;

	VectZero(V);
	aV=(PCoType)malloc(sizeof(CoType));
	aV->elt=t;
	aV->coef=F;
	AjoutListe(V,aV);
}	

/* Vecteur  identite */

void 	VectIdentite(Vect_Type V,int p,
                      Entier_Liste DomainePlace,
                      Classe_Liste SpecifClasse)
{	Fonct_Type F;

	F=FonctCreer();
	FonctIdentite(F,DomainePlace,SpecifClasse);
	VectFonct(V,F,p);
}	

/************************************************************************/
/* Procedure de test 							*/
/************************************************************************/

/* t support de V?: V.t = F */

static PCoType	ProcVectTestSupport(PCoType aV,int t)
{	if (aV->elt==t) return(aV);
	return(NULL);
}	


bool VectTestSupport(Vect_Type V,int t,Fonct_Type *F)
{
	PCoType aV;

	if ((aV=(PCoType)ParcoursListe(V,(ParcoursProcPtr)ProcVectTestSupport, t))==NULL)
  	return(false);
	*F=aV->coef;
	return(true);
}

/* Calcul du support de V */

static  void *  ProcVectSupport(PCoType aV,Entier_Liste Support)
{
EntierAjout(Support,aV->elt);
return(NULL);
}
                        
void	VectSupport(Vect_Type V,Entier_Liste Support )
{	EntierDetruit(Support);
	(void)ParcoursListe(V,(ParcoursProcPtr)ProcVectSupport,
		Support);
}		
	                        

/* V 1 seul entree: V = F.t */

bool VectTestUneEntree(Vect_Type V,int *t,Fonct_Type *F)
{	PCoType aV;

	if ((aV=(PCoType) ListePremier(V))==NULL) return(false);
	if (!UnSeulElement(V)) return(false);

	*t=aV->elt;
	*F=aV->coef;
	return(true);
}


/* Test fonction unitaire 				  */
/* Verifie que toutes les fonctions de V sont unitaires   */

static	PCoType	ProcVectTestUnitaire(PCoType aV)
{	if (FonctTestUnitaire(aV->coef)) return(NULL);
	return(aV);
}	

bool VectTestUnitaire(Fonct_Type V)
{

        if (ParcoursListe(V,(ParcoursProcPtr)ProcVectTestUnitaire)==NULL)
        	return(true);
        else	return(false);
}

/************************************************************************/
/*  Operations elementaires sur les vecteurs				*/
/************************************************************************/

/* Suppression d'une composante t */

static  PCoType  ProcVectSupprimeTransition(PCoType aV,int t)
{       if (aV->elt==t) return(aV);
                return(NULL);
}

void    VectSupprimeTransition(Vect_Type V,int t)
{       PCoType aV;

        if ((aV=(PCoType)ParcoursListe(V,(ParcoursProcPtr)ProcVectSupprimeTransition, t))!=NULL)
       	{	EnleveElement(V,aV);
      		FonctDetr(aV->coef);
       		free(aV);
	}
}	

/************************************************************************/
/* Comparaison des composantes d'un vecteurs avec une fonctions		*/
/************************************************************************/

/* Au moins 1 composant superieur a G*/

static	PCoType	ProcVectSuperieur(PCoType aV,Fonct_Type G,int t,int *tm,
	int NbVariable)
	{
	if (((*tm=aV->elt)!=t) && (FonctSuperieur(G,aV->coef,NbVariable))) return(aV);
	return(NULL);
	}	

bool VectSuperieur(Vect_Type V,Fonct_Type G,int t,int *tm,int NbVariable)
{	if (ParcoursListe(V,(ParcoursProcPtr)ProcVectSuperieur,G,
	t,tm,NbVariable)==NULL)
	return(false);
	return(true);
}

/* Toutes les composantes inferieurs a G */

static	PCoType	ProcVectInferieur(PCoType aV,Fonct_Type G,int NbVariable)
{	if (!FonctSuperieur(aV->coef,G,NbVariable)) return(aV);
	return(NULL);
}	

bool VectInferieur(Vect_Type V,Fonct_Type G,int NbVariable)
{	if (ParcoursListe(V,(ParcoursProcPtr)ProcVectInferieur,G,NbVariable)!=NULL)
	return(false);
	return(true);
}
