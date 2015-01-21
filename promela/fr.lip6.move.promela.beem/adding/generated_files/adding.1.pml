int c=1;
int x1=0;
int x2=0;


active proctype a1() { 

Q: if
::  d_step {c<20;x1 = c;}  goto R; 

fi;
R: if
:: x1 = x1+c; goto S; 

fi;
S: if
:: c = x1; goto Q; 

fi;
}

active proctype a2() { 

Q: if
::  d_step {c<20;x2 = c;}  goto R; 

fi;
R: if
:: x2 = x2+c; goto S; 

fi;
S: if
:: c = x2; goto Q; 

fi;
}

