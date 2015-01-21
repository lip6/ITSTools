int a;

chan f = [2] of {int};

active[1] proctype p() {
int b;
b=2;
f!a;
f!b;
/*f!a;*/
f?a;
f?a;
f?a;
}
