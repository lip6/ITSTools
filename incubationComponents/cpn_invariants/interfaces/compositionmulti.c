/****************************************************************/
/* Module definissant les divers multiplications des objets	*/
/* TYPE3 dans composition.h 					*/
/*								*/
/* Sur TYPE1 sont definis					*/
/*	T1MULT(a,b)	b:=a*b					*/
/*								*/
/* Sur TYPE2 sont definis: a*b=b*a				*/
/*		T2MULT(f,g)		g=f*g			*/
/*	ou	T2MULTBis(f,g,a)	a.g = f*g		*/
/*								*/
/*								*/
/* Sur TYPE3 sont alors definis					*/
/*	T3MULT1Gauche(a,F)	F=a.F				*/
/*	T3MULT1Droite(F,a)	F=F.a				*/
/*	T3MULT2Gauche(f,F)	F=f.F				*/
/*	T3MULT1Droite(a,F)	F=F.f				*/
/*	T3MULT(f,g)		G:=F*G				*/
/*								*/
/****************************************************************/

#include	"compositionmulti.h"

/********* arrangement d'un objet de TYPE3 **********************/

static void Arrangement(TYPE3 F)
{	TYPE3	G=T3CREER();

	T3ZERO(G);
	T3ADD(F,G);
	T3DUPL(G,F);
	T3DETR(G);
}
	
/****************************************************************/
/*	T3MULT1Gauche(a,F)	F=a.F				*/
/****************************************************************/

#ifdef T3MULT1Gauche

static void * Mult1Gauche(PCoType aF,TYPE1 a,TYPE3 F)
{	
	T1MULT(a,aF->coef);
	if (T1NULL(aF->coef))
	{	EnleveElement(F, aF);
		T1DETR(aF->coef);
		T2DETR(aF->elt);
		free(aF);

	}
	return(NULL);
}

void  	T3MULT1Gauche(TYPE1 a,TYPE3 F)
{
	if (T1NULL(a)) T3ZERO(F);
	else	(void)ParcoursListe(
	F,
	(ParcoursProcPtr)Mult1Gauche,
	a,
	F);
	Arrangement(F);
} 

#endif

/****************************************************************/
/*	T3MULT1Droite(F,a)	F=F.a				*/
/****************************************************************/
#ifdef T3MULT1Droite

static void * Mult1Droite(PCoType aF,TYPE1 a,TYPE3 F)
{	TYPE1	b=T1CREER();

	T1DUPL(a,b);
	T1MULT(aF->coef,b);
			  
	if (T1NULL(b))
	{	EnleveElement(F, aF);
		T1DETR(aF->coef);
		T2DETR(aF->elt);
		free(aF);
	}
	else
	{	T1DUPL(b,aF->coef);
	}
	T1DETR(b); 
	return(NULL);
}

void  	T3MULT1Droite(TYPE3 F,TYPE1 a)
{	if (T1NULL(a)) T3ZERO(F);
	else
	{	(void)ParcoursListe(F,(ParcoursProcPtr)Mult1Droite, a, F);
	}
	Arrangement(F);
} 

#endif

/****************************************************************/
/*	T3MULT2Gauche(f,F)	F=f.F				*/
/****************************************************************/

#ifdef T3MULT2Gauche

static void * Mult2Gauche(PCoType aF,TYPE2 f,TYPE3 F)

#ifdef T2MULT	/* Multiplication simple */

{	T2MULT(f,aF->elt);
	if (T2NULL(aF->elt))
	{	EnleveElement(F, aF);
		T1DETR(aF->coef);
		T2DETR(aF->elt);
		free(aF);  
		aF=NULL;  
	}
	return(NULL);
}
#endif

#ifdef T2MULTBIS	/* Multiplication moins simple */

{	TYPE1	a=T1CREER();

	T2MULTBIS(f,aF->elt,a);
	if (T2NULL(aF->elt))
	{	EnleveElement(F, aF);
		T1DETR(aF->coef);
		T2DETR(aF->elt);
		free(aF);
	}
	else
	{	T1MULT(a,aF->coef);
		if (T1NULL(aF->coef))
		{	EnleveElement(F, aF);
			T1DETR(aF->coef);
			T2DETR(aF->elt);
			free(aF);
		}
	}
	T1DETR(a);
	return(NULL);
}
#endif

static  void  	Mult2GaucheNonRange(TYPE2 f,TYPE3 F)
{	if (T2NULL(f)) T3ZERO(F);
	else
	{	(void)ParcoursListe(F,(ParcoursProcPtr)Mult2Gauche, f, F);
	}
}

void    T3MULT2Gauche(TYPE2 f,TYPE3 F)
{	Mult2GaucheNonRange(f,F);
	Arrangement(F);	
}


#endif


/****************************************************************/
/*	T3MULT1Droite(a,F)	F=F.f				*/
/****************************************************************/


#ifdef T3MULT2Droite

static void * Mult2Droite(PCoType aF,TYPE2 f,TYPE3 F)

#ifdef T2MULT	/* Multiplication simple */

{	TYPE2	g=T2CREER();

	T2DUPL(f,g);
	T2MULT(aF->elt,g);
	if (T2NULL(g))
		{
		EnleveElement(F, aF);
		T1DETR(aF->coef);
		T2DETR(aF->elt);
		free(aF);
		}
	else
		{
		T2DUPL(g,aF->elt);
		}
	T2DETR(g); 
	return(NULL);
}
#endif

#ifdef T2MULTBIS	/* Multiplication moins simple */

{	TYPE1	a=T1CREER();
 	TYPE2	g=T2CREER();

	T2DUPL(f,g);
	T2MULTBIS(aF->elt,g,a);
	if (T2NULL(g))
	{	EnleveElement(F, aF);
		T1DETR(aF->coef);
		T2DETR(aF->elt);
		free(aF);
	}
	else
	{	T1MULT(a,aF->coef);
		if (T1NULL(aF->coef))
		{	EnleveElement(F, aF);
			T1DETR(aF->coef);
			T2DETR(aF->elt);
			free(aF);
		}
		else
		{	T2DUPL(g,aF->elt);
		}
	}
	T1DETR(a);
	T2DETR(g);
	return(NULL);
}
#endif

void  	T3MULT2Droite(TYPE3 F,TYPE2 f)
{	if (T2NULL(f)) T3ZERO(F);
	else
	{	(void)ParcoursListe(F,(ParcoursProcPtr)Mult2Droite, f, F);
		Arrangement(F);
	}	
} 

#endif


/****************************************************************/
/*	T3MULT(f,g)		G:=F*G				*/
/****************************************************************/
#ifdef T3MULT

static void * Mult12(PCoType aF,TYPE3 G,TYPE3 H)
{	TYPE3	aFG=T3CREER();

	T3DUPL(G,aFG);
	T3MULT1Gauche(aF->coef,aFG);
	Mult2GaucheNonRange(aF->elt,aFG);
	T3ADD(aFG,H);
	T3DETR(aFG);
	return(NULL);
}

void	T3MULT(TYPE3 F,TYPE3 G)
{	TYPE3	H=T3CREER();

	if ( (T3NULL(F)) || (T3NULL(G)) ) T3ZERO(G);
	else
	{	T3ZERO(H);
		(void)ParcoursListe(F,(ParcoursProcPtr)Mult12, G, H);
		T3DUPL(H,G);
	}
	Arrangement(G);
	T3DETR(H); 
}
	
#endif
