typedef message {
        int a;
        int b;
        };
chan q0 = [2] of {message};

message c;

active[1] proctype p0() {
do
::q0!c;c.a=0;
::q0?c;c.a=0;
::q0?c;
::q0!c;
od;
}
