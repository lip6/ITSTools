/* Declaration des operation du TYPE1 = Fonctions */

#define TYPE1           Fonct_Type
#define T1CREER         FonctCreer
#define T1NULL          FonctTestZero
#define T1DETR          FonctDetr
#define T1DUPL          FonctDupl
#define T1ADD           FonctAdd
#define T1ORDRE		FonctOrdre
#define T1MULT		FonctMult
#define T1OPPOSE	FonctOppose
#define	T1SIGNE		FonctSigne

/* Declaration des operations du TYPE2 = entier */

#define TYPE2           int
#define T2CREER()       0
#define T2DETR(f)
#define T2DUPL(f,g)     g=f
#define T2ORDRE(f,g)    ((f<g) ? 1 : ((f>g) ? -1 : 0))


/*  Declaration des operations sur les vecteurs (places) */

#define TYPE3           Vect_Type
#define T3CREER         VectCreer
#define T3NULL          VectTestZero
#define T3DETR          VectDetr
#define T3ZERO          VectZero
#define T3DUPL          VectDupl
#define T3ADD           VectAdd
#define T3ORDRE		VectOrdre
#define T3MULT1Gauche	VectMultGaucheFonct
#define T3OPPOSE	VectOppose
#define	T3SIGNE		VectSigne



