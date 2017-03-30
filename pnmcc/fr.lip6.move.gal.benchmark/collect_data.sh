#! /bin/sh

# invoke me like this :
#  ./collect_data.sh  $(\ls -1 INPUTS/ | sed 's/.tgz//')

# another variant based on existing reachability runs
# \ls -1 oracle/*SS.out | sed 's/\-SS\.out//' | sed 's/oracle\///'

for i in "$@" ; do
#    OUT=oracle/$i-SS.out
#    CMD="./runatest.sh $i StateSpace"
    OUT=oracle/$i-RFS.out
    CMD="./runatest.sh $i ReachabilityFireabilitySimple"
    echo $CMD > $OUT
    $CMD >> $OUT
done

# for i in $(ls -1 *SS* | sed 's/-SS.out//') ; do cat verdicts.csv | grep "^$i" | grep ReachabilityFireabilitySimple ; done

# grab lines with good confidence level
# for i in $(ls -1 *SS* | sed 's/-SS.out//') ; do cat verdicts.csv | grep "^$i" | grep ReachabilityFireabilitySimple | ../csv_to_control.pl ; done


# adapt PT results to COL
# for i in *RFS* ; do j=$(echo $i | sed 's/PT/COL/'` ; if [ ! -f $j ]; then k=`echo $j | sed 's/RFS/SS/') ; if [ -f $k ]; then cat $i | sed 's/PT/COL/' | sed 's/TECHNIQUES/TECHNIQUES FROM_PT/' > $j ; fi ; fi ;done ;
