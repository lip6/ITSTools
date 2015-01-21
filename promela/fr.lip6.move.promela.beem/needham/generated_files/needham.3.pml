
chan net_N_P_P =[0] of {int};
chan net_N_N_P =[0] of {int};
chan net_N_P =[0] of {int};

active proctype initiator_0() { 
int m=0;
int party_nonce=0;

start: if
:: net_N_P_P!((1)+(7)*15+(10*15*15)); goto wait_resp; 

fi;
wait_resp: if
:: net_N_N_P?m; goto got_resp; 

fi;
got_resp: if
::  d_step {((m)%15)==1 && ((m)/(15*15))==7;party_nonce = (((m)%(15*15))/15);}  goto commited; 

::  ! (((m)%15)==1 && ((m)/(15*15))==7); goto corrupted; 

fi;
commited: if
:: net_N_P!((party_nonce)+(10)*15+(0*15*15)); goto finished; 

fi;
finished: 
corrupted: 
 false; }

active proctype initiator_1() { 
int m=0;
int party_nonce=0;

start: if
:: net_N_P_P!((2)+(8)*15+(11*15*15)); goto wait_resp; 

fi;
wait_resp: if
:: net_N_N_P?m; goto got_resp; 

fi;
got_resp: if
::  d_step {((m)%15)==2 && ((m)/(15*15))==8;party_nonce = (((m)%(15*15))/15);}  goto commited; 

::  ! (((m)%15)==2 && ((m)/(15*15))==8); goto corrupted; 

fi;
commited: if
:: net_N_P!((party_nonce)+(11)*15+(0*15*15)); goto finished; 

fi;
finished: 
corrupted: 
 false; }

active proctype initiator_2() { 
int m=0;
int party_nonce=0;

start: if
:: net_N_P_P!((3)+(9)*15+(12*15*15)); goto wait_resp; 

fi;
wait_resp: if
:: net_N_N_P?m; goto got_resp; 

fi;
got_resp: if
::  d_step {((m)%15)==3 && ((m)/(15*15))==9;party_nonce = (((m)%(15*15))/15);}  goto commited; 

::  ! (((m)%15)==3 && ((m)/(15*15))==9); goto corrupted; 

fi;
commited: if
:: net_N_P!((party_nonce)+(12)*15+(0*15*15)); goto finished; 

fi;
finished: 
corrupted: 
 false; }

active proctype responder_0() { 
int m=0;
int party=0;
int party_nonce=0;

start: if
:: net_N_P_P?m; goto got_msg; 

fi;
got_msg: if
::  d_step {((m)/(15*15))==10;party = (((m)%(15*15))/15);party_nonce = ((m)%15);}  goto send_reply; 

::  ! (((m)/(15*15))==10); goto corrupted; 

fi;
send_reply: if
:: net_N_N_P!((party_nonce)+(4)*15+(party*15*15)); goto wait_resp; 

fi;
wait_resp: if
:: net_N_P?m; goto got_resp; 

fi;
got_resp: if
::  ! (((m)%15)==4 && (((m)%(15*15))/15)==10); goto corrupted; 

:: ((m)%15)==4 && (((m)%(15*15))/15)==10; goto finished; 

fi;
finished: 
corrupted: 
 false; }

active proctype responder_1() { 
int m=0;
int party=0;
int party_nonce=0;

start: if
:: net_N_P_P?m; goto got_msg; 

fi;
got_msg: if
::  d_step {((m)/(15*15))==11;party = (((m)%(15*15))/15);party_nonce = ((m)%15);}  goto send_reply; 

::  ! (((m)/(15*15))==11); goto corrupted; 

fi;
send_reply: if
:: net_N_N_P!((party_nonce)+(5)*15+(party*15*15)); goto wait_resp; 

fi;
wait_resp: if
:: net_N_P?m; goto got_resp; 

fi;
got_resp: if
::  ! (((m)%15)==5 && (((m)%(15*15))/15)==11); goto corrupted; 

:: ((m)%15)==5 && (((m)%(15*15))/15)==11; goto finished; 

fi;
finished: 
corrupted: 
 false; }

active proctype intruder() { 
byte kNa=0;
byte kNb=0;
byte k_Na_Nb__A=0;
byte k_Na_A__B=0;
byte k_Nb__B=0;
int m=0;

q: if
:: net_N_N_P?m; goto got3; 

:: net_N_P_P?m; goto got3; 

:: net_N_P?m; goto got2; 

:: net_N_P_P!((13)+(7)*15+(10*15*15)); goto q; 

:: net_N_P_P!((13)+(10)*15+(10*15*15)); goto q; 

:: net_N_P_P!((13)+(3)*15+(10*15*15)); goto q; 

:: net_N_P_P!((7)+(7)*15+(10*15*15)); goto q; 

:: net_N_P_P!((7)+(10)*15+(10*15*15)); goto q; 

:: net_N_P_P!((7)+(3)*15+(10*15*15)); goto q; 

:: net_N_P_P!((10)+(7)*15+(10*15*15)); goto q; 

:: net_N_P_P!((10)+(10)*15+(10*15*15)); goto q; 

:: net_N_P_P!((10)+(3)*15+(10*15*15)); goto q; 

:: net_N_P_P!((3)+(7)*15+(10*15*15)); goto q; 

:: net_N_P_P!((3)+(10)*15+(10*15*15)); goto q; 

:: net_N_P_P!((3)+(3)*15+(10*15*15)); goto q; 

::  atomic {kNa==1;net_N_N_P!((1)+(1)*15+(7*15*15));}  goto q; 

::  atomic {kNa==1 && kNb==1 || k_Na_Nb__A==1;net_N_N_P!((1)+(4)*15+(7*15*15));}  goto q; 

::  atomic {kNa==1;net_N_N_P!((1)+(13)*15+(7*15*15));}  goto q; 

::  atomic {kNa==1;net_N_P_P!((1)+(7)*15+(7*15*15));}  goto q; 

::  atomic {kNa==1;net_N_P_P!((1)+(10)*15+(7*15*15));}  goto q; 

::  atomic {kNa==1;net_N_P_P!((1)+(3)*15+(7*15*15));}  goto q; 

::  atomic {kNa==1 || k_Na_A__B==1;net_N_P_P!((1)+(7)*15+(10*15*15));}  goto q; 

::  atomic {kNa==1;net_N_P_P!((1)+(10)*15+(10*15*15));}  goto q; 

::  atomic {kNa==1;net_N_P_P!((1)+(3)*15+(10*15*15));}  goto q; 

::  atomic {kNb==1;net_N_P_P!((4)+(7)*15+(10*15*15));}  goto q; 

::  atomic {kNb==1;net_N_P_P!((4)+(10)*15+(10*15*15));}  goto q; 

::  atomic {kNb==1;net_N_P_P!((4)+(3)*15+(10*15*15));}  goto q; 

::  atomic {kNb==1 || k_Nb__B==1;net_N_P!((4)+(10)*15+(0*15*15));}  goto q; 

fi;
got3: if
::  goto q; 

:: ((m)/(15*15))==3; goto c1; 

:: ((m)/(15*15))!=3; goto d1; 

fi;
c1: if
::  d_step {((m)%15)==1;kNa = 1;}  goto c2; 

::  d_step {((m)%15)==4;kNb = 1;}  goto c2; 

:: ((m)%15)!=1 && ((m)%15)!=4; goto c2; 

fi;
c2: if
::  d_step {((m)%15)==4 && (((m)%(15*15))/15)==10;k_Nb__B = 1;}  goto q; 

::  ! (((m)%15)==4 && (((m)%(15*15))/15)==10); goto q; 

fi;
d1: if
::  d_step {((m)%15)==1 && (((m)%(15*15))/15)==7 && ((m)/(15*15))==10;k_Na_A__B = 1;}  goto q; 

::  d_step {((m)%15)==1 && (((m)%(15*15))/15)==4 && ((m)/(15*15))==7;k_Na_Nb__A = 1;}  goto q; 

::  goto q; 

fi;
got2: if
:: (((m)%(15*15))/15)==3; goto e1; 

:: (((m)%(15*15))/15)!=3; goto f1; 

fi;
e1: if
::  d_step {((m)%15)==1;kNa = 1;}  goto q; 

::  d_step {((m)%15)==4;kNb = 1;}  goto q; 

:: ((m)%15)!=1 && ((m)%15)!=4; goto q; 

fi;
f1: if
::  d_step {((m)%15)==4 && (((m)%(15*15))/15)==10;k_Nb__B = 1;}  goto q; 

::  ! (((m)%15)==4 && (((m)%(15*15))/15)==10); goto q; 

fi;
}

