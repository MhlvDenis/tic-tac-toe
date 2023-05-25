package com.example.tictactoe.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.tictactoe.R
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
        binding.scoreTable.setOnClickListener {
            startActivity(Intent(this, StatisticsActivity::class.java))
        }
        initCrossesPlayer()
        initZeroesPlayer()
    }

    private fun initCrossesPlayer() {
        val crossesPlayer = menuViewModel.getCrossesPlayer()
        if (crossesPlayer != null) {
            binding.selectCrossesPlayer.visibility = View.GONE
            binding.selectCrossesPlayerImg.visibility = View.VISIBLE
            binding.selectCrossesPlayerImg.background = AppCompatResources.getDrawable(
                this,
                R.drawable.ic_launcher_background
            )
        }
    }

    private fun initZeroesPlayer() {
        val zeroesPlayer = menuViewModel.getZeroesPlayer()
        if (zeroesPlayer != null) {
            binding.selectZeroesPlayer.visibility = View.GONE
            binding.selectZeroesPlayerImg.visibility = View.VISIBLE
            binding.selectZeroesPlayerImg.background = AppCompatResources.getDrawable(
                this,
                R.drawable.ic_launcher_background
            )
        }
    }
}