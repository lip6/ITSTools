byte a[3];
byte w[3];
byte s[3];


active proctype P_0() { 
byte j=0;

NCS: if
::  d_step {a[0] = 1;j = 0;}  goto p2; 

fi;
p2: if
::  d_step {j<3 && s[j]==0;j = j+1;}  goto p2; 

:: j==3; goto p3; 

fi;
p3: if
::  d_step {w[0] = 1;a[0] = 0;}  goto p4; 

fi;
p4: if
::  d_step {s[0]==1;j = 0;}  goto p9; 

::  d_step {s[0]==0;j = 0;}  goto p5; 

fi;
p5: if
::  d_step {j<3 && a[j]==0;j = j+1;}  goto p5; 

::  ! (j<3 && a[j]==0); goto p6; 

fi;
p6: if
:: j<3; goto p7; 

::  d_step {j==3;s[0] = 1;j = 0;}  goto p61; 

fi;
p61: if
::  d_step {j<3 && a[j]==0;j = j+1;}  goto p61; 

::  d_step {j<3 && a[j]==1;s[0] = 0;}  goto p7; 

::  d_step {j==3;w[0] = 0;j = 0;}  goto p64; 

fi;
p64: if
::  d_step {j<3 && w[j]==0;j = j+1;}  goto p64; 

:: j==3; goto p7; 

fi;
p7: if
:: j==3; goto p8; 

::  d_step {j<3;j = 0;}  goto p71; 

fi;
p71: if
::  d_step {j<3 && (w[j]==1 || s[j]==0);j = j+1;}  goto p71; 

::  ! (j<3 && (w[j]==1 || s[j]==0)); goto p8; 

fi;
p8: if
::  d_step {j!=0 && j<3;s[0] = 1;w[0] = 0;}  goto p4; 

::  ! (j!=0 && j<3); goto p4; 

fi;
p9: if
::  d_step {j<0 && w[j]==0 && s[j]==0;j = j+1;}  goto p9; 

:: j==0; goto CS; 

fi;
CS: if
:: s[0] = 0; goto NCS; 

fi;
}

active proctype P_1() { 
byte j=0;

NCS: if
::  d_step {a[1] = 1;j = 0;}  goto p2; 

fi;
p2: if
::  d_step {j<3 && s[j]==0;j = j+1;}  goto p2; 

:: j==3; goto p3; 

fi;
p3: if
::  d_step {w[1] = 1;a[1] = 0;}  goto p4; 

fi;
p4: if
::  d_step {s[1]==1;j = 0;}  goto p9; 

::  d_step {s[1]==0;j = 0;}  goto p5; 

fi;
p5: if
::  d_step {j<3 && a[j]==0;j = j+1;}  goto p5; 

::  ! (j<3 && a[j]==0); goto p6; 

fi;
p6: if
:: j<3; goto p7; 

::  d_step {j==3;s[1] = 1;j = 0;}  goto p61; 

fi;
p61: if
::  d_step {j<3 && a[j]==0;j = j+1;}  goto p61; 

::  d_step {j<3 && a[j]==1;s[1] = 0;}  goto p7; 

::  d_step {j==3;w[1] = 0;j = 0;}  goto p64; 

fi;
p64: if
::  d_step {j<3 && w[j]==0;j = j+1;}  goto p64; 

:: j==3; goto p7; 

fi;
p7: if
:: j==3; goto p8; 

::  d_step {j<3;j = 0;}  goto p71; 

fi;
p71: if
::  d_step {j<3 && (w[j]==1 || s[j]==0);j = j+1;}  goto p71; 

::  ! (j<3 && (w[j]==1 || s[j]==0)); goto p8; 

fi;
p8: if
::  d_step {j!=1 && j<3;s[1] = 1;w[1] = 0;}  goto p4; 

::  ! (j!=1 && j<3); goto p4; 

fi;
p9: if
::  d_step {j<1 && w[j]==0 && s[j]==0;j = j+1;}  goto p9; 

:: j==1; goto CS; 

fi;
CS: if
:: s[1] = 0; goto NCS; 

fi;
}

active proctype P_2() { 
byte j=0;

NCS: if
::  d_step {a[2] = 1;j = 0;}  goto p2; 

fi;
p2: if
::  d_step {j<3 && s[j]==0;j = j+1;}  goto p2; 

:: j==3; goto p3; 

fi;
p3: if
::  d_step {w[2] = 1;a[2] = 0;}  goto p4; 

fi;
p4: if
::  d_step {s[2]==1;j = 0;}  goto p9; 

::  d_step {s[2]==0;j = 0;}  goto p5; 

fi;
p5: if
::  d_step {j<3 && a[j]==0;j = j+1;}  goto p5; 

::  ! (j<3 && a[j]==0); goto p6; 

fi;
p6: if
:: j<3; goto p7; 

::  d_step {j==3;s[2] = 1;j = 0;}  goto p61; 

fi;
p61: if
::  d_step {j<3 && a[j]==0;j = j+1;}  goto p61; 

::  d_step {j<3 && a[j]==1;s[2] = 0;}  goto p7; 

::  d_step {j==3;w[2] = 0;j = 0;}  goto p64; 

fi;
p64: if
::  d_step {j<3 && w[j]==0;j = j+1;}  goto p64; 

:: j==3; goto p7; 

fi;
p7: if
:: j==3; goto p8; 

::  d_step {j<3;j = 0;}  goto p71; 

fi;
p71: if
::  d_step {j<3 && (w[j]==1 || s[j]==0);j = j+1;}  goto p71; 

::  ! (j<3 && (w[j]==1 || s[j]==0)); goto p8; 

fi;
p8: if
::  d_step {j!=2 && j<3;s[2] = 1;w[2] = 0;}  goto p4; 

::  ! (j!=2 && j<3); goto p4; 

fi;
p9: if
::  d_step {j<2 && w[j]==0 && s[j]==0;j = j+1;}  goto p9; 

:: j==2; goto CS; 

fi;
CS: if
:: s[2] = 0; goto NCS; 

fi;
}

