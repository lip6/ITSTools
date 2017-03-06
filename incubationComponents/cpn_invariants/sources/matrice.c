#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/* Quelques fichiers utiles */

#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "CListes.h"

#include "taillechaine.h"
#include "variable.h"
#include "entiervariable.h"
#include "classe.h"
#include "domaine.h"
/* Specification des fonctions */
#include "fonction.h"
/* Specification des vecteurs */
#include "vecteur.h"
/* Preparation final */
#include "compmatrice.h"
/* Le final !!!!n */
#include "composition.c"
#include	"matrice.h"

/************************************************************************/
/* Construction de fonction particuliere				*/
/************************************************************************/

/* matrice -> fonction */

static void MatVect(Mat_Type M,Vect_Type V,int p)
{	PCoType	aM;

	VectZero(M);
	aM=(PCoType)malloc(sizeof(CoType));
	aM->elt=p;
	aM->coef=V;
	AjoutListe(M,aM);
}	

/* Matrice  identite */

void 	MatIdentite(Mat_Type M,int NbPlace,
                      Dom_Liste Domaine,
                      Classe_Liste SpecifClasse)
{	PCoType aM;
	Entier_Liste DomainePlace;
	int	p;

	for (p=1;p<=NbPlace;p++)
	{	aM=(PCoType)malloc(sizeof(CoType));
		aM->elt=p;
		aM->coef=FonctCreer();
		DomNom(Domaine,p,&DomainePlace);
		VectIdentite(aM->coef,p,DomainePlace,SpecifClasse);
		AjoutFinListe(M,aM);
	}	
	
}	

/************************************************************************/
/* Procedure de test 							*/
/************************************************************************/


/* p support de M?  Petit + on obtient sont vecteur M(p,.)*/

static	PCoType	ProcMatTestSupport(PCoType aM,int p)
{	if (aM->elt==p) return(aM);
	return(NULL);
}	


bool MatPlace(Mat_Type M,int p,Vect_Type *V)
{
	PCoType aM;
	if ((aM=(PCoType)ParcoursListe(M,(ParcoursProcPtr)ProcMatTestSupport, p))==NULL)
		return(false);
	*V=aM->coef;
	/*... Suite a un probleme de netoyage */
	if (VectTestZero(*V)) return(false);
	return(true);
}

bool MatTestPlace(Mat_Type M,int p)
{
	PCoType aM;
	if ((aM=(PCoType)ParcoursListe(M,(ParcoursProcPtr)ProcMatTestSupport, p))==NULL)
        	return(false);

	return(true);
}

/******* De l'utile a l'agreable **************/

/* Test: M(p,t) */

bool MatTestCoef(Mat_Type M,int p,int t,Fonct_Type *F)
{	Vect_Type V;	

	if (!MatPlace(M,p,&V)) return(false);
	return(VectTestSupport(V,t,F));
}	


/* p: 1 entree dans M : M(p,.)= F.t */

bool MatTestUneEntreePlace(Mat_Type M,int p,int *t,Fonct_Type *F)
{	Vect_Type V;

	if (!MatPlace(M,p,&V)) return(false);
	return(VectTestUneEntree(V,t,F));
}

/* t: 1 entree dans M: M(.,t)= F.p */

static	PCoType	ProcMatTestUneEntreeTransition(PCoType aM,int *p,int t,
				Fonct_Type *F,bool *b)
{	Fonct_Type Fbis;
	
	if (*b)
	{	if (VectTestSupport(aM->coef,t,&Fbis))
		{	*b=false;
			return(aM);
		}	
	}
	else
	{	if  (VectTestSupport(aM->coef,t,F))
		{	*p=aM->elt;
			*b=true;
		}	
	}
	return(NULL);
}	

bool MatTestUneEntreeTransition(Mat_Type M,int *p,int t,Fonct_Type *F)
{	bool b;

	b=false;
	(void)ParcoursListe(M,(ParcoursProcPtr)ProcMatTestUneEntreeTransition, p, t, F,&b);
	return(b);
}

/* Test place  unitaire M(p,.)= Somme F.t !=0 avec F unitaire	  */

bool MatTestUnitaire(Mat_Type M,int p)
{	Vect_Type V;

	if (!MatPlace(M,p,&V)) return(false);
	return(VectTestUnitaire(V));
}	
	


/*********** Et moins le graphe ********************/

/* Calcul du domaine d'une transition M(.,t) */

static	void *	ProcMatDomaineTransition(PCoType aM,Entier_Liste Domaine,int t)
{	Fonct_Type F;

	if (VectTestSupport(aM->coef,t,&F))
		FonctDomainePlus(F,Domaine);
	return(NULL);
}	


			
void	MatDomaineTransition(Mat_Type M1,Entier_Liste Domaine,
			int t)
{	
	(void)ParcoursListe(M1,(ParcoursProcPtr)ProcMatDomaineTransition,	Domaine, t);
}
	
/* Calcul du support M(.,t) */
static	void *	ProcMatSupportTransition(PCoType aM,Entier_Liste SupportTransition,int t)
{	Fonct_Type F;

	if (VectTestSupport(aM->coef,t,&F))
		EntierAjout(SupportTransition,aM->elt);
	return(NULL);
}	


			
void	MatSupportTransition(Mat_Type M,Entier_Liste SupportTransition,
			int t)
{	EntierDetruit(SupportTransition);
	(void)ParcoursListe(M,(ParcoursProcPtr)ProcMatSupportTransition, SupportTransition, t);
}
	

			
/* Calcul du support M(p,.) */

void	MatSupportPlace(Mat_Type M,Entier_Liste SupportPlace,
			int p)
{	Vect_Type V;

	EntierDetruit(SupportPlace);
	if (MatPlace(M,p,&V))
		VectSupport(V,SupportPlace);
}


/* Recherche d'une fonction Identite dans M(.,t)  */

                          				
bool	MatRechercheIdentite(Mat_Type M,
	Entier_Liste entree,Entier_Liste sortie,Entier_Liste Domaine,
	int t,int *p,Fonct_Type FI,int NbVariable)
{
	Fonct_Type	F;
	Entier_Liste Domainep;
	int i,n;

	Domainep=EntierCreer();


/* Parcours merdique de la liste entree */
	n=EntierTaille(entree); 

	for (i=1;i<=n;i++)
	{	EntierNom(entree,i,p);
		/* Regard si p est dans sortie */
		if (EntierIndice(sortie,*p)==-1) 	
		{	/* Regard si M(p,t)=Identite */
			MatTestCoef(M,*p,t,&F);
			FonctDomaine(F,	Domainep);
			if (EntierInclus(Domaine,Domainep))
			{	if (FonctTestIdentite(F,FI,NbVariable))
				{	EntierDetr(Domainep);
 					return(true);
				}
			}
		}
	}
	return(false);
}

/************************************************************************/
/*  Operations elementaires sur la matrice				*/
/************************************************************************/

/* Multiplication du ligne: M(p,.)=F.M(p,.) */

void	MatMultPlace(Mat_Type M,int p,Fonct_Type F)
{	Vect_Type V;

	if(MatPlace(M,p,&V))
		VectMultGaucheFonct(F,V);
}

/* Addition a une ligne: M(p,.)=M(p,.)+F.M(q,.) */

void	MatAddPlace(Mat_Type M,int p,int q,Fonct_Type F)
{	Vect_Type Vp,Vq;
 	Vect_Type V;
	Mat_Type M1;

	
	if ((MatPlace(M,q,&Vq)) && (!FonctTestZero(F)) )
	{	V=VectCreer();
		VectDupl(Vq,V);
		VectMultGaucheFonct(F,V);
		if(MatPlace(M,p,&Vp))
		{	VectAdd(V,Vp);
			VectDetr(V);
		}
		else
		{
			M1=MatCreer();
			MatVect(M1,V,p);
			MatAdd(M1,M);
			MatDetr(M1);
		}
	}
}


/* Suppression d'une ligne */

static	PCoType	ProcMatSupprimePlace(PCoType aM,int p)
{	if (aM->elt==p) return(aM);
	return(NULL);
}	

void    MatSupprimePlace(Mat_Type M,int p)
{	PCoType aM;

	if ((aM=(PCoType)ParcoursListe(M,(ParcoursProcPtr)ProcMatSupprimePlace, p))!=NULL)
        {	EnleveElement(M,aM);
        	VectDetr(aM->coef);
        	free(aM);
        }	
}

/* Suppression d'une colonne */

static  void *  ProcMatSupprimeTransition(PCoType aM,int t)
{	VectSupprimeTransition(aM->coef,t);
	return(NULL);
}	
	
void	MatSupprimeTransition(Mat_Type M,int t)
{        (void)ParcoursListe(M,(ParcoursProcPtr)ProcMatSupprimeTransition, t);
}

/* Suppression: M(p,t) */

void	MatSupprimeCoef(Mat_Type M,int p,int t)
{	Vect_Type V;	

	if (MatPlace(M,p,&V)) 
	{	VectSupprimeTransition(V,t);
		if (VectTestZero(V)) MatSupprimePlace(M,p);
	} 
}

/* Suppression des elements tel que M1(p,t)=M2(p,t) */
/*......... A optimiser largement ............      */

void	MatSimpl2Mat(Mat_Type M1,Mat_Type M2,Entier_Liste ExistePlace,
Entier_Liste ExisteTransition,int NbPlace,int NbTransition)

{	int p;
	int t;
	Fonct_Type F1;
	Fonct_Type F2;



	for (p=1;p<=NbPlace;p++)
	if (EntierIndice(ExistePlace,p)!=-1)
	for (t=1;t<=NbTransition;t++)
	if (EntierIndice(ExisteTransition,t)!=-1)
	{	if (MatTestCoef(M1,p,t,&F1))
		if (MatTestCoef(M2,p,t,&F2))
		if (FonctOrdre(F1,F2)==0)
		{	MatSupprimeCoef(M1,p,t);
			MatSupprimeCoef(M2,p,t);
		}
	}
}


/* Copie la ligne p d'une matrice M1 en la ligne q d'une matrice M2 */

void	MatCopiePlace(Mat_Type M1,int p,Mat_Type M2,int q)
{	Vect_Type V1;
	Vect_Type V2;
	Mat_Type M;

	MatSupprimePlace(M2,q);
	if (MatPlace(M1,p,&V1))
	{	M=MatCreer();
		V2=VectCreer();
		VectDupl(V1,V2);
		MatVect(M,V2,q);
		MatAdd(M,M2);
		MatDetr(M); 
	}
}	

/* Deplace la ligne p d'une matrice M1 en la ligne q d'une matrice M2 */

void    MatDeplacePlace(Mat_Type M1,int p,Mat_Type M2,int q)
{	MatCopiePlace(M1,p,M2,q);
	MatSupprimePlace(M1,p);
}

/* Switch la ligne p des matrices M1 et M2 */

void 	MatSwitchPlace(Mat_Type M1,Mat_Type M2,int p)
{	Vect_Type V1;
	Vect_Type V2;
	Vect_Type V;
	Mat_Type M;


	if (MatPlace(M1,p,&V1))
	{	if (MatPlace(M2,p,&V2))
		{	V=VectCreer();
			M=MatCreer();
			VectDupl(V1,V);
			MatVect(M,V,p);
			MatDeplacePlace(M1,p,M2,p);
			MatAdd(M,M1);
			MatDetr(M);
			VectDetr(V);
		}
		else
		{	MatDeplacePlace(M1,p,M2,p);
		}
	}
	else
	{	if (MatPlace(M2,p,&V2))
		{	MatDeplacePlace(M2,p,M1,p);
		}
	}
}
			


/* Petite procedure pour arranger les flots*/

static	void *	ProcMatNice(PCoType aM,Mat_Type M2)
{	Vect_Type V;	

	if (VectSigne(aM->coef)==-1)
	{	VectOppose(aM->coef);
		if (MatPlace(M2,aM->elt,&V)) VectOppose(V);
	}	
	return(NULL);
}	


void	MatNice(Mat_Type M1,Mat_Type M2)
{	
        (PCoType)ParcoursListe(M1,(ParcoursProcPtr)ProcMatNice, M2);
}

