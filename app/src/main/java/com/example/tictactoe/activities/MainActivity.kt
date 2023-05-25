package com.example.tictactoe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivityMainBinding
import com.example.tictactoe.other.Constants
import com.example.tictactoe.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                binding.putTV.text = getString(R.string.put_cross)
            } else {
                binding.putTV.text = getString(R.string.put_zero)
            }
        })
        mainViewModel.winner.observe(this@MainActivity, Observer {
            val winner = it ?: return@Observer
            if (winner == Constants.CROSS) {
                provideGameFinished(getString(R.string.crosses_won))
            } else if (winner == Constants.ZERO) {
                provideGameFinished(getString(R.string.zeroes_won))
            }
        })
        mainViewModel.isDraw.observe(this@MainActivity, Observer {
            val isBoardFilled = it ?: return@Observer
            if (isBoardFilled) {
                provideGameFinished(getString(R.string.draw))
            }
        })
    }

    private fun provideGameFinished(message: String) {
        AlertDialog.Builder(this)
            .setTitle(message)
            .setPositiveButton(getString(R.string.return_to_main_menu)) { _, _ ->
                finish()
                startActivity(Intent(this, MenuActivity::class.java))
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
