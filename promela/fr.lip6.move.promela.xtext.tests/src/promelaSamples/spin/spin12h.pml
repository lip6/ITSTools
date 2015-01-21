
 typedef MOT {
 short adsource;
 short addest;
 short tag;
 };
typedef LIO {
chan x00 = [1] of {MOT};
chan x01 = [1] of {MOT};
chan x10 = [1] of {MOT};
chan x11 = [1] of {MOT};
chan x20 = [1] of {MOT};
chan x21 = [1] of {MOT};
chan x30 = [1] of {MOT};
chan x31 = [1] of {MOT};
};
typedef LPOSSOUT {
chan x00 = [1] of {bit};
chan x01 = [1] of {bit};
chan x10 = [1] of {bit};
chan x11 = [1] of {bit};
chan x20 = [1] of {bit};
chan x21 = [1] of {bit};
chan x30 = [1] of {bit};
chan x31 = [1] of {bit};
chan x40 = [1] of {bit};
chan x41 = [1] of {bit};
};
typedef SUP {
short sup20;
short sup30;
short sup21;
short sup31;
short sup22;
short sup32;
short sup23;
short sup33;
};
typedef LINTER {
LPOSSOUT po;
LIO io;
};

        bool active_all;

LIO in1;
LINTER d1;
LINTER u1;
SUP sup1;
LINTER d2;
LINTER u2;
SUP sup2;
LINTER d3;
LINTER u3;
SUP sup3;


chan chan_sync0 = [1] of {bit};
chan chan_sync2 = [1] of {bit};
chan chan_sync4 = [1] of {bit};
chan chan_sync6 = [1] of {bit};

/* Variable globale de l'horloge */


       active[1] proctype proc_envoi0()
{
  MOT mot;
  /* numéro du mot à envoyer */
  short num_mot; 
  short num_mot_recu; 
  bit sync;

  atomic { (active_all==1);
    num_mot = 1;
    mot.adsource = 0;
    mot.addest = 1;
  }
  
  do
    ::in1.x00![_];
      if
      :: ((num_mot == 1) /*&& (nfull(in1.x00))*/) -> 
        /* Mot de début */
        mot.tag = 1;
in1.x00!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 2)) /*&& (nfull(in1.x00))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
in1.x00!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot == 2) /*&& (nfull(in1.x00))*/) -> 
        /* Mot de fin */
        mot.tag = 2;
in1.x00!mot;
        num_mot = num_mot + 1;
        
        /* Blocage */
        chan_sync0?sync;
        num_mot = 1;
        
      fi;

  od;
}

/*
// ////////////////////////////////////////////////////////////////////
//
// Process décrivant la reception d'une réponse par l'ininitiateur 0
//
// ////////////////////////////////////////////////////////////////////
*/
active[1] proctype proc_reception0()
{
MOT din;
  int num_mot_recu = 1; 
  (active_all==1);
  do
::

    
d1.io.x00?din;
    
    num_mot_recu = num_mot_recu + 1;
    
    if
    :: (num_mot_recu == (3)) ->
      
      
      /* Passage du jeton */
      chan_sync0!1;
      
       num_mot_recu = 1; 
    :: else ->
    fi;
  od;
  

}
active[1] proctype v2scible1()
{
MOT dt;
  (active_all==1);
  do
::
      /* Réception d'une requête */
d1.io.x01?dt;
      
      /* Envoi d'une réponse */
      atomic {
        dt.addest = dt.adsource;
        dt.adsource = 1;
      }
in1.x01!dt;
  od; 
}
active[1] proctype proc_envoi2()
{
  MOT mot;
  /* numéro du mot à envoyer */
  short num_mot; 
  short num_mot_recu; 
  bit sync;

  atomic { (active_all==1);
    num_mot = 1;
    mot.adsource = 2;
    mot.addest = 1;
  }
  
  do
    ::in1.x10![_];
      if
      :: ((num_mot == 1) /*&& (nfull(in1.x10))*/) -> 
        /* Mot de début */
        mot.tag = 1;
in1.x10!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 2)) /*&& (nfull(in1.x10))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
in1.x10!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot == 2) /*&& (nfull(in1.x10))*/) -> 
        /* Mot de fin */
        mot.tag = 2;
in1.x10!mot;
        num_mot = num_mot + 1;
        
        /* Blocage */
        chan_sync2?sync;
        num_mot = 1;
        
      fi;

  od;
}

/*
// ////////////////////////////////////////////////////////////////////
//
// Process décrivant la reception d'une réponse par l'ininitiateur 2
//
// ////////////////////////////////////////////////////////////////////
*/
active[1] proctype proc_reception2()
{
MOT din;
  int num_mot_recu = 1; 
  (active_all==1);
  do
::

    
d1.io.x10?din;
    
    num_mot_recu = num_mot_recu + 1;
    
    if
    :: (num_mot_recu == (3)) ->
      
      
      /* Passage du jeton */
      chan_sync2!1;
      
       num_mot_recu = 1; 
    :: else ->
    fi;
  od;
  

}
active[1] proctype v2scible3()
{
MOT dt;
  (active_all==1);
  do
::
      /* Réception d'une requête */
d1.io.x11?dt;
      
      /* Envoi d'une réponse */
      atomic {
        dt.addest = dt.adsource;
        dt.adsource = 3;
      }
in1.x11!dt;
  od; 
}
active[1] proctype proc_envoi4()
{
  MOT mot;
  /* numéro du mot à envoyer */
  short num_mot; 
  short num_mot_recu; 
  bit sync;

  atomic { (active_all==1);
    num_mot = 1;
    mot.adsource = 4;
    mot.addest = 1;
  }
  
  do
    ::in1.x20![_];
      if
      :: ((num_mot == 1) /*&& (nfull(in1.x20))*/) -> 
        /* Mot de début */
        mot.tag = 1;
in1.x20!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 2)) /*&& (nfull(in1.x20))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
in1.x20!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot == 2) /*&& (nfull(in1.x20))*/) -> 
        /* Mot de fin */
        mot.tag = 2;
in1.x20!mot;
        num_mot = num_mot + 1;
        
        /* Blocage */
        chan_sync4?sync;
        num_mot = 1;
        
      fi;

  od;
}

/*
// ////////////////////////////////////////////////////////////////////
//
// Process décrivant la reception d'une réponse par l'ininitiateur 4
//
// ////////////////////////////////////////////////////////////////////
*/
active[1] proctype proc_reception4()
{
MOT din;
  int num_mot_recu = 1; 
  (active_all==1);
  do
::

    
d1.io.x20?din;
    
    num_mot_recu = num_mot_recu + 1;
    
    if
    :: (num_mot_recu == (3)) ->
      
      
      /* Passage du jeton */
      chan_sync4!1;
      
       num_mot_recu = 1; 
    :: else ->
    fi;
  od;
  

}
active[1] proctype v2scible5()
{
MOT dt;
  (active_all==1);
  do
::
      /* Réception d'une requête */
d1.io.x21?dt;
      
      /* Envoi d'une réponse */
      atomic {
        dt.addest = dt.adsource;
        dt.adsource = 5;
      }
in1.x21!dt;
  od; 
}
active[1] proctype proc_envoi6()
{
  MOT mot;
  /* numéro du mot à envoyer */
  short num_mot; 
  short num_mot_recu; 
  bit sync;

  atomic { (active_all==1);
    num_mot = 1;
    mot.adsource = 6;
    mot.addest = 1;
  }
  
  do
    ::in1.x30![_];
      if
      :: ((num_mot == 1) /*&& (nfull(in1.x30))*/) -> 
        /* Mot de début */
        mot.tag = 1;
in1.x30!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 2)) /*&& (nfull(in1.x30))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
in1.x30!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot == 2) /*&& (nfull(in1.x30))*/) -> 
        /* Mot de fin */
        mot.tag = 2;
in1.x30!mot;
        num_mot = num_mot + 1;
        
        /* Blocage */
        chan_sync6?sync;
        num_mot = 1;
        
      fi;

  od;
}

/*
// ////////////////////////////////////////////////////////////////////
//
// Process décrivant la reception d'une réponse par l'ininitiateur 6
//
// ////////////////////////////////////////////////////////////////////
*/
active[1] proctype proc_reception6()
{
MOT din;
  int num_mot_recu = 1; 
  (active_all==1);
  do
::

    
d1.io.x30?din;
    
    num_mot_recu = num_mot_recu + 1;
    
    if
    :: (num_mot_recu == (3)) ->
      
      
      /* Passage du jeton */
      chan_sync6!1;
      
       num_mot_recu = 1; 
    :: else ->
    fi;
  od;
  

}
active[1] proctype v2scible7()
{
MOT dt;
  (active_all==1);
  do
::
      /* Réception d'une requête */
d1.io.x31?dt;
      
      /* Envoi d'une réponse */
      atomic {
        dt.addest = dt.adsource;
        dt.adsource = 7;
      }
in1.x31!dt;
  od; 
}
active[1] proctype proc_port_in0_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
in1.x00?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x00?jeton;
          do
          ::
d1.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x00?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x01?jeton;
          do
          ::
d1.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x00?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup20 == 0) ->
          atomic {
            sup1.sup20 = 1;
          u1.po.x00?jeton;
          }
          do
          ::
u1.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x00!jeton; sup1.sup20 = 0;goto SUITE_UP;}
            :: else ->
in1.x00?mess;
            fi;
          od;
        :: (sup1.sup30 == 0) ->
          atomic {
            sup1.sup30 = 1;
          u1.po.x01?jeton;
          }
          do
          ::
u1.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x01!jeton; sup1.sup30 = 0; goto SUITE_UP;}
            :: else -> 
in1.x00?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in0_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
in1.x01?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x00?jeton;
          do
          ::
d1.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x01?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x01?jeton;
          do
          ::
d1.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x01?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup20 == 0) ->
          atomic {
            sup1.sup20 = 1;
          u1.po.x00?jeton;
          }
          do
          ::
u1.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x00!jeton; sup1.sup20 = 0;goto SUITE_UP;}
            :: else ->
in1.x01?mess;
            fi;
          od;
        :: (sup1.sup30 == 0) ->
          atomic {
            sup1.sup30 = 1;
          u1.po.x01?jeton;
          }
          do
          ::
u1.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x01!jeton; sup1.sup30 = 0; goto SUITE_UP;}
            :: else -> 
in1.x01?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in0_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d2.io.x00?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x00?jeton;
          do
          ::
d1.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x00?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x01?jeton;
          do
          ::
d1.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x00?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup20 == 0) ->
          atomic {
            sup1.sup20 = 1;
          u1.po.x00?jeton;
          }
          do
          ::
u1.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x00!jeton; sup1.sup20 = 0;goto SUITE_UP;}
            :: else ->
d2.io.x00?mess;
            fi;
          od;
        :: (sup1.sup30 == 0) ->
          atomic {
            sup1.sup30 = 1;
          u1.po.x01?jeton;
          }
          do
          ::
u1.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x01!jeton; sup1.sup30 = 0; goto SUITE_UP;}
            :: else -> 
d2.io.x00?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in0_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d2.io.x10?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x00?jeton;
          do
          ::
d1.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x10?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x01?jeton;
          do
          ::
d1.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x10?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup20 == 0) ->
          atomic {
            sup1.sup20 = 1;
          u1.po.x00?jeton;
          }
          do
          ::
u1.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x00!jeton; sup1.sup20 = 0;goto SUITE_UP;}
            :: else ->
d2.io.x10?mess;
            fi;
          od;
        :: (sup1.sup30 == 0) ->
          atomic {
            sup1.sup30 = 1;
          u1.po.x01?jeton;
          }
          do
          ::
u1.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x01!jeton; sup1.sup30 = 0; goto SUITE_UP;}
            :: else -> 
d2.io.x10?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in1_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
in1.x10?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x10?jeton;
          do
          ::
d1.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x10?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x11?jeton;
          do
          ::
d1.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x10?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup21 == 0) ->
          atomic {
            sup1.sup21 = 1;
          u1.po.x10?jeton;
          }
          do
          ::
u1.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x10!jeton; sup1.sup21 = 0;goto SUITE_UP;}
            :: else ->
in1.x10?mess;
            fi;
          od;
        :: (sup1.sup31 == 0) ->
          atomic {
            sup1.sup31 = 1;
          u1.po.x11?jeton;
          }
          do
          ::
u1.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x11!jeton; sup1.sup31 = 0; goto SUITE_UP;}
            :: else -> 
in1.x10?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in1_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
in1.x11?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x10?jeton;
          do
          ::
d1.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x11?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x11?jeton;
          do
          ::
d1.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x11?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup21 == 0) ->
          atomic {
            sup1.sup21 = 1;
          u1.po.x10?jeton;
          }
          do
          ::
u1.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x10!jeton; sup1.sup21 = 0;goto SUITE_UP;}
            :: else ->
in1.x11?mess;
            fi;
          od;
        :: (sup1.sup31 == 0) ->
          atomic {
            sup1.sup31 = 1;
          u1.po.x11?jeton;
          }
          do
          ::
u1.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x11!jeton; sup1.sup31 = 0; goto SUITE_UP;}
            :: else -> 
in1.x11?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in1_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d2.io.x01?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x10?jeton;
          do
          ::
d1.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x01?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x11?jeton;
          do
          ::
d1.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x01?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup21 == 0) ->
          atomic {
            sup1.sup21 = 1;
          u1.po.x10?jeton;
          }
          do
          ::
u1.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x10!jeton; sup1.sup21 = 0;goto SUITE_UP;}
            :: else ->
d2.io.x01?mess;
            fi;
          od;
        :: (sup1.sup31 == 0) ->
          atomic {
            sup1.sup31 = 1;
          u1.po.x11?jeton;
          }
          do
          ::
u1.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x11!jeton; sup1.sup31 = 0; goto SUITE_UP;}
            :: else -> 
d2.io.x01?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in1_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d2.io.x11?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x10?jeton;
          do
          ::
d1.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x11?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x11?jeton;
          do
          ::
d1.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x11?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup21 == 0) ->
          atomic {
            sup1.sup21 = 1;
          u1.po.x10?jeton;
          }
          do
          ::
u1.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x10!jeton; sup1.sup21 = 0;goto SUITE_UP;}
            :: else ->
d2.io.x11?mess;
            fi;
          od;
        :: (sup1.sup31 == 0) ->
          atomic {
            sup1.sup31 = 1;
          u1.po.x11?jeton;
          }
          do
          ::
u1.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x11!jeton; sup1.sup31 = 0; goto SUITE_UP;}
            :: else -> 
d2.io.x11?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in2_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
in1.x20?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 2) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x20?jeton;
          do
          ::
d1.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x20?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x21?jeton;
          do
          ::
d1.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x20?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup22 == 0) ->
          atomic {
            sup1.sup22 = 1;
          u1.po.x20?jeton;
          }
          do
          ::
u1.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x20!jeton; sup1.sup22 = 0;goto SUITE_UP;}
            :: else ->
in1.x20?mess;
            fi;
          od;
        :: (sup1.sup32 == 0) ->
          atomic {
            sup1.sup32 = 1;
          u1.po.x21?jeton;
          }
          do
          ::
u1.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x21!jeton; sup1.sup32 = 0; goto SUITE_UP;}
            :: else -> 
in1.x20?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in2_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
in1.x21?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 2) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x20?jeton;
          do
          ::
d1.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x21?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x21?jeton;
          do
          ::
d1.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x21?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup22 == 0) ->
          atomic {
            sup1.sup22 = 1;
          u1.po.x20?jeton;
          }
          do
          ::
u1.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x20!jeton; sup1.sup22 = 0;goto SUITE_UP;}
            :: else ->
in1.x21?mess;
            fi;
          od;
        :: (sup1.sup32 == 0) ->
          atomic {
            sup1.sup32 = 1;
          u1.po.x21?jeton;
          }
          do
          ::
u1.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x21!jeton; sup1.sup32 = 0; goto SUITE_UP;}
            :: else -> 
in1.x21?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in2_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d2.io.x20?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 2) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x20?jeton;
          do
          ::
d1.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x20?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x21?jeton;
          do
          ::
d1.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x20?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup22 == 0) ->
          atomic {
            sup1.sup22 = 1;
          u1.po.x20?jeton;
          }
          do
          ::
u1.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x20!jeton; sup1.sup22 = 0;goto SUITE_UP;}
            :: else ->
d2.io.x20?mess;
            fi;
          od;
        :: (sup1.sup32 == 0) ->
          atomic {
            sup1.sup32 = 1;
          u1.po.x21?jeton;
          }
          do
          ::
u1.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x21!jeton; sup1.sup32 = 0; goto SUITE_UP;}
            :: else -> 
d2.io.x20?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in2_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d2.io.x30?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 2) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x20?jeton;
          do
          ::
d1.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x30?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x21?jeton;
          do
          ::
d1.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x30?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup22 == 0) ->
          atomic {
            sup1.sup22 = 1;
          u1.po.x20?jeton;
          }
          do
          ::
u1.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x20!jeton; sup1.sup22 = 0;goto SUITE_UP;}
            :: else ->
d2.io.x30?mess;
            fi;
          od;
        :: (sup1.sup32 == 0) ->
          atomic {
            sup1.sup32 = 1;
          u1.po.x21?jeton;
          }
          do
          ::
u1.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x21!jeton; sup1.sup32 = 0; goto SUITE_UP;}
            :: else -> 
d2.io.x30?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in3_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
in1.x30?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 3) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x30?jeton;
          do
          ::
d1.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x30?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x31?jeton;
          do
          ::
d1.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x30?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup23 == 0) ->
          atomic {
            sup1.sup23 = 1;
          u1.po.x30?jeton;
          }
          do
          ::
u1.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x30!jeton; sup1.sup23 = 0;goto SUITE_UP;}
            :: else ->
in1.x30?mess;
            fi;
          od;
        :: (sup1.sup33 == 0) ->
          atomic {
            sup1.sup33 = 1;
          u1.po.x31?jeton;
          }
          do
          ::
u1.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x31!jeton; sup1.sup33 = 0; goto SUITE_UP;}
            :: else -> 
in1.x30?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in3_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
in1.x31?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 3) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x30?jeton;
          do
          ::
d1.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x31?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x31?jeton;
          do
          ::
d1.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
in1.x31?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup23 == 0) ->
          atomic {
            sup1.sup23 = 1;
          u1.po.x30?jeton;
          }
          do
          ::
u1.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x30!jeton; sup1.sup23 = 0;goto SUITE_UP;}
            :: else ->
in1.x31?mess;
            fi;
          od;
        :: (sup1.sup33 == 0) ->
          atomic {
            sup1.sup33 = 1;
          u1.po.x31?jeton;
          }
          do
          ::
u1.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x31!jeton; sup1.sup33 = 0; goto SUITE_UP;}
            :: else -> 
in1.x31?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in3_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d2.io.x21?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 3) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x30?jeton;
          do
          ::
d1.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x21?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x31?jeton;
          do
          ::
d1.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x21?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup23 == 0) ->
          atomic {
            sup1.sup23 = 1;
          u1.po.x30?jeton;
          }
          do
          ::
u1.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x30!jeton; sup1.sup23 = 0;goto SUITE_UP;}
            :: else ->
d2.io.x21?mess;
            fi;
          od;
        :: (sup1.sup33 == 0) ->
          atomic {
            sup1.sup33 = 1;
          u1.po.x31?jeton;
          }
          do
          ::
u1.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x31!jeton; sup1.sup33 = 0; goto SUITE_UP;}
            :: else -> 
d2.io.x21?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in3_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d2.io.x31?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 3) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d1.po.x30?jeton;
          do
          ::
d1.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x31?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d1.po.x31?jeton;
          do
          ::
d1.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d1.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
d2.io.x31?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup1.sup23 == 0) ->
          atomic {
            sup1.sup23 = 1;
          u1.po.x30?jeton;
          }
          do
          ::
u1.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x30!jeton; sup1.sup23 = 0;goto SUITE_UP;}
            :: else ->
d2.io.x31?mess;
            fi;
          od;
        :: (sup1.sup33 == 0) ->
          atomic {
            sup1.sup33 = 1;
          u1.po.x31?jeton;
          }
          do
          ::
u1.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u1.po.x31!jeton; sup1.sup33 = 0; goto SUITE_UP;}
            :: else -> 
d2.io.x31?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in4_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u1.io.x00?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x00?jeton;
          do
          ::
d2.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x00?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x01?jeton;
          do
          ::
d2.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x00?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup20 == 0) ->
          atomic {
            sup2.sup20 = 1;
          u2.po.x00?jeton;
          }
          do
          ::
u2.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x00!jeton; sup2.sup20 = 0;goto SUITE_UP;}
            :: else ->
u1.io.x00?mess;
            fi;
          od;
        :: (sup2.sup30 == 0) ->
          atomic {
            sup2.sup30 = 1;
          u2.po.x01?jeton;
          }
          do
          ::
u2.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x01!jeton; sup2.sup30 = 0; goto SUITE_UP;}
            :: else -> 
u1.io.x00?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in4_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u1.io.x10?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x00?jeton;
          do
          ::
d2.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x10?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x01?jeton;
          do
          ::
d2.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x10?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup20 == 0) ->
          atomic {
            sup2.sup20 = 1;
          u2.po.x00?jeton;
          }
          do
          ::
u2.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x00!jeton; sup2.sup20 = 0;goto SUITE_UP;}
            :: else ->
u1.io.x10?mess;
            fi;
          od;
        :: (sup2.sup30 == 0) ->
          atomic {
            sup2.sup30 = 1;
          u2.po.x01?jeton;
          }
          do
          ::
u2.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x01!jeton; sup2.sup30 = 0; goto SUITE_UP;}
            :: else -> 
u1.io.x10?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in4_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d3.io.x00?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x00?jeton;
          do
          ::
d2.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x00?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x01?jeton;
          do
          ::
d2.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x00?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup20 == 0) ->
          atomic {
            sup2.sup20 = 1;
          u2.po.x00?jeton;
          }
          do
          ::
u2.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x00!jeton; sup2.sup20 = 0;goto SUITE_UP;}
            :: else ->
d3.io.x00?mess;
            fi;
          od;
        :: (sup2.sup30 == 0) ->
          atomic {
            sup2.sup30 = 1;
          u2.po.x01?jeton;
          }
          do
          ::
u2.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x01!jeton; sup2.sup30 = 0; goto SUITE_UP;}
            :: else -> 
d3.io.x00?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in4_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d3.io.x20?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x00?jeton;
          do
          ::
d2.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x20?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x01?jeton;
          do
          ::
d2.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x20?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup20 == 0) ->
          atomic {
            sup2.sup20 = 1;
          u2.po.x00?jeton;
          }
          do
          ::
u2.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x00!jeton; sup2.sup20 = 0;goto SUITE_UP;}
            :: else ->
d3.io.x20?mess;
            fi;
          od;
        :: (sup2.sup30 == 0) ->
          atomic {
            sup2.sup30 = 1;
          u2.po.x01?jeton;
          }
          do
          ::
u2.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x01!jeton; sup2.sup30 = 0; goto SUITE_UP;}
            :: else -> 
d3.io.x20?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in5_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u1.io.x01?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x10?jeton;
          do
          ::
d2.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x01?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x11?jeton;
          do
          ::
d2.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x01?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup21 == 0) ->
          atomic {
            sup2.sup21 = 1;
          u2.po.x10?jeton;
          }
          do
          ::
u2.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x10!jeton; sup2.sup21 = 0;goto SUITE_UP;}
            :: else ->
u1.io.x01?mess;
            fi;
          od;
        :: (sup2.sup31 == 0) ->
          atomic {
            sup2.sup31 = 1;
          u2.po.x11?jeton;
          }
          do
          ::
u2.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x11!jeton; sup2.sup31 = 0; goto SUITE_UP;}
            :: else -> 
u1.io.x01?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in5_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u1.io.x11?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x10?jeton;
          do
          ::
d2.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x11?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x11?jeton;
          do
          ::
d2.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x11?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup21 == 0) ->
          atomic {
            sup2.sup21 = 1;
          u2.po.x10?jeton;
          }
          do
          ::
u2.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x10!jeton; sup2.sup21 = 0;goto SUITE_UP;}
            :: else ->
u1.io.x11?mess;
            fi;
          od;
        :: (sup2.sup31 == 0) ->
          atomic {
            sup2.sup31 = 1;
          u2.po.x11?jeton;
          }
          do
          ::
u2.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x11!jeton; sup2.sup31 = 0; goto SUITE_UP;}
            :: else -> 
u1.io.x11?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in5_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d3.io.x10?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x10?jeton;
          do
          ::
d2.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x10?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x11?jeton;
          do
          ::
d2.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x10?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup21 == 0) ->
          atomic {
            sup2.sup21 = 1;
          u2.po.x10?jeton;
          }
          do
          ::
u2.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x10!jeton; sup2.sup21 = 0;goto SUITE_UP;}
            :: else ->
d3.io.x10?mess;
            fi;
          od;
        :: (sup2.sup31 == 0) ->
          atomic {
            sup2.sup31 = 1;
          u2.po.x11?jeton;
          }
          do
          ::
u2.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x11!jeton; sup2.sup31 = 0; goto SUITE_UP;}
            :: else -> 
d3.io.x10?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in5_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d3.io.x30?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x10?jeton;
          do
          ::
d2.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x30?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x11?jeton;
          do
          ::
d2.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x30?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup21 == 0) ->
          atomic {
            sup2.sup21 = 1;
          u2.po.x10?jeton;
          }
          do
          ::
u2.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x10!jeton; sup2.sup21 = 0;goto SUITE_UP;}
            :: else ->
d3.io.x30?mess;
            fi;
          od;
        :: (sup2.sup31 == 0) ->
          atomic {
            sup2.sup31 = 1;
          u2.po.x11?jeton;
          }
          do
          ::
u2.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x11!jeton; sup2.sup31 = 0; goto SUITE_UP;}
            :: else -> 
d3.io.x30?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in6_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u1.io.x20?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x20?jeton;
          do
          ::
d2.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x20?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x21?jeton;
          do
          ::
d2.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x20?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup22 == 0) ->
          atomic {
            sup2.sup22 = 1;
          u2.po.x20?jeton;
          }
          do
          ::
u2.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x20!jeton; sup2.sup22 = 0;goto SUITE_UP;}
            :: else ->
u1.io.x20?mess;
            fi;
          od;
        :: (sup2.sup32 == 0) ->
          atomic {
            sup2.sup32 = 1;
          u2.po.x21?jeton;
          }
          do
          ::
u2.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x21!jeton; sup2.sup32 = 0; goto SUITE_UP;}
            :: else -> 
u1.io.x20?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in6_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u1.io.x30?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x20?jeton;
          do
          ::
d2.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x30?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x21?jeton;
          do
          ::
d2.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x30?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup22 == 0) ->
          atomic {
            sup2.sup22 = 1;
          u2.po.x20?jeton;
          }
          do
          ::
u2.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x20!jeton; sup2.sup22 = 0;goto SUITE_UP;}
            :: else ->
u1.io.x30?mess;
            fi;
          od;
        :: (sup2.sup32 == 0) ->
          atomic {
            sup2.sup32 = 1;
          u2.po.x21?jeton;
          }
          do
          ::
u2.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x21!jeton; sup2.sup32 = 0; goto SUITE_UP;}
            :: else -> 
u1.io.x30?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in6_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d3.io.x01?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x20?jeton;
          do
          ::
d2.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x01?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x21?jeton;
          do
          ::
d2.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x01?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup22 == 0) ->
          atomic {
            sup2.sup22 = 1;
          u2.po.x20?jeton;
          }
          do
          ::
u2.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x20!jeton; sup2.sup22 = 0;goto SUITE_UP;}
            :: else ->
d3.io.x01?mess;
            fi;
          od;
        :: (sup2.sup32 == 0) ->
          atomic {
            sup2.sup32 = 1;
          u2.po.x21?jeton;
          }
          do
          ::
u2.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x21!jeton; sup2.sup32 = 0; goto SUITE_UP;}
            :: else -> 
d3.io.x01?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in6_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d3.io.x21?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x20?jeton;
          do
          ::
d2.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x21?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x21?jeton;
          do
          ::
d2.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x21?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup22 == 0) ->
          atomic {
            sup2.sup22 = 1;
          u2.po.x20?jeton;
          }
          do
          ::
u2.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x20!jeton; sup2.sup22 = 0;goto SUITE_UP;}
            :: else ->
d3.io.x21?mess;
            fi;
          od;
        :: (sup2.sup32 == 0) ->
          atomic {
            sup2.sup32 = 1;
          u2.po.x21?jeton;
          }
          do
          ::
u2.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x21!jeton; sup2.sup32 = 0; goto SUITE_UP;}
            :: else -> 
d3.io.x21?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in7_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u1.io.x21?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x30?jeton;
          do
          ::
d2.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x21?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x31?jeton;
          do
          ::
d2.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x21?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup23 == 0) ->
          atomic {
            sup2.sup23 = 1;
          u2.po.x30?jeton;
          }
          do
          ::
u2.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x30!jeton; sup2.sup23 = 0;goto SUITE_UP;}
            :: else ->
u1.io.x21?mess;
            fi;
          od;
        :: (sup2.sup33 == 0) ->
          atomic {
            sup2.sup33 = 1;
          u2.po.x31?jeton;
          }
          do
          ::
u2.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x31!jeton; sup2.sup33 = 0; goto SUITE_UP;}
            :: else -> 
u1.io.x21?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in7_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u1.io.x31?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x30?jeton;
          do
          ::
d2.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x31?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x31?jeton;
          do
          ::
d2.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
u1.io.x31?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup23 == 0) ->
          atomic {
            sup2.sup23 = 1;
          u2.po.x30?jeton;
          }
          do
          ::
u2.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x30!jeton; sup2.sup23 = 0;goto SUITE_UP;}
            :: else ->
u1.io.x31?mess;
            fi;
          od;
        :: (sup2.sup33 == 0) ->
          atomic {
            sup2.sup33 = 1;
          u2.po.x31?jeton;
          }
          do
          ::
u2.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x31!jeton; sup2.sup33 = 0; goto SUITE_UP;}
            :: else -> 
u1.io.x31?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in7_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d3.io.x11?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x30?jeton;
          do
          ::
d2.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x11?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x31?jeton;
          do
          ::
d2.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x11?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup23 == 0) ->
          atomic {
            sup2.sup23 = 1;
          u2.po.x30?jeton;
          }
          do
          ::
u2.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x30!jeton; sup2.sup23 = 0;goto SUITE_UP;}
            :: else ->
d3.io.x11?mess;
            fi;
          od;
        :: (sup2.sup33 == 0) ->
          atomic {
            sup2.sup33 = 1;
          u2.po.x31?jeton;
          }
          do
          ::
u2.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x31!jeton; sup2.sup33 = 0; goto SUITE_UP;}
            :: else -> 
d3.io.x11?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in7_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
d3.io.x31?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d2.po.x30?jeton;
          do
          ::
d2.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x31?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d2.po.x31?jeton;
          do
          ::
d2.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d2.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
d3.io.x31?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup2.sup23 == 0) ->
          atomic {
            sup2.sup23 = 1;
          u2.po.x30?jeton;
          }
          do
          ::
u2.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x30!jeton; sup2.sup23 = 0;goto SUITE_UP;}
            :: else ->
d3.io.x31?mess;
            fi;
          od;
        :: (sup2.sup33 == 0) ->
          atomic {
            sup2.sup33 = 1;
          u2.po.x31?jeton;
          }
          do
          ::
u2.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u2.po.x31!jeton; sup2.sup33 = 0; goto SUITE_UP;}
            :: else -> 
d3.io.x31?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in8_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u2.io.x00?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x00?jeton;
          do
          ::
d3.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x00?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x01?jeton;
          do
          ::
d3.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x00?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup20 == 0) ->
          atomic {
            sup3.sup20 = 1;
          u3.po.x00?jeton;
          }
          do
          ::
u3.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x00!jeton; sup3.sup20 = 0;goto SUITE_UP;}
            :: else ->
u2.io.x00?mess;
            fi;
          od;
        :: (sup3.sup30 == 0) ->
          atomic {
            sup3.sup30 = 1;
          u3.po.x01?jeton;
          }
          do
          ::
u3.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x01!jeton; sup3.sup30 = 0; goto SUITE_UP;}
            :: else -> 
u2.io.x00?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in8_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u2.io.x20?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x00?jeton;
          do
          ::
d3.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x20?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x01?jeton;
          do
          ::
d3.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x20?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup20 == 0) ->
          atomic {
            sup3.sup20 = 1;
          u3.po.x00?jeton;
          }
          do
          ::
u3.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x00!jeton; sup3.sup20 = 0;goto SUITE_UP;}
            :: else ->
u2.io.x20?mess;
            fi;
          od;
        :: (sup3.sup30 == 0) ->
          atomic {
            sup3.sup30 = 1;
          u3.po.x01?jeton;
          }
          do
          ::
u3.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x01!jeton; sup3.sup30 = 0; goto SUITE_UP;}
            :: else -> 
u2.io.x20?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in8_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u3.io.x01?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x00?jeton;
          do
          ::
d3.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x01?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x01?jeton;
          do
          ::
d3.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x01?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup20 == 0) ->
          atomic {
            sup3.sup20 = 1;
          u3.po.x00?jeton;
          }
          do
          ::
u3.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x00!jeton; sup3.sup20 = 0;goto SUITE_UP;}
            :: else ->
u3.io.x01?mess;
            fi;
          od;
        :: (sup3.sup30 == 0) ->
          atomic {
            sup3.sup30 = 1;
          u3.po.x01?jeton;
          }
          do
          ::
u3.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x01!jeton; sup3.sup30 = 0; goto SUITE_UP;}
            :: else -> 
u3.io.x01?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in8_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u3.io.x00?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x00?jeton;
          do
          ::
d3.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x00!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x00?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x01?jeton;
          do
          ::
d3.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x01!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x00?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup20 == 0) ->
          atomic {
            sup3.sup20 = 1;
          u3.po.x00?jeton;
          }
          do
          ::
u3.io.x00!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x00!jeton; sup3.sup20 = 0;goto SUITE_UP;}
            :: else ->
u3.io.x00?mess;
            fi;
          od;
        :: (sup3.sup30 == 0) ->
          atomic {
            sup3.sup30 = 1;
          u3.po.x01?jeton;
          }
          do
          ::
u3.io.x01!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x01!jeton; sup3.sup30 = 0; goto SUITE_UP;}
            :: else -> 
u3.io.x00?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in9_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u2.io.x10?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x10?jeton;
          do
          ::
d3.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x10?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x11?jeton;
          do
          ::
d3.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x10?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup21 == 0) ->
          atomic {
            sup3.sup21 = 1;
          u3.po.x10?jeton;
          }
          do
          ::
u3.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x10!jeton; sup3.sup21 = 0;goto SUITE_UP;}
            :: else ->
u2.io.x10?mess;
            fi;
          od;
        :: (sup3.sup31 == 0) ->
          atomic {
            sup3.sup31 = 1;
          u3.po.x11?jeton;
          }
          do
          ::
u3.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x11!jeton; sup3.sup31 = 0; goto SUITE_UP;}
            :: else -> 
u2.io.x10?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in9_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u2.io.x30?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x10?jeton;
          do
          ::
d3.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x30?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x11?jeton;
          do
          ::
d3.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x30?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup21 == 0) ->
          atomic {
            sup3.sup21 = 1;
          u3.po.x10?jeton;
          }
          do
          ::
u3.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x10!jeton; sup3.sup21 = 0;goto SUITE_UP;}
            :: else ->
u2.io.x30?mess;
            fi;
          od;
        :: (sup3.sup31 == 0) ->
          atomic {
            sup3.sup31 = 1;
          u3.po.x11?jeton;
          }
          do
          ::
u3.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x11!jeton; sup3.sup31 = 0; goto SUITE_UP;}
            :: else -> 
u2.io.x30?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in9_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u3.io.x11?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x10?jeton;
          do
          ::
d3.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x11?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x11?jeton;
          do
          ::
d3.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x11?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup21 == 0) ->
          atomic {
            sup3.sup21 = 1;
          u3.po.x10?jeton;
          }
          do
          ::
u3.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x10!jeton; sup3.sup21 = 0;goto SUITE_UP;}
            :: else ->
u3.io.x11?mess;
            fi;
          od;
        :: (sup3.sup31 == 0) ->
          atomic {
            sup3.sup31 = 1;
          u3.po.x11?jeton;
          }
          do
          ::
u3.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x11!jeton; sup3.sup31 = 0; goto SUITE_UP;}
            :: else -> 
u3.io.x11?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in9_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u3.io.x10?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x10?jeton;
          do
          ::
d3.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x10!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x10?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x11?jeton;
          do
          ::
d3.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x11!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x10?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup21 == 0) ->
          atomic {
            sup3.sup21 = 1;
          u3.po.x10?jeton;
          }
          do
          ::
u3.io.x10!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x10!jeton; sup3.sup21 = 0;goto SUITE_UP;}
            :: else ->
u3.io.x10?mess;
            fi;
          od;
        :: (sup3.sup31 == 0) ->
          atomic {
            sup3.sup31 = 1;
          u3.po.x11?jeton;
          }
          do
          ::
u3.io.x11!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x11!jeton; sup3.sup31 = 0; goto SUITE_UP;}
            :: else -> 
u3.io.x10?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in10_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u2.io.x01?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x20?jeton;
          do
          ::
d3.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x01?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x21?jeton;
          do
          ::
d3.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x01?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup22 == 0) ->
          atomic {
            sup3.sup22 = 1;
          u3.po.x20?jeton;
          }
          do
          ::
u3.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x20!jeton; sup3.sup22 = 0;goto SUITE_UP;}
            :: else ->
u2.io.x01?mess;
            fi;
          od;
        :: (sup3.sup32 == 0) ->
          atomic {
            sup3.sup32 = 1;
          u3.po.x21?jeton;
          }
          do
          ::
u3.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x21!jeton; sup3.sup32 = 0; goto SUITE_UP;}
            :: else -> 
u2.io.x01?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in10_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u2.io.x21?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x20?jeton;
          do
          ::
d3.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x21?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x21?jeton;
          do
          ::
d3.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x21?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup22 == 0) ->
          atomic {
            sup3.sup22 = 1;
          u3.po.x20?jeton;
          }
          do
          ::
u3.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x20!jeton; sup3.sup22 = 0;goto SUITE_UP;}
            :: else ->
u2.io.x21?mess;
            fi;
          od;
        :: (sup3.sup32 == 0) ->
          atomic {
            sup3.sup32 = 1;
          u3.po.x21?jeton;
          }
          do
          ::
u3.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x21!jeton; sup3.sup32 = 0; goto SUITE_UP;}
            :: else -> 
u2.io.x21?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in10_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u3.io.x11?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x20?jeton;
          do
          ::
d3.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x11?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x21?jeton;
          do
          ::
d3.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x11?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup22 == 0) ->
          atomic {
            sup3.sup22 = 1;
          u3.po.x20?jeton;
          }
          do
          ::
u3.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x20!jeton; sup3.sup22 = 0;goto SUITE_UP;}
            :: else ->
u3.io.x11?mess;
            fi;
          od;
        :: (sup3.sup32 == 0) ->
          atomic {
            sup3.sup32 = 1;
          u3.po.x21?jeton;
          }
          do
          ::
u3.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x21!jeton; sup3.sup32 = 0; goto SUITE_UP;}
            :: else -> 
u3.io.x11?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in10_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u3.io.x10?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x20?jeton;
          do
          ::
d3.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x20!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x10?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x21?jeton;
          do
          ::
d3.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x21!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x10?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup22 == 0) ->
          atomic {
            sup3.sup22 = 1;
          u3.po.x20?jeton;
          }
          do
          ::
u3.io.x20!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x20!jeton; sup3.sup22 = 0;goto SUITE_UP;}
            :: else ->
u3.io.x10?mess;
            fi;
          od;
        :: (sup3.sup32 == 0) ->
          atomic {
            sup3.sup32 = 1;
          u3.po.x21?jeton;
          }
          do
          ::
u3.io.x21!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x21!jeton; sup3.sup32 = 0; goto SUITE_UP;}
            :: else -> 
u3.io.x10?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in11_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u2.io.x11?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x30?jeton;
          do
          ::
d3.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x11?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x31?jeton;
          do
          ::
d3.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x11?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup23 == 0) ->
          atomic {
            sup3.sup23 = 1;
          u3.po.x30?jeton;
          }
          do
          ::
u3.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x30!jeton; sup3.sup23 = 0;goto SUITE_UP;}
            :: else ->
u2.io.x11?mess;
            fi;
          od;
        :: (sup3.sup33 == 0) ->
          atomic {
            sup3.sup33 = 1;
          u3.po.x31?jeton;
          }
          do
          ::
u3.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x31!jeton; sup3.sup33 = 0; goto SUITE_UP;}
            :: else -> 
u2.io.x11?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in11_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u2.io.x31?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x30?jeton;
          do
          ::
d3.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x31?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x31?jeton;
          do
          ::
d3.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
u2.io.x31?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup23 == 0) ->
          atomic {
            sup3.sup23 = 1;
          u3.po.x30?jeton;
          }
          do
          ::
u3.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x30!jeton; sup3.sup23 = 0;goto SUITE_UP;}
            :: else ->
u2.io.x31?mess;
            fi;
          od;
        :: (sup3.sup33 == 0) ->
          atomic {
            sup3.sup33 = 1;
          u3.po.x31?jeton;
          }
          do
          ::
u3.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x31!jeton; sup3.sup33 = 0; goto SUITE_UP;}
            :: else -> 
u2.io.x31?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in11_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u3.io.x31?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x30?jeton;
          do
          ::
d3.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x31?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x31?jeton;
          do
          ::
d3.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x31?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup23 == 0) ->
          atomic {
            sup3.sup23 = 1;
          u3.po.x30?jeton;
          }
          do
          ::
u3.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x30!jeton; sup3.sup23 = 0;goto SUITE_UP;}
            :: else ->
u3.io.x31?mess;
            fi;
          od;
        :: (sup3.sup33 == 0) ->
          atomic {
            sup3.sup33 = 1;
          u3.po.x31?jeton;
          }
          do
          ::
u3.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x31!jeton; sup3.sup33 = 0; goto SUITE_UP;}
            :: else -> 
u3.io.x31?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}
active[1] proctype proc_port_in11_3()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
u3.io.x30?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          d3.po.x30?jeton;
          do
          ::
d3.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x30!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x30?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          d3.po.x31?jeton;
          do
          ::
d3.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{d3.po.x31!jeton; goto SUITE_DOWN;}
            :: else -> 
u3.io.x30?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sup3.sup23 == 0) ->
          atomic {
            sup3.sup23 = 1;
          u3.po.x30?jeton;
          }
          do
          ::
u3.io.x30!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x30!jeton; sup3.sup23 = 0;goto SUITE_UP;}
            :: else ->
u3.io.x30?mess;
            fi;
          od;
        :: (sup3.sup33 == 0) ->
          atomic {
            sup3.sup33 = 1;
          u3.po.x31?jeton;
          }
          do
          ::
u3.io.x31!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{u3.po.x31!jeton; sup3.sup33 = 0; goto SUITE_UP;}
            :: else -> 
u3.io.x30?mess;
            fi;
          od;
        fi;
    
      SUITE_UP :
skip;
      
    fi;
  od;

}

init
{


  atomic {
    /* Initialisation de certaines variables globales */
/*    sprogress = 0;*/
       
    /*sortie_up2[0]  = 0;
    sortie_up2[1]  = 0;
    sortie_up3[0]  = 0;
    sortie_up3[1]  = 0;*/
   /* sortie_up20  = 0;
    sortie_up21  = 0;
    sortie_up30  = 0;
    sortie_up31  = 0;*/


		/* Démarage du process de l'horloge */
/*		run watchdog();*/
      
    /* CONFIGURATION DE DEADLOCK */
    /* Dépamrage des process associés aux initiateurs et aux cibles */

        d1.po.x00!1;
        d1.po.x01!1;
        d1.po.x10!1;
        d1.po.x11!1;
        d1.po.x20!1;
        d1.po.x21!1;
        d1.po.x30!1;
        d1.po.x31!1;
        u1.po.x00!1;
        u1.po.x01!1;
        u1.po.x10!1;
        u1.po.x11!1;
        u1.po.x20!1;
        u1.po.x21!1;
        u1.po.x30!1;
        u1.po.x31!1;

        d2.po.x00!1;
        d2.po.x01!1;
        d2.po.x10!1;
        d2.po.x11!1;
        d2.po.x20!1;
        d2.po.x21!1;
        d2.po.x30!1;
        d2.po.x31!1;
        u2.po.x00!1;
        u2.po.x01!1;
        u2.po.x10!1;
        u2.po.x11!1;
        u2.po.x20!1;
        u2.po.x21!1;
        u2.po.x30!1;
        u2.po.x31!1;

        d3.po.x00!1;
        d3.po.x01!1;
        d3.po.x10!1;
        d3.po.x11!1;
        d3.po.x20!1;
        d3.po.x21!1;
        d3.po.x30!1;
        d3.po.x31!1;
        u3.po.x00!1;
        u3.po.x01!1;
        u3.po.x10!1;
        u3.po.x11!1;
        u3.po.x20!1;
        u3.po.x21!1;
        u3.po.x30!1;
        u3.po.x31!1;
    active_all=1;

   } /* Fin du atomic */
} /* Fin de la fonction init */

        