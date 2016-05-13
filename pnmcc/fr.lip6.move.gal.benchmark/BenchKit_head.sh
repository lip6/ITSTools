#!/bin/bash
#############################################################################
# This is an example for the MCC'2015
#############################################################################

#############################################################################
# In this script, you will affect values to your tool in order to let
# BenchKit launch it in an apropriate way.
#############################################################################

# BK_EXAMINATION: it is a string that identifies your "examination"

set -x
export BINDIR=/home/mcc/BenchKit/

export MODEL=`pwd`

if [[ $BK_EXAMINATION = StateSpace && -f modelMain.xml ]] ; 
then
$BINDIR/its-reach-linux64 -i modelMain.xml -t ITSXML -ssDR 5 --stats | sed 's/Max variable value :/STATE_SPACE MAX_TOKEN_IN_PLACE /' | sed 's/Maximum sum along a path :/STATE_SPACE MAX_TOKEN_PER_MARKING /' | sed 's/Exact state count :/STATE_SPACE STATES /' | sed 's/Total edges in reachability graph :/STATE_SPACE TRANSITIONS /'
else
$BINDIR/runeclipse.sh $MODEL $BK_EXAMINATION -its
fi
