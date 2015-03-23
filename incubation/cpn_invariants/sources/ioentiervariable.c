#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */


#include "CListes.h"

#include "taillechaine.h"
#include "entiervariable.h"
#include	"variable.h"

#include	"ioentiervariable.h"

bool	EntierEntre(Entier_Liste L,char *f)
{	int v;
	char	*g;

	EntierDetruit(L);
	if (f[0]=='\0') return(true);
	g=f;
	do
	{	if (*g==' ') ++g;
		if (strcmp(g,"")==0) return(true);
		sscanf(g,"%d",&v);
		(void) EntierAjoutFin(L,v);
		g=strchr(g,' ');
	}
	while(g!=0);
	return(true);
}

bool	EntierEntreMax(Entier_Liste L,char *f,int *n)
{	int v;
	char	*g;

	EntierDetruit(L);
	if (f[0]=='\0') return(true);
	g=f;
	do
	{	if (*g==' ') ++g;
		if (strcmp(g,"")==0) return(true);
		sscanf(g,"%d",&v);
		if (v>=*n) *n=v+1;
		(void) EntierAjoutFin(L,v);
		g=strchr(g,' ');
	}
	while(g!=0);
	return(true);
}

void	EntierSort(Entier_Liste L,char *f)
{	Var_Type vc;
	int v;
	int i,n;

	strcpy(f,"");
	n=EntierTaille(L);
	if (n!=0)
	{	for (i=1;i<n;i++)
		{	EntierNom(L,i,&v);
			sprintf(vc,"%d",v);
			strcat(f,vc);
			strcat(f," ");
		}
		EntierNom(L,i,&v);
		sprintf(vc,"%d",v);
		strcat(f,vc);
	}
}	
