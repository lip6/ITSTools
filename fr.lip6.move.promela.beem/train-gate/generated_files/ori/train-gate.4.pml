byte e[7];
byte x=0;
byte max_x_1=0;
byte max_x_2=0;
byte max_x_3=0;
byte max_x_4=0;
byte max_x_5=0;
byte max_x_6=0;

chan appr =[0] of {int};
chan stop =[0] of {int};
chan go =[0] of {int};
chan leave =[0] of {int};
chan is_empty =[0] of {int};
chan notempty =[0] of {int};
chan hd =[0] of {int};
chan add =[0] of {int};
chan rem =[0] of {int};

active proctype Clock() { 

S1: if
::  d_step {x<=max_x_2 && x<=max_x_3 && x<=max_x_4 && x<=max_x_5 && x<=max_x_6 && x<=max_x_1;x = x+1;}  goto S1; 

fi;
}

active proctype Gate() { 

Free: if
:: notempty?0; goto S5; 

:: is_empty?0; goto S4; 

fi;
S1: if
:: rem?0; goto Free; 

fi;
S2: if
:: add!0; goto Occ; 

fi;
S3: if
:: add!0; goto Occ; 

fi;
S4: if
:: appr?0; goto S3; 

fi;
S5: if
:: hd!0; goto Send; 

fi;
S6: if
:: stop!0; goto S2; 

fi;
Occ: if
:: appr?0; goto S6; 

:: leave?0; goto S1; 

fi;
Send: if
:: go!0; goto Occ; 

fi;
}

active proctype IntQueue() { 
byte list[7];
byte ln=0;
byte i=0;

Start: if
::  atomic {ln>=1;rem!0;ln = ln-1;i = 0;}  goto Shiftdown; 

::  atomic {ln==0;is_empty!0;}  goto Start; 

::  atomic {add?0;list[ln] = e;ln = ln+1;}  goto Start; 

::  atomic {hd?0;e = list[0];}  goto Start; 

::  atomic {ln>0;notempty!0;}  goto Start; 

fi;
Shiftdown: if
::  d_step {i<ln;list[i] = list[i+1];i = i+1;}  goto Shiftdown; 

::  d_step {ln==i;list[i] = 0;i = 0;}  goto Start; 

fi;
}

active proctype Train_1() { 

Safe: if
::  atomic {appr!0;e = 1;x = 0;max_x_1 = 20;}  goto Appr; 

fi;
Stop: if
::  atomic {e==1;go?0;x = 0;max_x_1 = 15;}  goto Start; 

fi;
Cross: if
::  atomic {x>=3;leave!0;e = 1;x = 0;max_x_1 = 25;}  goto Safe; 

fi;
Appr: if
::  d_step {x>=10;x = 0;max_x_1 = 5;}  goto Cross; 

::  atomic {x<=10 && e==1;stop?0;x = 0;max_x_1 = 25;}  goto Stop; 

fi;
Start: if
::  d_step {x>=7;x = 0;max_x_1 = 5;}  goto Cross; 

fi;
}

active proctype Train_2() { 

Safe: if
::  atomic {appr!0;e = 2;x = 0;max_x_2 = 20;}  goto Appr; 

fi;
Stop: if
::  atomic {e==2;go?0;x = 0;max_x_2 = 15;}  goto Start; 

fi;
Cross: if
::  atomic {x>=3;leave!0;e = 2;x = 0;max_x_2 = 25;}  goto Safe; 

fi;
Appr: if
::  d_step {x>=10;x = 0;max_x_2 = 5;}  goto Cross; 

::  atomic {x<=10 && e==2;stop?0;x = 0;max_x_2 = 25;}  goto Stop; 

fi;
Start: if
::  d_step {x>=7;x = 0;max_x_2 = 5;}  goto Cross; 

fi;
}

active proctype Train_3() { 

Safe: if
::  atomic {appr!0;e = 3;x = 0;max_x_3 = 20;}  goto Appr; 

fi;
Stop: if
::  atomic {e==3;go?0;x = 0;max_x_3 = 15;}  goto Start; 

fi;
Cross: if
::  atomic {x>=3;leave!0;e = 3;x = 0;max_x_3 = 25;}  goto Safe; 

fi;
Appr: if
::  d_step {x>=10;x = 0;max_x_3 = 5;}  goto Cross; 

::  atomic {x<=10 && e==3;stop?0;x = 0;max_x_3 = 25;}  goto Stop; 

fi;
Start: if
::  d_step {x>=7;x = 0;max_x_3 = 5;}  goto Cross; 

fi;
}

active proctype Train_4() { 

Safe: if
::  atomic {appr!0;e = 4;x = 0;max_x_4 = 20;}  goto Appr; 

fi;
Stop: if
::  atomic {e==4;go?0;x = 0;max_x_4 = 15;}  goto Start; 

fi;
Cross: if
::  atomic {x>=3;leave!0;e = 4;x = 0;max_x_4 = 25;}  goto Safe; 

fi;
Appr: if
::  d_step {x>=10;x = 0;max_x_4 = 5;}  goto Cross; 

::  atomic {x<=10 && e==4;stop?0;x = 0;max_x_4 = 25;}  goto Stop; 

fi;
Start: if
::  d_step {x>=7;x = 0;max_x_4 = 5;}  goto Cross; 

fi;
}

active proctype Train_5() { 

Safe: if
::  atomic {appr!0;e = 5;x = 0;max_x_5 = 20;}  goto Appr; 

fi;
Stop: if
::  atomic {e==5;go?0;x = 0;max_x_5 = 15;}  goto Start; 

fi;
Cross: if
::  atomic {x>=3;leave!0;e = 5;x = 0;max_x_5 = 25;}  goto Safe; 

fi;
Appr: if
::  d_step {x>=10;x = 0;max_x_5 = 5;}  goto Cross; 

::  atomic {x<=10 && e==5;stop?0;x = 0;max_x_5 = 25;}  goto Stop; 

fi;
Start: if
::  d_step {x>=7;x = 0;max_x_5 = 5;}  goto Cross; 

fi;
}

active proctype Train_6() { 

Safe: if
::  atomic {appr!0;e = 6;x = 0;max_x_6 = 20;}  goto Appr; 

fi;
Stop: if
::  atomic {e==6;go?0;x = 0;max_x_6 = 15;}  goto Start; 

fi;
Cross: if
::  atomic {x>=3;leave!0;e = 6;x = 0;max_x_6 = 25;}  goto Safe; 

fi;
Appr: if
::  d_step {x>=10;x = 0;max_x_6 = 5;}  goto Cross; 

::  atomic {x<=10 && e==6;stop?0;x = 0;max_x_6 = 25;}  goto Stop; 

fi;
Start: if
::  d_step {x>=7;x = 0;max_x_6 = 5;}  goto Cross; 

fi;
}

