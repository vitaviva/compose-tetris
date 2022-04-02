package com.jetgame.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetgame.tetris.ui.theme.BodyColor
import com.jetgame.tetris.ui.theme.BrickSpirit
import com.jetgame.tetris.ui.theme.ScreenBackground

@Composable
private fun AppIcon() {

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .background(BodyColor, RoundedCornerShape(50.dp))
            .padding(top = 30.dp)
    ) {

        //Screen
        Box(Modifier.align(Alignment.CenterHorizontally)) {

            Box(
                Modifier
                    .align(Alignment.Center)
                    .size(360.dp, 220.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
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
                        .padding(12.dp)
                        .background(ScreenBackground)
                )

                Text(
                    "TETRIS",
                    textAlign = TextAlign.Center,
                    color = BrickSpirit,
                    fontSize = 75.sp,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold
                )
            }
        }


        Row(
            modifier = Modifier
                .padding(start = 45.dp, end = 45.dp)
                .height(160.dp)
                .padding(bottom = 10.dp)
        ) {
            //DIRECTION BTN
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.55f)
            ) {
                GameButton(
                    Modifier.align(Alignment.TopCenter),
                    size = DirectionButtonSize
                )
                GameButton(
                    Modifier.align(Alignment.CenterStart),
                    size = DirectionButtonSize
                )
                GameButton(
                    Modifier.align(Alignment.CenterEnd),
                    size = DirectionButtonSize
                )
                GameButton(
                    Modifier.align(Alignment.BottomCenter),
                    size = DirectionButtonSize
                )

            }


            //ROTATE BTN
            Box(
                modifier = Modifier
                    .weight(0.45f)
                    .fillMaxHeight()
            ) {
                GameButton(
                    Modifier.align(Alignment.CenterEnd),
                    size = RotateButtonSize
                )
            }
        }

    }
}


@Preview(widthDp = 400, heightDp = 400)
@Composable
private fun PreviewAppIcon() {
    AppIcon()
}