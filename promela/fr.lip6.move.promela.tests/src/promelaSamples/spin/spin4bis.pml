/*Spin 4 routers*/

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
};

typedef LayerInterface {
chan c00 = [1] of {Mot};
chan c01 = [1] of {Mot};
chan c10 = [1] of {Mot};
chan c11 = [1] of {Mot};
/*chan q00 = [1] of {Mot};
chan q01 = [1] of {Mot};
chan q10 = [1] of {Mot};
chan q11 = [1] of {Mot};
chan q20 = [1] of {Mot};
chan q21 = [1] of {Mot};
chan q30 = [1] of {Mot};
chan q31 = [1] of {Mot};*/
};

/*LayerInterface u30;
LayerInterface d30;*/
LayerInterface u20;
LayerInterface d20;
LayerInterface u10;
LayerInterface d10;
LayerInterface in0;
/*
LayerInterface u31;
LayerInterface d31;
LayerInterface u21;
LayerInterface d21;
LayerInterface u11;
LayerInterface d11;
LayerInterface in1;
*/
bit Ack0;
bit Ack2;
/*bit Ack4;
bit Ack6;
*/
active[1] proctype Envoi0() {
Mot mot;
int num=1;
mot.source = 0;
mot.type = REQUETE;
selectDest :

    mot.dest = 1;
    do
    ::(mot.dest<=3); break;
    ::(mot.dest<3); mot.dest=mot.dest+2;
    od;
    //mot.dest=1;
    sendMot:
    do
    ::atomic{ (num==1); mot.tag=DEBUT; in0.c00!mot;num++;}
    ::atomic{ (num==7); mot.tag=FIN; in0.c00!mot; num=1;break;}
    ::atomic{ (num>1); mot.tag=FIN; in0.c00!mot; num=1;break;}
    ::atomic{((num>1)&&(num<7)); mot.tag=INTER; in0.c00!mot; num++;}
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
   d10.c00?mot;
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
    do
    ::(mot.dest<=3); break;
    ::(mot.dest<3); mot.dest=mot.dest+2;
    od;
    //mot.dest=1;
    sendMot:
    do
    ::atomic{ (num==1); mot.tag=DEBUT; in0.c10!mot;num++;}
    ::atomic{ (num==7); mot.tag=FIN; in0.c10!mot; num=1;break;}
    ::atomic{ (num>1); mot.tag=FIN; in0.c10!mot; num=1;break;}
    ::atomic{((num>1)&&(num<7)); mot.tag=INTER; in0.c10!mot; num++;}
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
   d10.c10?mot;
   if
   ::(mot.tag==FIN); Ack2=1; goto Receive;
   ::else -> goto Receive;
   fi;
   }
   active[1] proctype Cible1 () {
            Mot mot;
            int t;
            do
            ::atomic{d10.c01?mot; mot.type=REPONSE;t=mot.source; mot.source=mot.dest; mot.dest=t; t=0;in0.c01!mot;}
            od;
            }
            active[1] proctype Cible3 () {
            Mot mot;
            int t;
            do
            ::atomic{d10.c11?mot; mot.type=REPONSE;t=mot.source; mot.source=mot.dest; mot.dest=t; t=0;in0.c11!mot;}
            od;
            }
            active[1] proctype Router0() {
        Port port[4];
        //Mot mot;
        int t;
updateport :
        do
        
            ::atomic{((!(port[0].toforward))&&nempty(in0.c00)); in0.c00?port[0].mess;port[0].toforward=1;}

            ::atomic{((!(port[1].toforward))&&nempty(in0.c01)); in0.c01?port[1].mess;port[1].toforward=1;}

            ::atomic{((!(port[2].toforward))&&nempty(d20.c00)); d20.c00?port[2].mess;port[2].toforward=1;}

            ::atomic{((!(port[3].toforward))&&nempty(d20.c01)); d20.c01?port[3].mess;port[3].toforward=1;}
::(1); break;
         od;
forwardmess:
        do
        
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==INTER));
                if
                ::atomic{(port[0].redirect==0); d10.c00!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==1); d10.c01!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==2); u10.c00!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==3); u10.c01!port[0].mess; port[0].toforward=0;}
                fi;
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==FIN));
                if
                ::atomic{(port[0].redirect==0); d10.c00!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==1); d10.c01!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==2); u10.c00!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==3); u10.c01!port[0].mess; port[0].toforward=0;port[0].lock=0;}
                fi;
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==DEBUT));
                if
                ::atomic{(((port[0].mess.dest/2)%4)==0);t= ((port[0].mess.dest/1)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[0].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[0].redirect==0); port[0].toforward=0; d10.c00!port[0].mess;}                                                       
                        ::atomic{(port[0].redirect==1); port[0].toforward=0; d10.c01!port[0].mess;}
                        fi;
                    fi;
                ::(((port[0].mess.dest/2)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[0].redirect=2; u10.c00!port[0].mess; port[0].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[0].redirect=3; u10.c01!port[0].mess; port[0].toforward=0;}
                        fi;
                fi;
                
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==INTER));
                if
                ::atomic{(port[1].redirect==0); d10.c00!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==1); d10.c01!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==2); u10.c00!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==3); u10.c01!port[1].mess; port[1].toforward=0;}
                fi;
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==FIN));
                if
                ::atomic{(port[1].redirect==0); d10.c00!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==1); d10.c01!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==2); u10.c00!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==3); u10.c01!port[1].mess; port[1].toforward=0;port[1].lock=0;}
                fi;
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==DEBUT));
                if
                ::atomic{(((port[1].mess.dest/2)%4)==0);t= ((port[1].mess.dest/1)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[1].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[1].redirect==0); port[1].toforward=0; d10.c00!port[1].mess;}                                                       
                        ::atomic{(port[1].redirect==1); port[1].toforward=0; d10.c01!port[1].mess;}
                        fi;
                    fi;
                ::(((port[1].mess.dest/2)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[1].redirect=2; u10.c00!port[1].mess; port[1].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[1].redirect=3; u10.c01!port[1].mess; port[1].toforward=0;}
                        fi;
                fi;
                
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==INTER));
                if
                ::atomic{(port[2].redirect==0); d10.c00!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==1); d10.c01!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==2); u10.c00!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==3); u10.c01!port[2].mess; port[2].toforward=0;}
                fi;
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==FIN));
                if
                ::atomic{(port[2].redirect==0); d10.c00!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==1); d10.c01!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==2); u10.c00!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==3); u10.c01!port[2].mess; port[2].toforward=0;port[2].lock=0;}
                fi;
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==DEBUT));
                if
                ::atomic{(((port[2].mess.dest/2)%4)==0);t= ((port[2].mess.dest/1)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[2].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[2].redirect==0); port[2].toforward=0; d10.c00!port[2].mess;}                                                       
                        ::atomic{(port[2].redirect==1); port[2].toforward=0; d10.c01!port[2].mess;}
                        fi;
                    fi;
                ::(((port[2].mess.dest/2)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[2].redirect=2; u10.c00!port[2].mess; port[2].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[2].redirect=3; u10.c01!port[2].mess; port[2].toforward=0;}
                        fi;
                fi;
                
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==INTER));
                if
                ::atomic{(port[3].redirect==0); d10.c00!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==1); d10.c01!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==2); u10.c00!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==3); u10.c01!port[3].mess; port[3].toforward=0;}
                fi;
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==FIN));
                if
                ::atomic{(port[3].redirect==0); d10.c00!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==1); d10.c01!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==2); u10.c00!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==3); u10.c01!port[3].mess; port[3].toforward=0;port[3].lock=0;}
                fi;
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==DEBUT));
                if
                ::atomic{(((port[3].mess.dest/2)%4)==0);t= ((port[3].mess.dest/1)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[3].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[3].redirect==0); port[3].toforward=0; d10.c00!port[3].mess;}                                                       
                        ::atomic{(port[3].redirect==1); port[3].toforward=0; d10.c01!port[3].mess;}
                        fi;
                    fi;
                ::(((port[3].mess.dest/2)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[3].redirect=2; u10.c00!port[3].mess; port[3].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[3].redirect=3; u10.c01!port[3].mess; port[3].toforward=0;}
                        fi;
                fi;
                ::((port[0].toforward==0)&&(port[1].toforward==0)&&(port[2].toforward==0)&&(port[3].toforward==0)); break;
        od;
     goto updateport;
     }

active[1] proctype Router1() {
        Port port[4];
        //Mot mot;
        int t;
updateport :
        do
        
            ::atomic{((!(port[0].toforward))&&nempty(in0.c10)); in0.c10?port[0].mess;port[0].toforward=1;}

            ::atomic{((!(port[1].toforward))&&nempty(in0.c11)); in0.c11?port[1].mess;port[1].toforward=1;}

            ::atomic{((!(port[2].toforward))&&nempty(d20.c10)); d20.c10?port[2].mess;port[2].toforward=1;}

            ::atomic{((!(port[3].toforward))&&nempty(d20.c11)); d20.c11?port[3].mess;port[3].toforward=1;}
::(1); break;
         od;
forwardmess:
        do
        
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==INTER));
                if
                ::atomic{(port[0].redirect==0); d10.c10!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==1); d10.c11!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==2); u10.c10!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==3); u10.c11!port[0].mess; port[0].toforward=0;}
                fi;
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==FIN));
                if
                ::atomic{(port[0].redirect==0); d10.c10!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==1); d10.c11!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==2); u10.c10!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==3); u10.c11!port[0].mess; port[0].toforward=0;port[0].lock=0;}
                fi;
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==DEBUT));
                if
                ::atomic{(((port[0].mess.dest/2)%4)==1);t= ((port[0].mess.dest/1)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[0].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[0].redirect==0); port[0].toforward=0; d10.c10!port[0].mess;}                                                       
                        ::atomic{(port[0].redirect==1); port[0].toforward=0; d10.c11!port[0].mess;}
                        fi;
                    fi;
                ::(((port[0].mess.dest/2)%4)!=1);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[0].redirect=2; u10.c10!port[0].mess; port[0].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[0].redirect=3; u10.c11!port[0].mess; port[0].toforward=0;}
                        fi;
                fi;
                
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==INTER));
                if
                ::atomic{(port[1].redirect==0); d10.c10!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==1); d10.c11!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==2); u10.c10!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==3); u10.c11!port[1].mess; port[1].toforward=0;}
                fi;
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==FIN));
                if
                ::atomic{(port[1].redirect==0); d10.c10!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==1); d10.c11!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==2); u10.c10!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==3); u10.c11!port[1].mess; port[1].toforward=0;port[1].lock=0;}
                fi;
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==DEBUT));
                if
                ::atomic{(((port[1].mess.dest/2)%4)==1);t= ((port[1].mess.dest/1)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[1].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[1].redirect==0); port[1].toforward=0; d10.c10!port[1].mess;}                                                       
                        ::atomic{(port[1].redirect==1); port[1].toforward=0; d10.c11!port[1].mess;}
                        fi;
                    fi;
                ::(((port[1].mess.dest/2)%4)!=1);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[1].redirect=2; u10.c10!port[1].mess; port[1].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[1].redirect=3; u10.c11!port[1].mess; port[1].toforward=0;}
                        fi;
                fi;
                
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==INTER));
                if
                ::atomic{(port[2].redirect==0); d10.c10!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==1); d10.c11!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==2); u10.c10!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==3); u10.c11!port[2].mess; port[2].toforward=0;}
                fi;
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==FIN));
                if
                ::atomic{(port[2].redirect==0); d10.c10!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==1); d10.c11!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==2); u10.c10!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==3); u10.c11!port[2].mess; port[2].toforward=0;port[2].lock=0;}
                fi;
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==DEBUT));
                if
                ::atomic{(((port[2].mess.dest/2)%4)==1);t= ((port[2].mess.dest/1)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[2].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[2].redirect==0); port[2].toforward=0; d10.c10!port[2].mess;}                                                       
                        ::atomic{(port[2].redirect==1); port[2].toforward=0; d10.c11!port[2].mess;}
                        fi;
                    fi;
                ::(((port[2].mess.dest/2)%4)!=1);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[2].redirect=2; u10.c10!port[2].mess; port[2].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[2].redirect=3; u10.c11!port[2].mess; port[2].toforward=0;}
                        fi;
                fi;
                
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==INTER));
                if
                ::atomic{(port[3].redirect==0); d10.c10!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==1); d10.c11!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==2); u10.c10!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==3); u10.c11!port[3].mess; port[3].toforward=0;}
                fi;
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==FIN));
                if
                ::atomic{(port[3].redirect==0); d10.c10!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==1); d10.c11!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==2); u10.c10!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==3); u10.c11!port[3].mess; port[3].toforward=0;port[3].lock=0;}
                fi;
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==DEBUT));
                if
                ::atomic{(((port[3].mess.dest/2)%4)==1);t= ((port[3].mess.dest/1)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[3].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[3].redirect==0); port[3].toforward=0; d10.c10!port[3].mess;}                                                       
                        ::atomic{(port[3].redirect==1); port[3].toforward=0; d10.c11!port[3].mess;}
                        fi;
                    fi;
                ::(((port[3].mess.dest/2)%4)!=1);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[3].redirect=2; u10.c10!port[3].mess; port[3].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[3].redirect=3; u10.c11!port[3].mess; port[3].toforward=0;}
                        fi;
                fi;
                ::((port[0].toforward==0)&&(port[1].toforward==0)&&(port[2].toforward==0)&&(port[3].toforward==0)); break;
        od;
     goto updateport;
     }

active[1] proctype Router4() {
        Port port[4];
        //Mot mot;
        int t;
updateport :
        do
        
            ::atomic{((!(port[0].toforward))&&nempty(u10.c00)); u10.c00?port[0].mess;port[0].toforward=1;}

            ::atomic{((!(port[1].toforward))&&nempty(u10.c10)); u10.c10?port[1].mess;port[1].toforward=1;}

            ::atomic{((!(port[2].toforward))&&nempty(u20.c00)); u20.c00?port[2].mess;port[2].toforward=1;}

            ::atomic{((!(port[3].toforward))&&nempty(u20.c01)); u20.c01?port[3].mess;port[3].toforward=1;}
::(1); break;
         od;
forwardmess:
        do
        
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==INTER));
                if
                ::atomic{(port[0].redirect==0); d20.c00!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==1); d20.c01!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==2); u20.c00!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==3); u20.c01!port[0].mess; port[0].toforward=0;}
                fi;
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==FIN));
                if
                ::atomic{(port[0].redirect==0); d20.c00!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==1); d20.c01!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==2); u20.c00!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==3); u20.c01!port[0].mess; port[0].toforward=0;port[0].lock=0;}
                fi;
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==DEBUT));
                if
                ::atomic{(((port[0].mess.dest/4)%4)==0);t= ((port[0].mess.dest/2)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[0].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[0].redirect==0); port[0].toforward=0; d20.c00!port[0].mess;}                                                       
                        ::atomic{(port[0].redirect==1); port[0].toforward=0; d20.c01!port[0].mess;}
                        fi;
                    fi;
                ::(((port[0].mess.dest/4)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[0].redirect=2; u20.c00!port[0].mess; port[0].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[0].redirect=3; u20.c01!port[0].mess; port[0].toforward=0;}
                        fi;
                fi;
                
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==INTER));
                if
                ::atomic{(port[1].redirect==0); d20.c00!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==1); d20.c01!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==2); u20.c00!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==3); u20.c01!port[1].mess; port[1].toforward=0;}
                fi;
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==FIN));
                if
                ::atomic{(port[1].redirect==0); d20.c00!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==1); d20.c01!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==2); u20.c00!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==3); u20.c01!port[1].mess; port[1].toforward=0;port[1].lock=0;}
                fi;
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==DEBUT));
                if
                ::atomic{(((port[1].mess.dest/4)%4)==0);t= ((port[1].mess.dest/2)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[1].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[1].redirect==0); port[1].toforward=0; d20.c00!port[1].mess;}                                                       
                        ::atomic{(port[1].redirect==1); port[1].toforward=0; d20.c01!port[1].mess;}
                        fi;
                    fi;
                ::(((port[1].mess.dest/4)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[1].redirect=2; u20.c00!port[1].mess; port[1].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[1].redirect=3; u20.c01!port[1].mess; port[1].toforward=0;}
                        fi;
                fi;
                
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==INTER));
                if
                ::atomic{(port[2].redirect==0); d20.c00!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==1); d20.c01!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==2); u20.c00!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==3); u20.c01!port[2].mess; port[2].toforward=0;}
                fi;
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==FIN));
                if
                ::atomic{(port[2].redirect==0); d20.c00!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==1); d20.c01!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==2); u20.c00!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==3); u20.c01!port[2].mess; port[2].toforward=0;port[2].lock=0;}
                fi;
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==DEBUT));
                if
                ::atomic{(((port[2].mess.dest/4)%4)==0);t= ((port[2].mess.dest/2)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[2].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[2].redirect==0); port[2].toforward=0; d20.c00!port[2].mess;}                                                       
                        ::atomic{(port[2].redirect==1); port[2].toforward=0; d20.c01!port[2].mess;}
                        fi;
                    fi;
                ::(((port[2].mess.dest/4)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[2].redirect=2; u20.c00!port[2].mess; port[2].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[2].redirect=3; u20.c01!port[2].mess; port[2].toforward=0;}
                        fi;
                fi;
                
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==INTER));
                if
                ::atomic{(port[3].redirect==0); d20.c00!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==1); d20.c01!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==2); u20.c00!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==3); u20.c01!port[3].mess; port[3].toforward=0;}
                fi;
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==FIN));
                if
                ::atomic{(port[3].redirect==0); d20.c00!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==1); d20.c01!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==2); u20.c00!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==3); u20.c01!port[3].mess; port[3].toforward=0;port[3].lock=0;}
                fi;
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==DEBUT));
                if
                ::atomic{(((port[3].mess.dest/4)%4)==0);t= ((port[3].mess.dest/2)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[3].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[3].redirect==0); port[3].toforward=0; d20.c00!port[3].mess;}                                                       
                        ::atomic{(port[3].redirect==1); port[3].toforward=0; d20.c01!port[3].mess;}
                        fi;
                    fi;
                ::(((port[3].mess.dest/4)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[3].redirect=2; u20.c00!port[3].mess; port[3].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[3].redirect=3; u20.c01!port[3].mess; port[3].toforward=0;}
                        fi;
                fi;
                ::((port[0].toforward==0)&&(port[1].toforward==0)&&(port[2].toforward==0)&&(port[3].toforward==0)); break;
        od;
     goto updateport;
     }

active[1] proctype Router5() {
        Port port[4];
        //Mot mot;
        int t;
updateport :
        do
        
            ::atomic{((!(port[0].toforward))&&nempty(u10.c01)); u10.c01?port[0].mess;port[0].toforward=1;}

            ::atomic{((!(port[1].toforward))&&nempty(u10.c11)); u10.c11?port[1].mess;port[1].toforward=1;}

            ::atomic{((!(port[2].toforward))&&nempty(u20.c10)); u20.c10?port[2].mess;port[2].toforward=1;}

            ::atomic{((!(port[3].toforward))&&nempty(u20.c11)); u20.c11?port[3].mess;port[3].toforward=1;}
::(1); break;
         od;
forwardmess:
        do
        
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==INTER));
                if
                ::atomic{(port[0].redirect==0); d20.c10!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==1); d20.c11!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==2); u20.c10!port[0].mess; port[0].toforward=0;}                                                                                   
                ::atomic{(port[0].redirect==3); u20.c11!port[0].mess; port[0].toforward=0;}
                fi;
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==FIN));
                if
                ::atomic{(port[0].redirect==0); d20.c10!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==1); d20.c11!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==2); u20.c10!port[0].mess; port[0].toforward=0;port[0].lock=0;}                                                                                                   
                ::atomic{(port[0].redirect==3); u20.c11!port[0].mess; port[0].toforward=0;port[0].lock=0;}
                fi;
            ::(((port[0].toforward)==1)&&(port[0].mess.tag==DEBUT));
                if
                ::atomic{(((port[0].mess.dest/4)%4)==0);t= ((port[0].mess.dest/2)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[0].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[0].redirect==0); port[0].toforward=0; d20.c10!port[0].mess;}                                                       
                        ::atomic{(port[0].redirect==1); port[0].toforward=0; d20.c11!port[0].mess;}
                        fi;
                    fi;
                ::(((port[0].mess.dest/4)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[0].redirect=2; u20.c10!port[0].mess; port[0].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[0].redirect=3; u20.c11!port[0].mess; port[0].toforward=0;}
                        fi;
                fi;
                
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==INTER));
                if
                ::atomic{(port[1].redirect==0); d20.c10!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==1); d20.c11!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==2); u20.c10!port[1].mess; port[1].toforward=0;}                                                                                   
                ::atomic{(port[1].redirect==3); u20.c11!port[1].mess; port[1].toforward=0;}
                fi;
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==FIN));
                if
                ::atomic{(port[1].redirect==0); d20.c10!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==1); d20.c11!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==2); u20.c10!port[1].mess; port[1].toforward=0;port[1].lock=0;}                                                                                                   
                ::atomic{(port[1].redirect==3); u20.c11!port[1].mess; port[1].toforward=0;port[1].lock=0;}
                fi;
            ::(((port[1].toforward)==1)&&(port[1].mess.tag==DEBUT));
                if
                ::atomic{(((port[1].mess.dest/4)%4)==0);t= ((port[1].mess.dest/2)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[1].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[1].redirect==0); port[1].toforward=0; d20.c10!port[1].mess;}                                                       
                        ::atomic{(port[1].redirect==1); port[1].toforward=0; d20.c11!port[1].mess;}
                        fi;
                    fi;
                ::(((port[1].mess.dest/4)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[1].redirect=2; u20.c10!port[1].mess; port[1].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[1].redirect=3; u20.c11!port[1].mess; port[1].toforward=0;}
                        fi;
                fi;
                
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==INTER));
                if
                ::atomic{(port[2].redirect==0); d20.c10!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==1); d20.c11!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==2); u20.c10!port[2].mess; port[2].toforward=0;}                                                                                   
                ::atomic{(port[2].redirect==3); u20.c11!port[2].mess; port[2].toforward=0;}
                fi;
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==FIN));
                if
                ::atomic{(port[2].redirect==0); d20.c10!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==1); d20.c11!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==2); u20.c10!port[2].mess; port[2].toforward=0;port[2].lock=0;}                                                                                                   
                ::atomic{(port[2].redirect==3); u20.c11!port[2].mess; port[2].toforward=0;port[2].lock=0;}
                fi;
            ::(((port[2].toforward)==1)&&(port[2].mess.tag==DEBUT));
                if
                ::atomic{(((port[2].mess.dest/4)%4)==0);t= ((port[2].mess.dest/2)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[2].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[2].redirect==0); port[2].toforward=0; d20.c10!port[2].mess;}                                                       
                        ::atomic{(port[2].redirect==1); port[2].toforward=0; d20.c11!port[2].mess;}
                        fi;
                    fi;
                ::(((port[2].mess.dest/4)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[2].redirect=2; u20.c10!port[2].mess; port[2].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[2].redirect=3; u20.c11!port[2].mess; port[2].toforward=0;}
                        fi;
                fi;
                
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==INTER));
                if
                ::atomic{(port[3].redirect==0); d20.c10!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==1); d20.c11!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==2); u20.c10!port[3].mess; port[3].toforward=0;}                                                                                   
                ::atomic{(port[3].redirect==3); u20.c11!port[3].mess; port[3].toforward=0;}
                fi;
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==FIN));
                if
                ::atomic{(port[3].redirect==0); d20.c10!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==1); d20.c11!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==2); u20.c10!port[3].mess; port[3].toforward=0;port[3].lock=0;}                                                                                                   
                ::atomic{(port[3].redirect==3); u20.c11!port[3].mess; port[3].toforward=0;port[3].lock=0;}
                fi;
            ::(((port[3].toforward)==1)&&(port[3].mess.tag==DEBUT));
                if
                ::atomic{(((port[3].mess.dest/4)%4)==0);t= ((port[3].mess.dest/2)%2);};
                    if
                    ::(port[t].lock);
                    ::atomic{
                        (!(port[t].lock));
                        port[t].lock=1;
                        port[3].redirect = t;
                        t=0;
                    }
                        if
                        ::atomic{(port[3].redirect==0); port[3].toforward=0; d20.c10!port[3].mess;}                                                       
                        ::atomic{(port[3].redirect==1); port[3].toforward=0; d20.c11!port[3].mess;}
                        fi;
                    fi;
                ::(((port[3].mess.dest/4)%4)!=0);
                        if
                        ::atomic{(!(port[2].lock)); port[2].lock=1; port[3].redirect=2; u20.c10!port[3].mess; port[3].toforward=0;}
                        ::atomic{(!(port[3].lock)); port[3].lock=1; port[3].redirect=3; u20.c11!port[3].mess; port[3].toforward=0;}
                        fi;
                fi;
                ::((port[0].toforward==0)&&(port[1].toforward==0)&&(port[2].toforward==0)&&(port[3].toforward==0)); break;
        od;
     goto updateport;
     }

