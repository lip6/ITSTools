/* Generated File of 2 philosophers */

chan f0=[1]of {int};
chan f1=[1]of {int};

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
::f0?tmp;goto rgt;
fi;
lft:
if
::f0?tmp; goto both;
/*::else; goto lft;*/
fi;
rgt:
if
::f1?tmp; goto both;
/*::else; goto rgt;*/
fi;
both:
atomic {
    f0!tmp;f1!tmp;goto none;
}
}

init {
atomic{
f0!0;
f1!0;
/*sat=1;*/
}
}