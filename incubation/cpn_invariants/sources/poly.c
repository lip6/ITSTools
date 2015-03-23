#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/* Quelques fichiers utiles */

#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "CListes.h"

/* Type et operations sur les monomes */
#include "monome.h"
/* Specification des polynomes */
#include "comppoly.h"
/* Le final !!!!n */
#include "composition.c"
#include "compositionmulti.c"

#include	"poly.h"
/*********************************************/
/* Quelques procedures de changement de type */
/*********************************************/

/* entier -> polynome */

void    PolyEntier(Poly_Type p,int i)
{	PCoType	aF;

	PolyZero(p);
	aF=(PCoType)malloc(sizeof(CoType));
	aF->coef=i;
	aF->elt =MoCreer();
	AjoutListe(p,aF);
}	


/* variable -> polynome */

void    PolyMonome(Poly_Type p,int i)
{	PCoType	aF;

	PolyZero(p);
	aF=(PCoType)malloc(sizeof(CoType));
	aF->coef=1;
	aF->elt =MoCreer();
	MoVariable(aF->elt,i);
	AjoutListe(p,aF);
}	

/*********************************************/
/* procedure test a 1                        */
/*********************************************/

bool	PolyTestUn(Poly_Type p)
{	PCoType aF;

	if(!UnSeulElement(p)) return(false);
	aF=(PCoType)ListePremier(p);
	if (!MoTestUn(aF->elt)) return(false);
	if (aF->coef!=1) return(false);
	return(true);
}	

bool	PolyTestNegatif(Poly_Type p)
{	PCoType aF;

	if(!UnSeulElement(p)) return(false);
	aF=(PCoType)ListePremier(p);
	if (!MoTestUn(aF->elt)) return(false);
	if (aF->coef>0) return(false);
	return(true);
}	


/************************************************/
/* Comparaison de 2 polynomes p1<=p2         	*/
/*  On ne gere que le cas p1 et p2 entiers.   	*/
/*  Si pas entier revoie false			*/
/************************************************/

bool PolySuperieur(Poly_Type p1,Poly_Type p2)
{	PCoType	aF1,aF2;

	if(!UnSeulElement(p1)) return(false);
	if(!UnSeulElement(p2)) return(false);
	aF1=(PCoType)ListePremier(p1);
	if (!MoTestUn(aF1->elt)) return(false);
	aF2=(PCoType)ListePremier(p2);
	if (!MoTestUn(aF2->elt)) return(false);
	if (aF2->coef>aF1->coef) return(false);
	return(true);
}
/************************************************/

