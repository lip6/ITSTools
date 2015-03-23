#ifndef _composition_
#define	_composition_

#include "CListes.h"
/********** Definition de type compose *********************************/
struct CoType
{	TYPE1	coef;
	TYPE2	elt ;
};

typedef		struct CoType CoType;
typedef		CoType  		*PCoType;

/* pour d�finir le TYPE3 */
#include	"fonction.h"
#include	"matrice.h"
#include	"monome.h"
#include	"poly.h"
	
#ifdef __cplusplus
extern "C" {
#endif
/****************   Creation de l'objet F ******************************/
extern	TYPE3					T3CREER(void);
/*****************  Test a zero  de F ***********************************/
extern	bool				T3NULL(TYPE3 F);
/*****************  Destruction d'une liste******************************/
extern	void					T3ZERO(TYPE3 F);
extern	void					T3DETR(TYPE3 F);
/**************************  Duplication **********************************/   
extern	void					T3DUPL(TYPE3 F, TYPE3 G);
/***************************** ADDITION ************************************/
extern	void					T3ADD(TYPE3 F, TYPE3 G);		
/****************** Ordre **************************************************/
#ifdef T3ORDRE
extern	int						T3ORDRE(TYPE3 F, TYPE3 G);
#endif		
/************************ T3 OPPOSE F=-F *********************************/
#ifdef	T3OPPOSE
extern	void					T3OPPOSE(TYPE3 F);
#endif
#ifdef	T3SIGNE
extern	int						T3SIGNE(TYPE3 F);
#endif
#ifdef __cplusplus
}
#endif
#endif
