package com.example.tictactoe.viewmodels

import androidx.lifecycle.ViewModel
import com.example.tictactoe.players.PlayersManager
import com.example.tictactoe.statistics.StatisticsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val playersManager: PlayersManager,
    private val statisticsManager: StatisticsManager
) : ViewModel() {

}