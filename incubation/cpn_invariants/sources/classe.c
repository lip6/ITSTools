#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
/************************************************************************/
/* Ce module contient les operations sur les classes ?			*/
/*									*/
/*	bool ClasseListeEntre(Classe_Liste L,char *f,		*/
/*					Var_Liste NomParametre);	*/
/*	void ClasseListeSort(Classe_Liste L,char *f,			*/
/*					Var_Liste NomParametre);	*/
/*	Classe_Liste    ClasseCreer();					*/
/*	void            ClasseInit(Classe_Liste L);			*/
/*									*/
/*									*/
/************************************************************************/

/************************************************************************/
/* Quelques fichiers utiles 						*/
/************************************************************************/

#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */
#include <ctype.h>
#include "CListes.h"

#include "taillechaine.h"

#include "iovariable.h"
#include "variable.h"

#include "poly.h"
#include "classe.h"



/************************************************************************/
/* Types associes aux classes 						*/
/************************************************************************/


	/******* Renseignements associes aux classes suivant leur type	*/
	
struct IntervalleType				/* Classe intervalle	*/
{	int		taille;
	Var_Type	debutnom;
	int		debut;
};
typedef struct IntervalleType IntervalleType;

struct EnumereType				/* Classe enumeree	*/
{	int		taille;
	Var_Liste	objets;
};
typedef struct EnumereType EnumereType;


	/******* Le type classe *********/

union	SpecifClasse
{	IntervalleType	intervalle;
	EnumereType	enumere;
	int		parametre;
};	
typedef	union	SpecifClasse SpecifClasse;

struct 	Classe_Type
{	int		classetype;
	SpecifClasse	specif;
};
typedef struct	Classe_Type 	Classe_Type;

typedef	Classe_Type	*PClasse_Type;


/************************************************************************/
/* Creation d'une classe liste						*/
/************************************************************************/

Classe_Liste ClasseCreer(void)
{	return((Classe_Liste)CreerListe());
}

/************************************************************************/
/* Init d'une classe liste						*/
/************************************************************************/

void	ClasseInit(Classe_Liste L)
{	ViderListe((Classe_Liste)L);
}

/************************************************************************/
/* Procedure d'entree de Classe_Liste 	TypeClasse:(specification)	*/
/************************************************************************/

static	bool TypeClasseEntre(int *a,char *f)
{	sscanf(f,"%d",a);
	if ( (0<*a) && (*a<6) ) 	return(true);
	else 				return(false);
}

static	bool SpecifEntre(SpecifClasse *S,char *f,
			int a,Var_Liste NomParametre)
{	Var_Type v;
	int	i,j;


	

	switch(a){
	case 1:	/* Classe integer 	*/
		break;
	case 2:	/* Classe char	  	*/ 
		break;
	case 3:	/* Classe parametre 	*/
		sscanf(f,"%s",v);
		i=VarAjout(NomParametre,v);
		S->parametre=i;
		break;
	case 4:	/* Classe intervalle 	*/
	/* jlm/iv 17/12/96 permet de lire le "1 5" ou "toto 1 5" */
		if (isdigit(f[0]))	{	sscanf(f,"%d %d",&i,&j);	v[0]='\0';	}
		else								sscanf(f,"%s %d %d",v,&i,&j);
		S->intervalle.taille=j-i+1;
		strcpy(S->intervalle.debutnom,v);
		S->intervalle.debut=i;
		break;
	case 5:	/* Classe enumere	*/
		S->enumere.objets=VarCreer();
		if (!VarEntre(S->enumere.objets,f)) return(false);
		S->enumere.taille=VarTaille(S->enumere.objets);
		break;
	case 6: return(false);	
	}
	return(true);	
}

static bool ClasseEntre(PClasse_Type C,char *f,Var_Liste NomParametre)
{	char 		/**g,*/*gd,*gf;
	Var_Type 	vt,vs;
/*	bool		b;*/
	
	gd=strchr(f,'(');
	gf=strchr(f,')');
	strncpy(vt,f,gd-f);vt[gd-f]='\0';
	if (!(gf==gd+1)) 
	{	strncpy(vs,gd+1,gf-gd-1);vs[gf-gd-1]='\0';
	}
	else strcpy(vs,"");
	
	if (!TypeClasseEntre(&C->classetype,vt)) return(false);
	if (!SpecifEntre(&C->specif,vs,C->classetype,NomParametre))
		return(false);
	return(true);
}

bool ClasseListeEntre(Classe_Liste L,char *f,Var_Liste NomParametre)
{	char		*g;
	Var_Type	v;
	PClasse_Type	PC;
	bool		b;

	if (f[0]=='\0') return(true);
	b=true;
	while(true)
	{
		g=strchr(f,')');
		if (g==0)	return(b);
		else
		{	strncpy(v,f,g-f+2);v[g-f+2]='\0';
			PC= (PClasse_Type) malloc(sizeof(Classe_Type));
			b=b&& ClasseEntre(PC,v,NomParametre);
			AjoutFinListe(L, PC);
			if (strlen(g)>1) f=g+1; else return(b);
		}
	}
}


/************************************************************************/
/* Procedure de sortie de Classe_Liste 	TypeClasse:(specification)	*/
/************************************************************************/

static	void TypeClasseSort(int a,char *f)
{	sprintf(f,"%d",a);
}

static	void SpecifSort(SpecifClasse S,char *f,
			int a,Var_Liste NomParametre)
{	Var_Type v;
	int 	i;
		
	switch(a){
	case 1:	/* Classe integer 	*/
		strcpy(f,"()");
		break;
	case 2:	/* Classe char	  	*/ 
		strcpy(f,"()");
		break;
	case 3:	/* Classe parametre 	*/
		VarNom(NomParametre,S.parametre,&v);
		strcpy(f,"(");
		strcat(f,v);
		strcat(f,")");
		break;
	case 4:	/* Classe intervalle 	*/
		strcpy(f,"(");
		strcat(f,S.intervalle.debutnom);strcat(f," ");
		sprintf(v,"%d", S.intervalle.debut);strcat(f,v);strcat(f," ");
		i= S.intervalle.debut + S.intervalle.taille-1;
		sprintf(v,"%d",i);strcat(f,v);
		strcat(f,")");
		break;
	case 5:	/* Classe enumere	*/
		strcpy(f,"(");
		VarSort(S.enumere.objets,v);strcat(f,v);
		strcat(f,")");
		break;
	}
}

static void ClasseSort(Classe_Type C,char *f,Var_Liste NomParametre)
{	/*char 		*g,gd,df;*/
	Var_Type 	vt,vs;

	
	TypeClasseSort(C.classetype,vt);
	SpecifSort(C.specif,vs,C.classetype,NomParametre);
	strcpy(f,vt);
	strcat(f,":");
	strcat(f,vs);
}



static void * ProcClasseListeSort(PClasse_Type PC,char *f,Var_Liste NomParametre)
{	Var_Type	v;

	ClasseSort(*PC,v,NomParametre);
	strcat(f,v);
	strcat(f," ");
	return(NULL);
}

void	ClasseListeSort(Classe_Liste L,char *f,Var_Liste NomParametre)
{	strcpy(f,"");
	(void) ParcoursListe(L,(ParcoursProcPtr) ProcClasseListeSort, f, NomParametre);
}
					

/************************************************************************/
/* int ClasseTaille(Classe_Liste L,int i);				*/
/************************************************************************/

static PClasse_Type RechHandle(PClasse_Type fc,int *i)
{
if (--*i==0) return(fc);
else return(NULL);
}

static void NomClasse(Classe_Liste L,PClasse_Type *fc,int i)
{	int j;

	j=i;
	*fc= (PClasse_Type)ParcoursListe(L,(ParcoursProcPtr)RechHandle,&j);
}

int ClasseTaille(Classe_Liste L,int i)
{	PClasse_Type fc;


	NomClasse(L,&fc,i);
	switch(fc->classetype){

/* Classe Integer */
	case 1:	{ 	return(0);
			break;
		}
	
/* Classe Char */
	case 2:	{	return(0);
			break;
		}
	
/* Classe Parametre */	
	case 3:	{	return(-fc->specif.parametre);
			break;
		}

/* Classe Intervalle */
	case 4:	{	return(fc->specif.intervalle.taille);
			break;
		}

/* Classe Enumere */
	case 5:	{	return((fc->specif.enumere.taille));
		break;
		}
	}
return(0); /* jlm */
}

/************************************************************************/
/* int ClasseConstantEntre(Classe_Liste L,int i);				*/
/************************************************************************/

int     ClasseConstanteEntre(Classe_Liste L,char *f,int IndClasse)
{	PClasse_Type fc;
	Var_Type v;
	int k;
	char c;

	NomClasse(L,&fc,IndClasse);
	switch(fc->classetype){

/* Classe Integer */
	case 1:	{	sscanf(f,"%d",&k);
			return(k);
			break;
		}
	
/* Classe Char */
	case 2:	{	sscanf(f,"%c",&c);
			return((int)c);
			break;
		}
	
/* Classe Parametre */	
	case 3:	{	sscanf(f,"%d",&k);
			return(k);
			break;
		}

/* Classe Intervalle */
	case 4:	{	sscanf(f+strlen(fc->specif.intervalle.debutnom)
					,"%d",&k);
			return(k-fc->specif.intervalle.debut);
			break;
		}

/* Classe Enumere */
	case 5:	{	strcpy(v,f);
			return(VarIndice(fc->specif.enumere.objets,v)-1);
			break;
		}
	}
return(0); /* jlm */
}
                        
/************************************************************************/
/* int ClasseConstantSort(Classe_Liste L,int i);			*/
/************************************************************************/

void ClasseConstanteSort(Classe_Liste L,char *f,int IndClasse,int k)
{	PClasse_Type fc;
	Var_Type v;
/*	char c;*/

	NomClasse(L,&fc,IndClasse);
	strcpy(f,"");
	switch(fc->classetype){

/* Classe Integer */
	case 1:	{	sprintf(f,"%d",k);
			break;
		}
	
/* Classe Char */
	case 2:	{	sprintf(f,"%c",(char)k);
			break;
		}
	
/* Classe Parametre */	
	case 3:	{	sprintf(f,"%d",k);
			break;
		}

/* Classe Intervalle */
	case 4:	{	strcpy(f,fc->specif.intervalle.debutnom);
			sprintf(v,"%d",k+fc->specif.intervalle.debut);
			strcat(f,v);
			break;
		}

/* Classe Enumere */
	case 5:	{	VarNom(fc->specif.enumere.objets,k+1,&v);
			strcpy(f,v);
			break;
		}
	}
}


