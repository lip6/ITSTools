#! /bin/bash

set -x
set -e


./install_eclipse.sh

./install_z3.sh

./install_inputs.sh

./install_yices.sh


