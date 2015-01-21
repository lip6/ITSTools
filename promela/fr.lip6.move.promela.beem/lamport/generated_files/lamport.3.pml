byte b[3];
byte x=255;
byte y=255;


active proctype P_0() { 
byte j=0;

NCS: if
:: b[0] = 1; goto q1; 

fi;
CS: if
:: y = 255; goto e1; 

fi;
q1: if
:: x = 0; goto q2; 

fi;
q2: if
::  d_step {y!=255;b[0] = 0;}  goto q22; 

:: y==255; goto p; 

fi;
q22: if
:: y==255; goto NCS; 

fi;
p: if
:: y = 0; goto q3; 

fi;
q3: if
:: x==0; goto CS; 

::  d_step {x!=0;b[0] = 0;j = 0;}  goto q4; 

fi;
q4: if
::  d_step {j<3 && b[j]==0;j = j+1;}  goto q4; 

:: j==3; goto q5; 

fi;
q5: if
:: y==x; goto CS; 

:: y==255; goto NCS; 

fi;
e1: if
:: b[0] = 0; goto NCS; 

fi;
}

active proctype P_1() { 
byte j=0;

NCS: if
:: b[1] = 1; goto q1; 

fi;
CS: if
:: y = 255; goto e1; 

fi;
q1: if
:: x = 1; goto q2; 

fi;
q2: if
::  d_step {y!=255;b[1] = 0;}  goto q22; 

:: y==255; goto p; 

fi;
q22: if
:: y==255; goto NCS; 

fi;
p: if
:: y = 1; goto q3; 

fi;
q3: if
:: x==1; goto CS; 

::  d_step {x!=1;b[1] = 0;j = 0;}  goto q4; 

fi;
q4: if
::  d_step {j<3 && b[j]==0;j = j+1;}  goto q4; 

:: j==3; goto q5; 

fi;
q5: if
:: y==x; goto CS; 

:: y==255; goto NCS; 

fi;
e1: if
:: b[1] = 0; goto NCS; 

fi;
}

active proctype P_2() { 
byte j=0;

NCS: if
:: b[2] = 1; goto q1; 

fi;
CS: if
:: y = 255; goto e1; 

fi;
q1: if
:: x = 2; goto q2; 

fi;
q2: if
::  d_step {y!=255;b[2] = 0;}  goto q22; 

:: y==255; goto p; 

fi;
q22: if
:: y==255; goto NCS; 

fi;
p: if
:: y = 2; goto q3; 

fi;
q3: if
:: x==2; goto CS; 

::  d_step {x!=2;b[2] = 0;j = 0;}  goto q4; 

fi;
q4: if
::  d_step {j<3 && b[j]==0;j = j+1;}  goto q4; 

:: j==3; goto q5; 

fi;
q5: if
:: y==x; goto CS; 

:: y==255; goto NCS; 

fi;
e1: if
:: b[2] = 0; goto NCS; 

fi;
}

