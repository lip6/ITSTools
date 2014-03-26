#!/bin/bash

for i in 'one' 'pop' 'Uppaal'
do
  echo "=== $i"
  ./sum.pl -f "^$i," fusedTrace.out
done
