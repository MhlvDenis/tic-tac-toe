package com.example.tictactoe.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.other.Constants
import com.example.tictactoe.players.Player
import com.example.tictactoe.players.PlayersManager
import com.example.tictactoe.statistics.Record
import com.example.tictactoe.statistics.StatisticsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val playersManager: PlayersManager,
    private val statisticsManager: StatisticsManager
): ViewModel() {
    private val _currentPlayer = MutableLiveData(Constants.CROSS)
    val currentPlayer: LiveData<String> = _currentPlayer

    private val _winner = MutableLiveData(Constants.EMPTY)
    val winner: LiveData<String> = _winner

    private val _isDraw = MutableLiveData(false)
    val isDraw: LiveData<Boolean> = _isDraw

    private val board = arrayOf(
        arrayOf(Constants.EMPTY, Constants.EMPTY, Constants.EMPTY),
        arrayOf(Constants.EMPTY, Constants.EMPTY, Constants.EMPTY),
        arrayOf(Constants.EMPTY, Constants.EMPTY, Constants.EMPTY)
    )

    fun getCell(row: Int, col: Int) = board[row][col]

    fun updateBoard(row: Int, col: Int): String {
        if (board[row][col] == Constants.EMPTY) {
            val currentPlayerValue = currentPlayer.value!!
            board[row][col] = currentPlayerValue

            if (checkWinner(currentPlayerValue)) {
                applyWinner()
            } else if (checkIsBoardFilled()) {
                applyDraw()
            } else {
                _currentPlayer.postValue(
                    if (currentPlayerValue == Constants.CROSS)
                        Constants.ZERO
                    else
                        Constants.CROSS
                )
            }

        }
        return board[row][col]
    }

    private fun checkWinner(player: String): Boolean {
        // rows
        if (board[0].all { it == player }) {
            return true
        }
        if (board[1].all { it == player }) {
            return true
        }
        if (board[2].all { it == player }) {
            return true
        }

        // columns
        for (i in 0..2) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true
            }
        }

        // diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true
        }
        if (board[2][0] == player && board[1][1] == player && board[0][2] == player) {
            return true
        }

        return false
    }

    private fun checkIsBoardFilled() =
        board.all { row ->
            row.all { it != Constants.EMPTY }
        }

    private fun applyWinner() {
        statisticsManager.addNewRecord(
            Record(
                crossesPlayer = playersManager.crossesPlayer!!,
                zeroesPlayer = playersManager.zeroesPlayer!!,
                winner =
                    if (currentPlayer.value!! == Constants.CROSS)
                        playersManager.crossesPlayer!!
                    else
                        playersManager.zeroesPlayer!!
            )
        )
        _winner.postValue(currentPlayer.value!!)
    }

    private fun applyDraw() {
        statisticsManager.addNewRecord(
            Record(
                crossesPlayer = playersManager.crossesPlayer!!,
                zeroesPlayer = playersManager.zeroesPlayer!!,
                winner = Player(name = "-")
            )
        )
        _isDraw.postValue(true)
    }
}
