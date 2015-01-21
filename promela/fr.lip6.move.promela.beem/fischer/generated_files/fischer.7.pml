byte id=0;
byte t[8];


init { 
 d_step { 
t[0] =255; t[1] =255; t[2] =255; t[3] =255; t[4] =255; t[5] =255; t[6] =255; t[7] =255; }
atomic { 
run Timer();
run P_0();
run P_1();
run P_2();
run P_3();
run P_4();
run P_5();
run P_6();
run P_7();
} }

proctype Timer() { 

q: if
::  d_step {t[0]!=0 && t[1]!=0 && t[2]!=0 && t[3]!=0 && t[4]!=0 && t[5]!=0 && t[6]!=0 && t[7]!=0;t[0] = (t[0]-1)|((t[0]==255)*255);t[1] = (t[1]-1)|((t[1]==255)*255);t[2] = (t[2]-1)|((t[2]==255)*255);t[3] = (t[3]-1)|((t[3]==255)*255);t[4] = (t[4]-1)|((t[4]==255)*255);t[5] = (t[5]-1)|((t[5]==255)*255);t[6] = (t[6]-1)|((t[6]==255)*255);t[7] = (t[7]-1)|((t[7]==255)*255);}  goto q; 

fi;
}

proctype P_0() { 

NCS: if
::  d_step {id==0;t[0] = 3;}  goto try; 

fi;
try: if
::  d_step {t[0] = 3;id = 0+1;}  goto wait; 

fi;
wait: if
::  d_step {t[0]==0;t[0] = 255;}  goto wait; 

:: t[0]==255 && id==0+1; goto CS; 

:: id!=0+1 && t[0]==255; goto NCS; 

fi;
CS: if
:: id = 0; goto NCS; 

fi;
}

proctype P_1() { 

NCS: if
::  d_step {id==0;t[1] = 3;}  goto try; 

fi;
try: if
::  d_step {t[1] = 3;id = 1+1;}  goto wait; 

fi;
wait: if
::  d_step {t[1]==0;t[1] = 255;}  goto wait; 

:: t[1]==255 && id==1+1; goto CS; 

:: id!=1+1 && t[1]==255; goto NCS; 

fi;
CS: if
:: id = 0; goto NCS; 

fi;
}

proctype P_2() { 

NCS: if
::  d_step {id==0;t[2] = 3;}  goto try; 

fi;
try: if
::  d_step {t[2] = 3;id = 2+1;}  goto wait; 

fi;
wait: if
::  d_step {t[2]==0;t[2] = 255;}  goto wait; 

:: t[2]==255 && id==2+1; goto CS; 

:: id!=2+1 && t[2]==255; goto NCS; 

fi;
CS: if
:: id = 0; goto NCS; 

fi;
}

proctype P_3() { 

NCS: if
::  d_step {id==0;t[3] = 3;}  goto try; 

fi;
try: if
::  d_step {t[3] = 3;id = 3+1;}  goto wait; 

fi;
wait: if
::  d_step {t[3]==0;t[3] = 255;}  goto wait; 

:: t[3]==255 && id==3+1; goto CS; 

:: id!=3+1 && t[3]==255; goto NCS; 

fi;
CS: if
:: id = 0; goto NCS; 

fi;
}

proctype P_4() { 

NCS: if
::  d_step {id==0;t[4] = 3;}  goto try; 

fi;
try: if
::  d_step {t[4] = 3;id = 4+1;}  goto wait; 

fi;
wait: if
::  d_step {t[4]==0;t[4] = 255;}  goto wait; 

:: t[4]==255 && id==4+1; goto CS; 

:: id!=4+1 && t[4]==255; goto NCS; 

fi;
CS: if
:: id = 0; goto NCS; 

fi;
}

proctype P_5() { 

NCS: if
::  d_step {id==0;t[5] = 3;}  goto try; 

fi;
try: if
::  d_step {t[5] = 3;id = 5+1;}  goto wait; 

fi;
wait: if
::  d_step {t[5]==0;t[5] = 255;}  goto wait; 

:: t[5]==255 && id==5+1; goto CS; 

:: id!=5+1 && t[5]==255; goto NCS; 

fi;
CS: if
:: id = 0; goto NCS; 

fi;
}

proctype P_6() { 

NCS: if
::  d_step {id==0;t[6] = 3;}  goto try; 

fi;
try: if
::  d_step {t[6] = 3;id = 6+1;}  goto wait; 

fi;
wait: if
::  d_step {t[6]==0;t[6] = 255;}  goto wait; 

:: t[6]==255 && id==6+1; goto CS; 

:: id!=6+1 && t[6]==255; goto NCS; 

fi;
CS: if
:: id = 0; goto NCS; 

fi;
}

proctype P_7() { 

NCS: if
::  d_step {id==0;t[7] = 3;}  goto try; 

fi;
try: if
::  d_step {t[7] = 3;id = 7+1;}  goto wait; 

fi;
wait: if
::  d_step {t[7]==0;t[7] = 255;}  goto wait; 

:: t[7]==255 && id==7+1; goto CS; 

:: id!=7+1 && t[7]==255; goto NCS; 

fi;
CS: if
:: id = 0; goto NCS; 

fi;
}

