package com.example.tictactoe.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.players.Player
import com.example.tictactoe.players.PlayersManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val playersManager: PlayersManager
) : ViewModel() {
    private val _isFormValid = MutableLiveData(isPlayersSelected())
    val isFormValid: LiveData<Boolean> = _isFormValid

    fun addCrossesPlayer(player: Player) {
        playersManager.crossesPlayer = player
        playersManager.addNewPlayer(player)
        playerDataChanged()
    }

    fun addZeroesPlayer(player: Player) {
        playersManager.zeroesPlayer = player
        playersManager.addNewPlayer(player)
        playerDataChanged()
    }

    fun getCrossesPlayer() = playersManager.crossesPlayer

    fun getZeroesPlayer() = playersManager.zeroesPlayer

    private fun playerDataChanged() {
        _isFormValid.postValue(isPlayersSelected())
    }

    private fun isPlayersSelected() = playersManager.crossesPlayer != null && playersManager.zeroesPlayer != null
}