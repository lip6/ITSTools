#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "CListes.h"

#include "monome.h"
#include "compmonome.h"
#include "iocomposition.c"
#include "taillechaine.h"
#include "variable.h"
#include	"iomonome.h"

bool MoEntre(Mo_Type m,char *f,Var_Liste L)
{	Var_Type ve,vc;
	int 	e,c;
	
	char *g,*gp,*gm;

	MoUn(m);
	g=f;
	do
	{	if (*g==' ') ++g;
		if (strlen(g)==0) return(true);	/* fin */
		gp=strchr(g,'^');
		gm=strchr(g,'*');
		if (gp==0)		/* plus de puissance */
		{	if (gm==0)	/* 1 variable        */
			{	strcpy(ve,g);
				if((e=VarIndice(L,ve))==-1) return(false);
				c=1;
				Entre(m,c,e);
				return(true);
			}
			else		/* n variables       */
			{	strncpy(ve,g,gm-g);ve[gm-g]='\0';
				if((e=VarIndice(L,ve))==-1) return(false);
				c=1;
				Entre(m,c,e);
			}
		}
		else			/* encore des puissances */
		{	if (gm==0)	/* 1 variable + 1 puissance */
			{	strncpy(ve,g,gp-g);ve[gp-g]='\0';
				if((e=VarIndice(L,ve))==-1) return(false);
				strcpy(vc,gp+1);
				sscanf(vc,"%d",&c);
				Entre(m,c,e);
				return(true);
			}
			if (gp<gm)	/* 1 variable + 1 puissance a lire */
			{	strncpy(ve,g,gp-g);ve[gp-g]='\0';
				if((e=VarIndice(L,ve))==-1) return(false);
				strncpy(vc,gp+1,gm-gp-1);vc[gm-gp-1]='\0';
				sscanf(vc,"%d",&c);
				Entre(m,c,e);
			}
			else		/* 1 variable a lire */
			{	strncpy(ve,g,gm-g);ve[gm-g]='\0';
				if((e=VarIndice(L,ve))==-1) return(false);
				c=1;
				Entre(m,c,e);
			}
		}
	g=gm+1;
	}
	while(g!=0);
	return(true);
}


void MoSort(Mo_Type m,char *f,Var_Liste L)
{       Var_Type ve,vc;
	int e,c;
        int i,n;

        strcpy(f,"");
	strcpy(vc,"");        
        n=Taille(m);
        if (n!=0)
        {       for (i=1;i<=n;i++)
                {	(void) Sort(m,&c,&e,i);
			VarNom(L,e,&ve);
                        strcat(f,ve);
                        if (c!=1)
                        {	strcat(f,"^");
                                sprintf(vc,"%d",c);
				strcat(f,vc);	
                        }		
                        if (i<n) strcat(f,"*");
                }
         }
}

