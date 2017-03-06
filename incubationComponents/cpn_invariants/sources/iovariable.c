#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */


#include "CListes.h"

#include "taillechaine.h"
#include "variable.h"
#include	"iovariable.h"

bool	VarEntre(Var_Liste L,char *f)
{	Var_Type v;
	char	*g;

	VarDetruit(L);
	if (f[0]=='\0') return(true);
	g=f;
	do
	{	if (*g==' ') g++;
		sscanf(g,"%s",v);
		(void) VarAjout(L,v);
		g=strchr(g,' ');
	}
	while(g!=0);
	return(true);
}

bool	VarChiffreEntre(Var_Liste L,char *f)
{	Var_Type v;
	char	*g,*gf;
	int	n;
/*	char	c; */

	VarDetruit(L);
	if (f[0]=='\0') return(true);
	g=f;
	do
	{	if (*g==' ') g++;
		gf=strchr(g,' ');
		if (gf==0) n=0;
		else
		{	strncpy(v,g,gf-g);v[gf-g]='\0';
			sscanf(v,"%d",&n);
		}	
		if (n!=0)
		{	
			g=gf;
			strncpy(v,g+1,n);v[n]='\0';
			g=g+n;
		}	
		else
		{	 g=g+2;
			strcpy(v,"");
		}
	(void) VarAjoutFin(L,v);
	g=strchr(g,' ');
	}
	while(g!=0);
	return(true);
}

void	VarSort(Var_Liste L,char *f)
{	Var_Type v;
	int i,n;

	strcpy(f,"");
	n=VarTaille(L);
	if (n!=0)
	{	for (i=1;i<n;i++)
		{	VarNom(L,i,&v);
			strcat(f,v);
			strcat(f," ");
		}
		VarNom(L,i,&v);
		strcat(f,v);
	}	
}
