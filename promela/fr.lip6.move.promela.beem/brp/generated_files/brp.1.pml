byte prod_n=0;

chan Sin =[0] of {int};
chan Sout =[0] of {int};
chan toK =[0] of {int};
chan fromK =[0] of {int};
chan toL =[0] of {int};
chan fromL =[0] of {int};
chan timeisout =[0] of {int};
chan Rout =[0] of {int};
chan shake =[0] of {int};
chan shakePC =[0] of {int};

active proctype Producer() { 
byte result=0;
byte n=0;

ready: if
:: prod_n = 1; goto start_send; 

:: prod_n = 2; goto start_send; 

:: prod_n = 3; goto start_send; 

:: prod_n = 4; goto start_send; 

:: prod_n = 5; goto start_send; 

fi;
start_send: if
:: Sin!prod_n; goto wait_result; 

fi;
wait_result: if
:: Sout?result; goto check; 

fi;
check: if
::  atomic {result==1;shakePC!0;}  goto ready; 

:: result==2 || result==3; goto start_send; 

fi;
}

active proctype Consumer() { 
byte m=0;
byte n=0;

ready: if
::  atomic {Rout?m;n = n+1;}  goto get_msg; 

fi;
get_msg: if
:: (m==4) || (m==5); goto ready; 

::  d_step {m==2;n = 0;}  goto ready; 

:: m==1; goto check; 

fi;
check: if
::  atomic {n==prod_n;shakePC?0;n = 0;}  goto ready; 

:: n!=prod_n; goto st_error; 

fi;
st_error: 
 false; }

active proctype Sender() { 
byte ab=0;
byte n=0;
byte i=0;
byte counter=0;

idle: if
::  atomic {Sin?n;i = 1;}  goto next_frame; 

fi;
next_frame: if
:: counter = 0; goto send; 

fi;
wait_ack: if
::  atomic {fromL?0;ab = 1-ab;}  goto success; 

::  atomic {counter==3;timeisout?0;}  goto q_error; 

::  atomic {counter<3;timeisout?0;counter = counter+1;}  goto send; 

fi;
send: if
::  atomic {i==1 && i==n;toK!(4+2+ab);}  goto wait_ack; 

::  atomic {i>1 && i==n;toK!(2+ab);}  goto wait_ack; 

::  atomic {i==1 && i<n;toK!(4+ab);}  goto wait_ack; 

::  atomic {i>1 && i<n;toK!ab;}  goto wait_ack; 

fi;
success: if
::  d_step {i<n;i = i+1;}  goto next_frame; 

::  atomic {i==n;Sout!1;}  goto ret; 

fi;
q_error: if
::  atomic {i<n;Sout!2;}  goto ret; 

::  atomic {i==n;Sout!3;}  goto ret; 

fi;
ret: if
:: shake!0; goto idle; 

fi;
}

active proctype Receiver() { 
byte value=0;
byte exp_ab=0;

new_file: if
:: fromK?value; goto first_safe; 

:: shake?0; goto new_file; 

fi;
idle: if
:: fromK?value; goto frame_received; 

:: (value&2)==2; goto ret; 

:: Rout!2; goto ret; 

fi;
frame_received: if
::  atomic {(value&1)==exp_ab && (value&2)==2;Rout!1;}  goto frame_reported; 

::  atomic {((value&1)==exp_ab) && ((value&2)==0) && ((value&4)==0);Rout!5;}  goto frame_reported; 

::  atomic {((value&1)==exp_ab) && ((value&2)==0) && ((value&4)==4);Rout!4;}  goto frame_reported; 

::  atomic {exp_ab!=(value&1);toL!0;}  goto idle; 

fi;
frame_reported: if
::  atomic {toL!0;exp_ab = 1-exp_ab;}  goto idle; 

fi;
first_safe: if
:: exp_ab = (value&1); goto frame_received; 

fi;
ret: if
:: shake?0; goto new_file; 

fi;
}

active proctype K() { 
byte value=0;

ready: if
:: toK?value; goto got_msg; 

fi;
got_msg: if
:: timeisout!0; goto ready; 

:: fromK!value; goto ready; 

fi;
}

active proctype L() { 

ready: if
:: toL?0; goto got_msg; 

fi;
got_msg: if
:: fromL!0; goto ready; 

:: timeisout!0; goto ready; 

fi;
}

