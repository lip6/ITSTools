#!/bin/bash
echo "#+TITLE: Test ITS Promela générés le $(date)"
echo "|Model |#S |Time |Mem(kb) |fin. SDD |fin. DDD |peak SDD |peak DDD |SDD Hom |SDD cache peak |DDD Hom |DDD cachepeak |SHom cache|"
echo "|-----+-----|" # tab dans fichier org pour réaligner
cat test_*.data | grep -A1 '^Model ,'  | grep -v '^Model ,'| sort| grep -v '^--'  | tr ',' '|' |awk '{print "|" $0 "|" }' | tr -d '\\'
