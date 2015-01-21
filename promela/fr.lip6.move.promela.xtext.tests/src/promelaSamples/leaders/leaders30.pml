/* Generated File of a 30 nodes ring*/

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
chan f10=[1]of {int};
chan f11=[1]of {int};
chan f12=[1]of {int};
chan f13=[1]of {int};
chan f14=[1]of {int};
chan f15=[1]of {int};
chan f16=[1]of {int};
chan f17=[1]of {int};
chan f18=[1]of {int};
chan f19=[1]of {int};
chan f20=[1]of {int};
chan f21=[1]of {int};
chan f22=[1]of {int};
chan f23=[1]of {int};
chan f24=[1]of {int};
chan f25=[1]of {int};
chan f26=[1]of {int};
chan f27=[1]of {int};
chan f28=[1]of {int};
chan f29=[1]of {int};

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
tmp=31;
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
tmp=31;
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
tmp=31;
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
tmp=31;
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
tmp=31;
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
tmp=31;
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
tmp=31;
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
tmp=31;
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
tmp=31;
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
::f10!n -> goto check;
::f9?[_] -> goto check;
fi;
check :
f9?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f10!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f10!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f9?t;atomic{f10!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n10() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f11!n -> goto check;
::f10?[_] -> goto check;
fi;
check :
f10?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f11!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f11!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f10?t;atomic{f11!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n11() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f12!n -> goto check;
::f11?[_] -> goto check;
fi;
check :
f11?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f12!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f12!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f11?t;atomic{f12!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n12() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f13!n -> goto check;
::f12?[_] -> goto check;
fi;
check :
f12?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f13!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f13!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f12?t;atomic{f13!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n13() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f14!n -> goto check;
::f13?[_] -> goto check;
fi;
check :
f13?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f14!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f14!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f13?t;atomic{f14!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n14() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f15!n -> goto check;
::f14?[_] -> goto check;
fi;
check :
f14?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f15!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f15!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f14?t;atomic{f15!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n15() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f16!n -> goto check;
::f15?[_] -> goto check;
fi;
check :
f15?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f16!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f16!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f15?t;atomic{f16!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n16() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f17!n -> goto check;
::f16?[_] -> goto check;
fi;
check :
f16?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f17!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f17!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f16?t;atomic{f17!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n17() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f18!n -> goto check;
::f17?[_] -> goto check;
fi;
check :
f17?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f18!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f18!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f17?t;atomic{f18!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n18() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f19!n -> goto check;
::f18?[_] -> goto check;
fi;
check :
f18?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f19!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f19!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f18?t;atomic{f19!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n19() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f20!n -> goto check;
::f19?[_] -> goto check;
fi;
check :
f19?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f20!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f20!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f19?t;atomic{f20!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n20() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f21!n -> goto check;
::f20?[_] -> goto check;
fi;
check :
f20?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f21!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f21!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f20?t;atomic{f21!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n21() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f22!n -> goto check;
::f21?[_] -> goto check;
fi;
check :
f21?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f22!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f22!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f21?t;atomic{f22!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n22() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f23!n -> goto check;
::f22?[_] -> goto check;
fi;
check :
f22?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f23!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f23!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f22?t;atomic{f23!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n23() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f24!n -> goto check;
::f23?[_] -> goto check;
fi;
check :
f23?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f24!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f24!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f23?t;atomic{f24!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n24() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f25!n -> goto check;
::f24?[_] -> goto check;
fi;
check :
f24?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f25!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f25!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f24?t;atomic{f25!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n25() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f26!n -> goto check;
::f25?[_] -> goto check;
fi;
check :
f25?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f26!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f26!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f25?t;atomic{f26!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n26() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f27!n -> goto check;
::f26?[_] -> goto check;
fi;
check :
f26?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f27!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f27!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f26?t;atomic{f27!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n27() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f28!n -> goto check;
::f27?[_] -> goto check;
fi;
check :
f27?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f28!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f28!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f27?t;atomic{f28!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n28() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f29!n -> goto check;
::f28?[_] -> goto check;
fi;
check :
f28?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f29!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f29!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f28?t;atomic{f29!t;t=0;}}
od;
leader :
tmp=31;
}

active[1] proctype n29() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f0!n -> goto check;
::f29?[_] -> goto check;
fi;
check :
f29?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f0!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f0!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f29?t;atomic{f0!t;t=0;}}
od;
leader :
tmp=31;
}
