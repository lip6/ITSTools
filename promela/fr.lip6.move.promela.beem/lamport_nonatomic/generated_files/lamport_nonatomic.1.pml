
chan read_0 =[0] of {int};
chan write_0 =[0] of {int};
chan done_0 =[0] of {int};
chan read_1 =[0] of {int};
chan write_1 =[0] of {int};
chan done_1 =[0] of {int};
chan read_2 =[0] of {int};
chan write_2 =[0] of {int};
chan done_2 =[0] of {int};

active proctype NonatomicVar_0() { 
byte x=0;
byte v=0;

q: if
:: read_0!x; goto q; 

:: write_0?v; goto r; 

fi;
r: if
::  atomic {done_0!0;x = v;}  goto q; 

:: read_0!0; goto r; 

:: read_0!1; goto r; 

fi;
}

active proctype NonatomicVar_1() { 
byte x=0;
byte v=0;

q: if
:: read_1!x; goto q; 

:: write_1?v; goto r; 

fi;
r: if
::  atomic {done_1!0;x = v;}  goto q; 

:: read_1!0; goto r; 

:: read_1!1; goto r; 

fi;
}

active proctype NonatomicVar_2() { 
byte x=0;
byte v=0;

q: if
:: read_2!x; goto q; 

:: write_2?v; goto r; 

fi;
r: if
::  atomic {done_2!0;x = v;}  goto q; 

:: read_2!0; goto r; 

:: read_2!1; goto r; 

fi;
}

active proctype P_0() { 
byte i=0;
byte v=0;

NCS: if
:: write_0!1; goto w1; 

fi;
w1: if
::  atomic {done_0?0;i = 0;}  goto p3; 

fi;
p3: if
::  d_step {i==0;i = 0+1;}  goto p8; 

::  atomic {i==0;read_0?v;}  goto p4; 

::  atomic {i==1;read_1?v;}  goto p4; 

::  atomic {i==2;read_2?v;}  goto p4; 

fi;
p8: if
::  atomic {i==0;read_0?v;}  goto p9; 

::  atomic {i==1;read_1?v;}  goto p9; 

::  atomic {i==2;read_2?v;}  goto p9; 

:: i==3; goto CS; 

fi;
p4: if
::  d_step {v==0;i = i+1;}  goto p3; 

:: v==1; goto p5; 

fi;
p5: if
:: v==1; goto p6; 

:: v==0; goto NCS; 

fi;
p6: if
:: write_0!0; goto w2; 

fi;
p61: if
::  atomic {i==0;read_0?v;}  goto p5; 

::  atomic {i==1;read_1?v;}  goto p5; 

::  atomic {i==2;read_2?v;}  goto p5; 

fi;
w2: if
:: done_0?0; goto p61; 

fi;
p9: if
::  atomic {v==1 && i==0;read_0?v;}  goto p9; 

::  atomic {v==1 && i==1;read_1?v;}  goto p9; 

::  atomic {v==1 && i==2;read_2?v;}  goto p9; 

::  d_step {v==0;i = i+1;}  goto p8; 

fi;
CS: if
:: write_0!0; goto w3; 

fi;
w3: if
:: done_0?0; goto NCS; 

fi;
}

active proctype P_1() { 
byte i=0;
byte v=0;

NCS: if
:: write_1!1; goto w1; 

fi;
w1: if
::  atomic {done_1?0;i = 0;}  goto p3; 

fi;
p3: if
::  d_step {i==1;i = 1+1;}  goto p8; 

::  atomic {i==0;read_0?v;}  goto p4; 

::  atomic {i==1;read_1?v;}  goto p4; 

::  atomic {i==2;read_2?v;}  goto p4; 

fi;
p8: if
::  atomic {i==0;read_0?v;}  goto p9; 

::  atomic {i==1;read_1?v;}  goto p9; 

::  atomic {i==2;read_2?v;}  goto p9; 

:: i==3; goto CS; 

fi;
p4: if
::  d_step {v==0;i = i+1;}  goto p3; 

:: v==1; goto p5; 

fi;
p5: if
:: v==1; goto p6; 

:: v==0; goto NCS; 

fi;
p6: if
:: write_1!0; goto w2; 

fi;
p61: if
::  atomic {i==0;read_0?v;}  goto p5; 

::  atomic {i==1;read_1?v;}  goto p5; 

::  atomic {i==2;read_2?v;}  goto p5; 

fi;
w2: if
:: done_1?0; goto p61; 

fi;
p9: if
::  atomic {v==1 && i==0;read_0?v;}  goto p9; 

::  atomic {v==1 && i==1;read_1?v;}  goto p9; 

::  atomic {v==1 && i==2;read_2?v;}  goto p9; 

::  d_step {v==0;i = i+1;}  goto p8; 

fi;
CS: if
:: write_1!0; goto w3; 

fi;
w3: if
:: done_1?0; goto NCS; 

fi;
}

active proctype P_2() { 
byte i=0;
byte v=0;

NCS: if
:: write_2!1; goto w1; 

fi;
w1: if
::  atomic {done_2?0;i = 0;}  goto p3; 

fi;
p3: if
::  d_step {i==2;i = 2+1;}  goto p8; 

::  atomic {i==0;read_0?v;}  goto p4; 

::  atomic {i==1;read_1?v;}  goto p4; 

::  atomic {i==2;read_2?v;}  goto p4; 

fi;
p8: if
::  atomic {i==0;read_0?v;}  goto p9; 

::  atomic {i==1;read_1?v;}  goto p9; 

::  atomic {i==2;read_2?v;}  goto p9; 

:: i==3; goto CS; 

fi;
p4: if
::  d_step {v==0;i = i+1;}  goto p3; 

:: v==1; goto p5; 

fi;
p5: if
:: v==1; goto p6; 

:: v==0; goto NCS; 

fi;
p6: if
:: write_2!0; goto w2; 

fi;
p61: if
::  atomic {i==0;read_0?v;}  goto p5; 

::  atomic {i==1;read_1?v;}  goto p5; 

::  atomic {i==2;read_2?v;}  goto p5; 

fi;
w2: if
:: done_2?0; goto p61; 

fi;
p9: if
::  atomic {v==1 && i==0;read_0?v;}  goto p9; 

::  atomic {v==1 && i==1;read_1?v;}  goto p9; 

::  atomic {v==1 && i==2;read_2?v;}  goto p9; 

::  d_step {v==0;i = i+1;}  goto p8; 

fi;
CS: if
:: write_2!0; goto w3; 

fi;
w3: if
:: done_2?0; goto NCS; 

fi;
}

