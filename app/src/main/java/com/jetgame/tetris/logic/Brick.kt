package com.jetgame.tetris.logic

import androidx.compose.ui.geometry.Offset
import kotlin.random.Random


data class Brick(val location: Offset = Offset(0, 0)) {
    companion object {
        fun of(spirit: Spirit) = spirit.location.map { Brick(it) }
    }
}

data class Spirit(
    val shape: List<Offset> = emptyList(),
    val offset: Offset = Offset(0, 0),
) {
    val location: List<Offset> = shape.map { it + offset }

    fun move(x: Int, y: Int): Spirit = copy(offset = offset + Offset(x, y))

    companion object {
        val Empty = Spirit()
    }
}


private val SpiritType = listOf(
    listOf(Offset(1, -1), Offset(1, 0), Offset(0, 0), Offset(0, 1)),
    listOf(Offset(0, -1), Offset(0, 0), Offset(1, 0), Offset(1, 1)),
    listOf(Offset(0, -1), Offset(0, 0), Offset(0, 1), Offset(0, 2)),
    listOf(Offset(0, 1), Offset(0, 0), Offset(0, -1), Offset(1, 0)),
    listOf(Offset(1, 0), Offset(0, 0), Offset(1, -1), Offset(0, -1)),
    listOf(Offset(0, -1), Offset(1, -1), Offset(1, 0), Offset(1, 1)),
    listOf(Offset(1, -1), Offset(0, -1), Offset(0, 0), Offset(0, 1))
)


fun Spirit.isValid(blocks: List<Brick>, playground: Pair<Int, Int>): Boolean {
    return location.none { location ->
        location.x < 0 || location.x > playground.first || location.y > playground.second - 1 ||
                blocks.any { it.location.x == location.x && it.location.y == location.y }
    }
}

fun Offset(x: Int, y: Int) = Offset(x.toFloat(), y.toFloat())

fun generateSpiritReverse(palyground: Pair<Int, Int>): List<Spirit> {
    return SpiritType.map { Spirit(it, Offset(Random.nextInt(palyground.first), 0)) }.shuffled()
}