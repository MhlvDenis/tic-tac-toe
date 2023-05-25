package com.example.tictactoe.players

class PlayersManager {
    private val players = mutableListOf<Player>()
    var crossesPlayer: Player? = null
    var zeroesPlayer: Player? = null

    fun addNewPlayer(player: Player) {
        for (i in players.indices) {
            if (players[i].name == player.name) {
                players[i] = player
                return
            }
        }
        players.add(player)
    }

    fun getPlayers(): List<Player> = players
}