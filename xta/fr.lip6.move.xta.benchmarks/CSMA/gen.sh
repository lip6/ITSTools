#!/bin/bash

seq="02 03 05 07 10 15 20 25 30"
seqVal="1 2 3 4 5"

for j in $seqVal; do
  for i in $seq; do
    ./csma.gen.awk $i $j
  done
 done
