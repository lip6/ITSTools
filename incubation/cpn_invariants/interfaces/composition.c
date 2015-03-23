/************************************************************************/
/* Module permettant de definir un type compose TYPE3 sur deux types	*/
/*     TYPE1 et TYPE2							*/
/*									*/
/* representant une somme formelle d'objets TYPE1.TYPE2.		*/
/*									*/
/* TYPE3 est defini comme une liste de structure <TYPE1 coef,TYP2 elt>	*/
/*									*/
/* Sur TYPE1 sont definis les operations				*/
/*		T1CREER()	creer l'objet a				*/
/* 		T1NULL(a) =     true si a==0                            */
/*                              false si a!=0				*/
/*		T1DETR(a)  :    Detruit l'objet a			*/
/*		T1DUPL(a,b) :	b=a					*/
/*		T1ADD(a,b)  :	b=a+b					*/
/*		T1ORDRE(a,b) pour definir T3ORDRE			*/
/*		T1OPPOSE(a) :   a=-a					*/
/* Sur TYPE2 								*/
/*		T2CREER()	creer l'objet f				*/
/*		T2ORDRE(f,g) =	0 si f=g				*/
/*				1 si f<g				*/
/*				-1 si f>g				*/
/*		T2DUPL(f,g) :	g=f					*/
/*		T3DETR(f)  :    Detruit l'objet f			*/
/* Sur TYPE3 sont alors definis						*/
/*		T3CREER()	creer l'objet F				*/
/*		T3NULL(F) =	true si F==0				*/
/*				false si F!=0				*/
/*		T3DETR(F)  :    Detruit l'objet F			*/
/*		T3ZERO(F)  :	F=0					*/
/*		T3DUPL(F,G) :	G=F					*/
/*		T3ADD(F,G) : 	G=F+G					*/
/*		T3ORDRE(F,G) si T1ORDRE est defini			*/
/*		T3OPPOSE(F):	F=-F					*/
/*		T3SIGNE(F)  :   Signe du Premier TYPE1			*/
/************************************************************************/

#include	"composition.h"
/****************   Creation de l'objet F ******************************/

TYPE3	T3CREER(void)
{	return( (TYPE3) CreerListe());
}

/*****************  Test a zero  de F ***********************************/

bool T3NULL(TYPE3 F)
{	if (ListePremier(F)==NULL) return(true); else return(false);
}

/*****************  Destruction d'une liste******************************/


static void * Detruire(PCoType aF)
{	T1DETR(aF->coef);
	T2DETR(aF->elt);
	free(aF);
	return(NULL);
}	

void T3ZERO(TYPE3 F)
{	if (!(T3NULL(F)))
	{	(void) ParcoursListe(F,(ParcoursProcPtr)Detruire);
		ViderListe(F);
	}	
}

void T3DETR(TYPE3 F)
{	T3ZERO(F);
	DetruireListe(F);
}


/**************************  Duplication **********************************/   

static void * DuplObjet(PCoType aF, TYPE3 G)
{	PCoType aG;
	aG = (PCoType) malloc(sizeof(CoType));
	aG->coef = T1CREER();
	aG->elt  = T2CREER();
	T1DUPL(aF->coef,aG->coef);
	T2DUPL(aF->elt,aG->elt);
	AjoutFinListe(G, aG);
	return(NULL);
}	
	

void T3DUPL(TYPE3 F, TYPE3 G)
{	T3ZERO(G);
	(void) ParcoursListe(F,(ParcoursProcPtr)DuplObjet,G);
}

/***************************** ADDITION ************************************/

static PCoType Rechercheplace(PCoType aG,PCoType aF,int *i)
{	if ( (*i= T2ORDRE(aF->elt,aG->elt)) ==-1)
		return(NULL);
	else
		return(aG);
}

static PCoType Rechercheelement(PCoType aG,PCoType aF)
{       if (aG==aF)
		return(aG);
	else return(NULL);
}


static void * Ajout(PCoType aF,TYPE3 G)
{	int i;
        PCoType aG;
	PCoType	aGRech=NULL;/* [jlm 02/09/98] work around for warning: `aGRech' might be used uninitialized in this function gcc 2.7.2 */
	if (ListePremier(G)==NULL)
		{ i=-1; aGRech=NULL; } 
	else
		aGRech =  (PCoType) ParcoursListe(G,(ParcoursProcPtr) Rechercheplace ,aF, &i);
	switch (i) {
	case -1	:	/* Warning  aGRech=NULL do not use it in case -1 */
			aG = (PCoType) malloc(sizeof(CoType));
			aG->coef = T1CREER();
			aG->elt  = T2CREER();
	        	T1DUPL(aF->coef,aG->coef);
	                T2DUPL(aF->elt,aG->elt);
	                AjoutFinListe(G, aG);
			break;
	case 0  :	T1ADD(aF->coef,aGRech->coef);
			if (T1NULL(aGRech->coef))
			{	EnleveElement(G, aGRech);
				T1DETR(aGRech->coef);
				T2DETR(aGRech->elt);
		                free(aGRech);
	
		        }         
			break;
	case 1	: 	aG = (PCoType) malloc(sizeof(CoType));
			aG->coef = T1CREER();
			aG->elt  = T2CREER();
	                T1DUPL(aF->coef,aG->coef);
	               	T2DUPL(aF->elt,aG->elt);
	               	(void)ParcoursListe(G,(ParcoursProcPtr)Rechercheelement,aGRech);
			ParcoursInsereAvant(aG);
			break;
	          
	}
	return(NULL);
}
	
void T3ADD(TYPE3 F, TYPE3 G)  
{	if (!T3NULL(F))
	{	(void) ParcoursListe(F,(ParcoursProcPtr) Ajout, G); 	
	}
}

		
/****************** Ordre **************************************************/

#ifdef T3ORDRE

/* Duplication d'une liste sans duppliquer les valeurs!!! */

static void * DuplAdresse(PCoType aF, TYPE3 G)
{       
				AjoutFinListe(G, aF);
        return(NULL);
}


static void DuplListeAdresse(TYPE3 F, TYPE3 G)
{
        ViderListe(G);
        (void) ParcoursListe(F,(ParcoursProcPtr)DuplAdresse, G);
}



int T3ORDRE(TYPE3 F, TYPE3 G)
{
 	TYPE3	FC,GC;
	PCoType aF,aG;
	int	i;
	
	i=2;
	FC = T3CREER();
	GC = T3CREER();
	DuplListeAdresse(F,FC);
	DuplListeAdresse(G,GC);
	while(i==2)
	{	aF = (PCoType)ListePremier(FC);
		aG = (PCoType)ListePremier(GC);
		
		if (aF==NULL)
		{	if (aG==NULL)	i=0;    /* F==G===0    */
			else			i=1;    /* F==0 < G!=0 */
		}
		else
		{	if (aG==NULL)	i=-1;   /* F!=0 > G==0 */
			else			        /* F!=0 ? G!=0 */
			{ if (!((i=T2ORDRE(aF->elt,aG->elt))!=0))
			  { if (!((i=T1ORDRE(aF->coef,aG->coef))!=0))
 			    { 	EnleveElement(FC, aF);
				EnleveElement(GC, aG);
				i=2;
			    }
			  }
			}			
		}
	 }
	 ViderListe(FC);DetruireListe(FC);
	 ViderListe(GC);DetruireListe(GC);
	 return(i);
}

#endif		

/************************ T3 OPPOSE F=-F *********************************/

#ifdef	T3OPPOSE

static void *	ProcT3oppose(PCoType aF)
{	T1OPPOSE(aF->coef);
	return(NULL);
}

void T3OPPOSE(TYPE3 F)
{	(void)ParcoursListe(F,(ParcoursProcPtr)ProcT3oppose);
}	
#endif

#ifdef	T3SIGNE

int	T3SIGNE(TYPE3 F)
{	PCoType aF;

	if (T3NULL(F)) return(0);	
	aF = (PCoType)ListePremier(F);
	return(T1SIGNE(aF->coef));
}
#endif
