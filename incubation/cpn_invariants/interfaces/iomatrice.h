#ifndef _iomatrice_
#define	_iomatrice_
	
#ifdef __cplusplus
extern "C" {
#endif
extern	bool				MatEntre(Dom_Liste Domaineplace,Mat_Type M,
															FILE *FichierDonnees,Var_Liste NomParametre,
															Classe_Liste SpecifClasse,Var_Liste NomVariable,
															int NbPlace,bool OuiDomainePlace);
                                
extern	void					MatSort(Mat_Type V,char *f,Var_Liste NomParametre,
															Classe_Liste SpecifClasse,Var_Liste NomVariable,
															Var_Liste NomClasse,
															Var_Liste NomTransition, Var_Liste NomPlace);
                                
extern	bool				MatMarquageEntre(Dom_Liste Domaineplace,Mat_Type M,
															FILE *FichierDonnees,Var_Liste NomParametre,
															Classe_Liste SpecifClasse,Var_Liste NomVariable,int NbPlace);
                                                
extern	void					MatSortPlace(Mat_Type M,Var_Liste NomParametre,
															Classe_Liste SpecifClasse,Var_Liste NomVariable,
															 Var_Liste NomClasse,Var_Liste NomPlace,
															Entier_Liste MacaoPlace);

extern	void					MatSortFlot(Mat_Type M1,Mat_Type M2,Mat_Type M3,
															Classe_Liste SpecifClasse,Var_Liste NomVariable,
															Var_Liste NomClasse,Var_Liste NomPlace,
															Entier_Liste MacaoPlace);

extern	void					AfficheMatrice(Mat_Type M,const char coucou[],Var_Liste NomParametre,
															Classe_Liste SpecifClasse,Var_Liste NomVariable,
															Var_Liste NomClasse,Var_Liste NomTransition,
															Var_Liste NomPlace);
#ifdef __cplusplus
}
#endif
#endif
