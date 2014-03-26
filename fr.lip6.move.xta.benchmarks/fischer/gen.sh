#!/bin/bash

seq="02 03 05 07 10 15 20 25 30 50"
k="02 04 08 16"

for i in $seq; do
  for j in $k; do
    ./fischer.gen.awk $i $j
  done
done
