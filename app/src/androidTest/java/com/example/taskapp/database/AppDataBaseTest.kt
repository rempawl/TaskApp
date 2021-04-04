package com.example.taskapp.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.taskapp.database.dao.TaskDao
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {
    private lateinit var taskDao: TaskDao

    private lateinit var appDataBase: AppDataBase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        appDataBase = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
    }

        @After
    fun close() {
        appDataBase.close()
    }
}
