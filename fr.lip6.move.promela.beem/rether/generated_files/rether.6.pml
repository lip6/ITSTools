byte in_RT[11];
byte RT_count=0;

chan reserve =[0] of {int};
chan release =[0] of {int};
chan grant =[0] of {int};
chan no_grant =[0] of {int};
chan done =[0] of {int};
chan visit_0 =[0] of {int};
chan visit_1 =[0] of {int};
chan visit_2 =[0] of {int};
chan visit_3 =[0] of {int};
chan visit_4 =[0] of {int};
chan visit_5 =[0] of {int};
chan visit_6 =[0] of {int};
chan visit_7 =[0] of {int};
chan visit_8 =[0] of {int};
chan visit_9 =[0] of {int};
chan visit_10 =[0] of {int};

active proctype Bandwidth() { 
byte i=0;

idle: if
:: reserve?i; goto res; 

:: release?i; goto rel; 

fi;
rel: if
::  d_step {in_RT[i]==1;in_RT[i] = 0;RT_count = RT_count-1;}  goto idle; 

:: in_RT[i]==0; goto error_st; 

fi;
res: if
::  atomic {RT_count>=2;no_grant!0;}  goto idle; 

::  atomic {RT_count<2;grant!0;RT_count = RT_count+1;in_RT[i] = 1;}  goto idle; 

fi;
error_st: 
 false; }

active proctype Node_0() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_0?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!0;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!0;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_1() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_1?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!1;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!1;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_2() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_2?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!2;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!2;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_3() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_3?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!3;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!3;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_4() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_4?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!4;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!4;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_5() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_5?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!5;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!5;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_6() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_6?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!6;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!6;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_7() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_7?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!7;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!7;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_8() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_8?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!8;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!8;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_9() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_9?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!9;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!9;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Node_10() { 
byte rt=0;
byte granted=0;

idle: if
:: visit_10?rt; goto start; 

fi;
start: if
:: rt==1; goto RT_action; 

:: rt==0; goto NRT_action; 

fi;
RT_action: if
:: granted==0; goto error_st; 

::  goto finish; 

::  atomic {release!10;granted = 0;}  goto finish; 

fi;
NRT_action: if
::  goto finish; 

::  atomic {granted==0;reserve!10;}  goto want_RT; 

fi;
want_RT: if
:: grant?0; goto reserved; 

:: no_grant?0; goto finish; 

fi;
reserved: if
:: granted = 1; goto finish; 

fi;
finish: if
:: done!0; goto idle; 

fi;
error_st: 
 false; }

active proctype Token() { 
byte i=0;
byte NRT_count=5;
byte next=0;

start: if
:: i = 0; goto RT_phase; 

fi;
RT_phase: if
::  d_step {i<11 && in_RT[i]==0;i = i+1;}  goto RT_phase; 

::  atomic {i==0 && in_RT[i]==1;visit_0!1;}  goto RT_wait; 

::  atomic {i==1 && in_RT[i]==1;visit_1!1;}  goto RT_wait; 

::  atomic {i==2 && in_RT[i]==1;visit_2!1;}  goto RT_wait; 

::  atomic {i==3 && in_RT[i]==1;visit_3!1;}  goto RT_wait; 

::  atomic {i==4 && in_RT[i]==1;visit_4!1;}  goto RT_wait; 

::  atomic {i==5 && in_RT[i]==1;visit_5!1;}  goto RT_wait; 

::  atomic {i==6 && in_RT[i]==1;visit_6!1;}  goto RT_wait; 

::  atomic {i==7 && in_RT[i]==1;visit_7!1;}  goto RT_wait; 

::  atomic {i==8 && in_RT[i]==1;visit_8!1;}  goto RT_wait; 

::  atomic {i==9 && in_RT[i]==1;visit_9!1;}  goto RT_wait; 

::  atomic {i==10 && in_RT[i]==1;visit_10!1;}  goto RT_wait; 

:: i==11; goto NRT_phase; 

fi;
RT_wait: if
::  atomic {done?0;i = i+1;}  goto RT_phase; 

fi;
NRT_phase: if
::  atomic {NRT_count>0 && next==0;visit_0!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==1;visit_1!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==2;visit_2!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==3;visit_3!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==4;visit_4!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==5;visit_5!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==6;visit_6!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==7;visit_7!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==8;visit_8!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==9;visit_9!0;}  goto NRT_wait; 

::  atomic {NRT_count>0 && next==10;visit_10!0;}  goto NRT_wait; 

:: NRT_count==0; goto cycle_end; 

fi;
NRT_wait: if
::  atomic {done?0;next = (next+1)%11;NRT_count = NRT_count-1;}  goto NRT_phase; 

fi;
cycle_end: if
:: NRT_count = 5-RT_count; goto start; 

fi;
}

