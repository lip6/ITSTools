#! /bin/bash

echo `date` >> trace.out

# DEFAULT order heuristic is fine

for j in  2 4 8 16 ; do 
	for i in *input_*$j.*.flat.gal ; do 
		($ITSREACHCMD  $i) >> trace.out 2>> trace.out ;
    done ; 
done ;


