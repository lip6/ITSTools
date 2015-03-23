#ifndef _monome_
#define	_monome_

#include "CListes.h"
typedef CListe     Mo_Type;

#ifdef __cplusplus
extern "C" {
#endif
extern	bool				MoTestUn(Mo_Type m);
extern 	void					MoDetr(Mo_Type m);
extern	void					MoUn(Mo_Type m);
extern	void					MoDupl(Mo_Type m1,Mo_Type m2);
extern	void					MoMult(Mo_Type m1,Mo_Type m2);
extern	int						MoOrdre(Mo_Type m1,Mo_Type m2);
extern	Mo_Type				MoCreer(void);
extern	void					MoVariable(Mo_Type m,int i);
#ifdef __cplusplus
}
#endif
#endif
