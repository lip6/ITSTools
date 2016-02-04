#! /bin/sh

set -e


./install_marcie.sh
./install_inputs.sh

# echo "##teamcity[testSuiteStarted name='PNMCC perfs']"

#for i in oracle/*.out ; do
#    ./run_test.pl $i -marcie;
#done;


for i in ctl/*.out ; do
    ./run_test.pl $i -marcie;
done;



# echo "##teamcity[testSuiteFinished name='PNMCC perfs']"

