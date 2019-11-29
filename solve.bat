@echo off
cd %1
if "%~3"==""
	(set file_path=./input/%2.txt)
else
	(set file_path=%3)
run %2 %file_path%