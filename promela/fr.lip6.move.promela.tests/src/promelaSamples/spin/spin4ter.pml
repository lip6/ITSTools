
 typedef MOT {
 short adsource;
 short addest;
 short tag;
 };
chan poss_out0_0 = [1] of {bit};
chan poss_out0_1 = [1] of {bit};
chan poss_out0_2 = [1] of {bit};
chan poss_out0_3 = [1] of {bit};
               
chan poss_out1_0 = [1] of {bit};
chan poss_out1_1 = [1] of {bit};
chan poss_out1_2 = [1] of {bit};
chan poss_out1_3 = [1] of {bit};

chan poss_out2_0 = [1] of {bit};
chan poss_out2_1 = [1] of {bit};
chan poss_out2_2 = [1] of {bit};
chan poss_out2_3 = [1] of {bit};
               
chan poss_out3_0 = [1] of {bit};
chan poss_out3_1 = [1] of {bit};
chan poss_out3_2 = [1] of {bit};
chan poss_out3_3 = [1] of {bit};

/*short sortie_up2[2];
short sortie_up3[2];*/
short sortie_up20;
short sortie_up30;
short sortie_up21;
short sortie_up31;
short sortie_up22;
short sortie_up32;
short sortie_up23;
short sortie_up33;
chan x0_d0_spin_data_in = [1] of {MOT};
chan x0_d0_spin_data_out = [1] of {MOT};
chan x0_d1_spin_data_in = [1] of {MOT};
chan x0_d1_spin_data_out = [1] of {MOT};
chan x0_u0_spin_data_out = [1] of {MOT};
chan x0_u1_spin_data_out = [1] of {MOT};
chan x1_d0_spin_data_in = [1] of {MOT};
chan x1_d0_spin_data_out = [1] of {MOT};
chan x1_d1_spin_data_in = [1] of {MOT};
chan x1_d1_spin_data_out = [1] of {MOT};
chan x1_u0_spin_data_out = [1] of {MOT};
chan x1_u1_spin_data_out = [1] of {MOT};
chan x2_d0_spin_data_in = [1] of {MOT};
chan x2_d0_spin_data_out = [1] of {MOT};
chan x2_d1_spin_data_in = [1] of {MOT};
chan x2_d1_spin_data_out = [1] of {MOT};
chan x2_u0_spin_data_out = [1] of {MOT};
chan x2_u1_spin_data_out = [1] of {MOT};
chan x3_d0_spin_data_in = [1] of {MOT};
chan x3_d0_spin_data_out = [1] of {MOT};
chan x3_d1_spin_data_in = [1] of {MOT};
chan x3_d1_spin_data_out = [1] of {MOT};
chan x3_u0_spin_data_out = [1] of {MOT};
chan x3_u1_spin_data_out = [1] of {MOT};

chan chan_sync0 = [1] of {bit};
chan chan_sync2 = [1] of {bit};

/* Variable globale de l'horloge */


       active[1] proctype proc_envoi0()
{
  MOT mot;
  /* numéro du mot à envoyer */
  short num_mot; 
  short num_mot_recu; 
  bit sync;

  atomic { 
    num_mot = 1;
    mot.adsource = 0;
if
::    mot.addest = 1;
::    mot.addest = 3;
fi;
  }
  
  do
    ::x0_d0_spin_data_in![_];
      if
      :: ((num_mot == 1) /*&& (nfull(x0_d0_spin_data_in))*/) -> 
        /* Mot de début */
        mot.tag = 1;
x0_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 3)) /*&& (nfull(x0_d0_spin_data_in))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
x0_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot <= 7) /*&& (nfull(x0_d0_spin_data_in))*/) -> 
        /* Mot de fin */
        mot.tag = 2;
x0_d0_spin_data_in!mot;
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
  
  do
::

    
x0_d0_spin_data_out?din;
    
    num_mot_recu = num_mot_recu + 1;
    
    if
    :: (num_mot_recu == (4)) ->
      
      
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
  
  do
::
      /* Réception d'une requête */
x0_d1_spin_data_out?dt;
      
      /* Envoi d'une réponse */
      atomic {
        dt.addest = dt.adsource;
        dt.adsource = 1;
      }
x0_d1_spin_data_in!dt;
  od; 
}
active[1] proctype proc_envoi2()
{
  MOT mot;
  /* numéro du mot à envoyer */
  short num_mot; 
  short num_mot_recu; 
  bit sync;

  atomic { 
    num_mot = 1;
    mot.adsource = 2;
if
::    mot.addest = 1;
::    mot.addest = 3;
fi;
  }
  
  do
    ::x1_d0_spin_data_in![_];
      if
      :: ((num_mot == 1) /*&& (nfull(x1_d0_spin_data_in))*/) -> 
        /* Mot de début */
        mot.tag = 1;
x1_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 3)) /*&& (nfull(x1_d0_spin_data_in))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
x1_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot <= 3) /*&& (nfull(x1_d0_spin_data_in))*/) -> 
        /* Mot de fin */
        mot.tag = 2;
x1_d0_spin_data_in!mot;
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
  
  do
::

    
x1_d0_spin_data_out?din;
    
    num_mot_recu = num_mot_recu + 1;
    
    if
    :: (num_mot_recu == (4)) ->
      
      
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
  
  do
::
      /* Réception d'une requête */
x1_d1_spin_data_out?dt;
      
      /* Envoi d'une réponse */
      atomic {
        dt.addest = dt.adsource;
        dt.adsource = 3;
      }
x1_d1_spin_data_in!dt;
  od; 
}
active[1] proctype proc_port_in0_0()
{
MOT mess;
  bit reserve;

  short sortie_down;
  bit jeton = 1;

  do
::
x0_d0_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out0_0?jeton;
          do
          ::
x0_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x0_d0_spin_data_in?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out0_1?jeton; 
          do
          ::
x0_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x0_d0_spin_data_in?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up20 == 0) ->
          atomic {
            sortie_up20 = 1;
            poss_out0_2?jeton;
          }
          do
          ::
x0_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_2!jeton; sortie_up20 = 0; goto SUITE_UP;}
            :: else ->
x0_d0_spin_data_in?mess;
            fi;
          od;
        :: (sortie_up30 == 0) ->
          atomic {
            sortie_up30 = 1;
            poss_out0_3?jeton;
          }
          do
          ::
x0_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out0_3!jeton; sortie_up30 = 0; goto SUITE_UP;}
            :: else -> 
x0_d0_spin_data_in?mess;
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
  bit jeton = 1;

  do
::
x0_d1_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out0_0?jeton;
          do
          ::
x0_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x0_d1_spin_data_in?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out0_1?jeton; 
          do
          ::
x0_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x0_d1_spin_data_in?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up20 == 0) ->
          atomic {
            sortie_up20 = 1;
            poss_out0_2?jeton;
          }
          do
          ::
x0_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_2!jeton; sortie_up20 = 0; goto SUITE_UP;}
            :: else ->
x0_d1_spin_data_in?mess;
            fi;
          od;
        :: (sortie_up30 == 0) ->
          atomic {
            sortie_up30 = 1;
            poss_out0_3?jeton;
          }
          do
          ::
x0_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out0_3!jeton; sortie_up30 = 0; goto SUITE_UP;}
            :: else -> 
x0_d1_spin_data_in?mess;
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
  bit jeton = 1;

  do
::
x2_d0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out0_0?jeton;
          do
          ::
x0_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x2_d0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out0_1?jeton; 
          do
          ::
x0_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x2_d0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up20 == 0) ->
          atomic {
            sortie_up20 = 1;
            poss_out0_2?jeton;
          }
          do
          ::
x0_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_2!jeton; sortie_up20 = 0; goto SUITE_UP;}
            :: else ->
x2_d0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up30 == 0) ->
          atomic {
            sortie_up30 = 1;
            poss_out0_3?jeton;
          }
          do
          ::
x0_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out0_3!jeton; sortie_up30 = 0; goto SUITE_UP;}
            :: else -> 
x2_d0_spin_data_out?mess;
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
  bit jeton = 1;

  do
::
x3_d0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out0_0?jeton;
          do
          ::
x0_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x3_d0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out0_1?jeton; 
          do
          ::
x0_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x3_d0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up20 == 0) ->
          atomic {
            sortie_up20 = 1;
            poss_out0_2?jeton;
          }
          do
          ::
x0_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out0_2!jeton; sortie_up20 = 0; goto SUITE_UP;}
            :: else ->
x3_d0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up30 == 0) ->
          atomic {
            sortie_up30 = 1;
            poss_out0_3?jeton;
          }
          do
          ::
x0_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out0_3!jeton; sortie_up30 = 0; goto SUITE_UP;}
            :: else -> 
x3_d0_spin_data_out?mess;
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
  bit jeton = 1;

  do
::
x1_d0_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out1_0?jeton;
          do
          ::
x1_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x1_d0_spin_data_in?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out1_1?jeton; 
          do
          ::
x1_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x1_d0_spin_data_in?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up21 == 0) ->
          atomic {
            sortie_up21 = 1;
            poss_out1_2?jeton;
          }
          do
          ::
x1_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_2!jeton; sortie_up21 = 0; goto SUITE_UP;}
            :: else ->
x1_d0_spin_data_in?mess;
            fi;
          od;
        :: (sortie_up31 == 0) ->
          atomic {
            sortie_up31 = 1;
            poss_out1_3?jeton;
          }
          do
          ::
x1_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out1_3!jeton; sortie_up31 = 0; goto SUITE_UP;}
            :: else -> 
x1_d0_spin_data_in?mess;
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
  bit jeton = 1;

  do
::
x1_d1_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out1_0?jeton;
          do
          ::
x1_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x1_d1_spin_data_in?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out1_1?jeton; 
          do
          ::
x1_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x1_d1_spin_data_in?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up21 == 0) ->
          atomic {
            sortie_up21 = 1;
            poss_out1_2?jeton;
          }
          do
          ::
x1_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_2!jeton; sortie_up21 = 0; goto SUITE_UP;}
            :: else ->
x1_d1_spin_data_in?mess;
            fi;
          od;
        :: (sortie_up31 == 0) ->
          atomic {
            sortie_up31 = 1;
            poss_out1_3?jeton;
          }
          do
          ::
x1_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out1_3!jeton; sortie_up31 = 0; goto SUITE_UP;}
            :: else -> 
x1_d1_spin_data_in?mess;
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
  bit jeton = 1;

  do
::
x2_d1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out1_0?jeton;
          do
          ::
x1_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x2_d1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out1_1?jeton; 
          do
          ::
x1_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x2_d1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up21 == 0) ->
          atomic {
            sortie_up21 = 1;
            poss_out1_2?jeton;
          }
          do
          ::
x1_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_2!jeton; sortie_up21 = 0; goto SUITE_UP;}
            :: else ->
x2_d1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up31 == 0) ->
          atomic {
            sortie_up31 = 1;
            poss_out1_3?jeton;
          }
          do
          ::
x1_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out1_3!jeton; sortie_up31 = 0; goto SUITE_UP;}
            :: else -> 
x2_d1_spin_data_out?mess;
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
  bit jeton = 1;

  do
::
x3_d1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out1_0?jeton;
          do
          ::
x1_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x3_d1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out1_1?jeton; 
          do
          ::
x1_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x3_d1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up21 == 0) ->
          atomic {
            sortie_up21 = 1;
            poss_out1_2?jeton;
          }
          do
          ::
x1_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out1_2!jeton; sortie_up21 = 0; goto SUITE_UP;}
            :: else ->
x3_d1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up31 == 0) ->
          atomic {
            sortie_up31 = 1;
            poss_out1_3?jeton;
          }
          do
          ::
x1_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out1_3!jeton; sortie_up31 = 0; goto SUITE_UP;}
            :: else -> 
x3_d1_spin_data_out?mess;
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
  bit jeton = 1;

  do
::
x0_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out2_0?jeton;
          do
          ::
x2_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x0_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out2_1?jeton; 
          do
          ::
x2_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x0_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up22 == 0) ->
          atomic {
            sortie_up22 = 1;
            poss_out2_2?jeton;
          }
          do
          ::
x2_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_2!jeton; sortie_up22 = 0; goto SUITE_UP;}
            :: else ->
x0_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up32 == 0) ->
          atomic {
            sortie_up32 = 1;
            poss_out2_3?jeton;
          }
          do
          ::
x2_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out2_3!jeton; sortie_up32 = 0; goto SUITE_UP;}
            :: else -> 
x0_u0_spin_data_out?mess;
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
  bit jeton = 1;

  do
::
x1_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out2_0?jeton;
          do
          ::
x2_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x1_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out2_1?jeton; 
          do
          ::
x2_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x1_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up22 == 0) ->
          atomic {
            sortie_up22 = 1;
            poss_out2_2?jeton;
          }
          do
          ::
x2_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_2!jeton; sortie_up22 = 0; goto SUITE_UP;}
            :: else ->
x1_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up32 == 0) ->
          atomic {
            sortie_up32 = 1;
            poss_out2_3?jeton;
          }
          do
          ::
x2_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out2_3!jeton; sortie_up32 = 0; goto SUITE_UP;}
            :: else -> 
x1_u0_spin_data_out?mess;
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
  bit jeton = 1;

  do
::
x2_d0_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out2_0?jeton;
          do
          ::
x2_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x2_d0_spin_data_in?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out2_1?jeton; 
          do
          ::
x2_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x2_d0_spin_data_in?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up22 == 0) ->
          atomic {
            sortie_up22 = 1;
            poss_out2_2?jeton;
          }
          do
          ::
x2_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_2!jeton; sortie_up22 = 0; goto SUITE_UP;}
            :: else ->
x2_d0_spin_data_in?mess;
            fi;
          od;
        :: (sortie_up32 == 0) ->
          atomic {
            sortie_up32 = 1;
            poss_out2_3?jeton;
          }
          do
          ::
x2_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out2_3!jeton; sortie_up32 = 0; goto SUITE_UP;}
            :: else -> 
x2_d0_spin_data_in?mess;
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
  bit jeton = 1;

  do
::
x2_d1_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out2_0?jeton;
          do
          ::
x2_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x2_d1_spin_data_in?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out2_1?jeton; 
          do
          ::
x2_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x2_d1_spin_data_in?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up22 == 0) ->
          atomic {
            sortie_up22 = 1;
            poss_out2_2?jeton;
          }
          do
          ::
x2_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_2!jeton; sortie_up22 = 0; goto SUITE_UP;}
            :: else ->
x2_d1_spin_data_in?mess;
            fi;
          od;
        :: (sortie_up32 == 0) ->
          atomic {
            sortie_up32 = 1;
            poss_out2_3?jeton;
          }
          do
          ::
x2_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out2_3!jeton; sortie_up32 = 0; goto SUITE_UP;}
            :: else -> 
x2_d1_spin_data_in?mess;
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
  bit jeton = 1;

  do
::
x0_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out3_0?jeton;
          do
          ::
x3_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x0_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out3_1?jeton; 
          do
          ::
x3_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x0_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up23 == 0) ->
          atomic {
            sortie_up23 = 1;
            poss_out3_2?jeton;
          }
          do
          ::
x3_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_2!jeton; sortie_up23 = 0; goto SUITE_UP;}
            :: else ->
x0_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up33 == 0) ->
          atomic {
            sortie_up33 = 1;
            poss_out3_3?jeton;
          }
          do
          ::
x3_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out3_3!jeton; sortie_up33 = 0; goto SUITE_UP;}
            :: else -> 
x0_u1_spin_data_out?mess;
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
  bit jeton = 1;

  do
::
x1_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out3_0?jeton;
          do
          ::
x3_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x1_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out3_1?jeton; 
          do
          ::
x3_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x1_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up23 == 0) ->
          atomic {
            sortie_up23 = 1;
            poss_out3_2?jeton;
          }
          do
          ::
x3_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_2!jeton; sortie_up23 = 0; goto SUITE_UP;}
            :: else ->
x1_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up33 == 0) ->
          atomic {
            sortie_up33 = 1;
            poss_out3_3?jeton;
          }
          do
          ::
x3_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out3_3!jeton; sortie_up33 = 0; goto SUITE_UP;}
            :: else -> 
x1_u1_spin_data_out?mess;
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
  bit jeton = 1;

  do
::
x3_d0_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out3_0?jeton;
          do
          ::
x3_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x3_d0_spin_data_in?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out3_1?jeton; 
          do
          ::
x3_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x3_d0_spin_data_in?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up23 == 0) ->
          atomic {
            sortie_up23 = 1;
            poss_out3_2?jeton;
          }
          do
          ::
x3_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_2!jeton; sortie_up23 = 0; goto SUITE_UP;}
            :: else ->
x3_d0_spin_data_in?mess;
            fi;
          od;
        :: (sortie_up33 == 0) ->
          atomic {
            sortie_up33 = 1;
            poss_out3_3?jeton;
          }
          do
          ::
x3_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out3_3!jeton; sortie_up33 = 0; goto SUITE_UP;}
            :: else -> 
x3_d0_spin_data_in?mess;
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
  bit jeton = 1;

  do
::
x3_d1_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out3_0?jeton;
          do
          ::
x3_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x3_d1_spin_data_in?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out3_1?jeton; 
          do
          ::
x3_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x3_d1_spin_data_in?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up23 == 0) ->
          atomic {
            sortie_up23 = 1;
            poss_out3_2?jeton;
          }
          do
          ::
x3_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_2!jeton; sortie_up23 = 0; goto SUITE_UP;}
            :: else ->
x3_d1_spin_data_in?mess;
            fi;
          od;
        :: (sortie_up33 == 0) ->
          atomic {
            sortie_up33 = 1;
            poss_out3_3?jeton;
          }
          do
          ::
x3_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out3_3!jeton; sortie_up33 = 0; goto SUITE_UP;}
            :: else -> 
x3_d1_spin_data_in?mess;
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
  /*  run proc_envoi_test0();
    run proc_reception_test0();
    run proc_envoi2();
    run proc_reception2();
    run v2scible1();
    run v2scible3();

*/
      
    /* Dépamrage des process associés aux routeurs RSPIN 0*/
    /* Libération des sémaphores */
    poss_out0_0!1;
    poss_out0_1!1;
    poss_out0_2!1;
    poss_out0_3!1;
  /*  
    run proc_port_in0_0();
    run proc_port_in0_1();
    run proc_port_in0_2();
    run proc_port_in0_3();
*/

    /* Dépamrage des process associés aux routeurs RSPIN 1*/
    /* Libération des sémaphores */
    poss_out1_0!1;
    poss_out1_1!1;
    poss_out1_2!1;
    poss_out1_3!1;
  /*  
    run proc_port_in1_0();
    run proc_port_in1_1();
    run proc_port_in1_2();
    run proc_port_in1_3();
 
*/
    /* Dépamrage des process associés aux routeurs RSPIN 0*/
    /* Libération des sémaphores */
    poss_out2_0!1;
    poss_out2_1!1;
    poss_out2_2!1;
    poss_out2_3!1;
  /*  
    run proc_port_in0_0();
    run proc_port_in0_1();
    run proc_port_in0_2();
    run proc_port_in0_3();
*/

    /* Dépamrage des process associés aux routeurs RSPIN 1*/
    /* Libération des sémaphores */
    poss_out3_0!1;
    poss_out3_1!1;
    poss_out3_2!1;
    poss_out3_3!1;
  /*  
    run proc_port_in1_0();
    run proc_port_in1_1();
    run proc_port_in1_2();
    run proc_port_in1_3();
 
*/


   } /* Fin du atomic */
} /* Fin de la fonction init */

        
