import java.io.File
import java.util.Scanner
import java.lang.Math

var totalNeededModuleFuel = 0.0
var totalFuel = 0.0

fun main(args: Array<String>) {
    if(args.size == 0) {
        return
    }

    val filePath = args[0]
    val scanner = Scanner(File(filePath))

    while(scanner.hasNext()) {
        val moduleFuel = Math.floor(scanner.nextLine().toDouble() / 3) - 2
        totalNeededModuleFuel = totalNeededModuleFuel + moduleFuel
        var mass = Math.floor(moduleFuel / 3) - 2
        while(mass > 0) {
            totalFuel = totalFuel + mass
            mass = Math.floor(mass / 3) - 2
        }
    }

    totalFuel = totalFuel + totalNeededModuleFuel

    println("Fuel needed for modules: $totalNeededModuleFuel")
    println("Total fuel needed: $totalFuel")
}