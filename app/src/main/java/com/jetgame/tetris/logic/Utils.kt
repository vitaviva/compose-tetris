package com.jetgame.tetris.logic

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.jetgame.tetris.R


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


val LedFontFamily = FontFamily(
    Font(R.font.unidream_led, FontWeight.Light),
    Font(R.font.unidream_led, FontWeight.Normal),
    Font(R.font.unidream_led, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.unidream_led, FontWeight.Medium),
    Font(R.font.unidream_led, FontWeight.Bold)
)

val NextMatrix = 4 to 2


object TT {
    val tt:Int = 0
}

fun main() {
    TT.tt
}