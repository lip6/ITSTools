byte leaders_num=0;

chan ch_0_in =[0] of {int};
chan ch_0_out =[0] of {int};
chan ch_1_in =[0] of {int};
chan ch_1_out =[0] of {int};
chan ch_2_in =[0] of {int};
chan ch_2_out =[0] of {int};

active proctype Node_0() { 
byte caw=0;
byte rec=0;
byte father=255;
byte lrec=0;
byte win=255;
byte j=0;
byte m=0;

start: if
::  atomic {j==0;ch_1_in!((0)+(0)*10+(0*10*10));j = j+1;}  goto start; 

:: j==1; goto wait; 

fi;
wait: if
:: ch_0_out?m; goto got_msg; 

::  d_step {lrec==1 && win==0;leaders_num = leaders_num+1;}  goto leader; 

:: lrec==1 && win!=0; goto lost; 

fi;
got_msg: if
:: ((m)/(10*10))==1; goto ldr_msg; 

:: ((m)/(10*10))==0; goto tok_msg; 

fi;
ldr_msg: if
::  d_step {lrec==0;j = 0;}  goto ldr_to_all; 

:: lrec>0; goto l2; 

fi;
ldr_to_all: if
::  atomic {j==0;ch_1_in!((0)+((((m)%(10*10))/10))*10+(1*10*10));j = j+1;}  goto ldr_to_all; 

:: j==1; goto l2; 

fi;
l2: if
::  d_step {lrec = lrec+1;win = (((m)%(10*10))/10);}  goto wait; 

fi;
tok_msg: if
::  d_step {(((m)%(10*10))/10)<caw;caw = (((m)%(10*10))/10);rec = 0;father = ((m)%10);j = 0;}  goto reinicialize; 

::  d_step {(((m)%(10*10))/10)==caw;rec = rec+1;}  goto same; 

:: (((m)%(10*10))/10)>caw; goto wait; 

fi;
reinicialize: if
::  atomic {j==0;ch_1_in!((0)+(caw)*10+(0*10*10));j = j+1;}  goto reinicialize; 

:: j==1; goto wait; 

fi;
same: if
:: rec<1; goto wait; 

:: rec==1; goto all; 

fi;
all: if
::  atomic {caw!=0 && father==0;ch_0_in!((0)+(caw)*10+(0*10*10));}  goto wait; 

::  atomic {caw!=0 && father==1;ch_1_in!((0)+(caw)*10+(0*10*10));}  goto wait; 

::  atomic {caw!=0 && father==2;ch_2_in!((0)+(caw)*10+(0*10*10));}  goto wait; 

::  d_step {caw==0;j = 0;}  goto become_leader; 

fi;
become_leader: if
::  atomic {j==0;ch_1_in!((0)+(0)*10+(1*10*10));j = j+1;}  goto become_leader; 

:: j==1; goto wait; 

fi;
leader: 
lost: 
 false; }

active proctype Node_1() { 
byte caw=1;
byte rec=0;
byte father=255;
byte lrec=0;
byte win=255;
byte j=0;
byte m=0;

start: if
::  atomic {j==0;ch_0_in!((1)+(1)*10+(0*10*10));j = j+1;}  goto start; 

::  atomic {j==1;ch_2_in!((1)+(1)*10+(0*10*10));j = j+1;}  goto start; 

:: j==2; goto wait; 

fi;
wait: if
:: ch_1_out?m; goto got_msg; 

::  d_step {lrec==2 && win==1;leaders_num = leaders_num+1;}  goto leader; 

:: lrec==2 && win!=1; goto lost; 

fi;
got_msg: if
:: ((m)/(10*10))==1; goto ldr_msg; 

:: ((m)/(10*10))==0; goto tok_msg; 

fi;
ldr_msg: if
::  d_step {lrec==0;j = 0;}  goto ldr_to_all; 

:: lrec>0; goto l2; 

fi;
ldr_to_all: if
::  atomic {j==0;ch_0_in!((1)+((((m)%(10*10))/10))*10+(1*10*10));j = j+1;}  goto ldr_to_all; 

::  atomic {j==1;ch_2_in!((1)+((((m)%(10*10))/10))*10+(1*10*10));j = j+1;}  goto ldr_to_all; 

:: j==2; goto l2; 

fi;
l2: if
::  d_step {lrec = lrec+1;win = (((m)%(10*10))/10);}  goto wait; 

fi;
tok_msg: if
::  d_step {(((m)%(10*10))/10)<caw;caw = (((m)%(10*10))/10);rec = 0;father = ((m)%10);j = 0;}  goto reinicialize; 

::  d_step {(((m)%(10*10))/10)==caw;rec = rec+1;}  goto same; 

:: (((m)%(10*10))/10)>caw; goto wait; 

fi;
reinicialize: if
::  atomic {j==0;ch_0_in!((1)+(caw)*10+(0*10*10));j = j+1;}  goto reinicialize; 

::  atomic {j==1;ch_2_in!((1)+(caw)*10+(0*10*10));j = j+1;}  goto reinicialize; 

:: j==2; goto wait; 

fi;
same: if
:: rec<2; goto wait; 

:: rec==2; goto all; 

fi;
all: if
::  atomic {caw!=1 && father==0;ch_0_in!((1)+(caw)*10+(0*10*10));}  goto wait; 

::  atomic {caw!=1 && father==1;ch_1_in!((1)+(caw)*10+(0*10*10));}  goto wait; 

::  atomic {caw!=1 && father==2;ch_2_in!((1)+(caw)*10+(0*10*10));}  goto wait; 

::  d_step {caw==1;j = 0;}  goto become_leader; 

fi;
become_leader: if
::  atomic {j==0;ch_0_in!((1)+(1)*10+(1*10*10));j = j+1;}  goto become_leader; 

::  atomic {j==1;ch_2_in!((1)+(1)*10+(1*10*10));j = j+1;}  goto become_leader; 

:: j==2; goto wait; 

fi;
leader: 
lost: 
 false; }

active proctype Node_2() { 
byte caw=2;
byte rec=0;
byte father=255;
byte lrec=0;
byte win=255;
byte j=0;
byte m=0;

start: if
::  atomic {j==0;ch_1_in!((2)+(2)*10+(0*10*10));j = j+1;}  goto start; 

:: j==1; goto wait; 

fi;
wait: if
:: ch_2_out?m; goto got_msg; 

::  d_step {lrec==1 && win==2;leaders_num = leaders_num+1;}  goto leader; 

:: lrec==1 && win!=2; goto lost; 

fi;
got_msg: if
:: ((m)/(10*10))==1; goto ldr_msg; 

:: ((m)/(10*10))==0; goto tok_msg; 

fi;
ldr_msg: if
::  d_step {lrec==0;j = 0;}  goto ldr_to_all; 

:: lrec>0; goto l2; 

fi;
ldr_to_all: if
::  atomic {j==0;ch_1_in!((2)+((((m)%(10*10))/10))*10+(1*10*10));j = j+1;}  goto ldr_to_all; 

:: j==1; goto l2; 

fi;
l2: if
::  d_step {lrec = lrec+1;win = (((m)%(10*10))/10);}  goto wait; 

fi;
tok_msg: if
::  d_step {(((m)%(10*10))/10)<caw;caw = (((m)%(10*10))/10);rec = 0;father = ((m)%10);j = 0;}  goto reinicialize; 

::  d_step {(((m)%(10*10))/10)==caw;rec = rec+1;}  goto same; 

:: (((m)%(10*10))/10)>caw; goto wait; 

fi;
reinicialize: if
::  atomic {j==0;ch_1_in!((2)+(caw)*10+(0*10*10));j = j+1;}  goto reinicialize; 

:: j==1; goto wait; 

fi;
same: if
:: rec<1; goto wait; 

:: rec==1; goto all; 

fi;
all: if
::  atomic {caw!=2 && father==0;ch_0_in!((2)+(caw)*10+(0*10*10));}  goto wait; 

::  atomic {caw!=2 && father==1;ch_1_in!((2)+(caw)*10+(0*10*10));}  goto wait; 

::  atomic {caw!=2 && father==2;ch_2_in!((2)+(caw)*10+(0*10*10));}  goto wait; 

::  d_step {caw==2;j = 0;}  goto become_leader; 

fi;
become_leader: if
::  atomic {j==0;ch_1_in!((2)+(2)*10+(1*10*10));j = j+1;}  goto become_leader; 

:: j==1; goto wait; 

fi;
leader: 
lost: 
 false; }

active proctype chnlnel_ch_0() { 
byte buf[3];
byte buf_act=0;

q: if
::  atomic { ! (buf_act==3);ch_0_in?buf[buf_act];buf_act = buf_act+1;}  goto q; 

::  atomic { ! (buf_act==0);ch_0_out!buf[0];buf[0] = buf[1];buf[1] = buf[2];buf[2] = 0;buf_act = buf_act-1;}  goto q; 

fi;
}

active proctype chnlnel_ch_1() { 
byte buf[3];
byte buf_act=0;

q: if
::  atomic { ! (buf_act==3);ch_1_in?buf[buf_act];buf_act = buf_act+1;}  goto q; 

::  atomic { ! (buf_act==0);ch_1_out!buf[0];buf[0] = buf[1];buf[1] = buf[2];buf[2] = 0;buf_act = buf_act-1;}  goto q; 

fi;
}

active proctype chnlnel_ch_2() { 
byte buf[3];
byte buf_act=0;

q: if
::  atomic { ! (buf_act==3);ch_2_in?buf[buf_act];buf_act = buf_act+1;}  goto q; 

::  atomic { ! (buf_act==0);ch_2_out!buf[0];buf[0] = buf[1];buf[1] = buf[2];buf[2] = 0;buf_act = buf_act-1;}  goto q; 

fi;
}

