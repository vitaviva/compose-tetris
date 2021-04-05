package com.jetgame.tetris.logic

import androidx.compose.ui.geometry.Offset
import kotlin.math.absoluteValue
import kotlin.random.Random


data class Spirit(
    val shape: List<Offset> = emptyList(),
    val offset: Offset = Offset(0, 0),
) {
    val location: List<Offset> = shape.map { it + offset }

    fun moveBy(step: Pair<Int, Int>): Spirit = copy(offset = offset + Offset(step.first, step.second))

    fun rotate(): Spirit {
        val newShape = shape.toMutableList()
        for (i in shape.indices) {
            newShape[i] = Offset(shape[i].y, -shape[i].x)
        }
        return copy(shape = newShape)
    }

    fun adjustOffset(matrix: Pair<Int, Int>): Spirit {
//        val yOffset =
//            (location.minByOrNull { it.y }?.y?.takeIf { it < 0 }?.absoluteValue ?: 0).toInt() +
//                    (location.maxByOrNull { it.y }?.y?.takeIf { it > matrix.second - 1 }
//                        ?.let { matrix.second - it - 1 } ?: 0).toInt()
        val xOffset =
            (location.minByOrNull { it.x }?.x?.takeIf { it < 0 }?.absoluteValue ?: 0).toInt() +
                    (location.maxByOrNull { it.x }?.x?.takeIf { it > matrix.first - 1 }
                        ?.let { matrix.first - it - 1 } ?: 0).toInt()
        return moveBy(xOffset to 0)
    }

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


fun Spirit.isValidInMatrix(blocks: List<Brick>, matrix: Pair<Int, Int>): Boolean {
    return location.none { location ->
        location.x < 0 || location.x > matrix.first - 1 || location.y > matrix.second - 1 ||
                blocks.any { it.location.x == location.x && it.location.y == location.y }
    }
}


fun generateSpiritReverse(matrix: Pair<Int, Int>): List<Spirit> {
    return SpiritType.map {
        Spirit(it, Offset(Random.nextInt(matrix.first - 1), -1)).adjustOffset(matrix)
    }.shuffled()
}