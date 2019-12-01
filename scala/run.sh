file_name=$1.scala
mkdir build
scalac -d build "$file_name"
scala -cp build $1 $2
rm -rf build