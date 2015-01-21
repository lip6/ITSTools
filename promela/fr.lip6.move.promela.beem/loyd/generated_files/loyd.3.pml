byte a[12];
byte x=0;
byte y=0;


init { 
 d_step { 
a[0] =0; a[1] =1; a[2] =2; a[3] =3; a[4] =4; a[5] =5; a[6] =6; a[7] =7; a[8] =8; a[9] =9; a[10] =10; a[11] =11; }
atomic { 
run P();
run Check();
} }

proctype P() { 

q: if
::  d_step {x>0;a[(y)*4+x] = a[(y)*4+x-1];a[(y)*4+x-1] = 0;x = x-1;}  goto q; 

::  d_step {x<4-1;a[(y)*4+x] = a[(y)*4+x+1];a[(y)*4+x+1] = 0;x = x+1;}  goto q; 

::  d_step {y>0;a[(y)*4+x] = a[(y-1)*4+x];a[(y-1)*4+x] = 0;y = y-1;}  goto q; 

::  d_step {y<3-1;a[(y)*4+x] = a[(y+1)*4+x];a[(y+1)*4+x] = 0;y = y+1;}  goto q; 

fi;
}

proctype Check() { 

not_done: if
:: a[0]==11 && a[1]==10 && a[2]==9 && a[3]==8 && a[4]==7 && a[5]==6 && a[6]==5 && a[7]==4 && a[8]==3 && a[9]==2 && a[10]==1 && a[11]==0; goto done; 

fi;
done: 
 false; }

