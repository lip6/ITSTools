/* Bakery Algorithm with 10 processes */

bool choosing[10];
int number[10];
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
active[1] proctype p5() {
int j;
choosing[5]=true;
atomic{max=max+1;number[5]=max;}
choosing[5]=false;
do
::(j<5) -> (!choosing[5]);((number[j]!=0) || ((number[j]<number[5])||((number[j]==number[5])&&((j<5)))));j++;
::(j==5)-> break;
 od;
number[5]=0;
}
active[1] proctype p6() {
int j;
choosing[6]=true;
atomic{max=max+1;number[6]=max;}
choosing[6]=false;
do
::(j<6) -> (!choosing[6]);((number[j]!=0) || ((number[j]<number[6])||((number[j]==number[6])&&((j<6)))));j++;
::(j==6)-> break;
 od;
number[6]=0;
}
active[1] proctype p7() {
int j;
choosing[7]=true;
atomic{max=max+1;number[7]=max;}
choosing[7]=false;
do
::(j<7) -> (!choosing[7]);((number[j]!=0) || ((number[j]<number[7])||((number[j]==number[7])&&((j<7)))));j++;
::(j==7)-> break;
 od;
number[7]=0;
}
active[1] proctype p8() {
int j;
choosing[8]=true;
atomic{max=max+1;number[8]=max;}
choosing[8]=false;
do
::(j<8) -> (!choosing[8]);((number[j]!=0) || ((number[j]<number[8])||((number[j]==number[8])&&((j<8)))));j++;
::(j==8)-> break;
 od;
number[8]=0;
}
active[1] proctype p9() {
int j;
choosing[9]=true;
atomic{max=max+1;number[9]=max;}
choosing[9]=false;
do
::(j<9) -> (!choosing[9]);((number[j]!=0) || ((number[j]<number[9])||((number[j]==number[9])&&((j<9)))));j++;
::(j==9)-> break;
 od;
number[9]=0;
}
