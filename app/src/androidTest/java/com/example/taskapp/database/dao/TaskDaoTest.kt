package com.example.taskapp.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.utils.FakeTasks
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var taskDao: TaskDao

    private lateinit var appDataBase: AppDataBase

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        appDataBase = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = appDataBase.taskDao()
    }

    @Test
    fun insertAndGetTask() {
        runBlocking {
            taskDao.insertItem(FakeTasks.dbTasks.first())
        }
    }

    @After
    fun close() {
        appDataBase.close()
    }

}