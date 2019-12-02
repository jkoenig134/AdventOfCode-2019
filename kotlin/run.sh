export file_name=$1.kt
kotlinc $file_name -include-runtime -d $1.jar
java -jar $1.jar $2