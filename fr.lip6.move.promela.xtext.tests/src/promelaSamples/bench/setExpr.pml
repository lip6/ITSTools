typedef S {
int x;
int y;
};
S s;
S T[2];

active[1] proctype p() {
T[s.x]=s;
//T[T[s.x]]=s;
}
