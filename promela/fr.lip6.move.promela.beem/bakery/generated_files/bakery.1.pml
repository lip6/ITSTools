byte choosing[2];
byte number[2];


active proctype P_0() { 
byte j=0;
byte max=0;

NCS: if
::  d_step {choosing[0] = 1;j = 0;max = 0;}  goto choose; 

fi;
choose: if
::  d_step {j<2 && number[j]>max;max = number[j];j = j+1;}  goto choose; 

::  d_step {j<2 && number[j]<=max;j = j+1;}  goto choose; 

::  d_step {j==2 && max<9;number[0] = max+1;j = 0;choosing[0] = 0;}  goto for_loop; 

fi;
for_loop: if
:: j<2 && choosing[j]==0; goto wait; 

:: j==2; goto CS; 

fi;
wait: if
::  d_step {number[j]==0 || (number[j]>number[0]) || (number[j]==number[0] && 0<=j);j = j+1;}  goto for_loop; 

fi;
CS: if
:: number[0] = 0; goto NCS; 

fi;
}

active proctype P_1() { 
byte j=0;
byte max=0;

NCS: if
::  d_step {choosing[1] = 1;j = 0;max = 0;}  goto choose; 

fi;
choose: if
::  d_step {j<2 && number[j]>max;max = number[j];j = j+1;}  goto choose; 

::  d_step {j<2 && number[j]<=max;j = j+1;}  goto choose; 

::  d_step {j==2 && max<9;number[1] = max+1;j = 0;choosing[1] = 0;}  goto for_loop; 

fi;
for_loop: if
:: j<2 && choosing[j]==0; goto wait; 

:: j==2; goto CS; 

fi;
wait: if
::  d_step {number[j]==0 || (number[j]>number[1]) || (number[j]==number[1] && 1<=j);j = j+1;}  goto for_loop; 

fi;
CS: if
:: number[1] = 0; goto NCS; 

fi;
}

