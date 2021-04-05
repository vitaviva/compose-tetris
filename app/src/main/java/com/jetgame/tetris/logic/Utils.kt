package com.jetgame.tetris.logic


fun Offset(x: Int, y: Int) = androidx.compose.ui.geometry.Offset(x.toFloat(), y.toFloat())

enum class Direction {
    LEFT, UP, RIGHT, DOWN
}

fun Direction.toOffset() = when (this) {
    Direction.LEFT -> -1 to 0
    Direction.UP -> 0 to -1
    Direction.RIGHT -> 1 to 0
    Direction.DOWN -> 0 to 1
}


