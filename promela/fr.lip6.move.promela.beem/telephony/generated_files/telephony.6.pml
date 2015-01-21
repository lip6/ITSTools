byte chnl[4];
byte partner[4];
byte record[4];


init { 
 d_step { 
chnl[0] =255; chnl[1] =255; chnl[2] =255; chnl[3] =255; partner[0] =255; partner[1] =255; partner[2] =255; partner[3] =255; record[0] =255; record[1] =255; record[2] =255; record[3] =255; }
atomic { 
run User_0();
run User_1();
run User_2();
run User_3();
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

:: partner[0] = 3; goto calling; 

:: partner[0] = 4; goto calling; 

fi;
calling: if
:: partner[0]==0; goto busy; 

:: partner[0]==4; goto unobtainable; 

:: partner[0]==4; goto ringback; 

::  d_step {partner[0]!=0 && partner[0]!=4 && chnl[partner[0]]!=255;record[partner[0]] = 0;}  goto busy; 

::  d_step {partner[0]!=0 && partner[0]!=4 && chnl[partner[0]]==255;record[partner[0]] = 0;chnl[partner[0]] = ((0)+(0)*20);chnl[0] = ((partner[0])+(0)*20);}  goto oalert; 

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

:: partner[1] = 3; goto calling; 

:: partner[1] = 4; goto calling; 

fi;
calling: if
:: partner[1]==1; goto busy; 

:: partner[1]==4; goto unobtainable; 

:: partner[1]==4; goto ringback; 

::  d_step {partner[1]!=1 && partner[1]!=4 && chnl[partner[1]]!=255;record[partner[1]] = 1;}  goto busy; 

::  d_step {partner[1]!=1 && partner[1]!=4 && chnl[partner[1]]==255;record[partner[1]] = 1;chnl[partner[1]] = ((1)+(0)*20);chnl[1] = ((partner[1])+(0)*20);}  goto oalert; 

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

proctype User_2() { 
byte dev=1;
byte mbit=0;

idle: if
::  d_step {chnl[2]==255;dev = 0;chnl[2] = ((2)+(0)*20);}  goto dialing; 

::  d_step {chnl[2]!=255;partner[2] = ((chnl[2])%20);}  goto q_i; 

fi;
dialing: if
::  d_step {dev = 1;chnl[2] = 255;}  goto idle; 

:: partner[2] = 0; goto calling; 

:: partner[2] = 1; goto calling; 

:: partner[2] = 2; goto calling; 

:: partner[2] = 3; goto calling; 

:: partner[2] = 4; goto calling; 

fi;
calling: if
:: partner[2]==2; goto busy; 

:: partner[2]==4; goto unobtainable; 

:: partner[2]==4; goto ringback; 

::  d_step {partner[2]!=2 && partner[2]!=4 && chnl[partner[2]]!=255;record[partner[2]] = 2;}  goto busy; 

::  d_step {partner[2]!=2 && partner[2]!=4 && chnl[partner[2]]==255;record[partner[2]] = 2;chnl[partner[2]] = ((2)+(0)*20);chnl[2] = ((partner[2])+(0)*20);}  goto oalert; 

fi;
busy: if
::  d_step {chnl[2] = 255;partner[2] = 255;dev = 1;}  goto idle; 

fi;
q_i: if
:: ((chnl[partner[2]])%20)==2; goto talert; 

::  d_step {((chnl[partner[2]])%20)!=2;partner[2] = 255;}  goto idle; 

fi;
talert: if
:: dev!=1 || chnl[2]==255; goto error_state; 

:: ((chnl[partner[2]])%20)==2; goto tpickup; 

:: ((chnl[partner[2]])%20)!=2; goto idle; 

fi;
unobtainable: if
::  d_step {chnl[2] = 255;partner[2] = 255;dev = 1;}  goto idle; 

fi;
oalert: if
:: ((chnl[2])%20)!=partner[2]; goto error_state; 

:: ((chnl[2])%20)==partner[2] && ((chnl[2])/20)==1; goto oconnected; 

:: ((chnl[2])%20)==partner[2] && ((chnl[2])/20)==0; goto oringout; 

fi;
oconnected: if
::  d_step {dev = 1;chnl[2] = 255;chnl[partner[2]] = 255;}  goto idle; 

fi;
oringout: if
::  d_step {dev = 1;chnl[2] = 255;partner[2] = ((((partner[2])%20))+(0)*20);}  goto idle; 

fi;
tpickup: if
::  d_step {((chnl[partner[2]])%20)==2 && ((chnl[partner[2]])/20)==0;dev = 0;chnl[partner[2]] = ((2)+(1)*20);chnl[2] = ((partner[2])+(1)*20);}  goto tconnected; 

::  d_step {chnl[partner[2]]==255 || ((chnl[partner[2]])%20)!=2;dev = 1;partner[2] = 255;chnl[2] = 255;}  goto idle; 

fi;
tconnected: if
::  d_step {((chnl[2])/20)==1 && dev==0;dev = 1;}  goto tconnected; 

::  d_step {((chnl[2])/20)==1 && dev==1;dev = 0;}  goto tconnected; 

::  d_step {((chnl[2])/20)==0;partner[2] = 255;chnl[2] = 255;}  goto idle; 

fi;
ringback: if
::  d_step {chnl[2] = 255;partner[2] = 255;dev = 1;}  goto idle; 

::  d_step {record[2]!=255;partner[2] = record[2];}  goto calling; 

fi;
error_state: 
 false; }

proctype User_3() { 
byte dev=1;
byte mbit=0;

idle: if
::  d_step {chnl[3]==255;dev = 0;chnl[3] = ((3)+(0)*20);}  goto dialing; 

::  d_step {chnl[3]!=255;partner[3] = ((chnl[3])%20);}  goto q_i; 

fi;
dialing: if
::  d_step {dev = 1;chnl[3] = 255;}  goto idle; 

:: partner[3] = 0; goto calling; 

:: partner[3] = 1; goto calling; 

:: partner[3] = 2; goto calling; 

:: partner[3] = 3; goto calling; 

:: partner[3] = 4; goto calling; 

fi;
calling: if
:: partner[3]==3; goto busy; 

:: partner[3]==4; goto unobtainable; 

:: partner[3]==4; goto ringback; 

::  d_step {partner[3]!=3 && partner[3]!=4 && chnl[partner[3]]!=255;record[partner[3]] = 3;}  goto busy; 

::  d_step {partner[3]!=3 && partner[3]!=4 && chnl[partner[3]]==255;record[partner[3]] = 3;chnl[partner[3]] = ((3)+(0)*20);chnl[3] = ((partner[3])+(0)*20);}  goto oalert; 

fi;
busy: if
::  d_step {chnl[3] = 255;partner[3] = 255;dev = 1;}  goto idle; 

fi;
q_i: if
:: ((chnl[partner[3]])%20)==3; goto talert; 

::  d_step {((chnl[partner[3]])%20)!=3;partner[3] = 255;}  goto idle; 

fi;
talert: if
:: dev!=1 || chnl[3]==255; goto error_state; 

:: ((chnl[partner[3]])%20)==3; goto tpickup; 

:: ((chnl[partner[3]])%20)!=3; goto idle; 

fi;
unobtainable: if
::  d_step {chnl[3] = 255;partner[3] = 255;dev = 1;}  goto idle; 

fi;
oalert: if
:: ((chnl[3])%20)!=partner[3]; goto error_state; 

:: ((chnl[3])%20)==partner[3] && ((chnl[3])/20)==1; goto oconnected; 

:: ((chnl[3])%20)==partner[3] && ((chnl[3])/20)==0; goto oringout; 

fi;
oconnected: if
::  d_step {dev = 1;chnl[3] = 255;chnl[partner[3]] = 255;}  goto idle; 

fi;
oringout: if
::  d_step {dev = 1;chnl[3] = 255;partner[3] = ((((partner[3])%20))+(0)*20);}  goto idle; 

fi;
tpickup: if
::  d_step {((chnl[partner[3]])%20)==3 && ((chnl[partner[3]])/20)==0;dev = 0;chnl[partner[3]] = ((3)+(1)*20);chnl[3] = ((partner[3])+(1)*20);}  goto tconnected; 

::  d_step {chnl[partner[3]]==255 || ((chnl[partner[3]])%20)!=3;dev = 1;partner[3] = 255;chnl[3] = 255;}  goto idle; 

fi;
tconnected: if
::  d_step {((chnl[3])/20)==1 && dev==0;dev = 1;}  goto tconnected; 

::  d_step {((chnl[3])/20)==1 && dev==1;dev = 0;}  goto tconnected; 

::  d_step {((chnl[3])/20)==0;partner[3] = 255;chnl[3] = 255;}  goto idle; 

fi;
ringback: if
::  d_step {chnl[3] = 255;partner[3] = 255;dev = 1;}  goto idle; 

::  d_step {record[3]!=255;partner[3] = record[3];}  goto calling; 

fi;
error_state: 
 false; }

