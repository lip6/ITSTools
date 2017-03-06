#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/************************************************************************/
/* Module permet de manipuler une liste L de variables v   		*/
/*									*/
/* Une variable est une chaine de caracteres de taille VARMAX		*/
/*									*/
/* Sur une liste sont definies les operations suivantes:		*/
/*									*/
/*	VarListe VarCreer(): Creation d'une liste			*/
/*									*/
/*	(void) VarDetruit(Var_Liste L) : Detruit une liste		*/
/*									*/
/*	int VarTaille(Var_Liste L): Donne la Taille de L		*/
/*									*/
/*      int VarIndice(Var_Liste L,Var_Type v) : Recherche v dans L et   */
/*              renvoie l'indice de v.                                  */
/*              Si v n'est pas dans L, on renvoie -1                    */
/*                                                                      */
/*	int VarAjout(Var_Liste L,Var_Type v) : Ajoute la variable v dans*/
/*		la liste L et renvoie l'indice de v dans L.		*/
/*		Si v est deja dans L, v  n'est pas rajoute.		*/
/*		Si v n'est pas dans L, v est mis en fin de liste	*/
/*									*/
/*	(void) VarSupprime(Var_Type L,int i) : Supprime la variable	*/
/*		d'indice i dans L. L'indice des variables superieures	*/
/*		sont decrementees de 1.					*/
/*									*/
/*	(void) VarNom(Var_Type L,int i,Var_Type v): Calcule v comme la	*/
/*		la variable d'indice i dans L. 				*/
/*		Si i est superieur a la taille de la liste, v=NULL.	*/
/*									*/
/************************************************************************/

#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */


#include "CListes.h"

#include "taillechaine.h"
#include "variable.h"

#define		TYPEOBJET	Var_Type
#define		TYPELISTE	Var_Liste
#define   CREER			VarCreer
#define		DETRUIT		VarDetruit
#define   TAILLE		VarTaille
#define		INDICE		VarIndice
#define		AJOUT			VarAjout
#define		SUPPRIME	VarSupprime
#define		NOM				VarNom
#define		AJOUTFIN	VarAjoutFin

#define		DUPL(a,b)	strcpy(b,a)
#define		EGAL(a,b)	strcmp(a,b)==0
#define		RIEN(a)		strcpy(a,"")

#include "liste.c"

#include	"variable.h"

/* initialisation d'une liste a x1,x2 .... xn */

void 	VarInitxi(Var_Liste L,int n)
{	Var_Type v,w;
	int i;
	
	for (i=1;i<=n;i++)
	{	strcpy(v,"x");
		strcpy(w,"");
		sprintf(w,"%d",i);
		strcat(v,w);
		(void) VarAjoutFin(L,v);
	}
}	
