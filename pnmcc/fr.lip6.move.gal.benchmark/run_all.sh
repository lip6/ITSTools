#! /bin/sh

set -e


./install_all.sh


echo "##teamcity[testSuiteStarted name='PNMCC perfs']"

for i in oracle/*.out ; do
    ./run_test.pl $i -its;
done;

echo "##teamcity[testSuiteFinished name='PNMCC perfs']"

