#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */


#include "CListes.h"
#include	"iofichier.h"

bool LireDonnees(char *f,FILE *FichierDonnees)
{	char c;
	int i;

	i=0;
	strcpy(f,"");
	
	do
	{	fscanf(FichierDonnees,"%c",&c);
	}
	while ((c!='[')&&(c!='\n'));

	if (c=='\n') return(false);
	
	do
	{	fscanf(FichierDonnees,"%c",&c);
		f[i]=c;
		i++;		
	}
	while ((c!=']')&&(c!='\n'));

	if (c=='\n') return(false);
	f[i-1]='\0';
	return(true);
} 
