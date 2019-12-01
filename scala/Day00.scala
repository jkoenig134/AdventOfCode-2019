import java.io.FileNotFoundException

import scala.io.Source

object Day00 {
  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      println("Please provide the path of the input file as argument")
      return
    }
    val lines = readFile(args(0))
    if (lines.isEmpty) {
      return
    }
    println(lines)
  }

  def readFile(file: String): List[String] = {
    try {
      return Source.fromFile(file).getLines.toList
    } catch {
      case e: FileNotFoundException => {
        println("The provided input file could not be found. Please check the path to the file.")
      }
    }
    return List.empty
  }
}