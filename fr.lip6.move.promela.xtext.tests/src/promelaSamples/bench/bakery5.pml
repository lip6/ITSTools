/* Bakery Algorithm with 5 processes */

bool choosing[5];
int number[5];
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
active[1] proctype p3() {
int j;
choosing[3]=true;
atomic{max=max+1;number[3]=max;}
choosing[3]=false;
do
::(j<3) -> (!choosing[3]);((number[j]!=0) || ((number[j]<number[3])||((number[j]==number[3])&&((j<3)))));j++;
::(j==3)-> break;
 od;
number[3]=0;
}
active[1] proctype p4() {
int j;
choosing[4]=true;
atomic{max=max+1;number[4]=max;}
choosing[4]=false;
do
::(j<4) -> (!choosing[4]);((number[j]!=0) || ((number[j]<number[4])||((number[j]==number[4])&&((j<4)))));j++;
::(j==4)-> break;
 od;
number[4]=0;
}
