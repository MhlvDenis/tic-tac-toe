package com.example.tictactoe.players

import android.net.Uri

data class Player(
    val name: String,
    val imageUri: Uri? = null
)