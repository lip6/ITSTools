/* Declaration des operations du TYPE1 = entier	*/

#define TYPE1 		int
#define T1CREER()	0
#define T1NULL(a) 	((a==0) ? true : false)
#define T1DETR(a)	
#define T1DUPL(a,b)	b=a
#define	T1ADD(a,b)	b=a+b
#define T1ORDRE(a,b)	((a<b) ? 1 : ((a>b) ? -1 : 0))

/* Declaration des operations du TYPE2 = entier */

#define TYPE2           int
#define T2CREER()	0	
#define T2DETR(f)       
#define T2DUPL(f,g)     g=f
#define T2ORDRE(f,g)    ((f<g) ? 1 : ((f>g) ? -1 : 0))

/*  Declaration des operations sur les monomes */

#define TYPE3			Mo_Type
#define T3CREER		MoCreer
#define T3NULL		MoTestUn
#define T3DETR		MoDetr
#define T3ZERO		MoUn
#define T3DUPL		MoDupl
#define T3ADD			MoMult
#define T3ORDRE		MoOrdre


