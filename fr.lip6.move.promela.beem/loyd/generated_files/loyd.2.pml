byte a[9];
byte x=0;
byte y=0;


init { 
 d_step { 
a[0] =0; a[1] =1; a[2] =2; a[3] =3; a[4] =4; a[5] =5; a[6] =6; a[7] =7; a[8] =8; }
atomic { 
run P();
run Check();
} }

proctype P() { 

q: if
::  d_step {x>0;a[(y)*3+x] = a[(y)*3+x-1];a[(y)*3+x-1] = 0;x = x-1;}  goto q; 

::  d_step {x<3-1;a[(y)*3+x] = a[(y)*3+x+1];a[(y)*3+x+1] = 0;x = x+1;}  goto q; 

::  d_step {y>0;a[(y)*3+x] = a[(y-1)*3+x];a[(y-1)*3+x] = 0;y = y-1;}  goto q; 

::  d_step {y<3-1;a[(y)*3+x] = a[(y+1)*3+x];a[(y+1)*3+x] = 0;y = y+1;}  goto q; 

fi;
}

proctype Check() { 

not_done: if
:: a[0]==8 && a[1]==7 && a[2]==6 && a[3]==5 && a[4]==4 && a[5]==3 && a[6]==2 && a[7]==1 && a[8]==0; goto done; 

fi;
done: 
 false; }

