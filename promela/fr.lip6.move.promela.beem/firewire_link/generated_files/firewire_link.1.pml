
chan LDreq_0 =[0] of {int};
chan LDres_0 =[0] of {int};
chan LDcon_0 =[0] of {int};
chan LDind_0 =[0] of {int};
chan TX_0 =[0] of {int};
chan PDreq_0 =[0] of {int};
chan PDind_0 =[0] of {int};
chan PAreq_0 =[0] of {int};
chan PAcon_0 =[0] of {int};
chan PCind_0 =[0] of {int};
chan TDreq_0 =[0] of {int};
chan LDreq_1 =[0] of {int};
chan LDres_1 =[0] of {int};
chan LDcon_1 =[0] of {int};
chan LDind_1 =[0] of {int};
chan TX_1 =[0] of {int};
chan PDreq_1 =[0] of {int};
chan PDind_1 =[0] of {int};
chan PAreq_1 =[0] of {int};
chan PAcon_1 =[0] of {int};
chan PCind_1 =[0] of {int};
chan TDreq_1 =[0] of {int};

active proctype Transreq_0() { 
byte m=0;

idle: if
:: LDind_0?m; goto got_msg; 

fi;
got_msg: if
::  atomic {m==101;LDres_0!100;}  goto idle; 

:: TX_0?0; goto concatenated_tr; 

::  goto split_tr; 

fi;
concatenated_tr: if
:: LDres_0!102; goto idle; 

fi;
split_tr: if
:: LDres_0!107; goto idle; 

fi;
}

active proctype Transres_0() { 
byte dest=0;
byte m=0;

idle: if
:: TDreq_0?dest; goto got_req; 

fi;
got_req: if
:: TX_0!0; goto q; 

::  goto q; 

fi;
q: if
:: LDreq_0!dest; goto wait_ack; 

fi;
wait_ack: if
:: LDcon_0?m; goto wait_q; 

fi;
wait_q: if
:: m==124 && dest==2; goto idle; 

:: m==105 && dest<2; goto idle; 

:: m==106 && dest<2; goto idle; 

fi;
}

active proctype Link_0() { 
int buf=-1;
byte dest=0;
byte m=0;
byte m2=0;
byte data=0;

link0: if
:: LDreq_0?buf; goto link0r; 

:: PAcon_0?m; goto link0a; 

:: PDind_0?m; goto link0i; 

fi;
link0r: if
:: PAreq_0!108; goto link0; 

fi;
link0a: if
:: m==111; goto link0; 

::  d_step {m==110;dest = buf;}  goto link2req; 

fi;
link0i: if
:: m!=119; goto link0; 

:: m==119; goto link4; 

fi;
link2req: if
:: PCind_0?0; goto q1; 

fi;
q1: if
:: PDreq_0!119; goto s1; 

fi;
s1: if
:: PCind_0?0; goto q2; 

fi;
q2: if
:: PDreq_0!118; goto s2; 

fi;
s2: if
:: PCind_0?0; goto q3; 

fi;
q3: if
:: PDreq_0!dest; goto s3; 

fi;
s3: if
:: PCind_0?0; goto q4; 

fi;
q4: if
:: PDreq_0!114; goto s4; 

fi;
s4: if
:: PCind_0?0; goto q5; 

fi;
q5: if
:: PDreq_0!116; goto s5; 

fi;
s5: if
:: PCind_0?0; goto q6; 

fi;
q6: if
:: PDreq_0!120; goto s6; 

fi;
s6: if
::  atomic {dest==2;LDcon_0!124;buf = -1;}  goto link0; 

:: dest<2; goto link3; 

fi;
link3: if
:: PDind_0?m; goto link3q; 

fi;
link3q: if
:: m==121; goto link3; 

::  atomic {m==122;LDcon_0!106;}  goto link0; 

:: m==119; goto link3A; 

::  atomic {m!=122 && m!=121 && m!=119;LDcon_0!106;dest = 2;}  goto linkWSA; 

fi;
link3A: if
:: PDind_0?m; goto link3Aq; 

fi;
link3Aq: if
::  atomic {m==119 || m==120 || m==122;LDcon_0!106;dest = 2;}  goto linkWSA; 

::  ! (m==119 || m==120 || m==122); goto link3RE; 

fi;
link3RE: if
:: PDind_0?m2; goto link3REq1; 

fi;
link3REq1: if
::  atomic {m==112 && (m2==121 || m2==120);LDcon_0!105;}  goto link3REq2; 

::  atomic { ! (m==112 && (m2==121 || m2==120));LDcon_0!106;}  goto link3REq2; 

fi;
link3REq2: if
:: m2==122; goto link0; 

::  d_step {m2!=122;dest = 2;}  goto linkWSA; 

fi;
linkWSA: if
:: PDind_0?m; goto linkWSAq; 

::  atomic {dest==0;PAcon_0?m;}  goto linkWSAa1; 

fi;
linkWSAq: if
:: m!=122; goto linkWSA; 

:: m==122; goto link0; 

fi;
linkWSAa1: if
::  atomic {m==110;PCind_0?0;}  goto linkWSAa2; 

fi;
linkWSAa2: if
:: PDreq_0!120; goto link0; 

fi;
link4: if
:: PDind_0?m; goto link4q; 

fi;
link4q: if
:: m==122; goto link0; 

::  d_step {m==119 || m==120 || m==122;dest = 2;}  goto linkWSA; 

:: m!=122 &&  ! (m==119 || m==120 || m==122); goto link4DH; 

fi;
link4DH: if
:: PDind_0?dest; goto link4DHq; 

fi;
link4DHq: if
:: dest==122; goto link0; 

::  atomic {dest==0;PAreq_0!109;}  goto link4RH; 

:: dest==2; goto link4RH; 

::  d_step {dest!=122 && dest!=0 && dest!=2;dest = 2;}  goto linkWSA; 

fi;
link4RH: if
:: PDind_0?m; goto link4RHq; 

fi;
link4RHq: if
:: m==114; goto link4RD; 

:: m!=114; goto linkWSA; 

fi;
link4RD: if
:: PDind_0?data; goto link4RDq; 

fi;
link4RDq: if
:: data==116 || data==117; goto link4RE; 

::  ! (data==116 || data==117); goto linkWSA; 

fi;
link4RE: if
:: PDind_0?m; goto link4REq; 

fi;
link4REq: if
:: (m==120 || m==121) && dest==0; goto link4DRec; 

:: (m==120 || m==121) && dest==2; goto link4BRec; 

::  ! (m==120 || m==121); goto linkWSA; 

fi;
link4DRec: if
::  atomic {data==116;LDind_0!103;}  goto link4DRecq; 

::  atomic {data==117;LDind_0!104;}  goto link4DRecq; 

fi;
link4BRec: if
::  atomic {data==116;LDind_0!101;}  goto link0; 

:: data!=116; goto link0; 

fi;
link4DRecq: if
:: PAcon_0?m; goto link4DRecq2; 

fi;
link4DRecq2: if
:: m==110; goto link5; 

fi;
link5: if
:: PCind_0?0; goto p1; 

:: LDres_0?m; goto link6; 

fi;
p1: if
:: PDreq_0!121; goto link5; 

fi;
p2: if
:: PDreq_0!119; goto p3; 

fi;
link6: if
:: PCind_0?0; goto p2; 

fi;
p3: if
:: PCind_0?0; goto p4; 

fi;
p4: if
:: PDreq_0!112; goto p5; 

fi;
p5: if
::  atomic {m==107;PDreq_0!120;}  goto link0; 

::  atomic {m!=107;PDreq_0!121;}  goto link7; 

fi;
p6: if
:: PDreq_0!121; goto link7; 

fi;
link7: if
:: PCind_0?0; goto p6; 

:: LDreq_0?dest; goto link2req; 

fi;
}

active proctype Transreq_1() { 
byte m=0;

idle: if
:: LDind_1?m; goto got_msg; 

fi;
got_msg: if
::  atomic {m==101;LDres_1!100;}  goto idle; 

:: TX_1?0; goto concatenated_tr; 

::  goto split_tr; 

fi;
concatenated_tr: if
:: LDres_1!102; goto idle; 

fi;
split_tr: if
:: LDres_1!107; goto idle; 

fi;
}

active proctype Transres_1() { 
byte dest=0;
byte m=0;

idle: if
:: TDreq_1?dest; goto got_req; 

fi;
got_req: if
:: TX_1!0; goto q; 

::  goto q; 

fi;
q: if
:: LDreq_1!dest; goto wait_ack; 

fi;
wait_ack: if
:: LDcon_1?m; goto wait_q; 

fi;
wait_q: if
:: m==124 && dest==2; goto idle; 

:: m==105 && dest<2; goto idle; 

:: m==106 && dest<2; goto idle; 

fi;
}

active proctype Link_1() { 
int buf=-1;
byte dest=0;
byte m=0;
byte m2=0;
byte data=0;

link0: if
:: LDreq_1?buf; goto link0r; 

:: PAcon_1?m; goto link0a; 

:: PDind_1?m; goto link0i; 

fi;
link0r: if
:: PAreq_1!108; goto link0; 

fi;
link0a: if
:: m==111; goto link0; 

::  d_step {m==110;dest = buf;}  goto link2req; 

fi;
link0i: if
:: m!=119; goto link0; 

:: m==119; goto link4; 

fi;
link2req: if
:: PCind_1?0; goto q1; 

fi;
q1: if
:: PDreq_1!119; goto s1; 

fi;
s1: if
:: PCind_1?0; goto q2; 

fi;
q2: if
:: PDreq_1!118; goto s2; 

fi;
s2: if
:: PCind_1?0; goto q3; 

fi;
q3: if
:: PDreq_1!dest; goto s3; 

fi;
s3: if
:: PCind_1?0; goto q4; 

fi;
q4: if
:: PDreq_1!114; goto s4; 

fi;
s4: if
:: PCind_1?0; goto q5; 

fi;
q5: if
:: PDreq_1!116; goto s5; 

fi;
s5: if
:: PCind_1?0; goto q6; 

fi;
q6: if
:: PDreq_1!120; goto s6; 

fi;
s6: if
::  atomic {dest==2;LDcon_1!124;buf = -1;}  goto link0; 

:: dest<2; goto link3; 

fi;
link3: if
:: PDind_1?m; goto link3q; 

fi;
link3q: if
:: m==121; goto link3; 

::  atomic {m==122;LDcon_1!106;}  goto link0; 

:: m==119; goto link3A; 

::  atomic {m!=122 && m!=121 && m!=119;LDcon_1!106;dest = 2;}  goto linkWSA; 

fi;
link3A: if
:: PDind_1?m; goto link3Aq; 

fi;
link3Aq: if
::  atomic {m==119 || m==120 || m==122;LDcon_1!106;dest = 2;}  goto linkWSA; 

::  ! (m==119 || m==120 || m==122); goto link3RE; 

fi;
link3RE: if
:: PDind_1?m2; goto link3REq1; 

fi;
link3REq1: if
::  atomic {m==112 && (m2==121 || m2==120);LDcon_1!105;}  goto link3REq2; 

::  atomic { ! (m==112 && (m2==121 || m2==120));LDcon_1!106;}  goto link3REq2; 

fi;
link3REq2: if
:: m2==122; goto link0; 

::  d_step {m2!=122;dest = 2;}  goto linkWSA; 

fi;
linkWSA: if
:: PDind_1?m; goto linkWSAq; 

::  atomic {dest==1;PAcon_1?m;}  goto linkWSAa1; 

fi;
linkWSAq: if
:: m!=122; goto linkWSA; 

:: m==122; goto link0; 

fi;
linkWSAa1: if
::  atomic {m==110;PCind_1?0;}  goto linkWSAa2; 

fi;
linkWSAa2: if
:: PDreq_1!120; goto link0; 

fi;
link4: if
:: PDind_1?m; goto link4q; 

fi;
link4q: if
:: m==122; goto link0; 

::  d_step {m==119 || m==120 || m==122;dest = 2;}  goto linkWSA; 

:: m!=122 &&  ! (m==119 || m==120 || m==122); goto link4DH; 

fi;
link4DH: if
:: PDind_1?dest; goto link4DHq; 

fi;
link4DHq: if
:: dest==122; goto link0; 

::  atomic {dest==1;PAreq_1!109;}  goto link4RH; 

:: dest==2; goto link4RH; 

::  d_step {dest!=122 && dest!=1 && dest!=2;dest = 2;}  goto linkWSA; 

fi;
link4RH: if
:: PDind_1?m; goto link4RHq; 

fi;
link4RHq: if
:: m==114; goto link4RD; 

:: m!=114; goto linkWSA; 

fi;
link4RD: if
:: PDind_1?data; goto link4RDq; 

fi;
link4RDq: if
:: data==116 || data==117; goto link4RE; 

::  ! (data==116 || data==117); goto linkWSA; 

fi;
link4RE: if
:: PDind_1?m; goto link4REq; 

fi;
link4REq: if
:: (m==120 || m==121) && dest==1; goto link4DRec; 

:: (m==120 || m==121) && dest==2; goto link4BRec; 

::  ! (m==120 || m==121); goto linkWSA; 

fi;
link4DRec: if
::  atomic {data==116;LDind_1!103;}  goto link4DRecq; 

::  atomic {data==117;LDind_1!104;}  goto link4DRecq; 

fi;
link4BRec: if
::  atomic {data==116;LDind_1!101;}  goto link0; 

:: data!=116; goto link0; 

fi;
link4DRecq: if
:: PAcon_1?m; goto link4DRecq2; 

fi;
link4DRecq2: if
:: m==110; goto link5; 

fi;
link5: if
:: PCind_1?0; goto p1; 

:: LDres_1?m; goto link6; 

fi;
p1: if
:: PDreq_1!121; goto link5; 

fi;
p2: if
:: PDreq_1!119; goto p3; 

fi;
link6: if
:: PCind_1?0; goto p2; 

fi;
p3: if
:: PCind_1?0; goto p4; 

fi;
p4: if
:: PDreq_1!112; goto p5; 

fi;
p5: if
::  atomic {m==107;PDreq_1!120;}  goto link0; 

::  atomic {m!=107;PDreq_1!121;}  goto link7; 

fi;
p6: if
:: PDreq_1!121; goto link7; 

fi;
link7: if
:: PCind_1?0; goto p6; 

:: LDreq_1?dest; goto link2req; 

fi;
}

active proctype Bus() { 
byte t[2];
byte destfault[2];
byte next[2];
byte i=0;
byte j=0;
byte m=0;
byte busy=0;

idle: if
::  ! (t[0]==0 && t[1]==0); goto arbresgab; 

::  atomic {PAreq_0?m;i = 0;}  goto idleq; 

::  atomic {PAreq_1?m;i = 1;}  goto idleq; 

fi;
arbresgab: if
::  d_step {t[0] = 0;t[1] = 0;}  goto idle; 

fi;
idleq: if
::  atomic {i==0 && t[i]==1;PAcon_0!111;}  goto idle; 

::  atomic {i==1 && t[i]==1;PAcon_1!111;}  goto idle; 

::  atomic {i==0 && t[i]==0;PAcon_0!110;t[i] = 1;busy = i;}  goto bus_busy; 

::  atomic {i==1 && t[i]==0;PAcon_1!110;t[i] = 1;busy = i;}  goto bus_busy; 

fi;
bus_busy: if
::  atomic {PAreq_0?m;i = 0;}  goto busyq; 

::  atomic {PAreq_1?m;i = 1;}  goto busyq; 

::  atomic {busy==0;PCind_0!0;}  goto get_next_packet; 

::  atomic {busy==1;PCind_1!0;}  goto get_next_packet; 

::  d_step {busy==2 && next[0]==0 && next[1]==0;j = 0;}  goto sub_action_gab; 

::  d_step {busy==2 &&  ! (next[0]==0 && next[1]==0);j = 0;}  goto resolve; 

fi;
busyq: if
::  atomic {m==108 && i==0;PAcon_0!111;}  goto bus_busy; 

::  atomic {m==108 && i==1;PAcon_1!111;}  goto bus_busy; 

::  d_step {m==109;next[i] = 1;}  goto bus_busy; 

fi;
sub_action_gab: if
::  atomic {0==j;PDind_0!122;j = j+1;}  goto sub_action_gab; 

::  atomic {1==j;PDind_1!122;j = j+1;}  goto sub_action_gab; 

:: j==2; goto idle; 

fi;
get_next_packet: if
::  atomic {busy==0;PDreq_0?m;j = 0;}  goto distribute; 

::  atomic {busy==1;PDreq_1?m;j = 0;}  goto distribute; 

fi;
resolve: if
::  d_step {j<2 && next[j]==0;j = j+1;}  goto resolve; 

::  atomic {0==j && next[j]==1;PAcon_0!110;}  goto resolveq; 

::  atomic {1==j && next[j]==1;PAcon_1!110;}  goto resolveq; 

:: j==2; goto resolve2; 

fi;
resolveq: if
::  atomic {0==j;PCind_0!0;j = j+1;}  goto resolve; 

::  atomic {1==j;PCind_1!0;j = j+1;}  goto resolve; 

fi;
resolve2: if
::  atomic {PDreq_0?m;j = 0;}  goto resolve2q; 

::  atomic {PDreq_1?m;j = 1;}  goto resolve2q; 

fi;
resolve2q: if
::  ! ((next[0]==0 && next[1]==0) || (next[0]+next[1]==1)) && m!=120; goto resolve; 

::  d_step { ! ((next[0]==0 && next[1]==0) || (next[0]+next[1]==1)) && m==120;next[j] = 0;}  goto resolve; 

::  d_step {(next[0]+next[1]==1) && m==120;next[j] = 0;j = 0;}  goto sub_action_gab; 

::  d_step {(next[0]+next[1]==1) && m!=120;busy = j;next[j] = 0;j = 0;}  goto distribute; 

fi;
distribute: if
::  d_step {j==busy;j = j+1;}  goto distribute; 

::  atomic {0==j;PAreq_0?m;}  goto distributeq; 

::  atomic {1==j;PAreq_1?m;}  goto distributeq; 

::  atomic {0==j &&  ! (m==114 && destfault[j]==1);PDind_0!m;j = j+1;}  goto distribute; 

::  atomic {1==j &&  ! (m==114 && destfault[j]==1);PDind_1!m;j = j+1;}  goto distribute; 

::  atomic {0==j && m==116;PDind_0!m;}  goto data_dummy; 

::  atomic {1==j && m==116;PDind_1!m;}  goto data_dummy; 

::  atomic {0==j && m==116;PDind_0!117;j = j+1;}  goto distribute; 

::  atomic {1==j && m==116;PDind_1!117;j = j+1;}  goto distribute; 

::  atomic {0==j && m==114;PDind_0!115;j = j+1;}  goto distribute; 

::  atomic {1==j && m==114;PDind_1!115;j = j+1;}  goto distribute; 

::  atomic {0==j && m==112;PDind_0!113;j = j+1;}  goto distribute; 

::  atomic {1==j && m==112;PDind_1!113;j = j+1;}  goto distribute; 

::  d_step {j!=busy && j<2 && m<100;destfault[j] = 1;i = 0;}  goto dest_cor; 

::  d_step {j!=busy && j<2 && m<100;destfault[j] = 1;i = 1;}  goto dest_cor; 

:: j==2 && m!=120; goto bus_busy; 

::  d_step {j==2 && m==120;busy = 2;}  goto bus_busy; 

fi;
distributeq: if
::  atomic {m==108 && j==0;PAcon_0!111;}  goto distribute; 

::  atomic {m==108 && j==1;PAcon_1!111;}  goto distribute; 

::  d_step {m==109;next[j] = 1;}  goto distribute; 

fi;
dest_cor: if
::  atomic {0==j;PDind_0!i;j = j+1;}  goto distribute; 

::  atomic {1==j;PDind_1!i;j = j+1;}  goto distribute; 

fi;
data_dummy: if
::  atomic {0==j;PDind_0!123;j = j+1;}  goto distribute; 

::  atomic {1==j;PDind_1!123;j = j+1;}  goto distribute; 

fi;
}

active proctype Application_0() { 

q: if
:: TDreq_0!1; goto r; 

fi;
r: 
 false; }

