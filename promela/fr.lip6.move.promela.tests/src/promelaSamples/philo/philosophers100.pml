/* Generated File of 100 philosophers */

/*int sat;*/
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

active[1] proctype phil0() {
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
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
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f19?tmp;goto lft;
::f20?tmp;goto rgt;
fi;
lft:
if
::f20?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f19?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f20!tmp;f19!tmp;goto none;
}
}


active[1] proctype phil20() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f20?tmp;goto lft;
::f21?tmp;goto rgt;
fi;
lft:
if
::f21?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f20?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f21!tmp;f20!tmp;goto none;
}
}


active[1] proctype phil21() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f21?tmp;goto lft;
::f22?tmp;goto rgt;
fi;
lft:
if
::f22?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f21?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f22!tmp;f21!tmp;goto none;
}
}


active[1] proctype phil22() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f22?tmp;goto lft;
::f23?tmp;goto rgt;
fi;
lft:
if
::f23?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f22?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f23!tmp;f22!tmp;goto none;
}
}


active[1] proctype phil23() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f23?tmp;goto lft;
::f24?tmp;goto rgt;
fi;
lft:
if
::f24?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f23?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f24!tmp;f23!tmp;goto none;
}
}


active[1] proctype phil24() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f24?tmp;goto lft;
::f25?tmp;goto rgt;
fi;
lft:
if
::f25?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f24?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f25!tmp;f24!tmp;goto none;
}
}


active[1] proctype phil25() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f25?tmp;goto lft;
::f26?tmp;goto rgt;
fi;
lft:
if
::f26?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f25?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f26!tmp;f25!tmp;goto none;
}
}


active[1] proctype phil26() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f26?tmp;goto lft;
::f27?tmp;goto rgt;
fi;
lft:
if
::f27?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f26?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f27!tmp;f26!tmp;goto none;
}
}


active[1] proctype phil27() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f27?tmp;goto lft;
::f28?tmp;goto rgt;
fi;
lft:
if
::f28?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f27?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f28!tmp;f27!tmp;goto none;
}
}


active[1] proctype phil28() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f28?tmp;goto lft;
::f29?tmp;goto rgt;
fi;
lft:
if
::f29?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f28?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f29!tmp;f28!tmp;goto none;
}
}


active[1] proctype phil29() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f29?tmp;goto lft;
::f30?tmp;goto rgt;
fi;
lft:
if
::f30?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f29?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f30!tmp;f29!tmp;goto none;
}
}


active[1] proctype phil30() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f30?tmp;goto lft;
::f31?tmp;goto rgt;
fi;
lft:
if
::f31?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f30?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f31!tmp;f30!tmp;goto none;
}
}


active[1] proctype phil31() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f31?tmp;goto lft;
::f32?tmp;goto rgt;
fi;
lft:
if
::f32?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f31?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f32!tmp;f31!tmp;goto none;
}
}


active[1] proctype phil32() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f32?tmp;goto lft;
::f33?tmp;goto rgt;
fi;
lft:
if
::f33?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f32?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f33!tmp;f32!tmp;goto none;
}
}


active[1] proctype phil33() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f33?tmp;goto lft;
::f34?tmp;goto rgt;
fi;
lft:
if
::f34?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f33?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f34!tmp;f33!tmp;goto none;
}
}


active[1] proctype phil34() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f34?tmp;goto lft;
::f35?tmp;goto rgt;
fi;
lft:
if
::f35?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f34?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f35!tmp;f34!tmp;goto none;
}
}


active[1] proctype phil35() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f35?tmp;goto lft;
::f36?tmp;goto rgt;
fi;
lft:
if
::f36?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f35?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f36!tmp;f35!tmp;goto none;
}
}


active[1] proctype phil36() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f36?tmp;goto lft;
::f37?tmp;goto rgt;
fi;
lft:
if
::f37?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f36?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f37!tmp;f36!tmp;goto none;
}
}


active[1] proctype phil37() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f37?tmp;goto lft;
::f38?tmp;goto rgt;
fi;
lft:
if
::f38?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f37?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f38!tmp;f37!tmp;goto none;
}
}


active[1] proctype phil38() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f38?tmp;goto lft;
::f39?tmp;goto rgt;
fi;
lft:
if
::f39?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f38?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f39!tmp;f38!tmp;goto none;
}
}


active[1] proctype phil39() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f39?tmp;goto lft;
::f40?tmp;goto rgt;
fi;
lft:
if
::f40?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f39?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f40!tmp;f39!tmp;goto none;
}
}


active[1] proctype phil40() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f40?tmp;goto lft;
::f41?tmp;goto rgt;
fi;
lft:
if
::f41?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f40?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f41!tmp;f40!tmp;goto none;
}
}


active[1] proctype phil41() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f41?tmp;goto lft;
::f42?tmp;goto rgt;
fi;
lft:
if
::f42?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f41?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f42!tmp;f41!tmp;goto none;
}
}


active[1] proctype phil42() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f42?tmp;goto lft;
::f43?tmp;goto rgt;
fi;
lft:
if
::f43?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f42?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f43!tmp;f42!tmp;goto none;
}
}


active[1] proctype phil43() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f43?tmp;goto lft;
::f44?tmp;goto rgt;
fi;
lft:
if
::f44?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f43?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f44!tmp;f43!tmp;goto none;
}
}


active[1] proctype phil44() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f44?tmp;goto lft;
::f45?tmp;goto rgt;
fi;
lft:
if
::f45?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f44?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f45!tmp;f44!tmp;goto none;
}
}


active[1] proctype phil45() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f45?tmp;goto lft;
::f46?tmp;goto rgt;
fi;
lft:
if
::f46?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f45?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f46!tmp;f45!tmp;goto none;
}
}


active[1] proctype phil46() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f46?tmp;goto lft;
::f47?tmp;goto rgt;
fi;
lft:
if
::f47?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f46?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f47!tmp;f46!tmp;goto none;
}
}


active[1] proctype phil47() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f47?tmp;goto lft;
::f48?tmp;goto rgt;
fi;
lft:
if
::f48?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f47?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f48!tmp;f47!tmp;goto none;
}
}


active[1] proctype phil48() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f48?tmp;goto lft;
::f49?tmp;goto rgt;
fi;
lft:
if
::f49?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f48?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f49!tmp;f48!tmp;goto none;
}
}


active[1] proctype phil49() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f49?tmp;goto lft;
::f50?tmp;goto rgt;
fi;
lft:
if
::f50?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f49?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f50!tmp;f49!tmp;goto none;
}
}


active[1] proctype phil50() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f50?tmp;goto lft;
::f51?tmp;goto rgt;
fi;
lft:
if
::f51?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f50?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f51!tmp;f50!tmp;goto none;
}
}


active[1] proctype phil51() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f51?tmp;goto lft;
::f52?tmp;goto rgt;
fi;
lft:
if
::f52?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f51?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f52!tmp;f51!tmp;goto none;
}
}


active[1] proctype phil52() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f52?tmp;goto lft;
::f53?tmp;goto rgt;
fi;
lft:
if
::f53?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f52?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f53!tmp;f52!tmp;goto none;
}
}


active[1] proctype phil53() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f53?tmp;goto lft;
::f54?tmp;goto rgt;
fi;
lft:
if
::f54?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f53?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f54!tmp;f53!tmp;goto none;
}
}


active[1] proctype phil54() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f54?tmp;goto lft;
::f55?tmp;goto rgt;
fi;
lft:
if
::f55?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f54?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f55!tmp;f54!tmp;goto none;
}
}


active[1] proctype phil55() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f55?tmp;goto lft;
::f56?tmp;goto rgt;
fi;
lft:
if
::f56?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f55?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f56!tmp;f55!tmp;goto none;
}
}


active[1] proctype phil56() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f56?tmp;goto lft;
::f57?tmp;goto rgt;
fi;
lft:
if
::f57?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f56?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f57!tmp;f56!tmp;goto none;
}
}


active[1] proctype phil57() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f57?tmp;goto lft;
::f58?tmp;goto rgt;
fi;
lft:
if
::f58?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f57?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f58!tmp;f57!tmp;goto none;
}
}


active[1] proctype phil58() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f58?tmp;goto lft;
::f59?tmp;goto rgt;
fi;
lft:
if
::f59?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f58?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f59!tmp;f58!tmp;goto none;
}
}


active[1] proctype phil59() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f59?tmp;goto lft;
::f60?tmp;goto rgt;
fi;
lft:
if
::f60?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f59?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f60!tmp;f59!tmp;goto none;
}
}


active[1] proctype phil60() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f60?tmp;goto lft;
::f61?tmp;goto rgt;
fi;
lft:
if
::f61?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f60?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f61!tmp;f60!tmp;goto none;
}
}


active[1] proctype phil61() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f61?tmp;goto lft;
::f62?tmp;goto rgt;
fi;
lft:
if
::f62?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f61?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f62!tmp;f61!tmp;goto none;
}
}


active[1] proctype phil62() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f62?tmp;goto lft;
::f63?tmp;goto rgt;
fi;
lft:
if
::f63?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f62?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f63!tmp;f62!tmp;goto none;
}
}


active[1] proctype phil63() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f63?tmp;goto lft;
::f64?tmp;goto rgt;
fi;
lft:
if
::f64?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f63?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f64!tmp;f63!tmp;goto none;
}
}


active[1] proctype phil64() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f64?tmp;goto lft;
::f65?tmp;goto rgt;
fi;
lft:
if
::f65?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f64?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f65!tmp;f64!tmp;goto none;
}
}


active[1] proctype phil65() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f65?tmp;goto lft;
::f66?tmp;goto rgt;
fi;
lft:
if
::f66?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f65?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f66!tmp;f65!tmp;goto none;
}
}


active[1] proctype phil66() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f66?tmp;goto lft;
::f67?tmp;goto rgt;
fi;
lft:
if
::f67?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f66?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f67!tmp;f66!tmp;goto none;
}
}


active[1] proctype phil67() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f67?tmp;goto lft;
::f68?tmp;goto rgt;
fi;
lft:
if
::f68?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f67?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f68!tmp;f67!tmp;goto none;
}
}


active[1] proctype phil68() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f68?tmp;goto lft;
::f69?tmp;goto rgt;
fi;
lft:
if
::f69?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f68?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f69!tmp;f68!tmp;goto none;
}
}


active[1] proctype phil69() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f69?tmp;goto lft;
::f70?tmp;goto rgt;
fi;
lft:
if
::f70?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f69?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f70!tmp;f69!tmp;goto none;
}
}


active[1] proctype phil70() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f70?tmp;goto lft;
::f71?tmp;goto rgt;
fi;
lft:
if
::f71?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f70?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f71!tmp;f70!tmp;goto none;
}
}


active[1] proctype phil71() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f71?tmp;goto lft;
::f72?tmp;goto rgt;
fi;
lft:
if
::f72?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f71?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f72!tmp;f71!tmp;goto none;
}
}


active[1] proctype phil72() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f72?tmp;goto lft;
::f73?tmp;goto rgt;
fi;
lft:
if
::f73?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f72?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f73!tmp;f72!tmp;goto none;
}
}


active[1] proctype phil73() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f73?tmp;goto lft;
::f74?tmp;goto rgt;
fi;
lft:
if
::f74?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f73?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f74!tmp;f73!tmp;goto none;
}
}


active[1] proctype phil74() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f74?tmp;goto lft;
::f75?tmp;goto rgt;
fi;
lft:
if
::f75?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f74?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f75!tmp;f74!tmp;goto none;
}
}


active[1] proctype phil75() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f75?tmp;goto lft;
::f76?tmp;goto rgt;
fi;
lft:
if
::f76?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f75?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f76!tmp;f75!tmp;goto none;
}
}


active[1] proctype phil76() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f76?tmp;goto lft;
::f77?tmp;goto rgt;
fi;
lft:
if
::f77?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f76?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f77!tmp;f76!tmp;goto none;
}
}


active[1] proctype phil77() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f77?tmp;goto lft;
::f78?tmp;goto rgt;
fi;
lft:
if
::f78?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f77?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f78!tmp;f77!tmp;goto none;
}
}


active[1] proctype phil78() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f78?tmp;goto lft;
::f79?tmp;goto rgt;
fi;
lft:
if
::f79?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f78?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f79!tmp;f78!tmp;goto none;
}
}


active[1] proctype phil79() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f79?tmp;goto lft;
::f80?tmp;goto rgt;
fi;
lft:
if
::f80?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f79?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f80!tmp;f79!tmp;goto none;
}
}


active[1] proctype phil80() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f80?tmp;goto lft;
::f81?tmp;goto rgt;
fi;
lft:
if
::f81?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f80?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f81!tmp;f80!tmp;goto none;
}
}


active[1] proctype phil81() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f81?tmp;goto lft;
::f82?tmp;goto rgt;
fi;
lft:
if
::f82?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f81?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f82!tmp;f81!tmp;goto none;
}
}


active[1] proctype phil82() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f82?tmp;goto lft;
::f83?tmp;goto rgt;
fi;
lft:
if
::f83?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f82?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f83!tmp;f82!tmp;goto none;
}
}


active[1] proctype phil83() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f83?tmp;goto lft;
::f84?tmp;goto rgt;
fi;
lft:
if
::f84?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f83?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f84!tmp;f83!tmp;goto none;
}
}


active[1] proctype phil84() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f84?tmp;goto lft;
::f85?tmp;goto rgt;
fi;
lft:
if
::f85?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f84?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f85!tmp;f84!tmp;goto none;
}
}


active[1] proctype phil85() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f85?tmp;goto lft;
::f86?tmp;goto rgt;
fi;
lft:
if
::f86?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f85?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f86!tmp;f85!tmp;goto none;
}
}


active[1] proctype phil86() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f86?tmp;goto lft;
::f87?tmp;goto rgt;
fi;
lft:
if
::f87?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f86?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f87!tmp;f86!tmp;goto none;
}
}


active[1] proctype phil87() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f87?tmp;goto lft;
::f88?tmp;goto rgt;
fi;
lft:
if
::f88?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f87?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f88!tmp;f87!tmp;goto none;
}
}


active[1] proctype phil88() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f88?tmp;goto lft;
::f89?tmp;goto rgt;
fi;
lft:
if
::f89?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f88?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f89!tmp;f88!tmp;goto none;
}
}


active[1] proctype phil89() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f89?tmp;goto lft;
::f90?tmp;goto rgt;
fi;
lft:
if
::f90?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f89?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f90!tmp;f89!tmp;goto none;
}
}


active[1] proctype phil90() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f90?tmp;goto lft;
::f91?tmp;goto rgt;
fi;
lft:
if
::f91?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f90?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f91!tmp;f90!tmp;goto none;
}
}


active[1] proctype phil91() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f91?tmp;goto lft;
::f92?tmp;goto rgt;
fi;
lft:
if
::f92?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f91?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f92!tmp;f91!tmp;goto none;
}
}


active[1] proctype phil92() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f92?tmp;goto lft;
::f93?tmp;goto rgt;
fi;
lft:
if
::f93?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f92?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f93!tmp;f92!tmp;goto none;
}
}


active[1] proctype phil93() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f93?tmp;goto lft;
::f94?tmp;goto rgt;
fi;
lft:
if
::f94?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f93?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f94!tmp;f93!tmp;goto none;
}
}


active[1] proctype phil94() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f94?tmp;goto lft;
::f95?tmp;goto rgt;
fi;
lft:
if
::f95?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f94?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f95!tmp;f94!tmp;goto none;
}
}


active[1] proctype phil95() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f95?tmp;goto lft;
::f96?tmp;goto rgt;
fi;
lft:
if
::f96?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f95?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f96!tmp;f95!tmp;goto none;
}
}


active[1] proctype phil96() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f96?tmp;goto lft;
::f97?tmp;goto rgt;
fi;
lft:
if
::f97?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f96?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f97!tmp;f96!tmp;goto none;
}
}


active[1] proctype phil97() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f97?tmp;goto lft;
::f98?tmp;goto rgt;
fi;
lft:
if
::f98?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f97?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f98!tmp;f97!tmp;goto none;
}
}


active[1] proctype phil98() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f98?tmp;goto lft;
::f99?tmp;goto rgt;
fi;
lft:
if
::f99?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f98?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f99!tmp;f98!tmp;goto none;
}
}


active[1] proctype phil99() {
/*int r;
int l;*/
/*(sat>=1);*/
none :
if
::f99?tmp;goto lft;
::f0?tmp;goto rgt;
fi;
lft:
if
::f0?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f99?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f0!tmp;f99!tmp;goto none;
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
f20!0;
f21!0;
f22!0;
f23!0;
f24!0;
f25!0;
f26!0;
f27!0;
f28!0;
f29!0;
f30!0;
f31!0;
f32!0;
f33!0;
f34!0;
f35!0;
f36!0;
f37!0;
f38!0;
f39!0;
f40!0;
f41!0;
f42!0;
f43!0;
f44!0;
f45!0;
f46!0;
f47!0;
f48!0;
f49!0;
f50!0;
f51!0;
f52!0;
f53!0;
f54!0;
f55!0;
f56!0;
f57!0;
f58!0;
f59!0;
f60!0;
f61!0;
f62!0;
f63!0;
f64!0;
f65!0;
f66!0;
f67!0;
f68!0;
f69!0;
f70!0;
f71!0;
f72!0;
f73!0;
f74!0;
f75!0;
f76!0;
f77!0;
f78!0;
f79!0;
f80!0;
f81!0;
f82!0;
f83!0;
f84!0;
f85!0;
f86!0;
f87!0;
f88!0;
f89!0;
f90!0;
f91!0;
f92!0;
f93!0;
f94!0;
f95!0;
f96!0;
f97!0;
f98!0;
f99!0;
/*sat=1;*/
}
}