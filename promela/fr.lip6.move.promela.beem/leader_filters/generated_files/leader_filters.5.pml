byte turn[6];
byte b[6];
byte c[6];


active proctype P_0() { 
byte curr=0;

p1: if
:: turn[curr] = 0; goto p2; 

fi;
p2: if
:: b[curr]==0; goto p3; 

fi;
p3: if
:: b[curr] = 1; goto p4; 

fi;
p4: if
:: turn[curr]!=0; goto p5; 

:: turn[curr]==0; goto p8; 

fi;
p5: if
:: c[curr] = 1; goto p6; 

fi;
p6: if
:: b[curr] = 0; goto p7; 

fi;
p8: if
:: curr>0 && c[curr-1]==0; goto p9; 

::  d_step {curr==0 || c[curr-1]==1;curr = curr+1;}  goto p1; 

fi;
p9: if
::  goto elected; 

fi;
p7: 
elected: 
 false; }

active proctype P_1() { 
byte curr=0;

p1: if
:: turn[curr] = 1; goto p2; 

fi;
p2: if
:: b[curr]==0; goto p3; 

fi;
p3: if
:: b[curr] = 1; goto p4; 

fi;
p4: if
:: turn[curr]!=1; goto p5; 

:: turn[curr]==1; goto p8; 

fi;
p5: if
:: c[curr] = 1; goto p6; 

fi;
p6: if
:: b[curr] = 0; goto p7; 

fi;
p8: if
:: curr>0 && c[curr-1]==0; goto p9; 

::  d_step {curr==0 || c[curr-1]==1;curr = curr+1;}  goto p1; 

fi;
p9: if
::  goto elected; 

fi;
p7: 
elected: 
 false; }

active proctype P_2() { 
byte curr=0;

p1: if
:: turn[curr] = 2; goto p2; 

fi;
p2: if
:: b[curr]==0; goto p3; 

fi;
p3: if
:: b[curr] = 1; goto p4; 

fi;
p4: if
:: turn[curr]!=2; goto p5; 

:: turn[curr]==2; goto p8; 

fi;
p5: if
:: c[curr] = 1; goto p6; 

fi;
p6: if
:: b[curr] = 0; goto p7; 

fi;
p8: if
:: curr>0 && c[curr-1]==0; goto p9; 

::  d_step {curr==0 || c[curr-1]==1;curr = curr+1;}  goto p1; 

fi;
p9: if
::  goto elected; 

fi;
p7: 
elected: 
 false; }

active proctype P_3() { 
byte curr=0;

p1: if
:: turn[curr] = 3; goto p2; 

fi;
p2: if
:: b[curr]==0; goto p3; 

fi;
p3: if
:: b[curr] = 1; goto p4; 

fi;
p4: if
:: turn[curr]!=3; goto p5; 

:: turn[curr]==3; goto p8; 

fi;
p5: if
:: c[curr] = 1; goto p6; 

fi;
p6: if
:: b[curr] = 0; goto p7; 

fi;
p8: if
:: curr>0 && c[curr-1]==0; goto p9; 

::  d_step {curr==0 || c[curr-1]==1;curr = curr+1;}  goto p1; 

fi;
p9: if
::  goto elected; 

fi;
p7: 
elected: 
 false; }

active proctype P_4() { 
byte curr=0;

p1: if
:: turn[curr] = 4; goto p2; 

fi;
p2: if
:: b[curr]==0; goto p3; 

fi;
p3: if
:: b[curr] = 1; goto p4; 

fi;
p4: if
:: turn[curr]!=4; goto p5; 

:: turn[curr]==4; goto p8; 

fi;
p5: if
:: c[curr] = 1; goto p6; 

fi;
p6: if
:: b[curr] = 0; goto p7; 

fi;
p8: if
:: curr>0 && c[curr-1]==0; goto p9; 

::  d_step {curr==0 || c[curr-1]==1;curr = curr+1;}  goto p1; 

fi;
p9: if
::  goto elected; 

fi;
p7: 
elected: 
 false; }

