import java.io.File
import java.util.Scanner

var currentPosition = 0

fun main(args: Array<String>) {
    if(args.isEmpty()) {
        return
    }
    var values = readInput(args)
    println("First value: ${processInput(values)}")
    for(x in 0..99) {
        for(y in 0..99) {
            values = readInput(args)
            values[1] = x
            values[2] = y
            if(processInput(values) == 19690720) {
                println("1 = $x")
                println("2 = $y")
            }
        }
    }
    println("Nothing found")
}

fun readInput(args: Array<String>): MutableList<Int> {
    val scanner = Scanner(File(args[0]))
    val rawValues = scanner.nextLine().split(",")
    val values: MutableList<Int> = mutableListOf()
    for(v in rawValues) {
        values.add(v.toInt())
    }
    scanner.close()
    return values
}

fun processInput(values: MutableList<Int>): Int {
    while(values.size > currentPosition) {
        when(values[currentPosition]) {
            1 -> values[values[currentPosition + 3]] = values[values[currentPosition + 1]] + values[values[currentPosition + 2]]
            2 -> values[values[currentPosition + 3]] = values[values[currentPosition + 1]] * values[values[currentPosition + 2]]
            99 -> {
                // Stop the program, return the first entry
                return values[0]
            }
            else -> {
                println("Error while running program!")
                return 0
            }
        }
        currentPosition += 4
    }
    return 0
}