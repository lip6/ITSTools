#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/************************************************************************/
/* Module permet de manipuler une liste L d'entier	  		*/
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

#include <stdio.h>
#include <string.h>

#include "CListes.h"


#include "entiervariable.h"

#define		TYPEOBJET	int
#define		TYPELISTE	Entier_Liste
#define    CREER		EntierCreer
#define		DETRUIT		EntierDetruit
#define		BIGDETRUIT	EntierDetr

#define   TAILLE		EntierTaille
#define		INDICE		EntierIndice
#define		AJOUT			EntierAjout
#define		SUPPRIME	EntierSupprime
#define		NOM				EntierNom
#define		AJOUTFIN	EntierAjoutFin
#define		INCLUSLISTE	EntierInclus
#define		EGALLISTE	EntierEgal
#define		DISJOINTLISTE	EntierDisjoint

#define		DUPL(a,b)	b=a
#define		EGAL(a,b)	(a==b)
#define		RIEN(a)		a=0

#include "liste.c"

/********* Initialisation d'une liste a 1 2 3 ....n **************/

void	EntierInit(Entier_Liste L,int n)
{	int i;

	EntierDetruit(L);
	for (i=1;i<=n;i++)
	{	EntierAjoutFin(L,i);
	}
}		
