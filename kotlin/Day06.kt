import java.io.File
import java.util.*

val orbitMap = mutableMapOf<String, TreeNode>()

fun main(args: Array<String>) {
    if(args.isEmpty()) {
        return
    }
    readSixthInput(args)
    solveSixthPart1()
    solveSixthPart2()
}

fun readSixthInput(args: Array<String>) {
    val scanner = Scanner(File(args[0]))
    while(scanner.hasNextLine()) {
        addOrbit(scanner.nextLine())
    }
    scanner.close()
}

fun solveSixthPart1() {
    var i = 0
    for(name in orbitMap.keys) {
        i += orbitMap[name]!!.parentChain().size
    }
    println("Part 1: $i")
}

fun solveSixthPart2() {
    val youChain = orbitMap["YOU"]!!.parentChain()
    val sanChain = orbitMap["SAN"]!!.parentChain()
    var commonPlanet: TreeNode? = null
    for(parent in youChain) {
        if(sanChain.contains(parent)) {
            commonPlanet = parent
            break
        }
    }
    println("Part 2: ${youChain.indexOf(commonPlanet) + sanChain.indexOf(commonPlanet)}")
}

fun addOrbit(orbitStatement: String) {
    val parentNode = orbitMap[orbitStatement.split(")")[0]] ?: TreeNode(orbitStatement.split(")")[0])
    val childNode = orbitMap[orbitStatement.split(")")[1]] ?: TreeNode(orbitStatement.split(")")[1])
    parentNode.addChildren(childNode)
    childNode.addParent(parentNode)
}

class TreeNode(name: String) {
    private val children = mutableListOf<TreeNode>()
    private var parent: TreeNode? = null

    init {
        orbitMap[name] = this
    }

    fun addChildren(child: TreeNode) {
        children.add(child)
    }

    fun addParent(parent: TreeNode) {
        this.parent = parent
    }

    fun parentChain(): List<TreeNode> {
        var currentPlanet = this.parent
        val chain = mutableListOf<TreeNode>()
        while(currentPlanet != null) {
            chain.add(currentPlanet)
            currentPlanet = currentPlanet.parent
        }
        return chain
    }
}