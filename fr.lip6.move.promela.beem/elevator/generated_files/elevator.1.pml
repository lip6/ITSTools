byte floor_queue_0[2];
byte floor_queue_0_act=0;
byte floor_queue_1[2];
byte floor_queue_1_act=0;
byte floor_queue_2[2];
byte floor_queue_2_act=0;
byte floor_queue_3[2];
byte floor_queue_3_act=0;
byte floor_queue_4[2];
byte floor_queue_4_act=0;
byte current=0;

chan call_0 =[0] of {int};
chan get_in_0 =[0] of {int};
chan get_out_0 =[0] of {int};
chan call_1 =[0] of {int};
chan get_in_1 =[0] of {int};
chan get_out_1 =[0] of {int};

active proctype Person_0() { 
byte at_floor=0;

out: if
:: call_0!at_floor; goto waiting; 

fi;
waiting: if
::  atomic {0!=at_floor;get_in_0!0;}  goto in_elevator; 

::  atomic {1!=at_floor;get_in_0!1;}  goto in_elevator; 

::  atomic {2!=at_floor;get_in_0!2;}  goto in_elevator; 

::  atomic {3!=at_floor;get_in_0!3;}  goto in_elevator; 

::  atomic {4!=at_floor;get_in_0!4;}  goto in_elevator; 

fi;
in_elevator: if
:: get_out_0?at_floor; goto out; 

fi;
}

active proctype Person_1() { 
byte at_floor=0;

out: if
:: call_1!at_floor; goto waiting; 

fi;
waiting: if
::  atomic {0!=at_floor;get_in_1!0;}  goto in_elevator; 

::  atomic {1!=at_floor;get_in_1!1;}  goto in_elevator; 

::  atomic {2!=at_floor;get_in_1!2;}  goto in_elevator; 

::  atomic {3!=at_floor;get_in_1!3;}  goto in_elevator; 

::  atomic {4!=at_floor;get_in_1!4;}  goto in_elevator; 

fi;
in_elevator: if
:: get_out_1?at_floor; goto out; 

fi;
}

active proctype Servis() { 
byte floor=0;
byte caller=0;

q: if
::  atomic {call_0?floor;caller = 0;}  goto r; 

::  atomic {call_1?floor;caller = 1;}  goto r; 

fi;
r: if
::  d_step {0==floor;floor_queue_0[floor_queue_0_act] = caller;floor_queue_0_act = floor_queue_0_act+1;}  goto q; 

::  d_step {1==floor;floor_queue_1[floor_queue_1_act] = caller;floor_queue_1_act = floor_queue_1_act+1;}  goto q; 

::  d_step {2==floor;floor_queue_2[floor_queue_2_act] = caller;floor_queue_2_act = floor_queue_2_act+1;}  goto q; 

::  d_step {3==floor;floor_queue_3[floor_queue_3_act] = caller;floor_queue_3_act = floor_queue_3_act+1;}  goto q; 

::  d_step {4==floor;floor_queue_4[floor_queue_4_act] = caller;floor_queue_4_act = floor_queue_4_act+1;}  goto q; 

fi;
}

active proctype Elevator() { 
byte going_to=0;
byte serving=0;
byte who=0;

choose_next: if
::  d_step {0==serving && floor_queue_0_act==0 && ( ! (floor_queue_0_act==0) ||  ! (floor_queue_1_act==0) ||  ! (floor_queue_2_act==0) ||  ! (floor_queue_3_act==0) ||  ! (floor_queue_4_act==0));serving = (serving+1)%5;}  goto choose_next; 

::  d_step {1==serving && floor_queue_1_act==0 && ( ! (floor_queue_0_act==0) ||  ! (floor_queue_1_act==0) ||  ! (floor_queue_2_act==0) ||  ! (floor_queue_3_act==0) ||  ! (floor_queue_4_act==0));serving = (serving+1)%5;}  goto choose_next; 

::  d_step {2==serving && floor_queue_2_act==0 && ( ! (floor_queue_0_act==0) ||  ! (floor_queue_1_act==0) ||  ! (floor_queue_2_act==0) ||  ! (floor_queue_3_act==0) ||  ! (floor_queue_4_act==0));serving = (serving+1)%5;}  goto choose_next; 

::  d_step {3==serving && floor_queue_3_act==0 && ( ! (floor_queue_0_act==0) ||  ! (floor_queue_1_act==0) ||  ! (floor_queue_2_act==0) ||  ! (floor_queue_3_act==0) ||  ! (floor_queue_4_act==0));serving = (serving+1)%5;}  goto choose_next; 

::  d_step {4==serving && floor_queue_4_act==0 && ( ! (floor_queue_0_act==0) ||  ! (floor_queue_1_act==0) ||  ! (floor_queue_2_act==0) ||  ! (floor_queue_3_act==0) ||  ! (floor_queue_4_act==0));serving = (serving+1)%5;}  goto choose_next; 

:: 0==serving && ( ! (floor_queue_0_act==0)); goto move_next; 

:: 1==serving && ( ! (floor_queue_1_act==0)); goto move_next; 

:: 2==serving && ( ! (floor_queue_2_act==0)); goto move_next; 

:: 3==serving && ( ! (floor_queue_3_act==0)); goto move_next; 

:: 4==serving && ( ! (floor_queue_4_act==0)); goto move_next; 

fi;
move_next: if
::  d_step {serving<current;current = current-1;}  goto move_next; 

::  d_step {serving>current;current = current+1;}  goto move_next; 

::  d_step {serving==current && 0==current;who = floor_queue_0[0];floor_queue_0[0] = floor_queue_0[1];floor_queue_0[1] = 0;floor_queue_0_act = floor_queue_0_act-1;}  goto q; 

::  d_step {serving==current && 1==current;who = floor_queue_1[0];floor_queue_1[0] = floor_queue_1[1];floor_queue_1[1] = 0;floor_queue_1_act = floor_queue_1_act-1;}  goto q; 

::  d_step {serving==current && 2==current;who = floor_queue_2[0];floor_queue_2[0] = floor_queue_2[1];floor_queue_2[1] = 0;floor_queue_2_act = floor_queue_2_act-1;}  goto q; 

::  d_step {serving==current && 3==current;who = floor_queue_3[0];floor_queue_3[0] = floor_queue_3[1];floor_queue_3[1] = 0;floor_queue_3_act = floor_queue_3_act-1;}  goto q; 

::  d_step {serving==current && 4==current;who = floor_queue_4[0];floor_queue_4[0] = floor_queue_4[1];floor_queue_4[1] = 0;floor_queue_4_act = floor_queue_4_act-1;}  goto q; 

fi;
q: if
::  atomic {0==who;get_in_0?going_to;}  goto transporting; 

::  atomic {1==who;get_in_1?going_to;}  goto transporting; 

fi;
transporting: if
::  atomic {0==who && going_to==current;get_out_0!current;going_to = 0;who = 0;}  goto choose_next; 

::  atomic {1==who && going_to==current;get_out_1!current;going_to = 0;who = 0;}  goto choose_next; 

::  d_step {going_to<current;current = current-1;}  goto transporting; 

::  d_step {going_to>current;current = current+1;}  goto transporting; 

fi;
}

