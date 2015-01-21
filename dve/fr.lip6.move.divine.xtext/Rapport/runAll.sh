#! /bin/bash

ITSCMD=/opt/upmc/master/s2/psar/eclipse/plugins/fr.lip6.move.coloane.tools.its_1.0.0.201404180114/bin/its-reach-linux64

set -x

(
for i in *.flat.gal ;
do
  $ITSCMD -i $i -t GAL --quiet 
done;

for i in *.dve ;
do
  $ITSCMD -i $i -t DVE --quiet
done;
) > trace.out 2> trace.out
