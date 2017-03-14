/* Generated File of 5 philosophers */

/*int sat;*/
int tmp;
chan f0=[1]of {int};
chan f1=[1]of {int};
chan f2=[1]of {int};
chan f3=[1]of {int};
chan f4=[1]of {int};

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
::f0?tmp;goto rgt;
fi;
lft:
if
::f0?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f4?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f0!tmp;f4!tmp;goto none;
}
}

init {
atomic{
f0!0;
f1!0;
f2!0;
f3!0;
f4!0;
/*sat=1;*/
}
}