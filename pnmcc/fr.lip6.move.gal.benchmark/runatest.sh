#! /bin/sh

export BINDIR=`pwd`

cd INPUTS

tar xzf $1.tgz
if [ -d ../patch/$1 ] ; 
then
    \cp -rf ../patch/$1 .
fi

cd $1

export MODEL=`pwd`

$BINDIR/runeclipse.sh $MODEL $2 $3

cd ..

#\rm -rf $1

cd ..
