#ifndef _iovecteur_
#define	_iovecteur_
	
#ifdef __cplusplus
extern "C" {
#endif
extern	bool				VectEntre(Vect_Type V,FILE *FichierDonnees,Var_Liste NomParametre,
															Classe_Liste SpecifClasse,Var_Liste NomVariable,
															Entier_Liste Domaine);
extern	void					VectSort(Vect_Type V,char *f,Var_Liste NomParametre,
															Classe_Liste SpecifClasse,Var_Liste NomVariable,
															Var_Liste NomClasse,Var_Liste NomTransition);
extern	bool				VectMarquageEntre(Vect_Type V,FILE *FichierDonnees,
															Var_Liste NomParametre,Classe_Liste SpecifClasse,
															Var_Liste NomVariable,Entier_Liste Domaine);
#ifdef __cplusplus
}
#endif
#endif
