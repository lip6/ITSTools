#! /bin/sh

export BINDIR=`pwd`

cd INPUTS

tar xzf $1.tgz

cd $1

export MODEL=`pwd`

if [ "$2" != "StateSpace" ];
then
    $BINDIR/marcie/marcie --net-file=model.pnml --mcc-file=$2.xml --memory=6 --suppress --rs-algorithm=3 --place-order=5 ;
else
#    $BINDIR/marcie/marcie --net-file=model.pnml --memory=6 --suppress --rs-algorithm=3 --place-order=5 ;
fi

cd ..

\rm -rf $1

cd ..
