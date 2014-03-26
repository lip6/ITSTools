#! /bin/bash

echo `date` >> trace.out

HEUR=--gen-order FOLLOW

for j in 1 2 3 4 5 ; do 
    for i in *input_*$j.one.flat.gal ; do 
	($ITSREACHCMD $i $HEUR) >> trace.out 2>> trace.out ; 
    done ; 
done ;
