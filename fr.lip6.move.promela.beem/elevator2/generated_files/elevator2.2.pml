byte req[10];
int t=0;
int p=0;
byte v=0;


active proctype cabin() { 

idle: if
:: v>0; goto mov; 

fi;
mov: if
:: t==p; goto open; 

::  d_step {t<p;p = p-1;}  goto mov; 

::  d_step {t>p;p = p+1;}  goto mov; 

fi;
open: if
::  d_step {req[p] = 0;v = 0;}  goto idle; 

fi;
}

active proctype environment() { 

read: if
::  d_step {req[0]==0;req[0] = 1;}  goto read; 

::  d_step {req[1]==0;req[1] = 1;}  goto read; 

::  d_step {req[2]==0;req[2] = 1;}  goto read; 

::  d_step {req[3]==0;req[3] = 1;}  goto read; 

::  d_step {req[4]==0;req[4] = 1;}  goto read; 

::  d_step {req[5]==0;req[5] = 1;}  goto read; 

::  d_step {req[6]==0;req[6] = 1;}  goto read; 

::  d_step {req[7]==0;req[7] = 1;}  goto read; 

::  d_step {req[8]==0;req[8] = 1;}  goto read; 

::  d_step {req[9]==0;req[9] = 1;}  goto read; 

fi;
}

active proctype controller() { 

wait: if
:: v==0; goto work; 

fi;
work: if
::  d_step {req[0]==1;t = 0;}  goto done; 

::  d_step {req[1]==1;t = 1;}  goto done; 

::  d_step {req[2]==1;t = 2;}  goto done; 

::  d_step {req[3]==1;t = 3;}  goto done; 

::  d_step {req[4]==1;t = 4;}  goto done; 

::  d_step {req[5]==1;t = 5;}  goto done; 

::  d_step {req[6]==1;t = 6;}  goto done; 

::  d_step {req[7]==1;t = 7;}  goto done; 

::  d_step {req[8]==1;t = 8;}  goto done; 

::  d_step {req[9]==1;t = 9;}  goto done; 

fi;
done: if
:: v = 1; goto wait; 

fi;
}

