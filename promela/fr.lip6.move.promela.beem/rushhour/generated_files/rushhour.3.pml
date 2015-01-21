byte A[49];


init { 
 d_step { 
A[0] =0; A[1] =0; A[2] =1; A[3] =0; A[4] =1; A[5] =1; A[6] =0; A[7] =1; A[8] =0; A[9] =1; A[10] =1; A[11] =1; A[12] =1; A[13] =1; A[14] =1; A[15] =1; A[16] =1; A[17] =1; A[18] =1; A[19] =1; A[20] =1; A[21] =0; A[22] =0; A[23] =0; A[24] =1; A[25] =0; A[26] =1; A[27] =0; A[28] =1; A[29] =1; A[30] =1; A[31] =0; A[32] =1; A[33] =1; A[34] =0; A[35] =1; A[36] =1; A[37] =1; A[38] =1; A[39] =1; A[40] =0; A[41] =1; A[42] =0; A[43] =0; A[44] =0; A[45] =0; A[46] =0; A[47] =0; A[48] =1; }
atomic { 
run Red_car();
run Car_hor_1();
run Car_hor_2();
run Car_hor_3();
run Car_hor_4();
run Car_ver_1();
run Car_ver_2();
run Car_ver_3();
run Car_ver_4();
run Car_ver_5();
run Car_ver_6();
run Car_ver_7();
run Car_ver_8();
} }

proctype Red_car() { 
byte x=1;
byte y=2;
byte lngth=2;

q: if
::  d_step {x>0 && A[((y)*7+x-1)]==0;A[((y)*7+x+lngth-1)] = 0;A[((y)*7+x-1)] = 1;x = x-1;}  goto q; 

::  d_step {x+lngth<7 && A[((y)*7+x+lngth)]==0;A[((y)*7+x)] = 0;A[((y)*7+x+lngth)] = 1;x = x+1;}  goto q; 

:: x==5; goto out; 

fi;
out: 
 false; }

proctype Car_hor_1() { 
byte x=4;
byte y=0;
byte lngth=2;

q: if
::  d_step {x>0 && A[((y)*7+x-1)]==0;A[((y)*7+x+lngth-1)] = 0;A[((y)*7+x-1)] = 1;x = x-1;}  goto q; 

::  d_step {x+lngth<7 && A[((y)*7+x+lngth)]==0;A[((y)*7+x)] = 0;A[((y)*7+x+lngth)] = 1;x = x+1;}  goto q; 

fi;
}

proctype Car_hor_2() { 
byte x=1;
byte y=4;
byte lngth=2;

q: if
::  d_step {x>0 && A[((y)*7+x-1)]==0;A[((y)*7+x+lngth-1)] = 0;A[((y)*7+x-1)] = 1;x = x-1;}  goto q; 

::  d_step {x+lngth<7 && A[((y)*7+x+lngth)]==0;A[((y)*7+x)] = 0;A[((y)*7+x+lngth)] = 1;x = x+1;}  goto q; 

fi;
}

proctype Car_hor_3() { 
byte x=4;
byte y=4;
byte lngth=2;

q: if
::  d_step {x>0 && A[((y)*7+x-1)]==0;A[((y)*7+x+lngth-1)] = 0;A[((y)*7+x-1)] = 1;x = x-1;}  goto q; 

::  d_step {x+lngth<7 && A[((y)*7+x+lngth)]==0;A[((y)*7+x)] = 0;A[((y)*7+x+lngth)] = 1;x = x+1;}  goto q; 

fi;
}

proctype Car_hor_4() { 
byte x=1;
byte y=5;
byte lngth=4;

q: if
::  d_step {x>0 && A[((y)*7+x-1)]==0;A[((y)*7+x+lngth-1)] = 0;A[((y)*7+x-1)] = 1;x = x-1;}  goto q; 

::  d_step {x+lngth<7 && A[((y)*7+x+lngth)]==0;A[((y)*7+x)] = 0;A[((y)*7+x+lngth)] = 1;x = x+1;}  goto q; 

fi;
}

proctype Car_ver_1() { 
byte x=0;
byte y=1;
byte lngth=2;

q: if
::  d_step {y>0 && A[((y-1)*7+x)]==0;A[((y+lngth-1)*7+x)] = 0;A[((y-1)*7+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<7 && A[((y+lngth)*7+x)]==0;A[((y)*7+x)] = 0;A[((y+lngth)*7+x)] = 1;y = y+1;}  goto q; 

fi;
}

proctype Car_ver_2() { 
byte x=0;
byte y=4;
byte lngth=2;

q: if
::  d_step {y>0 && A[((y-1)*7+x)]==0;A[((y+lngth-1)*7+x)] = 0;A[((y-1)*7+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<7 && A[((y+lngth)*7+x)]==0;A[((y)*7+x)] = 0;A[((y+lngth)*7+x)] = 1;y = y+1;}  goto q; 

fi;
}

proctype Car_ver_3() { 
byte x=2;
byte y=0;
byte lngth=2;

q: if
::  d_step {y>0 && A[((y-1)*7+x)]==0;A[((y+lngth-1)*7+x)] = 0;A[((y-1)*7+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<7 && A[((y+lngth)*7+x)]==0;A[((y)*7+x)] = 0;A[((y+lngth)*7+x)] = 1;y = y+1;}  goto q; 

fi;
}

proctype Car_ver_4() { 
byte x=3;
byte y=1;
byte lngth=3;

q: if
::  d_step {y>0 && A[((y-1)*7+x)]==0;A[((y+lngth-1)*7+x)] = 0;A[((y-1)*7+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<7 && A[((y+lngth)*7+x)]==0;A[((y)*7+x)] = 0;A[((y+lngth)*7+x)] = 1;y = y+1;}  goto q; 

fi;
}

proctype Car_ver_5() { 
byte x=4;
byte y=1;
byte lngth=2;

q: if
::  d_step {y>0 && A[((y-1)*7+x)]==0;A[((y+lngth-1)*7+x)] = 0;A[((y-1)*7+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<7 && A[((y+lngth)*7+x)]==0;A[((y)*7+x)] = 0;A[((y+lngth)*7+x)] = 1;y = y+1;}  goto q; 

fi;
}

proctype Car_ver_6() { 
byte x=5;
byte y=1;
byte lngth=3;

q: if
::  d_step {y>0 && A[((y-1)*7+x)]==0;A[((y+lngth-1)*7+x)] = 0;A[((y-1)*7+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<7 && A[((y+lngth)*7+x)]==0;A[((y)*7+x)] = 0;A[((y+lngth)*7+x)] = 1;y = y+1;}  goto q; 

fi;
}

proctype Car_ver_7() { 
byte x=6;
byte y=1;
byte lngth=2;

q: if
::  d_step {y>0 && A[((y-1)*7+x)]==0;A[((y+lngth-1)*7+x)] = 0;A[((y-1)*7+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<7 && A[((y+lngth)*7+x)]==0;A[((y)*7+x)] = 0;A[((y+lngth)*7+x)] = 1;y = y+1;}  goto q; 

fi;
}

proctype Car_ver_8() { 
byte x=6;
byte y=5;
byte lngth=2;

q: if
::  d_step {y>0 && A[((y-1)*7+x)]==0;A[((y+lngth-1)*7+x)] = 0;A[((y-1)*7+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<7 && A[((y+lngth)*7+x)]==0;A[((y)*7+x)] = 0;A[((y+lngth)*7+x)] = 1;y = y+1;}  goto q; 

fi;
}

