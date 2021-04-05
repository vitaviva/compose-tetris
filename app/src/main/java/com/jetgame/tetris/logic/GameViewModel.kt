package com.jetgame.tetris.logic

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
        Intent.Restart -> ViewState()
        Intent.Pause -> state
        Intent.Resume -> state
        is Intent.Move -> {
            val offset = intent.direction.toOffset()
            val spirit = state.spirit.moveBy(offset)
            if (spirit.isValidInMatrix(state.bricks, state.matrix)) {
                state.copy(spirit = spirit)
            } else {
                state
            }
        }
        Intent.Rotate -> {
            val spirit = state.spirit.rotate().adjustOffset(state.matrix)
            if (spirit.isValidInMatrix(state.bricks, state.matrix)) {
                state.copy(spirit = spirit)
            } else {
                state
            }
        }
        Intent.GameTick -> run {
            if (state.spirit != Empty) {
                val spirit = state.spirit.moveBy(Direction.DOWN.toOffset())
                if (spirit.isValidInMatrix(state.bricks, state.matrix)) {
                    return@run state.copy(spirit = spirit)
                }
            }
            val reserve =
                if (state.spiritReserve.isEmpty())
                    generateSpiritReverse(state.matrix)
                else state.spiritReserve
            val spirit = reserve.first()
            state.copy(
                spirit = spirit,
                spiritReserve = reserve - spirit,
                bricks = updateBricks(state.bricks, state.spirit, matrix = state.matrix)
            )

        }
        Intent.Drop -> {
            var i = 0
            while (state.spirit.moveBy(0 to ++i)
                    .isValidInMatrix(state.bricks, state.matrix)
            ) {
                //nothing to do
            }
            val spirit = state.spirit.moveBy(0 to i - 1)

            state.copy(spirit = spirit)
        }
    }

    private fun updateBricks(
        curBricks: List<Brick>,
        spirit: Spirit,
        matrix: Pair<Int, Int>
    ): List<Brick> {
        val bricks = (curBricks + Brick.of(spirit))
        val map = mutableMapOf<Float, MutableSet<Float>>()
        bricks.forEach {
            map.getOrPut(it.location.y) {
                mutableSetOf()
            }.add(it.location.x)
        }
        var res = bricks
        map.entries.sortedBy { it.key }.forEach { entry ->
            if (entry.value.size == matrix.first) {
                //clear line
                res = res.filter { it.location.y != entry.key }
                    .map { if (it.location.y < entry.key) it.offsetBy(0 to 1) else it }

            }
        }

        return res
    }

    data class ViewState(
        val bricks: List<Brick> = emptyList(),
        val spirit: Spirit = Empty,
        val spiritReserve: List<Spirit> = emptyList(),
        val matrix: Pair<Int, Int> = MatrixWidth to MatrixHeight
    )

    sealed class Intent {
        data class Move(val direction: Direction) : Intent()
        object Restart : Intent()
        object Pause : Intent()
        object Resume : Intent()
        object Rotate : Intent()
        object Drop : Intent()
        object GameTick : Intent()
    }
}


private const val MatrixWidth = 12
private const val MatrixHeight = 24