#! /bin/bash

HEUR="--gen-order FOLLOW"

echo $ITSREACHCMD

echo `date` >> trace.out
for j in 1 2 3 4 5 ; do 
    for i in *input_*$j.*.flat.gal ; do 
	($ITSREACHCMD  $i $HEUR) >> trace.out 2>> trace.out ; 
    done ; 
done ;
