#define N 5
#define MAX 5

bit t[N];

active[1] proctype p(){
int k=0;
int i=0;
bit bk;
debut:
if
::(k==N) -> goto fin;
::else -> goto create;
fi;
create:
do
::atomic{(i<MAX); bk=i; i=k;break;}
::atomic{(i<MAX); i++;}
::atomic{(i==MAX); bk=i; i=k;break;}
od;
do
::atomic{(i>0); t[i]=t[i-1]; i--;}
::atomic{(i==0); t[0]=bk; k++; bk=0; break;}
od;
goto debut;
fin:
}
