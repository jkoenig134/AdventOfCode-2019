@echo off
pushd .
cd %~dp0
if "%~3"=="" (set file_path="%~dp0input/%2.txt") else (set file_path="%~f3")
popd
%1/run %1/%2 %file_path%