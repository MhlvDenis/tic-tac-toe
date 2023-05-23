package com.example.tictactoe.players

class PlayersManager {
    private val players = mutableListOf<Player>()
    var firstCurrentPlayer: Player? = null
    var secondCurrentPlayer: Player? = null

    fun addNewPlayer(player: Player) {
        players.add(player)
    }

    fun getPlayers(): List<Player> = players
}