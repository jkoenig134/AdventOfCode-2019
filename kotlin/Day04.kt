import java.io.File
import java.util.*

var amountPart1 = 0
var amountPart2 = 0

fun main(args: Array<String>) {
    if(args.isEmpty()) {
        return
    }
    readFourthInput(args)
    println("Amount of solutions / Part 1: $amountPart1")
    println("Amount of solutions / Part 2: $amountPart2")
}

fun readFourthInput(args: Array<String>) {
    val scanner = Scanner(File(args[0]))
    val limits = scanner.nextLine().split("-")
    findSolutions(limits[0].toInt(), limits[1].toInt())
    scanner.close()
}

fun findSolutions(start: Int, stop: Int) {
    for(i in start..stop) {
        checkRequirements1(i)
        checkRequirements2(i)
    }
}

fun checkRequirements1(int: Int) {
    val intString = int.toString()
    if(intString[0].toInt() <= intString[1].toInt() && intString[1].toInt() <= intString[2].toInt() && intString[2].toInt() <= intString[3].toInt() && intString[3].toInt() <= intString[4].toInt() && intString[4].toInt() <= intString[5].toInt()) {
        for(i in 1..9) {
            val difference = intString.length - intString.replace(i.toString(), "").length
            if(difference >= 2) {
                amountPart1++
                return
            }
        }
    }
}

fun checkRequirements2(int: Int) {
    val intString = int.toString()
    if(intString[0].toInt() <= intString[1].toInt() && intString[1].toInt() <= intString[2].toInt() && intString[2].toInt() <= intString[3].toInt() && intString[3].toInt() <= intString[4].toInt() && intString[4].toInt() <= intString[5].toInt()) {
        for(i in 1..9) {
            val difference = intString.length - intString.replace(i.toString(), "").length
            if(difference == 2) {
                amountPart2++
                return
            }
        }
    }
}