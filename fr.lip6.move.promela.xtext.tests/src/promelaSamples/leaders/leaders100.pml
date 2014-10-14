/* Generated File of a 100 nodes ring*/

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
chan f30=[1]of {int};
chan f31=[1]of {int};
chan f32=[1]of {int};
chan f33=[1]of {int};
chan f34=[1]of {int};
chan f35=[1]of {int};
chan f36=[1]of {int};
chan f37=[1]of {int};
chan f38=[1]of {int};
chan f39=[1]of {int};
chan f40=[1]of {int};
chan f41=[1]of {int};
chan f42=[1]of {int};
chan f43=[1]of {int};
chan f44=[1]of {int};
chan f45=[1]of {int};
chan f46=[1]of {int};
chan f47=[1]of {int};
chan f48=[1]of {int};
chan f49=[1]of {int};
chan f50=[1]of {int};
chan f51=[1]of {int};
chan f52=[1]of {int};
chan f53=[1]of {int};
chan f54=[1]of {int};
chan f55=[1]of {int};
chan f56=[1]of {int};
chan f57=[1]of {int};
chan f58=[1]of {int};
chan f59=[1]of {int};
chan f60=[1]of {int};
chan f61=[1]of {int};
chan f62=[1]of {int};
chan f63=[1]of {int};
chan f64=[1]of {int};
chan f65=[1]of {int};
chan f66=[1]of {int};
chan f67=[1]of {int};
chan f68=[1]of {int};
chan f69=[1]of {int};
chan f70=[1]of {int};
chan f71=[1]of {int};
chan f72=[1]of {int};
chan f73=[1]of {int};
chan f74=[1]of {int};
chan f75=[1]of {int};
chan f76=[1]of {int};
chan f77=[1]of {int};
chan f78=[1]of {int};
chan f79=[1]of {int};
chan f80=[1]of {int};
chan f81=[1]of {int};
chan f82=[1]of {int};
chan f83=[1]of {int};
chan f84=[1]of {int};
chan f85=[1]of {int};
chan f86=[1]of {int};
chan f87=[1]of {int};
chan f88=[1]of {int};
chan f89=[1]of {int};
chan f90=[1]of {int};
chan f91=[1]of {int};
chan f92=[1]of {int};
chan f93=[1]of {int};
chan f94=[1]of {int};
chan f95=[1]of {int};
chan f96=[1]of {int};
chan f97=[1]of {int};
chan f98=[1]of {int};
chan f99=[1]of {int};

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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
tmp=101;
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
::f30!n -> goto check;
::f29?[_] -> goto check;
fi;
check :
f29?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f30!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f30!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f29?t;atomic{f30!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n30() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f31!n -> goto check;
::f30?[_] -> goto check;
fi;
check :
f30?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f31!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f31!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f30?t;atomic{f31!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n31() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f32!n -> goto check;
::f31?[_] -> goto check;
fi;
check :
f31?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f32!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f32!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f31?t;atomic{f32!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n32() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f33!n -> goto check;
::f32?[_] -> goto check;
fi;
check :
f32?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f33!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f33!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f32?t;atomic{f33!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n33() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f34!n -> goto check;
::f33?[_] -> goto check;
fi;
check :
f33?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f34!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f34!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f33?t;atomic{f34!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n34() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f35!n -> goto check;
::f34?[_] -> goto check;
fi;
check :
f34?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f35!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f35!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f34?t;atomic{f35!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n35() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f36!n -> goto check;
::f35?[_] -> goto check;
fi;
check :
f35?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f36!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f36!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f35?t;atomic{f36!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n36() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f37!n -> goto check;
::f36?[_] -> goto check;
fi;
check :
f36?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f37!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f37!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f36?t;atomic{f37!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n37() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f38!n -> goto check;
::f37?[_] -> goto check;
fi;
check :
f37?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f38!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f38!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f37?t;atomic{f38!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n38() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f39!n -> goto check;
::f38?[_] -> goto check;
fi;
check :
f38?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f39!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f39!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f38?t;atomic{f39!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n39() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f40!n -> goto check;
::f39?[_] -> goto check;
fi;
check :
f39?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f40!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f40!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f39?t;atomic{f40!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n40() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f41!n -> goto check;
::f40?[_] -> goto check;
fi;
check :
f40?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f41!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f41!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f40?t;atomic{f41!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n41() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f42!n -> goto check;
::f41?[_] -> goto check;
fi;
check :
f41?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f42!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f42!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f41?t;atomic{f42!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n42() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f43!n -> goto check;
::f42?[_] -> goto check;
fi;
check :
f42?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f43!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f43!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f42?t;atomic{f43!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n43() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f44!n -> goto check;
::f43?[_] -> goto check;
fi;
check :
f43?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f44!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f44!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f43?t;atomic{f44!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n44() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f45!n -> goto check;
::f44?[_] -> goto check;
fi;
check :
f44?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f45!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f45!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f44?t;atomic{f45!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n45() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f46!n -> goto check;
::f45?[_] -> goto check;
fi;
check :
f45?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f46!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f46!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f45?t;atomic{f46!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n46() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f47!n -> goto check;
::f46?[_] -> goto check;
fi;
check :
f46?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f47!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f47!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f46?t;atomic{f47!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n47() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f48!n -> goto check;
::f47?[_] -> goto check;
fi;
check :
f47?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f48!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f48!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f47?t;atomic{f48!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n48() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f49!n -> goto check;
::f48?[_] -> goto check;
fi;
check :
f48?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f49!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f49!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f48?t;atomic{f49!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n49() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f50!n -> goto check;
::f49?[_] -> goto check;
fi;
check :
f49?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f50!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f50!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f49?t;atomic{f50!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n50() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f51!n -> goto check;
::f50?[_] -> goto check;
fi;
check :
f50?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f51!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f51!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f50?t;atomic{f51!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n51() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f52!n -> goto check;
::f51?[_] -> goto check;
fi;
check :
f51?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f52!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f52!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f51?t;atomic{f52!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n52() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f53!n -> goto check;
::f52?[_] -> goto check;
fi;
check :
f52?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f53!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f53!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f52?t;atomic{f53!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n53() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f54!n -> goto check;
::f53?[_] -> goto check;
fi;
check :
f53?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f54!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f54!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f53?t;atomic{f54!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n54() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f55!n -> goto check;
::f54?[_] -> goto check;
fi;
check :
f54?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f55!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f55!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f54?t;atomic{f55!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n55() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f56!n -> goto check;
::f55?[_] -> goto check;
fi;
check :
f55?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f56!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f56!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f55?t;atomic{f56!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n56() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f57!n -> goto check;
::f56?[_] -> goto check;
fi;
check :
f56?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f57!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f57!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f56?t;atomic{f57!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n57() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f58!n -> goto check;
::f57?[_] -> goto check;
fi;
check :
f57?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f58!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f58!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f57?t;atomic{f58!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n58() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f59!n -> goto check;
::f58?[_] -> goto check;
fi;
check :
f58?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f59!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f59!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f58?t;atomic{f59!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n59() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f60!n -> goto check;
::f59?[_] -> goto check;
fi;
check :
f59?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f60!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f60!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f59?t;atomic{f60!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n60() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f61!n -> goto check;
::f60?[_] -> goto check;
fi;
check :
f60?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f61!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f61!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f60?t;atomic{f61!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n61() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f62!n -> goto check;
::f61?[_] -> goto check;
fi;
check :
f61?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f62!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f62!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f61?t;atomic{f62!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n62() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f63!n -> goto check;
::f62?[_] -> goto check;
fi;
check :
f62?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f63!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f63!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f62?t;atomic{f63!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n63() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f64!n -> goto check;
::f63?[_] -> goto check;
fi;
check :
f63?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f64!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f64!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f63?t;atomic{f64!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n64() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f65!n -> goto check;
::f64?[_] -> goto check;
fi;
check :
f64?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f65!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f65!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f64?t;atomic{f65!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n65() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f66!n -> goto check;
::f65?[_] -> goto check;
fi;
check :
f65?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f66!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f66!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f65?t;atomic{f66!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n66() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f67!n -> goto check;
::f66?[_] -> goto check;
fi;
check :
f66?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f67!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f67!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f66?t;atomic{f67!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n67() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f68!n -> goto check;
::f67?[_] -> goto check;
fi;
check :
f67?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f68!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f68!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f67?t;atomic{f68!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n68() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f69!n -> goto check;
::f68?[_] -> goto check;
fi;
check :
f68?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f69!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f69!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f68?t;atomic{f69!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n69() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f70!n -> goto check;
::f69?[_] -> goto check;
fi;
check :
f69?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f70!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f70!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f69?t;atomic{f70!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n70() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f71!n -> goto check;
::f70?[_] -> goto check;
fi;
check :
f70?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f71!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f71!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f70?t;atomic{f71!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n71() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f72!n -> goto check;
::f71?[_] -> goto check;
fi;
check :
f71?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f72!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f72!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f71?t;atomic{f72!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n72() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f73!n -> goto check;
::f72?[_] -> goto check;
fi;
check :
f72?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f73!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f73!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f72?t;atomic{f73!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n73() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f74!n -> goto check;
::f73?[_] -> goto check;
fi;
check :
f73?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f74!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f74!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f73?t;atomic{f74!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n74() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f75!n -> goto check;
::f74?[_] -> goto check;
fi;
check :
f74?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f75!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f75!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f74?t;atomic{f75!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n75() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f76!n -> goto check;
::f75?[_] -> goto check;
fi;
check :
f75?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f76!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f76!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f75?t;atomic{f76!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n76() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f77!n -> goto check;
::f76?[_] -> goto check;
fi;
check :
f76?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f77!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f77!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f76?t;atomic{f77!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n77() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f78!n -> goto check;
::f77?[_] -> goto check;
fi;
check :
f77?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f78!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f78!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f77?t;atomic{f78!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n78() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f79!n -> goto check;
::f78?[_] -> goto check;
fi;
check :
f78?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f79!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f79!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f78?t;atomic{f79!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n79() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f80!n -> goto check;
::f79?[_] -> goto check;
fi;
check :
f79?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f80!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f80!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f79?t;atomic{f80!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n80() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f81!n -> goto check;
::f80?[_] -> goto check;
fi;
check :
f80?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f81!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f81!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f80?t;atomic{f81!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n81() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f82!n -> goto check;
::f81?[_] -> goto check;
fi;
check :
f81?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f82!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f82!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f81?t;atomic{f82!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n82() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f83!n -> goto check;
::f82?[_] -> goto check;
fi;
check :
f82?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f83!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f83!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f82?t;atomic{f83!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n83() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f84!n -> goto check;
::f83?[_] -> goto check;
fi;
check :
f83?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f84!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f84!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f83?t;atomic{f84!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n84() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f85!n -> goto check;
::f84?[_] -> goto check;
fi;
check :
f84?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f85!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f85!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f84?t;atomic{f85!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n85() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f86!n -> goto check;
::f85?[_] -> goto check;
fi;
check :
f85?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f86!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f86!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f85?t;atomic{f86!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n86() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f87!n -> goto check;
::f86?[_] -> goto check;
fi;
check :
f86?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f87!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f87!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f86?t;atomic{f87!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n87() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f88!n -> goto check;
::f87?[_] -> goto check;
fi;
check :
f87?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f88!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f88!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f87?t;atomic{f88!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n88() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f89!n -> goto check;
::f88?[_] -> goto check;
fi;
check :
f88?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f89!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f89!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f88?t;atomic{f89!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n89() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f90!n -> goto check;
::f89?[_] -> goto check;
fi;
check :
f89?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f90!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f90!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f89?t;atomic{f90!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n90() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f91!n -> goto check;
::f90?[_] -> goto check;
fi;
check :
f90?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f91!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f91!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f90?t;atomic{f91!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n91() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f92!n -> goto check;
::f91?[_] -> goto check;
fi;
check :
f91?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f92!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f92!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f91?t;atomic{f92!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n92() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f93!n -> goto check;
::f92?[_] -> goto check;
fi;
check :
f92?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f93!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f93!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f92?t;atomic{f93!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n93() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f94!n -> goto check;
::f93?[_] -> goto check;
fi;
check :
f93?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f94!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f94!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f93?t;atomic{f94!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n94() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f95!n -> goto check;
::f94?[_] -> goto check;
fi;
check :
f94?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f95!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f95!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f94?t;atomic{f95!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n95() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f96!n -> goto check;
::f95?[_] -> goto check;
fi;
check :
f95?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f96!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f96!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f95?t;atomic{f96!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n96() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f97!n -> goto check;
::f96?[_] -> goto check;
fi;
check :
f96?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f97!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f97!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f96?t;atomic{f97!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n97() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f98!n -> goto check;
::f97?[_] -> goto check;
fi;
check :
f97?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f98!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f98!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f97?t;atomic{f98!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n98() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f99!n -> goto check;
::f98?[_] -> goto check;
fi;
check :
f98?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f99!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f99!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f98?t;atomic{f99!t;t=0;}}
od;
leader :
tmp=101;
}

active[1] proctype n99() {
int n;
int t;
d_step {
n=tmp;
tmp++;
}
begin :
if
::f0!n -> goto check;
::f99?[_] -> goto check;
fi;
check :
f99?t;
if
::atomic{(t==n); t=0; goto leader;}
::atomic{(t<n); t=0; f0!n; goto check;}
::atomic{(t<n); t=0; goto check;}
::atomic{(t>n); f0!t; t=0; goto loose;}
fi;
loose :
do
::atomic{f99?t;atomic{f0!t;t=0;}}
od;
leader :
tmp=101;
}
