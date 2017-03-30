#! /bin/bash

set -x

if [ ! -d eclipse ] ; then 
  if [ ! -f eclipse-platform-4.6.3-linux-gtk-x86_64.tar.gz ] ; then 
    wget http://mirror.ibcp.fr/pub/eclipse//eclipse/downloads/drops4/R-4.6.3-201703010400/eclipse-platform-4.6.3-linux-gtk-x86_64.tar.gz
#   wget http://mirror.ibcp.fr/pub/eclipse//eclipse/downloads/drops4/R-4.6.2-201611241400/eclipse-platform-4.6.2-linux-gtk-x86_64.tar.gz
#    wget http://mirror.ibcp.fr/pub/eclipse//eclipse/downloads/drops4/R-4.5.2-201602121500/eclipse-platform-4.5.2-linux-gtk-x86_64.tar.gz
#    wget http://mirror.ibcp.fr/pub/eclipse//eclipse/downloads/drops4/R-4.5.1-201509040015/eclipse-platform-4.5.1-linux-gtk-x86_64.tar.gz
#    wget http://mirror.ibcp.fr/pub/eclipse//eclipse/downloads/drops4/R-4.4.2-201502041700/eclipse-platform-4.4.2-linux-gtk-x86_64.tar.gz
  fi

  tar xvzf eclipse-platform-*-linux-gtk-x86_64.tar.gz
  rm eclipse-platform-*-linux-gtk-x86_64.tar.gz
fi

cd eclipse

# cleanup install
./eclipse \
-clean -purgeHistory \
-application org.eclipse.equinox.p2.director \
-noSplash \
-uninstallIUs fr.lip6.move.gal.feature.pnmcc.feature.group


# install
./eclipse \
-clean -purgeHistory \
-application org.eclipse.equinox.p2.director \
-noSplash \
-repository http://download.eclipse.org/releases/neon,https://lip6.github.io/ITSTools \
-installIUs fr.lip6.move.gal.feature.pnmcc.feature.group

