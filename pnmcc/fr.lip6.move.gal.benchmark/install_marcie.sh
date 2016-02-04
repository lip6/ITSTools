#! /bin/bash

set -x

if [ ! -d marcie ] ; then 
    
    yname=marcie-linux64-2016-02-04.tar.gz
    if [ ! -f $yname ] ; then 
	wget "http://www-dssz.informatik.tu-cottbus.de/track/download.php?id=153"
    fi

    tar xvzf $yname
    
    mv marcie*/ marcie/
fi

