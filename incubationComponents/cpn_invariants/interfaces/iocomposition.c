#include	"iocomposition.h"

/********** Taille: Taille d'une liste ******************************/

static  void * Compteur(PCoType aF,int *i)
{
       *i+=1;
        return(NULL);
}


static int     Taille(TYPE3 F)
{       int i;
        if (ListePremier(F)==NULL) return(0);
        else
        {	i=0;
        	(void) ParcoursListe(F,(ParcoursProcPtr)Compteur, &i);
        	return(i);
        }
}

/************* Ajout de l'element <a,f> dans la liste F *********************/
#ifndef	__iovecteur__
#ifndef __iomatrice__
/*
Les fonctions Rechercheplace, Rechercheelement et Entre ne sont pas utilis�es
pour les vecteurs ni les matrices 
jlm
*/
			static PCoType Rechercheplace(PCoType aG,PCoType aF,int *i)
				{
				if ( (*i= T2ORDRE(aF->elt,aG->elt)) ==-1)
			  	return(NULL);
			  else
			  	return(aG);
				}

			static PCoType Rechercheelement(PCoType aG,PCoType aF)
				{
				if (aG==aF) return(aG);
				else return(NULL);
				}

static void Entre(TYPE3 F,TYPE1 a,TYPE2 f)
{       int i;
        PCoType aF;
        PCoType aGRech=NULL;

        aF = (PCoType) malloc(sizeof(CoType));
        aF->elt=f;
        aF->coef=a;
        if (ListePremier(F)==NULL) i=-1;
        else
        aGRech =  (PCoType) ParcoursListe(F,(ParcoursProcPtr) Rechercheplace, aF, &i);
        switch (i) {
        case -1 :       AjoutFinListe(F,aF);
                        break;
        case 0  :       T1ADD(aF->coef,aGRech->coef);
                        if (T1NULL(aGRech->coef))
                        {       EnleveElement(F,aGRech);
/*...		                T1DETR(aGRech->coef);
                                T2DETR(aGRech->elt);
                                free(aGRech);
                                aGRech=NULL; ...*/
                        }
/*...                        free(aF);
                        aF=NULL;
                        T1DETR(a);
                        T2DETR(f);  ...*/

                        break;
        case 1  :       (void)  ParcoursListe(F,(ParcoursProcPtr)Rechercheelement
				,aGRech);
                        ParcoursInsereAvant(aF);
                        break;
        }
}
#endif
#endif
/********************** Recherche des valeurs du ieme elt dans F */

static PCoType RechHandle(PCoType aF,int *i)
{       if (--*i==0) return(aF);
        else return(NULL);
}

static bool Sort(TYPE3 F,TYPE1 *a,TYPE2 *f,int i)
{       PCoType aF;
	int j;

	j=i;
        if (0<j)
        {	aF= (PCoType) ParcoursListe(F,(ParcoursProcPtr)RechHandle, &j);

         	if (j== 0)
		{	*a = aF->coef;
			*f = aF->elt;
			return(true);
		}	
         	else return(false);
        }
	else  return(false);
}

