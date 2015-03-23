#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#define	__iovecteur__
/* Quelques fichiers utiles */
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */


#include "CListes.h"

/* Type sur les listes !! */
#include "taillechaine.h"
#include "variable.h"
#include "entiervariable.h"
/* Type sur les classes */
#include "classe.h"
/* Operations sur les fonctions */
#include "fonction.h"
#include "iofonction.h"
/* Operation sur les vecteurs */
#include "vecteur.h"
/* Operations de bases io sur les fonctions */
#include "compvecteur.h"
#include "iocomposition.c"
/* Operation sur fichier */
#include "iofichier.h"
#include	"iovecteur.h"

bool VectEntre(Vect_Type V,FILE *FichierDonnees,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
                Entier_Liste Domaine)                              
{	PCoType	FT;
	Fonct_Type F;
	Var_Type f;
	int transition;

	VectZero(V);
	transition=1;
	while (LireDonnees(f,FichierDonnees))
	{	F=FonctCreer();
		if(! FonctEntre(F,f,NomParametre,SpecifClasse,
	                       NomVariable,Domaine)) return(false);
		if (!FonctTestZero(F))
		{	FT= (PCoType) malloc(sizeof(CoType));
			FT->coef=F;
			FT->elt=transition;
			AjoutFinListe(V,FT);
		}
		else
		{	FonctDetr(F);
		}
		transition++;
	}
	return(true);
}

bool VectMarquageEntre(Vect_Type V,FILE *FichierDonnees,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
		Entier_Liste Domaine)

{	PCoType	FT;
	Fonct_Type F;
	Var_Type f;


	VectZero(V);
	if(!LireDonnees(f,FichierDonnees)) return(false);
	F=FonctCreer();
	if(! FonctEntre(F,f,NomParametre,SpecifClasse,
	                    NomVariable,Domaine)) return(false);
	if (!FonctTestZero(F))
	{	FT= (PCoType) malloc(sizeof(CoType));
		FT->coef=F;
		FT->elt=0;
		AjoutFinListe(V,FT);
	}
	else
	{	FonctDetr(F);
	}
	return(true);
}
		
void VectSort(Vect_Type V,char *f,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
                Var_Liste NomClasse,Var_Liste NomTransition)

{       Var_Type ve,vc;
	Fonct_Type F;
	int 	transition;
        int i,n;

        strcpy(f,"");
	strcpy(vc,"");        
        n=Taille(V);
        if (n!=0)
        {       for (i=1;i<=n;i++)
                {	(void) Sort(V,&F,&transition,i);
                	if (i>1) strcat(f,"+");

                	strcat(f,"(");
                	FonctSort(F,vc,NomParametre,SpecifClasse,
                	 	NomVariable,NomClasse);
                	strcat(f,vc);	
                	strcat(f,")");

			VarNom(NomTransition,transition,&ve);
			strcat(f,ve);
		}
	}
}

