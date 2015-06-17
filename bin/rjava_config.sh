#!/bin/bash

GCC_VERSION="4.3"

export R_BASE=/app/vendor/R
export R_HOME=$R_BASE/lib64/R
export JAVA_HOME=/app/vendor/openjdk-1.7.0_51
export JAVA_CPPFLAGS="-I$JAVA_HOME/include -I$JAVA_HOME/include/linux"
export R_INCLUDE=$R_HOME/include
export PATH=$JAVA_HOME/bin:$R_BASE/bin:/app/vendor/gcc-$GCC_VERSION/bin:$PATH

export JAVA_HOME=/app/vendor/openjdk-1.7.0_51
R CMD javareconf
