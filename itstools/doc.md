---
title: Configuration and Running of GAL-model files with the ITS_Tools
---

How to configure and run GAL_model with ITS_Tools?
=================================================

This documentation aims to give you an interactive way to configure and run GAL mode files using ITS_Tools in an eclipse configuration window. Several options have been chosen among all those available in the command-line its-tools version.
They are:
+ Input options (mandatory):
    
    -i path : specifies the path to the input model file.
    
    -t {CAMI|PROD|ROMEO|ITSXML|ETF|DVE|GAL|DLL|NDLL} : specifies format of the input model file :
        + CAMI : CAMI format (for P/T nets) is the native Petri net format of CPN-AMI
        PROD : PROD format (for P/T nets) is the native format of PROD
        ROMEO : an XML format (for Time Petri nets) that is the native format of Romeo
        ITSXML : a native XML format (for ANY kind of ITS) for this tool. These files allow to point to other files and are used as main file for composite definitions. See this example, the list of formalism/format supported is described here.
        ETF : Extended Table Format is the native format used by LTSmin, built from many front-ends.
        DVE : Divine is a modelling language similar to Promela. Input file should be in Divine format.
        GAL : Guarded Action Language. Input file should be in GAL syntax.
        DLL : use a dynamic library that provides a function "void loadModel (Model &,int)" typically written using the manipulation APIs. See demo/ folder.
        NDLL : same as DLL, but expect input formatted as size:lib.so. See demo/ folder for a usage example. Both DLL and NDLL are used to inject of arbitrary C++ ITS types into the ITSModel.
