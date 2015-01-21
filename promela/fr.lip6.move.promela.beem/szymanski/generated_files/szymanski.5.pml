byte a[5];
byte w[5];
byte s[5];


active proctype P_0() { 
byte j=0;

NCS: if
::  d_step {a[0] = 1;j = 0;}  goto p2; 

fi;
p2: if
::  d_step {j<5 && s[j]==0;j = j+1;}  goto p2; 

:: j==5; goto p3; 

fi;
p3: if
::  d_step {w[0] = 1;a[0] = 0;}  goto p4; 

fi;
p4: if
::  d_step {s[0]==1;j = 0;}  goto p9; 

::  d_step {s[0]==0;j = 0;}  goto p5; 

fi;
p5: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p5; 

::  ! (j<5 && a[j]==0); goto p6; 

fi;
p6: if
:: j<5; goto p7; 

::  d_step {j==5;s[0] = 1;j = 0;}  goto p61; 

fi;
p61: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p61; 

::  d_step {j<5 && a[j]==1;s[0] = 0;}  goto p7; 

::  d_step {j==5;w[0] = 0;j = 0;}  goto p64; 

fi;
p64: if
::  d_step {j<5 && w[j]==0;j = j+1;}  goto p64; 

:: j==5; goto p7; 

fi;
p7: if
:: j==5; goto p8; 

::  d_step {j<5;j = 0;}  goto p71; 

fi;
p71: if
::  d_step {j<5 && (w[j]==1 || s[j]==0);j = j+1;}  goto p71; 

::  ! (j<5 && (w[j]==1 || s[j]==0)); goto p8; 

fi;
p8: if
::  d_step {j!=0 && j<5;s[0] = 1;w[0] = 0;}  goto p4; 

::  ! (j!=0 && j<5); goto p4; 

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
::  d_step {j<5 && s[j]==0;j = j+1;}  goto p2; 

:: j==5; goto p3; 

fi;
p3: if
::  d_step {w[1] = 1;a[1] = 0;}  goto p4; 

fi;
p4: if
::  d_step {s[1]==1;j = 0;}  goto p9; 

::  d_step {s[1]==0;j = 0;}  goto p5; 

fi;
p5: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p5; 

::  ! (j<5 && a[j]==0); goto p6; 

fi;
p6: if
:: j<5; goto p7; 

::  d_step {j==5;s[1] = 1;j = 0;}  goto p61; 

fi;
p61: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p61; 

::  d_step {j<5 && a[j]==1;s[1] = 0;}  goto p7; 

::  d_step {j==5;w[1] = 0;j = 0;}  goto p64; 

fi;
p64: if
::  d_step {j<5 && w[j]==0;j = j+1;}  goto p64; 

:: j==5; goto p7; 

fi;
p7: if
:: j==5; goto p8; 

::  d_step {j<5;j = 0;}  goto p71; 

fi;
p71: if
::  d_step {j<5 && (w[j]==1 || s[j]==0);j = j+1;}  goto p71; 

::  ! (j<5 && (w[j]==1 || s[j]==0)); goto p8; 

fi;
p8: if
::  d_step {j!=1 && j<5;s[1] = 1;w[1] = 0;}  goto p4; 

::  ! (j!=1 && j<5); goto p4; 

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
::  d_step {j<5 && s[j]==0;j = j+1;}  goto p2; 

:: j==5; goto p3; 

fi;
p3: if
::  d_step {w[2] = 1;a[2] = 0;}  goto p4; 

fi;
p4: if
::  d_step {s[2]==1;j = 0;}  goto p9; 

::  d_step {s[2]==0;j = 0;}  goto p5; 

fi;
p5: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p5; 

::  ! (j<5 && a[j]==0); goto p6; 

fi;
p6: if
:: j<5; goto p7; 

::  d_step {j==5;s[2] = 1;j = 0;}  goto p61; 

fi;
p61: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p61; 

::  d_step {j<5 && a[j]==1;s[2] = 0;}  goto p7; 

::  d_step {j==5;w[2] = 0;j = 0;}  goto p64; 

fi;
p64: if
::  d_step {j<5 && w[j]==0;j = j+1;}  goto p64; 

:: j==5; goto p7; 

fi;
p7: if
:: j==5; goto p8; 

::  d_step {j<5;j = 0;}  goto p71; 

fi;
p71: if
::  d_step {j<5 && (w[j]==1 || s[j]==0);j = j+1;}  goto p71; 

::  ! (j<5 && (w[j]==1 || s[j]==0)); goto p8; 

fi;
p8: if
::  d_step {j!=2 && j<5;s[2] = 1;w[2] = 0;}  goto p4; 

::  ! (j!=2 && j<5); goto p4; 

fi;
p9: if
::  d_step {j<2 && w[j]==0 && s[j]==0;j = j+1;}  goto p9; 

:: j==2; goto CS; 

fi;
CS: if
:: s[2] = 0; goto NCS; 

fi;
}

active proctype P_3() { 
byte j=0;

NCS: if
::  d_step {a[3] = 1;j = 0;}  goto p2; 

fi;
p2: if
::  d_step {j<5 && s[j]==0;j = j+1;}  goto p2; 

:: j==5; goto p3; 

fi;
p3: if
::  d_step {w[3] = 1;a[3] = 0;}  goto p4; 

fi;
p4: if
::  d_step {s[3]==1;j = 0;}  goto p9; 

::  d_step {s[3]==0;j = 0;}  goto p5; 

fi;
p5: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p5; 

::  ! (j<5 && a[j]==0); goto p6; 

fi;
p6: if
:: j<5; goto p7; 

::  d_step {j==5;s[3] = 1;j = 0;}  goto p61; 

fi;
p61: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p61; 

::  d_step {j<5 && a[j]==1;s[3] = 0;}  goto p7; 

::  d_step {j==5;w[3] = 0;j = 0;}  goto p64; 

fi;
p64: if
::  d_step {j<5 && w[j]==0;j = j+1;}  goto p64; 

:: j==5; goto p7; 

fi;
p7: if
:: j==5; goto p8; 

::  d_step {j<5;j = 0;}  goto p71; 

fi;
p71: if
::  d_step {j<5 && (w[j]==1 || s[j]==0);j = j+1;}  goto p71; 

::  ! (j<5 && (w[j]==1 || s[j]==0)); goto p8; 

fi;
p8: if
::  d_step {j!=3 && j<5;s[3] = 1;w[3] = 0;}  goto p4; 

::  ! (j!=3 && j<5); goto p4; 

fi;
p9: if
::  d_step {j<3 && w[j]==0 && s[j]==0;j = j+1;}  goto p9; 

:: j==3; goto CS; 

fi;
CS: if
:: s[3] = 0; goto NCS; 

fi;
}

active proctype P_4() { 
byte j=0;

NCS: if
::  d_step {a[4] = 1;j = 0;}  goto p2; 

fi;
p2: if
::  d_step {j<5 && s[j]==0;j = j+1;}  goto p2; 

:: j==5; goto p3; 

fi;
p3: if
::  d_step {w[4] = 1;a[4] = 0;}  goto p4; 

fi;
p4: if
::  d_step {s[4]==1;j = 0;}  goto p9; 

::  d_step {s[4]==0;j = 0;}  goto p5; 

fi;
p5: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p5; 

::  ! (j<5 && a[j]==0); goto p6; 

fi;
p6: if
:: j<5; goto p7; 

::  d_step {j==5;s[4] = 1;j = 0;}  goto p61; 

fi;
p61: if
::  d_step {j<5 && a[j]==0;j = j+1;}  goto p61; 

::  d_step {j<5 && a[j]==1;s[4] = 0;}  goto p7; 

::  d_step {j==5;w[4] = 0;j = 0;}  goto p64; 

fi;
p64: if
::  d_step {j<5 && w[j]==0;j = j+1;}  goto p64; 

:: j==5; goto p7; 

fi;
p7: if
:: j==5; goto p8; 

::  d_step {j<5;j = 0;}  goto p71; 

fi;
p71: if
::  d_step {j<5 && (w[j]==1 || s[j]==0);j = j+1;}  goto p71; 

::  ! (j<5 && (w[j]==1 || s[j]==0)); goto p8; 

fi;
p8: if
::  d_step {j!=4 && j<5;s[4] = 1;w[4] = 0;}  goto p4; 

::  ! (j!=4 && j<5); goto p4; 

fi;
p9: if
::  d_step {j<4 && w[j]==0 && s[j]==0;j = j+1;}  goto p9; 

:: j==4; goto CS; 

fi;
CS: if
:: s[4] = 0; goto NCS; 

fi;
}

