package com.example.tictactoe.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivityMenuBinding
import com.example.tictactoe.players.Player
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
        binding.selectCrossesPlayer.setOnClickListener { provideSelectCrossesPlayer() }
        binding.selectCrossesPlayerImg.setOnClickListener { provideSelectCrossesPlayer() }
        binding.selectZeroesPlayer.setOnClickListener { provideSelectZeroesPlayer() }
        binding.selectZeroesPlayerImg.setOnClickListener { provideSelectZeroesPlayer() }
        initCrossesPlayer()
        initZeroesPlayer()
    }

    private fun provideSelectCrossesPlayer() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("New crosses player")
            .setView(input)
            .setPositiveButton("Add player") { dialog, which ->
                menuViewModel.addCrossesPlayer(Player(name = input.text.toString()))
                initCrossesPlayer()
            }
            .show()
    }

    private fun provideSelectZeroesPlayer() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("New zeroes player")
            .setView(input)
            .setPositiveButton("Add player") { dialog, which ->
                menuViewModel.addZeroesPlayer(Player(name = input.text.toString()))
                initZeroesPlayer()
            }
            .show()
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