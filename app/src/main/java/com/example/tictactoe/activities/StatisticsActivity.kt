package com.example.tictactoe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tictactoe.adapters.StatisticsAdapter
import com.example.tictactoe.databinding.ActivityStatisticsBinding
import com.example.tictactoe.statistics.StatisticsManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StatisticsActivity : AppCompatActivity() {
    @Inject
    lateinit var statisticsManager: StatisticsManager

    private lateinit var binding: ActivityStatisticsBinding

    @Inject
    lateinit var statisticsAdapter: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this)
        binding.statisticsRV.layoutManager = manager
        binding.statisticsRV.adapter = statisticsAdapter
    }
}