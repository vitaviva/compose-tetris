package com.jetgame.tetris.logic

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetgame.tetris.logic.Spirit.Companion.Empty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GameViewModel : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()


    fun dispatch(intent: Intent) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _viewState.value = reduce(viewState.value, intent)
            }
        }
    }

    private fun reduce(state: ViewState, intent: Intent): ViewState = when (intent) {
        Intent.Tap -> state
        Intent.Restart -> state
        Intent.Pause -> state
        Intent.Resume -> state
        is Intent.Swipe -> {
            state
        }
        Intent.Rotate -> {
            state
        }
        Intent.GameTick -> run {
            if (state.spirit != Empty) {
                val spirit = state.spirit.move(0, 1)
                if (spirit.isValid(state.bricks, state.playground)) {
                    return@run state.copy(spirit = spirit)
                }
            }
            val reserve =
                if (state.spiritReserve.isEmpty())
                    generateSpiritReverse(state.playground)
                else state.spiritReserve
            val spirit = reserve.first()
            state.copy(
                spirit = spirit,
                spiritReserve = reserve - spirit,
                bricks = state.bricks + Brick.of(state.spirit)
            )

        }
    }

    data class ViewState(
        val bricks: List<Brick> = emptyList(),
        val spirit: Spirit = Empty,
        val spiritReserve: List<Spirit> = emptyList(),
        val playground: Pair<Int, Int> = BrickCountX to BrickCountY
    )

    sealed class Intent {
        data class Swipe(val direction: Direction) : Intent()
        object Restart : Intent()
        object Pause : Intent()
        object Resume : Intent()
        object Rotate : Intent()
        object Tap : Intent()
        object GameTick : Intent()
    }
}

typealias Board = List<List<Color>>
typealias BoardSize = Pair<Int, Int>

enum class Direction {
    LEFT, UP, RIGHT, DOWN
}

private const val BrickCountX = 12
private const val BrickCountY = 24