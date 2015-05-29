#! /bin/sh

# invoke me like this :
#  ./collect_data.sh  `\ls -1 INPUTS/ | sed 's/.tgz//'`

for i in "$@" ; do
    OUT=oracle/$i-SS.out
    CMD="./runatest.sh $i StateSpace"
    echo $CMD > $OUT
    $CMD >> $OUT
done

