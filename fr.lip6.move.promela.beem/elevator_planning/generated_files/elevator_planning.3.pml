byte person[4];
byte conflictA[4];
byte conflictB[4];
byte not_alone[4];
byte at=0;
byte inA=0;
byte inB=0;
byte inn=0;
byte alone=0;
byte i=0;


init { 
 d_step { 
person[0] =7; person[1] =2; person[2] =6; person[3] =5; conflictA[0] =0; conflictA[1] =0; conflictA[2] =0; conflictA[3] =0; conflictB[0] =0; conflictB[1] =0; conflictB[2] =0; conflictB[3] =0; not_alone[0] =0; not_alone[1] =0; not_alone[2] =0; not_alone[3] =0; }
atomic { 
run Elevator();
} }

proctype Elevator() { 

q: if
:: person[0]==0 && person[1]==0 && person[2]==0 && person[3]==0; goto done; 

::  d_step {person[0]==at && inn<4 && (conflictA[0]==0 || inB==0) && (conflictB[0]==0 || inA==0) && (not_alone[0]==0 || inn>0);person[0] = 255;inn = inn+1;inA = inA+conflictA[0];inB = inB+conflictB[0];alone = alone+not_alone[0];}  goto q; 

::  d_step {person[1]==at && inn<4 && (conflictA[1]==0 || inB==0) && (conflictB[1]==0 || inA==0) && (not_alone[1]==0 || inn>0);person[1] = 255;inn = inn+1;inA = inA+conflictA[1];inB = inB+conflictB[1];alone = alone+not_alone[1];}  goto q; 

::  d_step {person[2]==at && inn<4 && (conflictA[2]==0 || inB==0) && (conflictB[2]==0 || inA==0) && (not_alone[2]==0 || inn>0);person[2] = 255;inn = inn+1;inA = inA+conflictA[2];inB = inB+conflictB[2];alone = alone+not_alone[2];}  goto q; 

::  d_step {person[3]==at && inn<4 && (conflictA[3]==0 || inB==0) && (conflictB[3]==0 || inA==0) && (not_alone[3]==0 || inn>0);person[3] = 255;inn = inn+1;inA = inA+conflictA[3];inB = inB+conflictB[3];alone = alone+not_alone[3];}  goto q; 

::  d_step {person[0]==255 && (inn>2 || (alone-not_alone[0]==0));person[0] = at;inn = inn-1;inA = inA-conflictA[0];inB = inB-conflictB[0];alone = alone-not_alone[0];}  goto q; 

::  d_step {person[1]==255 && (inn>2 || (alone-not_alone[1]==0));person[1] = at;inn = inn-1;inA = inA-conflictA[1];inB = inB-conflictB[1];alone = alone-not_alone[1];}  goto q; 

::  d_step {person[2]==255 && (inn>2 || (alone-not_alone[2]==0));person[2] = at;inn = inn-1;inA = inA-conflictA[2];inB = inB-conflictB[2];alone = alone-not_alone[2];}  goto q; 

::  d_step {person[3]==255 && (inn>2 || (alone-not_alone[3]==0));person[3] = at;inn = inn-1;inA = inA-conflictA[3];inB = inB-conflictB[3];alone = alone-not_alone[3];}  goto q; 

:: at = 0; goto q; 

:: at = 1; goto q; 

:: at = 2; goto q; 

:: at = 3; goto q; 

:: at = 4; goto q; 

:: at = 5; goto q; 

:: at = 6; goto q; 

:: at = 7; goto q; 

fi;
done: 
 false; }

