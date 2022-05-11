#! /bin/bash

set -x

ulimit -s 65536

# configure LTSmin to use a maximum of XGB of memory, this is neccessary
# because sysconf does not work in docker
# cg_ does not work on cluster with OAR but not cg_memory set
# cannot bound LTSmin memory if in portfolio with other methods...
# Basically guessing available memory and trying to take it all is a FBI
# "Fausse Bonne Idee",
# e.g. it will never support two LTSmin running different problems in parallel.
# 4 << 30 = 4294967296  4GB
# 8 << 30 = 8589934592  8GB
# 16 << 30 = 17179869184  16GB
if [[ -z "${LTSMIN_MEM_SIZE}" ]]; then
    export LTSMIN_MEM_SIZE=8589934592    
fi


# $BINDIR/itstools/its-tools  -Dosgi.locking=none -Declipse.stateSaveDelayInterval=-1 -Dosgi.configuration.area=/tmp/.eclipse -Xss8m -Xms40m -Xmx8192m  -Dfile.encoding=UTF-8  -data $1/workspace  -pnfolder $1 -examination $2 -z3path $BINDIR/z3/bin/z3 -yices2path $BINDIR/yices/bin/yices ${@:3}

java -Dosgi.requiredJavaVersion=1.6 -Dosgi.locking=none -Declipse.stateSaveDelayInterval=-1 -Dosgi.configuration.area=$1/.eclipse -Xss8m -Xms40m -Xmx8192m -Declipse.pde.launch=true -Dfile.encoding=UTF-8 -classpath $BINDIR/eclipse/plugins/org.eclipse.equinox.launcher_1.4.0.v20161219-1356.jar org.eclipse.equinox.launcher.Main -application fr.lip6.move.gal.application.pnmcc -data $1/workspace -os linux -ws gtk -arch x86_64 -nl en_US -consoleLog -pnfolder $1 -examination $2 -z3path $BINDIR/z3/bin/z3 -yices2path $BINDIR/yices/bin/yices ${@:3}
# -XX:MaxPermSize=512m
# -z3path $BINDIR/z3/bin/z3
# -its
#-configuration file:/home/mcc/workspace/.metadata/.plugins/org.eclipse.pde.core/fr.lip6.move.gal.application/ 
#-dev file:/data/ythierry/workspaces/luna2/.metadata/.plugins/org.eclipse.pde.core/fr.lip6.move.gal.application/dev.properties 
# luna : org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar
# mars : org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar
