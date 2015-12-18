#! /bin/bash

set -x

if [ ! -d BEEM ] ; then 
    
    if [ ! -f models_data.tar.gz ] ; then 
	wget http://paradise.fi.muni.cz/beem/models_data.tar.gz
    fi

    tar xvzf models_data.tar.gz

    mv data/ BEEM/

fi

