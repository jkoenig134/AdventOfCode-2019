set file_name=%1.scala
mkdir build
powershell -command scalac -d build %file_name%
powershell -command scala -cp build %1 %2
del build