package com.example.tictactoe.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMenuBinding
import com.example.tictactoe.viewmodels.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {
    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.play.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}