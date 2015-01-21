byte x=0;
byte y=255;
byte z=0;
byte t[3];


init { 
 d_step { 
t[0] =255; t[1] =255; t[2] =255; }
atomic { 
run Timer();
run P_0();
run P_1();
run P_2();
} }

proctype Timer() { 

q: if
::  d_step {t[0]!=0 && t[1]!=0 && t[2]!=0;t[0] = (t[0]-1)|((t[0]==255)*255);t[1] = (t[1]-1)|((t[1]==255)*255);t[2] = (t[2]-1)|((t[2]==255)*255);}  goto q; 

fi;
}

proctype P_0() { 

NCS: if
::  d_step {x = 0;t[0] = 255;}  goto p3; 

fi;
p3: if
::  d_step {y==255;t[0] = 2;}  goto p4; 

fi;
p4: if
::  d_step {y = 0;t[0] = 2;}  goto p5; 

fi;
p5: if
::  d_step {x==0;t[0] = 2;}  goto p9; 

::  d_step {x!=0;t[0] = 3;}  goto p6; 

fi;
p6: if
::  d_step {t[0]==0;t[0] = 2;}  goto p7; 

fi;
p7: if
::  d_step {y!=0;t[0] = 2;}  goto NCS; 

::  d_step {y==0;t[0] = 255;}  goto p8; 

fi;
p8: if
:: z==0; goto CS; 

fi;
p9: if
::  d_step {z = 1;t[0] = 255;}  goto CS; 

fi;
CS: if
::  d_step {z = 0;t[0] = 2;}  goto p12; 

fi;
p12: if
::  d_step {y!=0;t[0] = 255;}  goto NCS; 

::  d_step {y==0;t[0] = 2;}  goto p13; 

fi;
p13: if
::  d_step {y = 255;t[0] = 255;}  goto NCS; 

fi;
p2: 
 false; }

proctype P_1() { 

NCS: if
::  d_step {x = 1;t[1] = 255;}  goto p3; 

fi;
p3: if
::  d_step {y==255;t[1] = 2;}  goto p4; 

fi;
p4: if
::  d_step {y = 1;t[1] = 2;}  goto p5; 

fi;
p5: if
::  d_step {x==1;t[1] = 2;}  goto p9; 

::  d_step {x!=1;t[1] = 3;}  goto p6; 

fi;
p6: if
::  d_step {t[1]==0;t[1] = 2;}  goto p7; 

fi;
p7: if
::  d_step {y!=1;t[1] = 2;}  goto NCS; 

::  d_step {y==1;t[1] = 255;}  goto p8; 

fi;
p8: if
:: z==0; goto CS; 

fi;
p9: if
::  d_step {z = 1;t[1] = 255;}  goto CS; 

fi;
CS: if
::  d_step {z = 0;t[1] = 2;}  goto p12; 

fi;
p12: if
::  d_step {y!=1;t[1] = 255;}  goto NCS; 

::  d_step {y==1;t[1] = 2;}  goto p13; 

fi;
p13: if
::  d_step {y = 255;t[1] = 255;}  goto NCS; 

fi;
p2: 
 false; }

proctype P_2() { 

NCS: if
::  d_step {x = 2;t[2] = 255;}  goto p3; 

fi;
p3: if
::  d_step {y==255;t[2] = 2;}  goto p4; 

fi;
p4: if
::  d_step {y = 2;t[2] = 2;}  goto p5; 

fi;
p5: if
::  d_step {x==2;t[2] = 2;}  goto p9; 

::  d_step {x!=2;t[2] = 3;}  goto p6; 

fi;
p6: if
::  d_step {t[2]==0;t[2] = 2;}  goto p7; 

fi;
p7: if
::  d_step {y!=2;t[2] = 2;}  goto NCS; 

::  d_step {y==2;t[2] = 255;}  goto p8; 

fi;
p8: if
:: z==0; goto CS; 

fi;
p9: if
::  d_step {z = 1;t[2] = 255;}  goto CS; 

fi;
CS: if
::  d_step {z = 0;t[2] = 2;}  goto p12; 

fi;
p12: if
::  d_step {y!=2;t[2] = 255;}  goto NCS; 

::  d_step {y==2;t[2] = 2;}  goto p13; 

fi;
p13: if
::  d_step {y = 255;t[2] = 255;}  goto NCS; 

fi;
p2: 
 false; }

