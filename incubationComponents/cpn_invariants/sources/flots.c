#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "CListes.h"

/* quelques parametres */
#include "taillechaine.h"
/* suites classiques */

#include "variable.h"
#include "iovariable.h"
#include "entiervariable.h"
#include "ioentiervariable.h"
#include "classe.h"
#include "fonction.h"
#include "iofonction.h"
#include "domaine.h"
#include "vecteur.h"
#include "matrice.h"
#include "iomatrice.h"
#include "reduction.h"
#include "MacaoArc.h"
#include "ioreseau.h"

#include "flots.h"

#define   merde      while (getchar () != '\n')

/***********************************************************************/
static void affiche(const char coucou[])
{
/* affichage des matrices d'incidence en local */
#if 0
	printf("\n***********************************************\n");
	printf("\n....... %s .........\n\n",coucou);	
	printf("\n Matrice Operation:\n");
	printf("--------------------\n");
	MatSort(MatOperation,f,NomParametre,
                SpecifClasse,NomVariableFlot,
                NomClasse,
		NomPlace, NomPlace);
	printf("%s",f);
	printf("\n Matrice Pre:\n");
	printf(" ------------\n");
	MatSort( MatPre, f, NomParametre,
                 SpecifClasse, NomVariable,
                 NomClasse,
		 NomTransition,  NomPlace);
	printf("%s",f);
 	printf("\n Matrice Post:\n");
	printf(" -------------\n");
	MatSort( MatPost, f, NomParametre,
                 SpecifClasse, NomVariable,
                 NomClasse,
		 NomTransition,  NomPlace);
	printf("%s",f);
	printf("\n Marquage Initial:\n");
	printf("------------\n");
	MatSort( MatInitial, f, NomParametre,
                 SpecifClasse, NomVariable,
                 NomClasse,
		 NomTransition,  NomPlace);
	printf("%s",f);
/*...	getchar();
...*/
#else
#ifdef FK_MAC_OS
#pragma unused(coucou)
#endif
#endif
}


/************************************************************************/

bool Calculflots(FILE *Donnees)
	{	
/*	char 	f[max];
	int	i,k; jlm */
	bool ok;

/* Variables reseau */

	Mat_Type MatPre=MatCreer();
	Mat_Type MatPost=MatCreer();
	Mat_Type MatInitial=MatCreer();

	Entier_Liste	ExistePlace=EntierCreer();
	Entier_Liste	ExisteTransition=EntierCreer();
	

	int	NbPlace,NbTransition,NbClasse,NbVariable;
	
	Var_Liste	NomPlace=VarCreer();
	Var_Liste	NomTransition=VarCreer();


	Var_Liste	NomParametre=VarCreer();
	Var_Liste	NomClasse=VarCreer();	
	Var_Liste	NomVariable=VarCreer();
	Classe_Liste	SpecifClasse=ClasseCreer();
	Dom_Liste	DomainePlace=DomCreer();

/* variables flots */

	Mat_Type MatFlot=MatCreer();
	Mat_Type MatFlotValeur=MatCreer();
/*	Mat_Type MatFlotVecteur=MatCreer(); jlm not used */
	Mat_Type MatOperation=MatCreer();

	int	NbFlot=0;
	Var_Liste NomVariableFlot=VarCreer();
	

/* Variable d'etat de l'execution */
	bool	EtatReduction=false;
	int	EtatNoPlace=1;
/*	bool	test,testo; jlm */

/* Un peu de Macao */
	int     	MacaoMax=1;
	Entier_Liste    MacaoPlace=EntierCreer();
	Entier_Liste    MacaoTransition=EntierCreer();
	Arc_Matrice     MacaoPre=ArcCreer();
	Arc_Matrice     MacaoPost=ArcCreer();	
	
/******************************************************************/	

	int p,p1,p2,q,t;
	Fonct_Type	FI=FonctCreer();
	Fonct_Type	FI1=FonctCreer();
	Fonct_Type	FI2=FonctCreer();
	Entier_Liste	Lp=EntierCreer();
	Entier_Liste	Lt=EntierCreer();
/*	Entier_Liste	Domaine=EntierCreer(); jlm not used */

/***********************************************************************/

	
/***********************************************************************/

/****** Initialisation du module CListe ****/

/*	ListeInit(); jlm */
	
/*****  Lecture du reseau *********/

	ok=	ReseauEntre( &NbPlace,&NbTransition,&NbClasse,&NbVariable,
                             MatPre,MatPost,MatInitial,ExistePlace,ExisteTransition,
                             NomPlace,NomTransition,NomParametre,
                             NomClasse,NomVariable,SpecifClasse,
                             DomainePlace,&MacaoMax,MacaoPlace,
                             MacaoTransition,MacaoPre,MacaoPost,
                             Donnees);
/****** Fichier  ferme***/	

/*	fclose(Donnees); jlm 5/06/1999 */

/******** c'est parti ******/


        if (ok)
	{ 	


/****** Initialisation de MatOperation a Identite ************/

	MatIdentite(MatOperation,NbPlace,DomainePlace,SpecifClasse);
		
/* Init des Nom variable utilise dans les flots */
	
	VarInitxi(NomVariableFlot,ARITEMAX);
	

/*************** C'est vraiment parti  */
	affiche("Matrice initiale");


/* boucle integrant l'heuristique de l'identite pas trop merdique */
do
{	EtatReduction=false;


/* boucle de reduction pas trop destructive */
do
{	EtatReduction=false;

	/* boucle d'execution de reduction preservant les semi-flots */		
	do
	{	EtatReduction=false;

/************* Suppression des arcs Pre(p,t)=Post(p,t) */

MatSimpl2Mat(MatPre,MatPost,ExistePlace,ExisteTransition,NbPlace,NbTransition);
affiche("Suppression des doubles arcs");


/************* Simplification de place */

/*......Pre(p)=Post(p) .........*/

		for (EtatNoPlace=1;EtatNoPlace<=NbPlace;EtatNoPlace++)
		{	if (EntierIndice(ExistePlace,EtatNoPlace)!=-1)
			{  
				if (RedTestPlaceImplicite1		  						(EtatNoPlace,MatInitial,MatPre,MatPost,NbVariable) )	
				{  	EtatReduction=true;
					RedPlaceImplicite1
(EtatNoPlace,MatInitial,MatPre,MatPost,ExistePlace,
MatOperation,MatFlot,MatFlotValeur,&NbFlot);
					affiche("Place implicite: P=constant");
				}
			}
		}

	/* Fin simplification de place */


/************** Agglomeration de place */

		for (t=1;t<=NbTransition;t++)
		{	if (EntierIndice(ExisteTransition,t)!=-1)
			{
			  /* Pre Agglomeration de Place: q + f.p    */	

				if (RedTestPost
(&EtatNoPlace,t,Lp,Lt,MatInitial,MatPost,MatPre,NbVariable,FI))	
				{  	EtatReduction=true;
					
					RedAgglomeration
(EtatNoPlace,t,Lp,MatOperation,MatPost,MatPre,MatInitial,
ExistePlace,ExisteTransition,FI);
					affiche("PreAgglomeration"); 
				}

			   /* Post Agglomeration de Place : q + f.p   */
				else 
				if (RedTestPost
(&EtatNoPlace,t,Lp,Lt,MatInitial,MatPre,MatPost,NbVariable,FI))
				{  	EtatReduction=true;
					
					RedAgglomeration
(EtatNoPlace,t,Lp,MatOperation,MatPre,MatPost,MatInitial,
ExistePlace,ExisteTransition,FI);
					affiche("PostAgglomeration"); 
				}
		}

		} /* Fin Agglomeration de place */


/************** Simplication de transition */

		for (EtatNoPlace=1;EtatNoPlace<=NbTransition;EtatNoPlace++)
		{	if (EntierIndice(ExisteTransition,EtatNoPlace)!=-1)
			{  
				/* Pre(t)=Post(t)   */	

				if (RedTestTransitionNeutre1
(EtatNoPlace,&p,&t,MatPre,MatPost,NbVariable))	
				{  	EtatReduction=true;
					RedTransitionNeutre
(EtatNoPlace,MatPre,MatPost,ExisteTransition);					

					affiche("Transition nulle"); 
				}

			   /* Pre(t)=Post(t)=0 ????  */
				else if (RedTestTransitionNeutre2
(EtatNoPlace,MatPre,MatPost))
				{  	EtatReduction=true;
					RedTransitionNeutre
(EtatNoPlace,MatPre,MatPost,ExisteTransition);							
					affiche("Transition Isolee"); 
				}
			}

		} 

	/* Fin Simplication de transition */




	} /* fin du do : Reduction preservant les semi-flots */

	while (EtatReduction);

/********* Reduction ne preservant pas les semi-flots */

/* Simplication de place */

/*......Pre(p)=f.Pre(q) ; Post(p)=Post(q) ; Init(p)=f.Init(q) .........*/

		for (EtatNoPlace=1;EtatNoPlace<=NbPlace;EtatNoPlace++)
		{	if (EntierIndice(ExistePlace,EtatNoPlace)!=-1)
			{  
				if (RedTestPlaceSimpl2		  						(EtatNoPlace,&q,MatPre,MatPost,MatInitial,NbVariable,FI) )	
				{  	EtatReduction=true;
					RedPlaceSimpl2
(EtatNoPlace,q,MatInitial,MatPre,MatPost,ExistePlace,MatOperation,
FI,MatFlot,MatFlotValeur,&NbFlot);
					EtatNoPlace=NbPlace+1;
					affiche("Place implicite: P=f.Q"); 
				}
			}
		}


/*......Pre(p)=f.Post(q) ; Post(p)=Pre(q) .........*/

if (EtatReduction)
{
		for (EtatNoPlace=1;EtatNoPlace<=NbPlace;EtatNoPlace++)
		{	if (EntierIndice(ExistePlace,EtatNoPlace)!=-1)
			{  
				if (RedTestPlaceSimpl3		  						(EtatNoPlace,&q,MatPre,MatPost,NbVariable,FI) )	
				{  	EtatReduction=true;
					RedPlaceSimpl3
(EtatNoPlace,q,MatInitial,MatPre,MatPost,ExistePlace,MatOperation,
FI,MatFlot,MatFlotValeur,&NbFlot);
					EtatNoPlace=NbPlace+1;
					affiche("Place Semi-flots: P+f.Q");
				}
			}
		}
}



}/* fin du do : Reduction pas trop destructive */
while (EtatReduction);


/*.......******* Heuristique de l`identite pas trop merdique..........*/

		for (t=1;t<=NbTransition;t++)
		{	if (EntierIndice(ExisteTransition,t)!=-1)
			{
			  /* Heuristique de l'identite   */	

				if (RedTestIdentitepq
(&p1,&p2,t,Lp,Lt,MatPre,MatPost,NbVariable,FI1,FI2))	
				{  	
					EtatReduction=true;
					RedIdentitepq
(p1,p2,t,Lp,Lt,MatOperation,MatPre,MatPost,MatInitial,
ExistePlace,ExisteTransition,FI1,FI2);
					t=NbTransition+1;
					affiche("Bonne heuristique de l'identite"); 
				}
			}
		}




}/* fin du do : Reduction totale */
while (EtatReduction);


/* Affichage des matrices reduites */
		affiche("Matrice Finale");		
/* Affichage des flots */

		MatOppose(MatPre);
		MatAdd(MatPre,MatPost);
		
		AfficheMatrice(MatOperation,"Operation Matrice",
			NomParametre,SpecifClasse, NomVariableFlot,
                 	NomClasse, NomPlace, NomPlace);


		AfficheMatrice(MatPost,"Incidence Matrice",
			NomParametre,SpecifClasse, NomVariable,
                 	NomClasse, NomTransition, NomPlace);


		AfficheMatrice(MatInitial,"Initial Marking",
			NomParametre,SpecifClasse, NomVariable,
                 	NomClasse, NomTransition, NomPlace);

		MatNice(MatFlotValeur,MatFlot);	       

		MatSortFlot(MatFlot,MatFlotValeur,NomParametre,SpecifClasse,
			NomVariableFlot,NomClasse,NomPlace,MacaoPlace);
/******* Fin   *************************/
	return(true);
	}

			
	else 
		{
		EnvoiReponseTrace("Net not supported by this tool");
		return(false);
		}
}

