package com.example.tictactoe.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.other.Constants
import com.example.tictactoe.players.PlayersManager
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

    private val _isBoardFilled = MutableLiveData(false)
    val isBoardFilled: LiveData<Boolean> = _isBoardFilled

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
                _winner.postValue(currentPlayerValue)
            } else if (checkIsBoardFilled()) {
                _isBoardFilled.postValue(true)
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
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
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
}
