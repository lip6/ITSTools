int a;

active[1] proctype p() {
int b;
/*
do
::a=b;
::a=b;break;
::{a=b;}
::atomic{a=b;break;}
::atomic{a=b;}
::atomic{a=b;b=a;a=b;}
::{{a=b;b=a;a=b;}}
od;
if
::a=b;
::{a=b;}
::atomic{a=b;}
::atomic{a=b;b=a;a=b;}
::{{a=b;b=a;a=b;}}
fi;
*/
debut:
a=b;
do
::a=b;
::
    if
    ::a=b;
    ::a=b;break;
    ::{a=b;}
    ::atomic{a=b;break;}
    ::atomic{a=b;}
    ::atomic{a=b;b=a;a=b;}
    ::{{a=b;b=a;a=b;}}
    fi;
:: if
    ::a=b;
   fi;
od;
goto debut;
}

