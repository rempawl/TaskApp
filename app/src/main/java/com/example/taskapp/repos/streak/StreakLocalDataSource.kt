package com.example.taskapp.repos.streak

import com.example.taskapp.database.dao.StreakDao
import com.example.taskapp.database.entities.Streak
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
class StreakLocalDataSource @Inject constructor(private val streakDao: StreakDao) : StreakDataSource{
    override suspend fun saveStreak(item: Streak) = withContext(Dispatchers.IO){ streakDao.insertItem(item)}

}