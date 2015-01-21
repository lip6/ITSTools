byte on[10];
byte clear[10];
byte holding=253;


init { 
 d_step { 
on[0] =3; on[1] =6; on[2] =4; on[3] =8; on[4] =9; on[5] =254; on[6] =7; on[7] =0; on[8] =254; on[9] =1; clear[0] =0; clear[1] =0; clear[2] =1; clear[3] =0; clear[4] =0; clear[5] =1; clear[6] =0; clear[7] =0; clear[8] =0; clear[9] =0; }
atomic { 
run Hand();
} }

proctype Hand() { 

emptyhand: if
:: on[3]==2 && on[2]==5 && on[5]==9 && on[9]==4 && on[4]==7 && on[7]==1 && on[1]==0 && on[0]==6 && on[6]==8; goto done; 

::  d_step {clear[0]==1 && on[0]==254;on[0] = 255;clear[0] = 0;holding = 0;}  goto fullhand; 

::  d_step {clear[1]==1 && on[1]==254;on[1] = 255;clear[1] = 0;holding = 1;}  goto fullhand; 

::  d_step {clear[2]==1 && on[2]==254;on[2] = 255;clear[2] = 0;holding = 2;}  goto fullhand; 

::  d_step {clear[3]==1 && on[3]==254;on[3] = 255;clear[3] = 0;holding = 3;}  goto fullhand; 

::  d_step {clear[4]==1 && on[4]==254;on[4] = 255;clear[4] = 0;holding = 4;}  goto fullhand; 

::  d_step {clear[5]==1 && on[5]==254;on[5] = 255;clear[5] = 0;holding = 5;}  goto fullhand; 

::  d_step {clear[6]==1 && on[6]==254;on[6] = 255;clear[6] = 0;holding = 6;}  goto fullhand; 

::  d_step {clear[7]==1 && on[7]==254;on[7] = 255;clear[7] = 0;holding = 7;}  goto fullhand; 

::  d_step {clear[8]==1 && on[8]==254;on[8] = 255;clear[8] = 0;holding = 8;}  goto fullhand; 

::  d_step {clear[9]==1 && on[9]==254;on[9] = 255;clear[9] = 0;holding = 9;}  goto fullhand; 

::  d_step {clear[0]==1 && on[0]!=254;clear[on[0]] = 1;on[0] = 255;clear[0] = 0;holding = 0;}  goto fullhand; 

::  d_step {clear[1]==1 && on[1]!=254;clear[on[1]] = 1;on[1] = 255;clear[1] = 0;holding = 1;}  goto fullhand; 

::  d_step {clear[2]==1 && on[2]!=254;clear[on[2]] = 1;on[2] = 255;clear[2] = 0;holding = 2;}  goto fullhand; 

::  d_step {clear[3]==1 && on[3]!=254;clear[on[3]] = 1;on[3] = 255;clear[3] = 0;holding = 3;}  goto fullhand; 

::  d_step {clear[4]==1 && on[4]!=254;clear[on[4]] = 1;on[4] = 255;clear[4] = 0;holding = 4;}  goto fullhand; 

::  d_step {clear[5]==1 && on[5]!=254;clear[on[5]] = 1;on[5] = 255;clear[5] = 0;holding = 5;}  goto fullhand; 

::  d_step {clear[6]==1 && on[6]!=254;clear[on[6]] = 1;on[6] = 255;clear[6] = 0;holding = 6;}  goto fullhand; 

::  d_step {clear[7]==1 && on[7]!=254;clear[on[7]] = 1;on[7] = 255;clear[7] = 0;holding = 7;}  goto fullhand; 

::  d_step {clear[8]==1 && on[8]!=254;clear[on[8]] = 1;on[8] = 255;clear[8] = 0;holding = 8;}  goto fullhand; 

::  d_step {clear[9]==1 && on[9]!=254;clear[on[9]] = 1;on[9] = 255;clear[9] = 0;holding = 9;}  goto fullhand; 

fi;
fullhand: if
::  d_step {clear[0]==1;clear[0] = 0;clear[holding] = 1;on[holding] = 0;holding = 253;}  goto emptyhand; 

::  d_step {clear[1]==1;clear[1] = 0;clear[holding] = 1;on[holding] = 1;holding = 253;}  goto emptyhand; 

::  d_step {clear[2]==1;clear[2] = 0;clear[holding] = 1;on[holding] = 2;holding = 253;}  goto emptyhand; 

::  d_step {clear[3]==1;clear[3] = 0;clear[holding] = 1;on[holding] = 3;holding = 253;}  goto emptyhand; 

::  d_step {clear[4]==1;clear[4] = 0;clear[holding] = 1;on[holding] = 4;holding = 253;}  goto emptyhand; 

::  d_step {clear[5]==1;clear[5] = 0;clear[holding] = 1;on[holding] = 5;holding = 253;}  goto emptyhand; 

::  d_step {clear[6]==1;clear[6] = 0;clear[holding] = 1;on[holding] = 6;holding = 253;}  goto emptyhand; 

::  d_step {clear[7]==1;clear[7] = 0;clear[holding] = 1;on[holding] = 7;holding = 253;}  goto emptyhand; 

::  d_step {clear[8]==1;clear[8] = 0;clear[holding] = 1;on[holding] = 8;holding = 253;}  goto emptyhand; 

::  d_step {clear[9]==1;clear[9] = 0;clear[holding] = 1;on[holding] = 9;holding = 253;}  goto emptyhand; 

::  d_step {clear[holding] = 1;on[holding] = 254;holding = 253;}  goto emptyhand; 

fi;
done: 
 false; }

