#ifndef _ioreseau_
#define	_ioreseau_
	
#ifdef __cplusplus
extern "C" {
#endif
extern	bool				ReseauEntre(	int     *NbPlace,
				int	*NbTransition,
				int	*NbClasse,
				int	*NbVariable,
				Mat_Type MatPre,
				Mat_Type MatPost,
				Mat_Type MatInitial,
				Entier_Liste    ExistePlace,
				Entier_Liste    ExisteTransition,
				Var_Liste       NomPlace,
				Var_Liste       NomTransition,
				Var_Liste       NomParametre,
				Var_Liste       NomClasse,
				Var_Liste       NomVariable,
				Classe_Liste    SpecifClasse,
				Dom_Liste       DomainePlace,
				int             *MacaoMax,
				Entier_Liste    MacaoPlace,
				Entier_Liste    MacaoTransition,
				Arc_Matrice     MacaoPre,
				Arc_Matrice     MacaoPost,
				FILE    	*Donnees);
extern	void					ReseauSort(	int     NbPlace,
				int	NbTransition,
				int	NbClasse,
				int	NbVariable,
				Mat_Type MatPre,
				Mat_Type MatPost,
				Mat_Type MatInitial,
				Var_Liste       NomPlace,
				Var_Liste       NomTransition,
				Var_Liste       NomParametre,
				Var_Liste       NomClasse,
				Var_Liste       NomVariable,
				Classe_Liste    SpecifClasse,
				Dom_Liste       DomainePlace);
extern	void					ReseauSortSimple(	int     NbPlace,
				int	NbTransition,
				int	NbClasse,
				int	NbVariable,
				Mat_Type MatPre,
				Mat_Type MatPost,
				Mat_Type MatInitial,
				Var_Liste       NomPlace,
				Var_Liste       NomTransition,
				Var_Liste       NomParametre,
				Var_Liste       NomClasse,
				Var_Liste       NomVariable,
				Classe_Liste    SpecifClasse,
				Dom_Liste       DomainePlace);
#ifdef __cplusplus
}
#endif
#endif
