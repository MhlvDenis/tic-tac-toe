package com.example.tictactoe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe.databinding.ItemRecordBinding
import com.example.tictactoe.statistics.StatisticsManager
import javax.inject.Inject

class StatisticsAdapter @Inject constructor(
    private val statisticsManager: StatisticsManager
) : RecyclerView.Adapter<StatisticsAdapter.RecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecordBinding.inflate(inflater, parent, false)

        return RecordViewHolder(binding)
    }

    override fun getItemCount() = statisticsManager.getStatistics().size

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = statisticsManager.getStatistics()[position]
        with (holder.binding) {
            xPlayer.text = record.crossesPlayer.name
            oPlayer.text = record.zeroesPlayer.name
            winner.text = record.winner.name
        }
    }

    class RecordViewHolder(val binding: ItemRecordBinding) : RecyclerView.ViewHolder(binding.root)
}