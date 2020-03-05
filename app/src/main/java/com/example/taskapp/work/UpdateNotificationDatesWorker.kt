package com.example.taskapp.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.repos.task.TaskLocalDataSource
import com.example.taskapp.repos.task.TaskRepository

class UpdateNotificationDatesWorker (appContext: Context, workerParameters: WorkerParameters):
    CoroutineWorker(appContext,workerParameters) {


    companion object{
        const val  WORK_NAME = "UpdateNotificationDatesWorker"
    }


    override suspend fun doWork(): Result {
        val taskDataSource = TaskLocalDataSource(AppDataBase.getInstance(applicationContext).taskDao())
        val taskRepo = TaskRepository(taskDataSource)
        val tasks = taskRepo.getTasks()

        if(tasks.first() == TaskRepository.ERROR_TASK){
            return Result.retry()
        }else{
            //todo update
        }

        return Result.success()
    }
}