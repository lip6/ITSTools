#! /bin/bash

set -x

if [ ! -d INPUTS ] ; then 
    
    if [ ! -f MCC-INPUTS.tgz ] ; then 
	wget http://mcc.lip6.fr/2015/archives/MCC-INPUTS.tgz
    fi

    tar xvzf MCC-INPUTS.tgz

    mv BenchKit/INPUTS .
    \rm -r BenchKit
fi

