#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "CListes.h"

#include "taillechaine.h"
#include "entiervariable.h"
#include "ioentiervariable.h"
#include	"iofichier.h"
#include	"variable.h"

#include	"iodomaine.h"

void DomEntre(Entier_Liste domaine,FILE *donnees)
{	int i;
	Var_Type f;
	char *g;

	EntierDetruit(domaine);	
	(void) LireDonnees(f,donnees);
	sscanf(f,"%d",&i);
	if (i!=0)
	{	g=strchr(f,' ');
		(void) EntierEntre(domaine,g);
	}
}	
