#ifndef _MacaoReduction_
#define	_MacaoReduction_
	
#ifdef __cplusplus
extern "C" {
#endif
extern	void					MacaoRedPlaceImplicite(int p,Entier_Liste MacaoPlace);
extern	void					MacaoRedTransitionNeutre(int t,Entier_Liste MacaoTransition);
extern	void					MacaoRedPre(int p,int t,Entier_liste Lp,Mat_Type MatPre,
																	Mat_Type MatOperation,Entier_liste NomVariable,
																	Dom_Liste DomainePlace,Classe_Liste SpecifClasse,
																	Entier_liste NomVariableFlot,Var_Liste NomClasse,
																	Var_Liste NomPlace,Var_Liste NomParametre,
																	Entier_Liste MacaoPlace,Entier_Liste MacaoTransition,
																	Arc_Matrice MacaoPre,Arc_Matrice MacaoPost,
																	int *MacaoMax);
extern	void					MacaoRedPost(int p,int t,Entier_liste Lp,Mat_Type Mat_post,
																	Mat_Type MatOperation,Entier_liste NomVariable,
																	Dom_Liste DomainePlace,Classe_Liste SpecifClasse,
																	Entier_liste NomVariableFlot,Var_Liste NomClasse,
																	Var_Liste NomPlace,Var_Liste NomParametre,
																	Entier_Liste MacaoPlace,Entier_Liste MacaoTransition,
																	Arc_Matrice MacaoPre,Arc_Matrice MacaoPost,
																	int *MacaoMax);
#ifdef __cplusplus
}
#endif
#endif

