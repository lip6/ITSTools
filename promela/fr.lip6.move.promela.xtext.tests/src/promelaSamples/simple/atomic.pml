bit lock;
int a;

active[1] proctype invert() {
do
::lock=0;
::lock=1;
od;
}

active[1] proctype seq() {
atomic{
a=1;
a=2;
(lock==0);
a=3;
a=4;
}
}
