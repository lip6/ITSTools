#! /bin/bash

export BINDIR=$(pwd)

for ora in $(ls oracle/*SS.out) ; 
do 
    model=$(echo $ora | sed 's/-SS.*//' | sed 's/oracle\///'); 
    echo $model ; 
    cd INPUTS
    tar xzf $model.tgz
    cd $model
    for tech in LTLFireability LTLCardinality ; 
    do  
	short=$(echo $tech | sed s/[a-z]//g)
	outff=../../ltl/$model-$short.out
	if [ ! -f $outff ];
	then
	    echo ./runatest.sh $model $tech  > $outff
	    ../../limit_time.pl 600 ../../runeclipse.sh . $tech -its 2> /dev/null | grep FORMULA >> $outff ; 
	    # make sure job is stopped
	    killall its-ltl-linux64
	fi
    done
    cd ..
    rm -rf $model
    cd ..
done ;

# 
