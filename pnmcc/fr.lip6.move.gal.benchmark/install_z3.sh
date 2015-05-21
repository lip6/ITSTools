#! /bin/bash

set -x


# distrib is : z3-4.4.0.b6c40c6c0eaf-x64-ubuntu-14.04.zip
if [ ! -f z3.zip ] ; then 
    curl "http://download-codeplex.sec.s-msft.com/Download/Release?ProjectName=z3&DownloadId=1441362&FileTime=130718577284870000&Build=20999" > z3.zip
fi

if [ ! -d z3 ] ; then 
    unzip z3.zip
    mv z3*/ z3/ 
fi


