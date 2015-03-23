#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#define	__iomatrice__
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
#include "iovecteur.h"
/* Operation sur les domaines */
#include "domaine.h"
#include "iodomaine.h"
/* Operation de base sur les matrices */
#include "matrice.h"
/* Operations de bases io sur les matrices */
#include "compmatrice.h"
#include "iocomposition.c"
#include	"iomatrice.h"

bool MatEntre(Dom_Liste Domaineplace,Mat_Type M,
		FILE *FichierDonnees,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
		int NbPlace,bool OuiEntreDomainePlace)
                              
{	PCoType	VT;
	Vect_Type V;
	Entier_Liste Domaine=NULL;
	int place;

	if (!(OuiEntreDomainePlace)) Domaine=EntierCreer();

	MatZero(M);
	for (place=1;place<=NbPlace;place++)
	{	/* lire le domaine */
		if (OuiEntreDomainePlace) Domaine=EntierCreer();
		DomEntre(Domaine,FichierDonnees);
		if (OuiEntreDomainePlace) DomAjoutFin(Domaineplace,Domaine);

		/* lire le vecteur */
		V=VectCreer();
		if(! VectEntre(V,FichierDonnees,NomParametre,SpecifClasse,
	                       NomVariable,Domaine)) return(false);
                if (!VectTestZero(V))
                {       VT= (PCoType) malloc(sizeof(CoType));
                        VT->coef=V;
                        VT->elt=place;
                        AjoutFinListe(M,VT);
                }
                else
                {       VectDetr(V);
                }
                
        }
               
	return(true);	
}

bool MatMarquageEntre(Dom_Liste Domaineplace,Mat_Type M,
		FILE *FichierDonnees,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
		int NbPlace)
                              
{	PCoType	VT;
	Vect_Type V;
	Entier_Liste Domaine;
	int place;


	MatZero(M);
	for (place=1;place<=NbPlace;place++)
	{	/* lire le domaine */
		DomNom(Domaineplace,place,&Domaine);

		/* lire le vecteur */
		V=VectCreer();
		if(! VectMarquageEntre(V,FichierDonnees,NomParametre,
		SpecifClasse,NomVariable,Domaine))
			return(false);
                if (!VectTestZero(V))
                {       VT= (PCoType) malloc(sizeof(CoType));
                        VT->coef=V;
                        VT->elt=place;
                        AjoutFinListe(M,VT);
                }
                else
                {       VectDetr(V);
                }
                
        }
               
	return(true);	
}
		
static	void MatSortMerde(Mat_Type M,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
                Var_Liste NomClasse,Var_Liste NomTransition,
		Var_Liste NomPlace)

{       Var_Type ve,vc;
	Vect_Type V;
	int 	place;
        int i,n;
	char f[2000];

        strcpy(f,"");
	strcpy(vc,"");        
        n=Taille(M);
        if (n!=0)
        {       for (i=1;i<=n;i++)
                {	(void) Sort(M,&V,&place,i);

			VarNom(NomPlace,place,&ve);
			strcpy(f,"");
			strcat(f,ve);
			strcat(f,":");	
                	strcat(f,"[");
                	VectSort(V,vc,NomParametre,SpecifClasse,
                	 	NomVariable,NomClasse,NomTransition);
                	strcat(f,vc);	
                	strcat(f,"]");
		/*	EnvoiReponseTrace(f); */

		}
	}
}

void MatSort(Mat_Type M,char *f,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
                Var_Liste NomClasse,Var_Liste NomTransition,
		Var_Liste NomPlace)

{       Var_Type ve,vc;
	Vect_Type V;
	int 	place;
        int i,n;

        strcpy(f,"");
	strcpy(vc,"");        
        n=Taille(M);
        if (n!=0)
        {       for (i=1;i<=n;i++)
                {	(void) Sort(M,&V,&place,i);

			VarNom(NomPlace,place,&ve);
		
			strcat(f,ve);
			strcat(f,":");	
                	strcat(f,"[");
                	VectSort(V,vc,NomParametre,SpecifClasse,
                	 	NomVariable,NomClasse,NomTransition);
                	strcat(f,vc);	
                	strcat(f,"]\n");

		}
	}
}



/**************** Affichage des places ****************************************/

void MatSortPlace(Mat_Type M,Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
                Var_Liste NomClasse,Var_Liste NomPlace,Entier_Liste MacaoPlace)

{       char f[max];
	Vect_Type V;
	int 	place,MacaoNom;
        int i,n;
    


        n=Taille(M);
        if (n!=0)
        {       for (i=1;i<=n;i++)
                {	(void) Sort(M,&V,&place,i);
     			EntierNom(MacaoPlace,place,&MacaoNom);
                	VectSort(V,f,NomParametre,SpecifClasse,
                	 	NomVariable,NomClasse,NomPlace);
             
                	EnvoiDebutEnsemble2("Place Signification",1);
                	EnvoiMiseEnEvidence(MacaoNom);
			EnvoiReponseTexte(f);
			EnvoiFinEnsemble();
		}
	}
}

/**************** Affichage des flots ****************************************/

void MatSortFlot(Mat_Type M1,Mat_Type M2,
		Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
                Var_Liste NomClasse,Var_Liste NomPlace,Entier_Liste MacaoPlace)

{       Var_Type /*ve,*/vc,v;
	Vect_Type V1,V2;
	int 	place;
        int i,n;
	char f[max];
	Mat_Type MatVecteur=MatCreer();

	int p,nf,k,nomp;
	Fonct_Type F;
	Entier_Liste Support=EntierCreer();;



		MatDupl(M2,MatVecteur);
		MatOppose(MatVecteur);
		MatAdd(M1,MatVecteur);
		MatSupprimeTransition(MatVecteur,0);
	
		MatDupl(M2,MatVecteur);
		MatOppose(MatVecteur);
		MatAdd(M1,MatVecteur);	
		MatSupprimeTransition(MatVecteur,0);
	strcpy(vc,"");        
        n=Taille(M1);
        if (n!=0)
        {       for (i=1;i<=n;i++)
                {
			strcpy(f,"");
			strcat(f,"[");
                	sprintf(v,"%d",i);
                	strcat(f,v);
                	strcat(f,"]: ");
			(void) Sort(M1,&V1,&place,i);	
                   	VectSort(V1,vc,NomParametre,SpecifClasse,
                	 	NomVariable,NomClasse,NomPlace);
                	strcat(f,vc);
                	strcat(f," = ");
			if (MatPlace(M2,i,&V2))
			{	 VectSort(V2,vc,NomParametre,SpecifClasse,
			         	 NomVariable,NomClasse,NomPlace);
			         strcat(f,vc);
			}
			else strcat(f,"0");         

			EnvoiDebutEnsemble2("P-Flow",1);
/*****			nf=EntierTaille(MacaoPlace);

			for (k=1;k<=nf;k++)
			{	EntierNom(MacaoPlace,k,&nomp);
				EnvoiModifAttribut(nomp,"weight","");
			}
******/
/*			EnvoiResultatObjet(1);
*/			EnvoiReponseTexte(f);

		
			MatSupportPlace(MatVecteur,Support,i);
			nf=EntierTaille(Support);
			for (k=1;k<=nf;k++)
			{	EntierNom(Support,k,&p);
				EntierNom(MacaoPlace,p,&nomp);
				EnvoiMiseEnEvidence(nomp);
/*..*/				MatTestCoef(MatVecteur,i,p,&F);
				FonctSort(F,vc,NomParametre,SpecifClasse,
				NomVariable,NomClasse);
				EnvoiModifAttribut(nomp,"weight",vc);
/*..				EnvoiMiseEnEvidenceAttribut(nomp,"weight");
..*/			}
			EnvoiFinEnsemble();
		}
	}
}


void	AfficheMatrice(Mat_Type M,const char coucou[],Var_Liste NomParametre,
                Classe_Liste SpecifClasse,Var_Liste NomVariable,
                Var_Liste NomClasse,Var_Liste NomTransition,
		Var_Liste NomPlace)
{
	EnvoiReponseTrace(coucou);
/*
	EnvoiReponseTrace("__________________________________");
	EnvoiReponseTrace("");
*/
	MatSortMerde(M,NomParametre,
                 SpecifClasse, NomVariable,
                 NomClasse, NomTransition, NomPlace);
/*
	EnvoiReponseTrace("");
	EnvoiReponseTrace("__________________________________");
*/
}	
