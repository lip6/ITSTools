byte done=0;

chan plt_belt_0 =[0] of {int};
chan plt_table_0 =[0] of {int};
chan plt_press_0 =[0] of {int};
chan plt_deposit_0 =[0] of {int};
chan plt_belt_1 =[0] of {int};
chan plt_table_1 =[0] of {int};
chan plt_press_1 =[0] of {int};
chan plt_deposit_1 =[0] of {int};
chan plt_belt_2 =[0] of {int};
chan plt_table_2 =[0] of {int};
chan plt_press_2 =[0] of {int};
chan plt_deposit_2 =[0] of {int};
chan plt_belt_3 =[0] of {int};
chan plt_table_3 =[0] of {int};
chan plt_press_3 =[0] of {int};
chan plt_deposit_3 =[0] of {int};
chan new_plate =[0] of {int};
chan sen =[0] of {int};
chan read =[0] of {int};
chan put_table =[0] of {int};
chan get_table =[0] of {int};
chan put_press =[0] of {int};
chan get_press =[0] of {int};
chan put_deposit =[0] of {int};

active proctype Plate_0() { 

new: if
:: new_plate!0; goto wait_belt; 

fi;
wait_belt: if
:: plt_belt_0!0; goto wait_table; 

fi;
wait_table: if
:: plt_table_0!0; goto wait_press; 

fi;
wait_press: if
:: plt_press_0!0; goto wait_deposit; 

fi;
wait_deposit: if
::  atomic {done<4;plt_deposit_0!0;done = done+1;}  goto new; 

fi;
}

active proctype Plate_1() { 

new: if
:: new_plate!1; goto wait_belt; 

fi;
wait_belt: if
:: plt_belt_1!0; goto wait_table; 

fi;
wait_table: if
:: plt_table_1!0; goto wait_press; 

fi;
wait_press: if
:: plt_press_1!0; goto wait_deposit; 

fi;
wait_deposit: if
::  atomic {done<4;plt_deposit_1!0;done = done+1;}  goto new; 

fi;
}

active proctype Plate_2() { 

new: if
:: new_plate!2; goto wait_belt; 

fi;
wait_belt: if
:: plt_belt_2!0; goto wait_table; 

fi;
wait_table: if
:: plt_table_2!0; goto wait_press; 

fi;
wait_press: if
:: plt_press_2!0; goto wait_deposit; 

fi;
wait_deposit: if
::  atomic {done<4;plt_deposit_2!0;done = done+1;}  goto new; 

fi;
}

active proctype Plate_3() { 

new: if
:: new_plate!3; goto wait_belt; 

fi;
wait_belt: if
:: plt_belt_3!0; goto wait_table; 

fi;
wait_table: if
:: plt_table_3!0; goto wait_press; 

fi;
wait_press: if
:: plt_press_3!0; goto wait_deposit; 

fi;
wait_deposit: if
::  atomic {done<4;plt_deposit_3!0;done = done+1;}  goto new; 

fi;
}

active proctype Belt() { 
byte k=0;

wait: if
:: new_plate?k; goto got_new; 

fi;
got_new: if
:: sen!0; goto q1; 

fi;
q1: if
::  atomic {k==0;plt_belt_0?0;}  goto q2; 

::  atomic {k==1;plt_belt_1?0;}  goto q2; 

::  atomic {k==2;plt_belt_2?0;}  goto q2; 

::  atomic {k==3;plt_belt_3?0;}  goto q2; 

fi;
q2: if
:: put_table!k; goto wait; 

fi;
}

active proctype Sensor() { 
byte count=0;

q: if
::  atomic {sen?0;count = count+1;}  goto q; 

::  atomic {count>0;read!0;count = count-1;}  goto q; 

fi;
}

active proctype Table() { 
byte k=0;

down_empty: if
:: put_table?k; goto down_full; 

fi;
down_full: if
::  atomic {k==0;plt_table_0?0;}  goto up_full; 

::  atomic {k==1;plt_table_1?0;}  goto up_full; 

::  atomic {k==2;plt_table_2?0;}  goto up_full; 

::  atomic {k==3;plt_table_3?0;}  goto up_full; 

fi;
up_empty: if
::  goto down_empty; 

fi;
up_full: if
:: get_table!k; goto up_empty; 

fi;
}

active proctype Press() { 
byte k=0;

wait: if
:: put_press?k; goto pressing; 

fi;
pressing: if
::  atomic {k==0;plt_press_0?0;}  goto done; 

::  atomic {k==1;plt_press_1?0;}  goto done; 

::  atomic {k==2;plt_press_2?0;}  goto done; 

::  atomic {k==3;plt_press_3?0;}  goto done; 

fi;
done: if
:: get_press!k; goto wait; 

fi;
}

active proctype Deposit() { 
byte k=0;

wait: if
:: put_deposit?k; goto loaded; 

fi;
loaded: if
::  atomic {k==0;plt_deposit_0?0;}  goto wait; 

::  atomic {k==1;plt_deposit_1?0;}  goto wait; 

::  atomic {k==2;plt_deposit_2?0;}  goto wait; 

::  atomic {k==3;plt_deposit_3?0;}  goto wait; 

fi;
}

active proctype Robot() { 
byte at_press=0;
byte at_table=0;
byte A=255;
byte B=255;

wait: if
:: (B==255 && at_press==1) || (A==255 && at_table==1); goto Bpress; 

::  atomic {at_table==0;read?0;at_table = 1;}  goto wait; 

:: (A!=255 && at_press==0) || B!=255; goto Apress; 

fi;
Atable: if
::  atomic {at_table==1 && A==255;get_table?A;at_table = 0;}  goto Atable; 

:: A!=255; goto Bpress; 

fi;
Bpress: if
::  atomic {B==255 && at_press==1;get_press?B;at_press = 0;}  goto Bpress; 

:: A==255 && at_table==1; goto Atable; 

:: (A!=255 && at_press==0) || (B!=255 && (A!=255 || at_table==0)); goto wait; 

fi;
Apress: if
::  atomic {A!=255 && at_press==0;put_press!A;A = 255;at_press = 1;}  goto Apress; 

:: B!=255; goto Bdeposit; 

:: (A==255 || at_press==1) && B==255; goto wait; 

fi;
Bdeposit: if
::  atomic {B!=255;put_deposit!B;B = 255;}  goto Bdeposit; 

:: B==255; goto Apress; 

fi;
}

