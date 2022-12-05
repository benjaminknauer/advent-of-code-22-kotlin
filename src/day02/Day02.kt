package day02

import java.io.File
import java.lang.IllegalArgumentException

enum class Move(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    companion object {
        fun fromShortcode(shortcode: String): Move {
            return when (shortcode) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> throw IllegalArgumentException("Ungültiger Code %s".format(shortcode))
            }
        }
    }
}

enum class RoundOutcome(val score: Int) {
    WIN(6), DRAW(3), LOOSE(0);

    companion object {
        fun fromShortcode(shortcode: String): RoundOutcome {
            return when (shortcode) {
                "X" -> LOOSE
                "Y" -> DRAW
                "Z" -> WIN
                else -> throw IllegalArgumentException("Ungültiger Code %s".format(shortcode))
            }
        }
    }
}

class Game(private val gameRounds: List<GameRound>) {
    fun getScore(): Int {
        return gameRounds.sumOf { it.getScore() }
    }
}

class GameRound(private val yourMove: Move, private val opponentMove: Move) {
    constructor(opponentMove: Move, expectedOutcome: RoundOutcome) : this(
        calculateYourOption(
            opponentMove,
            expectedOutcome
        ), opponentMove
    )

    fun getScore(): Int {
        return calculateOutcome().score + yourMove.score
    }

    companion object {
        fun calculateYourOption(opponentMove: Move, expectedOutcome: RoundOutcome): Move {
            return when (expectedOutcome) {
                RoundOutcome.WIN -> {
                    when (opponentMove) {
                        Move.ROCK -> Move.PAPER
                        Move.PAPER -> Move.SCISSORS
                        Move.SCISSORS -> Move.ROCK
                    }
                }

                RoundOutcome.DRAW -> opponentMove
                RoundOutcome.LOOSE -> {
                    when (opponentMove) {
                        Move.ROCK -> Move.SCISSORS
                        Move.PAPER -> Move.ROCK
                        Move.SCISSORS -> Move.PAPER
                    }
                }
            }
        }
    }

    private fun calculateOutcome(): RoundOutcome {
        return when (yourMove) {
            Move.ROCK -> {
                when (opponentMove) {
                    Move.ROCK -> RoundOutcome.DRAW
                    Move.PAPER -> RoundOutcome.LOOSE
                    Move.SCISSORS -> RoundOutcome.WIN
                }
            }

            Move.PAPER -> {
                when (opponentMove) {
                    Move.ROCK -> RoundOutcome.WIN
                    Move.PAPER -> RoundOutcome.DRAW
                    Move.SCISSORS -> RoundOutcome.LOOSE
                }
            }

            Move.SCISSORS -> {
                when (opponentMove) {
                    Move.ROCK -> RoundOutcome.LOOSE
                    Move.PAPER -> RoundOutcome.WIN
                    Move.SCISSORS -> RoundOutcome.DRAW
                }
            }
        }
    }
}

fun parseInputPart1(input: String): Game {
    val gameRounds = input.split("\n")
        .map { gameround: String ->
            val selectedOptions = gameround.split(" ")
            val opponentMove = Move.fromShortcode(selectedOptions[0])
            val yourMove = Move.fromShortcode(selectedOptions[1])
            return@map GameRound(yourMove, opponentMove)
        }
    return Game(gameRounds)
}

fun parseInputPart2(input: String): Game {
    val gameRounds = input.split("\n")
        .map { gameround: String ->
            val shortcodes = gameround.split(" ")
            val opponentMove = Move.fromShortcode(shortcodes[0])
            val expectedOutcome = RoundOutcome.fromShortcode(shortcodes[1])
            return@map GameRound(opponentMove, expectedOutcome)
        }
    return Game(gameRounds)
}

fun part1(input: String): Int {
    val game = parseInputPart1(input)
    return game.getScore()
}

fun part2(input: String): Int {
    val game = parseInputPart2(input)
    return game.getScore()
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = File("src/day02/Day02_test.txt").readText()
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = File("src/day02/Day02.txt").readText()

    println("Result Part 1: %s".format(part1(input)))
    println("Result Part 2: %s".format(part2(input)))
}
