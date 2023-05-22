package com.example.tictactoe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.tictactoe.databinding.ActivityMainBinding
import com.example.tictactoe.other.Constants
import com.example.tictactoe.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBoard()
        mainViewModel.currentPlayer.observe(this@MainActivity, Observer {
            val currentPlayer = it ?: return@Observer
            if (currentPlayer == Constants.CROSS) {
                binding.putTV.text = "Put Cross"
            } else {
                binding.putTV.text = "Put Zero"
            }
        })
        mainViewModel.winner.observe(this@MainActivity, Observer {
            val winner = it ?: return@Observer
            if (winner == Constants.CROSS) {
                provideGameFinished("Crosses won")
            } else if (winner == Constants.ZERO) {
                provideGameFinished("Zeroes won")
            }
        })
        mainViewModel.isBoardFilled.observe(this@MainActivity, Observer {
            val isBoardFilled = it ?: return@Observer
            if (isBoardFilled) {
                provideGameFinished("Draw")
            }
        })
    }

    private fun provideGameFinished(message: String) {
        AlertDialog.Builder(this)
            .setTitle(message)
            .setPositiveButton("Return to menu") { _, _ ->

            }
            .setCancelable(false)
            .show()
    }

    private fun initBoard() {
        initButton(binding.a1, 0, 0)
        initButton(binding.a2, 0, 1)
        initButton(binding.a3, 0, 2)
        initButton(binding.b1, 1, 0)
        initButton(binding.b2, 1, 1)
        initButton(binding.b3, 1, 2)
        initButton(binding.c1, 2, 0)
        initButton(binding.c2, 2, 1)
        initButton(binding.c3, 2, 2)
    }

    private fun initButton(button: Button, row: Int, col: Int) {
        button.apply {
            text = mainViewModel.getCell(row, col)
            setOnClickListener {
                text = mainViewModel.updateBoard(row, col)
            }
        }
    }
}
