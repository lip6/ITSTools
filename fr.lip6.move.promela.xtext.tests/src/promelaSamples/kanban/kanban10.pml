/* Generated File Kanban System with  10 pieces*/


        int Todo1;
        int Made1;
        int Out1;
        int Back1;
        
        int Todo2;
        int Made2;
        int Out2;
        int Back2;
        
        int Todo3;
        int Made3;
        int Out3;
        int Back3;
        
        int Todo4;
        int Made4;
        int Out4;
        int Back4;
        
            chan f21 = [1] of {int};
            
            chan f31 = [1] of {int};
            
            chan f42 = [1] of {int};
            
            chan f43 = [1] of {int};
            
active[1] proctype Make4() {
int t;
begin:
(Todo4>0);

atomic{
Todo4--;
Made4++;
goto begin;}
}
active[1] proctype Select4() {
begin:
atomic{
(Made4>0);
Made4--;
if
::(1)-> Out4++;
::(1)-> Back4++;
fi;
goto begin;}
}
active[1] proctype Redo4() {
begin:
atomic{
(Back4>0);
Back4--;
Made4++;
goto begin;}
}
active[1] proctype Deliver4() {
begin:
(Out4>0);
f43!0;f42!0;
atomic{
Out4--;
Todo4++;
goto begin;}
}

active[1] proctype Make3() {
int t;
begin:
(Todo3>0);
f43?t;
atomic{
Todo3--;
Made3++;
goto begin;}
}
active[1] proctype Select3() {
begin:
atomic{
(Made3>0);
Made3--;
if
::(1)-> Out3++;
::(1)-> Back3++;
fi;
goto begin;}
}
active[1] proctype Redo3() {
begin:
atomic{
(Back3>0);
Back3--;
Made3++;
goto begin;}
}
active[1] proctype Deliver3() {
begin:
(Out3>0);
f31!0;
atomic{
Out3--;
Todo3++;
goto begin;}
}

active[1] proctype Make2() {
int t;
begin:
(Todo2>0);
f42?t;
atomic{
Todo2--;
Made2++;
goto begin;}
}
active[1] proctype Select2() {
begin:
atomic{
(Made2>0);
Made2--;
if
::(1)-> Out2++;
::(1)-> Back2++;
fi;
goto begin;}
}
active[1] proctype Redo2() {
begin:
atomic{
(Back2>0);
Back2--;
Made2++;
goto begin;}
}
active[1] proctype Deliver2() {
begin:
(Out2>0);
f21!0;
atomic{
Out2--;
Todo2++;
goto begin;}
}

active[1] proctype Make1() {
int t;
begin:
(Todo1>0);
f31?t;f21?t;
atomic{
Todo1--;
Made1++;
goto begin;}
}
active[1] proctype Select1() {
begin:
atomic{
(Made1>0);
Made1--;
if
::(1)-> Out1++;
::(1)-> Back1++;
fi;
goto begin;}
}
active[1] proctype Redo1() {
begin:
atomic{
(Back1>0);
Back1--;
Made1++;
goto begin;}
}
active[1] proctype Deliver1() {
begin:
(Out1>0);

atomic{
Out1--;
Todo1++;
goto begin;}
}
init {atomic{
            Todo1=10;
            Todo2=10;
            Todo3=10;
            Todo4=10;}
            }