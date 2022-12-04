import java.io.File
import java.util.stream.Collectors
import kotlin.streams.toList

data class Sweet(val calories: Int)
data class Elf(val sweets: List<Sweet>) {
    fun getCaloriesSum(): Int {
        return sweets.sumOf { it.calories }
    }
}

fun parseInput(input: String): List<Elf> {
    return input.split("\n\n")
        .map { elf ->
            val sweets = elf.split("\n")
                .map { it.toInt() }
                .map { Sweet(it) }
                .toList()
            Elf(sweets)
        }
}

fun main() {

    fun part1(input: String): Int {
        val elfsSortedByCalories = parseInput(input)
            .sortedByDescending { it.getCaloriesSum() }

        return elfsSortedByCalories.first().getCaloriesSum()
    }

    fun part2(input: String): Int {
        val elfsSortedByCalories = parseInput(input)
            .sortedByDescending { it.getCaloriesSum() }

        return elfsSortedByCalories
            .take(3)
            .sumOf { it.getCaloriesSum() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = File("src/Day01_test.txt").readText()
    check(part1(testInput) == 24000)

    val input = File("src/Day01.txt").readText()

    println("Result Part 1: %s".format(part1(input)))
    println("Result Part 2: %s".format(part2(input)))
}
