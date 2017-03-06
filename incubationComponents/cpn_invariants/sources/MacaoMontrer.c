#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */


#include "CListes.h"


#include "taillechaine.h"

#include "entiervariable.h"
#include	"MacaoMontrer.h"

/***************** Procedure Montrer *************************/

void MontPlaceImplicite(int p,Entier_Liste MacaoPlace)
{	int i;
	char f[max];

	strcpy(f,"Place implicite");
	EntierNom(MacaoPlace,p,&i);
/*	EnvoiEtatQuestion("redu",2,"Implicite Place");*/
	EnvoiDebutEnsemble2(f,1);	
	EnvoiMiseEnEvidence(i);
	EnvoiFinEnsemble();

}	

void MontTransitionNeutre(int t,Entier_Liste MacaoTransition)
{	int i;
	char f[max];

	strcpy(f,"Transition Neutre");

/*	EnvoiEtatQuestion("redu",2,"Neutral Transition");*/	
	EnvoiDebutEnsemble2(f,1);	
	EntierNom(MacaoTransition,t,&i);
	EnvoiMiseEnEvidence(i);
	EnvoiFinEnsemble();

}	

void	MontPreAggl(int p,int t,Entier_Liste MacaoPlace,Entier_Liste MacaoTransition)
{	int i;
	char f[max];

        strcpy(f,"Pre Agglomeration");

/*        EnvoiEtatQuestion("redu",2,"Pre-Agglomeration");*/
      EnvoiDebutEnsemble2(f,1);

        EntierNom(MacaoPlace,p,&i);
        EnvoiMiseEnEvidence(i);
        
        EntierNom(MacaoTransition,t,&i);
        EnvoiMiseEnEvidence(i);
        
        EnvoiFinEnsemble();

}        

void	MontPostAggl(int p,int t,Entier_Liste MacaoPlace,Entier_Liste MacaoTransition)
{	int i;
	char f[max];

        strcpy(f,"Post Agglomeration");
/*        EnvoiEtatQuestion("redu",2,"Post-Agglomeration");*/
        EnvoiDebutEnsemble2(f,1);

        EntierNom(MacaoPlace,p,&i);
        EnvoiMiseEnEvidence(i);
                
        EntierNom(MacaoTransition,t,&i);
        EnvoiMiseEnEvidence(i);
	EnvoiFinEnsemble();

}
        

