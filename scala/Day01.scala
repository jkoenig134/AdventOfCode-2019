import java.io.FileNotFoundException

import scala.io.Source

object Day01 {
  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      println("Please provide the path of the input file as argument")
      return
    }
    val lines = readFile(args(0))
    if (lines.isEmpty) {
      return
    }

    println("Required fuel for all modules: " + fuelForAllModules(lines, 0, 0))
  }

  def fuelForAllModules(lines: List[String], index: Int, all: Int): Int = {
    if (lines.length == index) {
      return all
    }
    fuelForAllModules(lines, index + 1, all + fuelForModule(lines(index).toInt))
  }

  def fuelForModule(mass: Int) = (Math.floor(mass / 3) - 2).toInt

  def readFile(file: String): List[String] = {
    try {
      return Source.fromFile(file).getLines.toList
    } catch {
      case e: FileNotFoundException =>
        println("The provided input file could not be found. Please check the path to the file.")
    }
    List.empty
  }
}