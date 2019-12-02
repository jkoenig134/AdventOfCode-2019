#!/bin/bash
# Define source and binary files
source_file=$1.hs
binary_file=run$1.bin

# Compile the code using ghc
ghc -o $binary_file $source_file

# Execute the binary with input file as argument
./$binary_file $2