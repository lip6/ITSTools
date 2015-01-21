/* Generated File of 20 philosophers */

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

active[1] proctype phil0() {
int tmp;
/*(sat>=1);*/
none :
if
::f0?tmp;goto lft;
::f1?tmp;goto rgt;
fi;
lft:
if
::f1?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f0?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f1!tmp;f0!tmp;goto none;
}
}


active[1] proctype phil1() {
int tmp;
/*(sat>=1);*/
none :
if
::f1?tmp;goto lft;
::f2?tmp;goto rgt;
fi;
lft:
if
::f2?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f1?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f2!tmp;f1!tmp;goto none;
}
}


active[1] proctype phil2() {
int tmp;
/*(sat>=1);*/
none :
if
::f2?tmp;goto lft;
::f3?tmp;goto rgt;
fi;
lft:
if
::f3?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f2?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f3!tmp;f2!tmp;goto none;
}
}


active[1] proctype phil3() {
int tmp;
/*(sat>=1);*/
none :
if
::f3?tmp;goto lft;
::f4?tmp;goto rgt;
fi;
lft:
if
::f4?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f3?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f4!tmp;f3!tmp;goto none;
}
}


active[1] proctype phil4() {
int tmp;
/*(sat>=1);*/
none :
if
::f4?tmp;goto lft;
::f5?tmp;goto rgt;
fi;
lft:
if
::f5?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f4?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f5!tmp;f4!tmp;goto none;
}
}


active[1] proctype phil5() {
int tmp;
/*(sat>=1);*/
none :
if
::f5?tmp;goto lft;
::f6?tmp;goto rgt;
fi;
lft:
if
::f6?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f5?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f6!tmp;f5!tmp;goto none;
}
}


active[1] proctype phil6() {
int tmp;
/*(sat>=1);*/
none :
if
::f6?tmp;goto lft;
::f7?tmp;goto rgt;
fi;
lft:
if
::f7?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f6?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f7!tmp;f6!tmp;goto none;
}
}


active[1] proctype phil7() {
int tmp;
/*(sat>=1);*/
none :
if
::f7?tmp;goto lft;
::f8?tmp;goto rgt;
fi;
lft:
if
::f8?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f7?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f8!tmp;f7!tmp;goto none;
}
}


active[1] proctype phil8() {
int tmp;
/*(sat>=1);*/
none :
if
::f8?tmp;goto lft;
::f9?tmp;goto rgt;
fi;
lft:
if
::f9?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f8?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f9!tmp;f8!tmp;goto none;
}
}


active[1] proctype phil9() {
int tmp;
/*(sat>=1);*/
none :
if
::f9?tmp;goto lft;
::f10?tmp;goto rgt;
fi;
lft:
if
::f10?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f9?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f10!tmp;f9!tmp;goto none;
}
}


active[1] proctype phil10() {
int tmp;
/*(sat>=1);*/
none :
if
::f10?tmp;goto lft;
::f11?tmp;goto rgt;
fi;
lft:
if
::f11?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f10?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f11!tmp;f10!tmp;goto none;
}
}


active[1] proctype phil11() {
int tmp;
/*(sat>=1);*/
none :
if
::f11?tmp;goto lft;
::f12?tmp;goto rgt;
fi;
lft:
if
::f12?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f11?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f12!tmp;f11!tmp;goto none;
}
}


active[1] proctype phil12() {
int tmp;
/*(sat>=1);*/
none :
if
::f12?tmp;goto lft;
::f13?tmp;goto rgt;
fi;
lft:
if
::f13?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f12?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f13!tmp;f12!tmp;goto none;
}
}


active[1] proctype phil13() {
int tmp;
/*(sat>=1);*/
none :
if
::f13?tmp;goto lft;
::f14?tmp;goto rgt;
fi;
lft:
if
::f14?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f13?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f14!tmp;f13!tmp;goto none;
}
}


active[1] proctype phil14() {
int tmp;
/*(sat>=1);*/
none :
if
::f14?tmp;goto lft;
::f15?tmp;goto rgt;
fi;
lft:
if
::f15?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f14?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f15!tmp;f14!tmp;goto none;
}
}


active[1] proctype phil15() {
int tmp;
/*(sat>=1);*/
none :
if
::f15?tmp;goto lft;
::f16?tmp;goto rgt;
fi;
lft:
if
::f16?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f15?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f16!tmp;f15!tmp;goto none;
}
}


active[1] proctype phil16() {
int tmp;
/*(sat>=1);*/
none :
if
::f16?tmp;goto lft;
::f17?tmp;goto rgt;
fi;
lft:
if
::f17?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f16?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f17!tmp;f16!tmp;goto none;
}
}


active[1] proctype phil17() {
int tmp;
/*(sat>=1);*/
none :
if
::f17?tmp;goto lft;
::f18?tmp;goto rgt;
fi;
lft:
if
::f18?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f17?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f18!tmp;f17!tmp;goto none;
}
}


active[1] proctype phil18() {
int tmp;
/*(sat>=1);*/
none :
if
::f18?tmp;goto lft;
::f19?tmp;goto rgt;
fi;
lft:
if
::f19?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f18?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f19!tmp;f18!tmp;goto none;
}
}


active[1] proctype phil19() {
int tmp;
/*(sat>=1);*/
none :
if
::f19?tmp;goto lft;
::f0?tmp;goto rgt;
fi;
lft:
if
::f0?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f19?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f0!tmp;f19!tmp;goto none;
}
}

init {
atomic{
f0!0;
f1!0;
f2!0;
f3!0;
f4!0;
f5!0;
f6!0;
f7!0;
f8!0;
f9!0;
f10!0;
f11!0;
f12!0;
f13!0;
f14!0;
f15!0;
f16!0;
f17!0;
f18!0;
f19!0;
/*sat=1;*/
}
}