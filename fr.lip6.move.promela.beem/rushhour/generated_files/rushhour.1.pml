byte A[36];


init { 
 d_step { 
A[0] =1; A[1] =1; A[2] =1; A[3] =1; A[4] =0; A[5] =0; A[6] =1; A[7] =0; A[8] =1; A[9] =1; A[10] =0; A[11] =0; A[12] =1; A[13] =1; A[14] =1; A[15] =1; A[16] =0; A[17] =0; A[18] =1; A[19] =1; A[20] =1; A[21] =1; A[22] =0; A[23] =0; A[24] =0; A[25] =0; A[26] =0; A[27] =0; A[28] =0; A[29] =0; A[30] =0; A[31] =0; A[32] =0; A[33] =1; A[34] =1; A[35] =1; }
atomic { 
run Car_hor_1();
run Red_car();
run Car_hor_3();
run Car_hor_4();
run Car_ver_1();
run Car_ver_2();
run Car_ver_3();
} }

proctype Car_hor_1() { 
byte x=0;
byte y=0;
byte lngth=2;

q: if
::  d_step {x>0 && A[((y)*6+x-1)]==0;A[((y)*6+x+lngth-1)] = 0;A[((y)*6+x-1)] = 1;x = x-1;}  goto q; 

::  d_step {x+lngth<6 && A[((y)*6+x+lngth)]==0;A[((y)*6+x)] = 0;A[((y)*6+x+lngth)] = 1;x = x+1;}  goto q; 

fi;
}

proctype Red_car() { 
byte x=1;
byte y=2;
byte lngth=2;

q: if
::  d_step {x>0 && A[((y)*6+x-1)]==0;A[((y)*6+x+lngth-1)] = 0;A[((y)*6+x-1)] = 1;x = x-1;}  goto q; 

::  d_step {x+lngth<6 && A[((y)*6+x+lngth)]==0;A[((y)*6+x)] = 0;A[((y)*6+x+lngth)] = 1;x = x+1;}  goto q; 

:: x==4; goto out; 

fi;
out: 
 false; }

proctype Car_hor_3() { 
byte x=1;
byte y=3;
byte lngth=3;

q: if
::  d_step {x>0 && A[((y)*6+x-1)]==0;A[((y)*6+x+lngth-1)] = 0;A[((y)*6+x-1)] = 1;x = x-1;}  goto q; 

::  d_step {x+lngth<6 && A[((y)*6+x+lngth)]==0;A[((y)*6+x)] = 0;A[((y)*6+x+lngth)] = 1;x = x+1;}  goto q; 

fi;
}

proctype Car_hor_4() { 
byte x=3;
byte y=5;
byte lngth=3;

q: if
::  d_step {x>0 && A[((y)*6+x-1)]==0;A[((y)*6+x+lngth-1)] = 0;A[((y)*6+x-1)] = 1;x = x-1;}  goto q; 

::  d_step {x+lngth<6 && A[((y)*6+x+lngth)]==0;A[((y)*6+x)] = 0;A[((y)*6+x+lngth)] = 1;x = x+1;}  goto q; 

fi;
}

proctype Car_ver_1() { 
byte x=0;
byte y=1;
byte lngth=3;

q: if
::  d_step {y>0 && A[((y-1)*6+x)]==0;A[((y+lngth-1)*6+x)] = 0;A[((y-1)*6+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<6 && A[((y+lngth)*6+x)]==0;A[((y)*6+x)] = 0;A[((y+lngth)*6+x)] = 1;y = y+1;}  goto q; 

fi;
}

proctype Car_ver_2() { 
byte x=2;
byte y=0;
byte lngth=2;

q: if
::  d_step {y>0 && A[((y-1)*6+x)]==0;A[((y+lngth-1)*6+x)] = 0;A[((y-1)*6+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<6 && A[((y+lngth)*6+x)]==0;A[((y)*6+x)] = 0;A[((y+lngth)*6+x)] = 1;y = y+1;}  goto q; 

fi;
}

proctype Car_ver_3() { 
byte x=3;
byte y=0;
byte lngth=3;

q: if
::  d_step {y>0 && A[((y-1)*6+x)]==0;A[((y+lngth-1)*6+x)] = 0;A[((y-1)*6+x)] = 1;y = y-1;}  goto q; 

::  d_step {y+lngth<6 && A[((y+lngth)*6+x)]==0;A[((y)*6+x)] = 0;A[((y+lngth)*6+x)] = 1;y = y+1;}  goto q; 

fi;
}

