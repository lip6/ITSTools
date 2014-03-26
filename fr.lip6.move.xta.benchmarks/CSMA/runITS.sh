#! /bin/bash

echo `date` >> trace.out
for j in 1 2 3 4 5 ; do for i in *input_*$j.pop.flat.gal ; do (/data/ythierry/download/memtime-1.3/memtime -m 4000000 -c 600 /data/ythierry/DDD/libits/bin/its-reach -i $i -t GAL --gen-order FOLLOW --quiet) >> trace.out 2>> trace.out ; done ; done ;