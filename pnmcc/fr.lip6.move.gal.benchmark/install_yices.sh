#! /bin/bash

set -x

if [ ! -d yices ] ; then 
    
    yname=yices-2.4.1-x86_64-unknown-linux-gnu-static-gmp.tar.gz
    if [ ! -f yices.tgz ] ; then 
	wget "http://yices.csl.sri.com/cgi-bin/yices2-newnewdownload.cgi?file=$yname&accept=I+Agree"
	mv yices2* yices.tgz
    fi

    tar xvzf yices.tgz 
    
    mv yices-2.4.1/ yices/
fi

