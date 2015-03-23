extern 	EnvoiMiseEnEvidence(long i);

extern	EnvoiTracerTexte(char *f);

extern	 EnvoiDetruitObjet(long i);

extern	void    EnvoiModifAttribut(long i,char *nom,char *valeur);

extern	void    EnvoiMiseEnEvidenceAttribut(long i,char *nom,long From,lomg To);


extern	void    EnvoiCreerObjet(char *type,long i);

extern	void    EnvoiCreerConnecteur(char *type,long i,long d,long f);
