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

#include	"reduction.h"

/*********************** Simplification Identite ****************************/
/* Calcul peut-etre pas  trop destructeur                                         */
/****************************************************************************/	
#if 0
bool	RedTestIdentite2(int *p,int t,
		Entier_Liste Entrep,Entier_Liste Sortiep,
		Mat_Type MatPre,Mat_Type MatPost,
		int NbVariable,Fonct_Type FI,bool *PdansPreOuPost)

{	/*Fonct_Type	Fpt;*/
	Entier_Liste	DomaineT=EntierCreer();

	MatSupportTransition(MatPre,Entrep,t);
	MatSupportTransition(MatPost,Sortiep,t);	
	
/* Recherche de la place p dans Entrep telque MatPre(p,t)=Identite */

	if (!EntierInclus(Sortiep,Entrep))
	{	EntierDetruit(DomaineT);
		MatDomaineTransition(MatPost,DomaineT,t);
 		if (MatRechercheIdentite(MatPre,Entrep,Sortiep,DomaineT,t,p,FI,
		 NbVariable))
		{	*PdansPreOuPost=true;
			EntierDetr(DomaineT);
			return(true);
		}
	}

/* Recherche de la place p dans Entrep telque MatPost(p,t)=Identite */

 	if (!EntierInclus(Entrep,Sortiep))
	{	EntierDetruit(DomaineT);
		MatDomaineTransition(MatPre,DomaineT,t);

	  	if (MatRechercheIdentite(MatPost,Sortiep,Entrep,DomaineT,t,p,FI,
			 NbVariable))
		{	*PdansPreOuPost=false;
			EntierDetr(DomaineT);
			return(true);
		}
	}
	return(false);
}	
#endif
/*********** Execution de l'heuristique de l'identite **************/
void	RedIdentite2(int p,int h,
		bool PdansPreOuPost,Entier_Liste L1, Entier_Liste L2,
		Mat_Type MatOperation,Mat_Type M1,Mat_Type M2,
		Mat_Type MatInitial,
		Entier_Liste ExistePlace,Entier_Liste ExisteTransition,
		Fonct_Type FI,bool *Test)
		
{
#ifdef FK_MAC_OS
#pragma unused(ExistePlace, ExisteTransition)
#endif
	Fonct_Type	F/*,G*/;
 	Fonct_Type	Fq=FonctCreer();
 	Fonct_Type	Fp=FonctCreer();
/*	Vect_Type	V;*/
	int	i,n,q;
	Mat_Type	MatPre,MatPost;
	Entier_Liste 	Entreh,Sortieh;
	Entier_Liste	Succp=EntierCreer();
	Entier_Liste	Predp=EntierCreer();
	Entier_Liste	voisinq=EntierCreer();

	if (PdansPreOuPost)
	{	MatPre=M1; Entreh=L1;
		MatPost=M2; Sortieh=L2;
	}
	else
	{	MatPre=M2;Sortieh=L1;
		MatPost=M1;Entreh=L2;
	}

	MatSupportPlace(MatPre,Succp,p);
	MatSupportPlace(MatPost,Predp,p);
		

/* Calcul M(p,.)=FI.M(p,.) sur MatFlot,MatPre,MatPost */
	MatTestCoef(MatPre,p,h,&F);
	FonctDupl(F,Fp);
	MatMultPlace(MatOperation,p,FI);
	MatMultPlace(MatPost,p,FI);   
	MatMultPlace(MatPre,p,FI);
	MatMultPlace(MatInitial,p,FI);


/* Pour toutes les places q Sortieh non Entreh M(q,.)=M(q,.)+Post(q,h).M(p,.) */
	*Test=false;
	if(!(n=EntierTaille(Sortieh))==0)
	{
		for (i=1;i<=n;i++)
		{	EntierNom(Sortieh,i,&q);
			MatSupportPlace(MatPre,voisinq,q);
			if (EntierDisjoint(voisinq,Succp))
			{	MatSupportPlace(MatPost,voisinq,q);
				if (EntierDisjoint(voisinq,Predp))
				{	(void) MatTestCoef(MatPost,q,h,&F);
					if (EntierIndice(Entreh,q)==-1)
					{	FonctDupl(F,Fq);
						MatAddPlace(MatOperation,q,p,Fq);	
						MatAddPlace(MatPost,q,p,Fq);
						MatAddPlace(MatPre,q,p,Fq);
						MatAddPlace(MatInitial,q,p,Fq);
						*Test=true;
					}
				}
			}
		}
	}



/* Pas de Suppression de la place p */

	MatMultPlace(MatOperation,p,Fp);	
/*	MatMultPlace(MatPost,p,Fp);	*/
	MatMultPlace(MatPre,p,Fp);	
	MatMultPlace(MatInitial,p,Fp);

	
/* marguerite */
	FonctDetr(Fq); 	
	FonctDetr(Fp); 
}

/*********************** Simplification Identite ****************************/
/* Calcul vraiment pas trop destructeur                                         */
/****************************************************************************/	
bool	RedTestIdentitepq(int *p,int *q,int t,
		Entier_Liste Entret,Entier_Liste Sortiet,
		Mat_Type MatPre,Mat_Type MatPost,
		int NbVariable,Fonct_Type FIp,Fonct_Type FIq)

{/*	Fonct_Type	Fpt;*/
	Entier_Liste	DomaineT=EntierCreer();

	MatSupportTransition(MatPre,Entret,t);
	MatSupportTransition(MatPost,Sortiet,t);	

/* Calcul du domaine de t */

	EntierDetruit(DomaineT);
	MatDomaineTransition(MatPre,DomaineT,t);
 	MatDomaineTransition(MatPost,DomaineT,t);

/* Recherche de la place p dans Entrep telque MatPre(p,t)=Identite */

 	if (!MatRechercheIdentite(MatPre,Entret,Sortiet,DomaineT,t,p,FIp,
		 NbVariable))
	{	EntierDetr(DomaineT);
		return(false);
	}

/* Recherche de la place p dans Entrep telque MatPost(p,t)=Identite */

  	if (!MatRechercheIdentite(MatPost,Sortiet,Entret,DomaineT,t,q,FIq,
		 NbVariable))
	{	EntierDetr(DomaineT);
		return(false);
	}
	EntierDetr(DomaineT);
	return(true);

}

/*********** Execution cool de l'heuristique de l'identite **************/

void	RedIdentitepq(int p1,int p2,int h,
		Entier_Liste Entreh, Entier_Liste Sortieh,
		Mat_Type MatOperation,Mat_Type MatPre,Mat_Type MatPost,
		Mat_Type MatInitial,
		Entier_Liste ExistePlace,Entier_Liste ExisteTransition,
		Fonct_Type FI1,Fonct_Type FI2)
		
{	Fonct_Type	F;
 	Fonct_Type	Fq=FonctCreer();
 	Fonct_Type	G=FonctCreer();
/*	Vect_Type	V;*/
	int	i,n,q;

		

/* Calcul M(p1,.)=FI1.M(p1,.) sur MatFlot,MatPre,MatPost */
	MatTestCoef(MatPre,p1,h,&F);
	FonctDupl(F,G);
	MatMultPlace(MatOperation,p1,FI1);
	MatMultPlace(MatPost,p1,FI1);  
	MatMultPlace(MatPre,p1,FI1);
	MatMultPlace(MatInitial,p1,FI1);

/* Pour toutes les places q Sortieh-{p2}  M(q,.)=M(q,.)+Post(q,h).M(p1,.) */

	i=EntierIndice(Sortieh,p2);
	EntierSupprime(Sortieh,i);
	if(!(n=EntierTaille(Sortieh))==0)
	{
		for (i=1;i<=n;i++)
		{	EntierNom(Sortieh,i,&q);
			(void) MatTestCoef(MatPost,q,h,&F);
			FonctDupl(F,Fq);
			MatAddPlace(MatOperation,q,p1,Fq);		
			MatAddPlace(MatPost,q,p1,Fq);
			MatAddPlace(MatPre,q,p1,Fq);
			MatAddPlace(MatInitial,q,p1,Fq);
		}
	}

	MatMultPlace(MatOperation,p1,G);
	MatMultPlace(MatPost,p1,G);  
	MatMultPlace(MatPre,p1,G);
	MatMultPlace(MatInitial,p1,G);

/* Calcul M(p,.)=FI.M(p,.) sur MatFlot,MatPre,MatPost */

	MatMultPlace(MatOperation,p2,FI2);
	MatMultPlace(MatPost,p2,FI2);  
	MatMultPlace(MatPre,p2,FI2);
	MatMultPlace(MatInitial,p2,FI2);



/* Pour toutes les places q Entreh   M(q,.)=M(q,.)+Pre(q,h).M(p2,.) */

	if(!(n=EntierTaille(Entreh))==0)
	{
		for (i=1;i<=n;i++)
		{	EntierNom(Entreh,i,&q);
			(void) MatTestCoef(MatPre,q,h,&F);
			FonctDupl(F,Fq);
			MatAddPlace(MatOperation,q,p2,Fq);		
			MatAddPlace(MatPost,q,p2,Fq);
			MatAddPlace(MatPre,q,p2,Fq);
			MatAddPlace(MatInitial,q,p2,Fq);
		}
	}



/* Suppression de la place p2 */
	MatSupprimePlace(MatOperation,p2);	
	MatSupprimePlace(MatPost,p2);	
	MatSupprimePlace(MatPre,p2);	
	MatSupprimePlace(MatInitial,p2);
	EntierSupprime(ExistePlace,EntierIndice(ExistePlace,p2));

/* Suppression de la transition h*/

	MatSupprimeTransition(MatPost,h);	
	MatSupprimeTransition(MatPre,h);
	EntierSupprime(ExisteTransition,EntierIndice(ExisteTransition,h));

/* marguerite */
	FonctDetr(Fq); 	
	FonctDetr(G); 
}


/*********************** Post Agglomeration ********************************/

bool	RedTestPost(int *p,int h,
		Entier_Liste Sortieh,Entier_Liste Entrep,
		Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
		int NbVariable,Fonct_Type FI)
		
{
#ifdef FK_MAC_OS
#pragma unused(MatInitial)
#endif
	Fonct_Type	F,Fq;		
	Entier_Liste	DomaineF=EntierCreer();
	Entier_Liste	DomaineFq=EntierCreer();
	int	i,n,q;
	
/* h n'a qu'une entree p: Pre(p,h)=F == Identite*/
	if (!MatTestUneEntreeTransition(MatPre,p,h,&F))  /*....T*/
	{	EntierDetr(DomaineF);
		EntierDetr(DomaineFq);
		return(false);
	}
/*...	if (!MatTestUneEntreeTransition(MatPre,&p,*h,&F)) 
	{	EntierDetr(DomaineF);
		EntierDetr(DomaineFq);
		return(false);
	}
...*/	if (!FonctTestIdentite(F,FI,NbVariable))
	{	EntierDetr(DomaineF);
		EntierDetr(DomaineFq);
		return(false);
	}

/* Calcul du domaine de depart de Post(p,h) */
	FonctDomaine(F,DomaineF);

/* Calcul des Entrees p = Entrep et h non dans Entrep*/
	MatSupportPlace(MatPost,Entrep,*p);
	if (EntierIndice(Entrep,h)!=-1)
	{	EntierDetr(DomaineF);
		EntierDetr(DomaineFq);
		return(false);
	}

	
/* Calcul des Sortie de h = Sortieh */
	MatSupportTransition(MatPost,Sortieh,h);

/* Les places q de Sortieh n'ont qu'une sortie h, Post(q,h) */
/*	de domaine Domaine						*/	

	if((n=EntierTaille(Sortieh))==0)
	{	EntierDetr(DomaineF);
		EntierDetr(DomaineFq);
		return(true);
	}

	for (i=1;i<=n;i++)
	{	EntierNom(Sortieh,i,&q);
		(void) MatTestCoef(MatPost,q,h,&Fq);
		FonctDomaine(Fq,DomaineFq);
		if (!EntierInclus(DomaineFq,DomaineF))
		{	EntierDetr(DomaineF);
			EntierDetr(DomaineFq);
			return(false);
		}
	}

	EntierDetr(DomaineF);
	EntierDetr(DomaineFq);
	return(true);
}

/*********** Execution d'une agglomeration **************/

void	RedAgglomeration(int p,int h,
		Entier_Liste EntreOuSortieh,
		Mat_Type MatOperation,Mat_Type MatPre,Mat_Type MatPost,
		Mat_Type MatInitial,
		Entier_Liste ExistePlace,Entier_Liste ExisteTransition,
		Fonct_Type FI)
		
{	Fonct_Type	F;
 	Fonct_Type	Fq=FonctCreer();
	int	i,n,q;

/* Calcul M(p,.)=FI.M(p,.) sur MatFlot,MatPre,MatPost */
	MatMultPlace(MatOperation,p,FI);
	MatMultPlace(MatPost,p,FI);
	MatMultPlace(MatPre,p,FI);
	MatMultPlace(MatInitial,p,FI);

/* Pour toutes les places q EntreOuSortieh  M(q,.)=M(q,.)+Post(q,h).M(p,.) */
	if(!(n=EntierTaille(EntreOuSortieh))==0)
	{
		for (i=1;i<=n;i++)
		{	EntierNom(EntreOuSortieh,i,&q);
			(void) MatTestCoef(MatPost,q,h,&F);
			FonctDupl(F,Fq);
			MatAddPlace(MatOperation,q,p,Fq);		
			MatAddPlace(MatPost,q,p,Fq);
			MatAddPlace(MatPre,q,p,Fq);
			MatAddPlace(MatInitial,q,p,Fq);
		}
	}

/* Suppression de la place p */
	MatSupprimePlace(MatOperation,p);	
	MatSupprimePlace(MatPost,p);	
	MatSupprimePlace(MatPre,p);	
	MatSupprimePlace(MatInitial,p);
	EntierSupprime(ExistePlace,EntierIndice(ExistePlace,p));

/* Suppression de la transition h*/

	MatSupprimeTransition(MatPost,h);	
	MatSupprimeTransition(MatPre,h);
	EntierSupprime(ExisteTransition,EntierIndice(ExisteTransition,h));

/* marguerite */
	FonctDetr(Fq); 	

}
/***** Simplication de place 2: Pre(p,.)=f.Pre(q,.);Post(p,.)=f.Post(q,.) */
/*                                                   Init(q)=f.Init(q)  ! */

bool	RedTestPlaceSimpl2(int p,int *q,
		Mat_Type M1,Mat_Type M2,Mat_Type MatInitial,
		int NbVariable,Fonct_Type G)
{	Entier_Liste Sortiet=EntierCreer();
	Entier_Liste Entrep=EntierCreer();
 	Fonct_Type FI=FonctCreer();
	Fonct_Type F,Fq;
	Vect_Type VPrep,VPostp,VInitialep,Vq;
	bool	VTestInitialep;
	Vect_Type V=VectCreer();
	int t;
	int i,TailleSortiet;
	Mat_Type MatPre;
	Mat_Type MatPost;
	bool	TestPostp;


/************ MatPre(p,.) <>0 si possible **********/

	if (MatTestPlace(M1,p))
	{	MatPre=M1;MatPost=M2;
	}
	else 
	{	MatPre=M2;MatPost=M1;
	}
	
/************* Calcul des entrees de p *************/
	MatSupportPlace(MatPre,Entrep,p);


/************* Calcul des sorties de la premiere transition *****/
	if (EntierTaille(Entrep)==0) 
	{	EntierDetr(Sortiet);
		FonctDetr(FI);
		VectDetr(V); 
		return(false);
	}	

/******** A revoir: seule la premiere transition est prise !!! ******/

	EntierNom(Entrep,1,&t);
	MatSupportTransition(MatPre,Sortiet,t);

	if ((TailleSortiet=EntierTaille(Sortiet))==1)
	{	EntierDetr(Sortiet);
		FonctDetr(FI);
		VectDetr(V); 
		return(false);
	}	

/************* Fonction et vecteur associe a p *****/	

	MatTestCoef(MatPre,p,t,&F);
	MatPlace(MatPre,p,&VPrep);
	TestPostp=MatPlace(MatPost,p,&VPostp);
	VTestInitialep=MatPlace(MatInitial,p,&VInitialep);

/*** Recherche de la bonne place q ****************/

	i=1;
	do
	{ 	EntierNom(Sortiet,i,q);
	  	if (p!=*q)
	  	{ 	MatTestCoef(MatPre,*q,t,&Fq);
	    		if (FonctTestIdentite(Fq,FI,NbVariable))
	    		{	FonctDupl(FI,G);
				FonctMult(F,G);

				MatPlace(MatPre,*q,&Vq);
				VectDupl(Vq,V);
				VectMultGaucheFonct(G,V);
				
				if (VectOrdre(VPrep,V)==0) 
				{	if (MatPlace(MatPost,*q,&Vq))
					{	if (TestPostp)
						{	VectDupl(Vq,V);
							VectMultGaucheFonct(G,V);
							if (VectOrdre(VPostp,V)==0)
							{	if (VTestInitialep)
								{	if (MatPlace(MatInitial,*q,&Vq))
									{	VectDupl(Vq,V);
										VectMultGaucheFonct(G,V);	
										if (VectOrdre(VInitialep,V)==0)
										{	EntierDetr(Sortiet);
											FonctDetr(FI);
											VectDetr(V); 
											return(true);
										}
									}	
								}
								else
								{	if (!MatPlace(MatInitial,*q,&Vq))
									{	EntierDetr(Sortiet);
										FonctDetr(FI);
										VectDetr(V);
										return(true);
									}
								}	
							}
						}
					}
					else
					{	if (!TestPostp)
						{	if (VTestInitialep)
							{	if (MatPlace(MatInitial,*q,&Vq))
								{	VectDupl(Vq,V);
									VectMultGaucheFonct(G,V);	
									if (VectOrdre(VInitialep,V)==0)
									{	EntierDetr(Sortiet);
										FonctDetr(FI);
										VectDetr(V); 
										return(true);
									}
								}	
							}
							else
							{	if (!MatPlace(MatInitial,*q,&Vq))
								{	EntierDetr(Sortiet);
									FonctDetr(FI);
									VectDetr(V);
									return(true);
								}
							}	
						}
					}

				}
			}
		}
		i++;
	}
	while (i<=TailleSortiet);

	EntierDetr(Sortiet);
	FonctDetr(FI);
	VectDetr(V); 
	return(false);
}
		
/* .... */

void	RedPlaceSimpl2(int p,int q,
	Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
	Entier_Liste ExistePlace,Mat_Type MatOperation,
	Fonct_Type G,Mat_Type MatFlot,Mat_Type MatFlotValeur,int *NbFlot)
{


	MatSupprimePlace(MatPre,p);
	MatSupprimePlace(MatPost,p);
	MatSupprimePlace(MatInitial,p);
	EntierSupprime(ExistePlace,EntierIndice(ExistePlace,p));
	
	*NbFlot+=1;
	MatDeplacePlace(MatOperation,p,MatFlot,*NbFlot);
	MatCopiePlace(MatOperation,q,MatFlotValeur,*NbFlot);
	MatMultPlace(MatFlotValeur,*NbFlot,G);
}	

/***** Simplication de place 3: Pre(p,.)=f.Pre(q,.);Post(p,.)=f.Post(q,.) : flot p+f.q=constante*/
/* Operation qui detruit trop rapidement la matrice !!!!!! */

bool	RedTestPlaceSimpl3(int p,int *q,
		Mat_Type M1,Mat_Type M2,
		int NbVariable,Fonct_Type G)
{	Entier_Liste Sortiet=EntierCreer();
	Entier_Liste Entrep=EntierCreer();
 	Fonct_Type FI=FonctCreer();
	Fonct_Type F,Fq;
	Vect_Type VPrep,VPostp,Vq/*,VInitialep*/;
/*	bool	VTestInitialep;*/
	Vect_Type V=VectCreer();
	int t;
	int i,TailleSortiet;
	Mat_Type MatPre;
	Mat_Type MatPost;
	bool	TestPostp;


/************ MatPre(p,.) <>0 si possible **********/

	if (MatTestPlace(M1,p))
	{	MatPre=M1;MatPost=M2;
	}
	else 
	{	MatPre=M2;MatPost=M1;
	}
	
/************* Calcul des entrees de p *************/
	MatSupportPlace(MatPre,Entrep,p);


/************* Calcul des sorties de la premiere transition *****/
	if (EntierTaille(Entrep)==0) 
	{	EntierDetr(Sortiet);
		FonctDetr(FI);
		VectDetr(V); 
		return(false);
	}	



/******** A revoir: seule la premiere transition est prise !!! ******/

	EntierNom(Entrep,1,&t);
	MatSupportTransition(MatPost,Sortiet,t);  /*...*/

	if ((TailleSortiet=EntierTaille(Sortiet))==0)
	{	EntierDetr(Sortiet);
		FonctDetr(FI);
		VectDetr(V); 
		return(false);
	}	

/************* Fonction et vecteur associe a p *****/	

	MatTestCoef(MatPre,p,t,&F);
	MatPlace(MatPre,p,&VPrep);
	TestPostp=MatPlace(MatPost,p,&VPostp);

/*** Recherche de la bonne place q ****************/

	i=1;
	do
	{ 	EntierNom(Sortiet,i,q);
	  	if (p!=*q)
	  	{ 	MatTestCoef(MatPost,*q,t,&Fq);  /*...*/
	    		if (FonctTestIdentite(Fq,FI,NbVariable))
	    		{	FonctDupl(FI,G);
				FonctMult(F,G);

				MatPlace(MatPost,*q,&Vq);   /*...*/
				VectDupl(Vq,V);
				VectMultGaucheFonct(G,V);
				
				if (VectOrdre(VPrep,V)==0) 
				{	if (MatPlace(MatPre,*q,&Vq))   /*...*/
					{	if (TestPostp)
						{	VectDupl(Vq,V);
							VectMultGaucheFonct(G,V);
							if (VectOrdre(VPostp,V)==0)
							{	EntierDetr(Sortiet);
								FonctDetr(FI);
								VectDetr(V); 
								return(true);
							}
						}
					}
					else
					{	if (!TestPostp)
						{	EntierDetr(Sortiet);
							FonctDetr(FI);
							VectDetr(V); 
							return(true);
						}
					}
				}
			}
		}
		i++;
	}
	while (i<=TailleSortiet);

	EntierDetr(Sortiet);
	FonctDetr(FI);
	VectDetr(V); 
	return(false);
}
		
/* .... */

void	RedPlaceSimpl3(int p,int q,
	Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
	Entier_Liste ExistePlace,Mat_Type MatOperation,
	Fonct_Type G,Mat_Type MatFlot,Mat_Type MatFlotValeur,int *NbFlot)
{

/* Operation p+G.q dans MatOperation et MatInitial */
	MatAddPlace(MatOperation,p,q,G);
	MatAddPlace(MatInitial,p,q,G);

/* Deplace la ligne p : MatOperation->MatFlot; MatInitial->MatFlotValeur */
	*NbFlot+=1;
	MatDeplacePlace(MatOperation,p,MatFlot,*NbFlot);
	MatDeplacePlace(MatInitial,p,MatFlotValeur,*NbFlot);

/* Achever la suppression de p */

	MatSupprimePlace(MatPre,p);
	MatSupprimePlace(MatPost,p);
	EntierSupprime(ExistePlace,EntierIndice(ExistePlace,p));

}	

/*............*/

/***** Simplication de place 2: Pre(q,.)=f.Pre(p,.);Post(q,.)=f.Post(q,.) */
/* Operation qui detruit trop rapidement la matrice !!!!!! */
#if 0
static bool	RedTestPlaceSimplBis2(int p,int *q,
		Mat_Type M1,Mat_Type M2,
		int NbVariable,Fonct_Type G)
{	Entier_Liste Sortiet=EntierCreer();
	Entier_Liste Entrep=EntierCreer();
 	Fonct_Type FI=FonctCreer();
	Fonct_Type F,Fq;
	Vect_Type VPrep,VPostp,Vq/*,VInitialep*/;
/*	bool	VTestInitialep;*/
	Vect_Type V=VectCreer();
	int t;
	int i,TailleSortiet;
	Mat_Type MatPre;
	Mat_Type MatPost;
	bool	TestPostp;


/************ MatPre(p,.) <>0 si possible **********/

	if (MatTestPlace(M1,p))
	{	MatPre=M1;MatPost=M2;
	}
	else 
	{	MatPre=M2;MatPost=M1;
	}
	
/************* Calcul des entrees de p *************/
	MatSupportPlace(MatPre,Entrep,p);


/************* Calcul des sorties de la premiere transition *****/
	if (EntierTaille(Entrep)==0) 
	{	EntierDetr(Sortiet);
		FonctDetr(FI);
		VectDetr(V); 
		return(false);
	}	



/******** A revoir: seule la premiere transition est prise !!! ******/

	EntierNom(Entrep,1,&t);
	MatSupportTransition(MatPre,Sortiet,t);

	if ((TailleSortiet=EntierTaille(Sortiet))==1)
	{	EntierDetr(Sortiet);
		FonctDetr(FI);
		VectDetr(V); 
		return(false);
	}	

/************* Fonction et vecteur associe a p *****/	

	MatTestCoef(MatPre,p,t,&F);
	MatPlace(MatPre,p,&VPrep);
	TestPostp=MatPlace(MatPost,p,&VPostp);

/*** Recherche de la bonne place q ****************/

	i=1;
	do
	{ 	EntierNom(Sortiet,i,q);
	  	if (p!=*q)
	  	{ 	MatTestCoef(MatPre,*q,t,&Fq);
	    		if (FonctTestIdentite(Fq,FI,NbVariable))
	    		{	FonctDupl(FI,G);
				FonctMult(F,G);

				MatPlace(MatPre,*q,&Vq);
				VectDupl(Vq,V);
				VectMultGaucheFonct(G,V);
				
				if (VectOrdre(VPrep,V)==0) 
				{	if (MatPlace(MatPost,*q,&Vq))
					{	if (TestPostp)
						{	VectDupl(Vq,V);
							VectMultGaucheFonct(G,V);
							if (VectOrdre(VPostp,V)==0)
							{	EntierDetr(Sortiet);
								FonctDetr(FI);
								VectDetr(V); 
								return(true);
							}
						}
					}
					else
					{	if (!TestPostp)
						{	EntierDetr(Sortiet);
							FonctDetr(FI);
							VectDetr(V); 
							return(true);
						}
					}
				}
			}
		}
		i++;
	}
	while (i<=TailleSortiet);

	EntierDetr(Sortiet);
	FonctDetr(FI);
	VectDetr(V); 
	return(false);
}
#endif	
/* .... */
#if 0
static void	RedPlaceSimplBis2(int p,int q,
	Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
	Entier_Liste ExistePlace,Mat_Type MatOperation,
	Fonct_Type G,Mat_Type MatFlot,Mat_Type MatFlotValeur,int *NbFlot)
{

/* Operation p-G.q dans MatOperation et MatInitial */
	FonctOppose(G);
	MatAddPlace(MatOperation,p,q,G);
	MatAddPlace(MatInitial,p,q,G);

/* Deplace la ligne p : MatOperation->MatFlot; MatInitial->MatFlotValeur */
	*NbFlot+=1;
	MatDeplacePlace(MatOperation,p,MatFlot,*NbFlot);
	MatDeplacePlace(MatInitial,p,MatFlotValeur,*NbFlot);

/* Achever la suppression de p */

	MatSupprimePlace(MatPre,p);
	MatSupprimePlace(MatPost,p);
	EntierSupprime(ExistePlace,EntierIndice(ExistePlace,p));

}		
#endif
/*********** Place implicite 1 Pre(p,.)=Post(p,.)  **********************/

bool	RedTestPlaceImplicite1(int p,
		Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
		int NbVariable)
{
#ifdef FK_MAC_OS
#pragma unused(MatInitial, NbVariable)
#endif
	/*Fonct_Type F;*/
	Vect_Type VPre,VPost;

	if (!MatPlace(MatPre,p,&VPre)) 
	{	if (!MatPlace(MatPost,p,&VPost)) return(true);
		else				return(false);
	}

	if (!MatPlace(MatPost,p,&VPost)) return(false);
	
	if (VectOrdre(VPre,VPost)!=0) return(false);		
        
	return(true);
}

void	RedPlaceImplicite1(int p,Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
	Entier_Liste ExistePlace,
	Mat_Type MatOperation,Mat_Type MatFlot,Mat_Type MatFlotValeur,int *NbFlot)
{	MatSupprimePlace(MatPre,p);
	MatSupprimePlace(MatPost,p);
	EntierSupprime(ExistePlace,EntierIndice(ExistePlace,p));	

	*NbFlot+=1;
	MatDeplacePlace(MatOperation,p,MatFlot,*NbFlot);
	MatDeplacePlace(MatInitial,p,MatFlotValeur,*NbFlot);
	
}	
		
#if 0
/************** Place Implicite 2 ***********************************/
static bool	RedTestPlaceImplicite2(int p,int *q,Entier_Liste Entrep,
		Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
		int NbVariable,Fonct_Type G)
{	Entier_Liste Sortiet=EntierCreer();
 	Fonct_Type FI=FonctCreer();
	Fonct_Type F,Fq;
	Vect_Type VPrep,VPostp,VInitialep,Vq;
	bool	VTestInitialep;
	Vect_Type V=VectCreer();
	int t;
	int i,TailleSortiet;
	
/************* Calcul des entrees de p *************/
	MatSupportPlace(MatPre,Entrep,p);

/************* Calcul des sorties de la premiere transition *****/
	if (EntierTaille(Entrep)==0) 
	{	EntierDetr(Sortiet);
		FonctDetr(FI);
		VectDetr(V); 
		return(false);
	}	
	EntierNom(Entrep,1,&t);
	MatSupportTransition(MatPre,Sortiet,t);

	if ((TailleSortiet=EntierTaille(Sortiet))==1)
	{	EntierDetr(Sortiet);
		FonctDetr(FI);
		VectDetr(V); 
		return(false);
	}	

/************* Fonction et vecteur associe a p *****/	
	MatTestCoef(MatPre,p,t,&F);
	MatPlace(MatPre,p,&VPrep);
	if (!MatPlace(MatPost,p,&VPostp))
	{	EntierDetr(Sortiet);
		FonctDetr(FI);
		VectDetr(V);
		return(false);
	}	
	VTestInitialep=MatPlace(MatInitial,p,&VInitialep);
	
	
/*** Recherche de la bonne place q ****************/

	i=1;
	do
	{	EntierNom(Sortiet,i,q);
		if (p!=*q)
		{	MatTestCoef(MatPre,*q,t,&Fq);
			if (FonctTestIdentite(Fq,FI,NbVariable))
			{	FonctDupl(FI,G);
				FonctMult(F,G);

				MatPlace(MatPre,*q,&Vq);
				VectDupl(Vq,V);
				VectMultGaucheFonct(G,V);
				
				if (VectOrdre(VPrep,V)==0) 
				{	if (MatPlace(MatPost,*q,&Vq))
					{	VectDupl(Vq,V);
						VectMultGaucheFonct(G,V);
						if (VectOrdre(VPostp,V)==0)
						{	if (VTestInitialep)
							{	if (MatPlace(MatInitial,*q,&Vq))
								{	VectDupl(Vq,V);
									VectMultGaucheFonct(G,V);	
									if (VectOrdre(VInitialep,V)==0)
									{	EntierDetr(Sortiet);
										FonctDetr(FI);
										VectDetr(V); 
										return(true);
									}
								}	
							}
							else
							{	if (!MatPlace(MatInitial,*q,&Vq))
								{	EntierDetr(Sortiet);
									FonctDetr(FI);
									VectDetr(V);
									return(true);
								}
							}		
						}
					}
				}
			}
		}
		i++;	
	}
	while (i<=TailleSortiet);
	EntierDetr(Sortiet);
	FonctDetr(FI);
	VectDetr(V); 
	return(false);
}
#endif
#if 0
static void	RedPlaceImplicite2(int p,int q,
	Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
	Entier_Liste ExistePlace,Mat_Type MatOperation,
	Fonct_Type G,Mat_Type MatFlot,Mat_Type MatFlotValeur,int *NbFlot)
{


	MatSupprimePlace(MatPre,p);
	MatSupprimePlace(MatPost,p);
	MatSupprimePlace(MatInitial,p);
	EntierSupprime(ExistePlace,EntierIndice(ExistePlace,p));
	
	*NbFlot+=1;
	MatDeplacePlace(MatOperation,p,MatFlot,*NbFlot);
	MatCopiePlace(MatOperation,q,MatFlotValeur,*NbFlot);
	MatMultPlace(MatFlotValeur,*NbFlot,G);
}		
#endif
/***************** Transition neutre *************************************/

bool	RedTestTransitionNeutre1(int t,int *p,int *tm,
		Mat_Type MatPre,Mat_Type MatPost,int NbVariable)
{
#ifdef FK_MAC_OS
#pragma unused(tm, NbVariable)
#endif
	/*Vect_Type VPost;*/
	Fonct_Type FPre,FPost;
	int q;

	/********* t n'a qu'une seule entree et sortie p */	
	if (!MatTestUneEntreeTransition(MatPre,p,t,&FPre)) return(false);
	if (!MatTestUneEntreeTransition(MatPost,&q,t,&FPost)) return(false);
	if (*p!=q) return(false);

	/********* Pre(p,t) = Post(p,t) ******************/
	if (FonctOrdre(FPre,FPost)!=0) return(false);

	/********* !tm / Post(p,tm)>Pre(p,t) *************/
/*	(void) MatPlace(MatPost,*p,&VPost);
	if (!VectSuperieur(VPost,FPre,t,tm,NbVariable)) return(false);
*/	return(true);
}	

bool RedTestTransitionNeutre2(int t,Mat_Type MatPre,Mat_Type MatPost)
{	Entier_Liste L=EntierCreer();
	bool b;
	
	MatSupportTransition(MatPre,L,t);
	if (EntierTaille(L)!=0) b=false;
	else
	{	MatSupportTransition(MatPost,L,t);
		if (EntierTaille(L)!=0) b=false;
		else b=true;
	}
	EntierDetr(L);
	return(b);	
}	

void	RedTransitionNeutre(int t,Mat_Type MatPre,Mat_Type MatPost,Entier_Liste ExisteTransition)
{	MatSupprimeTransition(MatPre,t);
	MatSupprimeTransition(MatPost,t);
	EntierSupprime(ExisteTransition,EntierIndice(ExisteTransition,t));
	
}
	
/**************** Domaine implicite ****************************************/
/*
bool RedTestSimplificationDomaine(int p,Mat_Type MatInitial,
		Mat_Type MatPre,Mat_Type MatPost,Fonct_Type G);
		.....
		a suivre
*/
