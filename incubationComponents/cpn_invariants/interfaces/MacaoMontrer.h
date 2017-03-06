#ifndef _MacaoMontrer_
#define	_MacaoMontrer_

#ifdef __cplusplus
extern "C" {
#endif
extern	void					MontPlaceImplicite(int p,Entier_Liste MacaoPlace);
extern	void					MontTransitionNeutre(int t,Entier_Liste MacaoTransition);
extern	void					MontPreAggl(int p,int t,Entier_Liste MacaoPlace,Entier_Liste MacaoTransition);
extern	void					MontPostAggl(int p,int t,Entier_Liste MacaoPlace,Entier_Liste MacaoTransition);
#ifdef __cplusplus
}
#endif
#endif
