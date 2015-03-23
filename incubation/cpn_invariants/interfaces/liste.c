/************************************************************************/
/* Module permet de manipuler une liste L d'objet	   		*/
/*									*/
/*									*/
/* Sur une liste sont definies les operations suivantes:		*/
/*									*/
/*	TYPELISTE CREER(): Creation d'une liste				*/
/*									*/
/*	(void) DETRUIT(TYPELISTE L) : Vide une liste			*/
/*									*/
/*	int TAILLE(TYPELISTE L): Donne la Taille de L			*/
/*									*/
/*      int INDICE(TYPELISTE L,TYPEOBJET v) : Recherche v dans L et 	*/
/*              renvoie l'indice de v.                                  */
/*              Si v n'est pas dans L, on renvoie -1                    */
/*                                                                      */
/*	int AJOUT(TYPEOBJET L,TYPEOBJET v) : Ajoute la variable v 	*/
/*		dans la liste L et renvoie l'indice de v dans L.	*/
/*		Si v est deja dans L, v  n'est pas rajoute.		*/
/*		Si v n'est pas dans L, v est mis en fin de liste	*/
/*									*/
/*	(void) SUPPRIME(TYPELISTE L,int i) : Supprime la variable	*/
/*		d'indice i dans L. L'indice des variables superieures	*/
/*		sont decrementees de 1.					*/
/*									*/
/*	(void) NOM(TYPELISTE L,int i,TYPEOBJET v): Calcule v comme la	*/
/*		la variable d'indice i dans L. 				*/
/*		Si i est superieur a la taille de la liste, v=NULL.	*/
/*									*/
/*	int 	AJOUTFIN(TYPELISTE L,TYPEOBJET v)			*/
/************************************************************************/


#include	"liste.h"

#include <stdlib.h>

/********** DETRUIT: Destruction d'une liste **************************/

static void * Detruit(PObjet vptr)
{	free(vptr);
	vptr=NULL;
	return(NULL);
}

void 	DETRUIT(TYPELISTE L)
{	(void) ParcoursListe(L,(ParcoursProcPtr)Detruit);
	ViderListe(L);
}
/********* BIGDETRUIT **************************************************/
#ifdef BIGDETRUIT

void	BIGDETRUIT(TYPELISTE L)
{	DETRUIT(L);
	DetruireListe(L);
}
#endif	


/********** VarCreer:  Creation d'une liste *****************************/

TYPELISTE CREER()
{	return( (TYPELISTE) CreerListe());
}


/********** TAILLE: Taille d'une liste ******************************/

static void * Compteur(PObjet vptr,int *i)
{
	*i+=1;
	return(NULL);
}

 
int 	TAILLE(TYPELISTE L)
{	int i;
	if (ListePremier(L)==NULL) return(0);
	else
	{i=0;
	(void) ParcoursListe(L,(ParcoursProcPtr)Compteur, &i);
	return(i);
	}
}

/********** i = INDICE(L,v) *****************************************/

static void * RechIndice(PObjet vptr,TYPEOBJET v,int *i)
{	if (EGAL(*vptr,v)) return(vptr);
	else
	{	*i+=1;
		return(NULL);
	}
}

int 	INDICE(TYPELISTE L,TYPEOBJET v)
{	int	i;
	if (ListePremier(L)==NULL) return(-1);
	else{ 
	i = 1;
	if (ParcoursListe(L,(ParcoursProcPtr)RechIndice, v, &i)==NULL)
	return(-1);
	else return(i);
	}
}
	

/********** i = AJOUT(L,v): Ajout de v dans L, i = Indice(v) dans L	*/
#ifdef AJOUT
int 	AJOUT(TYPELISTE L,TYPEOBJET v)
{	int	i;
	PObjet vptr;
	if ((i=INDICE(L,v))!=-1) return(i);
	else
	{	vptr = (PObjet) malloc(sizeof(TYPEOBJET));
		DUPL(v,*vptr);
		AjoutFinListe(L, vptr);
		return(TAILLE(L));
         }
}
#endif

/********* SUPPRIME(L,i) : Suppression de la variable d'indice i *****/

static void * RechHandle(PObjet vptr,int *i)
{	if (--*i==0) return(vptr);
	else return(NULL);
}
	
void 	SUPPRIME(TYPELISTE L,int i)
{	PObjet	vptr;
	if (0<i)
	{
		vptr=(PObjet)ParcoursListe(L,(ParcoursProcPtr)RechHandle, &i);
		if (vptr != NULL)
		{	EnleveElement(L, vptr);	
			free(vptr);
		}
	}
}

/********* NOM(L,i,v) : v=L(i) ***************************************/

void 	NOM(TYPELISTE L,int i,TYPEOBJET *v)
{       PObjet vptr;
	int j;

	j=i;
	if (0<j)
	{vptr= (PObjet) ParcoursListe(L,(ParcoursProcPtr)RechHandle, &j);
        if (j== 0)
	       	DUPL(*vptr,*v);
        
        else 	RIEN(*v);
	}
	else  RIEN(*v);
}

/********* i=AJOUTFin(L,v) ******************************************/

int 	AJOUTFIN(TYPELISTE L,TYPEOBJET v)
{	PObjet vptr;
	
	vptr = (PObjet) malloc(sizeof(TYPEOBJET));
	DUPL(v,*vptr);
	AjoutFinListe(L, vptr);
	return(TAILLE(L));
} 



/********* Inclusion L1 < L2 ****************************************/ 

#ifdef INCLUSLISTE

static void * ProcInclusListe(PObjet vptr, TYPELISTE L2)
{	if (INDICE(L2,*vptr)==-1) return(vptr);
	return(NULL);
}


bool	INCLUSLISTE(TYPELISTE L1,TYPELISTE L2)
{
	if (ParcoursListe(L1,(ParcoursProcPtr)ProcInclusListe, L2)==NULL)
		return(true);
	return(false);
}	

bool	EGALLISTE(TYPELISTE L1,TYPELISTE L2)
{	if (INCLUSLISTE(L1,L2)) return(INCLUSLISTE(L2,L1));
	return(false);
}

#endif

#ifdef DISJOINTLISTE

static void * ProcDisjointListe(PObjet vptr, TYPELISTE L2)
{	if (INDICE(L2,*vptr)==-1) return(NULL);
	return(vptr);
}


bool	DISJOINTLISTE(TYPELISTE L1,TYPELISTE L2)
{
	if (ParcoursListe(L1,(ParcoursProcPtr)ProcDisjointListe, L2)==NULL)
		return(true);
	return(false);
}	

#endif


