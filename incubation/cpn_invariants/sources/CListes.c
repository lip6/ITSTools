/************************************************************
  *	Auteurs		:	Jean-Luc MOUNIER + Xavier Bonnaire
  *	Programme	:	CListes.c
  *	Module		:	gestion des listes de Handles
  *
  *	Jeudi			17/07/86
  *	Vendredi	01/08/86	utilitaires BloquerOk/DeBloquer
  *	Mardi			05/08/86	viderListe debut
  *	Mercredi	15/10/86	cherche,enleve
  *	Vendredi	17/10/86	Parcours liste
  *	Samedi		14/02/87	MPW C
  *	Mercredi	12/08/87	trace
  * Mardi			24/07/90	mise ne place de compilation conditionnelle pour SunOS4
	*                     essentiellement parceque le compilateur C du Sun n'aime pas les prototypes
	*											de la norme ANSI-C
	*	Jeudi			26/07/90	ajout de ASupprimer et nbParcours avec Xavier Bonnaire
	* Lundi			04/05/92	passage � g++ (plus strict)
	* Mercredi	28/04/93	WITH_Handle
  ************************************************************/
#define	CListes
#include	"CListes.h"


#define	ERREUR(message) fprintf(stderr,message);

struct _ListElem
	{
	P_ListElem		pSuc;						/* pointeur vers l'�l�ment suivant	 */
	P_ListElem		pPre;						/* pointeur vers l'�l�ment pr�c�dent */
	void *				pDonnee;				/* pointeur vers la zone de donn�es quelconque */
	short					ASupprimer;			/* cet �l�ment doit �tre supprim� plus tard */
	};


struct _ListHeader				/*	uniquement t�te de liste */
	{
	P_ListElem		pSuc;						/* pointeur vers l'�l�ment suivant	 */
	P_ListElem		pPre;						/* pointeur vers l'�l�ment pr�c�dent */
	void *				pDonnee;				/* pour �tre compatible avec struc Liste =null important */
	short					ASupprimer;			/* nombre d'�l�ments � supprimer */
	short					nbParcours;			/* compte le nombre de ParcoursListe en cours pour cette liste */
	short					nbElem;					/* nombre d'�l�ments dans la liste */
	LiListType		liListType;			/* type de la liste */
	void *				pListPHash;			/* pointeur sur table de hachage si liste non standard */				
	};

static		CListe			 		pTeteParcours=NULL;	/* derni�re t�te de parcoursListe */
static		P_ListElem				hLastIndex=NULL;	/* dernier p_ListElem de parcoursListe pour insertion */
static		long					nbListes, nbElements;

/*-----------------------------------------------------------------------------------*/
void	ListeTrace(void)
	{
#ifndef	ForTOOLS
	AfficheNb("nombre de listes  = ",nbListes);
	AfficheNb("nombre d'�lements = ",nbElements);
#endif
	}
/*-----------------------------------------------------------------------------------*/
void	ListeInit(void)
	{
	hLastIndex=NULL;
	pTeteParcours=NULL;
	}
/*-----------------------------------------------------------------------------------*/
#define	TeteListe( pTete )	(pTete->pSuc)
#define	QueueListe( pTete )	(pTete->pPre)
#define	FinListe( pTete, hIndex ) ((pTete==(CListe)hIndex)? true:false)
#define	ElementListe( hIndex )	(hIndex->pDonnee)
#define	SuivantListe( hIndex )	(hIndex->pSuc)
#define	PrecedantListe( hIndex )	(hIndex->pPre)
#define	IncNbParcours(pTete)	((pTete->nbParcours)++)
#define	DecNbParcours(pTete)	(--(pTete->nbParcours))
#define	GetNbParcours(pTete)	(pTete->nbParcours)
#define	IsASupprimer(hIndex)	((hIndex->ASupprimer==0) ? false:true)
#define	IncNbAEnleve(pTete)		((pTete->ASupprimer)++)
#define	GetNbAEnleve(pTete)		(pTete->ASupprimer)
#define	SetASupprimer(hIndex)	(hIndex->ASupprimer=1)
#define	DecNbAEnleve(pTete)		(--(pTete->ASupprimer))
/*-----------------------------------------------------------------------------------*/
CListe	CreerListe(void)
	{
	CListe			pTete;
	nbListes++;	
	pTete=(CListe)MyMALLOC(sizeof(_ListHeader));
	if (pTete==NULL) {ERREUR(" CreerListe NewPtr==NULL"); return(NULL);}
		pTete->pSuc=(P_ListElem)pTete;
		pTete->pPre=(P_ListElem)pTete;
		pTete->pDonnee=NULL;				/* attention utilis� pour savoir si finliste sans pTete */
		pTete->ASupprimer=0;
		pTete->nbParcours=0;
		pTete->nbElem=0;
		pTete->liListType=LiStandard;
		pTete->pListPHash=NULL;
	return(pTete);
	}
typedef struct _ListHash	_ListHash;
typedef	_ListHash	*P_ListHash;
#define	_ListHashSize		32
#define	_ListHashMask		(_ListHashSize-1)
struct _ListHash
	{
	LiDataType 		isHandle;
	SqSize_t 				offset;
	CListe				tabHash[_ListHashSize];
	};
/*-----------------------------------------------------------------------------------*/
CListe LiNew(LiListType liListType,LiDataType isHandle,SqSize_t offset)
	{
	CListe					pTete;
	P_ListHash		pListPHash;
	short					ind;
	
	pTete=CreerListe();
	if (liListType==LiStandard)	return(pTete);
	pTete->liListType=liListType;
	pListPHash=(P_ListHash)MyMALLOC(sizeof(_ListHash));
	for (ind=0; ind<_ListHashSize; ind++)
		{
		pListPHash->tabHash[ind]=CreerListe();
		}
	pListPHash->offset=offset;
	pListPHash->isHandle=isHandle;
	pTete->pListPHash=pListPHash;
	return(pTete);
	}
/*-----------------------------------------------------------------------------------*/
static void LiAddInHash(CListe pTete,void * pDonnee)
	{
	register P_ListHash			pListPHash;
	pListPHash=pTete->pListPHash;
	if (pListPHash==NULL)
		{ ERREUR("LiAddInHash:Incorrect list hashing"); return; }
	AjoutListe(pListPHash->tabHash[(*((unsigned long *)(((Ptr)(*(Handle)pDonnee))+pListPHash->offset))) & _ListHashMask], pDonnee);
	}
/*-----------------------------------------------------------------------------------*/
static void LiSuppInHash(CListe pTete,void * pDonnee)
	{
	unsigned long * pValue;
	Ptr							ptr;
	short						ind;
	P_ListHash			pListPHash;

	pListPHash=pTete->pListPHash;
	if (pListPHash==NULL)
		{ ERREUR("LiAddInHash:Incorrect list hashing"); return; }
	ptr=(pListPHash->isHandle)? (Ptr)(*(Handle)pDonnee):(Ptr)pDonnee;
	pValue=(unsigned long *)(ptr+pListPHash->offset);
	ind= (*pValue) & _ListHashMask;
	EnleveElement(pListPHash->tabHash[ind], pDonnee);
	}
/*-----------------------------------------------------------------------------------*/
P_ListElem ChercheElement(CListe pTete,void * pDonnee)	/* r�sultat p_ListElem liste ou NULL */
	{
	register	P_ListElem			p_ListElem;
	for (	p_ListElem=TeteListe( pTete );
			! FinListe( pTete, p_ListElem );
			p_ListElem=SuivantListe( p_ListElem )
		 )
		{
		if ( pDonnee==ElementListe(p_ListElem) )
			if (IsASupprimer(p_ListElem)==false) return(p_ListElem);
		}
	return( NULL );
	}
/*-----------------------------------------------------------------------------------*/
P_ListElem AjoutListe(CListe pTete,void * pDonnee)							/* ajout en t�te */
	{
	P_ListElem 			hPremier,hTemp;
	if (pTete==NULL)
		{ERREUR(" AjoutListe pTete==NULL"); return(NULL);}
	if (pDonnee==NULL)
		{ERREUR(" AjoutListe pDonnee==NULL"); return(NULL);}
	hPremier=pTete->pSuc;
	hTemp=(P_ListElem)MyMALLOC(sizeof(_ListElem));
	if (hTemp==NULL) {ERREUR(" AjoutListe NewPtr==NULL"); return(NULL);}
		hTemp->pSuc=hPremier;
		hTemp->pPre=(P_ListElem)pTete;
		hTemp->pDonnee=pDonnee;
		hTemp->ASupprimer=0;
		hPremier->pPre=hTemp;
		pTete->pSuc=hTemp;
	nbElements++;
	(pTete->nbElem)++;
	if (pTete->liListType!=LiStandard)
		LiAddInHash(pTete, pDonnee);
	return(hTemp);
	}
/*-----------------------------------------------------------------------------------*/
bool	AjoutUListe(CListe pTete,void * pDonnee)			/* ajout Unique en t�te true si ajout OK */
	{
	if ( ChercheElement( pTete, pDonnee ) ) {return(false);}
	AjoutListe( pTete, pDonnee );
	return(true);
	}
/*-----------------------------------------------------------------------------------*/
P_ListElem	AjoutFinListe(CListe pTete,void * pDonnee)							/* ajout en queue */
	{
	P_ListElem 			hDernier,hTemp;
	if (pTete==NULL)
		{ERREUR(" AjoutFinListe pTete==NULL"); return(NULL);}
	if (pDonnee==NULL)
		{ERREUR(" AjoutFinListe pDonnee==NULL"); return(NULL);}

	hDernier=pTete->pPre;
	hTemp=(P_ListElem)MyMALLOC( sizeof(_ListElem) );
	if (hTemp==NULL) {ERREUR(" AjoutFinListe NewPtr==NULL"); return(NULL);}
		hTemp->pSuc=(P_ListElem)pTete;
		hTemp->pPre=hDernier;
		hTemp->pDonnee=pDonnee;
		hTemp->ASupprimer=0;
		hDernier->pSuc=hTemp;
		pTete->pPre=hTemp;
	nbElements++;
	(pTete->nbElem)++;
	if (pTete->liListType!=LiStandard)
		LiAddInHash(pTete, pDonnee);
	return(hTemp);
	}
/*-----------------------------------------------------------------------------------*/
bool	AjoutUFinListe(CListe pTete,void * pDonnee )			/* ajout Unique en t�te true si ajout OK */
	{
	if ( ChercheElement( pTete, pDonnee ) ) {return(false);}
	AjoutFinListe( pTete, pDonnee );
	return(true);
	}
/*-----------------------------------------------------------------------------------*/
void	ParcoursInsereAvant(void * pDonnee)						/* ajout avant hLastIndex */
	/*	attention ne marche pas lors d'appels r�cursifs de ParcoursListe 
			car la variable hLastIndex ne sera peut-�tre plus bonne
	*/
	{
	P_ListElem 			hDernier,hTemp;
	if (hLastIndex==NULL) return;
	hDernier=hLastIndex->pPre;
	hTemp=(P_ListElem)MyMALLOC( sizeof(_ListElem) );
	if (hTemp==NULL) {ERREUR(" ParcoursInsereAvant NewPtr==NULL"); return;}
		hTemp->pSuc=hLastIndex;
		hTemp->pPre=hDernier;
		hTemp->pDonnee=pDonnee;
		hTemp->ASupprimer=0;
		hDernier->pSuc=hTemp;
		hLastIndex->pPre=hTemp;
	nbElements++;
	(pTeteParcours->nbElem)++;
	}
/*-----------------------------------------------------------------------------------*/
void	ParcoursInsereApres(void * pDonnee)						/* ajout apr�s hLastIndex */
	/*	attention ne marche pas lors d'appels r�cursifs de ParcoursListe 
			car la variable hLastIndex ne sera peut-�tre plus bonne
	*/
	{
	P_ListElem 			hLastSucc,hTemp;
	if (hLastIndex==NULL) return;
	hLastSucc=hLastIndex->pSuc;
	hTemp=(P_ListElem)MyMALLOC( sizeof(_ListElem) );
	if (hTemp==NULL) {ERREUR(" ParcoursInsereAvant NewPtr==NULL"); return;}
		hTemp->pDonnee=pDonnee;
		hTemp->ASupprimer=0;
		hTemp->pSuc=hLastSucc;
		hTemp->pPre=hLastIndex;
		hLastSucc->pPre=hTemp;
		hLastIndex->pSuc=hTemp;
	nbElements++;
	(pTeteParcours->nbElem)++;
	}
/*-----------------------------------------------------------------------------------*/
static P_ListElem PrecedentListe(P_ListElem hIndex) 
	{
	return(hIndex->pPre);	
	}
/*-----------------------------------------------------------------------------------*/
void * ParcoursSuivant(void)
	{
	P_ListElem hIndex;
	if (hLastIndex==NULL) return(NULL);
/* find next element which will not be deleted */
	for	(	hIndex=SuivantListe(hLastIndex);
				ElementListe(hIndex);		/* head of the liste (element is null) */
				hIndex=SuivantListe(hIndex)
			)
		if ( ! IsASupprimer(hIndex) )	break;
	return( ElementListe(hIndex) );				/* ElementListe is null on the head */
	}
/*-----------------------------------------------------------------------------------*/
void * ParcoursPrecedent(void)
	{
	P_ListElem hIndex;
	if (hLastIndex==NULL) return(NULL);
/* find previous element which will not be deleted */
	for	(	hIndex=PrecedentListe(hLastIndex);
				ElementListe(hIndex);		/* head of the liste (element is null) */
				hIndex=PrecedentListe(hIndex)
			)
		if ( ! IsASupprimer(hIndex) )	break;
	return( ElementListe(hIndex) );				/* ElementListe is null on the head */
	}
/*-----------------------------------------------------------------------------------*/
void	ParcoursSauve(CListeSauve *cListeSauve)
	{
	if (cListeSauve==NULL)
		{ ERREUR("ListePremier:ParcoursSauve==NULL"); return; }
	cListeSauve->pTeteParcours=pTeteParcours;
	cListeSauve->hLastIndex=hLastIndex;
	}
/*-----------------------------------------------------------------------------------*/
void	ParcoursRestore(CListeSauve *cListeSauve)
	{
	if (cListeSauve==NULL)
		{ ERREUR("ListePremier:ParcoursRestore==NULL"); return; }
	pTeteParcours=cListeSauve->pTeteParcours;
	hLastIndex=cListeSauve->hLastIndex;
	}
/*-----------------------------------------------------------------------------------*/
void * ListePremier(CListe pTete)
	{
	register	P_ListElem			hIndex;
	if (pTete==NULL)
		{ ERREUR("ListePremier:pTete==NULL"); return(NULL); }

/* find first element which will not be deleted */
	for	(	hIndex=TeteListe(pTete);
				ElementListe(hIndex);		/* head of the liste (element is null) */
				hIndex=SuivantListe(hIndex)
			)
		if ( ! IsASupprimer(hIndex) )	break;

	hLastIndex=hIndex;pTeteParcours=pTete;
	if ( FinListe( pTete, hIndex ) ) return(NULL);
	return(ElementListe( hIndex ));
	}
/*-----------------------------------------------------------------------------------*/
void * ListeDernier(CListe pTete)
	{
	register	P_ListElem			hIndex;
	if (pTete==NULL)
		{ ERREUR("ListeDernier:pTete==NULL"); return(NULL); }

/* find last element which will not be deleted */
	for	(	hIndex=pTete->pPre;
				ElementListe(hIndex);		/* head of the liste (element is null) */
				hIndex=PrecedentListe(hIndex)
			)
		if ( ! IsASupprimer(hIndex) )	break;

	hLastIndex=hIndex;pTeteParcours=pTete;
	if ( FinListe( pTete, hIndex ) ) return(NULL);
	return( ElementListe( hIndex ) );
	}
/*-----------------------------------------------------------------------------------*/
bool UnSeulElement(CListe pTete)			/* true si la liste ne comporte qu'un seul �l�ment */
	{
	if (ListePremier(pTete)==NULL)	return(false);	/* liste vide */
	if (ListePremier(pTete)==ListeDernier(pTete)) return(true); else return(false);
	}
/*-----------------------------------------------------------------------------------*/
/* vide compl�tement la liste + traitement des �l�ments ? */
/* attention ne peut pas faire du vide s�lectif (pb chainage) */
void	ViderListe(CListe pTete)
	{
	P_ListElem			p_ListElem,index2;
	if (pTete==NULL)
		{ ERREUR("ViderListe:pTete==NULL"); return; }

	if (GetNbParcours(pTete))
		{ERREUR("# do not remove all while scanning the list !"); return;}
	for 	(	p_ListElem=TeteListe( pTete ); 			/* parcours les �l�ments de la Liste */
				! FinListe( pTete, p_ListElem );
			)
		{
		index2=SuivantListe( p_ListElem );
		MyFREE(p_ListElem);
		nbElements--;
		(pTete->nbElem)--;
		p_ListElem=index2;
		}
/* vider la t�te -> t�te pleine de vide ! */
	pTete->pSuc=(P_ListElem)pTete;
	pTete->pPre=(P_ListElem)pTete;
	pTete->pDonnee=NULL;		/* normalement inutile */
	pTete->ASupprimer=0;		/* normalement inutile */
	pTete->nbParcours=0;		/* normalement inutile */
	pTete->nbElem=0;		/* normalement inutile */
	
	if (pTete->liListType!=LiStandard)
		{
		short						ind;
		P_ListHash			pListPHash;

		pListPHash=pTete->pListPHash;
		for (ind=0; ind<_ListHashSize; ind++)
			{
			ViderListe(pListPHash->tabHash[ind]);
			}
		}
	}
/*-----------------------------------------------------------------------------------*/
/* Idem vider liste mais lorsque l'on est en cours de parcours  */
void ParcoursViderListe(CListe pTete)
	{
	P_ListElem			p_ListElem,index2;
	if (pTete==NULL)
		{ ERREUR("ViderListe:pTete==NULL"); return; }

	if (GetNbParcours(pTete)==0)
		{ERREUR("# do not use Parcours ViderListe if you are not scanning the list !"); return;}
	for 	(	p_ListElem=TeteListe( pTete ); 			/* parcours les �l�ments de la Liste */
				! FinListe( pTete, p_ListElem );
			)
		{
		index2=SuivantListe( p_ListElem );
		if (IsASupprimer(p_ListElem)==false)
			{
			SetASupprimer(p_ListElem);
			IncNbAEnleve(pTete);
			}
		p_ListElem=index2;
		}
	}
/*-----------------------------------------------------------------------------------*/
void EnleveElementIndex(CListe pTete,P_ListElem p_ListElem)
	{			/* enl�ve l'�l�ment point� par p_ListElem de la liste hTete si il n'y a pas de ParcoursList en cours */
				/* sur cette liste. Sinon indique que cet �l�ment devra �tre supprim� plus tard */
				/* (� la fin du dernier parcoursListe) et incr�mente le nombre d'�l�ments enlev�s */
	register	P_ListElem			indexTemp;
	
	if (pTete->liListType!=LiStandard)
		if (ElementListe(p_ListElem))
			{
			LiSuppInHash(pTete, ElementListe(p_ListElem));
			ElementListe(p_ListElem)=NULL;
			}
	if (GetNbParcours(pTete)==0)
		{
		indexTemp=p_ListElem->pPre;						/* p_ListElem du pr�c�dent */
		indexTemp->pSuc=p_ListElem->pSuc;
		indexTemp=p_ListElem->pSuc;
		indexTemp->pPre=p_ListElem->pPre;
		MyFREE(p_ListElem);
		nbElements--;
		(pTete->nbElem)--;
		}
	else
		{
		SetASupprimer(p_ListElem);
		IncNbAEnleve(pTete);
		}
	}
/*-----------------------------------------------------------------------------------*/
short	NbElementListe(CListe pTete)
	{
	if (pTete==NULL)
		{ERREUR(" NbElementListe pTete==NULL"); return(0);}
	return( (pTete->nbElem) - GetNbAEnleve(pTete) );
	}
/*-----------------------------------------------------------------------------------*/
void EnleveElement(CListe pTete,void * pDonnee)		/* cherche l'�l�ment et l'enl�ve si il existe erreur sinon*/
	{
	register	P_ListElem			p_ListElem;
	if (pTete==NULL)
		{ERREUR(" EnleveElement pTete==NULL"); return;}
	p_ListElem=ChercheElement( pTete, pDonnee );
	if (p_ListElem==NULL)
		{ ERREUR("EnleveElement:l'�l�ment n'appartient pas � la liste");return;}
	else EnleveElementIndex( pTete,p_ListElem );
	}
/*-----------------------------------------------------------------------------------*/
void EnleveElementCourant(void)		/* enl�ve courant si il existe erreur sinon*/
	{
	if (hLastIndex==NULL)
		{ERREUR("EnleveElementCourant:hLastIndex == NULL");return;}
	if (IsASupprimer(hLastIndex))
		{ERREUR("EnleveElementCourant:�l�ment d�j� supprim�");return;}
	else EnleveElementIndex(pTeteParcours, hLastIndex);
	}
/*-----------------------------------------------------------------------------------*/
bool	EnleveElementSiExiste(CListe pTete,void * pDonnee)					/* cherche l'�l�ment et l'enl�ve si il existe */
	{
	register	P_ListElem			p_ListElem;
 	if (pTete==NULL)
 		{ERREUR("EnleveElementSiExiste pTete==NULL"); return(false);}
	p_ListElem=ChercheElement(pTete, pDonnee);
	if (p_ListElem==NULL) { return(false);}
	EnleveElementIndex(pTete, p_ListElem);
	return(true);
	}
/*-----------------------------------------------------------------------------------*/
static void LiSupprimeEventuel(CListe pTete)				
	{	/* si on fini le dernier Parcours de cette liste il faut regarder si il y a des �l�ments a supprimer */
	if (DecNbParcours(pTete)==0)
		if (GetNbAEnleve(pTete)!=0)		
			{
			P_ListElem			p_ListElem, index2;
			for	(	p_ListElem=TeteListe( pTete );
						! FinListe( pTete, p_ListElem );
						p_ListElem=index2
					)
				{
				index2=SuivantListe( p_ListElem );
				if (IsASupprimer(p_ListElem))
					{
					EnleveElementIndex(pTete, p_ListElem);	/* NbParcours==0 donc il sera r�ellement enlev� */
					if (DecNbAEnleve(pTete)==0)	return;	/* D�cr�mente et test si il n'y en a plus � enlever */
					}
				}
			ERREUR("LiSupprimeEventuel:NbAEnleve is not null");
			}
	}
/*-----------------------------------------------------------------------------------*/
void * FindInListHashHandleULong(CListe pTete,unsigned long ident,SqSize_t offset)
	{
	register	P_ListHash	pListPHash;
	pListPHash=pTete->pListPHash;
	if (pListPHash==NULL)	{ ERREUR("FindInListHash:Incorrect list hashing"); return(NULL); }
	return(FindInList(pListPHash->tabHash[ident & _ListHashMask], IS_HANDLE, &ident, sizeof(unsigned long), offset));
	}
/*-----------------------------------------------------------------------------------*/
void * FindInList(CListe pTete,LiDataType isHandle,const void *pRecherche,SqSize_t size,SqSize_t offset)
	{
	P_ListElem			p_ListElem;
	Ptr							ptr;
	
	if (pTete==NULL)
		{ERREUR(" FindInList pTete==NULL"); return(NULL);}
/*	if (pTete->liListType!=LiStandard)	 v�rifier les type isHandle, size, offset */
/*		return(FindInListHash(pTete, isHandle, pRecherche, size, offset)); */
	for	(	p_ListElem=TeteListe( pTete );
				! FinListe( pTete, p_ListElem );
				p_ListElem=SuivantListe(p_ListElem)
			)
		{
		if (IsASupprimer(p_ListElem)==false) 	/* l'�l�ment n'est pas � supprimer, il est valide */
			{
			ptr=(isHandle)? (Ptr)(*(Handle)ElementListe(p_ListElem)):(Ptr)ElementListe(p_ListElem);
			if (memcmp(pRecherche, ptr+offset, size)==0) return((void *)ElementListe(p_ListElem));
			}
		}
	return( NULL );
	}
/*-----------------------------------------------------------------------------------*/
/* parcours la liste de t�te: tete, applique l'action: Action */
/* resultat (void *) Action( element, param1, param2, ... ) */
/* si r�sultat NULL: on continue le parcours */
void *	ParcoursListe(CListe pTete,ParcoursProcPtr Action,void *param1,void *param2,void *param3,void *param4,void *param5,void *param6,void *param7,void *param8 )
	{
	P_ListElem			p_ListElem;
	void *					resultat;
	if (pTete==NULL)
		{ERREUR(" ParcoursListe pTete==NULL"); return(NULL);}
	IncNbParcours(pTete);
	for	(	p_ListElem=TeteListe( pTete );
				! FinListe( pTete, p_ListElem );
				p_ListElem=SuivantListe(p_ListElem)
			)
		{
		if (IsASupprimer(p_ListElem)==false) 	/* l'�l�ment n'est pas � supprimer, il est valide */
			{
			hLastIndex=p_ListElem;pTeteParcours=pTete;
			resultat=(  (*Action) ( (void *)ElementListe(p_ListElem), param1, param2, param3, param4, param5, param6, param7, param8 ) );
			if (resultat)
				{
				LiSupprimeEventuel(pTete);
				return( resultat );
				}
			}
		}
	LiSupprimeEventuel(pTete);
	return( NULL );
	}
/*-----------------------------------------------------------------------------------*/
/* idem parcoursListe mais � l'envers */
void * ParcoursInverse(CListe pTete,ParcoursProcPtr Action,void *param1,void *param2,void *param3,void *param4,void *param5,void *param6,void *param7,void *param8 )
	{
	P_ListElem			p_ListElem;
	void *					resultat;
	if (pTete==NULL)
		{ERREUR(" ParcoursInverse pTete==NULL"); return(NULL);}
	IncNbParcours(pTete);
	for	(	p_ListElem=QueueListe( pTete );
				! FinListe( pTete, p_ListElem );
				p_ListElem=PrecedantListe(p_ListElem)
			)
		{
		if (IsASupprimer(p_ListElem)==false) 	/* l'�l�ment n'est pas � supprimer, il est valide */
			{
			hLastIndex=p_ListElem;pTeteParcours=pTete;
			resultat=(  (*Action) ( (void *)ElementListe(p_ListElem), param1, param2, param3, param4, param5, param6, param7, param8 ) );
			if (resultat)
				{
				LiSupprimeEventuel(pTete);
				return( resultat );
				}
			}
		}
	LiSupprimeEventuel(pTete);
	return( NULL );
	}
/*-----------------------------------------------------------------------------------*/
void DetruireListe(CListe pTete)				/* d�tuit la t�te de liste v�rifie que la liste est vide */
	{
	if (pTete==NULL) return;
	if (FinListe(pTete,TeteListe(pTete))==false)
		{ ERREUR("Not empty list"); return; }
	nbListes--;	
	if (pTete->liListType!=LiStandard)
		{
		if (pTete->pListPHash==NULL)
			{ ERREUR("Incorrect list hashing"); return; }
		MyFREE(pTete->pListPHash);pTete->pListPHash=NULL;
		}
	MyFREE(pTete);
	}
/*-----------------------------------------------------------------------------------*/
