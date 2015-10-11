#! /bin/sh

set -x

ulimit -s 65536

java -Dosgi.requiredJavaVersion=1.6 -XX:MaxPermSize=512m -Xss8m -Xms40m -Xmx8192m -Declipse.pde.launch=true -Dfile.encoding=UTF-8 -classpath $BINDIR/eclipse/plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar org.eclipse.equinox.launcher.Main -application fr.lip6.move.gal.application.pnmcc -data $BINDIR/workspace -os linux -ws gtk -arch x86_64 -nl en_US -consoleLog -pnfolder $1 -examination $2 -z3path $BINDIR/z3/bin/z3 $3
#-configuration file:/home/mcc/workspace/.metadata/.plugins/org.eclipse.pde.core/fr.lip6.move.gal.application/ 
#-dev file:/data/ythierry/workspaces/luna2/.metadata/.plugins/org.eclipse.pde.core/fr.lip6.move.gal.application/dev.properties 
