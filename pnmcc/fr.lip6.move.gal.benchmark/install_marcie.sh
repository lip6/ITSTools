#! /bin/bash

set -x

if [ ! -d marcie ] ; then 
    
    yname=marcie-linux64.tar.gz
    export DLURL=`curl 'http://www-dssz.informatik.tu-cottbus.de/DSSZ/Software/Marcie' | grep 'Linux (64bit)' | cut -f 6 -d \' `

    if [ ! -f $yname ] ; then 
	wget $DLURL -O $yname;
    fi

    tar xvzf $yname
    
    mv marcie*/ marcie/
fi

marcie/marcie 2> /dev/null  | grep Marcie



