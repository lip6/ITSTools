byte tGB=255;
byte tC=255;
byte tE=255;
byte tGC=255;
int toGear=0;
int currentGear=0;

chan OpenClutch =[0] of {int};
chan CloseClutch =[0] of {int};
chan ClutchIsOpen =[0] of {int};
chan ClutchIsClosed =[0] of {int};
chan ReqSet =[0] of {int};
chan ReqNeu =[0] of {int};
chan GearSet =[0] of {int};
chan GearNeu =[0] of {int};
chan ReqSpeed =[0] of {int};
chan ReqTorque =[0] of {int};
chan ReqZeroTorque =[0] of {int};
chan TorqueZero =[0] of {int};
chan SpeedSet =[0] of {int};
chan ReqNewGear =[0] of {int};
chan NewGear =[0] of {int};

active proctype Clutch() { 

closed: if
::  atomic {OpenClutch?0;tC = 3;}  goto opening; 

fi;
opening: if
::  atomic {tC<=1;ClutchIsOpen!0;tC = 255;}  goto open; 

::  d_step {tC==0;tC = 255;}  goto error_open; 

fi;
open: if
::  atomic {CloseClutch?0;tC = 3;}  goto closing; 

fi;
closing: if
::  atomic {tC<=1;ClutchIsClosed!0;tC = 255;}  goto closed; 

::  d_step {tC==0;tC = 255;}  goto error_close; 

fi;
error_open: 
error_close: 
 false; }

active proctype GearBox() { 

neutral: if
::  atomic {ReqSet?0;tGB = 6;}  goto closing; 

fi;
closing: if
::  d_step {tGB==0;tGB = 255;}  goto error_idle; 

::  atomic {tGB<=4;GearSet!0;tGB = 255;}  goto idle; 

fi;
idle: if
::  atomic {ReqNeu?0;tGB = 4;}  goto opening; 

fi;
opening: if
::  d_step {tGB==0;tGB = 255;}  goto error_neu; 

::  atomic {tGB<=2;GearNeu!0;tGB = 255;}  goto neutral; 

fi;
error_idle: 
error_neu: 
 false; }

active proctype Engine() { 

initial: if
::  atomic {ReqSpeed?0;tE = 4;}  goto find_speed; 

fi;
torque: if
::  atomic {ReqZeroTorque?0;tE = 8;}  goto dec_torque; 

fi;
dec_torque: if
:: tE==0; goto clutch_open; 

::  atomic {tE<=5;TorqueZero!0;tE = 255;}  goto zero; 

fi;
clutch_close: if
::  d_step {tE==0;tE = 255;}  goto error_speed; 

::  atomic {tE<17;ReqTorque?0;tE = 255;}  goto torque; 

fi;
clutch_open: if
:: toGear==0; goto initial; 

::  d_step {toGear!=0;tE = 18;}  goto clutch_close; 

fi;
speed: if
::  d_step {tE==0;tE = 255;}  goto error_speed; 

::  atomic {tE>0;ReqTorque?0;tE = 255;}  goto torque; 

fi;
find_speed: if
:: tE==0; goto clutch_open; 

::  atomic {tE<=3;SpeedSet!0;tE = 10;}  goto speed; 

fi;
zero: if
:: toGear==0; goto initial; 

::  atomic {toGear!=0;ReqSpeed?0;tE = 4;}  goto find_speed; 

fi;
error_speed: 
 false; }

active proctype Interface() { 

gear: if
::  atomic {currentGear<30;ReqNewGear!1;}  goto go_up; 

::  atomic {currentGear>-1;ReqNewGear!-1;}  goto go_down; 

fi;
go_up: if
::  atomic {NewGear?0;currentGear = currentGear+1;}  goto gear; 

fi;
go_down: if
::  atomic {NewGear?0;currentGear = currentGear-1;}  goto gear; 

fi;
}

active proctype GearControl() { 
int dir=0;

gear: if
::  atomic {ReqNewGear?dir;toGear = toGear+dir;}  goto initiate; 

fi;
initiate: if
::  atomic {currentGear!=0;ReqZeroTorque!0;tGC = 5;}  goto check_torque; 

:: currentGear==0; goto req_sync_speed; 

fi;
check_torque: if
::  atomic {tGC>0;TorqueZero?0;}  goto req_neu_gear; 

::  atomic {tGC==0;OpenClutch!0;tGC = 4;}  goto check_clutch2; 

fi;
req_neu_gear: if
::  atomic {ReqNeu!0;tGC = 5;}  goto check_gear_neu; 

fi;
check_gear_neu: if
:: GearNeu?0; goto req_sync_speed; 

::  d_step {tGC==0;tGC = 255;}  goto gneu_error; 

fi;
clutch_open2: if
::  atomic {ReqNeu!0;tGC = 5;}  goto check_gear_neu2; 

fi;
check_gear_neu2: if
::  d_step {tGC==0;tGC = 255;}  goto gneu_error; 

::  atomic {GearNeu?0;tGC = 0;}  goto req_set_gear2; 

fi;
req_sync_speed: if
:: toGear==0; goto gear_chnlged; 

::  atomic {toGear!=0;ReqSpeed!0;tGC = 3;}  goto check_sync_speed; 

fi;
check_clutch: if
:: tGC==0; goto copen_error; 

::  atomic {ClutchIsOpen?0;tGC = 0;}  goto clutch_open; 

fi;
req_set_gear2: if
::  atomic {toGear!=0;ReqSet!0;tGC = 7;}  goto check_gear_set2; 

::  atomic {toGear==0;CloseClutch!0;tGC = 4;}  goto check_clutch_closed2; 

fi;
check_sync_speed: if
::  atomic {tGC>0;SpeedSet?0;}  goto req_set_gear; 

::  atomic {tGC==0;OpenClutch!0;tGC = 4;}  goto check_clutch; 

fi;
req_set_gear: if
::  atomic {ReqSet!0;tGC = 7;}  goto check_gear_set1; 

fi;
clutch_open: if
::  atomic {ReqSet!0;tGC = 7;}  goto check_gear_set2; 

fi;
check_gear_set1: if
::  atomic {GearSet?0;tGC = 0;}  goto req_torque; 

::  d_step {tGC==0;tGC = 255;}  goto gset_error; 

fi;
check_gear_set2: if
::  d_step {tGC==0;tGC = 255;}  goto gset_error; 

::  atomic {GearSet?0;tGC = 0;}  goto clutch_close; 

fi;
req_torque: if
:: ReqTorque!0; goto gear_chnlged; 

fi;
check_clutch_closed: if
::  atomic {ClutchIsClosed?0;tGC = 0;}  goto req_torque; 

::  d_step {tGC==0;tGC = 255;}  goto cclose_error; 

fi;
clutch_close: if
::  atomic {CloseClutch!0;tGC = 4;}  goto check_clutch_closed; 

fi;
gear_chnlged: if
::  atomic {NewGear!0;tGC = 255;}  goto gear; 

fi;
check_clutch_closed2: if
::  d_step {tGC==0;tGC = 255;}  goto cclose_error; 

::  atomic {ClutchIsClosed?0;tGC = 0;}  goto gear_chnlged; 

fi;
check_clutch2: if
::  d_step {tGC==0;tGC = 255;}  goto copen_error; 

::  atomic {ClutchIsOpen?0;tGC = 0;}  goto clutch_open2; 

fi;
copen_error: 
gneu_error: 
gset_error: 
cclose_error: 
 false; }

active proctype Timer() { 

q: if
::  d_step {tGB!=0 && tC!=0 && tE!=0 && tGC!=0;tGB = (tGB-1)|((tGB==255)*255);tC = (tC-1)|((tC==255)*255);tE = (tE-1)|((tE==255)*255);tGC = (tGC-1)|((tGC==255)*255);}  goto q; 

fi;
}

