#ifndef _iofonction_
#define	_iofonction_
	
#ifdef __cplusplus
extern "C" {
#endif
extern	bool				FonctEntre(Fonct_Type F,char *f,Var_Liste NomParametre,
																Classe_Liste SpecifClasse,Var_Liste NomVariable,
																Entier_Liste Domaine);

extern	void					FonctSort(Fonct_Type F,char *f,Var_Liste NomParamatre,
																Classe_Liste SpecifClasse,Var_Liste NomVariable,
																Entier_Liste Domaine);
#ifdef __cplusplus
}
#endif
#endif


