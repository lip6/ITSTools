#ifdef HAVE_CONFIG_H
#include <config.h>
#endif
#ifdef FK_MAC_OS
#define	PRINTF_ALLOWED	1
#endif
#include <stdio.h> /* Converted by toFrameKit */
#include <string.h> /* Converted by toFrameKit */

#include "flots.h"
#include <signal.h>
/*#define PROCHAINEQUESTION EnvoiEtatQuestion("flots",100,"")*/
/*#define FINSANSTUER	EnvoiFinReponse2(2)*/

/*-----------------------------------------------------------------------------------*/
static void initpilote(char *f)
	{
#ifndef FK_MAC_OS
	FILE 	*Marguerite;
#endif
	strcpy(f,"FK_MNGR/.rdp_matrix");	/*	strcpy(f,".matrice"); */
/* A Zaza */
#ifndef FK_MAC_OS
	Marguerite=fopen(f,"r");
	if (Marguerite==NULL)
		{
		EnvoiReponseTrace("cpn_invariants:init pilote:Cannot open file FK_MNGR/.rdp_matrix !");
		FkExit(kFk_EndOnError);
		}
	else fclose(Marguerite);
#endif
	}
/*-----------------------------------------------------------------------------------*/
#ifdef avecSignal
void SegmentationErr(void)
{	  FkExit(kFk_EndOnError);
}
/*-----------------------------------------------------------------------------------*/
void BusErr(void)
{  FkExit(kFk_EndOnError);
}
#endif
/*-----------------------------------------------------------------------------------*/
FkEndStatus  FkServiceMain(int argc, char *argv[])
	{
#ifdef FK_MAC_OS
#pragma unused(argc, argv)
#endif
	FILE    *Donnees;
	char	f[256];
	Fkbool	result;
#ifdef avecSignal
	signal( SIGSEGV,SegmentationErr);
	signal(SIGBUS,BusErr);
#endif
	initpilote(&f[0]);
#ifdef FK_MAC_OS
	printf("Nom du fichier (.matrice):");
	scanf("%s",&f);	
#endif
	Donnees=fopen(f,"r");
	if (Donnees==NULL)
		{
		fprintf(stderr,"Can't open file %s\n", f);
		return(kFk_EndOnError);
		}

	FkClSendChannelCami(NULL, "TD(7:AMI-Net)");
	FkClSendChannelCami(NULL, "OB(1,1,5:place)");
	FkClSendChannelCami(NULL, "AT(6:weight,2,1,2,1:0)");
	FkClSendChannelCami(NULL, "FA()");

	EnvoiReponseTrace("Begin Flows Calculation");
/************ calcul des flots !!!!!!! *****/
	result=Calculflots(Donnees);
/****** Fichier  ferme***/	
	fclose(Donnees);
/****** arret mars ******/
#ifdef FK_MAC_OS
	printf("cpn_invariant end.\n");
#endif
	return((result==kFk_True) ? kFk_EndNoProblem:kFk_EndOnError);
}
/*-----------------------------------------------------------------------------------*/

