byte next[5];
byte locked[5];
byte tail=255;


init { 
 d_step { 
next[0] =255; next[1] =255; next[2] =255; next[3] =255; next[4] =255; }
atomic { 
run P_0();
run P_1();
run P_2();
run P_3();
run P_4();
} }

proctype P_0() { 
byte pred=0;

NCS: if
:: next[0] = 255; goto p2; 

fi;
p2: if
::  d_step {pred = tail;tail = 0;}  goto p3; 

fi;
p3: if
:: pred==255; goto CS; 

:: pred!=255; goto p4; 

fi;
p4: if
:: locked[0] = 1; goto p5; 

fi;
p5: if
:: next[pred] = 0; goto p6; 

fi;
p6: if
:: locked[0]==0; goto CS; 

fi;
CS: if
:: next[0]==255; goto p9; 

:: next[0]!=255; goto p13; 

fi;
p9: if
::  d_step {tail==0;tail = 255;}  goto NCS; 

:: tail!=0; goto p10; 

fi;
p13: if
:: locked[next[0]] = 0; goto NCS; 

fi;
p10: if
:: next[0]!=255; goto p13; 

fi;
}

proctype P_1() { 
byte pred=0;

NCS: if
:: next[1] = 255; goto p2; 

fi;
p2: if
::  d_step {pred = tail;tail = 1;}  goto p3; 

fi;
p3: if
:: pred==255; goto CS; 

:: pred!=255; goto p4; 

fi;
p4: if
:: locked[1] = 1; goto p5; 

fi;
p5: if
:: next[pred] = 1; goto p6; 

fi;
p6: if
:: locked[1]==0; goto CS; 

fi;
CS: if
:: next[1]==255; goto p9; 

:: next[1]!=255; goto p13; 

fi;
p9: if
::  d_step {tail==1;tail = 255;}  goto NCS; 

:: tail!=1; goto p10; 

fi;
p13: if
:: locked[next[1]] = 0; goto NCS; 

fi;
p10: if
:: next[1]!=255; goto p13; 

fi;
}

proctype P_2() { 
byte pred=0;

NCS: if
:: next[2] = 255; goto p2; 

fi;
p2: if
::  d_step {pred = tail;tail = 2;}  goto p3; 

fi;
p3: if
:: pred==255; goto CS; 

:: pred!=255; goto p4; 

fi;
p4: if
:: locked[2] = 1; goto p5; 

fi;
p5: if
:: next[pred] = 2; goto p6; 

fi;
p6: if
:: locked[2]==0; goto CS; 

fi;
CS: if
:: next[2]==255; goto p9; 

:: next[2]!=255; goto p13; 

fi;
p9: if
::  d_step {tail==2;tail = 255;}  goto NCS; 

:: tail!=2; goto p10; 

fi;
p13: if
:: locked[next[2]] = 0; goto NCS; 

fi;
p10: if
:: next[2]!=255; goto p13; 

fi;
}

proctype P_3() { 
byte pred=0;

NCS: if
:: next[3] = 255; goto p2; 

fi;
p2: if
::  d_step {pred = tail;tail = 3;}  goto p3; 

fi;
p3: if
:: pred==255; goto CS; 

:: pred!=255; goto p4; 

fi;
p4: if
:: locked[3] = 1; goto p5; 

fi;
p5: if
:: next[pred] = 3; goto p6; 

fi;
p6: if
:: locked[3]==0; goto CS; 

fi;
CS: if
:: next[3]==255; goto p9; 

:: next[3]!=255; goto p13; 

fi;
p9: if
::  d_step {tail==3;tail = 255;}  goto NCS; 

:: tail!=3; goto p10; 

fi;
p13: if
:: locked[next[3]] = 0; goto NCS; 

fi;
p10: if
:: next[3]!=255; goto p13; 

fi;
}

proctype P_4() { 
byte pred=0;

NCS: if
:: next[4] = 255; goto p2; 

fi;
p2: if
::  d_step {pred = tail;tail = 4;}  goto p3; 

fi;
p3: if
:: pred==255; goto CS; 

:: pred!=255; goto p4; 

fi;
p4: if
:: locked[4] = 1; goto p5; 

fi;
p5: if
:: next[pred] = 4; goto p6; 

fi;
p6: if
:: locked[4]==0; goto CS; 

fi;
CS: if
:: next[4]==255; goto p9; 

:: next[4]!=255; goto p13; 

fi;
p9: if
::  d_step {tail==4;tail = 255;}  goto NCS; 

:: tail!=4; goto p10; 

fi;
p13: if
:: locked[next[4]] = 0; goto NCS; 

fi;
p10: if
:: next[4]!=255; goto p13; 

fi;
}

