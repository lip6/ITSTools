
chan K_in =[0] of {int};
chan K_out =[0] of {int};
chan L_in =[0] of {int};
chan L_out =[0] of {int};
chan send =[0] of {int};
chan receive =[0] of {int};

active proctype chnlnel_K() { 
byte v=0;
byte lost=0;

ready: if
:: K_in?v; goto tr; 

fi;
tr: if
::  d_step {lost<20;lost = lost+1;}  goto ready; 

::  atomic {K_out!v;lost = 0;}  goto ready; 

fi;
data_lost: 
 false; }

active proctype chnlnel_L() { 
byte v=0;
byte lost=0;

ready: if
:: L_in?v; goto tr; 

fi;
tr: if
::  d_step {lost<20;lost = lost+1;}  goto ready; 

::  atomic {L_out!v;lost = 0;}  goto ready; 

fi;
dataOK: 
 false; }

active proctype Producer() { 

ready: if
::  goto produce0; 

::  goto produce1; 

fi;
produce0: if
:: send!0; goto ready; 

fi;
produce1: if
:: send!1; goto ready; 

fi;
}

active proctype Consumer() { 
byte value=0;

ready: if
:: receive?value; goto got_msg; 

fi;
got_msg: if
:: value==0; goto consume0; 

:: value==1; goto consume1; 

fi;
consume0: if
::  goto ready; 

fi;
consume1: if
::  goto ready; 

fi;
}

active proctype Sender() { 
byte value=0;
byte sab=0;
byte retry=0;

ready: if
::  atomic {send?value;sab = 1-sab;}  goto sending; 

fi;
sending: if
::  atomic {K_in!(value*2+sab);retry = 1;}  goto wait_ack; 

fi;
wait_ack: if
::  atomic {retry<30;K_in!(value*2+sab);retry = retry+1;}  goto wait_ack; 

:: L_out?value; goto ready; 

:: retry==30; goto failed; 

fi;
failed: 
 false; }

active proctype Receiver() { 
byte value=0;
byte rab=1;

waiting: if
:: K_out?value; goto got_msg; 

fi;
got_msg: if
:: value%2!=rab; goto waiting; 

::  atomic {value%2==rab;receive!(value/2);}  goto send_ack; 

fi;
send_ack: if
::  atomic {L_in!0;rab = 1-rab;}  goto waiting; 

fi;
}

