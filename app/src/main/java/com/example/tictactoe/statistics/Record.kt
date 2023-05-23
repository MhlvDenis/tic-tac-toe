package com.example.tictactoe.statistics

import com.example.tictactoe.players.Player

data class Record(
    val crossesPlayer: Player,
    val zerosPlayer: Player,
    val winner: Player
)