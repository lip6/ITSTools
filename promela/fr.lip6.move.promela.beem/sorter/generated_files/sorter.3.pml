byte token=0;
byte belt1_moving=1;
byte belt2_moving=0;
byte arm_kicking=0;
byte light_sensor_level=0;
byte button_pressed=0;
byte put_short_brick=0;
byte put_long_brick=0;
byte requests=0;
byte timer=0;
byte brick=0;


active proctype ButtonControler() { 

BC: if
::  d_step {token==0 && button_pressed==0;token = 1;}  goto BC; 

::  d_step {token==0 && button_pressed==1 && requests<5;button_pressed = 0;requests = requests+1;token = 1;}  goto BC; 

::  d_step {token==0 && button_pressed==1 && requests==5;button_pressed = 0;token = 1;}  goto BC; 

fi;
}

active proctype ArmControler() { 

AC0: if
::  d_step {token==1 &&  ! (brick==4 && timer>3);token = 2;}  goto AC0; 

::  d_step {token==1 && brick==4 && timer>3;brick = 0;arm_kicking = 1;belt1_moving = 0;token = 2;}  goto AC1; 

fi;
AC1: if
::  d_step {token==1;arm_kicking = 0;belt1_moving = 1;token = 2;}  goto AC0; 

fi;
}

active proctype Belt2Controler() { 
byte t1=0;

B2C0: if
::  d_step {token==2 &&  ! (brick==3 && timer>7);token = 3;}  goto B2C0; 

::  d_step {token==2 && (brick==3 && timer>7);t1 = 0;}  goto tmp; 

fi;
tmp: if
::  d_step {requests>0;belt2_moving = 2;token = 3;}  goto B2C1; 

::  d_step {requests==0;belt2_moving = 1;token = 3;}  goto B2C1; 

fi;
B2C1: if
::  d_step {token==2 && t1<4;t1 = t1+1;token = 3;}  goto B2C1; 

::  d_step {token==2 && t1==4;token = 3;}  goto B2C0; 

fi;
}

active proctype LightControler() { 
byte x=0;

LC0: if
::  d_step {token==3 && light_sensor_level==0;token = 4;}  goto LC0; 

::  d_step {token==3 && light_sensor_level==1;x = 1;token = 4;}  goto LC1; 

fi;
tmp: if
::  d_step {x<=2;brick = 3;token = 4;}  goto LC0; 

::  d_step {x>2;brick = 4;token = 4;}  goto LC0; 

fi;
LC1: if
::  d_step {token==3 && light_sensor_level==1;x = x+1;token = 4;}  goto LC1; 

::  d_step {token==3 && light_sensor_level==0;timer = 0;}  goto tmp; 

fi;
}

active proctype TimerStep() { 

T0: if
::  d_step {token==4 && timer<8;timer = timer+1;token = 5;}  goto T0; 

::  d_step {token==4 && timer==8;token = 5;}  goto T0; 

fi;
}

active proctype User() { 
byte t2=0;

U0: if
::  d_step {token==5;token = 6;}  goto U0; 

::  d_step {token==5;button_pressed = 1;token = 6;}  goto U0; 

::  d_step {token==5;put_short_brick = 1;t2 = 1;token = 6;}  goto Wait; 

::  d_step {token==5;put_long_brick = 1;t2 = 3;token = 6;}  goto Wait; 

fi;
Wait: if
::  d_step {token==5 && t2>0;t2 = t2-1;token = 6;}  goto Wait; 

::  d_step {token==5 && t2==0;token = 6;}  goto U0; 

fi;
}

active proctype ShortBrick1() { 
byte location=0;
byte position=0;

B: if
::  d_step {token==6 && location==0 && put_short_brick==0;token = 7;}  goto B; 

::  d_step {token==6 && location==0 && put_short_brick==1;put_short_brick = 0;location = 1;token = 7;}  goto B; 

::  d_step {token==6 && location==1 && (position<3 || position==4 || (position>5 && position<10)) && belt1_moving==1;position = position+1;token = 7;}  goto B; 

::  d_step {token==6 && location==1 && belt1_moving==1 && position==3;light_sensor_level = 1;position = position+1;token = 7;}  goto B; 

::  d_step {token==6 && location==1 && belt1_moving==1 && position==5;light_sensor_level = 0;position = position+1;token = 7;}  goto B; 

::  d_step {token==6 && location==1 && position==10 && arm_kicking==0 && belt1_moving==1;position = position+1;token = 7;}  goto B; 

::  d_step {token==6 && location==1 && position==11 && arm_kicking==0 && belt1_moving==1;location = 2;position = 3;token = 7;}  goto B; 

::  d_step {token==6 && location==1 && belt1_moving==0 && position<10;token = 7;}  goto B; 

::  d_step {token==6 && location==1 && belt1_moving==0 && (position==10 || position==11) && arm_kicking==0;token = 7;}  goto B; 

::  d_step {token==6 && location==1 && (position==10 || position==11) && arm_kicking==1;location = 3;token = 7;}  goto B; 

::  d_step {token==6 && location==2 && belt2_moving==1 && position<5;position = position+1;token = 7;}  goto B; 

::  d_step {token==6 && location==2 && belt2_moving==2 && position>0;position = position-1;token = 7;}  goto B; 

::  d_step {token==6 && location==2 && belt2_moving==0;token = 7;}  goto B; 

::  d_step {token==6 && location==2 && belt2_moving==1 && position==5;location = 4;token = 7;}  goto B; 

::  d_step {token==6 && location==2 && belt2_moving==2 && position==0;location = 5;token = 7;}  goto B; 

::  d_step {location==5 || location==4 || location==3;token = 7;}  goto B; 

fi;
}

active proctype ShortBrick2() { 
byte location=0;
byte position=0;

B: if
::  d_step {token==7 && location==0 && put_short_brick==0;token = 0;}  goto B; 

::  d_step {token==7 && location==0 && put_short_brick==1;put_short_brick = 0;location = 1;token = 0;}  goto B; 

::  d_step {token==7 && location==1 && (position<3 || position==4 || (position>5 && position<10)) && belt1_moving==1;position = position+1;token = 0;}  goto B; 

::  d_step {token==7 && location==1 && belt1_moving==1 && position==3;light_sensor_level = 1;position = position+1;token = 0;}  goto B; 

::  d_step {token==7 && location==1 && belt1_moving==1 && position==5;light_sensor_level = 0;position = position+1;token = 0;}  goto B; 

::  d_step {token==7 && location==1 && position==10 && arm_kicking==0 && belt1_moving==1;position = position+1;token = 0;}  goto B; 

::  d_step {token==7 && location==1 && position==11 && arm_kicking==0 && belt1_moving==1;location = 2;position = 3;token = 0;}  goto B; 

::  d_step {token==7 && location==1 && belt1_moving==0 && position<10;token = 0;}  goto B; 

::  d_step {token==7 && location==1 && belt1_moving==0 && (position==10 || position==11) && arm_kicking==0;token = 0;}  goto B; 

::  d_step {token==7 && location==1 && (position==10 || position==11) && arm_kicking==1;location = 3;token = 0;}  goto B; 

::  d_step {token==7 && location==2 && belt2_moving==1 && position<5;position = position+1;token = 0;}  goto B; 

::  d_step {token==7 && location==2 && belt2_moving==2 && position>0;position = position-1;token = 0;}  goto B; 

::  d_step {token==7 && location==2 && belt2_moving==0;token = 0;}  goto B; 

::  d_step {token==7 && location==2 && belt2_moving==1 && position==5;location = 4;token = 0;}  goto B; 

::  d_step {token==7 && location==2 && belt2_moving==2 && position==0;location = 5;token = 0;}  goto B; 

::  d_step {location==5 || location==4 || location==3;token = 0;}  goto B; 

fi;
}

