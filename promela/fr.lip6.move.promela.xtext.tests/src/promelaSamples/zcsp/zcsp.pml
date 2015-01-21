#define MaxM 3
#define MaxP 7
#define MaxR 4
#define SizeM 2
#define SizeP 4
#define SizeR 3
#define Ceil 2 
#define ChanSize 6

typedef Message_Entry
{
unsigned Size : 2 ;
unsigned First : SizeP;
unsigned R : SizeR;
bit A;
};

typedef Ack
{
byte No_Mess ;
byte R ;
};

typedef Packet
{
byte No_Mess ;
byte Sqn ;
byte R ;
bit Last; bit Bis; bit First;
};

unsigned EldP : SizeM;
unsigned FF : SizeM;
unsigned FT : SizeM;
unsigned Sended : SizeM;
bit ToBis;
//int _PSender,_PReceiver,_PUpdate;
bit SProgress;

Message_Entry Table[MaxM];

chan StoR =[ChanSize] of { Packet };
chan RtoU =[2] of { Ack };

#define IncPacket(pa) {\
pa = (pa+1)%MaxP;\
}

#define IncMessage(m) {\
m = (m+1)%MaxM;\
}

#define IncR(r) {\
r = (r+1)%MaxM;\
r=((r>Ceil);Ceil+1 : r);\
}

#define IncFT(ft) {\
  do\
    ::((((ft+1)%MaxM)!=FF)&&(ft!=FF)&&(ft!=EldP)) ->\
         ft = (ft+1)%MaxM;\
         if\
           ::(Table[ft].A) ->\
           ::else -> break;\
         fi;\
    ::((((ft+1)%MaxM)==FF)||(ft==FF)||(ft==EldP)) -> break;\
  od;\
}

#define Transmit(o,c) {\
if\
::(1) -> c!o;\
::(1) -> if\
         ::(o.R>=Ceil) -> c!o;\
         ::else ->\
         fi;\
fi;\
}

#define SendMessage(m,pa) {\
atomic{\
pa.No_Mess = m;\
pa.Sqn = Table[m].First;\
pa.First = 1;\
pa.R = Table[m].R;\
pa.Bis = (Table[m].R!=0);\
pa.Last = 0;}\
if\
  ::(Table[m].Size==1)-> atomic{pa.Last = 1;Transmit(pa, StoR);}\
  ::(Table[m].Size==2)-> Transmit(pa, StoR); atomic{pa.First = 0; IncPacket(pa.Sqn); pa.Last = 1;}; Transmit(pa, StoR)\
  ::(Table[m].Size==3)-> Transmit(pa, StoR); atomic{pa.First = 0; IncPacket(pa.Sqn);};Transmit(pa, StoR); atomic{pa.Last = 1; IncPacket(pa.Sqn);}; Transmit(pa, StoR)\
fi;\
}

#define ResetMessage(m) {\
Table[m].Size = 0;\
Table[m].First = 0;\
Table[m].R = 0;\
Table[m].A = 0;\
}

active[1] proctype Sender ()
{
//xs StoR;
unsigned N : SizeP;
unsigned Bis : SizeM;
Packet p;
//_PSender=_pid;
ftEldP :
  if
    ::(EldP!=FT) -> atomic{Bis=EldP; goto SendBis;}
    ::((EldP==FT)&&(((FF+1)%MaxM)!=(EldP%MaxM))) -> goto progress_Send;
  fi;
progress_Send :
  //SProgress =1;
  //SProgress =0;
  atomic {
    Table[FF].First = N;
    if
      ::(1)-> Table[FF].Size = 1;
      ::(1)-> Table[FF].Size = 2;
      ::(1)-> Table[FF].Size = 3;
    fi;
  }
  SendMessage(FF,p);
  atomic {
    Sended ++;
    N = (Table[FF].First+Table[FF].Size)%MaxP;
    IncMessage(FF);
    ToBis = 0;
    goto ftEldP;
  }
SendBis :
  IncR(Table[Bis].R);
  if
     ::(Table[Bis].R<=Ceil) -> SendMessage(Bis,p);
     ::else ->
  fi;
  goto TOBis;
TOBis :
  if
    ::(ToBis) -> atomic {ToBis=0;goto SendBis;}
    ::else -> goto AckBis;
  fi;
AckBis :
  if
    ::(EldP!=Bis) -> goto ftEldP;
    ::else -> goto TOBis;
  fi;
}

active[1] proctype Update ()
{
//xr RtoU;
Ack a;
//_PUpdate=_pid;
Select :
  if
    ::( (((FF+1)%MaxM)!=EldP) /*&& ( (((FT+1)%MaxM)!=FF)||(ToBis==0))*/ )
                 -> goto FTFF;
    ::RtoU?[_] -> goto GoodAck;
  fi;
GoodAck :
  atomic {
    RtoU?a;
    if
      ::((Table[a.No_Mess].R==a.R)&&(Table[a.No_Mess].A==0)) -> goto SeeAck;
      ::else -> goto Select;
    fi;
  }
SeeAck :
  atomic {
    Table[a.No_Mess].A = 1;
    if
      ::(FT==a.No_Mess) -> IncFT(FT);
      ::else -> 
    fi;
    goto AckEldP;
  }
AckEldP :
  if
    ::(EldP==a.No_Mess) -> goto ResetEldP;
    ::else -> goto Select;
  fi;
ResetEldP :
  atomic{
    ResetMessage(EldP);
    Sended --;
    IncMessage(EldP);
    goto Select;
  }
FTFF :
  if
    ::(((FT+1)%MaxM)!=FF)->atomic{IncFT(FT); goto Select;}
    ::((((FT+1)%MaxM)==FF)/*&&(ToBis==0)*/) -> atomic{ToBis = 1; goto Select;}
  fi;
}

active[1] proctype Receiver ()
{
unsigned n : SizeP;
unsigned b : SizeP;
Ack a;
n=0; b=0;
//_PReceiver = _pid;
Packet p;
Recept :
StoR?p;
goto bis;
bis :
if
::(p.Bis)->goto bfirst;
::else -> goto np;
fi;
np :
if
::(n==p.Sqn)-> IncPacket(n);goto last;
::else -> goto Recept;
fi;
last :
if
::(p.Last) -> goto AckER;
::else -> goto Recept;
fi;
bfirst :
if
::(p.First) -> b = p.Sqn; goto bib;
::else -> goto bnp;
fi;
bnp :
if
::(b==p.Sqn) -> goto bib;
::else -> goto Recept;
fi;
bib :
IncPacket(b);
goto bfnp;
bfnp :
if
::(n==p.Sqn) -> atomic{IncPacket(n); goto blast;}
::else -> goto bflast;
fi;
blast :
if
::(p.Last) -> goto AckER;
::else -> goto Recept;
fi;
bflast :
if
::(p.Last) -> goto AckE;
::else -> goto Recept;
fi;
AckER :
//printf("AckR :%d\n",p.No_Mess);
goto AckE;
AckE :
//printf("AckE :%d\n",p.No_Mess);
atomic{
a.No_Mess = p.No_Mess;
a.R= p.R;
Transmit(a,RtoU);}
goto Recept;
}
