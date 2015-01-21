byte temperature[2];
byte surface[2];
byte shape[2];
byte painted[2];
byte hashole[2];
byte busy[8];
byte scheduled[2];
byte objscheduled=0;


init { 
 d_step { 
temperature[0] =0; temperature[1] =0; surface[0] =1; surface[1] =1; shape[0] =1; shape[1] =1; painted[0] =0; painted[1] =1; hashole[0] =0; hashole[1] =0; busy[0] =0; busy[1] =0; busy[2] =0; busy[3] =0; busy[4] =0; busy[5] =0; busy[6] =0; busy[7] =0; scheduled[0] =0; scheduled[1] =0; }
atomic { 
run P();
} }

proctype P() { 

q: if
:: shape[1]==1 && surface[0]==0 && surface[1]==0 && hashole[1]==1 && shape[0]==0; goto done; 

::  d_step {busy[0]==0 && scheduled[0]==0 && temperature[0]==0;objscheduled = 1;busy[0] = 1;scheduled[0] = 1;surface[0] = 0;}  goto q; 

::  d_step {busy[0]==0 && scheduled[1]==0 && temperature[1]==0;objscheduled = 1;busy[0] = 1;scheduled[1] = 1;surface[1] = 0;}  goto q; 

::  d_step {busy[1]==0 && scheduled[0]==0;objscheduled = 1;busy[1] = 1;scheduled[0] = 1;shape[0] = 0;temperature[0] = 1;painted[0] = 255;hashole[0] = 255;surface[0] = 255;}  goto q; 

::  d_step {busy[1]==0 && scheduled[1]==0;objscheduled = 1;busy[1] = 1;scheduled[1] = 1;shape[1] = 0;temperature[1] = 1;painted[1] = 255;hashole[1] = 255;surface[1] = 255;}  goto q; 

::  d_step {busy[2]==0 && scheduled[0]==0;objscheduled = 1;busy[2] = 1;scheduled[0] = 1;surface[0] = 2;shape[0] = 0;painted[0] = 255;}  goto q; 

::  d_step {busy[2]==0 && scheduled[1]==0;objscheduled = 1;busy[2] = 1;scheduled[1] = 1;surface[1] = 2;shape[1] = 0;painted[1] = 255;}  goto q; 

::  d_step {busy[3]==0 && scheduled[0]==0;objscheduled = 1;busy[3] = 1;scheduled[0] = 1;surface[0] = 1;painted[0] = 255;}  goto q; 

::  d_step {busy[3]==0 && scheduled[1]==0;objscheduled = 1;busy[3] = 1;scheduled[1] = 1;surface[1] = 1;painted[1] = 255;}  goto q; 

::  d_step {busy[4]==0 && scheduled[0]==0 && temperature[0]==0 && hashole[0]!=0;objscheduled = 1;busy[4] = 1;scheduled[0] = 1;surface[0] = 2;hashole[0] = 0;}  goto q; 

::  d_step {busy[4]==0 && scheduled[1]==0 && temperature[1]==0 && hashole[1]!=0;objscheduled = 1;busy[4] = 1;scheduled[1] = 1;surface[1] = 2;hashole[1] = 0;}  goto q; 

::  d_step {busy[4]==0 && scheduled[0]==0 && temperature[0]==0 && hashole[0]!=1;objscheduled = 1;busy[4] = 1;scheduled[0] = 1;surface[0] = 2;hashole[0] = 1;}  goto q; 

::  d_step {busy[4]==0 && scheduled[1]==0 && temperature[1]==0 && hashole[1]!=1;objscheduled = 1;busy[4] = 1;scheduled[1] = 1;surface[1] = 2;hashole[1] = 1;}  goto q; 

::  d_step {busy[5]==0 && scheduled[0]==0 && temperature[0]==0 && hashole[0]!=0;objscheduled = 1;busy[5] = 1;scheduled[0] = 1;hashole[0] = 0;}  goto q; 

::  d_step {busy[5]==0 && scheduled[1]==0 && temperature[1]==0 && hashole[1]!=0;objscheduled = 1;busy[5] = 1;scheduled[1] = 1;hashole[1] = 0;}  goto q; 

::  d_step {busy[5]==0 && scheduled[0]==0 && temperature[0]==0 && hashole[0]!=1;objscheduled = 1;busy[5] = 1;scheduled[0] = 1;hashole[0] = 1;}  goto q; 

::  d_step {busy[5]==0 && scheduled[1]==0 && temperature[1]==0 && hashole[1]!=1;objscheduled = 1;busy[5] = 1;scheduled[1] = 1;hashole[1] = 1;}  goto q; 

::  d_step {busy[6]==0 && scheduled[0]==0 && temperature[0]==0;objscheduled = 1;busy[6] = 1;scheduled[0] = 1;painted[0] = 0;}  goto q; 

::  d_step {busy[6]==0 && scheduled[1]==0 && temperature[1]==0;objscheduled = 1;busy[6] = 1;scheduled[1] = 1;painted[1] = 0;}  goto q; 

::  d_step {busy[6]==0 && scheduled[0]==0 && temperature[0]==0;objscheduled = 1;busy[6] = 1;scheduled[0] = 1;painted[0] = 1;}  goto q; 

::  d_step {busy[6]==0 && scheduled[1]==0 && temperature[1]==0;objscheduled = 1;busy[6] = 1;scheduled[1] = 1;painted[1] = 1;}  goto q; 

::  d_step {busy[7]==0 && scheduled[0]==0;objscheduled = 1;busy[7] = 1;scheduled[0] = 1;painted[0] = 0;}  goto q; 

::  d_step {busy[7]==0 && scheduled[1]==0;objscheduled = 1;busy[7] = 1;scheduled[1] = 1;painted[1] = 0;}  goto q; 

::  d_step {busy[7]==0 && scheduled[0]==0;objscheduled = 1;busy[7] = 1;scheduled[0] = 1;painted[0] = 1;}  goto q; 

::  d_step {busy[7]==0 && scheduled[1]==0;objscheduled = 1;busy[7] = 1;scheduled[1] = 1;painted[1] = 1;}  goto q; 

::  d_step {objscheduled==1;scheduled[0] = 0;scheduled[1] = 0;busy[0] = 0;busy[1] = 0;busy[2] = 0;busy[3] = 0;busy[4] = 0;busy[5] = 0;busy[6] = 0;busy[7] = 0;objscheduled = 0;}  goto q; 

fi;
done: 
 false; }

