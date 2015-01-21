
chan userToCC =[0] of {int};
chan ccToUser_0_in =[0] of {int};
chan ccToUser_0_out =[0] of {int};
chan ccToUserAdmin_0 =[0] of {int};
chan ccToVault =[0] of {int};
chan vaultToCC =[0] of {int};
chan msgSync =[0] of {int};

active proctype User_0() { 
byte cmd=0;
byte edit[3];
byte registered[3];
byte waitingForCheckedOut=0;

ready: if
::  atomic {waitingForCheckedOut==0;userToCC!(0*16+0);}  goto doneGet; 

::  atomic {registered[0]==0 && edit[0]==0;userToCC!(9*16+0);registered[0] = 1;}  goto doneRegister; 

::  atomic {registered[0]==1 && edit[0]==0;userToCC!(10*16+0);registered[0] = 0;}  goto doneUnRegister; 

::  atomic {edit[0]==0 && waitingForCheckedOut==0;userToCC!(2*16+0);waitingForCheckedOut = 1;}  goto ready; 

:: edit[0]==1; goto editing; 

:: ccToUser_0_out?cmd; goto cmdReceived; 

fi;
doneGet: if
:: ccToUser_0_out?cmd; goto getCmdReceived; 

fi;
getCmdReceived: if
::  d_step {cmd==1;registered[0] = 1;}  goto ready; 

fi;
doneRegister: if
::  goto ready; 

fi;
doneUnRegister: if
::  goto ready; 

fi;
editing: if
::  atomic {userToCC!(5*16+0);edit[0] = 0;}  goto ready; 

::  atomic {userToCC!(7*16+0);edit[0] = 0;}  goto ready; 

:: userToCC!(8*16+0); goto ready; 

fi;
cmdReceived: if
::  d_step {cmd==3;edit[0] = 1;waitingForCheckedOut = 0;}  goto ready; 

::  d_step {cmd==4;waitingForCheckedOut = 0;}  goto ready; 

fi;
}

active proctype UserAdmin_0() { 
byte cmd=0;

ready: if
:: ccToUserAdmin_0?cmd; goto cmdReceived; 

fi;
cmdReceived: if
::  atomic {cmd==12;msgSync!0;}  goto doneNotify; 

::  atomic {cmd==11;msgSync!0;}  goto doneUpdate; 

fi;
doneNotify: if
::  goto ready; 

fi;
doneUpdate: if
::  goto ready; 

fi;
}

active proctype CC() { 
byte cmd=0;
byte ID=0;
byte registered[1];
byte writeLock=0;

ready: if
:: userToCC?cmd; goto cmdReceived; 

fi;
cmdReceived: if
:: cmd/16==0 && cmd%16==0; goto doneGet0; 

:: cmd/16==9 && cmd%16==0; goto doneRegister0; 

:: cmd/16==10 && cmd%16==0; goto doneUnRegister0; 

:: cmd/16==2; goto doneCheckOut; 

:: cmd/16==5; goto doneUnCheckOut; 

:: cmd/16==7; goto doneCheckIn; 

:: cmd/16==8; goto doneCheckInOut; 

fi;
doneGet0: if
:: registered[cmd%16] = 1; goto get_step1; 

fi;
doneRegister0: if
:: registered[0] = 1; goto ready; 

fi;
doneUnRegister0: if
:: registered[0] = 0; goto ready; 

fi;
doneCheckedOut0: if
:: ID = 0; goto checkOut_step4; 

fi;
doneNotify0: if
:: ID = ID+1; goto checkOut_step4; 

fi;
notifyCmdSent0: if
:: msgSync?0; goto doneNotify0; 

fi;
updateCmdSent0: if
:: msgSync?0; goto doneUpdate0; 

fi;
doneUnCheckOut0: if
:: writeLock = 0; goto Update; 

::  goto Update; 

fi;
doneCheckInOut0: if
:: writeLock==1; goto checkWriteLockTrue; 

:: writeLock==0; goto checkWriteLockFalse; 

fi;
doneUpdate0: if
:: ID = ID+1; goto update_cycle; 

fi;
get_step1: if
:: ccToVault!cmd; goto get_step2; 

fi;
get_step2: if
:: vaultToCC?cmd; goto get_step3; 

fi;
get_step3: if
::  atomic {cmd/16==1 && cmd%16==0;ccToUser_0_in!1;}  goto ready; 

fi;
doneCheckOut: if
:: writeLock==0; goto checkOut_step1; 

::  atomic {writeLock==1 && cmd%16==0;ccToUser_0_in!4;}  goto doneNotAvailable; 

fi;
checkOut_step1: if
::  atomic {ccToVault!cmd;writeLock = 1;}  goto checkOut_step2; 

fi;
checkOut_step2: if
:: vaultToCC?cmd; goto checkOut_step3; 

fi;
checkOut_step3: if
::  atomic {cmd/16==3 && cmd%16==0;ccToUser_0_in!3;}  goto doneCheckedOut; 

fi;
checkOut_step4: if
:: ID<1; goto checkOut_step5; 

:: ID>=1; goto ready; 

fi;
checkOut_step5: if
::  atomic {ID==0 && ID!=cmd%16 && registered[ID]==1;ccToUserAdmin_0!12;}  goto notifyCmdSent0; 

::  d_step {ID==cmd%16 || registered[ID]==0;ID = ID+1;}  goto checkOut_step4; 

fi;
doneNotAvailable: if
::  goto ready; 

fi;
doneCheckedOut: if
:: cmd%16==0; goto doneCheckedOut0; 

fi;
doneUnCheckOut: if
:: cmd%16==0; goto doneUnCheckOut0; 

fi;
doneCheckIn: if
::  atomic {ccToVault!cmd;writeLock = 0;}  goto checkIn_step1; 

fi;
checkIn_step1: if
:: ccToVault?cmd; goto checkIn_step2; 

fi;
checkIn_step2: if
:: cmd%16==0; goto doneUnCheckOut0; 

fi;
doneCheckInOut: if
:: ccToVault!(7*16+cmd%16); goto checkInOut_step1; 

fi;
checkInOut_step1: if
:: vaultToCC?cmd; goto checkInOut_step2; 

fi;
checkInOut_step2: if
:: cmd/16==11 && cmd%16==0; goto doneCheckInOut0; 

fi;
Update: if
:: ID = 0; goto update_cycle; 

fi;
checkWriteLockTrue: if
::  goto Update; 

fi;
checkWriteLockFalse: if
::  goto Update; 

fi;
update_cycle: if
:: ID<1; goto update_step1; 

:: ID>=1; goto ready; 

fi;
update_step1: if
::  atomic {ID==0 && ID!=cmd%16 && registered[ID]==1;ccToUserAdmin_0!11;}  goto updateCmdSent0; 

::  d_step {ID==cmd%16 || registered[ID]==0;ID = ID+1;}  goto update_cycle; 

fi;
doneCheckIn0: 
 false; }

active proctype Vault() { 
byte cmd=0;

ready: if
:: ccToVault?cmd; goto cmdReceived; 

fi;
cmdReceived: if
::  atomic {cmd/16==0;vaultToCC!(1*16+cmd%16);}  goto ready; 

::  atomic {cmd/16==2;vaultToCC!(3*16+cmd%16);}  goto ready; 

::  atomic {cmd/16==7;vaultToCC!(7*16+cmd%16);}  goto ready; 

fi;
}

active proctype chnlnel_ccToUser_0() { 
byte v=0;

ready: if
:: ccToUser_0_in?v; goto tr; 

fi;
tr: if
:: ccToUser_0_out!v; goto ready; 

fi;
}

