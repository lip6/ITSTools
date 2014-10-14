byte a[15];
byte x=2;
byte y=1;


init { 
 d_step { 
a[0] =1; a[1] =1; a[2] =1; a[3] =1; a[4] =1; a[5] =1; a[6] =1; a[7] =0; a[8] =2; a[9] =2; a[10] =2; a[11] =2; a[12] =2; a[13] =2; a[14] =2; }
atomic { 
run Toad();
run Frog();
run Check();
} }

proctype Toad() { 

q: if
::  d_step {x>0 && a[((y)*5+x-1)]==1;a[((y)*5+x)] = 1;a[((y)*5+x-1)] = 0;x = x-1;}  goto q; 

::  d_step {y>0 && a[((y-1)*5+x)]==1;a[((y)*5+x)] = 1;a[((y-1)*5+x)] = 0;y = y-1;}  goto q; 

::  d_step {x>1 && a[((y)*5+x-1)]==2 && a[((y)*5+x-2)]==1;a[((y)*5+x)] = 1;a[((y)*5+x-2)] = 0;x = x-2;}  goto q; 

::  d_step {y>1 && a[((y-1)*5+x)]==2 && a[((y-2)*5+x)]==1;a[((y)*5+x)] = 1;a[((y-2)*5+x)] = 0;y = y-2;}  goto q; 

fi;
}

proctype Frog() { 

q: if
::  d_step {x<5-1 && a[((y)*5+x+1)]==2;a[((y)*5+x)] = 2;a[((y)*5+x+1)] = 0;x = x+1;}  goto q; 

::  d_step {y<3-1 && a[((y+1)*5+x)]==2;a[((y)*5+x)] = 2;a[((y+1)*5+x)] = 0;y = y+1;}  goto q; 

::  d_step {x<5-2 && a[((y)*5+x+1)]==1 && a[((y)*5+x+2)]==2;a[((y)*5+x)] = 2;a[((y)*5+x+2)] = 0;x = x+2;}  goto q; 

::  d_step {y<3-2 && a[((y+1)*5+x)]==1 && a[((y+2)*5+x)]==2;a[((y)*5+x)] = 2;a[((y+2)*5+x)] = 0;y = y+2;}  goto q; 

fi;
}

proctype Check() { 

not_done: if
:: a[0]==2 && a[1]==2 && a[2]==2 && a[3]==2 && a[4]==2 && a[5]==2 && a[6]==2 && a[8]==1 && a[9]==1 && a[10]==1 && a[11]==1 && a[12]==1 && a[13]==1 && a[14]==1; goto done; 

fi;
done: 
 false; }

