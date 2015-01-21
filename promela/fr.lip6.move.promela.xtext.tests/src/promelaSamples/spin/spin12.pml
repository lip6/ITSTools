
 typedef MOT {
 short adsource;
 short addest;
 short tag;
 };
        bool active_all;
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
//
chan poss_out4_0 = [1] of {bit};
chan poss_out4_1 = [1] of {bit};
chan poss_out4_2 = [1] of {bit};
chan poss_out4_3 = [1] of {bit};
               
chan poss_out5_0 = [1] of {bit};
chan poss_out5_1 = [1] of {bit};
chan poss_out5_2 = [1] of {bit};
chan poss_out5_3 = [1] of {bit};

chan poss_out6_0 = [1] of {bit};
chan poss_out6_1 = [1] of {bit};
chan poss_out6_2 = [1] of {bit};
chan poss_out6_3 = [1] of {bit};
               
chan poss_out7_0 = [1] of {bit};
chan poss_out7_1 = [1] of {bit};
chan poss_out7_2 = [1] of {bit};
chan poss_out7_3 = [1] of {bit};

//
chan poss_out8_0 = [1] of {bit};
chan poss_out8_1 = [1] of {bit};
chan poss_out8_2 = [1] of {bit};
chan poss_out8_3 = [1] of {bit};
               
chan poss_out9_0 = [1] of {bit};
chan poss_out9_1 = [1] of {bit};
chan poss_out9_2 = [1] of {bit};
chan poss_out9_3 = [1] of {bit};

chan poss_out10_0 = [1] of {bit};
chan poss_out10_1 = [1] of {bit};
chan poss_out10_2 = [1] of {bit};
chan poss_out10_3 = [1] of {bit};
               
chan poss_out11_0 = [1] of {bit};
chan poss_out11_1 = [1] of {bit};
chan poss_out11_2 = [1] of {bit};
chan poss_out11_3 = [1] of {bit};


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
//
short sortie_up24;
short sortie_up34;
short sortie_up25;
short sortie_up35;
short sortie_up26;
short sortie_up36;
short sortie_up27;
short sortie_up37;
//
short sortie_up28;
short sortie_up38;
short sortie_up29;
short sortie_up39;
short sortie_up210;
short sortie_up310;
short sortie_up211;
short sortie_up311;
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
chan x4_d0_spin_data_out = [1] of {MOT};
chan x4_d1_spin_data_out = [1] of {MOT};
chan x4_u0_spin_data_out = [1] of {MOT};
chan x4_u1_spin_data_out = [1] of {MOT};
chan x5_d0_spin_data_out = [1] of {MOT};
chan x5_d1_spin_data_out = [1] of {MOT};
chan x5_u0_spin_data_out = [1] of {MOT};
chan x5_u1_spin_data_out = [1] of {MOT};
chan x6_d0_spin_data_out = [1] of {MOT};
chan x6_d1_spin_data_out = [1] of {MOT};
chan x6_u0_spin_data_out = [1] of {MOT};
chan x6_u1_spin_data_out = [1] of {MOT};
chan x7_d0_spin_data_out = [1] of {MOT};
chan x7_d1_spin_data_out = [1] of {MOT};
chan x7_u0_spin_data_out = [1] of {MOT};
chan x7_u1_spin_data_out = [1] of {MOT};
chan x8_d0_spin_data_out = [1] of {MOT};
chan x8_d1_spin_data_out = [1] of {MOT};
chan x8_u0_spin_data_out = [1] of {MOT};
chan x8_u1_spin_data_out = [1] of {MOT};
chan x9_d0_spin_data_out = [1] of {MOT};
chan x9_d1_spin_data_out = [1] of {MOT};
chan x9_u0_spin_data_out = [1] of {MOT};
chan x9_u1_spin_data_out = [1] of {MOT};
chan x10_d0_spin_data_out = [1] of {MOT};
chan x10_d1_spin_data_out = [1] of {MOT};
chan x10_u0_spin_data_out = [1] of {MOT};
chan x10_u1_spin_data_out = [1] of {MOT};
chan x11_d0_spin_data_out = [1] of {MOT};
chan x11_d1_spin_data_out = [1] of {MOT};
chan x11_u0_spin_data_out = [1] of {MOT};
chan x11_u1_spin_data_out = [1] of {MOT};

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
    ::x0_d0_spin_data_in![_];
      if
      :: ((num_mot == 1) /*&& (nfull(x0_d0_spin_data_in))*/) -> 
        /* Mot de début */
        mot.tag = 1;
x0_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 2)) /*&& (nfull(x0_d0_spin_data_in))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
x0_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot == 2) /*&& (nfull(x0_d0_spin_data_in))*/) -> 
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
  (active_all==1);
  do
::

    
x0_d0_spin_data_out?din;
    
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

  atomic { (active_all==1);
    num_mot = 1;
    mot.adsource = 2;
    mot.addest = 1;
  }
  
  do
    ::x1_d0_spin_data_in![_];
      if
      :: ((num_mot == 1) /*&& (nfull(x1_d0_spin_data_in))*/) -> 
        /* Mot de début */
        mot.tag = 1;
x1_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 2)) /*&& (nfull(x1_d0_spin_data_in))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
x1_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot == 2) /*&& (nfull(x1_d0_spin_data_in))*/) -> 
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
  (active_all==1);
  do
::

    
x1_d0_spin_data_out?din;
    
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
x1_d1_spin_data_out?dt;
      
      /* Envoi d'une réponse */
      atomic {
        dt.addest = dt.adsource;
        dt.adsource = 3;
      }
x1_d1_spin_data_in!dt;
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
    ::x2_d0_spin_data_in![_];
      if
      :: ((num_mot == 1) /*&& (nfull(x2_d0_spin_data_in))*/) -> 
        /* Mot de début */
        mot.tag = 1;
x2_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 2)) /*&& (nfull(x2_d0_spin_data_in))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
x2_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot == 2) /*&& (nfull(x2_d0_spin_data_in))*/) -> 
        /* Mot de fin */
        mot.tag = 2;
x2_d0_spin_data_in!mot;
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

    
x2_d0_spin_data_out?din;
    
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
x2_d1_spin_data_out?dt;
      
      /* Envoi d'une réponse */
      atomic {
        dt.addest = dt.adsource;
        dt.adsource = 5;
      }
x2_d1_spin_data_in!dt;
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
    ::x3_d0_spin_data_in![_];
      if
      :: ((num_mot == 1) /*&& (nfull(x3_d0_spin_data_in))*/) -> 
        /* Mot de début */
        mot.tag = 1;
x3_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
      
      :: (((num_mot != 1) && (num_mot < 2)) /*&& (nfull(x3_d0_spin_data_in))*/) -> 
        /* Mot de intermédiaire */
        mot.tag = 0;
x3_d0_spin_data_in!mot;
        num_mot = num_mot + 1;
        
      :: ((num_mot == 2) /*&& (nfull(x3_d0_spin_data_in))*/) -> 
        /* Mot de fin */
        mot.tag = 2;
x3_d0_spin_data_in!mot;
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

    
x3_d0_spin_data_out?din;
    
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
x3_d1_spin_data_out?dt;
      
      /* Envoi d'une réponse */
      atomic {
        dt.addest = dt.adsource;
        dt.adsource = 7;
      }
x3_d1_spin_data_in!dt;
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
(active_all==1);
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
(active_all==1);
  bit jeton = 1;

  do
::
x4_d0_spin_data_out?mess;
    
    
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
x4_d0_spin_data_out?mess;
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
x4_d0_spin_data_out?mess;
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
x4_d0_spin_data_out?mess;
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
x4_d0_spin_data_out?mess;
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
x5_d0_spin_data_out?mess;
    
    
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
x5_d0_spin_data_out?mess;
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
x5_d0_spin_data_out?mess;
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
x5_d0_spin_data_out?mess;
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
x5_d0_spin_data_out?mess;
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
(active_all==1);
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
(active_all==1);
  bit jeton = 1;

  do
::
x4_d1_spin_data_out?mess;
    
    
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
x4_d1_spin_data_out?mess;
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
x4_d1_spin_data_out?mess;
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
x4_d1_spin_data_out?mess;
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
x4_d1_spin_data_out?mess;
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
x5_d1_spin_data_out?mess;
    
    
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
x5_d1_spin_data_out?mess;
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
x5_d1_spin_data_out?mess;
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
x5_d1_spin_data_out?mess;
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
x5_d1_spin_data_out?mess;
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
x2_d0_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 2) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
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
active[1] proctype proc_port_in2_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
x2_d1_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 2) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
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
active[1] proctype proc_port_in2_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
x6_d0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 2) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out2_0?jeton;
          do
          ::
x2_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x6_d0_spin_data_out?mess;
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
x6_d0_spin_data_out?mess;
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
x6_d0_spin_data_out?mess;
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
x6_d0_spin_data_out?mess;
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
x7_d0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 2) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out2_0?jeton;
          do
          ::
x2_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out2_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x7_d0_spin_data_out?mess;
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
x7_d0_spin_data_out?mess;
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
x7_d0_spin_data_out?mess;
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
x7_d0_spin_data_out?mess;
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
x3_d0_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 3) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
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
active[1] proctype proc_port_in3_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
x3_d1_spin_data_in?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 3) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
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
active[1] proctype proc_port_in3_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
x6_d1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 3) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out3_0?jeton;
          do
          ::
x3_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x6_d1_spin_data_out?mess;
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
x6_d1_spin_data_out?mess;
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
x6_d1_spin_data_out?mess;
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
x6_d1_spin_data_out?mess;
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
x7_d1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 2) % 4) == 3) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (1)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out3_0?jeton;
          do
          ::
x3_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out3_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x7_d1_spin_data_out?mess;
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
x7_d1_spin_data_out?mess;
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
x7_d1_spin_data_out?mess;
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
x7_d1_spin_data_out?mess;
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
x0_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out4_0?jeton;
          do
          ::
x4_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x0_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out4_1?jeton; 
          do
          ::
x4_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_1!jeton; goto SUITE_DOWN;} 
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
        :: (sortie_up24 == 0) ->
          atomic {
            sortie_up24 = 1;
            poss_out4_2?jeton;
          }
          do
          ::
x4_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_2!jeton; sortie_up24 = 0; goto SUITE_UP;}
            :: else ->
x0_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up34 == 0) ->
          atomic {
            sortie_up34 = 1;
            poss_out4_3?jeton;
          }
          do
          ::
x4_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out4_3!jeton; sortie_up34 = 0; goto SUITE_UP;}
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
active[1] proctype proc_port_in4_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
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
          poss_out4_0?jeton;
          do
          ::
x4_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x1_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out4_1?jeton; 
          do
          ::
x4_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_1!jeton; goto SUITE_DOWN;} 
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
        :: (sortie_up24 == 0) ->
          atomic {
            sortie_up24 = 1;
            poss_out4_2?jeton;
          }
          do
          ::
x4_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_2!jeton; sortie_up24 = 0; goto SUITE_UP;}
            :: else ->
x1_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up34 == 0) ->
          atomic {
            sortie_up34 = 1;
            poss_out4_3?jeton;
          }
          do
          ::
x4_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out4_3!jeton; sortie_up34 = 0; goto SUITE_UP;}
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
active[1] proctype proc_port_in4_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
x8_d0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out4_0?jeton;
          do
          ::
x4_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x8_d0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out4_1?jeton; 
          do
          ::
x4_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x8_d0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up24 == 0) ->
          atomic {
            sortie_up24 = 1;
            poss_out4_2?jeton;
          }
          do
          ::
x4_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_2!jeton; sortie_up24 = 0; goto SUITE_UP;}
            :: else ->
x8_d0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up34 == 0) ->
          atomic {
            sortie_up34 = 1;
            poss_out4_3?jeton;
          }
          do
          ::
x4_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out4_3!jeton; sortie_up34 = 0; goto SUITE_UP;}
            :: else -> 
x8_d0_spin_data_out?mess;
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
x10_d0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out4_0?jeton;
          do
          ::
x4_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x10_d0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out4_1?jeton; 
          do
          ::
x4_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x10_d0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up24 == 0) ->
          atomic {
            sortie_up24 = 1;
            poss_out4_2?jeton;
          }
          do
          ::
x4_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out4_2!jeton; sortie_up24 = 0; goto SUITE_UP;}
            :: else ->
x10_d0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up34 == 0) ->
          atomic {
            sortie_up34 = 1;
            poss_out4_3?jeton;
          }
          do
          ::
x4_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out4_3!jeton; sortie_up34 = 0; goto SUITE_UP;}
            :: else -> 
x10_d0_spin_data_out?mess;
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
x0_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out5_0?jeton;
          do
          ::
x5_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x0_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out5_1?jeton; 
          do
          ::
x5_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_1!jeton; goto SUITE_DOWN;} 
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
        :: (sortie_up25 == 0) ->
          atomic {
            sortie_up25 = 1;
            poss_out5_2?jeton;
          }
          do
          ::
x5_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_2!jeton; sortie_up25 = 0; goto SUITE_UP;}
            :: else ->
x0_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up35 == 0) ->
          atomic {
            sortie_up35 = 1;
            poss_out5_3?jeton;
          }
          do
          ::
x5_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out5_3!jeton; sortie_up35 = 0; goto SUITE_UP;}
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
active[1] proctype proc_port_in5_1()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
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
          poss_out5_0?jeton;
          do
          ::
x5_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x1_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out5_1?jeton; 
          do
          ::
x5_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_1!jeton; goto SUITE_DOWN;} 
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
        :: (sortie_up25 == 0) ->
          atomic {
            sortie_up25 = 1;
            poss_out5_2?jeton;
          }
          do
          ::
x5_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_2!jeton; sortie_up25 = 0; goto SUITE_UP;}
            :: else ->
x1_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up35 == 0) ->
          atomic {
            sortie_up35 = 1;
            poss_out5_3?jeton;
          }
          do
          ::
x5_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out5_3!jeton; sortie_up35 = 0; goto SUITE_UP;}
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
active[1] proctype proc_port_in5_2()
{
MOT mess;
  bit reserve;

  short sortie_down;
(active_all==1);
  bit jeton = 1;

  do
::
x9_d0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out5_0?jeton;
          do
          ::
x5_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x9_d0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out5_1?jeton; 
          do
          ::
x5_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x9_d0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up25 == 0) ->
          atomic {
            sortie_up25 = 1;
            poss_out5_2?jeton;
          }
          do
          ::
x5_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_2!jeton; sortie_up25 = 0; goto SUITE_UP;}
            :: else ->
x9_d0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up35 == 0) ->
          atomic {
            sortie_up35 = 1;
            poss_out5_3?jeton;
          }
          do
          ::
x5_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out5_3!jeton; sortie_up35 = 0; goto SUITE_UP;}
            :: else -> 
x9_d0_spin_data_out?mess;
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
x11_d0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out5_0?jeton;
          do
          ::
x5_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x11_d0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out5_1?jeton; 
          do
          ::
x5_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x11_d0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up25 == 0) ->
          atomic {
            sortie_up25 = 1;
            poss_out5_2?jeton;
          }
          do
          ::
x5_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out5_2!jeton; sortie_up25 = 0; goto SUITE_UP;}
            :: else ->
x11_d0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up35 == 0) ->
          atomic {
            sortie_up35 = 1;
            poss_out5_3?jeton;
          }
          do
          ::
x5_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out5_3!jeton; sortie_up35 = 0; goto SUITE_UP;}
            :: else -> 
x11_d0_spin_data_out?mess;
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
x2_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out6_0?jeton;
          do
          ::
x6_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x2_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out6_1?jeton; 
          do
          ::
x6_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x2_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up26 == 0) ->
          atomic {
            sortie_up26 = 1;
            poss_out6_2?jeton;
          }
          do
          ::
x6_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_2!jeton; sortie_up26 = 0; goto SUITE_UP;}
            :: else ->
x2_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up36 == 0) ->
          atomic {
            sortie_up36 = 1;
            poss_out6_3?jeton;
          }
          do
          ::
x6_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out6_3!jeton; sortie_up36 = 0; goto SUITE_UP;}
            :: else -> 
x2_u0_spin_data_out?mess;
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
x3_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out6_0?jeton;
          do
          ::
x6_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x3_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out6_1?jeton; 
          do
          ::
x6_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x3_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up26 == 0) ->
          atomic {
            sortie_up26 = 1;
            poss_out6_2?jeton;
          }
          do
          ::
x6_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_2!jeton; sortie_up26 = 0; goto SUITE_UP;}
            :: else ->
x3_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up36 == 0) ->
          atomic {
            sortie_up36 = 1;
            poss_out6_3?jeton;
          }
          do
          ::
x6_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out6_3!jeton; sortie_up36 = 0; goto SUITE_UP;}
            :: else -> 
x3_u0_spin_data_out?mess;
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
x8_d1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out6_0?jeton;
          do
          ::
x6_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x8_d1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out6_1?jeton; 
          do
          ::
x6_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x8_d1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up26 == 0) ->
          atomic {
            sortie_up26 = 1;
            poss_out6_2?jeton;
          }
          do
          ::
x6_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_2!jeton; sortie_up26 = 0; goto SUITE_UP;}
            :: else ->
x8_d1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up36 == 0) ->
          atomic {
            sortie_up36 = 1;
            poss_out6_3?jeton;
          }
          do
          ::
x6_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out6_3!jeton; sortie_up36 = 0; goto SUITE_UP;}
            :: else -> 
x8_d1_spin_data_out?mess;
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
x10_d1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out6_0?jeton;
          do
          ::
x6_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x10_d1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out6_1?jeton; 
          do
          ::
x6_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x10_d1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up26 == 0) ->
          atomic {
            sortie_up26 = 1;
            poss_out6_2?jeton;
          }
          do
          ::
x6_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out6_2!jeton; sortie_up26 = 0; goto SUITE_UP;}
            :: else ->
x10_d1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up36 == 0) ->
          atomic {
            sortie_up36 = 1;
            poss_out6_3?jeton;
          }
          do
          ::
x6_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out6_3!jeton; sortie_up36 = 0; goto SUITE_UP;}
            :: else -> 
x10_d1_spin_data_out?mess;
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
x2_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out7_0?jeton;
          do
          ::
x7_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x2_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out7_1?jeton; 
          do
          ::
x7_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x2_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up27 == 0) ->
          atomic {
            sortie_up27 = 1;
            poss_out7_2?jeton;
          }
          do
          ::
x7_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_2!jeton; sortie_up27 = 0; goto SUITE_UP;}
            :: else ->
x2_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up37 == 0) ->
          atomic {
            sortie_up37 = 1;
            poss_out7_3?jeton;
          }
          do
          ::
x7_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out7_3!jeton; sortie_up37 = 0; goto SUITE_UP;}
            :: else -> 
x2_u1_spin_data_out?mess;
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
x3_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out7_0?jeton;
          do
          ::
x7_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x3_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out7_1?jeton; 
          do
          ::
x7_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x3_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up27 == 0) ->
          atomic {
            sortie_up27 = 1;
            poss_out7_2?jeton;
          }
          do
          ::
x7_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_2!jeton; sortie_up27 = 0; goto SUITE_UP;}
            :: else ->
x3_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up37 == 0) ->
          atomic {
            sortie_up37 = 1;
            poss_out7_3?jeton;
          }
          do
          ::
x7_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out7_3!jeton; sortie_up37 = 0; goto SUITE_UP;}
            :: else -> 
x3_u1_spin_data_out?mess;
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
x9_d1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out7_0?jeton;
          do
          ::
x7_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x9_d1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out7_1?jeton; 
          do
          ::
x7_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x9_d1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up27 == 0) ->
          atomic {
            sortie_up27 = 1;
            poss_out7_2?jeton;
          }
          do
          ::
x7_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_2!jeton; sortie_up27 = 0; goto SUITE_UP;}
            :: else ->
x9_d1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up37 == 0) ->
          atomic {
            sortie_up37 = 1;
            poss_out7_3?jeton;
          }
          do
          ::
x7_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out7_3!jeton; sortie_up37 = 0; goto SUITE_UP;}
            :: else -> 
x9_d1_spin_data_out?mess;
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
x11_d1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 4) % 4) == 1) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (2)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out7_0?jeton;
          do
          ::
x7_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x11_d1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out7_1?jeton; 
          do
          ::
x7_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x11_d1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up27 == 0) ->
          atomic {
            sortie_up27 = 1;
            poss_out7_2?jeton;
          }
          do
          ::
x7_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out7_2!jeton; sortie_up27 = 0; goto SUITE_UP;}
            :: else ->
x11_d1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up37 == 0) ->
          atomic {
            sortie_up37 = 1;
            poss_out7_3?jeton;
          }
          do
          ::
x7_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out7_3!jeton; sortie_up37 = 0; goto SUITE_UP;}
            :: else -> 
x11_d1_spin_data_out?mess;
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
x4_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out8_0?jeton;
          do
          ::
x8_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x4_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out8_1?jeton; 
          do
          ::
x8_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x4_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up28 == 0) ->
          atomic {
            sortie_up28 = 1;
            poss_out8_2?jeton;
          }
          do
          ::
x8_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_2!jeton; sortie_up28 = 0; goto SUITE_UP;}
            :: else ->
x4_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up38 == 0) ->
          atomic {
            sortie_up38 = 1;
            poss_out8_3?jeton;
          }
          do
          ::
x8_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out8_3!jeton; sortie_up38 = 0; goto SUITE_UP;}
            :: else -> 
x4_u0_spin_data_out?mess;
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
x6_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out8_0?jeton;
          do
          ::
x8_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x6_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out8_1?jeton; 
          do
          ::
x8_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x6_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up28 == 0) ->
          atomic {
            sortie_up28 = 1;
            poss_out8_2?jeton;
          }
          do
          ::
x8_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_2!jeton; sortie_up28 = 0; goto SUITE_UP;}
            :: else ->
x6_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up38 == 0) ->
          atomic {
            sortie_up38 = 1;
            poss_out8_3?jeton;
          }
          do
          ::
x8_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out8_3!jeton; sortie_up38 = 0; goto SUITE_UP;}
            :: else -> 
x6_u0_spin_data_out?mess;
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
x8_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out8_0?jeton;
          do
          ::
x8_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x8_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out8_1?jeton; 
          do
          ::
x8_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x8_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up28 == 0) ->
          atomic {
            sortie_up28 = 1;
            poss_out8_2?jeton;
          }
          do
          ::
x8_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_2!jeton; sortie_up28 = 0; goto SUITE_UP;}
            :: else ->
x8_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up38 == 0) ->
          atomic {
            sortie_up38 = 1;
            poss_out8_3?jeton;
          }
          do
          ::
x8_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out8_3!jeton; sortie_up38 = 0; goto SUITE_UP;}
            :: else -> 
x8_u1_spin_data_out?mess;
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
x8_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out8_0?jeton;
          do
          ::
x8_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x8_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out8_1?jeton; 
          do
          ::
x8_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x8_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up28 == 0) ->
          atomic {
            sortie_up28 = 1;
            poss_out8_2?jeton;
          }
          do
          ::
x8_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out8_2!jeton; sortie_up28 = 0; goto SUITE_UP;}
            :: else ->
x8_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up38 == 0) ->
          atomic {
            sortie_up38 = 1;
            poss_out8_3?jeton;
          }
          do
          ::
x8_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out8_3!jeton; sortie_up38 = 0; goto SUITE_UP;}
            :: else -> 
x8_u0_spin_data_out?mess;
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
x5_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out9_0?jeton;
          do
          ::
x9_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x5_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out9_1?jeton; 
          do
          ::
x9_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x5_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up29 == 0) ->
          atomic {
            sortie_up29 = 1;
            poss_out9_2?jeton;
          }
          do
          ::
x9_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_2!jeton; sortie_up29 = 0; goto SUITE_UP;}
            :: else ->
x5_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up39 == 0) ->
          atomic {
            sortie_up39 = 1;
            poss_out9_3?jeton;
          }
          do
          ::
x9_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out9_3!jeton; sortie_up39 = 0; goto SUITE_UP;}
            :: else -> 
x5_u0_spin_data_out?mess;
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
x7_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out9_0?jeton;
          do
          ::
x9_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x7_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out9_1?jeton; 
          do
          ::
x9_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x7_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up29 == 0) ->
          atomic {
            sortie_up29 = 1;
            poss_out9_2?jeton;
          }
          do
          ::
x9_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_2!jeton; sortie_up29 = 0; goto SUITE_UP;}
            :: else ->
x7_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up39 == 0) ->
          atomic {
            sortie_up39 = 1;
            poss_out9_3?jeton;
          }
          do
          ::
x9_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out9_3!jeton; sortie_up39 = 0; goto SUITE_UP;}
            :: else -> 
x7_u0_spin_data_out?mess;
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
x9_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out9_0?jeton;
          do
          ::
x9_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x9_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out9_1?jeton; 
          do
          ::
x9_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x9_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up29 == 0) ->
          atomic {
            sortie_up29 = 1;
            poss_out9_2?jeton;
          }
          do
          ::
x9_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_2!jeton; sortie_up29 = 0; goto SUITE_UP;}
            :: else ->
x9_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up39 == 0) ->
          atomic {
            sortie_up39 = 1;
            poss_out9_3?jeton;
          }
          do
          ::
x9_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out9_3!jeton; sortie_up39 = 0; goto SUITE_UP;}
            :: else -> 
x9_u1_spin_data_out?mess;
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
x9_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out9_0?jeton;
          do
          ::
x9_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x9_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out9_1?jeton; 
          do
          ::
x9_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x9_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up29 == 0) ->
          atomic {
            sortie_up29 = 1;
            poss_out9_2?jeton;
          }
          do
          ::
x9_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out9_2!jeton; sortie_up29 = 0; goto SUITE_UP;}
            :: else ->
x9_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up39 == 0) ->
          atomic {
            sortie_up39 = 1;
            poss_out9_3?jeton;
          }
          do
          ::
x9_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out9_3!jeton; sortie_up39 = 0; goto SUITE_UP;}
            :: else -> 
x9_u0_spin_data_out?mess;
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
x4_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out10_0?jeton;
          do
          ::
x10_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x4_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out10_1?jeton; 
          do
          ::
x10_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x4_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up210 == 0) ->
          atomic {
            sortie_up210 = 1;
            poss_out10_2?jeton;
          }
          do
          ::
x10_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_2!jeton; sortie_up210 = 0; goto SUITE_UP;}
            :: else ->
x4_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up310 == 0) ->
          atomic {
            sortie_up310 = 1;
            poss_out10_3?jeton;
          }
          do
          ::
x10_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out10_3!jeton; sortie_up310 = 0; goto SUITE_UP;}
            :: else -> 
x4_u1_spin_data_out?mess;
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
x6_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out10_0?jeton;
          do
          ::
x10_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x6_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out10_1?jeton; 
          do
          ::
x10_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x6_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up210 == 0) ->
          atomic {
            sortie_up210 = 1;
            poss_out10_2?jeton;
          }
          do
          ::
x10_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_2!jeton; sortie_up210 = 0; goto SUITE_UP;}
            :: else ->
x6_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up310 == 0) ->
          atomic {
            sortie_up310 = 1;
            poss_out10_3?jeton;
          }
          do
          ::
x10_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out10_3!jeton; sortie_up310 = 0; goto SUITE_UP;}
            :: else -> 
x6_u1_spin_data_out?mess;
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
x10_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out10_0?jeton;
          do
          ::
x10_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x10_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out10_1?jeton; 
          do
          ::
x10_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x10_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up210 == 0) ->
          atomic {
            sortie_up210 = 1;
            poss_out10_2?jeton;
          }
          do
          ::
x10_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_2!jeton; sortie_up210 = 0; goto SUITE_UP;}
            :: else ->
x10_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up310 == 0) ->
          atomic {
            sortie_up310 = 1;
            poss_out10_3?jeton;
          }
          do
          ::
x10_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out10_3!jeton; sortie_up310 = 0; goto SUITE_UP;}
            :: else -> 
x10_u1_spin_data_out?mess;
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
x10_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out10_0?jeton;
          do
          ::
x10_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x10_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out10_1?jeton; 
          do
          ::
x10_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x10_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up210 == 0) ->
          atomic {
            sortie_up210 = 1;
            poss_out10_2?jeton;
          }
          do
          ::
x10_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out10_2!jeton; sortie_up210 = 0; goto SUITE_UP;}
            :: else ->
x10_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up310 == 0) ->
          atomic {
            sortie_up310 = 1;
            poss_out10_3?jeton;
          }
          do
          ::
x10_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out10_3!jeton; sortie_up310 = 0; goto SUITE_UP;}
            :: else -> 
x10_u0_spin_data_out?mess;
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
x5_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out11_0?jeton;
          do
          ::
x11_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x5_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out11_1?jeton; 
          do
          ::
x11_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x5_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up211 == 0) ->
          atomic {
            sortie_up211 = 1;
            poss_out11_2?jeton;
          }
          do
          ::
x11_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_2!jeton; sortie_up211 = 0; goto SUITE_UP;}
            :: else ->
x5_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up311 == 0) ->
          atomic {
            sortie_up311 = 1;
            poss_out11_3?jeton;
          }
          do
          ::
x11_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out11_3!jeton; sortie_up311 = 0; goto SUITE_UP;}
            :: else -> 
x5_u1_spin_data_out?mess;
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
x7_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out11_0?jeton;
          do
          ::
x11_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x7_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out11_1?jeton; 
          do
          ::
x11_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x7_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up211 == 0) ->
          atomic {
            sortie_up211 = 1;
            poss_out11_2?jeton;
          }
          do
          ::
x11_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_2!jeton; sortie_up211 = 0; goto SUITE_UP;}
            :: else ->
x7_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up311 == 0) ->
          atomic {
            sortie_up311 = 1;
            poss_out11_3?jeton;
          }
          do
          ::
x11_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out11_3!jeton; sortie_up311 = 0; goto SUITE_UP;}
            :: else -> 
x7_u1_spin_data_out?mess;
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
x11_u1_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out11_0?jeton;
          do
          ::
x11_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x11_u1_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out11_1?jeton; 
          do
          ::
x11_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x11_u1_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up211 == 0) ->
          atomic {
            sortie_up211 = 1;
            poss_out11_2?jeton;
          }
          do
          ::
x11_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_2!jeton; sortie_up211 = 0; goto SUITE_UP;}
            :: else ->
x11_u1_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up311 == 0) ->
          atomic {
            sortie_up311 = 1;
            poss_out11_3?jeton;
          }
          do
          ::
x11_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out11_3!jeton; sortie_up311 = 0; goto SUITE_UP;}
            :: else -> 
x11_u1_spin_data_out?mess;
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
x11_u0_spin_data_out?mess;
    
    
    if 
    :: (((mess.addest / 8) % 4) == 0) -> 
    /* Traitement des ports DOWN. */
        sortie_down = (mess.addest / (4)) % 2;
     
        if 
        :: (sortie_down == 0) ->
          poss_out11_0?jeton;
          do
          ::
x11_d0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_0!jeton; goto SUITE_DOWN;}
            :: else -> 
x11_u0_spin_data_out?mess;
            fi;
           od;
        :: (sortie_down == 1) ->
          poss_out11_1?jeton; 
          do
          ::
x11_d1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_1!jeton; goto SUITE_DOWN;} 
            :: else -> 
x11_u0_spin_data_out?mess;
            fi;
           od;
        fi;
        
        SUITE_DOWN :
skip;
        
    :: else ->
    /* Traitement des ports UP. */
        if 
        :: (sortie_up211 == 0) ->
          atomic {
            sortie_up211 = 1;
            poss_out11_2?jeton;
          }
          do
          ::
x11_u0_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2) -> atomic{poss_out11_2!jeton; sortie_up211 = 0; goto SUITE_UP;}
            :: else ->
x11_u0_spin_data_out?mess;
            fi;
          od;
        :: (sortie_up311 == 0) ->
          atomic {
            sortie_up311 = 1;
            poss_out11_3?jeton;
          }
          do
          ::
x11_u1_spin_data_out!mess;
            
            if 
            :: (mess.tag == 2 )-> atomic{poss_out11_3!jeton; sortie_up311 = 0; goto SUITE_UP;}
            :: else -> 
x11_u0_spin_data_out?mess;
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
    poss_out4_0!1;
    poss_out4_1!1;
    poss_out4_2!1;
    poss_out4_3!1;
    
    poss_out5_0!1;
    poss_out5_1!1;
    poss_out5_2!1;
    poss_out5_3!1;

    poss_out6_0!1;
    poss_out6_1!1;
    poss_out6_2!1;
    poss_out6_3!1;
    
    poss_out7_0!1;
    poss_out7_1!1;
    poss_out7_2!1;
    poss_out7_3!1;

    poss_out8_0!1;
    poss_out8_1!1;
    poss_out8_2!1;
    poss_out8_3!1;
    
    poss_out9_0!1;
    poss_out9_1!1;
    poss_out9_2!1;
    poss_out9_3!1;

    poss_out10_0!1;
    poss_out10_1!1;
    poss_out10_2!1;
    poss_out10_3!1;
    
    poss_out11_0!1;
    poss_out11_1!1;
    poss_out11_2!1;
    poss_out11_3!1;

    active_all=1;

   } /* Fin du atomic */
} /* Fin de la fonction init */

        