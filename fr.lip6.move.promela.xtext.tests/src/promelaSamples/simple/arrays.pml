typedef S {
int a;
int b;
};

S T[2];

active[1] proctype p() {
S m;
m.a = 1;
m.b = 2;
T[m.a] = m;
T[m.a] = 1;
m = T[0];
}
