#! /bin/sh

export BINDIR=`pwd`

export GSPNBIN=$BINDIR/gspn/SOURCES/

export PATH=${PATH}:$GSPNBIN/scripts
export PATH=${PATH}:$GSPNBIN/bin
export MCC=1

export LD_LIBRARY_PATH=$BINDIR/gspn/meddly-bugfixed/lib:$LD_LIBRARY_PATH

export MCCCTLCONV=$BINDIR/MCC/MccCtlConv/dist/MccCtlConv.jar
export GREATSPN_DIR=$GSPNBIN/bin

export BK_EXAMINATION=$2


cd INPUTS

tar xzf $1.tgz

if [ -d ../patch/$1 ] ; 
then
    \cp -rf ../patch/$1 .
fi

cd $1

cp $BINDIR/gspn/run_greatspn.sh .

$BINDIR/limit_time.pl 600 bash run_greatspn.sh 
killall RGMEDD

cd ..

\rm -rf $1

cd ..
