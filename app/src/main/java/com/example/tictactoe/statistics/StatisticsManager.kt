package com.example.tictactoe.statistics

class StatisticsManager {
    private val statistics = mutableListOf<Record>()

    fun addNewRecord(record: Record) {
        statistics.add(record)
    }

    fun getStatistics(): List<Record> = statistics
}