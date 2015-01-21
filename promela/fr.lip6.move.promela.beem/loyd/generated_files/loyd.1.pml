byte a[6];
byte x=0;
byte y=0;


init { 
 d_step { 
a[0] =0; a[1] =1; a[2] =2; a[3] =3; a[4] =4; a[5] =5; }
atomic { 
run P();
run Check();
} }

proctype P() { 

q: if
::  d_step {x>0;a[(y)*2+x] = a[(y)*2+x-1];a[(y)*2+x-1] = 0;x = x-1;}  goto q; 

::  d_step {x<2-1;a[(y)*2+x] = a[(y)*2+x+1];a[(y)*2+x+1] = 0;x = x+1;}  goto q; 

::  d_step {y>0;a[(y)*2+x] = a[(y-1)*2+x];a[(y-1)*2+x] = 0;y = y-1;}  goto q; 

::  d_step {y<3-1;a[(y)*2+x] = a[(y+1)*2+x];a[(y+1)*2+x] = 0;y = y+1;}  goto q; 

fi;
}

proctype Check() { 

not_done: if
:: a[0]==5 && a[1]==4 && a[2]==3 && a[3]==2 && a[4]==1 && a[5]==0; goto done; 

fi;
done: 
 false; }

