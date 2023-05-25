package com.example.tictactoe.viewmodels

import androidx.lifecycle.ViewModel
import com.example.tictactoe.players.Player
import com.example.tictactoe.players.PlayersManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val playersManager: PlayersManager
) : ViewModel() {
    fun addCrossesPlayer(player: Player) {
        playersManager.crossesPlayer = player
        playersManager.addNewPlayer(player)
    }

    fun addZeroesPlayer(player: Player) {
        playersManager.zeroesPlayer = player
        playersManager.addNewPlayer(player)
    }

    fun getCrossesPlayer() = playersManager.crossesPlayer

    fun getZeroesPlayer() = playersManager.zeroesPlayer
}