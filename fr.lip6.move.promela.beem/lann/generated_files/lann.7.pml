
chan link_0_in =[0] of {int};
chan link_0_out =[0] of {int};
chan link_1_in =[0] of {int};
chan link_1_out =[0] of {int};
chan link_2_in =[0] of {int};
chan link_2_out =[0] of {int};
chan link_3_in =[0] of {int};
chan link_3_out =[0] of {int};
chan link_4_in =[0] of {int};
chan link_4_out =[0] of {int};

active proctype P_0() { 
byte status=0;
byte v=0;

CS: if
::  atomic {link_1_in!255;status = 0;}  goto wait; 

fi;
wait: if
:: link_0_out?v; goto got_msg; 

::  atomic {status==0;link_1_in!24;status = 1;}  goto wait; 

fi;
got_msg: if
:: v==255; goto CS; 

:: v!=255 && v>24; goto wait; 

::  atomic {v!=255 && status==1 && v<24;link_1_in!v;status = 2;}  goto wait; 

::  atomic {v!=255 && status==0 && v<24;link_1_in!v;}  goto wait; 

::  d_step {v!=255 && v==24 && status!=1;status = 0;}  goto wait; 

:: v!=255 && v==24 && status==1; goto CS; 

fi;
}

active proctype P_1() { 
byte status=0;
byte v=0;

wait: if
:: link_1_out?v; goto got_msg; 

::  atomic {status==0;link_2_in!14;status = 1;}  goto wait; 

fi;
CS: if
::  atomic {link_2_in!255;status = 0;}  goto wait; 

fi;
got_msg: if
:: v==255; goto CS; 

:: v!=255 && v>14; goto wait; 

::  atomic {v!=255 && status==1 && v<14;link_2_in!v;status = 2;}  goto wait; 

::  atomic {v!=255 && status==0 && v<14;link_2_in!v;}  goto wait; 

::  d_step {v!=255 && v==14 && status!=1;status = 0;}  goto wait; 

:: v!=255 && v==14 && status==1; goto CS; 

fi;
}

active proctype P_2() { 
byte status=0;
byte v=0;

wait: if
:: link_2_out?v; goto got_msg; 

::  atomic {status==0;link_3_in!6;status = 1;}  goto wait; 

fi;
CS: if
::  atomic {link_3_in!255;status = 0;}  goto wait; 

fi;
got_msg: if
:: v==255; goto CS; 

:: v!=255 && v>6; goto wait; 

::  atomic {v!=255 && status==1 && v<6;link_3_in!v;status = 2;}  goto wait; 

::  atomic {v!=255 && status==0 && v<6;link_3_in!v;}  goto wait; 

::  d_step {v!=255 && v==6 && status!=1;status = 0;}  goto wait; 

:: v!=255 && v==6 && status==1; goto CS; 

fi;
}

active proctype P_3() { 
byte status=0;
byte v=0;

wait: if
:: link_3_out?v; goto got_msg; 

::  atomic {status==0;link_4_in!0;status = 1;}  goto wait; 

fi;
CS: if
::  atomic {link_4_in!255;status = 0;}  goto wait; 

fi;
got_msg: if
:: v==255; goto CS; 

:: v!=255 && v>0; goto wait; 

::  atomic {v!=255 && status==1 && v<0;link_4_in!v;status = 2;}  goto wait; 

::  atomic {v!=255 && status==0 && v<0;link_4_in!v;}  goto wait; 

::  d_step {v!=255 && v==0 && status!=1;status = 0;}  goto wait; 

:: v!=255 && v==0 && status==1; goto CS; 

fi;
}

active proctype P_4() { 
byte status=0;
byte v=0;

wait: if
:: link_4_out?v; goto got_msg; 

::  atomic {status==0;link_0_in!23;status = 1;}  goto wait; 

fi;
CS: if
::  atomic {link_0_in!255;status = 0;}  goto wait; 

fi;
got_msg: if
:: v==255; goto CS; 

:: v!=255 && v>23; goto wait; 

::  atomic {v!=255 && status==1 && v<23;link_0_in!v;status = 2;}  goto wait; 

::  atomic {v!=255 && status==0 && v<23;link_0_in!v;}  goto wait; 

::  d_step {v!=255 && v==23 && status!=1;status = 0;}  goto wait; 

:: v!=255 && v==23 && status==1; goto CS; 

fi;
}

active proctype chnlnel_link_0() { 
byte v=0;

ready: if
:: link_0_in?v; goto tr; 

fi;
tr: if
::  goto ready; 

:: link_0_out!v; goto ready; 

fi;
}

active proctype chnlnel_link_1() { 
byte v=0;

ready: if
:: link_1_in?v; goto tr; 

fi;
tr: if
::  goto ready; 

:: link_1_out!v; goto ready; 

fi;
}

active proctype chnlnel_link_2() { 
byte v=0;

ready: if
:: link_2_in?v; goto tr; 

fi;
tr: if
::  goto ready; 

:: link_2_out!v; goto ready; 

fi;
}

active proctype chnlnel_link_3() { 
byte v=0;

ready: if
:: link_3_in?v; goto tr; 

fi;
tr: if
::  goto ready; 

:: link_3_out!v; goto ready; 

fi;
}

active proctype chnlnel_link_4() { 
byte v=0;

ready: if
:: link_4_in?v; goto tr; 

fi;
tr: if
::  goto ready; 

:: link_4_out!v; goto ready; 

fi;
}

