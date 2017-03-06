#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/************************************************************************/
/* Module permet de manipuler une liste L de domaine	   		*/
/*									*/
/*									*/
/* Sur une liste sont definies les operations suivantes:		*/
/*									*/
/*	Dom_Liste DomCreer(): Creation d'une liste			*/
/*									*/
/*	(void) DomNom(Dom_Liste L,int i,Liste_Entier *v):		*/
/*		Calcule v comme la					*/
/*		la variable d'indice i dans L. 				*/
/*		Si i est superieur a la taille de la liste, v=NULL.	*/
/*									*/
/*	int 	DomAjoutFin(Dom_Liste L,Liste_Entier *)			*/
/************************************************************************/

#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */
#include "CListes.h"

#include "entiervariable.h"
#include "domaine.h"

/********** VarCreer:  Creation d'une liste *****************************/

Dom_Liste DomCreer(void)
	{
	return((Dom_Liste)CreerListe());
	}

/********* DomNom(L,i,v) : v=L(i) ***************************************/
static Entier_Liste RechHandle(Entier_Liste vptr,int *i)
	{
	if (--*i==0) return(vptr);
	else return(NULL);
	}

void 	DomNom(Dom_Liste L,int i,Entier_Liste *v)
	{
	int j;
	j=i;
	*v= (Entier_Liste) ParcoursListe(L,(ParcoursProcPtr)RechHandle, &j);
	}

/********* i=AJOUTFin(L,v) ******************************************/
void 	DomAjoutFin(Dom_Liste L,Entier_Liste v)
{
AjoutFinListe(L,v);
} 
