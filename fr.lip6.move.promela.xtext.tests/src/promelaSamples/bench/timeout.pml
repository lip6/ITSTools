chan q=[2]of{int};

active[1] proctype p() {
do
::q!1;
od;
}

active[1] proctype t() {
int a;
do
::timeout->q?a;
od;
}
