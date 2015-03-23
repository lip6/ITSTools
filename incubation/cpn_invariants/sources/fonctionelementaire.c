#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/************************************************************************/
/* Ce module contient les operations sur les fonctions elementaire ?    */
/*                                                                      */
/************************************************************************/

/************************************************************************/
/* Quelques fichiers utiles                                             */
/************************************************************************/

#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */


#include "CListes.h"

#include "taillechaine.h"
#include "variable.h"
#include "iovariable.h"
#include "entiervariable.h"
#include "ioentiervariable.h"
#include "classe.h"
#include "poly.h"
#include "iopoly.h"
#include	"fonctionelementaire.h"

/************************************************************************/
/* Procedure de creation				                */
/************************************************************************/

FonctElement_Type       FonctElementCreer(void)
{	return((FonctElement_Type)CreerListe());
}

/************************************************************************/
/* Procedure f=<>					                */
/************************************************************************/

static	void *	ProcFonctElementUn(PFonctionClasse fc)
{	free(fc);
	return(NULL);
}

void 	FonctElementUn(FonctElement_Type F)
{	(void) ParcoursListe(F,(ParcoursProcPtr)ProcFonctElementUn);
	ViderListe(F);
}


/************************************************************************/
/* Procedure Detruire					                */
/************************************************************************/

void    FonctElementDetr(FonctElement_Type F)
{	FonctElementUn(F);
	DetruireListe(F);
}

/************************************************************************/
/* Procedure de duplication				                */
/************************************************************************/

static void * DuplObjet(PFonctionClasse aF,FonctElement_Type G)
{       PFonctionClasse aG;
        aG = (PFonctionClasse) malloc(sizeof(FonctionClasse));
        aG->typefonction =	aF->typefonction;
        aG->variable 	=	aF->variable;
	aG->successeur	=	aF->successeur;
	aG->classe	=	aF->classe;
	aG->taille	=	aF->taille;
        
        AjoutFinListe(G,aG);
        return(NULL);
}

void    FonctElementDupl(FonctElement_Type F,FonctElement_Type G)
{       FonctElementUn(G);
        (void) ParcoursListe(F,(ParcoursProcPtr)DuplObjet,G);
}

/************************************************************************/
/* Procedure d'ordre					                */
/************************************************************************/
/* Liste des variables de diffusion unique */

static	void *	ProcFonctElementDiff1(PFonctionClasse aF,Entier_Liste L1,
		Entier_Liste L2,int *i)
{	int k;

	if ((aF->typefonction=='d')&&(aF->variable>0))
	{	if(EntierAjout(L1,aF->variable)==*i+1)
		{	*i+=1;
			(void) EntierAjoutFin(L2,aF->variable);
		}
		else
		{	if ( (k=EntierIndice(L2,aF->variable))!=-1)
				EntierSupprime(L2,k);
		}	
	}
	return(NULL);
}	

static 	void	FonctElementDiff1(FonctElement_Type F,Entier_Liste L)
{
	Entier_Liste	L1=EntierCreer();
	int		i;

	i=0;
	EntierDetruit(L);
	EntierDetruit(L1);
	(void) ParcoursListe(F, (ParcoursProcPtr)ProcFonctElementDiff1, L1, L, &i);
	EntierDetr(L1); 
}




/* Duplication d'une liste sans dupliquer les valeurs!!! */

static void * DuplAdresse(PFonctionClasse aF, FonctElement_Type G)
{       AjoutFinListe(G,aF);
        return(NULL);
}


static void DuplListeAdresse(FonctElement_Type F, FonctElement_Type G)
{	ViderListe(G);
	(void) ParcoursListe(F,(ParcoursProcPtr)DuplAdresse, G);
}

/* Ordre entre 2 fonctions de classes */

static	int Ordre(PFonctionClasse aF,PFonctionClasse aG,
		Entier_Liste LF, Entier_Liste LG,
		Entier_Liste DF, Entier_Liste DG)
{	int kF,kG;

        if(aF->typefonction !=	aG->typefonction)
        {	if(aF->typefonction <  aF->typefonction) return(1);
		else					 return(-1);
	} 
        if(aF->variable != aG->variable)
        {	if (aF->typefonction=='d')
        	{ 	if ((aF->variable==0) ||
			   (EntierIndice(DF,aF->variable)!=-1))
			{	if ((aG->variable!=0)&&
				(EntierIndice(DG,aG->variable)==-1))
					return(1);
			}
			else
			{	if ((aG->variable==0) ||
				  (EntierIndice(DG,aG->variable)!=-1))
					return(-1);
				else
				{ 	kF=EntierAjout(LF,aF->variable);
					kG=EntierAjout(LG,aG->variable);
					if (kF!=kG)
        				{ if (kF<kG)	return(1);
        	  	    	   	  else 		return(-1);
        	  	    	   	}  
        	  	    	}   
			}	
        	}
		else	
		{	if(aF->variable <  aG->variable) return(1);
			else				 return(-1);
		}
	} 
        if(aF->successeur != aG->successeur)
        {	if(aF->successeur <  aG->successeur) return(1);
		else					 return(-1);
	}
	return(0);
}

/* Ordre entre 2 fonctions elementaires */

int     FonctElementOrdre(FonctElement_Type F,FonctElement_Type G)
{	FonctElement_Type	FC=FonctElementCreer();
 	FonctElement_Type	GC=FonctElementCreer();
 	Entier_Liste		LF=EntierCreer();
 	Entier_Liste            LG=EntierCreer();
 	Entier_Liste		DF=EntierCreer();
 	Entier_Liste            DG=EntierCreer();
	PFonctionClasse		aF,aG;
	int			i;


	EntierDetruit(LF);
	EntierDetruit(LG);
	
	FonctElementDiff1(F,DF);
	FonctElementDiff1(G,DG);
	
	DuplListeAdresse(F,FC);
	DuplListeAdresse(G,GC);
	
	while(true)
	{	aF = (PFonctionClasse)ListePremier(FC);
		aG = (PFonctionClasse)ListePremier(GC);                        
		if (aF==NULL)
		{	ViderListe(FC);DetruireListe(FC);
			ViderListe(GC);DetruireListe(GC);
			EntierDetr(LF);
			EntierDetr(LG);
			EntierDetr(DF);
			EntierDetr(DG); 
			return(0);
		}
		if ((i= Ordre(aF,aG,LF,LG,DF,DG))!=0)
		{	ViderListe(FC);DetruireListe(FC);
			ViderListe(GC);DetruireListe(GC);
			EntierDetr(LF);
			EntierDetr(LG);
			EntierDetr(DF);
			EntierDetr(DG); 
			return(i);
		}	
		EnleveElement(FC,aF);
		EnleveElement(GC,aG);
	}
}

/************************************************************************/
/* Calcul arite d'une fonction 						*/
/************************************************************************/

static  void * ProcFonctElementArite(PFonctionClasse aF,int *i)
{
#ifdef FK_MAC_OS
#pragma unused(aF)
#endif
				*i+=1;
        return(NULL);
}

int	FonctElementArite(FonctElement_Type F)
{	int 	i;

	i=0;
	(void) ParcoursListe(F, (ParcoursProcPtr)ProcFonctElementArite,&i);
	return(i);
}
  
/************************************************************************/
/* Procedure de multiplication				                */
/************************************************************************/
/* Recherche du i eme element */

static	PFonctionClasse RechHandle(PFonctionClasse aF,int *i)
{	if (--*i==0) return(aF);
	else return(NULL);
}

static	PFonctionClasse FonctClasseRech(FonctElement_Type F,int i)
	{
	int j;
	j=i;
	return( (PFonctionClasse) ParcoursListe(F,(ParcoursProcPtr)RechHandle, &j));
	}


/* Liste des variables de diffusion >0 */

static	void * ProcFonctElementDiff(PFonctionClasse aF,Entier_Liste L)
{	if ((aF->typefonction=='d')&&(aF->variable>0))
		(void) EntierAjout(L,aF->variable);	
	return(NULL);
}

static	void	FonctElementDiff(FonctElement_Type F,Entier_Liste L)
{	EntierDetruit(L);
	(void) ParcoursListe(F,
	                (ParcoursProcPtr)ProcFonctElementDiff,L);
}

/* Liste des variables de diffusion <0 double */

static	void *	ProcFonctElementDiff2(PFonctionClasse aF,Entier_Liste L1,
		Entier_Liste L2,int *i)
{	if ((aF->typefonction=='d')&&(aF->variable<0))
	{	if(EntierAjout(L1,aF->variable)==*i+1)
		{	*i+=1;
		}
		else
		{
			(void) EntierAjout(L2,aF->variable);
		}	
	}
	return(NULL);
}	

static 	void	FonctElementDiff2(FonctElement_Type F,Entier_Liste L)
{	Entier_Liste	L1=EntierCreer();
	int		i;

	i=0;
	EntierDetruit(L);
	EntierDetruit(L1);
	(void) ParcoursListe(F, (ParcoursProcPtr)ProcFonctElementDiff2,L1, L, &i);
	EntierDetr(L1); 		
}

/* Preparation de la fonction G pour le calcul de F.G: 	*/
/*	Eviter des fonctions de diffusions communes	*/	

static	void *	ProcFonctElementPrepare(PFonctionClasse aF,
		Entier_Liste L,int pas,int *diff0)
{	if (aF->typefonction=='d')
	{	if (aF->variable<0) aF->variable=aF->variable-pas;	
		else
		{	if (aF->variable==0)
			{	aF->variable=-2*pas-*diff0;
				*diff0+=1;
			}
			else
			{ if (EntierIndice(L,aF->variable)!=-1)
				aF->variable=-3*pas-aF->variable;
			}	
		}	
	}
	return(NULL);
}
		
static	void	FonctElementPrepare(FonctElement_Type F,FonctElement_Type G)
{	int	diff0,pas;
 	Entier_Liste	L=EntierCreer();
	
	pas=FonctElementArite(F)+FonctElementArite(G);
	diff0=0;
	FonctElementDiff(F,L);

	(void) ParcoursListe(G,
	                (ParcoursProcPtr)ProcFonctElementPrepare,L, pas, &diff0);
	EntierDetr(L); 
}




/* Calcul du polynome= le produit des tailles des classes des fonctions	*/
/* de diffusion dans G non utilise dans la composition (variable!=0)	*/

static 	void *	ProcFonctElementMultPoly(PFonctionClasse aG,Poly_Type P,
		Entier_Liste L,int *tailleL)
{	Poly_Type Q=PolyCreer();

	if (aG->typefonction=='d')
 	{	if (EntierAjout(L,aG->variable)==*tailleL+1)
 		{	if (aG->taille>=0) 	PolyEntier(Q,aG->taille);
			else 			PolyMonome(Q,-aG->taille);
			(*tailleL)++;	/* was *tailleL++ */
			PolyMult(Q,P);
 		}	
 	}
 	PolyDetr(Q); 
	return(NULL);	
}

static 	void	FonctElementMultPoly(FonctElement_Type G,Poly_Type P,
		Entier_Liste L)

{	int	tailleL;
	
	tailleL=EntierTaille(L);
	PolyEntier(P,1);
	
	(void) ParcoursListe(G, (ParcoursProcPtr)ProcFonctElementMultPoly, P,L,&tailleL);
}

/* PolyRepare ******************????????????**********/

/* renumerotage des diffusions <0 */

static	void *	ProcFonctElementRepare1(PFonctionClasse aF,Entier_Liste L)
{	int i;

	if ((aF->typefonction=='d')&&(aF->variable<0))
	{	if ((i=EntierIndice(L,aF->variable))!=-1)
		{	aF->variable=-i;
		}	
		else
		{	aF->variable=0;
			aF->successeur=0;
		}	
	}
	return(NULL);
}

static	void	FonctElementRepare1(FonctElement_Type F)
{	Entier_Liste	L=EntierCreer();

	FonctElementDiff2(F,L);
	(void) ParcoursListe(F,
	                (ParcoursProcPtr)ProcFonctElementRepare1,L);
	EntierDetr(L); 
}

/* remise a jour des successeurs associes aux diffusions */

static	void * ProcFonctElementRepare2(PFonctionClasse aF,Entier_Liste L,
		Entier_Liste LS,int *i)
{	int k,s;

	if ((aF->typefonction=='d')&&(aF->variable!=0))
	{	if ((k=EntierAjout(L,aF->variable))==*i+1)
		{	EntierAjoutFin(LS,aF->successeur);
			aF->successeur=0;
			*i+=1;
		}
		else
		{	EntierNom(LS,k,&s);
			aF->successeur+= -s;
		}
	}	
	return(NULL);
}

static	void	FonctElementRepare2(FonctElement_Type F)
{	Entier_Liste L=EntierCreer();
 	Entier_Liste LS=EntierCreer();
	int i;


	EntierDetruit(L);
	EntierDetruit(LS);
	i=0;

	(void) ParcoursListe(F,(ParcoursProcPtr)ProcFonctElementRepare2,L,LS,&i);
	EntierDetr(L);
	EntierDetr(LS);	
}

/* La complete ! */

static	void	FonctElementRepare(FonctElement_Type F)
{	FonctElementRepare1(F);
 	FonctElementRepare2(F);
}	
	

/* Composition d'une fonction de classe */

static	void *	ProcFonctElementMult(PFonctionClasse aF,
			FonctElement_Type G,Entier_Liste L)
{	PFonctionClasse	aG;

	if (aF->typefonction =='i')
	{	aG=FonctClasseRech(G,aF->variable);
        	aF->typefonction =	aG->typefonction;
        	aF->variable 	=	aG->variable;
		aF->successeur	=	aF->successeur+aG->successeur;
		if (aF->taille>0)
			aF->successeur= (aF->taille+aF->successeur) %aF->taille;
		if (aG->typefonction =='d')
			(void)EntierAjout(L,aG->variable);	
	}
	return(NULL);
}
	

/*Composition de fonctions elementaires */

void    FonctElementMult(FonctElement_Type F,FonctElement_Type G,Poly_Type P)
{	FonctElement_Type FC=FonctElementCreer();
	FonctElement_Type GC=FonctElementCreer();
 	Entier_Liste	L=EntierCreer();

	EntierDetruit(L);
	FonctElementDupl(F,FC);
	FonctElementDupl(G,GC);
	FonctElementPrepare(FC,GC);

	(void) ParcoursListe(FC,
	                (ParcoursProcPtr)ProcFonctElementMult,GC,L);

	FonctElementMultPoly(GC,P,L);                

	FonctElementRepare(FC);
	FonctElementDupl(FC,G);

	FonctElementDetr(FC);
	FonctElementDetr(GC);	
	EntierDetr(L); 
}

/************************************************************************/
/* Init a quelques fonctions particuliere				*/
/************************************************************************/

/* Init a une fonction identite */

void	FonctElementIdentite(FonctElement_Type F,
			Entier_Liste DomainePlace,
			Classe_Liste SpecifClasse)
{	int i,n,d;
	PFonctionClasse aF;

	FonctElementUn(F);
	n=EntierTaille(DomainePlace);
	for (i=1;i<=n;i++)
	{	EntierNom(DomainePlace,i,&d);			
		aF = (PFonctionClasse) malloc(sizeof(FonctionClasse));
	        aF->typefonction =	'i';
        	aF->variable 	=	i;
		aF->successeur	=	0;
		aF->classe	=	d;
		aF->taille	=	ClasseTaille(SpecifClasse,d);
	
		AjoutFinListe(F,aF);
	}
}	

/* Init a une fonction constante sans domaine pour inverse generalise*/

void	FonctElementConstante(FonctElement_Type F,
	int NbVariable)

{	int i;
	PFonctionClasse aF;

	FonctElementUn(F);

	for (i=1;i<=NbVariable;i++)
	{	
		aF = (PFonctionClasse) malloc(sizeof(FonctionClasse));
	        aF->typefonction =	'c';
        	aF->variable 	=	1;
		aF->successeur	=	0;
		aF->classe	=	1;
		aF->taille	=	0;
		AjoutFinListe(F,aF);
	}
}	

	
/************************************************************************/
/* Quelques procedures de test				                */
/************************************************************************/

/* test fonction identite! */

static	PFonctionClasse ProcFonctElementTestIdentite(PFonctionClasse aF,FonctElement_Type FI,int *i)

{	PFonctionClasse aFI;

	*i+=1;
	if (aF->typefonction !='i') return(aF);
	
	aFI=FonctClasseRech(FI,aF->variable);		

	if (aFI->typefonction=='i') return(aF);

       	aFI->typefonction =	'i';
       	aFI->variable 	=	*i;
	if (aF->taille>0)
		aFI->successeur= (aF->taille-aF->successeur) %aF->taille;
	else	 aFI->successeur =       -aF->successeur;
	aFI->taille	=	aF->taille;
	aFI->classe	=	aF->classe;
	return(NULL);
}	
	
bool	FonctElementTestIdentite(FonctElement_Type F,FonctElement_Type FI,
		int NbVariable)
{	int i;					

	i=0;
  	FonctElementConstante(FI,NbVariable);
	if (ParcoursListe(F,(ParcoursProcPtr)ProcFonctElementTestIdentite,FI,&i) != NULL) return(false);
	return(true);
}	


/***************************************************************************/

/* Calcul du domaine d'une fonction elementaire */

static	void *	ProcFonctElementDomaine(PFonctionClasse aF,
			Entier_Liste Domaine)
{	if (aF->typefonction=='i')
		(void) EntierAjout(Domaine,aF->variable);
	return(NULL);
}

void	FonctElementDomaine(FonctElement_Type F,Entier_Liste Domaine)
{	(void)ParcoursListe(F,(ParcoursProcPtr)ProcFonctElementDomaine,Domaine);
}        
                




	
/***************************************************************************/
/* Preparatif a la quasi-injectivite */

static	void * ProcFonctElementQuasi(PFonctionClasse aFC,	FonctElement_Type F,int *i)

{	PFonctionClasse aF;

	*i+=1;
	if (aFC->typefonction !='i') return(NULL);
	aF=FonctClasseRech(F,*i);		
	if ((aF->typefonction !='i')|| (aF->variable != aFC->variable)
	|| (aF->successeur != aFC->successeur)) aFC->typefonction='c';
	return(NULL);
}

void	FonctElementQuasi(FonctElement_Type F,FonctElement_Type FC)
{	int i;

	i=0;
	(void) ParcoursListe(FC,(ParcoursProcPtr)ProcFonctElementQuasi,F,&i);
}

/*** Preparatif pour simplification de domaine !!!! */

bool FonctElementTestSimplificationDomaine(FonctElement_Type F,Poly_Type p,
		FonctElement_Type G,int i,
		Entier_Liste DomainePlace,Classe_Liste SpecifClasse)
{	PFonctionClasse aF,aG;

	aF=FonctClasseRech(F,i);
	if (aF->typefonction=='i') return(false);

	FonctElementIdentite(G,DomainePlace,SpecifClasse);
	aG=FonctClasseRech(G,i);
	aG->typefonction=aF->typefonction;
	aG->variable=0;
	aG->successeur=aF->successeur;
	if (aF->typefonction=='c') PolyEntier(p,1);
	if (aF->typefonction=='d')
	{	if (aF->taille==0) return(false);
		if (aF->taille<0) PolyMonome(p,-aF->taille);
		else	PolyEntier(p,aF->taille);
	}
	return(true);	
}

/** Suppression de la ieme composante */
void	VectSupprimeComp(FonctElement_Type F,int i)
{	PFonctionClasse aF;

	aF=FonctClasseRech(F,i);
	EnleveElement(F,aF);
	free(aF);
}	



/************************************************************************/
/* Comparaison de fonctions elementaires f,g				*/
/*	Pour x, il existe y / g.y>=f.x					*/
/* Attention, pas fiable pour les fonctions diffusions			*/
/************************************************************************/

static	void *	ProcFonctElementChangIdent(PFonctionClasse aG,
		int vG,int vF,int k,int *i)
{
	*i+=1;

	if ((aG->typefonction=='i') &&(aG->variable==vG))
	{	aG->variable=vF;
		aG->successeur=aG->successeur+k;
		if (aG->taille>0)
			aG->successeur= (aG->taille+aG->successeur) %aG->taille;
	}
	return(NULL);	
}

static void FonctElementChangIdent(FonctElement_Type G,int vG,int vF,int k)
{	int i;

	i=0;
	(void) ParcoursListe(G,(ParcoursProcPtr)ProcFonctElementChangIdent,vG,vF,k,&i);	
}

static	void *	ProcFonctElementChangConst(PFonctionClasse aG,
		int vG,int k,int *i)
{
	*i+=1;

	if ((aG->typefonction=='i') &&(aG->variable==vG))
	{	aG->typefonction='c';
		aG->successeur=aG->successeur+k;
		if (aG->taille>0)
			aG->successeur= (aG->taille+aG->successeur) %aG->taille;
	}
	return(NULL);
}

		
static void FonctElementChangConst(FonctElement_Type G,int vG,int k)
{	int i;

	i=0;
	(void) ParcoursListe(G,(ParcoursProcPtr)ProcFonctElementChangConst,vG,k,&i);	
}

/*** Un peu de preration ***/
static void * ProcFonctElementPrepareSuperieur(PFonctionClasse aF,
			int NbVariable)
{	if (aF->typefonction=='i') aF->variable+=NbVariable;
	return(NULL);
}

static void FonctElementPrepareSuperieur(FonctElement_Type F,int NbVariable)
{	(void) ParcoursListe(F,(ParcoursProcPtr)ProcFonctElementPrepareSuperieur,
                NbVariable);
}	



static	void *	ProcFonctElementSuperieur(PFonctionClasse aF,
				FonctElement_Type G,int *i)
{	PFonctionClasse aG;
	int k;
	
	*i+=1;
	if (aF->typefonction=='i')
	{	aG=FonctClasseRech(G,*i);
		switch(aG->typefonction){
		case 'i': /* Faire changement de variable dans G */
				k=aF->successeur-aG->successeur;
				FonctElementChangIdent(G,aG->variable,
					aF->variable,k);
				return(NULL);	
				break;	
		case 'c': /* Fin */
				return(aF);
				break;
		case 'd': /* Mettre aF a diffusion */
				aF->typefonction='d';
				aF->variable=0;
				aF->successeur=0;
				return(NULL);
				break;
		}		
	}

	if (aF->typefonction=='c')
	{	aG=FonctClasseRech(G,*i);
		switch(aG->typefonction){
		case 'i': /* Faire changement de variable dans G */
				k=aF->successeur-aG->successeur;
				FonctElementChangConst(G,aG->variable,k);
				return(NULL);
				break;	
		case 'c': /* Fin */
				if (aF->successeur==aG->successeur)
					return(NULL);
				else	
					return(aF);
				break;
		case 'd': /* Mettre aF a diffusion */
				aF->typefonction='d';
				aF->variable=0;
				aF->successeur=0;
				return(NULL);
				break;
		}		
	}
	return(NULL);
}


bool	FonctElementSuperieur(FonctElement_Type F,FonctElement_Type G,
	int NbVariable)
{	int i;
	FonctElement_Type FC=FonctElementCreer();
	FonctElement_Type GC=FonctElementCreer();
 	bool	b;

	/* Faire les copies de F,G */
	FonctElementDupl(F,FC);
	FonctElementDupl(G,GC);

	/* FC,GC pas les memes variables */
	FonctElementPrepareSuperieur(FC,NbVariable);

	/* Substitution et comparaison ? */
	i=0;
	if (ParcoursListe(FC,(ParcoursProcPtr)ProcFonctElementSuperieur,GC,&i)!=NULL)
		b=false;
	else
	/* Comparaison des 2 fonctions */
	{	if (FonctElementOrdre(FC,GC)==0) b=true;
		else				b=false;
	}
	FonctElementDetr(FC);
	FonctElementDetr(GC); 
	return(b);
}
				


/************************************************************************/
/* Procedure d'entree d'une fonctions elementaires                      */
/************************************************************************/
 
static bool FonctionClasseEntre(PFonctionClasse fc,char *f,
		Classe_Liste SpecifClasse,int IndClasse, Var_Liste NomVariable)
{	Var_Type v;
	char	*g;

	fc->classe=IndClasse;
	fc->taille=ClasseTaille(SpecifClasse,IndClasse);


/* fonction de diffusion */
	if (strncmp(f,"ALL.",4)==0) 
	{	fc->typefonction='d';
		if ((g=strchr(f,'+'))==0)
		{	if  ((g=strchr(f,'-'))==0)
			{	strcpy(v,f+4);
				if((fc->variable=VarIndice(NomVariable,v))==-1)
					fc->variable=0;
				fc->successeur=0;
				return(true);
			}
		}
		if (g!=0)
		{	if (g[0]!=g[1]) return(false);
			strncpy(v,f+4,g-f-4);strcat(v,"\0");
			if ((fc->variable=VarIndice(NomVariable,v))==-1)
				return(false);
			sscanf(g+1,"%d",&fc->successeur);
		}
		if ((strchr(g+2,'+')==0) && (strchr(g+2,'-')==0))
			return(true);
		else	return(false);	


	}

/* fonction identite */
 
		fc->typefonction='i';
		if ((g=strchr(f,'+'))==0)
		{	if  ((g=strchr(f,'-'))==0)
			{	strcpy(v,f);
				if((fc->variable=VarIndice(NomVariable,v))!=-1)
				{	fc->successeur=0;
					return(true);
				}	
			}
		}
		if (g!=0)
	 	{       if (g[0]!=g[1]) return(false);
			strncpy(v,f,g-f);v[g-f]='\0';
			if ((fc->variable=VarIndice(NomVariable,v))==-1)
				return(false);
			sscanf(g+1,"%d",&fc->successeur);
			if ((strchr(g+2,'+')==0) && (strchr(g+2,'-')==0))
				return(true);
			else	return(false);
		}	


/* fonction constante */ 
 
		fc->typefonction='c';
		fc->variable=0;
		sscanf(f,"%s",v);
		fc->successeur=ClasseConstanteEntre(SpecifClasse,v,IndClasse);
		return(true);
}

		
bool FonctElementEntre(FonctElement_Type F,char *f,Classe_Liste SpecifClasse,
			Var_Liste NomVariable,Entier_Liste Domaine)

{	char	*gd,*gf;
	Var_Type v;
	int i,IndClasse;
	bool	b;
	PFonctionClasse fc;	

	FonctElementUn(F);
	b=true;

	i=1;
	
	gd=strchr(f,'<');
	if (strncmp(gd,"<>",2)!=0)
	while (gd[0]!='>')
	{	gf=strchr(gd+1,' ');
		if (gf==0) gf=strchr(gd+1,'>');
		strncpy(v,gd+1,gf-gd-1);v[gf-gd-1]='\0';
	

		EntierNom(Domaine,i,&IndClasse);
		fc=(PFonctionClasse)malloc(sizeof(FonctionClasse));
		if (!(FonctionClasseEntre(fc,v,SpecifClasse,IndClasse,
				NomVariable))) return(false);
		AjoutFinListe(F,fc);
		i++;
		gd=gf;
	}
	return(true);	
}

/************************************************************************/
/* Procedure de sortie d'une fonctions elementaires                     */
/************************************************************************/
 
static void * FonctionClasseSort(PFonctionClasse fc,char *f,
		Classe_Liste SpecifClasse, Var_Liste NomVariable,
		Var_Liste NomClasse)
{	Var_Type v;

	switch(fc->typefonction)
	{
/* fonction identite */
	case 'i': 	
			VarNom(NomVariable,fc->variable,&v);
		  	strcat(f,v);
			if (fc->successeur!=0)
		  	{	if (fc->successeur>0)
					strcat(f,"++");
				else	strcat(f,"-");	
		  		strcpy(v,"");
				sprintf(v,"%d",fc->successeur);
				strcat(f,v);
			}
			break;
/* fonction constante */
	case 'c':	VarNom(NomClasse,fc->classe,&v);
			strcat(f,v);
			strcat(f,".");
			ClasseConstanteSort(SpecifClasse,
					v,fc->classe,fc->successeur);
			strcat(f,v);
			break;

/* fonction de diffusion */
	case 'd':	
			if (fc->variable>0)
			{	VarNom(NomVariable,fc->variable,&v);
				strcat(f,v);
			}
			else
			{	VarNom(NomClasse,fc->classe,&v);
				strcat(f,v);
				if (fc->variable<0)
				{	sprintf(v,"%d",-fc->variable);
					strcat(f,v);
				}
			}	
			
                        strcat(f,".ALL");
			if (fc->successeur!=0)
			{	if  (fc->successeur <0)
					strcat(f,"-");
				else
					strcat(f,"++");
				sprintf(v,"%d",fc->successeur);		
				strcat(f,v);		
			}
			break;
	}
	strcat(f,",");
	return(NULL);
}                       
			

void FonctElementSort(FonctElement_Type F,char *f,Classe_Liste SpecifClasse,
		Var_Liste NomVariable, Var_Liste NomClasse)

{	/*Var_Type v; jlm */
	int	i;

	strcpy(f,"<");
	ParcoursListe(F,(ParcoursProcPtr)FonctionClasseSort,
				f,SpecifClasse,
				NomVariable, NomClasse);

	if ((i=strlen(f))==1) 	strcpy(f,"<>");
	else			f[i-1]='>';			
}


