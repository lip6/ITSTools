byte b[3];
byte readers=0;
byte sem=1;
byte chnlge_to=0;


init { 
 d_step { 
b[0] =0; b[1] =0; b[2] =2; }
atomic { 
run slave_1();
run slave_2();
run master_1();
run master_2();
run master_3();
} }

proctype slave_1() { 

idle: if
::  d_step {sem==1;sem = 0;}  goto q0; 

fi;
q0: if
::  d_step {b[0]==1;b[0] = 0;}  goto q1; 

::  d_step {b[1]==1;b[1] = 0;}  goto q1; 

::  d_step {b[2]==1;b[2] = 0;}  goto q1; 

:: b[0]!=1 && b[1]!=1 && b[2]!=1; goto q1; 

fi;
q1: if
::  d_step {b[0]==2;b[0] = 1;}  goto q2; 

::  d_step {b[1]==2;b[1] = 1;}  goto q2; 

::  d_step {b[2]==2;b[2] = 1;}  goto q2; 

:: b[0]!=2 && b[1]!=2 && b[2]!=2; goto error_state; 

fi;
q2: if
::  d_step {b[0]==0;b[0] = 2;}  goto q3; 

::  d_step {b[1]==0;b[1] = 2;}  goto q3; 

::  d_step {b[2]==0;b[2] = 2;}  goto q3; 

:: b[0]!=0 && b[1]!=0 && b[2]!=0; goto error_state; 

fi;
q3: if
:: sem = 1; goto idle; 

fi;
error_state: 
 false; }

proctype slave_2() { 

idle: if
::  d_step {sem==1;sem = 0;}  goto q0; 

fi;
q0: if
::  d_step {b[0]==1;b[0] = 0;}  goto q1; 

::  d_step {b[1]==1;b[1] = 0;}  goto q1; 

::  d_step {b[2]==1;b[2] = 0;}  goto q1; 

:: b[0]!=1 && b[1]!=1 && b[2]!=1; goto q1; 

fi;
q1: if
::  d_step {b[0]==2;b[0] = 1;}  goto q2; 

::  d_step {b[1]==2;b[1] = 1;}  goto q2; 

::  d_step {b[2]==2;b[2] = 1;}  goto q2; 

:: b[0]!=2 && b[1]!=2 && b[2]!=2; goto error_state; 

fi;
q2: if
::  d_step {b[0]==0;b[0] = 2;}  goto q3; 

::  d_step {b[1]==0;b[1] = 2;}  goto q3; 

::  d_step {b[2]==0;b[2] = 2;}  goto q3; 

:: b[0]!=0 && b[1]!=0 && b[2]!=0; goto error_state; 

fi;
q3: if
:: sem = 1; goto idle; 

fi;
error_state: 
 false; }

proctype master_1() { 

idle: if
::  d_step {sem==1;sem = 0;}  goto q0; 

fi;
q0: if
:: b[0]==3; goto master; 

:: b[1]==3; goto master; 

:: b[2]==3; goto master; 

:: b[0]!=3 && b[1]!=3 && b[2]!=3; goto no_master; 

fi;
no_master: if
::  d_step {b[0]==1;b[0] = 3;}  goto master; 

::  d_step {b[1]==1;b[1] = 3;}  goto master; 

::  d_step {b[2]==1;b[2] = 3;}  goto master; 

::  d_step {b[0]!=1 && b[1]!=1 && b[2]!=1;sem = 1;}  goto idle; 

fi;
master: if
::  d_step {readers = readers+1;sem = 1;}  goto reading; 

fi;
reading: if
::  d_step {sem==1;sem = 0;readers = readers-1;}  goto r0; 

fi;
r0: if
:: readers==0; goto no_readers; 

:: readers>0; goto r1; 

fi;
no_readers: if
::  d_step {b[0]==1 || b[0]==1 || b[0]==1;chnlge_to = 0;}  goto chnlge; 

::  d_step {b[0]!=1 && b[0]!=1 && b[0]!=1;chnlge_to = 1;}  goto chnlge; 

fi;
chnlge: if
::  d_step {b[0]==3;b[0] = chnlge_to;}  goto r1; 

::  d_step {b[0]==3;b[0] = chnlge_to;}  goto r1; 

::  d_step {b[0]==3;b[0] = chnlge_to;}  goto r1; 

fi;
r1: if
:: sem = 1; goto idle; 

fi;
}

proctype master_2() { 

idle: if
::  d_step {sem==1;sem = 0;}  goto q0; 

fi;
q0: if
:: b[0]==3; goto master; 

:: b[1]==3; goto master; 

:: b[2]==3; goto master; 

:: b[0]!=3 && b[1]!=3 && b[2]!=3; goto no_master; 

fi;
no_master: if
::  d_step {b[0]==1;b[0] = 3;}  goto master; 

::  d_step {b[1]==1;b[1] = 3;}  goto master; 

::  d_step {b[2]==1;b[2] = 3;}  goto master; 

::  d_step {b[0]!=1 && b[1]!=1 && b[2]!=1;sem = 1;}  goto idle; 

fi;
master: if
::  d_step {readers = readers+1;sem = 1;}  goto reading; 

fi;
reading: if
::  d_step {sem==1;sem = 0;readers = readers-1;}  goto r0; 

fi;
r0: if
:: readers==0; goto no_readers; 

:: readers>0; goto r1; 

fi;
no_readers: if
::  d_step {b[0]==1 || b[0]==1 || b[0]==1;chnlge_to = 0;}  goto chnlge; 

::  d_step {b[0]!=1 && b[0]!=1 && b[0]!=1;chnlge_to = 1;}  goto chnlge; 

fi;
chnlge: if
::  d_step {b[0]==3;b[0] = chnlge_to;}  goto r1; 

::  d_step {b[0]==3;b[0] = chnlge_to;}  goto r1; 

::  d_step {b[0]==3;b[0] = chnlge_to;}  goto r1; 

fi;
r1: if
:: sem = 1; goto idle; 

fi;
}

proctype master_3() { 

idle: if
::  d_step {sem==1;sem = 0;}  goto q0; 

fi;
q0: if
:: b[0]==3; goto master; 

:: b[1]==3; goto master; 

:: b[2]==3; goto master; 

:: b[0]!=3 && b[1]!=3 && b[2]!=3; goto no_master; 

fi;
no_master: if
::  d_step {b[0]==1;b[0] = 3;}  goto master; 

::  d_step {b[1]==1;b[1] = 3;}  goto master; 

::  d_step {b[2]==1;b[2] = 3;}  goto master; 

::  d_step {b[0]!=1 && b[1]!=1 && b[2]!=1;sem = 1;}  goto idle; 

fi;
master: if
::  d_step {readers = readers+1;sem = 1;}  goto reading; 

fi;
reading: if
::  d_step {sem==1;sem = 0;readers = readers-1;}  goto r0; 

fi;
r0: if
:: readers==0; goto no_readers; 

:: readers>0; goto r1; 

fi;
no_readers: if
::  d_step {b[0]==1 || b[0]==1 || b[0]==1;chnlge_to = 0;}  goto chnlge; 

::  d_step {b[0]!=1 && b[0]!=1 && b[0]!=1;chnlge_to = 1;}  goto chnlge; 

fi;
chnlge: if
::  d_step {b[0]==3;b[0] = chnlge_to;}  goto r1; 

::  d_step {b[0]==3;b[0] = chnlge_to;}  goto r1; 

::  d_step {b[0]==3;b[0] = chnlge_to;}  goto r1; 

fi;
r1: if
:: sem = 1; goto idle; 

fi;
}

