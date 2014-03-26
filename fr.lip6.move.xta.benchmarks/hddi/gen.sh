#!/bin/bash

seq="02 03 05 07  10 15 20 25 30"
k="1 2 5 10 20"
jlist="0 1 10 50"

for jj in $jlist; do
for i in $seq; do
  for j in $k; do
    ./tokenring.gen.awk $i $j $jj
  done
done
done
