/*Spin 12 routers*/

#define DEBUT 1
#define INTER 0
#define FIN 2
#define REQUETE 0
#define REPONSE 1
#define NB_MOT 2

typedef Mot {
int source;
int dest;
int tag;
int type;
};

typedef Port {
    int redirect;
    bit lock;
    Mot mess;
    bit toforward;
    chan in =[1] of {Mot};
};

typedef Router {
    Port p00;
    Port p01;
    Port p10;
    Port p11;
};

typedef Pair {
    Router l;
    Router r;
};

typedef SubNet {
    Pair u;
    Pair d;
};

typedef Inter {
    Port i00;
    Port i01;
    Port i10;
    Port i11;
};

Pair L3l;
Pair L3r;
SubNet Sub0;
SubNet Sub1;
Inter I0;
Inter I1;

bit Ack0;
bit Ack2;
bit Ack4;
bit Ack6;
active[1] proctype Envoi0() {
Mot mot;
int num=1;
mot.source = 0;
mot.type = REQUETE;
selectDest :

    mot.dest = 1;
  /*  do
    ::(mot.dest<=7); break;
    ::(mot.dest<7); mot.dest=mot.dest+2;
    od;*/
    mot.dest=1;
    sendMot:
    do
    ::atomic{ (num==1); mot.tag=DEBUT; Sub0.d.l.p00.in!mot;num++;}
    ::atomic{ (num==7); mot.tag=FIN; Sub0.d.l.p00.in!mot; num=1;break;}
    ::atomic{ (num>1); mot.tag=FIN; Sub0.d.l.p00.in!mot; num=1;break;}
    //::atomic{((num>1)&&(num<7)); mot.tag=INTER; Sub0.d.l.p00.in!mot; num++;}
    od;
waitSync:
    atomic {
        (Ack0==1);
        Ack0=0;
        goto selectDest;
        }
   }
   active[1] proctype Reception0() {
Mot mot;
   Receive :
   I0.i00.in?mot;
   if
   ::(mot.tag==FIN); Ack0=1; goto Receive;
   ::else -> goto Receive;
   fi;
   }
   active[1] proctype Envoi2() {
Mot mot;
int num=1;
mot.source = 2;
mot.type = REQUETE;
selectDest :

    mot.dest = 1;
  /*  do
    ::(mot.dest<=7); break;
    ::(mot.dest<7); mot.dest=mot.dest+2;
    od;*/
    mot.dest=1;
    sendMot:
    do
    ::atomic{ (num==1); mot.tag=DEBUT; Sub0.d.r.p00.in!mot;num++;}
    ::atomic{ (num==7); mot.tag=FIN; Sub0.d.r.p00.in!mot; num=1;break;}
    ::atomic{ (num>1); mot.tag=FIN; Sub0.d.r.p00.in!mot; num=1;break;}
    //::atomic{((num>1)&&(num<7)); mot.tag=INTER; Sub0.d.r.p00.in!mot; num++;}
    od;
waitSync:
    atomic {
        (Ack2==1);
        Ack2=0;
        goto selectDest;
        }
   }
   active[1] proctype Reception2() {
Mot mot;
   Receive :
   I0.i10.in?mot;
   if
   ::(mot.tag==FIN); Ack2=1; goto Receive;
   ::else -> goto Receive;
   fi;
   }
   active[1] proctype Envoi4() {
Mot mot;
int num=1;
mot.source = 4;
mot.type = REQUETE;
selectDest :

    mot.dest = 1;
  /*  do
    ::(mot.dest<=7); break;
    ::(mot.dest<7); mot.dest=mot.dest+2;
    od;*/
    mot.dest=1;
    sendMot:
    do
    ::atomic{ (num==1); mot.tag=DEBUT; Sub1.d.l.p00.in!mot;num++;}
    ::atomic{ (num==7); mot.tag=FIN; Sub1.d.l.p00.in!mot; num=1;break;}
    ::atomic{ (num>1); mot.tag=FIN; Sub1.d.l.p00.in!mot; num=1;break;}
    //::atomic{((num>1)&&(num<7)); mot.tag=INTER; Sub1.d.l.p00.in!mot; num++;}
    od;
waitSync:
    atomic {
        (Ack4==1);
        Ack4=0;
        goto selectDest;
        }
   }
   active[1] proctype Reception4() {
Mot mot;
   Receive :
   I1.i00.in?mot;
   if
   ::(mot.tag==FIN); Ack4=1; goto Receive;
   ::else -> goto Receive;
   fi;
   }
   active[1] proctype Envoi6() {
Mot mot;
int num=1;
mot.source = 6;
mot.type = REQUETE;
selectDest :

    mot.dest = 1;
  /*  do
    ::(mot.dest<=7); break;
    ::(mot.dest<7); mot.dest=mot.dest+2;
    od;*/
    mot.dest=1;
    sendMot:
    do
    ::atomic{ (num==1); mot.tag=DEBUT; Sub1.d.r.p00.in!mot;num++;}
    ::atomic{ (num==7); mot.tag=FIN; Sub1.d.r.p00.in!mot; num=1;break;}
    ::atomic{ (num>1); mot.tag=FIN; Sub1.d.r.p00.in!mot; num=1;break;}
    //::atomic{((num>1)&&(num<7)); mot.tag=INTER; Sub1.d.r.p00.in!mot; num++;}
    od;
waitSync:
    atomic {
        (Ack6==1);
        Ack6=0;
        goto selectDest;
        }
   }
   active[1] proctype Reception6() {
Mot mot;
   Receive :
   I1.i10.in?mot;
   if
   ::(mot.tag==FIN); Ack6=1; goto Receive;
   ::else -> goto Receive;
   fi;
   }
   active[1] proctype Cible1 () {
            Mot mot;
            int t;
            do
            ::atomic{I0.i01.in?mot; mot.type=REPONSE;t=mot.source; mot.source=mot.dest; mot.dest=t; t=0;Sub0.d.l.p01.in!mot;}
            od;
            }
            active[1] proctype Cible3 () {
            Mot mot;
            int t;
            do
            ::atomic{I0.i11.in?mot; mot.type=REPONSE;t=mot.source; mot.source=mot.dest; mot.dest=t; t=0;Sub0.d.r.p01.in!mot;}
            od;
            }
            active[1] proctype Cible5 () {
            Mot mot;
            int t;
            do
            ::atomic{I1.i01.in?mot; mot.type=REPONSE;t=mot.source; mot.source=mot.dest; mot.dest=t; t=0;Sub1.d.l.p01.in!mot;}
            od;
            }
            active[1] proctype Cible7 () {
            Mot mot;
            int t;
            do
            ::atomic{I1.i11.in?mot; mot.type=REPONSE;t=mot.source; mot.source=mot.dest; mot.dest=t; t=0;Sub1.d.r.p01.in!mot;}
            od;
            }
            active[1] proctype R0P0() {
do
::atomic{((Sub0.d.l.p00.toforward==0)&&(nempty(Sub0.d.l.p00.in))); Sub0.d.l.p00.in?Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=1;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==DEBUT)&&(nfull(I0.i00.in))&&(Sub0.d.l.p00.mess.dest/2==0)&&((Sub0.d.l.p00.mess.dest/1)%2==0)&&(I0.i00.lock==0)); Sub0.d.l.p00.redirect=0; I0.i00.lock=1; I0.i00.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==DEBUT)&&(nfull(I0.i01.in))&&(Sub0.d.l.p00.mess.dest/2==0)&&((Sub0.d.l.p00.mess.dest/1)%2==1)&&(I0.i01.lock==0)); Sub0.d.l.p00.redirect=1; I0.i01.lock=1; I0.i01.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p00.in))&&(Sub0.d.l.p00.mess.dest/2!=0)&&(1/*Mode non Séparé*/)&&(Sub0.u.l.p00.lock==0)); Sub0.d.l.p00.redirect=2; Sub0.u.l.p00.lock=1; Sub0.u.l.p00.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p00.in))&&(Sub0.d.l.p00.mess.dest/2!=0)&&(1/*Mode non Séparé*/)&&(Sub0.u.r.p00.lock==0)); Sub0.d.l.p00.redirect=3; Sub0.u.r.p00.lock=1; Sub0.u.r.p00.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==INTER)&&(Sub0.d.l.p00.redirect==0)&&(nfull(I0.i00.in))); I0.i00.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==INTER)&&(Sub0.d.l.p00.redirect==1)&&(nfull(I0.i01.in))); I0.i01.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==INTER)&&(Sub0.d.l.p00.redirect==2)&&(nfull(Sub0.u.l.p00.in))); Sub0.u.l.p00.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==INTER)&&(Sub0.d.l.p00.redirect==3)&&(nfull(Sub0.u.r.p00.in))); Sub0.u.r.p00.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==FIN)&&(Sub0.d.l.p00.redirect==0)&&(nfull(I0.i00.in))); I0.i00.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;I0.i00.lock=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==FIN)&&(Sub0.d.l.p00.redirect==1)&&(nfull(I0.i01.in))); I0.i01.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;I0.i01.lock=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==FIN)&&(Sub0.d.l.p00.redirect==2)&&(nfull(Sub0.u.l.p00.in))); Sub0.u.l.p00.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;Sub0.u.l.p00.lock=0;}
::atomic{((Sub0.d.l.p00.toforward==1)&&(Sub0.d.l.p00.mess.tag==FIN)&&(Sub0.d.l.p00.redirect==3)&&(nfull(Sub0.u.r.p00.in))); Sub0.u.r.p00.in!Sub0.d.l.p00.mess; Sub0.d.l.p00.toforward=0;Sub0.u.r.p00.lock=0;}
od;

}
active[1] proctype R0P1() {
do
::atomic{((Sub0.d.l.p01.toforward==0)&&(nempty(Sub0.d.l.p01.in))); Sub0.d.l.p01.in?Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=1;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==DEBUT)&&(nfull(I0.i00.in))&&(Sub0.d.l.p01.mess.dest/2==0)&&((Sub0.d.l.p01.mess.dest/1)%2==0)&&(I0.i00.lock==0)); Sub0.d.l.p01.redirect=0; I0.i00.lock=1; I0.i00.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==DEBUT)&&(nfull(I0.i01.in))&&(Sub0.d.l.p01.mess.dest/2==0)&&((Sub0.d.l.p01.mess.dest/1)%2==1)&&(I0.i01.lock==0)); Sub0.d.l.p01.redirect=1; I0.i01.lock=1; I0.i01.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p00.in))&&(Sub0.d.l.p01.mess.dest/2!=0)&&(1/*Mode non Séparé*/)&&(Sub0.u.l.p00.lock==0)); Sub0.d.l.p01.redirect=2; Sub0.u.l.p00.lock=1; Sub0.u.l.p00.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p00.in))&&(Sub0.d.l.p01.mess.dest/2!=0)&&(1/*Mode non Séparé*/)&&(Sub0.u.r.p00.lock==0)); Sub0.d.l.p01.redirect=3; Sub0.u.r.p00.lock=1; Sub0.u.r.p00.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==INTER)&&(Sub0.d.l.p01.redirect==0)&&(nfull(I0.i00.in))); I0.i00.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==INTER)&&(Sub0.d.l.p01.redirect==1)&&(nfull(I0.i01.in))); I0.i01.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==INTER)&&(Sub0.d.l.p01.redirect==2)&&(nfull(Sub0.u.l.p00.in))); Sub0.u.l.p00.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==INTER)&&(Sub0.d.l.p01.redirect==3)&&(nfull(Sub0.u.r.p00.in))); Sub0.u.r.p00.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==FIN)&&(Sub0.d.l.p01.redirect==0)&&(nfull(I0.i00.in))); I0.i00.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;I0.i00.lock=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==FIN)&&(Sub0.d.l.p01.redirect==1)&&(nfull(I0.i01.in))); I0.i01.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;I0.i01.lock=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==FIN)&&(Sub0.d.l.p01.redirect==2)&&(nfull(Sub0.u.l.p00.in))); Sub0.u.l.p00.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;Sub0.u.l.p00.lock=0;}
::atomic{((Sub0.d.l.p01.toforward==1)&&(Sub0.d.l.p01.mess.tag==FIN)&&(Sub0.d.l.p01.redirect==3)&&(nfull(Sub0.u.r.p00.in))); Sub0.u.r.p00.in!Sub0.d.l.p01.mess; Sub0.d.l.p01.toforward=0;Sub0.u.r.p00.lock=0;}
od;

}
active[1] proctype R0P2() {
do
::atomic{((Sub0.d.l.p10.toforward==0)&&(nempty(Sub0.d.l.p10.in))); Sub0.d.l.p10.in?Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=1;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==DEBUT)&&(nfull(I0.i00.in))&&(Sub0.d.l.p10.mess.dest/2==0)&&((Sub0.d.l.p10.mess.dest/1)%2==0)&&(I0.i00.lock==0)); Sub0.d.l.p10.redirect=0; I0.i00.lock=1; I0.i00.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==DEBUT)&&(nfull(I0.i01.in))&&(Sub0.d.l.p10.mess.dest/2==0)&&((Sub0.d.l.p10.mess.dest/1)%2==1)&&(I0.i01.lock==0)); Sub0.d.l.p10.redirect=1; I0.i01.lock=1; I0.i01.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p00.in))&&(Sub0.d.l.p10.mess.dest/2!=0)&&(1/*Mode non Séparé*/)&&(Sub0.u.l.p00.lock==0)); Sub0.d.l.p10.redirect=2; Sub0.u.l.p00.lock=1; Sub0.u.l.p00.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p00.in))&&(Sub0.d.l.p10.mess.dest/2!=0)&&(1/*Mode non Séparé*/)&&(Sub0.u.r.p00.lock==0)); Sub0.d.l.p10.redirect=3; Sub0.u.r.p00.lock=1; Sub0.u.r.p00.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==INTER)&&(Sub0.d.l.p10.redirect==0)&&(nfull(I0.i00.in))); I0.i00.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==INTER)&&(Sub0.d.l.p10.redirect==1)&&(nfull(I0.i01.in))); I0.i01.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==INTER)&&(Sub0.d.l.p10.redirect==2)&&(nfull(Sub0.u.l.p00.in))); Sub0.u.l.p00.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==INTER)&&(Sub0.d.l.p10.redirect==3)&&(nfull(Sub0.u.r.p00.in))); Sub0.u.r.p00.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==FIN)&&(Sub0.d.l.p10.redirect==0)&&(nfull(I0.i00.in))); I0.i00.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;I0.i00.lock=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==FIN)&&(Sub0.d.l.p10.redirect==1)&&(nfull(I0.i01.in))); I0.i01.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;I0.i01.lock=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==FIN)&&(Sub0.d.l.p10.redirect==2)&&(nfull(Sub0.u.l.p00.in))); Sub0.u.l.p00.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;Sub0.u.l.p00.lock=0;}
::atomic{((Sub0.d.l.p10.toforward==1)&&(Sub0.d.l.p10.mess.tag==FIN)&&(Sub0.d.l.p10.redirect==3)&&(nfull(Sub0.u.r.p00.in))); Sub0.u.r.p00.in!Sub0.d.l.p10.mess; Sub0.d.l.p10.toforward=0;Sub0.u.r.p00.lock=0;}
od;

}
active[1] proctype R0P3() {
do
::atomic{((Sub0.d.l.p11.toforward==0)&&(nempty(Sub0.d.l.p11.in))); Sub0.d.l.p11.in?Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=1;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==DEBUT)&&(nfull(I0.i00.in))&&(Sub0.d.l.p11.mess.dest/2==0)&&((Sub0.d.l.p11.mess.dest/1)%2==0)&&(I0.i00.lock==0)); Sub0.d.l.p11.redirect=0; I0.i00.lock=1; I0.i00.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==DEBUT)&&(nfull(I0.i01.in))&&(Sub0.d.l.p11.mess.dest/2==0)&&((Sub0.d.l.p11.mess.dest/1)%2==1)&&(I0.i01.lock==0)); Sub0.d.l.p11.redirect=1; I0.i01.lock=1; I0.i01.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p00.in))&&(Sub0.d.l.p11.mess.dest/2!=0)&&(1/*Mode non Séparé*/)&&(Sub0.u.l.p00.lock==0)); Sub0.d.l.p11.redirect=2; Sub0.u.l.p00.lock=1; Sub0.u.l.p00.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p00.in))&&(Sub0.d.l.p11.mess.dest/2!=0)&&(1/*Mode non Séparé*/)&&(Sub0.u.r.p00.lock==0)); Sub0.d.l.p11.redirect=3; Sub0.u.r.p00.lock=1; Sub0.u.r.p00.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==INTER)&&(Sub0.d.l.p11.redirect==0)&&(nfull(I0.i00.in))); I0.i00.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==INTER)&&(Sub0.d.l.p11.redirect==1)&&(nfull(I0.i01.in))); I0.i01.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==INTER)&&(Sub0.d.l.p11.redirect==2)&&(nfull(Sub0.u.l.p00.in))); Sub0.u.l.p00.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==INTER)&&(Sub0.d.l.p11.redirect==3)&&(nfull(Sub0.u.r.p00.in))); Sub0.u.r.p00.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==FIN)&&(Sub0.d.l.p11.redirect==0)&&(nfull(I0.i00.in))); I0.i00.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;I0.i00.lock=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==FIN)&&(Sub0.d.l.p11.redirect==1)&&(nfull(I0.i01.in))); I0.i01.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;I0.i01.lock=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==FIN)&&(Sub0.d.l.p11.redirect==2)&&(nfull(Sub0.u.l.p00.in))); Sub0.u.l.p00.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;Sub0.u.l.p00.lock=0;}
::atomic{((Sub0.d.l.p11.toforward==1)&&(Sub0.d.l.p11.mess.tag==FIN)&&(Sub0.d.l.p11.redirect==3)&&(nfull(Sub0.u.r.p00.in))); Sub0.u.r.p00.in!Sub0.d.l.p11.mess; Sub0.d.l.p11.toforward=0;Sub0.u.r.p00.lock=0;}
od;

}
active[1] proctype R1P0() {
do
::atomic{((Sub0.d.r.p00.toforward==0)&&(nempty(Sub0.d.r.p00.in))); Sub0.d.r.p00.in?Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=1;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==DEBUT)&&(nfull(I0.i10.in))&&(Sub0.d.r.p00.mess.dest/2==1)&&((Sub0.d.r.p00.mess.dest/1)%2==0)&&(I0.i10.lock==0)); Sub0.d.r.p00.redirect=0; I0.i10.lock=1; I0.i10.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==DEBUT)&&(nfull(I0.i11.in))&&(Sub0.d.r.p00.mess.dest/2==1)&&((Sub0.d.r.p00.mess.dest/1)%2==1)&&(I0.i11.lock==0)); Sub0.d.r.p00.redirect=1; I0.i11.lock=1; I0.i11.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p01.in))&&(Sub0.d.r.p00.mess.dest/2!=1)&&(1/*Mode non Séparé*/)&&(Sub0.u.l.p01.lock==0)); Sub0.d.r.p00.redirect=2; Sub0.u.l.p01.lock=1; Sub0.u.l.p01.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p01.in))&&(Sub0.d.r.p00.mess.dest/2!=1)&&(1/*Mode non Séparé*/)&&(Sub0.u.r.p01.lock==0)); Sub0.d.r.p00.redirect=3; Sub0.u.r.p01.lock=1; Sub0.u.r.p01.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==INTER)&&(Sub0.d.r.p00.redirect==0)&&(nfull(I0.i10.in))); I0.i10.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==INTER)&&(Sub0.d.r.p00.redirect==1)&&(nfull(I0.i11.in))); I0.i11.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==INTER)&&(Sub0.d.r.p00.redirect==2)&&(nfull(Sub0.u.l.p01.in))); Sub0.u.l.p01.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==INTER)&&(Sub0.d.r.p00.redirect==3)&&(nfull(Sub0.u.r.p01.in))); Sub0.u.r.p01.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==FIN)&&(Sub0.d.r.p00.redirect==0)&&(nfull(I0.i10.in))); I0.i10.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;I0.i10.lock=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==FIN)&&(Sub0.d.r.p00.redirect==1)&&(nfull(I0.i11.in))); I0.i11.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;I0.i11.lock=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==FIN)&&(Sub0.d.r.p00.redirect==2)&&(nfull(Sub0.u.l.p01.in))); Sub0.u.l.p01.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;Sub0.u.l.p01.lock=0;}
::atomic{((Sub0.d.r.p00.toforward==1)&&(Sub0.d.r.p00.mess.tag==FIN)&&(Sub0.d.r.p00.redirect==3)&&(nfull(Sub0.u.r.p01.in))); Sub0.u.r.p01.in!Sub0.d.r.p00.mess; Sub0.d.r.p00.toforward=0;Sub0.u.r.p01.lock=0;}
od;

}
active[1] proctype R1P1() {
do
::atomic{((Sub0.d.r.p01.toforward==0)&&(nempty(Sub0.d.r.p01.in))); Sub0.d.r.p01.in?Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=1;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==DEBUT)&&(nfull(I0.i10.in))&&(Sub0.d.r.p01.mess.dest/2==1)&&((Sub0.d.r.p01.mess.dest/1)%2==0)&&(I0.i10.lock==0)); Sub0.d.r.p01.redirect=0; I0.i10.lock=1; I0.i10.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==DEBUT)&&(nfull(I0.i11.in))&&(Sub0.d.r.p01.mess.dest/2==1)&&((Sub0.d.r.p01.mess.dest/1)%2==1)&&(I0.i11.lock==0)); Sub0.d.r.p01.redirect=1; I0.i11.lock=1; I0.i11.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p01.in))&&(Sub0.d.r.p01.mess.dest/2!=1)&&(1/*Mode non Séparé*/)&&(Sub0.u.l.p01.lock==0)); Sub0.d.r.p01.redirect=2; Sub0.u.l.p01.lock=1; Sub0.u.l.p01.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p01.in))&&(Sub0.d.r.p01.mess.dest/2!=1)&&(1/*Mode non Séparé*/)&&(Sub0.u.r.p01.lock==0)); Sub0.d.r.p01.redirect=3; Sub0.u.r.p01.lock=1; Sub0.u.r.p01.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==INTER)&&(Sub0.d.r.p01.redirect==0)&&(nfull(I0.i10.in))); I0.i10.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==INTER)&&(Sub0.d.r.p01.redirect==1)&&(nfull(I0.i11.in))); I0.i11.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==INTER)&&(Sub0.d.r.p01.redirect==2)&&(nfull(Sub0.u.l.p01.in))); Sub0.u.l.p01.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==INTER)&&(Sub0.d.r.p01.redirect==3)&&(nfull(Sub0.u.r.p01.in))); Sub0.u.r.p01.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==FIN)&&(Sub0.d.r.p01.redirect==0)&&(nfull(I0.i10.in))); I0.i10.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;I0.i10.lock=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==FIN)&&(Sub0.d.r.p01.redirect==1)&&(nfull(I0.i11.in))); I0.i11.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;I0.i11.lock=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==FIN)&&(Sub0.d.r.p01.redirect==2)&&(nfull(Sub0.u.l.p01.in))); Sub0.u.l.p01.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;Sub0.u.l.p01.lock=0;}
::atomic{((Sub0.d.r.p01.toforward==1)&&(Sub0.d.r.p01.mess.tag==FIN)&&(Sub0.d.r.p01.redirect==3)&&(nfull(Sub0.u.r.p01.in))); Sub0.u.r.p01.in!Sub0.d.r.p01.mess; Sub0.d.r.p01.toforward=0;Sub0.u.r.p01.lock=0;}
od;

}
active[1] proctype R1P2() {
do
::atomic{((Sub0.d.r.p10.toforward==0)&&(nempty(Sub0.d.r.p10.in))); Sub0.d.r.p10.in?Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=1;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==DEBUT)&&(nfull(I0.i10.in))&&(Sub0.d.r.p10.mess.dest/2==1)&&((Sub0.d.r.p10.mess.dest/1)%2==0)&&(I0.i10.lock==0)); Sub0.d.r.p10.redirect=0; I0.i10.lock=1; I0.i10.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==DEBUT)&&(nfull(I0.i11.in))&&(Sub0.d.r.p10.mess.dest/2==1)&&((Sub0.d.r.p10.mess.dest/1)%2==1)&&(I0.i11.lock==0)); Sub0.d.r.p10.redirect=1; I0.i11.lock=1; I0.i11.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p01.in))&&(Sub0.d.r.p10.mess.dest/2!=1)&&(1/*Mode non Séparé*/)&&(Sub0.u.l.p01.lock==0)); Sub0.d.r.p10.redirect=2; Sub0.u.l.p01.lock=1; Sub0.u.l.p01.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p01.in))&&(Sub0.d.r.p10.mess.dest/2!=1)&&(1/*Mode non Séparé*/)&&(Sub0.u.r.p01.lock==0)); Sub0.d.r.p10.redirect=3; Sub0.u.r.p01.lock=1; Sub0.u.r.p01.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==INTER)&&(Sub0.d.r.p10.redirect==0)&&(nfull(I0.i10.in))); I0.i10.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==INTER)&&(Sub0.d.r.p10.redirect==1)&&(nfull(I0.i11.in))); I0.i11.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==INTER)&&(Sub0.d.r.p10.redirect==2)&&(nfull(Sub0.u.l.p01.in))); Sub0.u.l.p01.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==INTER)&&(Sub0.d.r.p10.redirect==3)&&(nfull(Sub0.u.r.p01.in))); Sub0.u.r.p01.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==FIN)&&(Sub0.d.r.p10.redirect==0)&&(nfull(I0.i10.in))); I0.i10.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;I0.i10.lock=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==FIN)&&(Sub0.d.r.p10.redirect==1)&&(nfull(I0.i11.in))); I0.i11.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;I0.i11.lock=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==FIN)&&(Sub0.d.r.p10.redirect==2)&&(nfull(Sub0.u.l.p01.in))); Sub0.u.l.p01.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;Sub0.u.l.p01.lock=0;}
::atomic{((Sub0.d.r.p10.toforward==1)&&(Sub0.d.r.p10.mess.tag==FIN)&&(Sub0.d.r.p10.redirect==3)&&(nfull(Sub0.u.r.p01.in))); Sub0.u.r.p01.in!Sub0.d.r.p10.mess; Sub0.d.r.p10.toforward=0;Sub0.u.r.p01.lock=0;}
od;

}
active[1] proctype R1P3() {
do
::atomic{((Sub0.d.r.p11.toforward==0)&&(nempty(Sub0.d.r.p11.in))); Sub0.d.r.p11.in?Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=1;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==DEBUT)&&(nfull(I0.i10.in))&&(Sub0.d.r.p11.mess.dest/2==1)&&((Sub0.d.r.p11.mess.dest/1)%2==0)&&(I0.i10.lock==0)); Sub0.d.r.p11.redirect=0; I0.i10.lock=1; I0.i10.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==DEBUT)&&(nfull(I0.i11.in))&&(Sub0.d.r.p11.mess.dest/2==1)&&((Sub0.d.r.p11.mess.dest/1)%2==1)&&(I0.i11.lock==0)); Sub0.d.r.p11.redirect=1; I0.i11.lock=1; I0.i11.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p01.in))&&(Sub0.d.r.p11.mess.dest/2!=1)&&(1/*Mode non Séparé*/)&&(Sub0.u.l.p01.lock==0)); Sub0.d.r.p11.redirect=2; Sub0.u.l.p01.lock=1; Sub0.u.l.p01.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p01.in))&&(Sub0.d.r.p11.mess.dest/2!=1)&&(1/*Mode non Séparé*/)&&(Sub0.u.r.p01.lock==0)); Sub0.d.r.p11.redirect=3; Sub0.u.r.p01.lock=1; Sub0.u.r.p01.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==INTER)&&(Sub0.d.r.p11.redirect==0)&&(nfull(I0.i10.in))); I0.i10.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==INTER)&&(Sub0.d.r.p11.redirect==1)&&(nfull(I0.i11.in))); I0.i11.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==INTER)&&(Sub0.d.r.p11.redirect==2)&&(nfull(Sub0.u.l.p01.in))); Sub0.u.l.p01.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==INTER)&&(Sub0.d.r.p11.redirect==3)&&(nfull(Sub0.u.r.p01.in))); Sub0.u.r.p01.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==FIN)&&(Sub0.d.r.p11.redirect==0)&&(nfull(I0.i10.in))); I0.i10.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;I0.i10.lock=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==FIN)&&(Sub0.d.r.p11.redirect==1)&&(nfull(I0.i11.in))); I0.i11.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;I0.i11.lock=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==FIN)&&(Sub0.d.r.p11.redirect==2)&&(nfull(Sub0.u.l.p01.in))); Sub0.u.l.p01.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;Sub0.u.l.p01.lock=0;}
::atomic{((Sub0.d.r.p11.toforward==1)&&(Sub0.d.r.p11.mess.tag==FIN)&&(Sub0.d.r.p11.redirect==3)&&(nfull(Sub0.u.r.p01.in))); Sub0.u.r.p01.in!Sub0.d.r.p11.mess; Sub0.d.r.p11.toforward=0;Sub0.u.r.p01.lock=0;}
od;

}
active[1] proctype R2P0() {
do
::atomic{((Sub1.d.l.p00.toforward==0)&&(nempty(Sub1.d.l.p00.in))); Sub1.d.l.p00.in?Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=1;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==DEBUT)&&(nfull(I1.i00.in))&&(Sub1.d.l.p00.mess.dest/2==2)&&((Sub1.d.l.p00.mess.dest/1)%2==0)&&(I1.i00.lock==0)); Sub1.d.l.p00.redirect=0; I1.i00.lock=1; I1.i00.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==DEBUT)&&(nfull(I1.i01.in))&&(Sub1.d.l.p00.mess.dest/2==2)&&((Sub1.d.l.p00.mess.dest/1)%2==1)&&(I1.i01.lock==0)); Sub1.d.l.p00.redirect=1; I1.i01.lock=1; I1.i01.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p00.in))&&(Sub1.d.l.p00.mess.dest/2!=2)&&(1/*Mode non Séparé*/)&&(Sub1.u.l.p00.lock==0)); Sub1.d.l.p00.redirect=2; Sub1.u.l.p00.lock=1; Sub1.u.l.p00.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p00.in))&&(Sub1.d.l.p00.mess.dest/2!=2)&&(1/*Mode non Séparé*/)&&(Sub1.u.r.p00.lock==0)); Sub1.d.l.p00.redirect=3; Sub1.u.r.p00.lock=1; Sub1.u.r.p00.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==INTER)&&(Sub1.d.l.p00.redirect==0)&&(nfull(I1.i00.in))); I1.i00.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==INTER)&&(Sub1.d.l.p00.redirect==1)&&(nfull(I1.i01.in))); I1.i01.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==INTER)&&(Sub1.d.l.p00.redirect==2)&&(nfull(Sub1.u.l.p00.in))); Sub1.u.l.p00.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==INTER)&&(Sub1.d.l.p00.redirect==3)&&(nfull(Sub1.u.r.p00.in))); Sub1.u.r.p00.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==FIN)&&(Sub1.d.l.p00.redirect==0)&&(nfull(I1.i00.in))); I1.i00.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;I1.i00.lock=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==FIN)&&(Sub1.d.l.p00.redirect==1)&&(nfull(I1.i01.in))); I1.i01.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;I1.i01.lock=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==FIN)&&(Sub1.d.l.p00.redirect==2)&&(nfull(Sub1.u.l.p00.in))); Sub1.u.l.p00.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;Sub1.u.l.p00.lock=0;}
::atomic{((Sub1.d.l.p00.toforward==1)&&(Sub1.d.l.p00.mess.tag==FIN)&&(Sub1.d.l.p00.redirect==3)&&(nfull(Sub1.u.r.p00.in))); Sub1.u.r.p00.in!Sub1.d.l.p00.mess; Sub1.d.l.p00.toforward=0;Sub1.u.r.p00.lock=0;}
od;

}
active[1] proctype R2P1() {
do
::atomic{((Sub1.d.l.p01.toforward==0)&&(nempty(Sub1.d.l.p01.in))); Sub1.d.l.p01.in?Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=1;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==DEBUT)&&(nfull(I1.i00.in))&&(Sub1.d.l.p01.mess.dest/2==2)&&((Sub1.d.l.p01.mess.dest/1)%2==0)&&(I1.i00.lock==0)); Sub1.d.l.p01.redirect=0; I1.i00.lock=1; I1.i00.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==DEBUT)&&(nfull(I1.i01.in))&&(Sub1.d.l.p01.mess.dest/2==2)&&((Sub1.d.l.p01.mess.dest/1)%2==1)&&(I1.i01.lock==0)); Sub1.d.l.p01.redirect=1; I1.i01.lock=1; I1.i01.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p00.in))&&(Sub1.d.l.p01.mess.dest/2!=2)&&(1/*Mode non Séparé*/)&&(Sub1.u.l.p00.lock==0)); Sub1.d.l.p01.redirect=2; Sub1.u.l.p00.lock=1; Sub1.u.l.p00.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p00.in))&&(Sub1.d.l.p01.mess.dest/2!=2)&&(1/*Mode non Séparé*/)&&(Sub1.u.r.p00.lock==0)); Sub1.d.l.p01.redirect=3; Sub1.u.r.p00.lock=1; Sub1.u.r.p00.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==INTER)&&(Sub1.d.l.p01.redirect==0)&&(nfull(I1.i00.in))); I1.i00.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==INTER)&&(Sub1.d.l.p01.redirect==1)&&(nfull(I1.i01.in))); I1.i01.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==INTER)&&(Sub1.d.l.p01.redirect==2)&&(nfull(Sub1.u.l.p00.in))); Sub1.u.l.p00.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==INTER)&&(Sub1.d.l.p01.redirect==3)&&(nfull(Sub1.u.r.p00.in))); Sub1.u.r.p00.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==FIN)&&(Sub1.d.l.p01.redirect==0)&&(nfull(I1.i00.in))); I1.i00.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;I1.i00.lock=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==FIN)&&(Sub1.d.l.p01.redirect==1)&&(nfull(I1.i01.in))); I1.i01.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;I1.i01.lock=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==FIN)&&(Sub1.d.l.p01.redirect==2)&&(nfull(Sub1.u.l.p00.in))); Sub1.u.l.p00.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;Sub1.u.l.p00.lock=0;}
::atomic{((Sub1.d.l.p01.toforward==1)&&(Sub1.d.l.p01.mess.tag==FIN)&&(Sub1.d.l.p01.redirect==3)&&(nfull(Sub1.u.r.p00.in))); Sub1.u.r.p00.in!Sub1.d.l.p01.mess; Sub1.d.l.p01.toforward=0;Sub1.u.r.p00.lock=0;}
od;

}
active[1] proctype R2P2() {
do
::atomic{((Sub1.d.l.p10.toforward==0)&&(nempty(Sub1.d.l.p10.in))); Sub1.d.l.p10.in?Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=1;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==DEBUT)&&(nfull(I1.i00.in))&&(Sub1.d.l.p10.mess.dest/2==2)&&((Sub1.d.l.p10.mess.dest/1)%2==0)&&(I1.i00.lock==0)); Sub1.d.l.p10.redirect=0; I1.i00.lock=1; I1.i00.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==DEBUT)&&(nfull(I1.i01.in))&&(Sub1.d.l.p10.mess.dest/2==2)&&((Sub1.d.l.p10.mess.dest/1)%2==1)&&(I1.i01.lock==0)); Sub1.d.l.p10.redirect=1; I1.i01.lock=1; I1.i01.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p00.in))&&(Sub1.d.l.p10.mess.dest/2!=2)&&(1/*Mode non Séparé*/)&&(Sub1.u.l.p00.lock==0)); Sub1.d.l.p10.redirect=2; Sub1.u.l.p00.lock=1; Sub1.u.l.p00.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p00.in))&&(Sub1.d.l.p10.mess.dest/2!=2)&&(1/*Mode non Séparé*/)&&(Sub1.u.r.p00.lock==0)); Sub1.d.l.p10.redirect=3; Sub1.u.r.p00.lock=1; Sub1.u.r.p00.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==INTER)&&(Sub1.d.l.p10.redirect==0)&&(nfull(I1.i00.in))); I1.i00.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==INTER)&&(Sub1.d.l.p10.redirect==1)&&(nfull(I1.i01.in))); I1.i01.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==INTER)&&(Sub1.d.l.p10.redirect==2)&&(nfull(Sub1.u.l.p00.in))); Sub1.u.l.p00.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==INTER)&&(Sub1.d.l.p10.redirect==3)&&(nfull(Sub1.u.r.p00.in))); Sub1.u.r.p00.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==FIN)&&(Sub1.d.l.p10.redirect==0)&&(nfull(I1.i00.in))); I1.i00.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;I1.i00.lock=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==FIN)&&(Sub1.d.l.p10.redirect==1)&&(nfull(I1.i01.in))); I1.i01.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;I1.i01.lock=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==FIN)&&(Sub1.d.l.p10.redirect==2)&&(nfull(Sub1.u.l.p00.in))); Sub1.u.l.p00.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;Sub1.u.l.p00.lock=0;}
::atomic{((Sub1.d.l.p10.toforward==1)&&(Sub1.d.l.p10.mess.tag==FIN)&&(Sub1.d.l.p10.redirect==3)&&(nfull(Sub1.u.r.p00.in))); Sub1.u.r.p00.in!Sub1.d.l.p10.mess; Sub1.d.l.p10.toforward=0;Sub1.u.r.p00.lock=0;}
od;

}
active[1] proctype R2P3() {
do
::atomic{((Sub1.d.l.p11.toforward==0)&&(nempty(Sub1.d.l.p11.in))); Sub1.d.l.p11.in?Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=1;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==DEBUT)&&(nfull(I1.i00.in))&&(Sub1.d.l.p11.mess.dest/2==2)&&((Sub1.d.l.p11.mess.dest/1)%2==0)&&(I1.i00.lock==0)); Sub1.d.l.p11.redirect=0; I1.i00.lock=1; I1.i00.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==DEBUT)&&(nfull(I1.i01.in))&&(Sub1.d.l.p11.mess.dest/2==2)&&((Sub1.d.l.p11.mess.dest/1)%2==1)&&(I1.i01.lock==0)); Sub1.d.l.p11.redirect=1; I1.i01.lock=1; I1.i01.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p00.in))&&(Sub1.d.l.p11.mess.dest/2!=2)&&(1/*Mode non Séparé*/)&&(Sub1.u.l.p00.lock==0)); Sub1.d.l.p11.redirect=2; Sub1.u.l.p00.lock=1; Sub1.u.l.p00.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p00.in))&&(Sub1.d.l.p11.mess.dest/2!=2)&&(1/*Mode non Séparé*/)&&(Sub1.u.r.p00.lock==0)); Sub1.d.l.p11.redirect=3; Sub1.u.r.p00.lock=1; Sub1.u.r.p00.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==INTER)&&(Sub1.d.l.p11.redirect==0)&&(nfull(I1.i00.in))); I1.i00.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==INTER)&&(Sub1.d.l.p11.redirect==1)&&(nfull(I1.i01.in))); I1.i01.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==INTER)&&(Sub1.d.l.p11.redirect==2)&&(nfull(Sub1.u.l.p00.in))); Sub1.u.l.p00.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==INTER)&&(Sub1.d.l.p11.redirect==3)&&(nfull(Sub1.u.r.p00.in))); Sub1.u.r.p00.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==FIN)&&(Sub1.d.l.p11.redirect==0)&&(nfull(I1.i00.in))); I1.i00.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;I1.i00.lock=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==FIN)&&(Sub1.d.l.p11.redirect==1)&&(nfull(I1.i01.in))); I1.i01.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;I1.i01.lock=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==FIN)&&(Sub1.d.l.p11.redirect==2)&&(nfull(Sub1.u.l.p00.in))); Sub1.u.l.p00.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;Sub1.u.l.p00.lock=0;}
::atomic{((Sub1.d.l.p11.toforward==1)&&(Sub1.d.l.p11.mess.tag==FIN)&&(Sub1.d.l.p11.redirect==3)&&(nfull(Sub1.u.r.p00.in))); Sub1.u.r.p00.in!Sub1.d.l.p11.mess; Sub1.d.l.p11.toforward=0;Sub1.u.r.p00.lock=0;}
od;

}
active[1] proctype R3P0() {
do
::atomic{((Sub1.d.r.p00.toforward==0)&&(nempty(Sub1.d.r.p00.in))); Sub1.d.r.p00.in?Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=1;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==DEBUT)&&(nfull(I1.i10.in))&&(Sub1.d.r.p00.mess.dest/2==3)&&((Sub1.d.r.p00.mess.dest/1)%2==0)&&(I1.i10.lock==0)); Sub1.d.r.p00.redirect=0; I1.i10.lock=1; I1.i10.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==DEBUT)&&(nfull(I1.i11.in))&&(Sub1.d.r.p00.mess.dest/2==3)&&((Sub1.d.r.p00.mess.dest/1)%2==1)&&(I1.i11.lock==0)); Sub1.d.r.p00.redirect=1; I1.i11.lock=1; I1.i11.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p01.in))&&(Sub1.d.r.p00.mess.dest/2!=3)&&(1/*Mode non Séparé*/)&&(Sub1.u.l.p01.lock==0)); Sub1.d.r.p00.redirect=2; Sub1.u.l.p01.lock=1; Sub1.u.l.p01.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p01.in))&&(Sub1.d.r.p00.mess.dest/2!=3)&&(1/*Mode non Séparé*/)&&(Sub1.u.r.p01.lock==0)); Sub1.d.r.p00.redirect=3; Sub1.u.r.p01.lock=1; Sub1.u.r.p01.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==INTER)&&(Sub1.d.r.p00.redirect==0)&&(nfull(I1.i10.in))); I1.i10.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==INTER)&&(Sub1.d.r.p00.redirect==1)&&(nfull(I1.i11.in))); I1.i11.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==INTER)&&(Sub1.d.r.p00.redirect==2)&&(nfull(Sub1.u.l.p01.in))); Sub1.u.l.p01.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==INTER)&&(Sub1.d.r.p00.redirect==3)&&(nfull(Sub1.u.r.p01.in))); Sub1.u.r.p01.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==FIN)&&(Sub1.d.r.p00.redirect==0)&&(nfull(I1.i10.in))); I1.i10.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;I1.i10.lock=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==FIN)&&(Sub1.d.r.p00.redirect==1)&&(nfull(I1.i11.in))); I1.i11.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;I1.i11.lock=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==FIN)&&(Sub1.d.r.p00.redirect==2)&&(nfull(Sub1.u.l.p01.in))); Sub1.u.l.p01.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;Sub1.u.l.p01.lock=0;}
::atomic{((Sub1.d.r.p00.toforward==1)&&(Sub1.d.r.p00.mess.tag==FIN)&&(Sub1.d.r.p00.redirect==3)&&(nfull(Sub1.u.r.p01.in))); Sub1.u.r.p01.in!Sub1.d.r.p00.mess; Sub1.d.r.p00.toforward=0;Sub1.u.r.p01.lock=0;}
od;

}
active[1] proctype R3P1() {
do
::atomic{((Sub1.d.r.p01.toforward==0)&&(nempty(Sub1.d.r.p01.in))); Sub1.d.r.p01.in?Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=1;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==DEBUT)&&(nfull(I1.i10.in))&&(Sub1.d.r.p01.mess.dest/2==3)&&((Sub1.d.r.p01.mess.dest/1)%2==0)&&(I1.i10.lock==0)); Sub1.d.r.p01.redirect=0; I1.i10.lock=1; I1.i10.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==DEBUT)&&(nfull(I1.i11.in))&&(Sub1.d.r.p01.mess.dest/2==3)&&((Sub1.d.r.p01.mess.dest/1)%2==1)&&(I1.i11.lock==0)); Sub1.d.r.p01.redirect=1; I1.i11.lock=1; I1.i11.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p01.in))&&(Sub1.d.r.p01.mess.dest/2!=3)&&(1/*Mode non Séparé*/)&&(Sub1.u.l.p01.lock==0)); Sub1.d.r.p01.redirect=2; Sub1.u.l.p01.lock=1; Sub1.u.l.p01.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p01.in))&&(Sub1.d.r.p01.mess.dest/2!=3)&&(1/*Mode non Séparé*/)&&(Sub1.u.r.p01.lock==0)); Sub1.d.r.p01.redirect=3; Sub1.u.r.p01.lock=1; Sub1.u.r.p01.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==INTER)&&(Sub1.d.r.p01.redirect==0)&&(nfull(I1.i10.in))); I1.i10.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==INTER)&&(Sub1.d.r.p01.redirect==1)&&(nfull(I1.i11.in))); I1.i11.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==INTER)&&(Sub1.d.r.p01.redirect==2)&&(nfull(Sub1.u.l.p01.in))); Sub1.u.l.p01.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==INTER)&&(Sub1.d.r.p01.redirect==3)&&(nfull(Sub1.u.r.p01.in))); Sub1.u.r.p01.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==FIN)&&(Sub1.d.r.p01.redirect==0)&&(nfull(I1.i10.in))); I1.i10.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;I1.i10.lock=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==FIN)&&(Sub1.d.r.p01.redirect==1)&&(nfull(I1.i11.in))); I1.i11.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;I1.i11.lock=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==FIN)&&(Sub1.d.r.p01.redirect==2)&&(nfull(Sub1.u.l.p01.in))); Sub1.u.l.p01.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;Sub1.u.l.p01.lock=0;}
::atomic{((Sub1.d.r.p01.toforward==1)&&(Sub1.d.r.p01.mess.tag==FIN)&&(Sub1.d.r.p01.redirect==3)&&(nfull(Sub1.u.r.p01.in))); Sub1.u.r.p01.in!Sub1.d.r.p01.mess; Sub1.d.r.p01.toforward=0;Sub1.u.r.p01.lock=0;}
od;

}
active[1] proctype R3P2() {
do
::atomic{((Sub1.d.r.p10.toforward==0)&&(nempty(Sub1.d.r.p10.in))); Sub1.d.r.p10.in?Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=1;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==DEBUT)&&(nfull(I1.i10.in))&&(Sub1.d.r.p10.mess.dest/2==3)&&((Sub1.d.r.p10.mess.dest/1)%2==0)&&(I1.i10.lock==0)); Sub1.d.r.p10.redirect=0; I1.i10.lock=1; I1.i10.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==DEBUT)&&(nfull(I1.i11.in))&&(Sub1.d.r.p10.mess.dest/2==3)&&((Sub1.d.r.p10.mess.dest/1)%2==1)&&(I1.i11.lock==0)); Sub1.d.r.p10.redirect=1; I1.i11.lock=1; I1.i11.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p01.in))&&(Sub1.d.r.p10.mess.dest/2!=3)&&(1/*Mode non Séparé*/)&&(Sub1.u.l.p01.lock==0)); Sub1.d.r.p10.redirect=2; Sub1.u.l.p01.lock=1; Sub1.u.l.p01.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p01.in))&&(Sub1.d.r.p10.mess.dest/2!=3)&&(1/*Mode non Séparé*/)&&(Sub1.u.r.p01.lock==0)); Sub1.d.r.p10.redirect=3; Sub1.u.r.p01.lock=1; Sub1.u.r.p01.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==INTER)&&(Sub1.d.r.p10.redirect==0)&&(nfull(I1.i10.in))); I1.i10.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==INTER)&&(Sub1.d.r.p10.redirect==1)&&(nfull(I1.i11.in))); I1.i11.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==INTER)&&(Sub1.d.r.p10.redirect==2)&&(nfull(Sub1.u.l.p01.in))); Sub1.u.l.p01.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==INTER)&&(Sub1.d.r.p10.redirect==3)&&(nfull(Sub1.u.r.p01.in))); Sub1.u.r.p01.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==FIN)&&(Sub1.d.r.p10.redirect==0)&&(nfull(I1.i10.in))); I1.i10.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;I1.i10.lock=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==FIN)&&(Sub1.d.r.p10.redirect==1)&&(nfull(I1.i11.in))); I1.i11.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;I1.i11.lock=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==FIN)&&(Sub1.d.r.p10.redirect==2)&&(nfull(Sub1.u.l.p01.in))); Sub1.u.l.p01.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;Sub1.u.l.p01.lock=0;}
::atomic{((Sub1.d.r.p10.toforward==1)&&(Sub1.d.r.p10.mess.tag==FIN)&&(Sub1.d.r.p10.redirect==3)&&(nfull(Sub1.u.r.p01.in))); Sub1.u.r.p01.in!Sub1.d.r.p10.mess; Sub1.d.r.p10.toforward=0;Sub1.u.r.p01.lock=0;}
od;

}
active[1] proctype R3P3() {
do
::atomic{((Sub1.d.r.p11.toforward==0)&&(nempty(Sub1.d.r.p11.in))); Sub1.d.r.p11.in?Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=1;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==DEBUT)&&(nfull(I1.i10.in))&&(Sub1.d.r.p11.mess.dest/2==3)&&((Sub1.d.r.p11.mess.dest/1)%2==0)&&(I1.i10.lock==0)); Sub1.d.r.p11.redirect=0; I1.i10.lock=1; I1.i10.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==DEBUT)&&(nfull(I1.i11.in))&&(Sub1.d.r.p11.mess.dest/2==3)&&((Sub1.d.r.p11.mess.dest/1)%2==1)&&(I1.i11.lock==0)); Sub1.d.r.p11.redirect=1; I1.i11.lock=1; I1.i11.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p01.in))&&(Sub1.d.r.p11.mess.dest/2!=3)&&(1/*Mode non Séparé*/)&&(Sub1.u.l.p01.lock==0)); Sub1.d.r.p11.redirect=2; Sub1.u.l.p01.lock=1; Sub1.u.l.p01.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p01.in))&&(Sub1.d.r.p11.mess.dest/2!=3)&&(1/*Mode non Séparé*/)&&(Sub1.u.r.p01.lock==0)); Sub1.d.r.p11.redirect=3; Sub1.u.r.p01.lock=1; Sub1.u.r.p01.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==INTER)&&(Sub1.d.r.p11.redirect==0)&&(nfull(I1.i10.in))); I1.i10.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==INTER)&&(Sub1.d.r.p11.redirect==1)&&(nfull(I1.i11.in))); I1.i11.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==INTER)&&(Sub1.d.r.p11.redirect==2)&&(nfull(Sub1.u.l.p01.in))); Sub1.u.l.p01.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==INTER)&&(Sub1.d.r.p11.redirect==3)&&(nfull(Sub1.u.r.p01.in))); Sub1.u.r.p01.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==FIN)&&(Sub1.d.r.p11.redirect==0)&&(nfull(I1.i10.in))); I1.i10.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;I1.i10.lock=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==FIN)&&(Sub1.d.r.p11.redirect==1)&&(nfull(I1.i11.in))); I1.i11.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;I1.i11.lock=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==FIN)&&(Sub1.d.r.p11.redirect==2)&&(nfull(Sub1.u.l.p01.in))); Sub1.u.l.p01.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;Sub1.u.l.p01.lock=0;}
::atomic{((Sub1.d.r.p11.toforward==1)&&(Sub1.d.r.p11.mess.tag==FIN)&&(Sub1.d.r.p11.redirect==3)&&(nfull(Sub1.u.r.p01.in))); Sub1.u.r.p01.in!Sub1.d.r.p11.mess; Sub1.d.r.p11.toforward=0;Sub1.u.r.p01.lock=0;}
od;

}
active[1] proctype R4P0() {
do
::atomic{((Sub0.u.l.p00.toforward==0)&&(nempty(Sub0.u.l.p00.in))); Sub0.u.l.p00.in?Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=1;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==DEBUT)&&(nfull(Sub0.d.l.p10.in))&&(Sub0.u.l.p00.mess.dest/4==0)&&((Sub0.u.l.p00.mess.dest/2)%2==0)&&(Sub0.d.l.p10.lock==0)); Sub0.u.l.p00.redirect=0; Sub0.d.l.p10.lock=1; Sub0.d.l.p10.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==DEBUT)&&(nfull(Sub0.d.r.p10.in))&&(Sub0.u.l.p00.mess.dest/4==0)&&((Sub0.u.l.p00.mess.dest/2)%2==1)&&(Sub0.d.r.p10.lock==0)); Sub0.u.l.p00.redirect=1; Sub0.d.r.p10.lock=1; Sub0.d.r.p10.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==DEBUT)&&(nfull(L3l.l.p00.in))&&(Sub0.u.l.p00.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p00.lock==0)); Sub0.u.l.p00.redirect=2; L3l.l.p00.lock=1; L3l.l.p00.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==DEBUT)&&(nfull(L3r.l.p00.in))&&(Sub0.u.l.p00.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3r.l.p00.lock==0)); Sub0.u.l.p00.redirect=3; L3r.l.p00.lock=1; L3r.l.p00.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==INTER)&&(Sub0.u.l.p00.redirect==0)&&(nfull(Sub0.d.l.p10.in))); Sub0.d.l.p10.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==INTER)&&(Sub0.u.l.p00.redirect==1)&&(nfull(Sub0.d.r.p10.in))); Sub0.d.r.p10.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==INTER)&&(Sub0.u.l.p00.redirect==2)&&(nfull(L3l.l.p00.in))); L3l.l.p00.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==INTER)&&(Sub0.u.l.p00.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==FIN)&&(Sub0.u.l.p00.redirect==0)&&(nfull(Sub0.d.l.p10.in))); Sub0.d.l.p10.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;Sub0.d.l.p10.lock=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==FIN)&&(Sub0.u.l.p00.redirect==1)&&(nfull(Sub0.d.r.p10.in))); Sub0.d.r.p10.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;Sub0.d.r.p10.lock=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==FIN)&&(Sub0.u.l.p00.redirect==2)&&(nfull(L3l.l.p00.in))); L3l.l.p00.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;L3l.l.p00.lock=0;}
::atomic{((Sub0.u.l.p00.toforward==1)&&(Sub0.u.l.p00.mess.tag==FIN)&&(Sub0.u.l.p00.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.l.p00.mess; Sub0.u.l.p00.toforward=0;L3r.l.p00.lock=0;}
od;

}
active[1] proctype R4P1() {
do
::atomic{((Sub0.u.l.p01.toforward==0)&&(nempty(Sub0.u.l.p01.in))); Sub0.u.l.p01.in?Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=1;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==DEBUT)&&(nfull(Sub0.d.l.p10.in))&&(Sub0.u.l.p01.mess.dest/4==0)&&((Sub0.u.l.p01.mess.dest/2)%2==0)&&(Sub0.d.l.p10.lock==0)); Sub0.u.l.p01.redirect=0; Sub0.d.l.p10.lock=1; Sub0.d.l.p10.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==DEBUT)&&(nfull(Sub0.d.r.p10.in))&&(Sub0.u.l.p01.mess.dest/4==0)&&((Sub0.u.l.p01.mess.dest/2)%2==1)&&(Sub0.d.r.p10.lock==0)); Sub0.u.l.p01.redirect=1; Sub0.d.r.p10.lock=1; Sub0.d.r.p10.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==DEBUT)&&(nfull(L3l.l.p00.in))&&(Sub0.u.l.p01.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p00.lock==0)); Sub0.u.l.p01.redirect=2; L3l.l.p00.lock=1; L3l.l.p00.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==DEBUT)&&(nfull(L3r.l.p00.in))&&(Sub0.u.l.p01.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3r.l.p00.lock==0)); Sub0.u.l.p01.redirect=3; L3r.l.p00.lock=1; L3r.l.p00.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==INTER)&&(Sub0.u.l.p01.redirect==0)&&(nfull(Sub0.d.l.p10.in))); Sub0.d.l.p10.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==INTER)&&(Sub0.u.l.p01.redirect==1)&&(nfull(Sub0.d.r.p10.in))); Sub0.d.r.p10.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==INTER)&&(Sub0.u.l.p01.redirect==2)&&(nfull(L3l.l.p00.in))); L3l.l.p00.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==INTER)&&(Sub0.u.l.p01.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==FIN)&&(Sub0.u.l.p01.redirect==0)&&(nfull(Sub0.d.l.p10.in))); Sub0.d.l.p10.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;Sub0.d.l.p10.lock=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==FIN)&&(Sub0.u.l.p01.redirect==1)&&(nfull(Sub0.d.r.p10.in))); Sub0.d.r.p10.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;Sub0.d.r.p10.lock=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==FIN)&&(Sub0.u.l.p01.redirect==2)&&(nfull(L3l.l.p00.in))); L3l.l.p00.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;L3l.l.p00.lock=0;}
::atomic{((Sub0.u.l.p01.toforward==1)&&(Sub0.u.l.p01.mess.tag==FIN)&&(Sub0.u.l.p01.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.l.p01.mess; Sub0.u.l.p01.toforward=0;L3r.l.p00.lock=0;}
od;

}
active[1] proctype R4P2() {
do
::atomic{((Sub0.u.l.p10.toforward==0)&&(nempty(Sub0.u.l.p10.in))); Sub0.u.l.p10.in?Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=1;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==DEBUT)&&(nfull(Sub0.d.l.p10.in))&&(Sub0.u.l.p10.mess.dest/4==0)&&((Sub0.u.l.p10.mess.dest/2)%2==0)&&(Sub0.d.l.p10.lock==0)); Sub0.u.l.p10.redirect=0; Sub0.d.l.p10.lock=1; Sub0.d.l.p10.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==DEBUT)&&(nfull(Sub0.d.r.p10.in))&&(Sub0.u.l.p10.mess.dest/4==0)&&((Sub0.u.l.p10.mess.dest/2)%2==1)&&(Sub0.d.r.p10.lock==0)); Sub0.u.l.p10.redirect=1; Sub0.d.r.p10.lock=1; Sub0.d.r.p10.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==DEBUT)&&(nfull(L3l.l.p00.in))&&(Sub0.u.l.p10.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p00.lock==0)); Sub0.u.l.p10.redirect=2; L3l.l.p00.lock=1; L3l.l.p00.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==DEBUT)&&(nfull(L3r.l.p00.in))&&(Sub0.u.l.p10.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3r.l.p00.lock==0)); Sub0.u.l.p10.redirect=3; L3r.l.p00.lock=1; L3r.l.p00.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==INTER)&&(Sub0.u.l.p10.redirect==0)&&(nfull(Sub0.d.l.p10.in))); Sub0.d.l.p10.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==INTER)&&(Sub0.u.l.p10.redirect==1)&&(nfull(Sub0.d.r.p10.in))); Sub0.d.r.p10.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==INTER)&&(Sub0.u.l.p10.redirect==2)&&(nfull(L3l.l.p00.in))); L3l.l.p00.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==INTER)&&(Sub0.u.l.p10.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==FIN)&&(Sub0.u.l.p10.redirect==0)&&(nfull(Sub0.d.l.p10.in))); Sub0.d.l.p10.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;Sub0.d.l.p10.lock=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==FIN)&&(Sub0.u.l.p10.redirect==1)&&(nfull(Sub0.d.r.p10.in))); Sub0.d.r.p10.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;Sub0.d.r.p10.lock=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==FIN)&&(Sub0.u.l.p10.redirect==2)&&(nfull(L3l.l.p00.in))); L3l.l.p00.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;L3l.l.p00.lock=0;}
::atomic{((Sub0.u.l.p10.toforward==1)&&(Sub0.u.l.p10.mess.tag==FIN)&&(Sub0.u.l.p10.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.l.p10.mess; Sub0.u.l.p10.toforward=0;L3r.l.p00.lock=0;}
od;

}
active[1] proctype R4P3() {
do
::atomic{((Sub0.u.l.p11.toforward==0)&&(nempty(Sub0.u.l.p11.in))); Sub0.u.l.p11.in?Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=1;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==DEBUT)&&(nfull(Sub0.d.l.p10.in))&&(Sub0.u.l.p11.mess.dest/4==0)&&((Sub0.u.l.p11.mess.dest/2)%2==0)&&(Sub0.d.l.p10.lock==0)); Sub0.u.l.p11.redirect=0; Sub0.d.l.p10.lock=1; Sub0.d.l.p10.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==DEBUT)&&(nfull(Sub0.d.r.p10.in))&&(Sub0.u.l.p11.mess.dest/4==0)&&((Sub0.u.l.p11.mess.dest/2)%2==1)&&(Sub0.d.r.p10.lock==0)); Sub0.u.l.p11.redirect=1; Sub0.d.r.p10.lock=1; Sub0.d.r.p10.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==DEBUT)&&(nfull(L3l.l.p00.in))&&(Sub0.u.l.p11.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p00.lock==0)); Sub0.u.l.p11.redirect=2; L3l.l.p00.lock=1; L3l.l.p00.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==DEBUT)&&(nfull(L3r.l.p00.in))&&(Sub0.u.l.p11.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3r.l.p00.lock==0)); Sub0.u.l.p11.redirect=3; L3r.l.p00.lock=1; L3r.l.p00.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==INTER)&&(Sub0.u.l.p11.redirect==0)&&(nfull(Sub0.d.l.p10.in))); Sub0.d.l.p10.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==INTER)&&(Sub0.u.l.p11.redirect==1)&&(nfull(Sub0.d.r.p10.in))); Sub0.d.r.p10.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==INTER)&&(Sub0.u.l.p11.redirect==2)&&(nfull(L3l.l.p00.in))); L3l.l.p00.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==INTER)&&(Sub0.u.l.p11.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==FIN)&&(Sub0.u.l.p11.redirect==0)&&(nfull(Sub0.d.l.p10.in))); Sub0.d.l.p10.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;Sub0.d.l.p10.lock=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==FIN)&&(Sub0.u.l.p11.redirect==1)&&(nfull(Sub0.d.r.p10.in))); Sub0.d.r.p10.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;Sub0.d.r.p10.lock=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==FIN)&&(Sub0.u.l.p11.redirect==2)&&(nfull(L3l.l.p00.in))); L3l.l.p00.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;L3l.l.p00.lock=0;}
::atomic{((Sub0.u.l.p11.toforward==1)&&(Sub0.u.l.p11.mess.tag==FIN)&&(Sub0.u.l.p11.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.l.p11.mess; Sub0.u.l.p11.toforward=0;L3r.l.p00.lock=0;}
od;

}
active[1] proctype R5P0() {
do
::atomic{((Sub0.u.r.p00.toforward==0)&&(nempty(Sub0.u.r.p00.in))); Sub0.u.r.p00.in?Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=1;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==DEBUT)&&(nfull(Sub0.d.l.p11.in))&&(Sub0.u.r.p00.mess.dest/4==0)&&((Sub0.u.r.p00.mess.dest/2)%2==0)&&(Sub0.d.l.p11.lock==0)); Sub0.u.r.p00.redirect=0; Sub0.d.l.p11.lock=1; Sub0.d.l.p11.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==DEBUT)&&(nfull(Sub0.d.r.p11.in))&&(Sub0.u.r.p00.mess.dest/4==0)&&((Sub0.u.r.p00.mess.dest/2)%2==1)&&(Sub0.d.r.p11.lock==0)); Sub0.u.r.p00.redirect=1; Sub0.d.r.p11.lock=1; Sub0.d.r.p11.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==DEBUT)&&(nfull(L3l.r.p00.in))&&(Sub0.u.r.p00.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p00.lock==0)); Sub0.u.r.p00.redirect=2; L3l.r.p00.lock=1; L3l.r.p00.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==DEBUT)&&(nfull(L3r.l.p00.in))&&(Sub0.u.r.p00.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3r.l.p00.lock==0)); Sub0.u.r.p00.redirect=3; L3r.l.p00.lock=1; L3r.l.p00.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==INTER)&&(Sub0.u.r.p00.redirect==0)&&(nfull(Sub0.d.l.p11.in))); Sub0.d.l.p11.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==INTER)&&(Sub0.u.r.p00.redirect==1)&&(nfull(Sub0.d.r.p11.in))); Sub0.d.r.p11.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==INTER)&&(Sub0.u.r.p00.redirect==2)&&(nfull(L3l.r.p00.in))); L3l.r.p00.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==INTER)&&(Sub0.u.r.p00.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==FIN)&&(Sub0.u.r.p00.redirect==0)&&(nfull(Sub0.d.l.p11.in))); Sub0.d.l.p11.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;Sub0.d.l.p11.lock=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==FIN)&&(Sub0.u.r.p00.redirect==1)&&(nfull(Sub0.d.r.p11.in))); Sub0.d.r.p11.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;Sub0.d.r.p11.lock=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==FIN)&&(Sub0.u.r.p00.redirect==2)&&(nfull(L3l.r.p00.in))); L3l.r.p00.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;L3l.r.p00.lock=0;}
::atomic{((Sub0.u.r.p00.toforward==1)&&(Sub0.u.r.p00.mess.tag==FIN)&&(Sub0.u.r.p00.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.r.p00.mess; Sub0.u.r.p00.toforward=0;L3r.l.p00.lock=0;}
od;

}
active[1] proctype R5P1() {
do
::atomic{((Sub0.u.r.p01.toforward==0)&&(nempty(Sub0.u.r.p01.in))); Sub0.u.r.p01.in?Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=1;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==DEBUT)&&(nfull(Sub0.d.l.p11.in))&&(Sub0.u.r.p01.mess.dest/4==0)&&((Sub0.u.r.p01.mess.dest/2)%2==0)&&(Sub0.d.l.p11.lock==0)); Sub0.u.r.p01.redirect=0; Sub0.d.l.p11.lock=1; Sub0.d.l.p11.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==DEBUT)&&(nfull(Sub0.d.r.p11.in))&&(Sub0.u.r.p01.mess.dest/4==0)&&((Sub0.u.r.p01.mess.dest/2)%2==1)&&(Sub0.d.r.p11.lock==0)); Sub0.u.r.p01.redirect=1; Sub0.d.r.p11.lock=1; Sub0.d.r.p11.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==DEBUT)&&(nfull(L3l.r.p00.in))&&(Sub0.u.r.p01.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p00.lock==0)); Sub0.u.r.p01.redirect=2; L3l.r.p00.lock=1; L3l.r.p00.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==DEBUT)&&(nfull(L3r.l.p00.in))&&(Sub0.u.r.p01.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3r.l.p00.lock==0)); Sub0.u.r.p01.redirect=3; L3r.l.p00.lock=1; L3r.l.p00.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==INTER)&&(Sub0.u.r.p01.redirect==0)&&(nfull(Sub0.d.l.p11.in))); Sub0.d.l.p11.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==INTER)&&(Sub0.u.r.p01.redirect==1)&&(nfull(Sub0.d.r.p11.in))); Sub0.d.r.p11.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==INTER)&&(Sub0.u.r.p01.redirect==2)&&(nfull(L3l.r.p00.in))); L3l.r.p00.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==INTER)&&(Sub0.u.r.p01.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==FIN)&&(Sub0.u.r.p01.redirect==0)&&(nfull(Sub0.d.l.p11.in))); Sub0.d.l.p11.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;Sub0.d.l.p11.lock=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==FIN)&&(Sub0.u.r.p01.redirect==1)&&(nfull(Sub0.d.r.p11.in))); Sub0.d.r.p11.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;Sub0.d.r.p11.lock=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==FIN)&&(Sub0.u.r.p01.redirect==2)&&(nfull(L3l.r.p00.in))); L3l.r.p00.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;L3l.r.p00.lock=0;}
::atomic{((Sub0.u.r.p01.toforward==1)&&(Sub0.u.r.p01.mess.tag==FIN)&&(Sub0.u.r.p01.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.r.p01.mess; Sub0.u.r.p01.toforward=0;L3r.l.p00.lock=0;}
od;

}
active[1] proctype R5P2() {
do
::atomic{((Sub0.u.r.p10.toforward==0)&&(nempty(Sub0.u.r.p10.in))); Sub0.u.r.p10.in?Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=1;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==DEBUT)&&(nfull(Sub0.d.l.p11.in))&&(Sub0.u.r.p10.mess.dest/4==0)&&((Sub0.u.r.p10.mess.dest/2)%2==0)&&(Sub0.d.l.p11.lock==0)); Sub0.u.r.p10.redirect=0; Sub0.d.l.p11.lock=1; Sub0.d.l.p11.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==DEBUT)&&(nfull(Sub0.d.r.p11.in))&&(Sub0.u.r.p10.mess.dest/4==0)&&((Sub0.u.r.p10.mess.dest/2)%2==1)&&(Sub0.d.r.p11.lock==0)); Sub0.u.r.p10.redirect=1; Sub0.d.r.p11.lock=1; Sub0.d.r.p11.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==DEBUT)&&(nfull(L3l.r.p00.in))&&(Sub0.u.r.p10.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p00.lock==0)); Sub0.u.r.p10.redirect=2; L3l.r.p00.lock=1; L3l.r.p00.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==DEBUT)&&(nfull(L3r.l.p00.in))&&(Sub0.u.r.p10.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3r.l.p00.lock==0)); Sub0.u.r.p10.redirect=3; L3r.l.p00.lock=1; L3r.l.p00.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==INTER)&&(Sub0.u.r.p10.redirect==0)&&(nfull(Sub0.d.l.p11.in))); Sub0.d.l.p11.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==INTER)&&(Sub0.u.r.p10.redirect==1)&&(nfull(Sub0.d.r.p11.in))); Sub0.d.r.p11.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==INTER)&&(Sub0.u.r.p10.redirect==2)&&(nfull(L3l.r.p00.in))); L3l.r.p00.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==INTER)&&(Sub0.u.r.p10.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==FIN)&&(Sub0.u.r.p10.redirect==0)&&(nfull(Sub0.d.l.p11.in))); Sub0.d.l.p11.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;Sub0.d.l.p11.lock=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==FIN)&&(Sub0.u.r.p10.redirect==1)&&(nfull(Sub0.d.r.p11.in))); Sub0.d.r.p11.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;Sub0.d.r.p11.lock=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==FIN)&&(Sub0.u.r.p10.redirect==2)&&(nfull(L3l.r.p00.in))); L3l.r.p00.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;L3l.r.p00.lock=0;}
::atomic{((Sub0.u.r.p10.toforward==1)&&(Sub0.u.r.p10.mess.tag==FIN)&&(Sub0.u.r.p10.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.r.p10.mess; Sub0.u.r.p10.toforward=0;L3r.l.p00.lock=0;}
od;

}
active[1] proctype R5P3() {
do
::atomic{((Sub0.u.r.p11.toforward==0)&&(nempty(Sub0.u.r.p11.in))); Sub0.u.r.p11.in?Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=1;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==DEBUT)&&(nfull(Sub0.d.l.p11.in))&&(Sub0.u.r.p11.mess.dest/4==0)&&((Sub0.u.r.p11.mess.dest/2)%2==0)&&(Sub0.d.l.p11.lock==0)); Sub0.u.r.p11.redirect=0; Sub0.d.l.p11.lock=1; Sub0.d.l.p11.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==DEBUT)&&(nfull(Sub0.d.r.p11.in))&&(Sub0.u.r.p11.mess.dest/4==0)&&((Sub0.u.r.p11.mess.dest/2)%2==1)&&(Sub0.d.r.p11.lock==0)); Sub0.u.r.p11.redirect=1; Sub0.d.r.p11.lock=1; Sub0.d.r.p11.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==DEBUT)&&(nfull(L3l.r.p00.in))&&(Sub0.u.r.p11.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p00.lock==0)); Sub0.u.r.p11.redirect=2; L3l.r.p00.lock=1; L3l.r.p00.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==DEBUT)&&(nfull(L3r.l.p00.in))&&(Sub0.u.r.p11.mess.dest/4!=0)&&(1/*Mode non Séparé*/)&&(L3r.l.p00.lock==0)); Sub0.u.r.p11.redirect=3; L3r.l.p00.lock=1; L3r.l.p00.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==INTER)&&(Sub0.u.r.p11.redirect==0)&&(nfull(Sub0.d.l.p11.in))); Sub0.d.l.p11.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==INTER)&&(Sub0.u.r.p11.redirect==1)&&(nfull(Sub0.d.r.p11.in))); Sub0.d.r.p11.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==INTER)&&(Sub0.u.r.p11.redirect==2)&&(nfull(L3l.r.p00.in))); L3l.r.p00.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==INTER)&&(Sub0.u.r.p11.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==FIN)&&(Sub0.u.r.p11.redirect==0)&&(nfull(Sub0.d.l.p11.in))); Sub0.d.l.p11.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;Sub0.d.l.p11.lock=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==FIN)&&(Sub0.u.r.p11.redirect==1)&&(nfull(Sub0.d.r.p11.in))); Sub0.d.r.p11.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;Sub0.d.r.p11.lock=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==FIN)&&(Sub0.u.r.p11.redirect==2)&&(nfull(L3l.r.p00.in))); L3l.r.p00.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;L3l.r.p00.lock=0;}
::atomic{((Sub0.u.r.p11.toforward==1)&&(Sub0.u.r.p11.mess.tag==FIN)&&(Sub0.u.r.p11.redirect==3)&&(nfull(L3r.l.p00.in))); L3r.l.p00.in!Sub0.u.r.p11.mess; Sub0.u.r.p11.toforward=0;L3r.l.p00.lock=0;}
od;

}
active[1] proctype R6P0() {
do
::atomic{((Sub1.u.l.p00.toforward==0)&&(nempty(Sub1.u.l.p00.in))); Sub1.u.l.p00.in?Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=1;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==DEBUT)&&(nfull(Sub1.d.l.p10.in))&&(Sub1.u.l.p00.mess.dest/4==1)&&((Sub1.u.l.p00.mess.dest/2)%2==0)&&(Sub1.d.l.p10.lock==0)); Sub1.u.l.p00.redirect=0; Sub1.d.l.p10.lock=1; Sub1.d.l.p10.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==DEBUT)&&(nfull(Sub1.d.r.p10.in))&&(Sub1.u.l.p00.mess.dest/4==1)&&((Sub1.u.l.p00.mess.dest/2)%2==1)&&(Sub1.d.r.p10.lock==0)); Sub1.u.l.p00.redirect=1; Sub1.d.r.p10.lock=1; Sub1.d.r.p10.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==DEBUT)&&(nfull(L3l.l.p01.in))&&(Sub1.u.l.p00.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3l.l.p01.lock==0)); Sub1.u.l.p00.redirect=2; L3l.l.p01.lock=1; L3l.l.p01.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==DEBUT)&&(nfull(L3r.l.p01.in))&&(Sub1.u.l.p00.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3r.l.p01.lock==0)); Sub1.u.l.p00.redirect=3; L3r.l.p01.lock=1; L3r.l.p01.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==INTER)&&(Sub1.u.l.p00.redirect==0)&&(nfull(Sub1.d.l.p10.in))); Sub1.d.l.p10.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==INTER)&&(Sub1.u.l.p00.redirect==1)&&(nfull(Sub1.d.r.p10.in))); Sub1.d.r.p10.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==INTER)&&(Sub1.u.l.p00.redirect==2)&&(nfull(L3l.l.p01.in))); L3l.l.p01.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==INTER)&&(Sub1.u.l.p00.redirect==3)&&(nfull(L3r.l.p01.in))); L3r.l.p01.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==FIN)&&(Sub1.u.l.p00.redirect==0)&&(nfull(Sub1.d.l.p10.in))); Sub1.d.l.p10.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;Sub1.d.l.p10.lock=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==FIN)&&(Sub1.u.l.p00.redirect==1)&&(nfull(Sub1.d.r.p10.in))); Sub1.d.r.p10.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;Sub1.d.r.p10.lock=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==FIN)&&(Sub1.u.l.p00.redirect==2)&&(nfull(L3l.l.p01.in))); L3l.l.p01.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;L3l.l.p01.lock=0;}
::atomic{((Sub1.u.l.p00.toforward==1)&&(Sub1.u.l.p00.mess.tag==FIN)&&(Sub1.u.l.p00.redirect==3)&&(nfull(L3r.l.p01.in))); L3r.l.p01.in!Sub1.u.l.p00.mess; Sub1.u.l.p00.toforward=0;L3r.l.p01.lock=0;}
od;

}
active[1] proctype R6P1() {
do
::atomic{((Sub1.u.l.p01.toforward==0)&&(nempty(Sub1.u.l.p01.in))); Sub1.u.l.p01.in?Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=1;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==DEBUT)&&(nfull(Sub1.d.l.p10.in))&&(Sub1.u.l.p01.mess.dest/4==1)&&((Sub1.u.l.p01.mess.dest/2)%2==0)&&(Sub1.d.l.p10.lock==0)); Sub1.u.l.p01.redirect=0; Sub1.d.l.p10.lock=1; Sub1.d.l.p10.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==DEBUT)&&(nfull(Sub1.d.r.p10.in))&&(Sub1.u.l.p01.mess.dest/4==1)&&((Sub1.u.l.p01.mess.dest/2)%2==1)&&(Sub1.d.r.p10.lock==0)); Sub1.u.l.p01.redirect=1; Sub1.d.r.p10.lock=1; Sub1.d.r.p10.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==DEBUT)&&(nfull(L3l.l.p01.in))&&(Sub1.u.l.p01.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3l.l.p01.lock==0)); Sub1.u.l.p01.redirect=2; L3l.l.p01.lock=1; L3l.l.p01.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==DEBUT)&&(nfull(L3r.l.p01.in))&&(Sub1.u.l.p01.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3r.l.p01.lock==0)); Sub1.u.l.p01.redirect=3; L3r.l.p01.lock=1; L3r.l.p01.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==INTER)&&(Sub1.u.l.p01.redirect==0)&&(nfull(Sub1.d.l.p10.in))); Sub1.d.l.p10.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==INTER)&&(Sub1.u.l.p01.redirect==1)&&(nfull(Sub1.d.r.p10.in))); Sub1.d.r.p10.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==INTER)&&(Sub1.u.l.p01.redirect==2)&&(nfull(L3l.l.p01.in))); L3l.l.p01.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==INTER)&&(Sub1.u.l.p01.redirect==3)&&(nfull(L3r.l.p01.in))); L3r.l.p01.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==FIN)&&(Sub1.u.l.p01.redirect==0)&&(nfull(Sub1.d.l.p10.in))); Sub1.d.l.p10.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;Sub1.d.l.p10.lock=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==FIN)&&(Sub1.u.l.p01.redirect==1)&&(nfull(Sub1.d.r.p10.in))); Sub1.d.r.p10.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;Sub1.d.r.p10.lock=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==FIN)&&(Sub1.u.l.p01.redirect==2)&&(nfull(L3l.l.p01.in))); L3l.l.p01.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;L3l.l.p01.lock=0;}
::atomic{((Sub1.u.l.p01.toforward==1)&&(Sub1.u.l.p01.mess.tag==FIN)&&(Sub1.u.l.p01.redirect==3)&&(nfull(L3r.l.p01.in))); L3r.l.p01.in!Sub1.u.l.p01.mess; Sub1.u.l.p01.toforward=0;L3r.l.p01.lock=0;}
od;

}
active[1] proctype R6P2() {
do
::atomic{((Sub1.u.l.p10.toforward==0)&&(nempty(Sub1.u.l.p10.in))); Sub1.u.l.p10.in?Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=1;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==DEBUT)&&(nfull(Sub1.d.l.p10.in))&&(Sub1.u.l.p10.mess.dest/4==1)&&((Sub1.u.l.p10.mess.dest/2)%2==0)&&(Sub1.d.l.p10.lock==0)); Sub1.u.l.p10.redirect=0; Sub1.d.l.p10.lock=1; Sub1.d.l.p10.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==DEBUT)&&(nfull(Sub1.d.r.p10.in))&&(Sub1.u.l.p10.mess.dest/4==1)&&((Sub1.u.l.p10.mess.dest/2)%2==1)&&(Sub1.d.r.p10.lock==0)); Sub1.u.l.p10.redirect=1; Sub1.d.r.p10.lock=1; Sub1.d.r.p10.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==DEBUT)&&(nfull(L3l.l.p01.in))&&(Sub1.u.l.p10.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3l.l.p01.lock==0)); Sub1.u.l.p10.redirect=2; L3l.l.p01.lock=1; L3l.l.p01.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==DEBUT)&&(nfull(L3r.l.p01.in))&&(Sub1.u.l.p10.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3r.l.p01.lock==0)); Sub1.u.l.p10.redirect=3; L3r.l.p01.lock=1; L3r.l.p01.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==INTER)&&(Sub1.u.l.p10.redirect==0)&&(nfull(Sub1.d.l.p10.in))); Sub1.d.l.p10.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==INTER)&&(Sub1.u.l.p10.redirect==1)&&(nfull(Sub1.d.r.p10.in))); Sub1.d.r.p10.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==INTER)&&(Sub1.u.l.p10.redirect==2)&&(nfull(L3l.l.p01.in))); L3l.l.p01.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==INTER)&&(Sub1.u.l.p10.redirect==3)&&(nfull(L3r.l.p01.in))); L3r.l.p01.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==FIN)&&(Sub1.u.l.p10.redirect==0)&&(nfull(Sub1.d.l.p10.in))); Sub1.d.l.p10.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;Sub1.d.l.p10.lock=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==FIN)&&(Sub1.u.l.p10.redirect==1)&&(nfull(Sub1.d.r.p10.in))); Sub1.d.r.p10.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;Sub1.d.r.p10.lock=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==FIN)&&(Sub1.u.l.p10.redirect==2)&&(nfull(L3l.l.p01.in))); L3l.l.p01.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;L3l.l.p01.lock=0;}
::atomic{((Sub1.u.l.p10.toforward==1)&&(Sub1.u.l.p10.mess.tag==FIN)&&(Sub1.u.l.p10.redirect==3)&&(nfull(L3r.l.p01.in))); L3r.l.p01.in!Sub1.u.l.p10.mess; Sub1.u.l.p10.toforward=0;L3r.l.p01.lock=0;}
od;

}
active[1] proctype R6P3() {
do
::atomic{((Sub1.u.l.p11.toforward==0)&&(nempty(Sub1.u.l.p11.in))); Sub1.u.l.p11.in?Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=1;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==DEBUT)&&(nfull(Sub1.d.l.p10.in))&&(Sub1.u.l.p11.mess.dest/4==1)&&((Sub1.u.l.p11.mess.dest/2)%2==0)&&(Sub1.d.l.p10.lock==0)); Sub1.u.l.p11.redirect=0; Sub1.d.l.p10.lock=1; Sub1.d.l.p10.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==DEBUT)&&(nfull(Sub1.d.r.p10.in))&&(Sub1.u.l.p11.mess.dest/4==1)&&((Sub1.u.l.p11.mess.dest/2)%2==1)&&(Sub1.d.r.p10.lock==0)); Sub1.u.l.p11.redirect=1; Sub1.d.r.p10.lock=1; Sub1.d.r.p10.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==DEBUT)&&(nfull(L3l.l.p01.in))&&(Sub1.u.l.p11.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3l.l.p01.lock==0)); Sub1.u.l.p11.redirect=2; L3l.l.p01.lock=1; L3l.l.p01.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==DEBUT)&&(nfull(L3r.l.p01.in))&&(Sub1.u.l.p11.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3r.l.p01.lock==0)); Sub1.u.l.p11.redirect=3; L3r.l.p01.lock=1; L3r.l.p01.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==INTER)&&(Sub1.u.l.p11.redirect==0)&&(nfull(Sub1.d.l.p10.in))); Sub1.d.l.p10.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==INTER)&&(Sub1.u.l.p11.redirect==1)&&(nfull(Sub1.d.r.p10.in))); Sub1.d.r.p10.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==INTER)&&(Sub1.u.l.p11.redirect==2)&&(nfull(L3l.l.p01.in))); L3l.l.p01.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==INTER)&&(Sub1.u.l.p11.redirect==3)&&(nfull(L3r.l.p01.in))); L3r.l.p01.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==FIN)&&(Sub1.u.l.p11.redirect==0)&&(nfull(Sub1.d.l.p10.in))); Sub1.d.l.p10.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;Sub1.d.l.p10.lock=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==FIN)&&(Sub1.u.l.p11.redirect==1)&&(nfull(Sub1.d.r.p10.in))); Sub1.d.r.p10.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;Sub1.d.r.p10.lock=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==FIN)&&(Sub1.u.l.p11.redirect==2)&&(nfull(L3l.l.p01.in))); L3l.l.p01.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;L3l.l.p01.lock=0;}
::atomic{((Sub1.u.l.p11.toforward==1)&&(Sub1.u.l.p11.mess.tag==FIN)&&(Sub1.u.l.p11.redirect==3)&&(nfull(L3r.l.p01.in))); L3r.l.p01.in!Sub1.u.l.p11.mess; Sub1.u.l.p11.toforward=0;L3r.l.p01.lock=0;}
od;

}
active[1] proctype R7P0() {
do
::atomic{((Sub1.u.r.p00.toforward==0)&&(nempty(Sub1.u.r.p00.in))); Sub1.u.r.p00.in?Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=1;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==DEBUT)&&(nfull(Sub1.d.l.p11.in))&&(Sub1.u.r.p00.mess.dest/4==1)&&((Sub1.u.r.p00.mess.dest/2)%2==0)&&(Sub1.d.l.p11.lock==0)); Sub1.u.r.p00.redirect=0; Sub1.d.l.p11.lock=1; Sub1.d.l.p11.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==DEBUT)&&(nfull(Sub1.d.r.p11.in))&&(Sub1.u.r.p00.mess.dest/4==1)&&((Sub1.u.r.p00.mess.dest/2)%2==1)&&(Sub1.d.r.p11.lock==0)); Sub1.u.r.p00.redirect=1; Sub1.d.r.p11.lock=1; Sub1.d.r.p11.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==DEBUT)&&(nfull(L3l.r.p01.in))&&(Sub1.u.r.p00.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3l.r.p01.lock==0)); Sub1.u.r.p00.redirect=2; L3l.r.p01.lock=1; L3l.r.p01.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==DEBUT)&&(nfull(L3r.r.p01.in))&&(Sub1.u.r.p00.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3r.r.p01.lock==0)); Sub1.u.r.p00.redirect=3; L3r.r.p01.lock=1; L3r.r.p01.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==INTER)&&(Sub1.u.r.p00.redirect==0)&&(nfull(Sub1.d.l.p11.in))); Sub1.d.l.p11.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==INTER)&&(Sub1.u.r.p00.redirect==1)&&(nfull(Sub1.d.r.p11.in))); Sub1.d.r.p11.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==INTER)&&(Sub1.u.r.p00.redirect==2)&&(nfull(L3l.r.p01.in))); L3l.r.p01.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==INTER)&&(Sub1.u.r.p00.redirect==3)&&(nfull(L3r.r.p01.in))); L3r.r.p01.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==FIN)&&(Sub1.u.r.p00.redirect==0)&&(nfull(Sub1.d.l.p11.in))); Sub1.d.l.p11.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;Sub1.d.l.p11.lock=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==FIN)&&(Sub1.u.r.p00.redirect==1)&&(nfull(Sub1.d.r.p11.in))); Sub1.d.r.p11.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;Sub1.d.r.p11.lock=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==FIN)&&(Sub1.u.r.p00.redirect==2)&&(nfull(L3l.r.p01.in))); L3l.r.p01.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;L3l.r.p01.lock=0;}
::atomic{((Sub1.u.r.p00.toforward==1)&&(Sub1.u.r.p00.mess.tag==FIN)&&(Sub1.u.r.p00.redirect==3)&&(nfull(L3r.r.p01.in))); L3r.r.p01.in!Sub1.u.r.p00.mess; Sub1.u.r.p00.toforward=0;L3r.r.p01.lock=0;}
od;

}
active[1] proctype R7P1() {
do
::atomic{((Sub1.u.r.p01.toforward==0)&&(nempty(Sub1.u.r.p01.in))); Sub1.u.r.p01.in?Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=1;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==DEBUT)&&(nfull(Sub1.d.l.p11.in))&&(Sub1.u.r.p01.mess.dest/4==1)&&((Sub1.u.r.p01.mess.dest/2)%2==0)&&(Sub1.d.l.p11.lock==0)); Sub1.u.r.p01.redirect=0; Sub1.d.l.p11.lock=1; Sub1.d.l.p11.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==DEBUT)&&(nfull(Sub1.d.r.p11.in))&&(Sub1.u.r.p01.mess.dest/4==1)&&((Sub1.u.r.p01.mess.dest/2)%2==1)&&(Sub1.d.r.p11.lock==0)); Sub1.u.r.p01.redirect=1; Sub1.d.r.p11.lock=1; Sub1.d.r.p11.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==DEBUT)&&(nfull(L3l.r.p01.in))&&(Sub1.u.r.p01.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3l.r.p01.lock==0)); Sub1.u.r.p01.redirect=2; L3l.r.p01.lock=1; L3l.r.p01.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==DEBUT)&&(nfull(L3r.r.p01.in))&&(Sub1.u.r.p01.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3r.r.p01.lock==0)); Sub1.u.r.p01.redirect=3; L3r.r.p01.lock=1; L3r.r.p01.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==INTER)&&(Sub1.u.r.p01.redirect==0)&&(nfull(Sub1.d.l.p11.in))); Sub1.d.l.p11.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==INTER)&&(Sub1.u.r.p01.redirect==1)&&(nfull(Sub1.d.r.p11.in))); Sub1.d.r.p11.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==INTER)&&(Sub1.u.r.p01.redirect==2)&&(nfull(L3l.r.p01.in))); L3l.r.p01.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==INTER)&&(Sub1.u.r.p01.redirect==3)&&(nfull(L3r.r.p01.in))); L3r.r.p01.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==FIN)&&(Sub1.u.r.p01.redirect==0)&&(nfull(Sub1.d.l.p11.in))); Sub1.d.l.p11.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;Sub1.d.l.p11.lock=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==FIN)&&(Sub1.u.r.p01.redirect==1)&&(nfull(Sub1.d.r.p11.in))); Sub1.d.r.p11.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;Sub1.d.r.p11.lock=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==FIN)&&(Sub1.u.r.p01.redirect==2)&&(nfull(L3l.r.p01.in))); L3l.r.p01.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;L3l.r.p01.lock=0;}
::atomic{((Sub1.u.r.p01.toforward==1)&&(Sub1.u.r.p01.mess.tag==FIN)&&(Sub1.u.r.p01.redirect==3)&&(nfull(L3r.r.p01.in))); L3r.r.p01.in!Sub1.u.r.p01.mess; Sub1.u.r.p01.toforward=0;L3r.r.p01.lock=0;}
od;

}
active[1] proctype R7P2() {
do
::atomic{((Sub1.u.r.p10.toforward==0)&&(nempty(Sub1.u.r.p10.in))); Sub1.u.r.p10.in?Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=1;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==DEBUT)&&(nfull(Sub1.d.l.p11.in))&&(Sub1.u.r.p10.mess.dest/4==1)&&((Sub1.u.r.p10.mess.dest/2)%2==0)&&(Sub1.d.l.p11.lock==0)); Sub1.u.r.p10.redirect=0; Sub1.d.l.p11.lock=1; Sub1.d.l.p11.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==DEBUT)&&(nfull(Sub1.d.r.p11.in))&&(Sub1.u.r.p10.mess.dest/4==1)&&((Sub1.u.r.p10.mess.dest/2)%2==1)&&(Sub1.d.r.p11.lock==0)); Sub1.u.r.p10.redirect=1; Sub1.d.r.p11.lock=1; Sub1.d.r.p11.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==DEBUT)&&(nfull(L3l.r.p01.in))&&(Sub1.u.r.p10.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3l.r.p01.lock==0)); Sub1.u.r.p10.redirect=2; L3l.r.p01.lock=1; L3l.r.p01.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==DEBUT)&&(nfull(L3r.r.p01.in))&&(Sub1.u.r.p10.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3r.r.p01.lock==0)); Sub1.u.r.p10.redirect=3; L3r.r.p01.lock=1; L3r.r.p01.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==INTER)&&(Sub1.u.r.p10.redirect==0)&&(nfull(Sub1.d.l.p11.in))); Sub1.d.l.p11.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==INTER)&&(Sub1.u.r.p10.redirect==1)&&(nfull(Sub1.d.r.p11.in))); Sub1.d.r.p11.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==INTER)&&(Sub1.u.r.p10.redirect==2)&&(nfull(L3l.r.p01.in))); L3l.r.p01.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==INTER)&&(Sub1.u.r.p10.redirect==3)&&(nfull(L3r.r.p01.in))); L3r.r.p01.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==FIN)&&(Sub1.u.r.p10.redirect==0)&&(nfull(Sub1.d.l.p11.in))); Sub1.d.l.p11.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;Sub1.d.l.p11.lock=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==FIN)&&(Sub1.u.r.p10.redirect==1)&&(nfull(Sub1.d.r.p11.in))); Sub1.d.r.p11.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;Sub1.d.r.p11.lock=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==FIN)&&(Sub1.u.r.p10.redirect==2)&&(nfull(L3l.r.p01.in))); L3l.r.p01.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;L3l.r.p01.lock=0;}
::atomic{((Sub1.u.r.p10.toforward==1)&&(Sub1.u.r.p10.mess.tag==FIN)&&(Sub1.u.r.p10.redirect==3)&&(nfull(L3r.r.p01.in))); L3r.r.p01.in!Sub1.u.r.p10.mess; Sub1.u.r.p10.toforward=0;L3r.r.p01.lock=0;}
od;

}
active[1] proctype R7P3() {
do
::atomic{((Sub1.u.r.p11.toforward==0)&&(nempty(Sub1.u.r.p11.in))); Sub1.u.r.p11.in?Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=1;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==DEBUT)&&(nfull(Sub1.d.l.p11.in))&&(Sub1.u.r.p11.mess.dest/4==1)&&((Sub1.u.r.p11.mess.dest/2)%2==0)&&(Sub1.d.l.p11.lock==0)); Sub1.u.r.p11.redirect=0; Sub1.d.l.p11.lock=1; Sub1.d.l.p11.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==DEBUT)&&(nfull(Sub1.d.r.p11.in))&&(Sub1.u.r.p11.mess.dest/4==1)&&((Sub1.u.r.p11.mess.dest/2)%2==1)&&(Sub1.d.r.p11.lock==0)); Sub1.u.r.p11.redirect=1; Sub1.d.r.p11.lock=1; Sub1.d.r.p11.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==DEBUT)&&(nfull(L3l.r.p01.in))&&(Sub1.u.r.p11.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3l.r.p01.lock==0)); Sub1.u.r.p11.redirect=2; L3l.r.p01.lock=1; L3l.r.p01.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==DEBUT)&&(nfull(L3r.r.p01.in))&&(Sub1.u.r.p11.mess.dest/4!=1)&&(1/*Mode non Séparé*/)&&(L3r.r.p01.lock==0)); Sub1.u.r.p11.redirect=3; L3r.r.p01.lock=1; L3r.r.p01.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==INTER)&&(Sub1.u.r.p11.redirect==0)&&(nfull(Sub1.d.l.p11.in))); Sub1.d.l.p11.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==INTER)&&(Sub1.u.r.p11.redirect==1)&&(nfull(Sub1.d.r.p11.in))); Sub1.d.r.p11.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==INTER)&&(Sub1.u.r.p11.redirect==2)&&(nfull(L3l.r.p01.in))); L3l.r.p01.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==INTER)&&(Sub1.u.r.p11.redirect==3)&&(nfull(L3r.r.p01.in))); L3r.r.p01.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==FIN)&&(Sub1.u.r.p11.redirect==0)&&(nfull(Sub1.d.l.p11.in))); Sub1.d.l.p11.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;Sub1.d.l.p11.lock=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==FIN)&&(Sub1.u.r.p11.redirect==1)&&(nfull(Sub1.d.r.p11.in))); Sub1.d.r.p11.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;Sub1.d.r.p11.lock=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==FIN)&&(Sub1.u.r.p11.redirect==2)&&(nfull(L3l.r.p01.in))); L3l.r.p01.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;L3l.r.p01.lock=0;}
::atomic{((Sub1.u.r.p11.toforward==1)&&(Sub1.u.r.p11.mess.tag==FIN)&&(Sub1.u.r.p11.redirect==3)&&(nfull(L3r.r.p01.in))); L3r.r.p01.in!Sub1.u.r.p11.mess; Sub1.u.r.p11.toforward=0;L3r.r.p01.lock=0;}
od;

}
active[1] proctype R8P0() {
do
::atomic{((L3l.l.p00.toforward==0)&&(nempty(L3l.l.p00.in))); L3l.l.p00.in?L3l.l.p00.mess; L3l.l.p00.toforward=1;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p10.in))&&(L3l.l.p00.mess.dest/8==0)&&((L3l.l.p00.mess.dest/4)%2==0)&&(Sub0.u.l.p10.lock==0)); L3l.l.p00.redirect=0; Sub0.u.l.p10.lock=1; Sub0.u.l.p10.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p10.in))&&(L3l.l.p00.mess.dest/8==0)&&((L3l.l.p00.mess.dest/4)%2==1)&&(Sub1.u.l.p10.lock==0)); L3l.l.p00.redirect=1; Sub1.u.l.p10.lock=1; Sub1.u.l.p10.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==DEBUT)&&(nfull(L3l.l.p10.in))&&(L3l.l.p00.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p10.lock==0)); L3l.l.p00.redirect=2; L3l.l.p10.lock=1; L3l.l.p10.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==DEBUT)&&(nfull(L3l.l.p11.in))&&(L3l.l.p00.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p11.lock==0)); L3l.l.p00.redirect=3; L3l.l.p11.lock=1; L3l.l.p11.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==INTER)&&(L3l.l.p00.redirect==0)&&(nfull(Sub0.u.l.p10.in))); Sub0.u.l.p10.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==INTER)&&(L3l.l.p00.redirect==1)&&(nfull(Sub1.u.l.p10.in))); Sub1.u.l.p10.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==INTER)&&(L3l.l.p00.redirect==2)&&(nfull(L3l.l.p10.in))); L3l.l.p10.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==INTER)&&(L3l.l.p00.redirect==3)&&(nfull(L3l.l.p11.in))); L3l.l.p11.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==FIN)&&(L3l.l.p00.redirect==0)&&(nfull(Sub0.u.l.p10.in))); Sub0.u.l.p10.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;Sub0.u.l.p10.lock=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==FIN)&&(L3l.l.p00.redirect==1)&&(nfull(Sub1.u.l.p10.in))); Sub1.u.l.p10.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;Sub1.u.l.p10.lock=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==FIN)&&(L3l.l.p00.redirect==2)&&(nfull(L3l.l.p10.in))); L3l.l.p10.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;L3l.l.p10.lock=0;}
::atomic{((L3l.l.p00.toforward==1)&&(L3l.l.p00.mess.tag==FIN)&&(L3l.l.p00.redirect==3)&&(nfull(L3l.l.p11.in))); L3l.l.p11.in!L3l.l.p00.mess; L3l.l.p00.toforward=0;L3l.l.p11.lock=0;}
od;

}
active[1] proctype R8P1() {
do
::atomic{((L3l.l.p01.toforward==0)&&(nempty(L3l.l.p01.in))); L3l.l.p01.in?L3l.l.p01.mess; L3l.l.p01.toforward=1;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p10.in))&&(L3l.l.p01.mess.dest/8==0)&&((L3l.l.p01.mess.dest/4)%2==0)&&(Sub0.u.l.p10.lock==0)); L3l.l.p01.redirect=0; Sub0.u.l.p10.lock=1; Sub0.u.l.p10.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p10.in))&&(L3l.l.p01.mess.dest/8==0)&&((L3l.l.p01.mess.dest/4)%2==1)&&(Sub1.u.l.p10.lock==0)); L3l.l.p01.redirect=1; Sub1.u.l.p10.lock=1; Sub1.u.l.p10.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==DEBUT)&&(nfull(L3l.l.p10.in))&&(L3l.l.p01.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p10.lock==0)); L3l.l.p01.redirect=2; L3l.l.p10.lock=1; L3l.l.p10.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==DEBUT)&&(nfull(L3l.l.p11.in))&&(L3l.l.p01.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p11.lock==0)); L3l.l.p01.redirect=3; L3l.l.p11.lock=1; L3l.l.p11.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==INTER)&&(L3l.l.p01.redirect==0)&&(nfull(Sub0.u.l.p10.in))); Sub0.u.l.p10.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==INTER)&&(L3l.l.p01.redirect==1)&&(nfull(Sub1.u.l.p10.in))); Sub1.u.l.p10.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==INTER)&&(L3l.l.p01.redirect==2)&&(nfull(L3l.l.p10.in))); L3l.l.p10.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==INTER)&&(L3l.l.p01.redirect==3)&&(nfull(L3l.l.p11.in))); L3l.l.p11.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==FIN)&&(L3l.l.p01.redirect==0)&&(nfull(Sub0.u.l.p10.in))); Sub0.u.l.p10.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;Sub0.u.l.p10.lock=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==FIN)&&(L3l.l.p01.redirect==1)&&(nfull(Sub1.u.l.p10.in))); Sub1.u.l.p10.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;Sub1.u.l.p10.lock=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==FIN)&&(L3l.l.p01.redirect==2)&&(nfull(L3l.l.p10.in))); L3l.l.p10.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;L3l.l.p10.lock=0;}
::atomic{((L3l.l.p01.toforward==1)&&(L3l.l.p01.mess.tag==FIN)&&(L3l.l.p01.redirect==3)&&(nfull(L3l.l.p11.in))); L3l.l.p11.in!L3l.l.p01.mess; L3l.l.p01.toforward=0;L3l.l.p11.lock=0;}
od;

}
active[1] proctype R8P2() {
do
::atomic{((L3l.l.p10.toforward==0)&&(nempty(L3l.l.p10.in))); L3l.l.p10.in?L3l.l.p10.mess; L3l.l.p10.toforward=1;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p10.in))&&(L3l.l.p10.mess.dest/8==0)&&((L3l.l.p10.mess.dest/4)%2==0)&&(Sub0.u.l.p10.lock==0)); L3l.l.p10.redirect=0; Sub0.u.l.p10.lock=1; Sub0.u.l.p10.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p10.in))&&(L3l.l.p10.mess.dest/8==0)&&((L3l.l.p10.mess.dest/4)%2==1)&&(Sub1.u.l.p10.lock==0)); L3l.l.p10.redirect=1; Sub1.u.l.p10.lock=1; Sub1.u.l.p10.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==DEBUT)&&(nfull(L3l.l.p10.in))&&(L3l.l.p10.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p10.lock==0)); L3l.l.p10.redirect=2; L3l.l.p10.lock=1; L3l.l.p10.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==DEBUT)&&(nfull(L3l.l.p11.in))&&(L3l.l.p10.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p11.lock==0)); L3l.l.p10.redirect=3; L3l.l.p11.lock=1; L3l.l.p11.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==INTER)&&(L3l.l.p10.redirect==0)&&(nfull(Sub0.u.l.p10.in))); Sub0.u.l.p10.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==INTER)&&(L3l.l.p10.redirect==1)&&(nfull(Sub1.u.l.p10.in))); Sub1.u.l.p10.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==INTER)&&(L3l.l.p10.redirect==2)&&(nfull(L3l.l.p10.in))); L3l.l.p10.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==INTER)&&(L3l.l.p10.redirect==3)&&(nfull(L3l.l.p11.in))); L3l.l.p11.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==FIN)&&(L3l.l.p10.redirect==0)&&(nfull(Sub0.u.l.p10.in))); Sub0.u.l.p10.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;Sub0.u.l.p10.lock=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==FIN)&&(L3l.l.p10.redirect==1)&&(nfull(Sub1.u.l.p10.in))); Sub1.u.l.p10.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;Sub1.u.l.p10.lock=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==FIN)&&(L3l.l.p10.redirect==2)&&(nfull(L3l.l.p10.in))); L3l.l.p10.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;L3l.l.p10.lock=0;}
::atomic{((L3l.l.p10.toforward==1)&&(L3l.l.p10.mess.tag==FIN)&&(L3l.l.p10.redirect==3)&&(nfull(L3l.l.p11.in))); L3l.l.p11.in!L3l.l.p10.mess; L3l.l.p10.toforward=0;L3l.l.p11.lock=0;}
od;

}
active[1] proctype R8P3() {
do
::atomic{((L3l.l.p11.toforward==0)&&(nempty(L3l.l.p11.in))); L3l.l.p11.in?L3l.l.p11.mess; L3l.l.p11.toforward=1;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p10.in))&&(L3l.l.p11.mess.dest/8==0)&&((L3l.l.p11.mess.dest/4)%2==0)&&(Sub0.u.l.p10.lock==0)); L3l.l.p11.redirect=0; Sub0.u.l.p10.lock=1; Sub0.u.l.p10.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p10.in))&&(L3l.l.p11.mess.dest/8==0)&&((L3l.l.p11.mess.dest/4)%2==1)&&(Sub1.u.l.p10.lock==0)); L3l.l.p11.redirect=1; Sub1.u.l.p10.lock=1; Sub1.u.l.p10.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==DEBUT)&&(nfull(L3l.l.p10.in))&&(L3l.l.p11.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p10.lock==0)); L3l.l.p11.redirect=2; L3l.l.p10.lock=1; L3l.l.p10.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==DEBUT)&&(nfull(L3l.l.p11.in))&&(L3l.l.p11.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.l.p11.lock==0)); L3l.l.p11.redirect=3; L3l.l.p11.lock=1; L3l.l.p11.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==INTER)&&(L3l.l.p11.redirect==0)&&(nfull(Sub0.u.l.p10.in))); Sub0.u.l.p10.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==INTER)&&(L3l.l.p11.redirect==1)&&(nfull(Sub1.u.l.p10.in))); Sub1.u.l.p10.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==INTER)&&(L3l.l.p11.redirect==2)&&(nfull(L3l.l.p10.in))); L3l.l.p10.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==INTER)&&(L3l.l.p11.redirect==3)&&(nfull(L3l.l.p11.in))); L3l.l.p11.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==FIN)&&(L3l.l.p11.redirect==0)&&(nfull(Sub0.u.l.p10.in))); Sub0.u.l.p10.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;Sub0.u.l.p10.lock=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==FIN)&&(L3l.l.p11.redirect==1)&&(nfull(Sub1.u.l.p10.in))); Sub1.u.l.p10.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;Sub1.u.l.p10.lock=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==FIN)&&(L3l.l.p11.redirect==2)&&(nfull(L3l.l.p10.in))); L3l.l.p10.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;L3l.l.p10.lock=0;}
::atomic{((L3l.l.p11.toforward==1)&&(L3l.l.p11.mess.tag==FIN)&&(L3l.l.p11.redirect==3)&&(nfull(L3l.l.p11.in))); L3l.l.p11.in!L3l.l.p11.mess; L3l.l.p11.toforward=0;L3l.l.p11.lock=0;}
od;

}
active[1] proctype R9P0() {
do
::atomic{((L3l.r.p00.toforward==0)&&(nempty(L3l.r.p00.in))); L3l.r.p00.in?L3l.r.p00.mess; L3l.r.p00.toforward=1;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p10.in))&&(L3l.r.p00.mess.dest/8==0)&&((L3l.r.p00.mess.dest/4)%2==0)&&(Sub0.u.r.p10.lock==0)); L3l.r.p00.redirect=0; Sub0.u.r.p10.lock=1; Sub0.u.r.p10.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p10.in))&&(L3l.r.p00.mess.dest/8==0)&&((L3l.r.p00.mess.dest/4)%2==1)&&(Sub1.u.r.p10.lock==0)); L3l.r.p00.redirect=1; Sub1.u.r.p10.lock=1; Sub1.u.r.p10.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==DEBUT)&&(nfull(L3l.r.p10.in))&&(L3l.r.p00.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p10.lock==0)); L3l.r.p00.redirect=2; L3l.r.p10.lock=1; L3l.r.p10.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==DEBUT)&&(nfull(L3l.r.p11.in))&&(L3l.r.p00.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p11.lock==0)); L3l.r.p00.redirect=3; L3l.r.p11.lock=1; L3l.r.p11.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==INTER)&&(L3l.r.p00.redirect==0)&&(nfull(Sub0.u.r.p10.in))); Sub0.u.r.p10.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==INTER)&&(L3l.r.p00.redirect==1)&&(nfull(Sub1.u.r.p10.in))); Sub1.u.r.p10.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==INTER)&&(L3l.r.p00.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==INTER)&&(L3l.r.p00.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==FIN)&&(L3l.r.p00.redirect==0)&&(nfull(Sub0.u.r.p10.in))); Sub0.u.r.p10.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;Sub0.u.r.p10.lock=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==FIN)&&(L3l.r.p00.redirect==1)&&(nfull(Sub1.u.r.p10.in))); Sub1.u.r.p10.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;Sub1.u.r.p10.lock=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==FIN)&&(L3l.r.p00.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;L3l.r.p10.lock=0;}
::atomic{((L3l.r.p00.toforward==1)&&(L3l.r.p00.mess.tag==FIN)&&(L3l.r.p00.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3l.r.p00.mess; L3l.r.p00.toforward=0;L3l.r.p11.lock=0;}
od;

}
active[1] proctype R9P1() {
do
::atomic{((L3l.r.p01.toforward==0)&&(nempty(L3l.r.p01.in))); L3l.r.p01.in?L3l.r.p01.mess; L3l.r.p01.toforward=1;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p10.in))&&(L3l.r.p01.mess.dest/8==0)&&((L3l.r.p01.mess.dest/4)%2==0)&&(Sub0.u.r.p10.lock==0)); L3l.r.p01.redirect=0; Sub0.u.r.p10.lock=1; Sub0.u.r.p10.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p10.in))&&(L3l.r.p01.mess.dest/8==0)&&((L3l.r.p01.mess.dest/4)%2==1)&&(Sub1.u.r.p10.lock==0)); L3l.r.p01.redirect=1; Sub1.u.r.p10.lock=1; Sub1.u.r.p10.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==DEBUT)&&(nfull(L3l.r.p10.in))&&(L3l.r.p01.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p10.lock==0)); L3l.r.p01.redirect=2; L3l.r.p10.lock=1; L3l.r.p10.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==DEBUT)&&(nfull(L3l.r.p11.in))&&(L3l.r.p01.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p11.lock==0)); L3l.r.p01.redirect=3; L3l.r.p11.lock=1; L3l.r.p11.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==INTER)&&(L3l.r.p01.redirect==0)&&(nfull(Sub0.u.r.p10.in))); Sub0.u.r.p10.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==INTER)&&(L3l.r.p01.redirect==1)&&(nfull(Sub1.u.r.p10.in))); Sub1.u.r.p10.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==INTER)&&(L3l.r.p01.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==INTER)&&(L3l.r.p01.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==FIN)&&(L3l.r.p01.redirect==0)&&(nfull(Sub0.u.r.p10.in))); Sub0.u.r.p10.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;Sub0.u.r.p10.lock=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==FIN)&&(L3l.r.p01.redirect==1)&&(nfull(Sub1.u.r.p10.in))); Sub1.u.r.p10.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;Sub1.u.r.p10.lock=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==FIN)&&(L3l.r.p01.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;L3l.r.p10.lock=0;}
::atomic{((L3l.r.p01.toforward==1)&&(L3l.r.p01.mess.tag==FIN)&&(L3l.r.p01.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3l.r.p01.mess; L3l.r.p01.toforward=0;L3l.r.p11.lock=0;}
od;

}
active[1] proctype R9P2() {
do
::atomic{((L3l.r.p10.toforward==0)&&(nempty(L3l.r.p10.in))); L3l.r.p10.in?L3l.r.p10.mess; L3l.r.p10.toforward=1;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p10.in))&&(L3l.r.p10.mess.dest/8==0)&&((L3l.r.p10.mess.dest/4)%2==0)&&(Sub0.u.r.p10.lock==0)); L3l.r.p10.redirect=0; Sub0.u.r.p10.lock=1; Sub0.u.r.p10.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p10.in))&&(L3l.r.p10.mess.dest/8==0)&&((L3l.r.p10.mess.dest/4)%2==1)&&(Sub1.u.r.p10.lock==0)); L3l.r.p10.redirect=1; Sub1.u.r.p10.lock=1; Sub1.u.r.p10.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==DEBUT)&&(nfull(L3l.r.p10.in))&&(L3l.r.p10.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p10.lock==0)); L3l.r.p10.redirect=2; L3l.r.p10.lock=1; L3l.r.p10.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==DEBUT)&&(nfull(L3l.r.p11.in))&&(L3l.r.p10.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p11.lock==0)); L3l.r.p10.redirect=3; L3l.r.p11.lock=1; L3l.r.p11.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==INTER)&&(L3l.r.p10.redirect==0)&&(nfull(Sub0.u.r.p10.in))); Sub0.u.r.p10.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==INTER)&&(L3l.r.p10.redirect==1)&&(nfull(Sub1.u.r.p10.in))); Sub1.u.r.p10.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==INTER)&&(L3l.r.p10.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==INTER)&&(L3l.r.p10.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==FIN)&&(L3l.r.p10.redirect==0)&&(nfull(Sub0.u.r.p10.in))); Sub0.u.r.p10.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;Sub0.u.r.p10.lock=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==FIN)&&(L3l.r.p10.redirect==1)&&(nfull(Sub1.u.r.p10.in))); Sub1.u.r.p10.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;Sub1.u.r.p10.lock=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==FIN)&&(L3l.r.p10.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;L3l.r.p10.lock=0;}
::atomic{((L3l.r.p10.toforward==1)&&(L3l.r.p10.mess.tag==FIN)&&(L3l.r.p10.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3l.r.p10.mess; L3l.r.p10.toforward=0;L3l.r.p11.lock=0;}
od;

}
active[1] proctype R9P3() {
do
::atomic{((L3l.r.p11.toforward==0)&&(nempty(L3l.r.p11.in))); L3l.r.p11.in?L3l.r.p11.mess; L3l.r.p11.toforward=1;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p10.in))&&(L3l.r.p11.mess.dest/8==0)&&((L3l.r.p11.mess.dest/4)%2==0)&&(Sub0.u.r.p10.lock==0)); L3l.r.p11.redirect=0; Sub0.u.r.p10.lock=1; Sub0.u.r.p10.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p10.in))&&(L3l.r.p11.mess.dest/8==0)&&((L3l.r.p11.mess.dest/4)%2==1)&&(Sub1.u.r.p10.lock==0)); L3l.r.p11.redirect=1; Sub1.u.r.p10.lock=1; Sub1.u.r.p10.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==DEBUT)&&(nfull(L3l.r.p10.in))&&(L3l.r.p11.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p10.lock==0)); L3l.r.p11.redirect=2; L3l.r.p10.lock=1; L3l.r.p10.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==DEBUT)&&(nfull(L3l.r.p11.in))&&(L3l.r.p11.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p11.lock==0)); L3l.r.p11.redirect=3; L3l.r.p11.lock=1; L3l.r.p11.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==INTER)&&(L3l.r.p11.redirect==0)&&(nfull(Sub0.u.r.p10.in))); Sub0.u.r.p10.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==INTER)&&(L3l.r.p11.redirect==1)&&(nfull(Sub1.u.r.p10.in))); Sub1.u.r.p10.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==INTER)&&(L3l.r.p11.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==INTER)&&(L3l.r.p11.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==FIN)&&(L3l.r.p11.redirect==0)&&(nfull(Sub0.u.r.p10.in))); Sub0.u.r.p10.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;Sub0.u.r.p10.lock=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==FIN)&&(L3l.r.p11.redirect==1)&&(nfull(Sub1.u.r.p10.in))); Sub1.u.r.p10.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;Sub1.u.r.p10.lock=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==FIN)&&(L3l.r.p11.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;L3l.r.p10.lock=0;}
::atomic{((L3l.r.p11.toforward==1)&&(L3l.r.p11.mess.tag==FIN)&&(L3l.r.p11.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3l.r.p11.mess; L3l.r.p11.toforward=0;L3l.r.p11.lock=0;}
od;

}
active[1] proctype R10P0() {
do
::atomic{((L3r.l.p00.toforward==0)&&(nempty(L3r.l.p00.in))); L3r.l.p00.in?L3r.l.p00.mess; L3r.l.p00.toforward=1;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p11.in))&&(L3r.l.p00.mess.dest/8==0)&&((L3r.l.p00.mess.dest/4)%2==0)&&(Sub0.u.l.p11.lock==0)); L3r.l.p00.redirect=0; Sub0.u.l.p11.lock=1; Sub0.u.l.p11.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p11.in))&&(L3r.l.p00.mess.dest/8==0)&&((L3r.l.p00.mess.dest/4)%2==1)&&(Sub1.u.l.p11.lock==0)); L3r.l.p00.redirect=1; Sub1.u.l.p11.lock=1; Sub1.u.l.p11.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==DEBUT)&&(nfull(L3l.r.p10.in))&&(L3r.l.p00.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p10.lock==0)); L3r.l.p00.redirect=2; L3l.r.p10.lock=1; L3l.r.p10.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==DEBUT)&&(nfull(L3l.r.p11.in))&&(L3r.l.p00.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p11.lock==0)); L3r.l.p00.redirect=3; L3l.r.p11.lock=1; L3l.r.p11.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==INTER)&&(L3r.l.p00.redirect==0)&&(nfull(Sub0.u.l.p11.in))); Sub0.u.l.p11.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==INTER)&&(L3r.l.p00.redirect==1)&&(nfull(Sub1.u.l.p11.in))); Sub1.u.l.p11.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==INTER)&&(L3r.l.p00.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==INTER)&&(L3r.l.p00.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==FIN)&&(L3r.l.p00.redirect==0)&&(nfull(Sub0.u.l.p11.in))); Sub0.u.l.p11.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;Sub0.u.l.p11.lock=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==FIN)&&(L3r.l.p00.redirect==1)&&(nfull(Sub1.u.l.p11.in))); Sub1.u.l.p11.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;Sub1.u.l.p11.lock=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==FIN)&&(L3r.l.p00.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;L3l.r.p10.lock=0;}
::atomic{((L3r.l.p00.toforward==1)&&(L3r.l.p00.mess.tag==FIN)&&(L3r.l.p00.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3r.l.p00.mess; L3r.l.p00.toforward=0;L3l.r.p11.lock=0;}
od;

}
active[1] proctype R10P1() {
do
::atomic{((L3r.l.p01.toforward==0)&&(nempty(L3r.l.p01.in))); L3r.l.p01.in?L3r.l.p01.mess; L3r.l.p01.toforward=1;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p11.in))&&(L3r.l.p01.mess.dest/8==0)&&((L3r.l.p01.mess.dest/4)%2==0)&&(Sub0.u.l.p11.lock==0)); L3r.l.p01.redirect=0; Sub0.u.l.p11.lock=1; Sub0.u.l.p11.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p11.in))&&(L3r.l.p01.mess.dest/8==0)&&((L3r.l.p01.mess.dest/4)%2==1)&&(Sub1.u.l.p11.lock==0)); L3r.l.p01.redirect=1; Sub1.u.l.p11.lock=1; Sub1.u.l.p11.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==DEBUT)&&(nfull(L3l.r.p10.in))&&(L3r.l.p01.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p10.lock==0)); L3r.l.p01.redirect=2; L3l.r.p10.lock=1; L3l.r.p10.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==DEBUT)&&(nfull(L3l.r.p11.in))&&(L3r.l.p01.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p11.lock==0)); L3r.l.p01.redirect=3; L3l.r.p11.lock=1; L3l.r.p11.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==INTER)&&(L3r.l.p01.redirect==0)&&(nfull(Sub0.u.l.p11.in))); Sub0.u.l.p11.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==INTER)&&(L3r.l.p01.redirect==1)&&(nfull(Sub1.u.l.p11.in))); Sub1.u.l.p11.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==INTER)&&(L3r.l.p01.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==INTER)&&(L3r.l.p01.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==FIN)&&(L3r.l.p01.redirect==0)&&(nfull(Sub0.u.l.p11.in))); Sub0.u.l.p11.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;Sub0.u.l.p11.lock=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==FIN)&&(L3r.l.p01.redirect==1)&&(nfull(Sub1.u.l.p11.in))); Sub1.u.l.p11.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;Sub1.u.l.p11.lock=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==FIN)&&(L3r.l.p01.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;L3l.r.p10.lock=0;}
::atomic{((L3r.l.p01.toforward==1)&&(L3r.l.p01.mess.tag==FIN)&&(L3r.l.p01.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3r.l.p01.mess; L3r.l.p01.toforward=0;L3l.r.p11.lock=0;}
od;

}
active[1] proctype R10P2() {
do
::atomic{((L3r.l.p10.toforward==0)&&(nempty(L3r.l.p10.in))); L3r.l.p10.in?L3r.l.p10.mess; L3r.l.p10.toforward=1;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p11.in))&&(L3r.l.p10.mess.dest/8==0)&&((L3r.l.p10.mess.dest/4)%2==0)&&(Sub0.u.l.p11.lock==0)); L3r.l.p10.redirect=0; Sub0.u.l.p11.lock=1; Sub0.u.l.p11.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p11.in))&&(L3r.l.p10.mess.dest/8==0)&&((L3r.l.p10.mess.dest/4)%2==1)&&(Sub1.u.l.p11.lock==0)); L3r.l.p10.redirect=1; Sub1.u.l.p11.lock=1; Sub1.u.l.p11.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==DEBUT)&&(nfull(L3l.r.p10.in))&&(L3r.l.p10.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p10.lock==0)); L3r.l.p10.redirect=2; L3l.r.p10.lock=1; L3l.r.p10.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==DEBUT)&&(nfull(L3l.r.p11.in))&&(L3r.l.p10.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p11.lock==0)); L3r.l.p10.redirect=3; L3l.r.p11.lock=1; L3l.r.p11.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==INTER)&&(L3r.l.p10.redirect==0)&&(nfull(Sub0.u.l.p11.in))); Sub0.u.l.p11.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==INTER)&&(L3r.l.p10.redirect==1)&&(nfull(Sub1.u.l.p11.in))); Sub1.u.l.p11.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==INTER)&&(L3r.l.p10.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==INTER)&&(L3r.l.p10.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==FIN)&&(L3r.l.p10.redirect==0)&&(nfull(Sub0.u.l.p11.in))); Sub0.u.l.p11.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;Sub0.u.l.p11.lock=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==FIN)&&(L3r.l.p10.redirect==1)&&(nfull(Sub1.u.l.p11.in))); Sub1.u.l.p11.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;Sub1.u.l.p11.lock=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==FIN)&&(L3r.l.p10.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;L3l.r.p10.lock=0;}
::atomic{((L3r.l.p10.toforward==1)&&(L3r.l.p10.mess.tag==FIN)&&(L3r.l.p10.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3r.l.p10.mess; L3r.l.p10.toforward=0;L3l.r.p11.lock=0;}
od;

}
active[1] proctype R10P3() {
do
::atomic{((L3r.l.p11.toforward==0)&&(nempty(L3r.l.p11.in))); L3r.l.p11.in?L3r.l.p11.mess; L3r.l.p11.toforward=1;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==DEBUT)&&(nfull(Sub0.u.l.p11.in))&&(L3r.l.p11.mess.dest/8==0)&&((L3r.l.p11.mess.dest/4)%2==0)&&(Sub0.u.l.p11.lock==0)); L3r.l.p11.redirect=0; Sub0.u.l.p11.lock=1; Sub0.u.l.p11.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==DEBUT)&&(nfull(Sub1.u.l.p11.in))&&(L3r.l.p11.mess.dest/8==0)&&((L3r.l.p11.mess.dest/4)%2==1)&&(Sub1.u.l.p11.lock==0)); L3r.l.p11.redirect=1; Sub1.u.l.p11.lock=1; Sub1.u.l.p11.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==DEBUT)&&(nfull(L3l.r.p10.in))&&(L3r.l.p11.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p10.lock==0)); L3r.l.p11.redirect=2; L3l.r.p10.lock=1; L3l.r.p10.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==DEBUT)&&(nfull(L3l.r.p11.in))&&(L3r.l.p11.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3l.r.p11.lock==0)); L3r.l.p11.redirect=3; L3l.r.p11.lock=1; L3l.r.p11.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==INTER)&&(L3r.l.p11.redirect==0)&&(nfull(Sub0.u.l.p11.in))); Sub0.u.l.p11.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==INTER)&&(L3r.l.p11.redirect==1)&&(nfull(Sub1.u.l.p11.in))); Sub1.u.l.p11.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==INTER)&&(L3r.l.p11.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==INTER)&&(L3r.l.p11.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==FIN)&&(L3r.l.p11.redirect==0)&&(nfull(Sub0.u.l.p11.in))); Sub0.u.l.p11.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;Sub0.u.l.p11.lock=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==FIN)&&(L3r.l.p11.redirect==1)&&(nfull(Sub1.u.l.p11.in))); Sub1.u.l.p11.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;Sub1.u.l.p11.lock=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==FIN)&&(L3r.l.p11.redirect==2)&&(nfull(L3l.r.p10.in))); L3l.r.p10.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;L3l.r.p10.lock=0;}
::atomic{((L3r.l.p11.toforward==1)&&(L3r.l.p11.mess.tag==FIN)&&(L3r.l.p11.redirect==3)&&(nfull(L3l.r.p11.in))); L3l.r.p11.in!L3r.l.p11.mess; L3r.l.p11.toforward=0;L3l.r.p11.lock=0;}
od;

}
active[1] proctype R11P0() {
do
::atomic{((L3r.r.p00.toforward==0)&&(nempty(L3r.r.p00.in))); L3r.r.p00.in?L3r.r.p00.mess; L3r.r.p00.toforward=1;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p11.in))&&(L3r.r.p00.mess.dest/8==0)&&((L3r.r.p00.mess.dest/4)%2==0)&&(Sub0.u.r.p11.lock==0)); L3r.r.p00.redirect=0; Sub0.u.r.p11.lock=1; Sub0.u.r.p11.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p11.in))&&(L3r.r.p00.mess.dest/8==0)&&((L3r.r.p00.mess.dest/4)%2==1)&&(Sub1.u.r.p11.lock==0)); L3r.r.p00.redirect=1; Sub1.u.r.p11.lock=1; Sub1.u.r.p11.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==DEBUT)&&(nfull(L3r.r.p10.in))&&(L3r.r.p00.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3r.r.p10.lock==0)); L3r.r.p00.redirect=2; L3r.r.p10.lock=1; L3r.r.p10.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==DEBUT)&&(nfull(L3r.r.p11.in))&&(L3r.r.p00.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3r.r.p11.lock==0)); L3r.r.p00.redirect=3; L3r.r.p11.lock=1; L3r.r.p11.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==INTER)&&(L3r.r.p00.redirect==0)&&(nfull(Sub0.u.r.p11.in))); Sub0.u.r.p11.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==INTER)&&(L3r.r.p00.redirect==1)&&(nfull(Sub1.u.r.p11.in))); Sub1.u.r.p11.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==INTER)&&(L3r.r.p00.redirect==2)&&(nfull(L3r.r.p10.in))); L3r.r.p10.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==INTER)&&(L3r.r.p00.redirect==3)&&(nfull(L3r.r.p11.in))); L3r.r.p11.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==FIN)&&(L3r.r.p00.redirect==0)&&(nfull(Sub0.u.r.p11.in))); Sub0.u.r.p11.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;Sub0.u.r.p11.lock=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==FIN)&&(L3r.r.p00.redirect==1)&&(nfull(Sub1.u.r.p11.in))); Sub1.u.r.p11.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;Sub1.u.r.p11.lock=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==FIN)&&(L3r.r.p00.redirect==2)&&(nfull(L3r.r.p10.in))); L3r.r.p10.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;L3r.r.p10.lock=0;}
::atomic{((L3r.r.p00.toforward==1)&&(L3r.r.p00.mess.tag==FIN)&&(L3r.r.p00.redirect==3)&&(nfull(L3r.r.p11.in))); L3r.r.p11.in!L3r.r.p00.mess; L3r.r.p00.toforward=0;L3r.r.p11.lock=0;}
od;

}
active[1] proctype R11P1() {
do
::atomic{((L3r.r.p01.toforward==0)&&(nempty(L3r.r.p01.in))); L3r.r.p01.in?L3r.r.p01.mess; L3r.r.p01.toforward=1;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p11.in))&&(L3r.r.p01.mess.dest/8==0)&&((L3r.r.p01.mess.dest/4)%2==0)&&(Sub0.u.r.p11.lock==0)); L3r.r.p01.redirect=0; Sub0.u.r.p11.lock=1; Sub0.u.r.p11.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p11.in))&&(L3r.r.p01.mess.dest/8==0)&&((L3r.r.p01.mess.dest/4)%2==1)&&(Sub1.u.r.p11.lock==0)); L3r.r.p01.redirect=1; Sub1.u.r.p11.lock=1; Sub1.u.r.p11.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==DEBUT)&&(nfull(L3r.r.p10.in))&&(L3r.r.p01.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3r.r.p10.lock==0)); L3r.r.p01.redirect=2; L3r.r.p10.lock=1; L3r.r.p10.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==DEBUT)&&(nfull(L3r.r.p11.in))&&(L3r.r.p01.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3r.r.p11.lock==0)); L3r.r.p01.redirect=3; L3r.r.p11.lock=1; L3r.r.p11.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==INTER)&&(L3r.r.p01.redirect==0)&&(nfull(Sub0.u.r.p11.in))); Sub0.u.r.p11.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==INTER)&&(L3r.r.p01.redirect==1)&&(nfull(Sub1.u.r.p11.in))); Sub1.u.r.p11.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==INTER)&&(L3r.r.p01.redirect==2)&&(nfull(L3r.r.p10.in))); L3r.r.p10.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==INTER)&&(L3r.r.p01.redirect==3)&&(nfull(L3r.r.p11.in))); L3r.r.p11.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==FIN)&&(L3r.r.p01.redirect==0)&&(nfull(Sub0.u.r.p11.in))); Sub0.u.r.p11.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;Sub0.u.r.p11.lock=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==FIN)&&(L3r.r.p01.redirect==1)&&(nfull(Sub1.u.r.p11.in))); Sub1.u.r.p11.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;Sub1.u.r.p11.lock=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==FIN)&&(L3r.r.p01.redirect==2)&&(nfull(L3r.r.p10.in))); L3r.r.p10.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;L3r.r.p10.lock=0;}
::atomic{((L3r.r.p01.toforward==1)&&(L3r.r.p01.mess.tag==FIN)&&(L3r.r.p01.redirect==3)&&(nfull(L3r.r.p11.in))); L3r.r.p11.in!L3r.r.p01.mess; L3r.r.p01.toforward=0;L3r.r.p11.lock=0;}
od;

}
active[1] proctype R11P2() {
do
::atomic{((L3r.r.p10.toforward==0)&&(nempty(L3r.r.p10.in))); L3r.r.p10.in?L3r.r.p10.mess; L3r.r.p10.toforward=1;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p11.in))&&(L3r.r.p10.mess.dest/8==0)&&((L3r.r.p10.mess.dest/4)%2==0)&&(Sub0.u.r.p11.lock==0)); L3r.r.p10.redirect=0; Sub0.u.r.p11.lock=1; Sub0.u.r.p11.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p11.in))&&(L3r.r.p10.mess.dest/8==0)&&((L3r.r.p10.mess.dest/4)%2==1)&&(Sub1.u.r.p11.lock==0)); L3r.r.p10.redirect=1; Sub1.u.r.p11.lock=1; Sub1.u.r.p11.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==DEBUT)&&(nfull(L3r.r.p10.in))&&(L3r.r.p10.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3r.r.p10.lock==0)); L3r.r.p10.redirect=2; L3r.r.p10.lock=1; L3r.r.p10.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==DEBUT)&&(nfull(L3r.r.p11.in))&&(L3r.r.p10.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3r.r.p11.lock==0)); L3r.r.p10.redirect=3; L3r.r.p11.lock=1; L3r.r.p11.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==INTER)&&(L3r.r.p10.redirect==0)&&(nfull(Sub0.u.r.p11.in))); Sub0.u.r.p11.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==INTER)&&(L3r.r.p10.redirect==1)&&(nfull(Sub1.u.r.p11.in))); Sub1.u.r.p11.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==INTER)&&(L3r.r.p10.redirect==2)&&(nfull(L3r.r.p10.in))); L3r.r.p10.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==INTER)&&(L3r.r.p10.redirect==3)&&(nfull(L3r.r.p11.in))); L3r.r.p11.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==FIN)&&(L3r.r.p10.redirect==0)&&(nfull(Sub0.u.r.p11.in))); Sub0.u.r.p11.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;Sub0.u.r.p11.lock=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==FIN)&&(L3r.r.p10.redirect==1)&&(nfull(Sub1.u.r.p11.in))); Sub1.u.r.p11.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;Sub1.u.r.p11.lock=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==FIN)&&(L3r.r.p10.redirect==2)&&(nfull(L3r.r.p10.in))); L3r.r.p10.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;L3r.r.p10.lock=0;}
::atomic{((L3r.r.p10.toforward==1)&&(L3r.r.p10.mess.tag==FIN)&&(L3r.r.p10.redirect==3)&&(nfull(L3r.r.p11.in))); L3r.r.p11.in!L3r.r.p10.mess; L3r.r.p10.toforward=0;L3r.r.p11.lock=0;}
od;

}
active[1] proctype R11P3() {
do
::atomic{((L3r.r.p11.toforward==0)&&(nempty(L3r.r.p11.in))); L3r.r.p11.in?L3r.r.p11.mess; L3r.r.p11.toforward=1;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==DEBUT)&&(nfull(Sub0.u.r.p11.in))&&(L3r.r.p11.mess.dest/8==0)&&((L3r.r.p11.mess.dest/4)%2==0)&&(Sub0.u.r.p11.lock==0)); L3r.r.p11.redirect=0; Sub0.u.r.p11.lock=1; Sub0.u.r.p11.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==DEBUT)&&(nfull(Sub1.u.r.p11.in))&&(L3r.r.p11.mess.dest/8==0)&&((L3r.r.p11.mess.dest/4)%2==1)&&(Sub1.u.r.p11.lock==0)); L3r.r.p11.redirect=1; Sub1.u.r.p11.lock=1; Sub1.u.r.p11.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==DEBUT)&&(nfull(L3r.r.p10.in))&&(L3r.r.p11.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3r.r.p10.lock==0)); L3r.r.p11.redirect=2; L3r.r.p10.lock=1; L3r.r.p10.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==DEBUT)&&(nfull(L3r.r.p11.in))&&(L3r.r.p11.mess.dest/8!=0)&&(1/*Mode non Séparé*/)&&(L3r.r.p11.lock==0)); L3r.r.p11.redirect=3; L3r.r.p11.lock=1; L3r.r.p11.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==INTER)&&(L3r.r.p11.redirect==0)&&(nfull(Sub0.u.r.p11.in))); Sub0.u.r.p11.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==INTER)&&(L3r.r.p11.redirect==1)&&(nfull(Sub1.u.r.p11.in))); Sub1.u.r.p11.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==INTER)&&(L3r.r.p11.redirect==2)&&(nfull(L3r.r.p10.in))); L3r.r.p10.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==INTER)&&(L3r.r.p11.redirect==3)&&(nfull(L3r.r.p11.in))); L3r.r.p11.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==FIN)&&(L3r.r.p11.redirect==0)&&(nfull(Sub0.u.r.p11.in))); Sub0.u.r.p11.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;Sub0.u.r.p11.lock=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==FIN)&&(L3r.r.p11.redirect==1)&&(nfull(Sub1.u.r.p11.in))); Sub1.u.r.p11.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;Sub1.u.r.p11.lock=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==FIN)&&(L3r.r.p11.redirect==2)&&(nfull(L3r.r.p10.in))); L3r.r.p10.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;L3r.r.p10.lock=0;}
::atomic{((L3r.r.p11.toforward==1)&&(L3r.r.p11.mess.tag==FIN)&&(L3r.r.p11.redirect==3)&&(nfull(L3r.r.p11.in))); L3r.r.p11.in!L3r.r.p11.mess; L3r.r.p11.toforward=0;L3r.r.p11.lock=0;}
od;

}
