#! /bin/bash

echo `date` >> upptrace.out

for i in *.xta ;
do
(/data/ythierry/download/memtime-1.3/memtime -m 4000000 -c 600 /data/ythierry/download/uppaal64-4.1.16/bin-Linux/verifyta $i) >> upptrace.out 2>> upptrace.out
done
