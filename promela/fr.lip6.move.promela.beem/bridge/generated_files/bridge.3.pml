byte total_time=0;
byte where_is_torch=0;
byte on_right=0;

chan wanna_go =[0] of {int};
chan lets_go =[0] of {int};

active proctype torch() { 
byte time1=0;
byte time2=0;

free: if
:: wanna_go?time1; goto one; 

fi;
one: if
:: wanna_go?time2; goto two; 

::  goto going; 

fi;
two: if
::  atomic {time1>=time2;lets_go!0;}  goto going; 

::  atomic {time1<time2;lets_go!0;time1 = time2;time2 = 0;}  goto going; 

fi;
going: if
::  atomic {total_time+time1<=200;lets_go!0;total_time = total_time+time1;time1 = 0;where_is_torch = 1-where_is_torch;}  goto free; 

fi;
}

active proctype soldier_1() { 

left: if
::  atomic {where_is_torch==0;wanna_go!5;}  goto go_right; 

fi;
go_right: if
::  atomic {lets_go?0;on_right = on_right+1;}  goto right; 

fi;
right: if
::  atomic {where_is_torch==1;wanna_go!5;on_right = on_right-1;}  goto go_left; 

fi;
go_left: if
:: lets_go?0; goto left; 

fi;
}

active proctype soldier_2() { 

left: if
::  atomic {where_is_torch==0;wanna_go!10;}  goto go_right; 

fi;
go_right: if
::  atomic {lets_go?0;on_right = on_right+1;}  goto right; 

fi;
right: if
::  atomic {where_is_torch==1;wanna_go!10;on_right = on_right-1;}  goto go_left; 

fi;
go_left: if
:: lets_go?0; goto left; 

fi;
}

active proctype soldier_3() { 

left: if
::  atomic {where_is_torch==0;wanna_go!20;}  goto go_right; 

fi;
go_right: if
::  atomic {lets_go?0;on_right = on_right+1;}  goto right; 

fi;
right: if
::  atomic {where_is_torch==1;wanna_go!20;on_right = on_right-1;}  goto go_left; 

fi;
go_left: if
:: lets_go?0; goto left; 

fi;
}

active proctype soldier_4() { 

left: if
::  atomic {where_is_torch==0;wanna_go!25;}  goto go_right; 

fi;
go_right: if
::  atomic {lets_go?0;on_right = on_right+1;}  goto right; 

fi;
right: if
::  atomic {where_is_torch==1;wanna_go!25;on_right = on_right-1;}  goto go_left; 

fi;
go_left: if
:: lets_go?0; goto left; 

fi;
}

active proctype soldier_5() { 

left: if
::  atomic {where_is_torch==0;wanna_go!30;}  goto go_right; 

fi;
go_right: if
::  atomic {lets_go?0;on_right = on_right+1;}  goto right; 

fi;
right: if
::  atomic {where_is_torch==1;wanna_go!30;on_right = on_right-1;}  goto go_left; 

fi;
go_left: if
:: lets_go?0; goto left; 

fi;
}

active proctype soldier_6() { 

left: if
::  atomic {where_is_torch==0;wanna_go!30;}  goto go_right; 

fi;
go_right: if
::  atomic {lets_go?0;on_right = on_right+1;}  goto right; 

fi;
right: if
::  atomic {where_is_torch==1;wanna_go!30;on_right = on_right-1;}  goto go_left; 

fi;
go_left: if
:: lets_go?0; goto left; 

fi;
}

active proctype soldier_7() { 

left: if
::  atomic {where_is_torch==0;wanna_go!40;}  goto go_right; 

fi;
go_right: if
::  atomic {lets_go?0;on_right = on_right+1;}  goto right; 

fi;
right: if
::  atomic {where_is_torch==1;wanna_go!40;on_right = on_right-1;}  goto go_left; 

fi;
go_left: if
:: lets_go?0; goto left; 

fi;
}

active proctype soldier_8() { 

left: if
::  atomic {where_is_torch==0;wanna_go!45;}  goto go_right; 

fi;
go_right: if
::  atomic {lets_go?0;on_right = on_right+1;}  goto right; 

fi;
right: if
::  atomic {where_is_torch==1;wanna_go!45;on_right = on_right-1;}  goto go_left; 

fi;
go_left: if
:: lets_go?0; goto left; 

fi;
}

