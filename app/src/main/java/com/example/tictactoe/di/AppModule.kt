package com.example.tictactoe.di

import com.example.tictactoe.players.PlayersManager
import com.example.tictactoe.statistics.StatisticsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePlayersManager() = PlayersManager()

    @Singleton
    @Provides
    fun provideStatisticsManager() = StatisticsManager()
}