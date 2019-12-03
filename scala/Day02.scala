import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.Source

object Day02 {

  def main(args: Array[String]): Unit = {
    val source = Source.fromFile(args(0))
    val codeArray = ArrayBuffer[Int]()
    source.getLines.map(l => l.split(",").map(s => s.toInt).toList).foreach(codeArray.addAll)
    val taskOne = codeArray
    val taskTwo = codeArray.toArray
    taskOne(1) = 12
    taskOne(2) = 2
    println(executeIntcode(taskOne))
    println(findInput(taskTwo))
    source.close()
  }

  def executeIntcode(codeArray: ArrayBuffer[Int]): Int = {
    for (i <- 0 to codeArray.length / 4) {
      val index = i * 4
      val opcode = codeArray(index)
      opcode match {
        case 1 => codeArray(codeArray(index + 3)) = codeArray(codeArray(index + 1)) + codeArray(codeArray(index + 2))
        case 2 => codeArray(codeArray(index + 3)) = codeArray(codeArray(index + 1)) * codeArray(codeArray(index + 2))
        case 99 => return codeArray(0)
        case _ => return -1
      }
    }
    -1
  }

  def findInput(codeArray: Array[Int]): Int = {
    for (n <- 0 to 99) {
      for (v <- 0 to 99) {
        val codes = ArrayBuffer[Int]()
        codes.addAll(codeArray)
        codes(1) = n
        codes(2) = v
        if (executeIntcode(codes) == 19690720) {
          return 100 * n + v
        }
      }
    }
    -1
  }
}
