package com.jetgame.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetgame.tetris.ui.theme.BodyBackground
import com.jetgame.tetris.ui.theme.Purple200
import com.jetgame.tetris.ui.theme.Purple500
import com.jetgame.tetris.ui.theme.ScreenBackground

@Composable
fun GameBody(screen: @Composable () -> Unit) {

    Column(Modifier.background(BodyBackground)) {

        Box(
            Modifier
                .size(400.dp, 450.dp)
                .padding(50.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawScreenBorder(
                    Offset(0f, 0f),
                    Offset(size.width, 0f),
                    Offset(0f, size.height),
                    Offset(size.width, size.height)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .background(ScreenBackground)
            ) {
                screen()
            }
        }


        val ButtonText = @Composable { text: String, modifier: Modifier ->
            Text(
                text, modifier = modifier,
                color = Color.White.copy(0.9f),
                fontSize = 20.sp
            )
        }

        Row(
            modifier = Modifier
                .padding(40.dp)
                .height(160.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                GameButton(
                    Modifier.align(Alignment.TopCenter),
                    size = DirectionButtonSize
                ) {
                    ButtonText("▲", it)
                }
                GameButton(
                    Modifier.align(Alignment.CenterStart),
                    size = DirectionButtonSize
                ) {
                    ButtonText("◀", it)
                }
                GameButton(
                    Modifier.align(Alignment.CenterEnd),
                    size = DirectionButtonSize
                ) {
                    ButtonText("▶", it)
                }
                GameButton(
                    Modifier.align(Alignment.BottomCenter),
                    size = DirectionButtonSize
                ) {
                    ButtonText("▼", it)
                }

            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                GameButton(
                    Modifier.align(Alignment.CenterEnd),
                    size = DropButtonSize
                ) {
                    ButtonText("Drop", it)
                }
            }
        }

    }
}


fun DrawScope.drawScreenBorder(
    topLef: Offset,
    topRight: Offset,
    bottomLeft: Offset,
    bottomRight: Offset
) {
    var path = Path().apply {
        moveTo(topLef.x, topLef.y)
        lineTo(topRight.x, topRight.y)
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            topLef.y + topRight.x / 2 + topLef.x / 2
        )
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            bottomLeft.y - topRight.x / 2 + topLef.x / 2
        )
        lineTo(bottomLeft.x, bottomLeft.y)
        close()
    }
    drawPath(path, Color.Black.copy(0.5f))

    path = Path().apply {
        moveTo(bottomRight.x, bottomRight.y)
        lineTo(bottomLeft.x, bottomLeft.y)
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            bottomLeft.y - topRight.x / 2 + topLef.x / 2
        )
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            topLef.y + topRight.x / 2 + topLef.x / 2
        )
        lineTo(topRight.x, topRight.y)
        close()
    }

    drawPath(path, Color.White.copy(0.5f))

}

@Composable
fun GameButton(
    modifier: Modifier = Modifier,
    size: Dp, content: @Composable (Modifier) -> Unit
) {
    val backgroundShape = RoundedCornerShape(size / 2)
    Box(
        modifier = modifier
            .shadow(5.dp, shape = backgroundShape)
            .size(size = size)
//            .padding(20.dp)
            .clip(backgroundShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Purple200,
                        Purple500
                    ),
                    startY = 0f,
                    endY = 80f
                )
            )
            .clickable { },
    ) {
        content(Modifier.align(Alignment.Center))
    }
}


@Preview
@Composable
fun PreviewGameBody() {
    GameBody {}
}


val DirectionButtonSize = 60.dp
val DropButtonSize = 90.dp