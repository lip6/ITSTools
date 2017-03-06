#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/* Gestion des numeros d'arcs (p,t) */

#include <stdio.h>
#include <string.h>

#include "CListes.h"
#include "taillechaine.h"

#include	"MacaoArc.h"
/*********** structure des donnees "matrice creuse"! */

struct  NoTransition
{	int	transition;
	int	numero;
};
typedef struct NoTransition NoTransition;
typedef NoTransition *PNoTransition;
typedef CListe Arc_Ligne;	
struct  NoPlace
{       int     	place;
        Arc_Ligne	ligne;
};
typedef struct NoPlace NoPlace;
typedef NoPlace *PNoPlace;

/********************************************************/
/******** Creation **************************************/
/********************************************************/

Arc_Matrice	ArcCreer(void)
{	return(CreerListe());
}
/*************************************** utile ************************/
/*** recherche de la ligne p */

static PNoPlace ProcArcPlaceRech(PNoPlace aL,int p)
{	
	if (aL->place==p) return(aL);
	return(NULL);
}

static	void *	ArcPlaceRech(Arc_Matrice L,int p)
	{
	return(ParcoursListe(L,(ParcoursProcPtr) ProcArcPlaceRech, p));
	}

/************** recherche dans une ligne *******/

static 	PNoTransition 	ProcArcTransitionRech(PNoTransition aL,int t)
{	
	if (aL->transition==t)	return(aL);
	return(NULL);
}	

static	void * ArcTransitionRech(Arc_Ligne L,int t)
	{
	return(ParcoursListe(L,(ParcoursProcPtr) ProcArcTransitionRech,	t));
	}

/**********************************************************************/
/*************** Procedure d'affichage de la matrice ******************/
/**********************************************************************/
static	void *	ArcSortTransition(PNoTransition aT,char *f)
{	 char    v[max];

	strcpy(v,"");sprintf(v," %d.",aT->numero);
	strcat(f,v);
	strcpy(v,"");sprintf(v,"<%d> ",aT->transition);
  strcat(f,v);
	return(NULL);		/* ajout de jlm */
}

static	void *	ArcSortPlace(PNoPlace aP,char *f)
{	char	v[max];

	strcpy(v,"");sprintf(v,"[%d]:",aP->place);
	strcat(f,v);

	ParcoursListe(aP->ligne,(ParcoursProcPtr) ArcSortTransition, f);
	strcat(f,"\n");
	return(NULL);
}                
	
void ArcSort(Arc_Matrice M,char *f)
{	strcpy(f,"");
	ParcoursListe(M,(ParcoursProcPtr) ArcSortPlace,
                        f);
}                        

/*********************************************************/
/************* Procedure de lecture ********************/
/*********************************************************/

static void	ArcLigneEntre(Arc_Ligne L,FILE *Donnees,int NbTransition,int *n)
{	char 	c;
	int i,v;
	PNoTransition	t;

	fscanf(Donnees,"%c",&c); 
	for (i=1;i<=NbTransition;i++)
	{	fscanf(Donnees,"%d",&v);
		if (v!=0)
		{	if (v>=*n) *n=v+1;
			t=(PNoTransition)malloc(sizeof(NoTransition));
			t->transition=i;
			t->numero=v;			
			AjoutFinListe(L,t);
		}
	}
	fscanf(Donnees,"%c",&c);
	fscanf(Donnees,"%c",&c);
}	

void	ArcEntre(Arc_Matrice L,FILE *Donnees,int NbPlace,int NbTransition,int *n)
{	int i;
	PNoPlace p;

	for (i=1;i<=NbPlace;i++)
	{	p=(PNoPlace)malloc(sizeof(NoPlace));
		p->ligne=(Arc_Ligne)CreerListe();
		ArcLigneEntre(p->ligne,Donnees,NbTransition,n);
		p->place=i;
		AjoutFinListe(L,p);
	}
}
	
/*********************************************************/
/************* Ajout  un nouvelle arc *******************/
/*******************************************************/


static	int 	ArcLigneAjout(Arc_Ligne L,int t,int *n)
{	PNoTransition aL,bL;
	
	aL= (PNoTransition)ArcTransitionRech(L,t);
	if (aL!=0) return(aL->numero);
	bL=(PNoTransition)malloc(sizeof(NoTransition));
	bL->transition=t;	
	bL->numero=*n;
	*n+=1;
	AjoutFinListe(L,bL);
	return((*n)-1);
}

/***** L'Ajout *********/
int 	ArcAjout(Arc_Matrice L,int p,int t,int *n)
{	PNoPlace aL,bL;

	
	aL=(PNoPlace)ArcPlaceRech(L,p);
	if (aL!=NULL) return(ArcLigneAjout(aL->ligne,t,n));
	
	bL=(PNoPlace)malloc(sizeof(NoPlace));
	bL->place=p;	
	bL->ligne=(Arc_Ligne)CreerListe();
	(void) ArcLigneAjout(bL->ligne,t,n);
	AjoutFinListe(L,bL);		
	return((*n)-1);
}	





/***********************************************************************/
/****************** Procedure Recherche ********************************/
/***********************************************************************/

int 	ArcRecherche(Arc_Matrice L,int p,int t)
{	PNoPlace	aP;
	PNoTransition 	aT;


	aP=(PNoPlace)ArcPlaceRech(L,p);
	if (aP==NULL) return(0);
	aT=(PNoTransition)ArcTransitionRech(aP->ligne,t);
	if (aT==NULL) return(0);
	return(aT->numero);
}

/***********************************************************************/
/****************** Procedure Suppression place*************************/
/***********************************************************************/	

void     ArcPlaceSupprime(Arc_Matrice L,int p)
{	PNoPlace        aP;
	
	aP=(PNoPlace)ArcPlaceRech(L,p);	
	if (aP!=NULL) EnleveElement(L,aP);
}

/***********************************************************************/
/****************** Procedure Suppression Transition********************/
/***********************************************************************/	

static	void *	ProcArcTransitionSupprime(PNoPlace aP,int t)
{	PNoTransition	aT;
	

	aT=(PNoTransition)ArcPlaceRech(aP->ligne,t);
	if (aT!=NULL)  EnleveElement(aP->ligne,aT);
	return(NULL);
}	

void	ArcTransitionSupprime(Arc_Matrice L,int t)
{
	ParcoursListe(L,(ParcoursProcPtr) ProcArcTransitionSupprime,
                t);
}



