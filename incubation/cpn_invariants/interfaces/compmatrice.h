/* Declaration des operation du TYPE1 = Vecteur */

#define TYPE1           Vect_Type
#define T1CREER         VectCreer
#define T1NULL          VectTestZero
#define T1DETR          VectDetr
#define T1DUPL          VectDupl
#define T1ADD           VectAdd
#define T1ORDRE		VectOrdre
#define	T1OPPOSE	VectOppose

/* Declaration des operations du TYPE2 = entier */

#define TYPE2           int
#define T2CREER()       0
#define T2DETR(f)
#define T2DUPL(f,g)     g=f
#define T2ORDRE(f,g)    ((f<g) ? 1 : ((f>g) ? -1 : 0))


/*  Declaration des operations sur les matrices */

#define TYPE3           Mat_Type
#define T3CREER         MatCreer
#define T3NULL          MatTestZero
#define T3DETR          MatDetr
#define T3ZERO          MatZero
#define T3DUPL          MatDupl
#define T3ADD           MatAdd
#define T3ORDRE		MatOrdre
#define T3OPPOSE	MatOppose





