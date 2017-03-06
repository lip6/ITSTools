/*	#include	<CListes.h>	*/
/************************************************************
  *	Auteur			:	Jean-Luc MOUNIER
  *	Programme		:	CListes.h
  *	Module			:	Fen�tre d'historique
  *	
	* Samedi		16/05/92	__cplusplus
	* Dimanche	22/11/92	void *
  ************************************************************/
#ifndef _CListes_
#define	_CListes_


#include <stdbool.h>

typedef struct _ListElem	_ListElem;
typedef	_ListElem	*P_ListElem;
typedef struct _ListHeader _ListHeader;
typedef		_ListHeader	*CListe;

typedef enum { LiStandard=0, LiHashULong } LiListType;
typedef enum { IS_HANDLE=true, IS_POINTER=false } LiDataType;

typedef struct CListeSauve CListeSauve;

struct	CListeSauve
	{
	CListe			 		pTeteParcours;
	P_ListElem				hLastIndex;
	};

typedef void *(*ParcoursProcPtr)(void *element, ...); /* void *param1, void *param2, void *param3, void *param4 */

#ifdef __cplusplus
extern "C" {
#endif
extern	void					ListeInit(void);
extern	CListe				LiNew(LiListType liListType,LiDataType isHandle,size_t offset);
extern	void					ListeTrace(void);
extern	CListe				CreerListe(void);
extern	P_ListElem		AjoutFinListe(CListe	hTete,void *hDonnee);
extern	P_ListElem		AjoutListe(CListe	hTete,void *hDonnee);
extern	bool				AjoutUListe(CListe	hTete,void *hDonnee);
extern	bool				AjoutUFinListe(CListe	hTete,void *hDonnee);
extern	void					ParcoursInsereAvant(void *hDonnee);
extern	void					ParcoursInsereApres(void * pDonnee);
extern	void *				ParcoursSuivant(void);
extern	void *				ParcoursPrecedent(void);
extern	void					ParcoursSauve(CListeSauve *cListeSauve);
extern	void					ParcoursRestore(CListeSauve *cListeSauve);
extern	void *				ListePremier(CListe	hTete);
extern	void *				ListeDernier(CListe	hTete);
extern	bool				UnSeulElement(CListe	hTete);
extern	void					ViderListe(CListe	hTete);
extern	void 					ParcoursViderListe(CListe pTete);
extern	void					EnleveElement(CListe	hTete,void *hDonnee);
extern	bool				EnleveElementSiExiste(CListe hTete,void *hDonnee);
extern	void					EnleveElementCourant(void);
extern	void *				FindInListHashHandleULong(CListe pTete,unsigned long ident,size_t offset);
extern	void *				FindInList(CListe	hTete,LiDataType isHandle,const void *pRecherche, size_t size, size_t offset);
#ifdef CListes
extern	void *				ParcoursListe(CListe hTete,ParcoursProcPtr Action,void *param1,void *param2,void *param3,void *param4,void *param5,void *param6,void *param7,void *param8);
extern	void *				ParcoursInverse(CListe hTete,ParcoursProcPtr Action,void *param1,void *param2,void *param3,void *param4,void *param5,void *param6,void *param7,void *param8);
#else
extern	void *				ParcoursListe(CListe	hTete,ParcoursProcPtr Action, ...);	
extern	void *				ParcoursInverse(CListe	hTete,ParcoursProcPtr Action, ...);
#endif
extern	void					DetruireListe(CListe	hTete);
extern	P_ListElem		ChercheElement(CListe hTete,void *hDonnee);
extern	void 					EnleveElementIndex(CListe pTete,P_ListElem p_ListElem);
extern	short					NbElementListe(CListe hTete);
#ifdef __cplusplus
}
#endif
#endif
