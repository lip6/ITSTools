#define max 10
int cpt;

active[1] proctype inc() {
do
::(cpt<max)-> cpt++;
::else-> break;
od;
}

active[1] proctype dec() {
do
::(cpt>0)-> cpt--;
::else-> break;
od;
}
