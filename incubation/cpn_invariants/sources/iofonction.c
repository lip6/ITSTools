#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/* Quelques fichiers utiles */
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */


#include "CListes.h"

/* Operations sur les listes !! */
#include "taillechaine.h"
#include "entiervariable.h"
/* Les Monomes */
#include "monome.h"
/*#include "iomonome.h"*/
/* Operations sur les polynomes */
#include "poly.h"
#include "iopoly.h"
/* Operations sur les classes */
#include "classe.h"
/* Operations sur les fonctions elementaires */
#include "fonctionelementaire.h"
/* Operations sur les fonctions */
#include "fonction.h"
/* Operations de bases io sur les fonctions */
#include "compfonction.h"
#include "iocomposition.c"
#include	"iofonction.h"

bool FonctEntre(Fonct_Type F,char *f,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
                Entier_Liste Domaine)                              
{	FonctElement_Type Fe;
	Poly_Type p; 

	Var_Type ve,vc;
/*	int k; jlm */
	
	char *g,*gd,*gf;

	FonctZero(F);
	if (strncmp(f,"<>",2)==0) return(true); /*fonction nulle*/
		
	if (EntierTaille(Domaine)==0) 	/* fonction ordinaire */
	{	gd=strchr(f,'<');
		gf=strchr(f,'>');
		strncpy(vc,gd+1,gf-gd-1);ve[gf-gd-1]='\0';
		p=PolyCreer();
		if (!PolyEntre(p,vc,NomParametre))
			return(false);
		Fe=FonctElementCreer();
		FonctElementUn(Fe);
		Entre(F,p,Fe);
		return(true);
	}
	
	g=f; 				/* Fonction coloree */	
	do
	{	if (*g==' ') ++g;
		if (strlen(g)==0) return(true);	/* fin */
		gd=strchr(g,'<');
		gf=strchr(g,'>');
		if (gd!=0)		/* coef <fonct> */
		{	strncpy(vc,g,gd-g);vc[gd-g]='\0';
			p=PolyCreer();
			if (!PolyEntre(p,vc,NomParametre))
				return(false);
			strncpy(ve,gd,gf-gd+1);ve[gf-gd+1]='\0';
			Fe=FonctElementCreer();
			if(!FonctElementEntre(Fe,ve,SpecifClasse,
			NomVariable,Domaine))
				return(false);
			Entre(F,p,Fe);
		}
		g=strchr(g,'&');
		if (g[1]!='&') return(false);
		if (strlen(g)==2) return(true);
		g+=2;
	}
	while(true);
	return(true);
}

void FonctSort(FonctElement_Type F,char *f,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
                Var_Liste NomClasse)

{       Var_Type ve,vc;
 	Poly_Type p;
 	FonctElement_Type Fe;
        int i,n;

        strcpy(f,"");
	strcpy(vc,"");        
        n=Taille(F);
        if (n!=0)
        {       for (i=1;i<=n;i++)
                {	(void) Sort(F,&p,&Fe,i);
                	if (MoTestUn(Fe))
			{	PolySort(p,vc,NomParametre);
				strcat(f,vc);
			}
                	else
                	{	PolySort(p,vc,NomParametre);
                		if ((strchr(vc+1,'+')==0) &&
                		(strchr(vc+1,'-')==0))
                		{ /* poly= 1 composante */
                			if ((vc[0]!='-')&&(i>1))
						strcat(f,"+");
                			if (strcmp(vc,"1")!=0)
                			{ if (strcmp(vc,"-1")==0)
						strcat(f,"-");
					  else
						strcat(f,vc);
					}	
				}
				else
				{	if (i>1) strcat(f,"+");
					strcat(f,"(");
					strcat(f,vc);
					strcat(f,")");
				}
				FonctElementSort(Fe,ve,SpecifClasse,
					NomVariable,NomClasse);;
				strcat(f,ve);
			}
		}
	}
}
