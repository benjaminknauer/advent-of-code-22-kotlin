package day01

import java.io.File

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

fun getCaloriesOfTopNElves(elves: List<Elf>, n: Int): Int {
    val elvesSortedByCalories = elves
        .sortedByDescending { it.getCaloriesSum() }

    return elvesSortedByCalories
        .take(n)
        .sumOf { it.getCaloriesSum() }
}

fun part1(input: String): Int {
    val elves = parseInput(input)

    return getCaloriesOfTopNElves(elves, 1)
}

fun part2(input: String): Int {
    val elves = parseInput(input)

    return getCaloriesOfTopNElves(elves, 3)
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = File("src/day01/Day01_test.txt").readText()
    check(part1(testInput) == 24000)

    val input = File("src/day01/Day01.txt").readText()

    println("Result Part 1: %s".format(part1(input)))
    println("Result Part 2: %s".format(part2(input)))
}
