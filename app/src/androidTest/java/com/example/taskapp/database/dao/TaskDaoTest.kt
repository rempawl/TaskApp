package com.example.taskapp.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.utils.FakeTasks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
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
        runBlockingTest {
            taskDao.insertItem(FakeTasks.dbTasks.first())
        }
    }

    @After
    fun close() {
        appDataBase.close()
    }

}