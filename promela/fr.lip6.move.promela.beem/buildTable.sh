#! /bin/bash

cat test_*.data | grep -A1 '^Model ,'  | grep -v '^Model ,'
