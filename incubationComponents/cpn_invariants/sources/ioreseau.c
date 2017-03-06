#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */


#include "CListes.h"

#include "taillechaine.h"

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
#include "MacaoArc.h"

#include	"ioreseau.h"

bool ReseauEntre(	int     *NbPlace,
			int	*NbTransition,
			int	*NbClasse,
			int	*NbVariable,
			Mat_Type MatPre,
			Mat_Type MatPost,
			Mat_Type MatInitial,
			Entier_Liste    ExistePlace,
			Entier_Liste    ExisteTransition,
			Var_Liste       NomPlace,
			Var_Liste       NomTransition,
			Var_Liste       NomParametre,
			Var_Liste       NomClasse,
			Var_Liste       NomVariable,
			Classe_Liste    SpecifClasse,
			Dom_Liste       DomainePlace,
			int		*MacaoMax,
			Entier_Liste	MacaoPlace,
			Entier_Liste	MacaoTransition,
			Arc_Matrice	MacaoPre,
			Arc_Matrice	MacaoPost,
			FILE    	*Donnees)

{	char f[max];
	int i/*,n*/;


	
/******  NbPlace,NbTransition,NbClasse,NbVariable *****/

	fscanf(Donnees,"%d",NbPlace);
	fscanf(Donnees,"%d",NbTransition);
	fscanf(Donnees,"%d",NbClasse);
	fscanf(Donnees,"%d",NbVariable);
	fgets(f,max,Donnees);	

/******* Init de la liste des places et transitions existantes *****/
	EntierInit(ExistePlace,*NbPlace);
	EntierInit(ExisteTransition,*NbTransition);
	
/****** MacaoPlace *****/
	fgets(f,max,Donnees);
	if (f[0]!='\n') 
	EntierEntreMax(MacaoPlace,f,MacaoMax);
	
/****** MacaoTransition *****/
	fgets(f,max,Donnees);
	if (f[0]!='\n') 
	EntierEntreMax(MacaoTransition,f,MacaoMax);
	
/****** NomPlace *********/
 	fgets(f,max,Donnees);
	if (f[0]!='\n') 
	VarChiffreEntre(NomPlace,f);

/****** NomTransition *********/
 	fgets(f,max,Donnees);
	if (f[0]!='\n') 
	VarChiffreEntre(NomTransition,f);

/****** NomClasse ********/
 	fgets(f,max,Donnees);
	if (f[0]!='\n') 
	VarChiffreEntre(NomClasse,f);

/****** NomVariable ********/
 	fgets(f,max,Donnees);
	if (f[0]!='\n') 
	VarChiffreEntre(NomVariable,f);

/****** TypeClasse ********/
	fgets(f,max,Donnees);
	if (f[0]!='\n') 
	if (!ClasseListeEntre(SpecifClasse,f,NomParametre)) return(false);
		

/****** Domaine des variables **/
	fgets(f,max,Donnees);	
	
/****** Matrice Pre  *********/

	if (!MatEntre(DomainePlace,MatPre,Donnees,NomParametre,SpecifClasse,
	NomVariable,*NbPlace,true))
		return(false);


/****** Macao Pre *********/
	ArcEntre(MacaoPre,Donnees,*NbPlace,*NbTransition,MacaoMax);
	
/****** Matrice Post  *********/

	if (!MatEntre(DomainePlace,MatPost,Donnees,NomParametre,SpecifClasse,
	NomVariable,*NbPlace,false))
		return(false);

	
/****** Macao Post *********/
	ArcEntre(MacaoPost,Donnees,*NbPlace,*NbTransition,MacaoMax);

/***** Predicat transition  A suivre ****/	

	 for(i=1;i<=*NbTransition;i++)
	{	fgets(f,max,Donnees);
		/* if (strncmp(f,"{}",2)!=0) return(false); */
	}	

/***** Marquage initiale ********/
	if (!MatMarquageEntre(DomainePlace,MatInitial,Donnees,NomParametre,
	SpecifClasse,NomVariable,*NbPlace))
		return(false);
	
	 return(true);
}

#if 0
void	ReseauSort(	int     NbPlace,
			int	NbTransition,
			int	NbClasse,
			int	NbVariable,
			Mat_Type MatPre,
			Mat_Type MatPost,
			Mat_Type MatInitial,
			Var_Liste       NomPlace,
			Var_Liste       NomTransition,
			Var_Liste       NomParametre,
			Var_Liste       NomClasse,
			Var_Liste       NomVariable,
			Classe_Liste    SpecifClasse,
			Dom_Liste       DomainePlace)

{
#pragma unused(DomainePlace)
char 	f[max];
/*	int	i,k; */

/*********** Affichage des resultats **************/	 

/******  NbPlace,NbTransition,NbClasse,NbVariable *****/

	printf("NbPlace      = %d \n",NbPlace);
	printf("NbTransition = %d \n",NbTransition);
	printf("NbClasse     = %d \n",NbClasse);
	printf("NbVariable   = %d \n",NbVariable);
	printf("\n");

/****** MacaoPlace *****/
	
/****** MacaoTransition *****/

	
/****** NomPlace *********/

	VarSort(NomPlace,f);
	printf("NomPlace = %s \n",f);
	printf("\n");

/****** NomTransition *********/

	VarSort(NomTransition,f);
	printf("NomTransition = %s \n",f);
	printf("\n");
	

/****** NomClasse ********/

	VarSort(NomClasse,f);
	printf("NomClasse = %s \n",f);
	printf("\n");

/****** NomVariable ********/

	VarSort(NomVariable,f);
	printf("NomVariable = %s \n",f);
	printf("\n");	

/****** TypeClasse ********/
 	
	ClasseListeSort(SpecifClasse,f,NomParametre);
	printf("SpecifClasse = %s \n",f);
	printf("\n");

/****** Domaine des variables **/
		
/****** Matrice Pre  *********/

	MatSort(MatPre,f,NomParametre,
             	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);
	printf("Pre =\n%s \n",f);
	printf("\n");


/****** Macao Pre *********/
	
/****** Matrice Post  *********/

	MatSort(MatPost,f,NomParametre,
	       	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);
	printf("Post = \n%s \n",f);
	printf("\n");
	

/****** Macao Post *********/


/****** Predicat NON ******/

/****** Marquage initiale ******/

	MatSort(MatInitial,f,NomParametre,
	       	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);
	printf("Marquage Initial = \n%s \n",f);
	printf("\n");
}

void	ReseauSortSimple(int 	NbPlace,
			int	NbTransition,
			int	NbClasse,
			int	NbVariable,
			Mat_Type MatPre,
			Mat_Type MatPost,
			Mat_Type MatInitial,
			Var_Liste       NomPlace,
			Var_Liste       NomTransition,
			Var_Liste       NomParametre,
			Var_Liste       NomClasse,
			Var_Liste       NomVariable,
			Classe_Liste    SpecifClasse,
			Dom_Liste       DomainePlace)

{
#pragma unused(NbPlace, NbTransition, NbClasse, NbVariable, DomainePlace)
	char 	f[max];
/*	int	i,k; */

/*********** Affichage des resultats **************/	 
		
/****** Matrice Pre  *********/

	MatSort(MatPre,f,NomParametre,
             	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);
	printf("Pre =\n%s \n",f);
	printf("\n");

/****** Matrice Post  *********/

	MatSort(MatPost,f,NomParametre,
	       	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);
	printf("Post = \n%s \n",f);
	printf("\n");
	
/****** Marquage initiale ******/

	MatSort(MatInitial,f,NomParametre,
	       	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);
	printf("Marquage Initial = \n%s \n",f);
	printf("\n");
}

static void	ReseauSortRien(int 	NbPlace,
			int	NbTransition,
			int	NbClasse,
			int	NbVariable,
			Mat_Type MatPre,
			Mat_Type MatPost,
			Mat_Type MatInitial,
			Mat_Type MatFlot,
			Var_Liste       NomPlace,
			Var_Liste       NomTransition,
			Var_Liste       NomParametre,
			Var_Liste       NomClasse,
			Var_Liste       NomVariable,
			Classe_Liste    SpecifClasse,
			Dom_Liste       DomainePlace)

{
#pragma unused(NbPlace, NbTransition, NbClasse, NbVariable, DomainePlace)
char 	f[max];
/*	int	i,k;*/

/*********** Affichage des resultats **************/	 
		
/****** Matrice Pre  *********/

	MatSort(MatPre,f,NomParametre,
             	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);

/****** Matrice Post  *********/

	MatSort(MatPost,f,NomParametre,
	       	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);
	
/****** Marquage initiale ******/

	MatSort(MatInitial,f,NomParametre,
	       	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);

	MatSort(MatFlot,f,NomParametre,
	       	SpecifClasse,NomVariable,NomClasse,NomTransition,NomPlace);
}
#endif

