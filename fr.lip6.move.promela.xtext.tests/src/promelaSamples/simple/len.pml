chan q = [3] of {byte};

active[1] proctype p() {
int e;
int f;
int ne;
int nf;
int l;
atomic{
e = empty(q);
f = full(q);
ne = nempty(q);
nf = nfull(q);
l = len(q);
}
atomic{
q!0;
e = empty(q);
f = full(q);
ne = nempty(q);
nf = nfull(q);
l = len(q);
}
atomic{
q!0;
e = empty(q);
f = full(q);
ne = nempty(q);
nf = nfull(q);
l = len(q);
}
atomic{
q!0;
e = empty(q);
f = full(q);
ne = nempty(q);
nf = nfull(q);
l = len(q);
}
atomic{
q!0;
e = empty(q);
f = full(q);
ne = nempty(q);
nf = nfull(q);
l = len(q);
}
}
