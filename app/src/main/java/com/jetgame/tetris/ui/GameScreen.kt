package com.jetgame.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jetgame.tetris.R
import com.jetgame.tetris.logic.Brick
import com.jetgame.tetris.logic.GameViewModel
import com.jetgame.tetris.logic.Spirit
import com.jetgame.tetris.ui.theme.BlockPlayground
import com.jetgame.tetris.ui.theme.BlockSpirit
import com.jetgame.tetris.ui.theme.ScreenBackground
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlin.math.min

@ObsoleteCoroutinesApi
@Composable
fun GameScreen(modifier: Modifier = Modifier) {

    val viewModel = viewModel<GameViewModel>()
    val viewState by viewModel.viewState.collectAsState()

    val tickerChannel = remember { ticker(delayMillis = 200) }

    LaunchedEffect(key1 = Unit) {
        for (event in tickerChannel) {
            viewModel.dispatch(GameViewModel.Intent.GameTick)
        }
    }

    Box(
        modifier
            .background(Color.Black)
            .padding(1.dp)
            .background(ScreenBackground)
            .padding(10.dp)
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()

        ) {

            val brickSize = min(
                size.width / viewState.playground.first,
                size.height / viewState.playground.second
            )

            drawPlayground(brickSize = brickSize, viewState.playground)
            drawBricks(viewState.bricks, brickSize)
            drawSpirit(viewState.spirit, brickSize)
        }
    }

}


fun DrawScope.drawPlayground(brickSize: Float, playground: Pair<Int, Int>) {
    (0 until playground.first).forEach { x ->
        (0 until playground.second).forEach { y ->
            drawBrick(
                brickSize,
                Offset(x.toFloat(), y.toFloat()),
                BlockPlayground
            )
        }
    }

    //draw border
    val gap = playground.first * brickSize * 0.05f
    scale(1.0f) {
        drawRect(
            Color.Black,
            size = Size(
                playground.first * brickSize + gap,
                playground.second * brickSize + gap
            ),
            topLeft = Offset(
                -gap / 2,
                -gap / 2
            ),
            style = Stroke(1f)
        )
    }
}

fun DrawScope.drawBricks(brick: List<Brick>, brickSize: Float) {
    brick.forEach {
        drawBrick(brickSize, it.location, BlockSpirit)
    }
}

fun DrawScope.drawSpirit(spirit: Spirit, brickSize: Float) {
    spirit.location.forEach {
        drawBrick(
            brickSize,
            Offset(it.x, it.y),
            BlockSpirit
        )
    }
}

fun DrawScope.drawBrick(
    brickSize: Float,
    offset: Offset,
    color: Color
) {

    val actualLocation = Offset(
        offset.x * brickSize,
        offset.y * brickSize
    )

    val outerSize = brickSize * 0.8f
    val outerOffset = (brickSize - outerSize) / 2

    drawRect(
        color,
        topLeft = actualLocation + Offset(outerOffset, outerOffset),
        size = Size(outerSize, outerSize),
        style = Stroke(outerSize / 10)
    )

    val innerSize = brickSize * 0.5f
    val innerOffset = (brickSize - innerSize) / 2

    drawRect(
        color,
        actualLocation + Offset(innerOffset, innerOffset),
        size = Size(innerSize, innerSize)
    )

}


@Preview
@Composable
fun PreviewGamescreen(
    modifier: Modifier = Modifier
        .width(220.dp)
        .height(200.dp)
) {

    Box(
        modifier
            .background(Color.Black)
            .padding(1.dp)
            .background(ScreenBackground)
            .padding(10.dp)
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(ScreenBackground)
        ) {

            val brickSize = min(
                size.width / 12,
                size.height / 24
            )

            drawPlayground(brickSize = brickSize, 12 to 24)

        }

    }


}