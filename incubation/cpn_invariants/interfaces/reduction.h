#ifndef _reduction_
#define	_reduction_
	
#ifdef __cplusplus
extern "C" {
#endif
/* Agglomeration */
extern	bool				RedTestPost(int *p,int f,
                Entier_Liste Sortieh,Entier_Liste Entrep,
                Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
                int NbVariable,Fonct_Type FI);
extern	void 					RedAgglomeration(int p,int h,
                Entier_Liste EntreOuSortieh,
                Mat_Type MatOperation,Mat_Type MatPre,Mat_Type MatPost,
		Mat_Type MatInitial,
		Entier_Liste ExistePlace,Entier_Liste ExisteTransition,
                Fonct_Type FI);
/* Place implicite 1 Pre(p,.)=Post(p,.) <M(p)*/
extern	bool				RedTestPlaceImplicite1(int p,
                Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
		int NbVariable);
extern	void					RedPlaceImplicite1(int p,Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,Entier_Liste ExistePlace,
 		Mat_Type MatOperation,Mat_Type MatFlot,Mat_Type MatFlotValeur,int *NbFlot);
/* Place implicite 2 */
/* ........ A jeter peut etre ........... */
/*...
extern	bool RedTestPlaceImplicite2(int p,int *q,Entier_Liste Entrep,
                Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
                int NbVariable,Fonct_Type G);
                                
extern	void	RedPlaceImplicite2(int p,int q,
        	Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,Entier_Liste ExistePlace,Mat_Type MatOperation,
                Fonct_Type G,Mat_Type MatFlot,Mat_Type MatFlotValeur,int *NbFlot);
...*/
/* Place simplication 2 p=f.q */
extern	bool				RedTestPlaceSimpl2(int p,int *q,
		Mat_Type M1,Mat_Type M2,Mat_Type MatInitial,
		int NbVariable,Fonct_Type G);
extern	void					RedPlaceSimpl2(int p,int q,
	Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
	Entier_Liste ExistePlace,Mat_Type MatOperation,
	Fonct_Type G,Mat_Type MatFlot,Mat_Type MatFlotValeur,int *NbFlot);
/* Place simplification 3 p+f.q */
extern	bool				RedTestPlaceSimpl3(int p,int *q,
		Mat_Type M1,Mat_Type M2,
		int NbVariable,Fonct_Type G);
extern	void					RedPlaceSimpl3(int p,int q,
	Mat_Type MatInitial,Mat_Type MatPre,Mat_Type MatPost,
	Entier_Liste ExistePlace,Mat_Type MatOperation,
	Fonct_Type G,Mat_Type MatFlot,Mat_Type MatFlotValeur,int *NbFlot);
/* Transition neutre : */
extern	bool				RedTestTransitionNeutre1(int t,int *p,int *tm,
                Mat_Type MatPre,Mat_Type MatPost,int NbVariable);
extern	bool				RedTestTransitionNeutre2(int t,Mat_Type MatPre,Mat_Type MatPost);
extern	void					RedTransitionNeutre(int t,Mat_Type MatPre,Mat_Type MatPost,Entier_Liste ExisteTransition);
/* Heuristique de l`identite bis avec une place en entree degageant 
seulement lesplace en sortie */
extern	bool	RedTestIdentite2(int *p,int h,
		Entier_Liste Entrep,Entier_Liste Sortiep,
		Mat_Type MatPre,Mat_Type MatPost,
		int NbVariable,Fonct_Type FI,bool *PdansPreOuPost );
extern	void					RedIdentite2(int p,int h,
		bool PdansPreOuPost,Entier_Liste Entreh, Entier_Liste Sortieh,
		Mat_Type MatOperation,Mat_Type M1,Mat_Type M2,
		Mat_Type MatInitial,
		Entier_Liste ExistePlace,Entier_Liste ExisteTransition,
		Fonct_Type FI,bool *test );
/* Heuristique de l'identite avec 1 place en entree et une en sortie */
extern	bool				RedTestIdentitepq(int *p,int *q,int t,
		Entier_Liste Entret,Entier_Liste Sortiet,
		Mat_Type MatPre,Mat_Type MatPost,
		int NbVariable,Fonct_Type FIp,Fonct_Type FIq);
extern	void					RedIdentitepq(int p1,int p2,int h,
		Entier_Liste Entreh, Entier_Liste Sortieh,
		Mat_Type MatOperation,Mat_Type MatPre,Mat_Type Mat_Post,
		Mat_Type MatInitial,
		Entier_Liste ExistePlace,Entier_Liste ExisteTransition,
		Fonct_Type FI1,Fonct_Type FI2);
#ifdef __cplusplus
}
#endif
#endif
