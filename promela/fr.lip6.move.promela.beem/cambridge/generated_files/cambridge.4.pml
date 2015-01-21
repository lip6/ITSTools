
chan toK =[0] of {int};
chan fromK =[0] of {int};
chan toL =[0] of {int};
chan fromL =[0] of {int};
chan sRESET1 =[0] of {int};
chan sRESET2 =[0] of {int};
chan rRESET1 =[0] of {int};
chan rRESET2 =[0] of {int};
chan sRDY1 =[0] of {int};
chan sRDY2 =[0] of {int};
chan sNOTRDY1 =[0] of {int};
chan sNOTRDY2 =[0] of {int};
chan rDATA1 =[0] of {int};
chan rDATA2 =[0] of {int};
chan rNODATA1 =[0] of {int};
chan rNODATA2 =[0] of {int};

active proctype Sender() { 
int n=-1;
int m=0;

idle: if
:: sRESET1?0; goto ack_reset; 

:: sNOTRDY1?m; goto q_i; 

:: sRDY1?m; goto q_a; 

:: rRESET1!0; goto reset; 

fi;
ack_reset: if
::  atomic {rRESET1!0;n = -1;}  goto idle; 

fi;
reset: if
:: sNOTRDY1?m; goto reset; 

:: sRDY1?m; goto reset; 

::  atomic {sRESET1?0;n = -1;}  goto idle; 

fi;
advance: if
:: rNODATA1!n; goto N; 

:: rDATA1!n; goto E; 

fi;
N: if
:: rRESET1!0; goto reset; 

:: sRESET1?0; goto ack_reset; 

:: rDATA1!n; goto E; 

:: sRDY1?m; goto q_n; 

fi;
E: if
:: rRESET1!0; goto reset; 

:: sRESET1?0; goto ack_reset; 

:: sNOTRDY1?m; goto q_e; 

:: sRDY1?m; goto q_e; 

fi;
q_i: if
:: m==(n+1)%4; goto idle; 

:: m!=(n+1)%4; goto q_error; 

fi;
q_e: if
::  atomic {m==n;rDATA1!n;}  goto E; 

::  d_step {m==(n+1)%4;n = (n+1)%4;}  goto advance; 

fi;
q_n: if
::  atomic {m==n;rNODATA1!n;}  goto N; 

:: m!=n; goto q_error; 

fi;
q_a: if
::  d_step {m==(n+1)%4;n = (n+1)%4;}  goto advance; 

:: m!=(n+1)%4; goto q_error; 

fi;
q_error: 
 false; }

active proctype Receiver() { 
int n=0;
int m=0;

idle: if
:: rRESET2?0; goto ack_reset; 

:: rNODATA2?m; goto q_i; 

:: rDATA2?m; goto q_a; 

:: sRESET2!0; goto reset; 

:: sRDY2!n; goto E; 

fi;
ack_reset: if
::  atomic {sRESET2!0;n = 0;}  goto idle; 

fi;
reset: if
:: rDATA2?m; goto reset; 

:: rNODATA2?m; goto reset; 

::  atomic {rRESET2?0;n = 0;}  goto idle; 

fi;
advance: if
:: sNOTRDY2!n; goto N; 

:: sRDY2!n; goto E; 

fi;
N: if
:: sRESET2!0; goto reset; 

:: rRESET2?0; goto ack_reset; 

:: sRDY2!n; goto E; 

:: rDATA2?m; goto q_n; 

fi;
E: if
:: sRESET2!0; goto reset; 

:: rRESET2?0; goto ack_reset; 

:: sRDY2!n; goto E; 

:: rDATA2?m; goto q_e; 

:: rNODATA2?m; goto q_e2; 

fi;
q_i: if
:: m==n; goto idle; 

:: m!=n; goto q_error; 

fi;
q_e: if
::  atomic {(m+1)%4==n;sRDY2!n;}  goto E; 

:: m==n; goto advance; 

fi;
q_n: if
::  atomic {(m+1)%4==n;sNOTRDY2!n;}  goto N; 

:: (m+1)%4!=n; goto N; 

fi;
q_a: if
::  d_step {m==n;n = (n+1)%4;}  goto advance; 

:: m!=n; goto q_error; 

fi;
q_e2: if
:: m==n; goto idle; 

:: m!=n; goto q_error; 

fi;
q_error: 
 false; }

active proctype StoR() { 
byte buf[5];
byte buf_act=0;
byte n=0;

q: if
::  atomic { ! (buf_act==5);rRESET1?0;buf[buf_act] = 0;buf_act = buf_act+1;}  goto q; 

::  atomic { ! (buf_act==5);rDATA1?n;buf[buf_act] = 3+8*n;buf_act = buf_act+1;}  goto q; 

::  atomic { ! (buf_act==5);rNODATA1?n;buf[buf_act] = 4+8*n;buf_act = buf_act+1;}  goto q; 

::  atomic { ! (buf_act==0) && buf[0]==0;rRESET2!0;buf[0] = buf[1];buf[1] = buf[2];buf[2] = buf[3];buf[3] = buf[4];buf[4] = 0;buf_act = buf_act-1;}  goto q; 

::  atomic { ! (buf_act==0) && buf[0]%8==3;rDATA2!(buf[0]/8);buf[0] = buf[1];buf[1] = buf[2];buf[2] = buf[3];buf[3] = buf[4];buf[4] = 0;buf_act = buf_act-1;}  goto q; 

::  atomic { ! (buf_act==0) && buf[0]%8==4;rNODATA2!(buf[0]/8);buf[0] = buf[1];buf[1] = buf[2];buf[2] = buf[3];buf[3] = buf[4];buf[4] = 0;buf_act = buf_act-1;}  goto q; 

fi;
}

active proctype RtoS() { 
byte buf[5];
byte buf_act=0;
byte n=0;

q: if
::  atomic { ! (buf_act==5);sRESET2?0;buf[buf_act] = 0;buf_act = buf_act+1;}  goto q; 

::  atomic { ! (buf_act==5);sRDY2?n;buf[buf_act] = 1+8*n;buf_act = buf_act+1;}  goto q; 

::  atomic { ! (buf_act==5);sNOTRDY2?n;buf[buf_act] = 2+8*n;buf_act = buf_act+1;}  goto q; 

::  atomic { ! (buf_act==0) && buf[0]==0;sRESET1!0;buf[0] = buf[1];buf[1] = buf[2];buf[2] = buf[3];buf[3] = buf[4];buf[4] = 0;buf_act = buf_act-1;}  goto q; 

::  atomic { ! (buf_act==0) && buf[0]%8==1;sRDY1!(buf[0]/8);buf[0] = buf[1];buf[1] = buf[2];buf[2] = buf[3];buf[3] = buf[4];buf[4] = 0;buf_act = buf_act-1;}  goto q; 

::  atomic { ! (buf_act==0) && buf[0]%8==2;sNOTRDY1!(buf[0]/8);buf[0] = buf[1];buf[1] = buf[2];buf[2] = buf[3];buf[3] = buf[4];buf[4] = 0;buf_act = buf_act-1;}  goto q; 

fi;
}

