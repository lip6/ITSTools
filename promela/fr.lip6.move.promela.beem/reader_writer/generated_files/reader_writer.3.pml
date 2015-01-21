byte actvR=0;

chan start_read =[0] of {int};
chan stop_read =[0] of {int};
chan start_write =[0] of {int};
chan stop_write =[0] of {int};

active proctype reader_0() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_1() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_2() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_3() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_4() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_5() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_6() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_7() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_8() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_9() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_10() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_11() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_12() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype reader_13() { 

idle: if
:: start_read!0; goto reading; 

fi;
reading: if
:: stop_read!0; goto idle; 

fi;
}

active proctype writer_0() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_1() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_2() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_3() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_4() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_5() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_6() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_7() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_8() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_9() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_10() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_11() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_12() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype writer_13() { 

idle: if
:: start_write!0; goto writing; 

fi;
writing: if
:: stop_write!0; goto idle; 

fi;
}

active proctype control() { 

ready: if
::  atomic {start_read?0;actvR = 1;}  goto readers_actv; 

:: start_write?0; goto writer_actv; 

fi;
readers_actv: if
::  atomic {start_read?0;actvR = actvR+1;}  goto readers_actv; 

::  atomic {actvR>0;stop_read?0;actvR = 1;}  goto readers_actv; 

::  atomic {actvR==1;stop_read?0;actvR = 0;}  goto ready; 

:: stop_write?0; goto q_error; 

fi;
writer_actv: if
:: stop_write?0; goto ready; 

:: stop_read?0; goto q_error; 

fi;
q_error: 
 false; }

