#include <ltsmin/pins.h>
#include <ltsmin/ltsmin-standard.h>
#include <ltsmin/lts-type.h>
#include "model.h"
int state_length() {
  return 16 ;
}
#define true 1
#define false 0
int initial [16] ;
int* initial_state() {
  // Pm4
  initial [0] = 0;
  // Pback4
  initial [1] = 0;
  // Pout4
  initial [2] = 0;
  // P4
  initial [3] = 5;
  // Pm3
  initial [4] = 0;
  // Pback3
  initial [5] = 0;
  // Pout3
  initial [6] = 0;
  // P3
  initial [7] = 5;
  // Pm2
  initial [8] = 0;
  // Pback2
  initial [9] = 0;
  // Pout2
  initial [10] = 0;
  // P2
  initial [11] = 5;
  // Pm1
  initial [12] = 0;
  // Pout1
  initial [13] = 0;
  // Pback1
  initial [14] = 0;
  // P1
  initial [15] = 5;
  return initial;
}
int rm[16][16] = {
  {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
  {0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
  {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
  {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
  {0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1}
};
int* read_matrix(int row) {
  return rm[row];
}
int wm[16][16] = {
  {0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0},
  {0,0,1,1,1,0,0,1,1,0,0,1,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0},
  {0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0},
  {0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
  {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
  {0,0,0,0,0,0,1,1,0,0,1,1,1,0,0,1}
};
int* write_matrix(int row) {
  return wm[row];
}
int * guardsPerTrans [16] =  {
  ((int[]){1,0}),
  ((int[]){1,1}),
  ((int[]){1,2}),
  ((int[]){1,3}),
  ((int[]){1,4}),
  ((int[]){1,5}),
  ((int[]){1,6}),
  ((int[]){1,7}),
  ((int[]){1,8}),
  ((int[]){1,9}),
  ((int[]){1,10}),
  ((int[]){1,11}),
  ((int[]){1,12}),
  ((int[]){1,13}),
  ((int[]){1,14}),
  ((int[]){1,15})
};
int group_count() {
  return 16 ;
}
int label_count() {
  return 16 ;
}
char * labnames [16] = {
  "enabled0",
  "enabled1",
  "enabled2",
  "enabled3",
  "enabled4",
  "enabled5",
  "enabled6",
  "enabled7",
  "enabled8",
  "enabled9",
  "enabled10",
  "enabled11",
  "enabled12",
  "enabled13",
  "enabled14",
  "enabled15"
};
int lm[16][16] = {
  {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
  {0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
  {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
  {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
  {0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1}
};
int* label_matrix(int row) {
  return lm[row];
}
int mayDisable[16][16] = {
  {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}
};
int mayEnable[16][16] = {
  {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
  {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
  {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
  {0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
  {0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
  {0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
  {0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0},
  {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0}
};
const int* gal_get_label_nes_matrix(int g) {
 return mayEnable[g];
}
const int* gal_get_label_nds_matrix(int g) {
 return mayDisable[g];
}
int coenabled[16][16] = {
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
};
const int* coEnab_matrix(int g) {
 return coenabled[g];
}
int dna[16][16] = {
  {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
};
const int* dna_matrix(int g) {
 return dna[g];
}
typedef struct state {
  struct state * next;
  int state [16];
} state_t ;
static inline state_t * nextStatement1 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[12] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement2 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[12] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement3 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[13] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement0 (state_t * current) {
  current = nextStatement1(current);
  current = nextStatement2(current);
  current = nextStatement3(current);
  return current;
}
static inline state_t * nextStatement5 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[2] >= 1 && src[7] >= 1 && src[11] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement6 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[3] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement7 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[7] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement8 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[4] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement9 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[8] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement10 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[11] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement11 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[2] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement4 (state_t * current) {
  current = nextStatement5(current);
  current = nextStatement6(current);
  current = nextStatement7(current);
  current = nextStatement8(current);
  current = nextStatement9(current);
  current = nextStatement10(current);
  current = nextStatement11(current);
  return current;
}
static inline state_t * nextStatement13 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[14] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement14 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[14] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement15 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[12] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement12 (state_t * current) {
  current = nextStatement13(current);
  current = nextStatement14(current);
  current = nextStatement15(current);
  return current;
}
static inline state_t * nextStatement17 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[12] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement18 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[14] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement19 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[12] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement16 (state_t * current) {
  current = nextStatement17(current);
  current = nextStatement18(current);
  current = nextStatement19(current);
  return current;
}
static inline state_t * nextStatement21 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[8] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement22 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[8] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement23 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[10] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement20 (state_t * current) {
  current = nextStatement21(current);
  current = nextStatement22(current);
  current = nextStatement23(current);
  return current;
}
static inline state_t * nextStatement25 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[8] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement26 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[9] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement27 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[8] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement24 (state_t * current) {
  current = nextStatement25(current);
  current = nextStatement26(current);
  current = nextStatement27(current);
  return current;
}
static inline state_t * nextStatement29 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[9] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement30 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[9] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement31 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[8] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement28 (state_t * current) {
  current = nextStatement29(current);
  current = nextStatement30(current);
  current = nextStatement31(current);
  return current;
}
static inline state_t * nextStatement33 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[4] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement34 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[6] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement35 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[4] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement32 (state_t * current) {
  current = nextStatement33(current);
  current = nextStatement34(current);
  current = nextStatement35(current);
  return current;
}
static inline state_t * nextStatement37 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[5] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement38 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[4] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement39 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[5] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement36 (state_t * current) {
  current = nextStatement37(current);
  current = nextStatement38(current);
  current = nextStatement39(current);
  return current;
}
static inline state_t * nextStatement41 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[4] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement42 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[4] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement43 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[5] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement40 (state_t * current) {
  current = nextStatement41(current);
  current = nextStatement42(current);
  current = nextStatement43(current);
  return current;
}
static inline state_t * nextStatement45 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[0] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement46 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[2] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement47 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[0] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement44 (state_t * current) {
  current = nextStatement45(current);
  current = nextStatement46(current);
  current = nextStatement47(current);
  return current;
}
static inline state_t * nextStatement49 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[1] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement50 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[1] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement51 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[0] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement48 (state_t * current) {
  current = nextStatement49(current);
  current = nextStatement50(current);
  current = nextStatement51(current);
  return current;
}
static inline state_t * nextStatement53 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[0] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement54 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[1] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement55 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[0] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement52 (state_t * current) {
  current = nextStatement53(current);
  current = nextStatement54(current);
  current = nextStatement55(current);
  return current;
}
static inline state_t * nextStatement57 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[3] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement58 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[3] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement59 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[0] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement56 (state_t * current) {
  current = nextStatement57(current);
  current = nextStatement58(current);
  current = nextStatement59(current);
  return current;
}
static inline state_t * nextStatement61 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[13] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement62 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[15] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement63 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[13] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement60 (state_t * current) {
  current = nextStatement61(current);
  current = nextStatement62(current);
  current = nextStatement63(current);
  return current;
}
static inline state_t * nextStatement65 (state_t * current) {
  state_t ** pcur = & current;
  state_t * next = NULL;
  for (state_t * it = current; it ; it = next) {
    int * src = it->state;
    if (src[15] >= 1 && src[6] >= 1 && src[10] >= 1) {
      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  
    } else {
      next = it->next;
      free(it);
    }
  }
  *pcur=NULL;
  return current;
}
static inline state_t * nextStatement66 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[7] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement67 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[6] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement68 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[12] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement69 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[11] += 1    ;
  }
  return current;
}
static inline state_t * nextStatement70 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[15] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement71 (state_t * current) {
  for (state_t * it = current; it ; it = it->next) {
    int * dst = it->state;  // the next state values
    dst[10] -= 1    ;
  }
  return current;
}
static inline state_t * nextStatement64 (state_t * current) {
  current = nextStatement65(current);
  current = nextStatement66(current);
  current = nextStatement67(current);
  current = nextStatement68(current);
  current = nextStatement69(current);
  current = nextStatement70(current);
  current = nextStatement71(current);
  return current;
}
int next_state(void* model, int group, int *src, TransitionCB callback, void *arg) {
  state_t * cur = malloc(sizeof(state_t));

  memcpy(& cur->state, src,  sizeof(int)* 16);

  cur->next = NULL;

  // provide transition labels and group number of transition.
  int action[1];
  action[0] = group;
  transition_info_t transition_info = { action, group };
  switch (group) {
  case 0 : 
     cur = nextStatement0(cur);
     break;
  case 1 : 
     cur = nextStatement4(cur);
     break;
  case 2 : 
     cur = nextStatement12(cur);
     break;
  case 3 : 
     cur = nextStatement16(cur);
     break;
  case 4 : 
     cur = nextStatement20(cur);
     break;
  case 5 : 
     cur = nextStatement24(cur);
     break;
  case 6 : 
     cur = nextStatement28(cur);
     break;
  case 7 : 
     cur = nextStatement32(cur);
     break;
  case 8 : 
     cur = nextStatement36(cur);
     break;
  case 9 : 
     cur = nextStatement40(cur);
     break;
  case 10 : 
     cur = nextStatement44(cur);
     break;
  case 11 : 
     cur = nextStatement48(cur);
     break;
  case 12 : 
     cur = nextStatement52(cur);
     break;
  case 13 : 
     cur = nextStatement56(cur);
     break;
  case 14 : 
     cur = nextStatement60(cur);
     break;
  case 15 : 
     cur = nextStatement64(cur);
     break;
  default : return 0 ;
  } // end switch(group) 
  int nbsucc = 0;
  for (state_t * it=cur ; it ;  nbsucc++) {
     callback(arg, &transition_info, it->state, wm[group]);
     state_t * tmp = it->next; free(it); it=tmp;
  }
  return nbsucc; // return number of successors
}
int state_label(void* model, int label, int* src) {
  state_t * cur = malloc(sizeof(state_t));

  memcpy(& cur->state, src,  sizeof(int)* 16);

  cur->next = NULL;

  switch (label) {
  case 0 : 
     cur = nextStatement0(cur);
     break;
  case 1 : 
     cur = nextStatement4(cur);
     break;
  case 2 : 
     cur = nextStatement12(cur);
     break;
  case 3 : 
     cur = nextStatement16(cur);
     break;
  case 4 : 
     cur = nextStatement20(cur);
     break;
  case 5 : 
     cur = nextStatement24(cur);
     break;
  case 6 : 
     cur = nextStatement28(cur);
     break;
  case 7 : 
     cur = nextStatement32(cur);
     break;
  case 8 : 
     cur = nextStatement36(cur);
     break;
  case 9 : 
     cur = nextStatement40(cur);
     break;
  case 10 : 
     cur = nextStatement44(cur);
     break;
  case 11 : 
     cur = nextStatement48(cur);
     break;
  case 12 : 
     cur = nextStatement52(cur);
     break;
  case 13 : 
     cur = nextStatement56(cur);
     break;
  case 14 : 
     cur = nextStatement60(cur);
     break;
  case 15 : 
     cur = nextStatement64(cur);
     break;
  default : return 0 ;
  } // end switch(group) 
  int res = (cur != NULL);
  for (state_t * it=cur ; it ;  ) {
     state_t * tmp = it->next; free(it); it=tmp;
  }
  return res; // return label
}
int state_label_many(void* model, int * src, int * label, int guards_only) {
  (void)model;
  label[0] = 
    state_label(model,0,src) ;
  label[1] = 
    state_label(model,1,src) ;
  label[2] = 
    state_label(model,2,src) ;
  label[3] = 
    state_label(model,3,src) ;
  label[4] = 
    state_label(model,4,src) ;
  label[5] = 
    state_label(model,5,src) ;
  label[6] = 
    state_label(model,6,src) ;
  label[7] = 
    state_label(model,7,src) ;
  label[8] = 
    state_label(model,8,src) ;
  label[9] = 
    state_label(model,9,src) ;
  label[10] = 
    state_label(model,10,src) ;
  label[11] = 
    state_label(model,11,src) ;
  label[12] = 
    state_label(model,12,src) ;
  label[13] = 
    state_label(model,13,src) ;
  label[14] = 
    state_label(model,14,src) ;
  label[15] = 
    state_label(model,15,src) ;
  return 0; // return number of successors
}
void sl_group (model_t model, sl_group_enum_t group, int*src, int *label)
  {
  state_label_many (model, src, label, group == GB_SL_GUARDS);
  (void) group; // Both groups overlap, and start at index 0!
  }
void sl_all (model_t model, int*src, int *label)
  {
  state_label_many (model, src, label, 0);
  }
char pins_plugin_name[] = "GAL";
void pins_model_init(model_t m) {
  // create the LTS type LTSmin will generate
  lts_type_t ltstype=lts_type_create();
  // set the length of the state
  lts_type_set_state_length(ltstype, state_length());
  // add an int type for a state slot
  int int_type = lts_type_add_type(ltstype, "int", NULL);
  lts_type_set_format (ltstype, int_type, LTStypeDirect);
  // set state name & type
  lts_type_set_state_name(ltstype,0,"Pm4");
  lts_type_set_state_typeno(ltstype,0,int_type);
  lts_type_set_state_name(ltstype,1,"Pback4");
  lts_type_set_state_typeno(ltstype,1,int_type);
  lts_type_set_state_name(ltstype,2,"Pout4");
  lts_type_set_state_typeno(ltstype,2,int_type);
  lts_type_set_state_name(ltstype,3,"P4");
  lts_type_set_state_typeno(ltstype,3,int_type);
  lts_type_set_state_name(ltstype,4,"Pm3");
  lts_type_set_state_typeno(ltstype,4,int_type);
  lts_type_set_state_name(ltstype,5,"Pback3");
  lts_type_set_state_typeno(ltstype,5,int_type);
  lts_type_set_state_name(ltstype,6,"Pout3");
  lts_type_set_state_typeno(ltstype,6,int_type);
  lts_type_set_state_name(ltstype,7,"P3");
  lts_type_set_state_typeno(ltstype,7,int_type);
  lts_type_set_state_name(ltstype,8,"Pm2");
  lts_type_set_state_typeno(ltstype,8,int_type);
  lts_type_set_state_name(ltstype,9,"Pback2");
  lts_type_set_state_typeno(ltstype,9,int_type);
  lts_type_set_state_name(ltstype,10,"Pout2");
  lts_type_set_state_typeno(ltstype,10,int_type);
  lts_type_set_state_name(ltstype,11,"P2");
  lts_type_set_state_typeno(ltstype,11,int_type);
  lts_type_set_state_name(ltstype,12,"Pm1");
  lts_type_set_state_typeno(ltstype,12,int_type);
  lts_type_set_state_name(ltstype,13,"Pout1");
  lts_type_set_state_typeno(ltstype,13,int_type);
  lts_type_set_state_name(ltstype,14,"Pback1");
  lts_type_set_state_typeno(ltstype,14,int_type);
  lts_type_set_state_name(ltstype,15,"P1");
  lts_type_set_state_typeno(ltstype,15,int_type);
  // add an action type for edge labels
  int action_type = lts_type_add_type(ltstype, "action", NULL);
  lts_type_set_format (ltstype, action_type, LTStypeEnum);
  lts_type_set_edge_label_count (ltstype, 1);
  lts_type_set_edge_label_name(ltstype, 0, "action");
  lts_type_set_edge_label_type(ltstype, 0, "action");
  lts_type_set_edge_label_typeno(ltstype, 0, action_type);
  // add a bool type for state labels
  int bool_type = lts_type_add_type (ltstype, LTSMIN_TYPE_BOOL, NULL);
  lts_type_set_format(ltstype, bool_type, LTStypeEnum);
  lts_type_set_state_label_count (ltstype, label_count());
  for (int i =0; i < label_count() ; i++) {
    lts_type_set_state_label_typeno (ltstype, i, bool_type);
    lts_type_set_state_label_name (ltstype, i, labnames[i]);
  }
  lts_type_validate(ltstype);
  GBsetLTStype(m, ltstype);
  GBchunkPut(m, action_type, chunk_str("tr0"));
  GBchunkPut(m, action_type, chunk_str("tr1"));
  GBchunkPut(m, action_type, chunk_str("tr2"));
  GBchunkPut(m, action_type, chunk_str("tr3"));
  GBchunkPut(m, action_type, chunk_str("tr4"));
  GBchunkPut(m, action_type, chunk_str("tr5"));
  GBchunkPut(m, action_type, chunk_str("tr6"));
  GBchunkPut(m, action_type, chunk_str("tr7"));
  GBchunkPut(m, action_type, chunk_str("tr8"));
  GBchunkPut(m, action_type, chunk_str("tr9"));
  GBchunkPut(m, action_type, chunk_str("tr10"));
  GBchunkPut(m, action_type, chunk_str("tr11"));
  GBchunkPut(m, action_type, chunk_str("tr12"));
  GBchunkPut(m, action_type, chunk_str("tr13"));
  GBchunkPut(m, action_type, chunk_str("tr14"));
  GBchunkPut(m, action_type, chunk_str("tr15"));
  GBsetInitialState(m, initial_state());
  GBsetNextStateLong(m, (next_method_grey_t) next_state);
  GBsetStateLabelLong(m, (get_label_method_t) state_label);
  matrix_t *cm = malloc(sizeof(matrix_t));
  dm_create(cm, group_count(), state_length());
  matrix_t *rm = malloc(sizeof(matrix_t));
  dm_create(rm, group_count(), state_length());
  for (int i = 0; i < group_count(); i++) {
    for (int j = 0; j < state_length(); j++) {
      if (read_matrix(i)[j]) {
        dm_set(cm, i, j);
        dm_set(rm, i, j);
      }
    }
  }
  GBsetDMInfoRead(m, rm);
  matrix_t *wm = malloc(sizeof(matrix_t));
  dm_create(wm, group_count(), state_length());
  for (int i = 0; i < group_count(); i++) {
    for (int j = 0; j < state_length(); j++) {
      if (write_matrix(i)[j]) {
        dm_set(cm, i, j);
        dm_set(wm, i, j);
      }
    }
  }
  GBsetDMInfoMustWrite(m, wm);
  GBsetDMInfo(m, cm);
  matrix_t *lm = malloc(sizeof(matrix_t));
  dm_create(lm, label_count(), state_length());
  for (int i = 0; i < label_count(); i++) {
    for (int j = 0; j < state_length(); j++) {
      if (label_matrix(i)[j]) dm_set(lm, i, j);
    }
  }
  GBsetStateLabelInfo(m, lm);
  GBsetGuardsInfo(m,(guard_t**) &guardsPerTrans);
  int sl_size = label_count();
  int nguards = label_count();
  sl_group_t* sl_group_all = malloc(sizeof(sl_group_t) + sl_size * sizeof(int));
  sl_group_all->count = sl_size;
  for(int i=0; i < sl_group_all->count; i++) sl_group_all->sl_idx[i] = i;
  GBsetStateLabelGroupInfo(m, GB_SL_ALL, sl_group_all);
  if (nguards > 0) {
    sl_group_t* sl_group_guards = malloc(sizeof(sl_group_t) + nguards * sizeof(int));
    sl_group_guards->count = nguards;
    for(int i=0; i < sl_group_guards->count; i++) sl_group_guards->sl_idx[i] = i;
    GBsetStateLabelGroupInfo(m, GB_SL_GUARDS, sl_group_guards);
  }
  GBsetStateLabelsGroup(m, sl_group);
  int ngroups = group_count();
  matrix_t *gnes_info = malloc(sizeof(matrix_t));
  dm_create(gnes_info, sl_size, ngroups);
  for(int i = 0; i < sl_size; i++) {
    const int *guardnes = gal_get_label_nes_matrix(i);
    for(int j = 0; j < ngroups; j++) {
      if (guardnes[j]) dm_set(gnes_info, i, j);
    }
  }
  GBsetGuardNESInfo(m, gnes_info);
  matrix_t *gnds_info = malloc(sizeof(matrix_t));
  dm_create(gnds_info, sl_size, ngroups);
  for(int i = 0; i < sl_size; i++) {
    const int *guardnds = gal_get_label_nds_matrix(i);
    for(int j = 0; j < ngroups; j++) {
      if (guardnds[j]) dm_set(gnds_info, i, j);
    }
  }
  GBsetGuardNDSInfo(m, gnds_info);
  matrix_t *coEnab = malloc(sizeof(matrix_t));
  dm_create(coEnab, group_count(), group_count());
  for (int i = 0; i < group_count(); i++) {
    for (int j = 0; j < group_count(); j++) {
      if (coEnab_matrix(i)[j]) dm_set(coEnab, i, j);
    }
  }
  GBsetGuardCoEnabledInfo(m, coEnab);
  matrix_t *dna = malloc(sizeof(matrix_t));
  dm_create(dna, group_count(), group_count());
  for (int i = 0; i < group_count(); i++) {
    for (int j = 0; j < group_count(); j++) {
      if (dna_matrix(i)[j]) dm_set(dna, i, j);
    }
  }
  GBsetDoNotAccordInfo(m, dna);
}
