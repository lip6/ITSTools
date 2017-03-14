/* Bakery Algorithm with 3 processes */

bool choosing[3];
int number[3];
int max;
active[1] proctype p0() {
int j;
choosing[0]=true;
atomic{max=max+1;number[0]=max;}
choosing[0]=false;
do
::(j<0) -> (!choosing[0]);((number[j]!=0) || ((number[j]<number[0])||((number[j]==number[0])&&((j<0)))));j++;
::(j==0)-> break;
 od;
number[0]=0;
}
active[1] proctype p1() {
int j;
choosing[1]=true;
atomic{max=max+1;number[1]=max;}
choosing[1]=false;
do
::(j<1) -> (!choosing[1]);((number[j]!=0) || ((number[j]<number[1])||((number[j]==number[1])&&((j<1)))));j++;
::(j==1)-> break;
 od;
number[1]=0;
}
active[1] proctype p2() {
int j;
choosing[2]=true;
atomic{max=max+1;number[2]=max;}
choosing[2]=false;
do
::(j<2) -> (!choosing[2]);((number[j]!=0) || ((number[j]<number[2])||((number[j]==number[2])&&((j<2)))));j++;
::(j==2)-> break;
 od;
number[2]=0;
}
