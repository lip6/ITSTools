byte cur=0;
byte sleeping=0;
byte sleep_op=0;
byte sw_stand_by=0;
byte generated_ap_interrupt=0;
byte lsl_command=0;
byte lsl_running=0;
byte lsl_data=0;
byte ap_interrupt=0;
byte enabled_lsl_interrupt=0;
byte lsl_interrupt=0;
byte lsl_interrupt_ex=0;
byte generated_lsl_interrupt=0;
byte some_running=0;
byte some_data=0;
byte some_interrupt=0;

chan ap_down =[0] of {int};
chan ap_actv =[0] of {int};
chan ap_down_ack =[0] of {int};
chan ap_down_nack =[0] of {int};
chan calc =[0] of {int};

active proctype AP() { 
byte no_ap_ints=0;

actv: if
:: ap_actv?0; goto actv; 

::  d_step {no_ap_ints<1;generated_ap_interrupt = 1;no_ap_ints = no_ap_ints+1;}  goto actv; 

:: ap_down!0; goto prepare_ack; 

fi;
prepare_ack: if
:: ap_down!0; goto prepare_ack; 

:: ap_down_nack?0; goto actv; 

:: ap_down_ack?0; goto stand_by; 

fi;
stand_by: if
::  goto actv; 

:: ap_actv?0; goto actv; 

fi;
}

active proctype LSL_Interrupt_Handler() { 

lsl_int_service: if
::  d_step {cur==0 && enabled_lsl_interrupt==1 && generated_lsl_interrupt==1;cur = 1;lsl_interrupt_ex = 1;}  goto interrupt_received; 

fi;
interrupt_received: if
:: cur==1 && sleeping==0; goto awake; 

::  d_step {cur==1 && sleeping==1;sleeping = 0;}  goto awake; 

fi;
awake: if
::  d_step {cur==1;enabled_lsl_interrupt = 0;generated_lsl_interrupt = 0;lsl_interrupt = 1;lsl_interrupt_ex = 0;some_interrupt = 1;}  goto check_stand_by; 

fi;
check_stand_by: if
::  d_step {cur==1 && sw_stand_by==0;cur = 0;}  goto lsl_int_service; 

:: cur==1 && sw_stand_by==1; goto insert_noop; 

fi;
insert_noop: if
::  d_step {cur==1;sleep_op = 0;}  goto clear_stand_by; 

fi;
clear_stand_by: if
::  d_step {cur==1;sw_stand_by = 0;cur = 0;}  goto lsl_int_service; 

fi;
}

active proctype AP_Interrupt_Handler() { 
byte old_cur=0;

ap_int_service: if
::  d_step {generated_ap_interrupt==1 && lsl_interrupt_ex==0;generated_ap_interrupt = 0;}  goto set_cur; 

fi;
set_cur: if
::  d_step {cur==0;old_cur = 0;cur = 2;}  goto interrupt_received; 

::  d_step {cur==1;old_cur = 1;cur = 2;}  goto interrupt_received; 

fi;
interrupt_received: if
:: sleeping==0; goto awake; 

::  d_step {sleeping==1;sleeping = 0;}  goto awake; 

fi;
awake: if
::  d_step {enabled_lsl_interrupt = 0;generated_lsl_interrupt = 0;ap_interrupt = 1;some_interrupt = 1;}  goto check_stand_by; 

fi;
check_stand_by: if
:: sw_stand_by==0; goto reset_cur; 

:: sw_stand_by==1; goto insert_noop; 

fi;
reset_cur: if
::  d_step {old_cur==0;cur = 0;}  goto ap_int_service; 

::  d_step {old_cur==1;cur = 1;}  goto ap_int_service; 

fi;
insert_noop: if
:: sleep_op = 0; goto clear_stand_by; 

fi;
clear_stand_by: if
:: sw_stand_by = 0; goto reset_cur; 

fi;
}

active proctype Interrupt_Generator() { 
byte no_lsl_ints=0;

generate: if
::  d_step {enabled_lsl_interrupt==1 && no_lsl_ints<2;generated_lsl_interrupt = 1;no_lsl_ints = no_lsl_ints+1;}  goto generate; 

fi;
}

active proctype LSL_Driver() { 

stand_by: if
::  d_step {cur==0 && lsl_command==3;lsl_command = 0;}  goto stand_by; 

::  d_step {cur==0 && lsl_command==1;lsl_command = 0;}  goto up_down_received; 

::  d_step {cur==0 && lsl_command==2;lsl_command = 0;}  goto up_down_received; 

fi;
up_down_received: if
:: cur==0; goto react; 

fi;
react: if
::  d_step {cur==0;lsl_running = 0;lsl_data = 0;}  goto call; 

::  d_step {cur==0;lsl_running = 1;lsl_data = 1;some_running = 1;some_data = 1;}  goto stand_by; 

fi;
call: if
:: calc!0; goto stand_by; 

fi;
}

active proctype Calc() { 

idle: if
:: calc?0; goto calc_data; 

fi;
calc_data: if
:: lsl_data==1; goto calc_running; 

::  d_step {lsl_data==0;some_data = 0;}  goto calc_running; 

fi;
calc_running: if
:: lsl_running==1; goto idle; 

::  d_step {lsl_running==0;some_running = 0;}  goto idle; 

fi;
}

active proctype IOP() { 

actv: if
::  atomic {cur==0;ap_down?0;}  goto down_received; 

fi;
going_down: if
::  d_step {cur==0;lsl_data = 0;some_data = 0;lsl_running = 1;some_running = 1;}  goto clear_interrupts; 

fi;
clear_interrupts: if
::  d_step {cur==0;ap_interrupt = 0;lsl_interrupt = 0;some_interrupt = 0;}  goto enable_lsl_interrupt; 

fi;
enable_lsl_interrupt: if
::  d_step {cur==0;enabled_lsl_interrupt = 1;generated_lsl_interrupt = 0;}  goto issue_down_lsl; 

fi;
issue_down_lsl: if
::  d_step {cur==0;lsl_command = 2;}  goto wait_for_down; 

fi;
wait_for_down: if
:: cur==0 && some_running==0; goto down_expected; 

:: cur==0 && some_data==1; goto down_expected; 

fi;
down_expected: if
:: cur==0 && some_data==1; goto disable_lsl_interrupt; 

:: cur==0 && some_data==0; goto down_verified; 

fi;
disable_lsl_interrupt: if
::  d_step {cur==0;enabled_lsl_interrupt = 0;generated_lsl_interrupt = 0;}  goto issue_actv_commands; 

fi;
down_verified: if
:: ap_down_ack!0; goto insert_noop; 

fi;
down_received: if
::  goto going_down; 

fi;
insert_noop: if
::  d_step {cur==0;sleep_op = 1;}  goto set_stand_by; 

fi;
set_stand_by: if
::  d_step {cur==0;sw_stand_by = 1;}  goto check_interrupts; 

fi;
check_interrupts: if
:: cur==0 && some_interrupt==0; goto check_noop; 

:: cur==0 && some_interrupt==1; goto wake_up; 

fi;
check_noop: if
:: cur==0 && sleep_op==0; goto wake_up; 

::  d_step {cur==0 && sleep_op==1;sleeping = 1;}  goto w_stand_by; 

fi;
w_stand_by: if
:: cur==0; goto stand_by; 

fi;
wake_up: if
::  d_step {cur==0;sw_stand_by = 0;lsl_data = 0;some_data = 0;lsl_running = 1;some_running = 1;}  goto clear_int; 

fi;
stand_by: if
:: cur==0 && some_interrupt==1; goto wake_up; 

fi;
clear_int: if
::  d_step {cur==0;ap_interrupt = 0;lsl_interrupt = 0;some_interrupt = 0;}  goto issue_lsl_up; 

fi;
issue_lsl_up: if
::  d_step {cur==0;lsl_command = 1;}  goto wait_init_response; 

fi;
wait_init_response: if
:: cur==0 && some_running==0; goto data_expected; 

:: cur==0 && some_data==1; goto data_expected; 

fi;
data_expected: if
:: cur==0 && some_data==1; goto s_actv; 

:: cur==0 && some_data==0; goto noise; 

fi;
noise: if
::  d_step {cur==0;lsl_data = 0;some_data = 0;lsl_running = 1;some_running = 1;}  goto re_enable_lsl_interrupt; 

fi;
s_actv: if
:: ap_actv!0; goto now_wait; 

fi;
now_wait: if
:: cur==0; goto actv; 

fi;
re_enable_lsl_interrupt: if
::  d_step {cur==0;enabled_lsl_interrupt = 1;}  goto re_issue_lsl_down; 

fi;
re_issue_lsl_down: if
::  d_step {cur==0;lsl_command = 2;}  goto wait_response; 

fi;
wait_response: if
:: cur==0 && some_running==0; goto observe_status; 

:: cur==0 && some_data==1; goto observe_status; 

fi;
observe_status: if
:: cur==0 && some_data==0; goto insert_noop; 

:: cur==0 && some_data==1; goto clear_lsl_interrupt; 

fi;
clear_lsl_interrupt: if
::  d_step {cur==0;enabled_lsl_interrupt = 0;generated_lsl_interrupt = 0;}  goto send_actv_command; 

fi;
send_actv_command: if
::  atomic {ap_actv!0;lsl_command = 3;}  goto back_to_actv; 

fi;
back_to_actv: if
:: cur==0; goto actv; 

fi;
issue_actv_commands: if
::  d_step {cur==0;lsl_command = 3;}  goto send_nack; 

fi;
send_nack: if
:: ap_down_nack!0; goto enter_actv; 

fi;
enter_actv: if
:: cur==0; goto actv; 

fi;
}

