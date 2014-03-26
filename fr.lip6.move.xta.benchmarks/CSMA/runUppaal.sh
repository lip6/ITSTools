#! /bin/bash

echo `date` >> upptrace.out

for i in *.xta ; do
    ($UPPAALCMD $i) >> upptrace.out 2>> upptrace.out
done
