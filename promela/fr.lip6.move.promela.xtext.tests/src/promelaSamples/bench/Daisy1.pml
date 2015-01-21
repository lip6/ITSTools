
//#ifndef DAISY_H
//#define DAISY_H

/* On a que deux type de command :
 *      - avec donnée (=> 1):
 *         * 1 bit pour la direction (0 => READ / READWRITE; 1 => WRITE)
 *         * 3 bit pour la longueur de la transaction
 *      - sans donnée (=> 0);
 *         * le bit de direction à 1 signifie LOCK/UNLOCK
 */
 
#define SD        0   /* sans donnée */
#define AD        1   /* avec donnée */

#define READ      0
#define WRITE     1
#define LOCK      1

#define WM        0
#define WE        1

typedef commande_t {
  bit dest;         /* id du wrapper esclave cible */
  bit type;         /* definie le type de requete */
  bit dir;          /* indique la direction de la transaction */
  bit lg0;          /* indique la longueur de la transaction */
  bit lg1;
  bit lg2;
};

typedef req_t {
  commande_t cde;    /* la commande */
  bit src;           /* id du wrapper maitre source */
  bool answer;
  bool free;
};
 
typedef data_t {
  bit type_dest;        /* type de wrapper (WM / WE) */
  bit id_dest;        /* bit de differenciation */
  bit lg0;          /* numero du mot dans la transaction */
  bit lg1;
  bit lg2;
  bool free;
};

//#endif /* DAISY_H */

int nb_req = 0;
/* Pipe 1 */
chan P1_data_out = [1] of { data_t };
chan P1_req_out = [1] of { req_t };

/* Wrapper Maitre 1 */
chan WM1_data_out = [1] of { data_t };
chan WM1_req_out = [1] of { req_t };

chan WM1_from_task = [1] of {commande_t};
chan WM1_to_task = [1] of {commande_t};

/* Wrapper Esclave 1 */
chan WS1_data_out = [1] of { data_t };
chan WS1_req_out = [1] of { req_t };

chan WS1_from_task = [1] of {commande_t};
chan WS1_to_task = [1] of {commande_t};


active[1] proctype Pipe0()
{
  data_t data;
  req_t req;

  atomic{
    data.free = true;
    req.free = true;
  
    P1_data_out!data; P1_req_out!req; 
  }

progress: do 
  :: atomic { WS1_data_out?data; WS1_req_out?req; }
     atomic { P1_data_out!data; P1_req_out!req; }
  od;
}

active[1] proctype WrapperMaitre0()
{
/*  xr data_in; xs data_out;
  xr req_in; xs req_out;
  xs to_task;*/

  data_t data;
  req_t req;

  bit type;
  bit dir;
  bit dest;
  unsigned lg : 3;
  unsigned cpt : 3;


debut: do
  :: (empty(WM1_from_task)) -> /* pas de requette sur from_task */
       atomic { P1_data_out?data; P1_req_out?req; }
       atomic { WM1_data_out!data; WM1_req_out!req; }
  :: (nempty(WM1_from_task)) -> /* requette sur from_task */
debut_req: do
       :: atomic { P1_data_out?data; P1_req_out?req; }
	  if 
	  :: (req.free == true) -> break; /* requette libre */
	  :: (req.free != true) -> atomic { WM1_data_out!data; WM1_req_out!req; }
	  fi;
       od;
       
       /* création de la requette */
       atomic{
       WM1_from_task?req.cde;
       req.answer = false;
       req.free = false;
       req.src = 0;
       /* on sauvegarde qlqs infos */
       type = req.cde.type;
       dir = req.cde.dir;
       lg = ((req.cde.lg2 * 4) + (req.cde.lg1 * 2) + req.cde.lg0);
       dest = req.cde.dest;
       /* on envoie la requette sur le bus */
       WM1_data_out!data; WM1_req_out!req;
       }

       do /* on attend la réponse sur REQ */
       :: atomic {P1_data_out?data; P1_req_out?req; }
	  
	  if 
	  :: ((!req.free) && req.answer && (req.src == 0) ) -> 
	       break;
	  :: else ->atomic { WM1_data_out!data; WM1_req_out!req; }
	  fi;
       od;

       /* on libère le slot */
       atomic { 
         req.free = true;
	 WM1_data_out!data; WM1_req_out!req;
       }

       if 
       :: (type == SD) -> goto debut;
       :: else ->
         if
         :: (dir == READ) -> /* si c'est un READ ou READWRITE */
	      cpt = 0;
	      do
	      :: ((cpt < lg) || ((lg == 0) && (cpt == 0))) -> 
	         atomic { P1_data_out?data; P1_req_out?req; }
		 if
		 :: (!data.free &&
		    ((data.lg0 + (data.lg1*2) + (data.lg2*4)) == (cpt+1)) &&
		    (data.type_dest == WM) && (data.id_dest) == 0) ->
	       	      atomic {
	                WM1_to_task!req.cde;
		        cpt++;
		        data.free = true;
		      }
		 :: else -> skip;
		 fi;
		 atomic { WM1_data_out!data; WM1_req_out!req; }
	      :: ((cpt >= lg) && !((lg == 0) && (cpt == 0))) -> break;
	      od;
	 :: else -> skip;
         fi;

         if
	 :: ((dir == WRITE) || (lg == 0)) -> /* si c'est un WRITE ou READWRITE */
	      cpt = 0;
	      do
	      :: ((cpt < lg) || (lg == 0 && cpt == 0)) ->
		   if
		   :: (empty(WM1_from_task)) -> 
		        atomic { P1_data_out?data; P1_req_out?req; }
			atomic { WM1_data_out!data; WM1_req_out!req; }
		   :: (nempty(WM1_from_task)) ->
		        do
			:: atomic { P1_data_out?data; }
			   if
			   :: (data.free) ->
			        atomic {
				  WM1_from_task?req.cde;
				  data.free = false;
				  cpt++;
				  data.lg0 = cpt;
				  data.lg1 = cpt / 2;
				  data.lg2 = cpt / 4;
				  data.type_dest = WE;
				  data.id_dest = dest;
				}
				P1_req_out?req; 
				atomic { WM1_data_out!data; WM1_req_out!req; }
				break;
			   :: else -> 
			        P1_req_out?req; 
				atomic { WM1_data_out!data; WM1_req_out!req; }
		           fi;
		        od;
	           fi;
	      :: ((cpt >= lg) && !(lg == 0 && cpt == 0)) -> break;
	      od;
         :: else -> skip;
         fi;
      fi;
fin_req: skip;
  od;
}
active [1] proctype WrapperEsclave0()
{
/*  xr data_in; xs data_out;
  xr req_in; xs req_out;
  xs to_task;*/

  data_t data;
  req_t req;
  
  bit locked;
  bit src;
  bit type;
  bit dir;
  unsigned lg : 3;
  unsigned cpt : 3;

//we_pid = _pid;

debut: do
  :: atomic { WM1_data_out?data; WM1_req_out?req; }
     if
     :: ((!req.free) && (!req.answer) && (req.cde.dest == 0) &&
	(!locked || req.src == src)) ->
          /* on sauvegarde qlqs infos */
          atomic {
	  lg = req.cde.lg0 + (req.cde.lg1 * 2) + (req.cde.lg2 * 4);
          type = req.cde.type;
	  dir = req.cde.dir;
	  src = req.src;
          
	  /* on envoie l'acquitement sur le bus REQ */
	  req.answer = true;
	  WS1_data_out!data; WS1_req_out!req; 
	  }

	  if 
	  :: ((type == SD) && (dir == LOCK)) ->
	     atomic {
	       locked = ! locked;
	       #ifndef NOCPT
	         nb_req--;
	       #endif
	       goto debut;
	     };
	  :: else -> skip;
	  fi;
	  
	  WS1_to_task!req.cde;

	  if
	  :: (type == SD) -> goto debut;
	  :: else -> skip;
	  fi;	  

          if
          :: (dir == READ) -> /* si c'est un READ ou READWRITE */
	     cpt = 0;
	     do /* tant qu'il reste des données à envoyer */
	     :: ((cpt < lg) || (lg == 0 && cpt == 0)) -> 
	          if
	          :: (empty(WS1_from_task)) -> 
		       atomic { WM1_data_out?data; WM1_req_out?req; }
		       atomic { WS1_data_out!data; WS1_req_out!req; }
	          :: (nempty(WS1_from_task)) ->
		       do /* on attend que le bus DATA soit libre */
		       :: atomic { WM1_data_out?data;}
		          if
		          :: (data.free) ->
			       atomic {
			         WS1_from_task?req.cde;
				 cpt++;
				 data.free = false;
				 data.lg0 = cpt;
				 data.lg1 = cpt / 2;
				 data.lg2 = cpt / 4;
				 data.type_dest = WM;
				 data.id_dest = src;
			       }
			       WM1_req_out?req;
			       atomic { WS1_data_out!data; WS1_req_out!req; }
			       break;
		          :: else -> 
			       WM1_req_out?req;
			       atomic { WS1_data_out!data; WS1_req_out!req; }
		          fi;
		       od;
	          fi;
	     :: ((cpt >= lg) && !(lg == 0 && cpt == 0)) -> break;
	     od;
           :: else -> skip;
           fi; /* fin READ */

           if /* si c'est un WRITE ou READWRITE */
           :: ((dir == WRITE) || (lg == 0)) -> 
	      cpt = 0;
	      do /* tant qu'il y a des données à recevoir */
	      :: ((cpt < lg) || (lg == 0 && cpt == 0)) -> 
	           atomic { WM1_data_out?data; WM1_req_out?req; }
	           if
	           :: (!data.free && 
		      (data.type_dest == WE) && (data.id_dest == 0) &&
		      ((data.lg0 + (data.lg1*2) + (data.lg2*4)) == cpt+1)) ->
		        atomic {
			WS1_to_task!req.cde;
		        cpt++;
		        data.free = true;
			}
	           :: else -> skip;
	           fi;
		   atomic { WS1_data_out!data; WS1_req_out!req; }
	      :: ((cpt >= lg) && !(lg == 0 && cpt == 0)) -> break;
	      od;
           :: else -> skip;
           fi; /* fin WRITE ou READWRITE */
     :: else -> atomic { WS1_data_out!data; WS1_req_out!req; }
     fi;
  od;
}
        active[1] proctype TacheMaitre0()
{
//  xr to_task;

  commande_t request;
  unsigned cpt : 3;

debut:  do
     :: (nb_req > 0) -> 
terminaison: (nb_req == 0);
     :: /* choix de la command */
	atomic {
	/* choix de la destination */
	request.dest = 0; /* Tache/Wrapper Esclave 0 */

        if /* choix du type de commande */
	:: request.type = SD; /* sans donnee */
	:: request.type = AD; /* avec donnee */
	fi;

	if  /* choix de la longeur */
	:: ((request.type == AD)) ->
	   if
	   :: request.lg0 = 0;
	   :: request.lg0 = 1;
	   fi;
	   if
	   :: request.lg1 = 0;
	   :: request.lg1 = 1;
	   fi;
	   if
	   :: request.lg2 = 0;
	   :: request.lg2 = 1;
	   fi;
	:: else -> 
	     request.lg0 = 0;
	     request.lg1 = 0;
	     request.lg2 = 0;
        fi;

	if /* choix de la direction */
	:: ((request.lg0 == 0) && (request.lg1 == 0) && (request.lg2 == 0) &&
	   (request.type == AD)) ->
	     request.dir = 0;
	:: else -> if
	   :: request.dir = READ;   /* lecture */
	   :: request.dir = WRITE;  /* ecriture ou LOCK/UNLOCK si SD */
	   fi;
	fi;
	} /* fin  d_step */
	/* envoie de la requette */
progress:atomic {
	  WM1_from_task!request;
	  nb_req++;
	}	
	
	if 
	:: (request.type == SD) -> goto debut;
	:: else -> cpt = (request.lg2 * 4) + (request.lg1 * 2) + request.lg0;
	fi;

	if
	:: ((request.dir == READ) && (cpt != 0))->
	   do
	   :: (cpt > 0)-> 
	        atomic {
		  WM1_to_task?request;
		  cpt--;
		}
	   :: (cpt == 0) ->
	        atomic {
		  nb_req--;
		  break;
		}
	   od;
	:: (request.dir == WRITE) ->
	   do
	   :: (cpt > 0) ->
	        atomic {
		  WM1_from_task!request;
		  cpt--;
		}
	   :: (cpt == 0) -> break;
	   od;
	:: ((request.dir == READ) && (cpt == 0))->
	   WM1_to_task?request;
	   WM1_from_task!request;
	fi;
    od;
}

active [1] proctype TacheEsclave0()
{
//  xr to_task;

  commande_t cde;

  unsigned cpt : 3;

debut: do 
  :: (nempty(WS1_to_task)) ->
     atomic {
     WS1_to_task?cde;
     if 
     :: (cde.type == SD) ->       
	  #ifndef NOCPT
	    nb_req--;
	  #endif
	  goto debut;
     :: (cde.type == AD) -> cpt = (cde.lg2 * 4) + (cde.lg1 * 2) + cde.lg0;
     fi;
     
     }
     if
     ::( cde.dir == READ && (cpt != 0)) ->
	  do
	  :: (cpt > 0) ->
	       atomic {
	         cpt--;
	         WS1_from_task!cde;
	       }
	  :: (cpt == 0) -> break;
	  od;
     :: (cde.dir == WRITE) ->
	  do
	  :: (cpt > 0) ->
	       atomic {
	         WS1_to_task?cde;
	         cpt--;
	       }
	  :: (cpt == 0) -> 
	       atomic {
	         #ifndef NOCPT
		   nb_req--;
		 #endif
		 break;
	       }
	  od;
     :: (cde.dir == READ && (cpt == 0)) ->
     	  WS1_from_task!cde;
	  atomic {
	    WS1_to_task?cde;
	    #ifndef NOCPT
	      nb_req--;
	    #endif
	  }
     fi;
  od;
}
