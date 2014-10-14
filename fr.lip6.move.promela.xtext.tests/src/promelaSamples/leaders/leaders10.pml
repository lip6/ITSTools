/* Generated File of a 10 nodes ring*/

int tmp;
chan f0=[1]of {int};
chan f1=[1]of {int};
chan f2=[1]of {int};
chan f3=[1]of {int};
chan f4=[1]of {int};
chan f5=[1]of {int};
chan f6=[1]of {int};
chan f7=[1]of {int};
chan f8=[1]of {int};
chan f9=[1]of {int};

active[1] proctype n0() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f1!n -> goto check;
::f0?[_] -> goto check;
fi;
check :
f0?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f1!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f1!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f0?t;atomic{f1!t;t=0;}}
od;
leader :
tmp=11;
}

active[1] proctype n1() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f2!n -> goto check;
::f1?[_] -> goto check;
fi;
check :
f1?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f2!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f2!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f1?t;atomic{f2!t;t=0;}}
od;
leader :
tmp=11;
}

active[1] proctype n2() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f3!n -> goto check;
::f2?[_] -> goto check;
fi;
check :
f2?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f3!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f3!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f2?t;atomic{f3!t;t=0;}}
od;
leader :
tmp=11;
}

active[1] proctype n3() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f4!n -> goto check;
::f3?[_] -> goto check;
fi;
check :
f3?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f4!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f4!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f3?t;atomic{f4!t;t=0;}}
od;
leader :
tmp=11;
}

active[1] proctype n4() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f5!n -> goto check;
::f4?[_] -> goto check;
fi;
check :
f4?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f5!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f5!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f4?t;atomic{f5!t;t=0;}}
od;
leader :
tmp=11;
}

active[1] proctype n5() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f6!n -> goto check;
::f5?[_] -> goto check;
fi;
check :
f5?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f6!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f6!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f5?t;atomic{f6!t;t=0;}}
od;
leader :
tmp=11;
}

active[1] proctype n6() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f7!n -> goto check;
::f6?[_] -> goto check;
fi;
check :
f6?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f7!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f7!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f6?t;atomic{f7!t;t=0;}}
od;
leader :
tmp=11;
}

active[1] proctype n7() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f8!n -> goto check;
::f7?[_] -> goto check;
fi;
check :
f7?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f8!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f8!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f7?t;atomic{f8!t;t=0;}}
od;
leader :
tmp=11;
}

active[1] proctype n8() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f9!n -> goto check;
::f8?[_] -> goto check;
fi;
check :
f8?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f9!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f9!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f8?t;atomic{f9!t;t=0;}}
od;
leader :
tmp=11;
}

active[1] proctype n9() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f0!n -> goto check;
::f9?[_] -> goto check;
fi;
check :
f9?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f0!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f0!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f9?t;atomic{f0!t;t=0;}}
od;
leader :
tmp=11;
}
