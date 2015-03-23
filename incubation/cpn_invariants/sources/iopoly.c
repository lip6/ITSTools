#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/* Quelques fichiers utiles */
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "CListes.h"

/* Operations sur les listes !! */
#include "taillechaine.h"
#include "variable.h"
/* Les Monomes */
#include "monome.h"
#include "iomonome.h"
/* Operations sur les polynomes */
#include "poly.h"
/* Operations de bases io sur les polys */
#include "comppoly.h"
#include "iocomposition.c"

#include	"iopoly.h"

bool PolyEntre(Poly_Type p,char *f,Var_Liste L)
{	Var_Type ve,vc;
	Mo_Type	m;
	int 	c;
	
	char *g,*gd,*gf;

	PolyZero(p);
	g=f;
	do
	{	if (*g==' ') ++g;
		if (strlen(g)==0) return(true);	/* fin */
		gd=strchr(g,'(');
		gf=strchr(g,')');
		if (gd!=0)		/* coef (monome) */
		{	strncpy(vc,g,gd-g);vc[gd-g]='\0';
			sscanf(vc,"%d",&c);
			strncpy(ve,gd+1,gf-gd-1);ve[gf-gd-1]='\0';
			m=MoCreer();
			if(!MoEntre(m,ve,L))
			{	MoDetr(m);
				return(false);
			}	
			Entre(p,c,m);
		}
		else 			/* le poly = 1 entier */
		{ 	sscanf(f,"%d",&c);
			m=MoCreer();
			MoUn(m);
			Entre(p,c,m);
			return(true);
		}
	g=gf+1;
	}
	while(g!=0);
	return(true);
}


void PolySort(Poly_Type p,char *f,Var_Liste L)
{       Var_Type ve,vc;
	Mo_Type	m;
	int c;
        int i,n;

        strcpy(f,"");
	strcpy(vc,"");        
        n=Taille(p);
        if (n!=0)
        {       for (i=1;i<=n;i++)
                {	(void) Sort(p,&c,&m,i);
                	if (MoTestUn(m))
			{	if ( (c>0)&&(i>1)) strcat(f,"+");
				sprintf(vc,"%d",c);
				strcat(f,vc);
			}
                	else
                	{	MoSort(m,ve,L);
                		if ((c>0)&&(i>1)) strcat(f,"+");
				if ((c!=-1)&&(c!=1))
				{ 	sprintf(vc,"%d",c);
					strcat(f,vc);
					strcat(f,"*");
				}
				if (c==-1)  strcat(f,"-");
				strcat(f,ve);
			}
		}
	}
}
