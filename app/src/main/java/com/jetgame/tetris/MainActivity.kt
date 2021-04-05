package com.jetgame.tetris

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jetgame.tetris.logic.Direction
import com.jetgame.tetris.logic.GameViewModel
import com.jetgame.tetris.ui.GameBody
import com.jetgame.tetris.ui.GameScreen
import com.jetgame.tetris.ui.PreviewGamescreen
import com.jetgame.tetris.ui.combinedClickable
import com.jetgame.tetris.ui.theme.ComposetetrisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar(window)
        setContent {
            ComposetetrisTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val viewModel = viewModel<GameViewModel>()

                    GameBody(combinedClickable(
                        onMove = { direction: Direction ->
                            if (direction == Direction.UP) viewModel.dispatch(GameViewModel.Intent.Drop)
                            else viewModel.dispatch(GameViewModel.Intent.Move(direction))
                        },
                        onRotate = {
                            viewModel.dispatch(GameViewModel.Intent.Rotate)
                        },
                        onRestart = {
                            viewModel.dispatch(GameViewModel.Intent.Restart)
                        }
                    )) {
                        GameScreen(
                            Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }


    private fun transparentStatusBar(window: Window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        val vis = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = option or vis
        window.statusBarColor = Color.TRANSPARENT
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposetetrisTheme {
        GameBody {
            PreviewGamescreen(Modifier.fillMaxSize())
        }
    }
}