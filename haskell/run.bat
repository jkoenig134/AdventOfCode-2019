:: Define source and binary files
set "source_file=%1.hs"
set "binary_file=run%1.exe"

:: Compile the code using ghc
ghc -o %binary_file% %source_file%

:: Execute the binary with input file as argument
cmd /c %binary_file% %2