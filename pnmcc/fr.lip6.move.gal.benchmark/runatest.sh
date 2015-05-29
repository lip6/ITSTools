#! /bin/sh

export BINDIR=`pwd`

cd INPUTS

tar xzf $1.tgz

cd $1

export MODEL=`pwd`

$BINDIR/runeclipse.sh $MODEL $2

cd ..

# \rm -rf $1

cd ..
