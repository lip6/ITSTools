#! /bin/bash

set -x

if [ ! -d INPUTS ] ; then 
    
    for i in MCC-INPUTS.tgz MCC-INPUTS_MODIFIED.tgz MCC-INPUTS_MODIFIED-2.tgz ;
    do 
	if [ ! -f $i ] ; then 
	    wget http://mcc.lip6.fr/2016/archives/$i
	fi
	tar xvzf $i
    done

    mv BenchKit/INPUTS .
    \rm -r BenchKit
fi

