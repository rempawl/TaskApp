package com.example.taskapp.dataSources.streak

import com.example.taskapp.database.entities.Streak

interface StreakDataSource {
    suspend fun saveStreak(item: Streak): Long
}