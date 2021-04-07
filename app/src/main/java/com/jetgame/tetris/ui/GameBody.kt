package com.jetgame.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetgame.tetris.R
import com.jetgame.tetris.logic.Direction
import com.jetgame.tetris.ui.theme.BodyColor
import com.jetgame.tetris.ui.theme.ScreenBackground

@Composable
fun GameBody(
    clickable: Clickable = combinedClickable(),
    screen: @Composable () -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .background(BodyColor, RoundedCornerShape(10.dp))
            .padding(top = 20.dp)
    ) {

        //Screen
        Box(Modifier.align(Alignment.CenterHorizontally)) {


            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(330.dp, 400.dp)
                    .padding(top = 20.dp)
                    .background(Color.Black.copy(alpha = 0.8f))
                    .padding(5.dp)
                    .background(BodyColor)
            )

            Box(
                Modifier
                    .width(120.dp)
                    .height(45.dp)
                    .align(Alignment.TopCenter)
                    .background(BodyColor)
            ) {
                Text(
                    stringResource(id = R.string.body_label),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

            }

            Box(
                Modifier
                    .align(Alignment.Center)
                    .size(360.dp, 380.dp)
                    .padding(start = 50.dp, end = 50.dp, top = 50.dp, bottom = 30.dp)
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
        }

        Spacer(modifier = Modifier.height(20.dp))

        val SettingText = @Composable { text: String, modifier: Modifier ->
            Text(
                text, modifier = modifier,
                color = Color.Black.copy(0.9f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }


        //Setting Button
        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
        ) {
            Row {
                SettingText(stringResource(id = R.string.button_sounds), Modifier.weight(1f))
                SettingText(stringResource(id = R.string.button_pause), Modifier.weight(1f))
                SettingText(stringResource(id = R.string.button_reset), Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {

                //SOUNDS
                GameButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp, end = 20.dp),
                    onClick = { clickable.onMute() },
                    size = SettingButtonSize
                ) {}

                //PAUSE
                GameButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp, end = 20.dp),
                    onClick = { clickable.onPause() },
                    size = SettingButtonSize
                ) {}

                //RESET
                GameButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp, end = 20.dp),
                    onClick = { clickable.onRestart() },
                    size = SettingButtonSize
                ) {}

            }
        }


        Spacer(modifier = Modifier.height(30.dp))


        //Game Button
        val ButtonText = @Composable { modifier: Modifier,
                                       text: String ->
            Text(
                text, modifier = modifier,
                color = Color.White.copy(0.9f),
                fontSize = 18.sp
            )
        }

        Row(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
                .height(160.dp)
        ) {
            //DIRECTION BTN
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                GameButton(
                    Modifier.align(Alignment.TopCenter),
                    onClick = { clickable.onMove(Direction.Up) },
                    autoInvokeWhenPressed = false,
                    size = DirectionButtonSize
                ) {
                    ButtonText(it, stringResource(id = R.string.button_up))
                }
                GameButton(
                    Modifier.align(Alignment.CenterStart),
                    onClick = { clickable.onMove(Direction.Left) },
                    autoInvokeWhenPressed = true,
                    size = DirectionButtonSize
                ) {
                    ButtonText(it, stringResource(id = R.string.button_left))
                }
                GameButton(
                    Modifier.align(Alignment.CenterEnd),
                    onClick = { clickable.onMove(Direction.Right) },
                    autoInvokeWhenPressed = true,
                    size = DirectionButtonSize
                ) {
                    ButtonText(it, stringResource(id = R.string.button_right))
                }
                GameButton(
                    Modifier.align(Alignment.BottomCenter),
                    onClick = { clickable.onMove(Direction.Down) },
                    autoInvokeWhenPressed = true,
                    size = DirectionButtonSize
                ) {
                    ButtonText(it, stringResource(id = R.string.button_down))
                }

            }


            //ROTATE BTN
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                GameButton(
                    Modifier.align(Alignment.CenterEnd),
                    onClick = { clickable.onRotate() },
                    autoInvokeWhenPressed = false,
                    size = RotateButtonSize
                ) {
                    ButtonText(it, stringResource(id = R.string.button_rotate))
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


data class Clickable constructor(
    val onMove: (Direction) -> Unit,
    val onRotate: () -> Unit,
    val onRestart: () -> Unit,
    val onPause: () -> Unit,
    val onMute: () -> Unit
)

fun combinedClickable(
    onMove: (Direction) -> Unit = {},
    onRotate: () -> Unit = {},
    onRestart: () -> Unit = {},
    onPause: () -> Unit = {},
    onMute: () -> Unit = {}
) = Clickable(onMove, onRotate, onRestart, onPause, onMute)


@Preview(widthDp = 400, heightDp = 700)
@Composable
fun PreviewGameBody() {
    GameBody {}
}


val DirectionButtonSize = 60.dp
val RotateButtonSize = 90.dp
val SettingButtonSize = 15.dp