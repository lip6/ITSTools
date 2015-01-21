byte chnl[2];
byte partner[2];
byte call_forward_busy[2];
byte record[2];


init { 
 d_step { 
chnl[0] =255; chnl[1] =255; partner[0] =255; partner[1] =255; call_forward_busy[0] =1; call_forward_busy[1] =255; record[0] =255; record[1] =255; }
atomic { 
run User_0();
run User_1();
} }

proctype User_0() { 
byte dev=1;
byte mbit=0;

idle: if
::  d_step {chnl[0]==255;dev = 0;chnl[0] = ((0)+(0)*20);}  goto dialing; 

::  d_step {chnl[0]!=255;partner[0] = ((chnl[0])%20);}  goto q_i; 

fi;
dialing: if
::  d_step {dev = 1;chnl[0] = 255;}  goto idle; 

:: partner[0] = 0; goto calling; 

:: partner[0] = 1; goto calling; 

:: partner[0] = 2; goto calling; 

fi;
calling: if
:: partner[0]==0; goto busy; 

:: partner[0]==2; goto unobtainable; 

:: partner[0]==2; goto ringback; 

::  d_step {partner[0]!=0 && partner[0]!=2 && chnl[partner[0]]!=255 && call_forward_busy[partner[0]]==255;record[partner[0]] = 0;}  goto busy; 

::  d_step {partner[0]!=0 && partner[0]!=2 && chnl[partner[0]]!=255 && call_forward_busy[partner[0]]!=255;record[partner[0]] = 0;partner[0] = call_forward_busy[partner[0]];}  goto calling; 

::  d_step {partner[0]!=0 && partner[0]!=2 && chnl[partner[0]]==255;record[partner[0]] = 0;chnl[partner[0]] = ((0)+(0)*20);chnl[0] = ((partner[0])+(0)*20);}  goto oalert; 

fi;
busy: if
::  d_step {chnl[0] = 255;partner[0] = 255;dev = 1;}  goto idle; 

fi;
q_i: if
:: ((chnl[partner[0]])%20)==0; goto talert; 

::  d_step {((chnl[partner[0]])%20)!=0;partner[0] = 255;}  goto idle; 

fi;
talert: if
:: dev!=1 || chnl[0]==255; goto error_state; 

:: ((chnl[partner[0]])%20)==0; goto tpickup; 

:: ((chnl[partner[0]])%20)!=0; goto idle; 

fi;
unobtainable: if
::  d_step {chnl[0] = 255;partner[0] = 255;dev = 1;}  goto idle; 

fi;
oalert: if
:: ((chnl[0])%20)!=partner[0]; goto error_state; 

:: ((chnl[0])%20)==partner[0] && ((chnl[0])/20)==1; goto oconnected; 

:: ((chnl[0])%20)==partner[0] && ((chnl[0])/20)==0; goto oringout; 

fi;
oconnected: if
::  d_step {dev = 1;chnl[0] = 255;chnl[partner[0]] = 255;}  goto idle; 

fi;
oringout: if
::  d_step {dev = 1;chnl[0] = 255;partner[0] = ((((partner[0])%20))+(0)*20);}  goto idle; 

fi;
tpickup: if
::  d_step {((chnl[partner[0]])%20)==0 && ((chnl[partner[0]])/20)==0;dev = 0;chnl[partner[0]] = ((0)+(1)*20);chnl[0] = ((partner[0])+(1)*20);}  goto tconnected; 

::  d_step {chnl[partner[0]]==255 || ((chnl[partner[0]])%20)!=0;dev = 1;partner[0] = 255;chnl[0] = 255;}  goto idle; 

fi;
tconnected: if
::  d_step {((chnl[0])/20)==1 && dev==0;dev = 1;}  goto tconnected; 

::  d_step {((chnl[0])/20)==1 && dev==1;dev = 0;}  goto tconnected; 

::  d_step {((chnl[0])/20)==0;partner[0] = 255;chnl[0] = 255;}  goto idle; 

fi;
ringback: if
::  d_step {chnl[0] = 255;partner[0] = 255;dev = 1;}  goto idle; 

::  d_step {record[0]!=255;partner[0] = record[0];}  goto calling; 

fi;
error_state: 
 false; }

proctype User_1() { 
byte dev=1;
byte mbit=0;

idle: if
::  d_step {chnl[1]==255;dev = 0;chnl[1] = ((1)+(0)*20);}  goto dialing; 

::  d_step {chnl[1]!=255;partner[1] = ((chnl[1])%20);}  goto q_i; 

fi;
dialing: if
::  d_step {dev = 1;chnl[1] = 255;}  goto idle; 

:: partner[1] = 0; goto calling; 

:: partner[1] = 1; goto calling; 

:: partner[1] = 2; goto calling; 

fi;
calling: if
:: partner[1]==1; goto busy; 

:: partner[1]==2; goto unobtainable; 

:: partner[1]==2; goto ringback; 

::  d_step {partner[1]!=1 && partner[1]!=2 && chnl[partner[1]]!=255 && call_forward_busy[partner[1]]==255;record[partner[1]] = 1;}  goto busy; 

::  d_step {partner[1]!=1 && partner[1]!=2 && chnl[partner[1]]!=255 && call_forward_busy[partner[1]]!=255;record[partner[1]] = 1;partner[1] = call_forward_busy[partner[1]];}  goto calling; 

::  d_step {partner[1]!=1 && partner[1]!=2 && chnl[partner[1]]==255;record[partner[1]] = 1;chnl[partner[1]] = ((1)+(0)*20);chnl[1] = ((partner[1])+(0)*20);}  goto oalert; 

fi;
busy: if
::  d_step {chnl[1] = 255;partner[1] = 255;dev = 1;}  goto idle; 

fi;
q_i: if
:: ((chnl[partner[1]])%20)==1; goto talert; 

::  d_step {((chnl[partner[1]])%20)!=1;partner[1] = 255;}  goto idle; 

fi;
talert: if
:: dev!=1 || chnl[1]==255; goto error_state; 

:: ((chnl[partner[1]])%20)==1; goto tpickup; 

:: ((chnl[partner[1]])%20)!=1; goto idle; 

fi;
unobtainable: if
::  d_step {chnl[1] = 255;partner[1] = 255;dev = 1;}  goto idle; 

fi;
oalert: if
:: ((chnl[1])%20)!=partner[1]; goto error_state; 

:: ((chnl[1])%20)==partner[1] && ((chnl[1])/20)==1; goto oconnected; 

:: ((chnl[1])%20)==partner[1] && ((chnl[1])/20)==0; goto oringout; 

fi;
oconnected: if
::  d_step {dev = 1;chnl[1] = 255;chnl[partner[1]] = 255;}  goto idle; 

fi;
oringout: if
::  d_step {dev = 1;chnl[1] = 255;partner[1] = ((((partner[1])%20))+(0)*20);}  goto idle; 

fi;
tpickup: if
::  d_step {((chnl[partner[1]])%20)==1 && ((chnl[partner[1]])/20)==0;dev = 0;chnl[partner[1]] = ((1)+(1)*20);chnl[1] = ((partner[1])+(1)*20);}  goto tconnected; 

::  d_step {chnl[partner[1]]==255 || ((chnl[partner[1]])%20)!=1;dev = 1;partner[1] = 255;chnl[1] = 255;}  goto idle; 

fi;
tconnected: if
::  d_step {((chnl[1])/20)==1 && dev==0;dev = 1;}  goto tconnected; 

::  d_step {((chnl[1])/20)==1 && dev==1;dev = 0;}  goto tconnected; 

::  d_step {((chnl[1])/20)==0;partner[1] = 255;chnl[1] = 255;}  goto idle; 

fi;
ringback: if
::  d_step {chnl[1] = 255;partner[1] = 255;dev = 1;}  goto idle; 

::  d_step {record[1]!=255;partner[1] = record[1];}  goto calling; 

fi;
error_state: 
 false; }

