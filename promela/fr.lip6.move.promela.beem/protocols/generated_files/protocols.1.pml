
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
::  d_step {lost<3;lost = lost+1;}  goto ready; 

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
::  d_step {lost<3;lost = lost+1;}  goto ready; 

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

ready: if
:: send?value; goto sending; 

fi;
sending: if
:: K_in!value; goto ready; 

fi;
}

active proctype Receiver() { 
byte value=0;

wait_msg: if
:: K_out?value; goto got_msg; 

fi;
got_msg: if
:: receive!value; goto wait_msg; 

fi;
}

