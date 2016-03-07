#! /bin/bash

set -x

if [ ! -d gspn ] ; then 
    
    mkdir gspn
    cd gspn
    
    folder='http://www.di.unito.it/~beccuti/Yann/'
    
    for yname in SOURCES.tar.gz MCC.tar.gz meddly-bugfixed.tgz ; do
    
      export DLURL="$folder/$yname"

      if [ ! -f $yname ] ; then 
	  wget $DLURL -O $yname;
      fi

      tar xvzf $yname    
    done

    #get rid of environment configuration
    head -15  MCC/greatspn_tool_mcc2015.sh > run_greatspn.sh
    tail -n +22  MCC/greatspn_tool_mcc2015.sh >> run_greatspn.sh
    
    cd ..
fi

