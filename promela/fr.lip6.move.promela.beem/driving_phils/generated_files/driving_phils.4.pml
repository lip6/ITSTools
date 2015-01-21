byte request[6];
byte starvers[6];
byte resources[4];
int res0[3];
int res1[3];
int acquiring[3];
int entryRound=1;
byte phase=0;
byte fire=0;


active proctype round_about() { 
int i=0;

reset: if
::  d_step {i<3;res0[i] = -1;res1[i] = -1;acquiring[i] = -1;i = i+1;}  goto reset; 

::  d_step {i==3;i = 0;phase = 0;}  goto begin0; 

fi;
begin0: if
::  d_step {i<4;resources[i] = 0;i = i+1;}  goto begin0; 

::  d_step {i==4;i = 0;}  goto begin1; 

fi;
begin1: if
::  d_step {i<3 && res0[i]!=-1;resources[res0[i]*2] = entryRound;resources[res0[i]*2+1] = i;i = i+1;}  goto begin1; 

::  d_step {i<3 && res0[i]==-1;i = i+1;}  goto begin1; 

::  d_step {i==3;i = 0;}  goto begin2; 

fi;
begin2: if
::  d_step {i<3 && res1[i]!=-1;resources[res1[i]*2] = entryRound;resources[res1[i]*2+1] = i;i = i+1;}  goto begin2; 

::  d_step {i<3 && res1[i]==-1;i = i+1;}  goto begin2; 

::  d_step {i==3;i = 0;phase = 1;fire = 0;}  goto action; 

fi;
action: if
::  d_step {fire==3;fire = 0;phase = 2;}  goto end0; 

fi;
end0: if
::  d_step {i<2 && resources[2*i]!=0;request[2*resources[2*i+1]+i] = 0;starvers[2*resources[2*i+1]+i] = 0;i = i+1;}  goto end0; 

::  d_step {i<2 && resources[2*i]==0;i = i+1;}  goto end0; 

::  d_step {i==2;i = 0;}  goto end1; 

fi;
end1: if
::  d_step {i<6;i = i+1;}  goto end1; 

::  d_step {i==6 && fire==3;phase = 0;i = 0;}  goto begin0; 

fi;
begin3: 
end2: 
 false; }

active proctype phil_0() { 
int i=0;

action: if
::  d_step {phase==1 && res0[0]!=-1;resources[res0[0]] = 0;resources[res0[0]+1] = 0;res0[0] = res1[0];res1[0] = -1;fire = fire+1;}  goto end; 

::  d_step {phase==1 && res1[0]==-1 && acquiring[0]==-1;acquiring[0] = 0;fire = fire+1;request[0*2+0] = entryRound;}  goto end; 

::  d_step {phase==1 && res1[0]==-1 && acquiring[0]==-1;acquiring[0] = 1;fire = fire+1;request[0*2+1] = entryRound;}  goto end; 

::  d_step {phase==1;fire = fire+1;}  goto end; 

fi;
end: if
::  d_step {phase==2 && acquiring[0]==-1;fire = fire+1;}  goto action; 

:: phase==2 && acquiring[0]!=-1 && fire==0; goto mutex; 

::  d_step {phase==2 && acquiring[0]!=-1;fire = fire+1;}  goto action; 

fi;
mutex: if
::  d_step {i<3 && res0[i]!=acquiring[0] && res1[i]!=acquiring[0];i = i+1;}  goto mutex; 

::  d_step {i<3 && (res0[i]==acquiring[0] || res1[i]==acquiring[0]);fire = fire+1;i = i+1;}  goto action; 

::  d_step {i==3 && res0[0]==-1;res0[0] = acquiring[0];acquiring[0] = -1;fire = fire+1;i = 0;}  goto action; 

::  d_step {i==3 && res0[0]!=-1;res1[0] = acquiring[0];acquiring[0] = -1;fire = fire+1;i = 0;}  goto action; 

fi;
}

active proctype phil_1() { 
int i=0;

action: if
::  d_step {phase==1 && res0[1]!=-1;resources[res0[1]] = 0;resources[res0[1]+1] = 0;res0[1] = res1[1];res1[1] = -1;fire = fire+1;}  goto end; 

::  d_step {phase==1 && res1[1]==-1 && acquiring[1]==-1;acquiring[1] = 0;fire = fire+1;request[1*2+0] = entryRound;}  goto end; 

::  d_step {phase==1 && res1[1]==-1 && acquiring[1]==-1;acquiring[1] = 1;fire = fire+1;request[1*2+1] = entryRound;}  goto end; 

::  d_step {phase==1;fire = fire+1;}  goto end; 

fi;
end: if
::  d_step {phase==2 && acquiring[1]==-1;fire = fire+1;}  goto action; 

:: phase==2 && acquiring[1]!=-1 && fire==1; goto mutex; 

::  d_step {phase==2 && acquiring[1]!=-1;fire = fire+1;}  goto action; 

fi;
mutex: if
::  d_step {i<3 && res0[i]!=acquiring[1] && res1[i]!=acquiring[1];i = i+1;}  goto mutex; 

::  d_step {i<3 && (res0[i]==acquiring[1] || res1[i]==acquiring[1]);fire = fire+1;i = i+1;}  goto action; 

::  d_step {i==3 && res0[1]==-1;res0[1] = acquiring[1];acquiring[1] = -1;fire = fire+1;i = 0;}  goto action; 

::  d_step {i==3 && res0[1]!=-1;res1[1] = acquiring[1];acquiring[1] = -1;fire = fire+1;i = 0;}  goto action; 

fi;
}

active proctype phil_2() { 
int i=0;

action: if
::  d_step {phase==1 && res0[2]!=-1;resources[res0[2]] = 0;resources[res0[2]+1] = 0;res0[2] = res1[2];res1[2] = -1;fire = fire+1;}  goto end; 

::  d_step {phase==1 && res1[2]==-1 && acquiring[2]==-1;acquiring[2] = 0;fire = fire+1;request[2*2+0] = entryRound;}  goto end; 

::  d_step {phase==1 && res1[2]==-1 && acquiring[2]==-1;acquiring[2] = 1;fire = fire+1;request[2*2+1] = entryRound;}  goto end; 

::  d_step {phase==1;fire = fire+1;}  goto end; 

fi;
end: if
::  d_step {phase==2 && acquiring[2]==-1;fire = fire+1;}  goto action; 

:: phase==2 && acquiring[2]!=-1 && fire==2; goto mutex; 

::  d_step {phase==2 && acquiring[2]!=-1;fire = fire+1;}  goto action; 

fi;
mutex: if
::  d_step {i<3 && res0[i]!=acquiring[2] && res1[i]!=acquiring[2];i = i+1;}  goto mutex; 

::  d_step {i<3 && (res0[i]==acquiring[2] || res1[i]==acquiring[2]);fire = fire+1;i = i+1;}  goto action; 

::  d_step {i==3 && res0[2]==-1;res0[2] = acquiring[2];acquiring[2] = -1;fire = fire+1;i = 0;}  goto action; 

::  d_step {i==3 && res0[2]!=-1;res1[2] = acquiring[2];acquiring[2] = -1;fire = fire+1;i = 0;}  goto action; 

fi;
}

