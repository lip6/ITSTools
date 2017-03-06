#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/* Quelques fichiers utiles */

#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "CListes.h"

#include "compmonome.h"
#include "composition.c"
#include	"monome.h"

/* variable -> monome */

void    MoVariable(Mo_Type m,int i)
{	PCoType	aF;

	MoUn(m);
	aF=(PCoType)malloc(sizeof(CoType));
	aF->coef=1;
	aF->elt =i;
	AjoutListe(m,aF);
}	

